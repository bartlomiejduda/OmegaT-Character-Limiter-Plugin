package org.omegat.plugins.characterlimiter;


import org.omegat.core.Core;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PluginConfig {

	private static final Logger logger = LoggerFactory.getLogger(PluginConfig.class);
	public String config_path;
	public boolean allow_longer_strings;
	public boolean enable_sound;
	public String sound_name;
	public boolean enable_global_character_limit;
	public int global_character_limit;

	public PluginConfig() {

		config_path = get_config_path();

		if (check_if_config_file_exists())
		{
			load_config_from_file();
		}
		else
		{
			// get default config
			allow_longer_strings = false;
			enable_sound = false;
			sound_name = "beep.wav";
			enable_global_character_limit = false;
			global_character_limit = 10;
			save_config_to_file(false);
		}

	}

	void load_config_from_file()
	{
		Properties prop = new Properties();

		try {
			prop.load(new FileInputStream(get_config_path()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		allow_longer_strings = Boolean.valueOf(prop.getProperty("allow_longer_strings"));
		enable_sound = Boolean.valueOf(prop.getProperty("enable_sound"));
		sound_name = prop.getProperty("sound_name");
		enable_global_character_limit = Boolean.valueOf(prop.getProperty("enable_global_character_limit"));
		global_character_limit = Integer.valueOf(prop.getProperty("global_character_limit"));
	}

	String get_config_path()
	{
		logger.info("[PLUGIN] Initializing get_config_path");
		File dir = new File(Core.getProject().getProjectProperties().getProjectRoot() + "/character_limiter_config");
		dir.mkdir();
		StringBuilder string_builder = new StringBuilder(dir.getAbsolutePath());
		string_builder.append("/config.properties");
		config_path = new File(string_builder.toString()).getAbsolutePath();
		return config_path;
	}

	void overwrite_config(boolean input_allow_longer_strings,
					 boolean input_enable_sound,
					 String input_sound_name,
					 boolean input_enable_global_character_limit,
					 int input_global_character_limit
	)
	{
		logger.info("[PLUGIN] Initializing overwrite_config");
		allow_longer_strings = input_allow_longer_strings;
		enable_sound = input_enable_sound;
		sound_name = input_sound_name;
		enable_global_character_limit = input_enable_global_character_limit;
		global_character_limit = input_global_character_limit;
		save_config_to_file(true);
	}

	boolean check_if_config_file_exists()
	{
		logger.info("[PLUGIN] Initializing check_if_config_file_exists");
		if (config_path != null)
		{
			File f = new File(config_path);
			if(f.exists() && !f.isDirectory()) {
				logger.info("[PLUGIN] Config file exists");
				return true;
			}
		}

		logger.info("[PLUGIN] Config file doesn't exist");
		return false;
	}


	void save_config_to_file(boolean overwrite_config_file)
	{
		logger.info("[PLUGIN] Initializing save_config_to_file");

		if (!check_if_config_file_exists() || overwrite_config_file)
		{
			logger.info("[PLUGIN] Saving config to file");
			File appConfig = new File(config_path);
			Properties prop = new Properties();
			try (Writer inputStream = new FileWriter(appConfig)) {

				prop.setProperty("allow_longer_strings", String.valueOf(allow_longer_strings));
				prop.setProperty("enable_sound", String.valueOf(enable_sound));
				prop.setProperty("sound_name", sound_name);
				prop.setProperty("enable_global_character_limit", String.valueOf(enable_global_character_limit));
				prop.setProperty("global_character_limit", String.valueOf(global_character_limit));

				prop.store(inputStream, CharacterLimiterPlugin.getLocalizedString("PROPERTIES_FILE_TITLE"));

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		else
		{
			logger.info("[PLUGIN] Config WON't be saved");
		}

	}
	

}
