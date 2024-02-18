package org.omegat.plugins.characterlimiter;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.omegat.core.Core;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PluginDocumentListener implements DocumentListener {

	private CharacterLimiterPlugin character_limiter;
	private static final Logger logger = LoggerFactory.getLogger(PluginDocumentListener.class);

	public PluginDocumentListener(CharacterLimiterPlugin character_limiter) {
		this.character_limiter = character_limiter;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {

    }

	@Override
	public void removeUpdate(DocumentEvent e) {

	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		logger.info("[PLUGIN] Initializing changedUpdate");
		if (Core.getEditor().getCurrentTranslation() != null)
		{
			if (character_limiter.getMenu().is_limiter_enabled())
			{
				character_limiter.execute_limit_characters_logic();
			}
		}
	}
}