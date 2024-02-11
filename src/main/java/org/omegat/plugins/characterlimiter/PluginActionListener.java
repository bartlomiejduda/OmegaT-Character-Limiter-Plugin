package org.omegat.plugins.characterlimiter;

import java.awt.Font;

import org.omegat.core.data.SourceTextEntry;
import org.omegat.core.events.IEditorEventListener;
import org.omegat.core.events.IEntryEventListener;
import org.omegat.core.events.IFontChangedEventListener;
import org.omegat.core.events.IProjectEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PluginActionListener
		implements IEditorEventListener, IEntryEventListener, IFontChangedEventListener, IProjectEventListener {

	private CharacterLimiterPlugin character_limiter;
	private PluginCaretUpdateListener caret_listener;
	private PluginEditorTextAreaDocumentListener text_area_listener;

	private static final Logger logger = LoggerFactory.getLogger(CharacterLimiterMenu.class);

	public PluginActionListener(CharacterLimiterPlugin character_limiter) {
		logger.info("[PLUGIN] Initializing ActionListener");
		this.character_limiter = character_limiter;

		this.caret_listener = null;
		this.text_area_listener = null;


		if (caret_listener == null) {
			logger.info("[PLUGIN] Adding NEW caret_listener");
			caret_listener = new PluginCaretUpdateListener(character_limiter);
			PluginAccessTools.getEditorTextArea().addCaretListener(caret_listener);
		}

	}

	@Override
	public void onNewWord(String newWord) {

	}

	@Override
	public void onFontChanged(Font newFont) {

	}

	@Override
	public void onNewFile(String activeFileName) {
		logger.info("[PLUGIN] Initializing onNewFile");
	}


	void add_text_area_listener()
	{
		if (text_area_listener == null) {
			logger.info("[PLUGIN] Adding NEW text_area_listener");
			text_area_listener = new PluginEditorTextAreaDocumentListener(character_limiter);
			PluginAccessTools.getEditorTextArea().getOmDocument().addDocumentListener(text_area_listener);
		}
	}

	@Override
	public void onEntryActivated(SourceTextEntry newEntry) {
		logger.info("[PLUGIN] Initializing onEntryActivated");
		logger.info("[PLUGIN] new entry source text = " + newEntry.getSrcText());
		int source_text_length = newEntry.getSrcText().length();
		logger.info("[PLUGIN] new entry source text length  = " + source_text_length);

		add_text_area_listener();
	}



	@Override
	public void onProjectChanged(PROJECT_CHANGE_TYPE eventType) {
		logger.info("[PLUGIN] Initializing onProjectChanged");
		if (eventType == PROJECT_CHANGE_TYPE.CLOSE) {
			character_limiter.close();
			text_area_listener = null;
		} else if (eventType == PROJECT_CHANGE_TYPE.LOAD || eventType == PROJECT_CHANGE_TYPE.CREATE) {
			character_limiter.start();
		}
	}
}