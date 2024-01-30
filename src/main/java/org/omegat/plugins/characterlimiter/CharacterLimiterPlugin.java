package org.omegat.plugins.characterlimiter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ResourceBundle;

import org.omegat.core.Core;
import org.omegat.core.CoreEvents;
import org.omegat.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharacterLimiterPlugin {
	private static final Logger logger = LoggerFactory.getLogger(CharacterLimiterPlugin.class);
	private static ResourceBundle bundle = ResourceBundle.getBundle("org/omegat/plugins/characterlimiter/Bundle");
	private CharacterLimiterMenu menu;
	private String config_path;

	public CharacterLimiterPlugin() {
		menu = new CharacterLimiterMenu(this);
		CoreEvents.registerApplicationEventListener(new ApplicationEventListenerSessionLog(this));
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
	}
	
	void disable() {
		write();
		menu.setSelected(false);
	}
	
	void close() {
		disable();
		menu.setEnabled(false);
		config_path = null;
	}
	
	private void write() {
		if (config_path != null) {
			try {
				StringBuilder string_builder = new StringBuilder();
//				ticks.forEach(o -> string_builder.append(o).append("\r\n"));
				Files.write(new File(config_path).toPath(), string_builder.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
//				ticks.clear();
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
