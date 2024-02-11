package org.omegat.plugins.characterlimiter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.omegat.core.Core;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PluginEditorTextAreaDocumentListener implements DocumentListener {

	private CharacterLimiterPlugin character_limiter;
	private static final Logger logger = LoggerFactory.getLogger(PluginEditorTextAreaDocumentListener.class);

	public PluginEditorTextAreaDocumentListener(CharacterLimiterPlugin character_limiter) {
		this.character_limiter = character_limiter;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {

		logger.info("[PLUGIN] Initializing insertUpdate");
    }

	@Override
	public void removeUpdate(DocumentEvent e) {
		logger.info("[PLUGIN] Initializing removeUpdate");
	}



	@Override
	public void changedUpdate(DocumentEvent e) {
		logger.info("[PLUGIN] Initializing changedUpdate");
		if (Core.getEditor().getCurrentTranslation() != null)
		{
			character_limiter.execute_limit_characters_logic();
		}
	}

}