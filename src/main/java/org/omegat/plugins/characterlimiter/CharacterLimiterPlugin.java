package org.omegat.plugins.characterlimiter;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

import com.vlsolutions.swing.docking.Dockable;
import org.omegat.core.Core;
import org.omegat.core.CoreEvents;
import org.omegat.gui.main.DockableScrollPane;
import org.omegat.gui.main.IMainWindow;
import org.omegat.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class CharacterLimiterPlugin {
	private static final Logger logger = LoggerFactory.getLogger(CharacterLimiterPlugin.class);
	private static ResourceBundle bundle = ResourceBundle.getBundle("org/omegat/plugins/characterlimiter/Bundle");
	private CharacterLimiterMenu menu;
	PluginConfig plugin_config;
	private DockableScrollPane scrollPane;

	public CharacterLimiterPlugin() {
		menu = new CharacterLimiterMenu(this);
		CoreEvents.registerApplicationEventListener(new PluginApplicationEventListener(this));
	} 
	
	CharacterLimiterMenu getMenu() {
		return menu;
	}
	
	static String getLocalizedString(String key, Object... formatArgs) {
		return StringUtil.format(bundle.getString(key), formatArgs);
	}
	
	void start() {
		logger.info("[PLUGIN] Initializing start...");
		menu.set_limiter_menu_items_enabled(true);
		menu.set_limiter_menu_items_selected(true);
		plugin_config = new PluginConfig();
		add_plugin_dockable();
	}

	void add_plugin_dockable()
	{
		JTextPane text_pane = new JTextPane();
		text_pane.setOpaque(true);
		text_pane.setBackground(Color.white);
		scrollPane = new DockableScrollPane("plugin_dockable_infobox", getLocalizedString("PLUGIN_DOCKABLE_TITLE"), text_pane, true);
		IMainWindow mw = Core.getMainWindow();
		mw.getDesktop().addDockable(scrollPane);
	}

	void remove_plugin_dockable()
	{
		IMainWindow mw = Core.getMainWindow();
		mw.getDesktop().remove((Dockable) scrollPane);
	}

	void set_plugin_dockable_text(String dockable_text)
	{
		JTextPane text_pane = new JTextPane();
		text_pane.setContentType("text/html");
		text_pane.setText(dockable_text);
		text_pane.setEditable(false);
		text_pane.setFont(Core.getMainWindow().getApplicationFont());
		text_pane.setOpaque(true);
		text_pane.setBackground(Color.white);
		Border border = text_pane.getBorder();
		Border margin = new EmptyBorder(5,5,5,5);
		text_pane.setBorder(new CompoundBorder(border, margin));
		scrollPane.setViewportView(text_pane);
	}

	void play_limiter_sound() {
		logger.info("[PLUGIN] Initializing play_limiter_sound");
		try {
			URL sound_url = this.getClass().getResource("/beep.wav");
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(sound_url);
			Clip clip = AudioSystem.getClip();
			clip.addLineListener(new LineListener(){
				public void update(LineEvent event){
					if(event.getType() == LineEvent.Type.STOP){
						event.getLine().close();
						clip.close();
					}
				}
			});
			clip.open(audioIn);
			clip.start();
			audioIn.close();
		} catch (Exception ex) {
			logger.error("[PLUGIN] Can't play sound.");
		}
	}


	String get_dockable_text(int source_length, int translation_length, boolean enable_global_character_limit, int global_character_limit)
	{
		int character_limit;
		if (enable_global_character_limit)
		{
			character_limit = global_character_limit;
		}
		else
		{
			character_limit = source_length;
		}

		int percent = (translation_length * 100) / character_limit;
		String percent_color;

		if (percent < 60)
		{
			percent_color = "GREEN";
		}
		else if (percent >= 60 && percent <= 100)
		{
			percent_color = "ORANGE";
		}
		else
		{
			percent_color = "RED";
		}

		StringBuilder dockable_text = new StringBuilder();

		dockable_text.append("<html>");
		if (enable_global_character_limit)
		{
			dockable_text.append(CharacterLimiterPlugin.getLocalizedString("DOCKABLE_TEXT_GLOBAL_CHARACTER_LIMIT_LABEL"))
					.append(" ").append(character_limit);
			dockable_text.append("<br>");
		}
		dockable_text.append(CharacterLimiterPlugin.getLocalizedString("DOCKABLE_TEXT_SOURCE_TEXT_LENGTH_LABEL"))
				.append(" ").append(source_length);
		dockable_text.append("<br>");
		dockable_text.append(CharacterLimiterPlugin.getLocalizedString("DOCKABLE_TEXT_TRANSLATION_TEXT_LENGTH_LABEL")).append(" ");
		dockable_text.append(String.format("<font color=%s>", percent_color));
		dockable_text.append(translation_length);
		dockable_text.append(" (" + percent + "%)");
		dockable_text.append("</font>");
		dockable_text.append("</html>");


		return dockable_text.toString();
	}

	private void limit_text_on_update(String translation_text, int character_limit) {
		Runnable do_text_limiting = new Runnable() {
			@Override
			public void run() {
				String text_to_replace = translation_text.substring(0, character_limit);
				Core.getEditor().replaceEditText(text_to_replace);
			}
		};
		SwingUtilities.invokeLater(do_text_limiting);
	}


	void execute_limit_characters_logic()
	{
		String source_text = Core.getEditor().getCurrentEntry().getSrcText();
		String translation_text = Core.getEditor().getCurrentTranslation();
		String dockable_text = get_dockable_text(source_text.length(), translation_text.length(),
				plugin_config.enable_global_character_limit, plugin_config.global_character_limit);
		set_plugin_dockable_text(dockable_text);

		int character_limit;
		if (plugin_config.enable_global_character_limit)
		{
			character_limit = plugin_config.global_character_limit;
		}
		else
		{
			character_limit = source_text.length();
		}

		if (translation_text.length() > character_limit)
		{
			logger.info("[PLUGIN] Trying to limit text in segment to " + character_limit + " characters");
			if (!plugin_config.allow_longer_strings)
			{
				limit_text_on_update(translation_text, character_limit);
			}
			if (plugin_config.enable_sound)
			{
				play_limiter_sound();
			}
		}
	}




	void disable() {
		menu.set_limiter_menu_items_selected(false);
		remove_plugin_dockable();
	}
	
	void close() {
		disable();
		menu.set_limiter_menu_items_enabled(false);
		plugin_config = null;

		remove_plugin_dockable();
	}
	
	PluginConfig get_config() {
		if (plugin_config == null)
		{
			return new PluginConfig();
		}

		return plugin_config;
	}
}
