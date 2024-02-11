package org.omegat.plugins.characterlimiter;

import java.io.File;
import java.io.IOException;
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

import javax.swing.*;

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
		JLabel label = new JLabel("");
		scrollPane = new DockableScrollPane("plugin_dockable_infobox", getLocalizedString("PLUGIN_DOCKABLE_TITLE"), label, true);
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
		JLabel label = new JLabel(dockable_text);
		scrollPane.setViewportView(label);
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
		// TODO
		return null;
	}
}
