package org.omegat.plugins.characterlimiter;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
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
	private String config_path;
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
		logger.info("PLUGIN START...");
		File dir = new File(Core.getProject().getProjectProperties().getProjectRoot() + "/character_limiter_config");
		dir.mkdir();

		StringBuilder string_builder = new StringBuilder(dir.getAbsolutePath());
		string_builder.append("/config.ini");
		config_path = new File(string_builder.toString()).getAbsolutePath();
		menu.setEnabled(true);
		menu.setSelected(true);

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


	String get_dockable_text(int source_length, int translation_length)
	{
		int percent = (translation_length * 100) / source_length;
		String percent_color = "GREEN";

		if (percent >= 60 && percent <= 100)
		{
			percent_color = "ORANGE";
		}
		else if (percent > 100)
		{
			percent_color = "RED";
		}

		StringBuilder dockable_text = new StringBuilder();

		dockable_text.append("<html>");
		dockable_text.append("Source text length: ").append(source_length);
		dockable_text.append("<br>");
		dockable_text.append("Translation text length: ");
		dockable_text.append(String.format("<FONT COLOR=%s>", percent_color));
		dockable_text.append(translation_length);
		dockable_text.append(" (" + percent + "%)");
		dockable_text.append("</FONT>");
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
		int character_limit = source_text.length();  // TODO - add logic for global limit
		String dockable_text = get_dockable_text(source_text.length(), translation_text.length());
		set_plugin_dockable_text(dockable_text);

		if (translation_text.length() > character_limit)
		{
			logger.info("[PLUGIN] Limiting text in segment to " + character_limit + " characters");
			limit_text_on_update(translation_text, character_limit);
			play_limiter_sound();
		}
	}




	void disable() {
		write();
		menu.setSelected(false);

		remove_plugin_dockable();
	}
	
	void close() {
		disable();
		menu.setEnabled(false);
		config_path = null;

		remove_plugin_dockable();
	}
	
	private void write() {
		if (config_path != null) {
			try {
				StringBuilder string_builder = new StringBuilder();
				Files.write(new File(config_path).toPath(), string_builder.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	PluginConfig get_config() {
		if (config_path == null)
		{
			return new PluginConfig();
		}

		return new PluginConfig(); // TODO - fix this
	}
}
