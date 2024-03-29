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
	private PluginDocumentListener text_area_listener;

	private static final Logger logger = LoggerFactory.getLogger(PluginActionListener.class);

	public PluginActionListener(CharacterLimiterPlugin character_limiter) {
		logger.info("[PLUGIN] Initializing ActionListener");
		this.character_limiter = character_limiter;
		this.text_area_listener = null;
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
			text_area_listener = new PluginDocumentListener(character_limiter);
			PluginAccessTools.getEditorTextArea().getOmDocument().addDocumentListener(text_area_listener);
		}
	}

	@Override
	public void onEntryActivated(SourceTextEntry newEntry) {
		logger.info("[PLUGIN] Initializing onEntryActivated");
		add_text_area_listener();
	}



	@Override
	public void onProjectChanged(PROJECT_CHANGE_TYPE eventType) {
		logger.info("[PLUGIN] Initializing onProjectChanged");
		logger.info("[PLUGIN] CHANGE TYPE: " + eventType);

		if (eventType == PROJECT_CHANGE_TYPE.CLOSE)
		{
			character_limiter.close();
			if (text_area_listener != null)
			{
				PluginAccessTools.getEditorTextArea().getOmDocument().removeDocumentListener(text_area_listener);
				text_area_listener = null;
			}
		}
		else if (eventType == PROJECT_CHANGE_TYPE.LOAD || eventType == PROJECT_CHANGE_TYPE.CREATE)
		{
			character_limiter.start();
		}
		else if (eventType == PROJECT_CHANGE_TYPE.MODIFIED)
		{
			if (text_area_listener != null)
			{
				PluginAccessTools.getEditorTextArea().getOmDocument().removeDocumentListener(text_area_listener);
				text_area_listener = null;
			}

		}
	}
}