package org.omegat.plugins.characterlimiter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PluginConfig {

	private static final Logger logger = LoggerFactory.getLogger(PluginConfig.class);
	private String config_value;

	public PluginConfig() {
		config_value = "TEMP";

	}

	void save_config()
	{
		logger.info("[PLUGIN] Initializing save_config");
	}
	

}
