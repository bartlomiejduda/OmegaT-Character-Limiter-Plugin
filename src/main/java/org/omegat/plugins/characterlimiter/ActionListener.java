package org.omegat.plugins.characterlimiter;

import java.awt.Font;

import org.omegat.core.data.SourceTextEntry;
import org.omegat.core.events.IEditorEventListener;
import org.omegat.core.events.IEntryEventListener;
import org.omegat.core.events.IFontChangedEventListener;
import org.omegat.core.events.IProjectEventListener;

public class ActionListener
		implements IEditorEventListener, IEntryEventListener, IFontChangedEventListener, IProjectEventListener {

	private CharacterLimiterPlugin character_limiter;

	public ActionListener(CharacterLimiterPlugin character_limiter) {
		this.character_limiter = character_limiter;
	}

	@Override
	public void onNewWord(String newWord) {

	}

	@Override
	public void onFontChanged(Font newFont) {

	}

	@Override
	public void onNewFile(String activeFileName) {

	}

	@Override
	public void onEntryActivated(SourceTextEntry newEntry) {

	}



	@Override
	public void onProjectChanged(PROJECT_CHANGE_TYPE eventType) {
		if (eventType == PROJECT_CHANGE_TYPE.CLOSE) {
			character_limiter.close();
		} else if (eventType == PROJECT_CHANGE_TYPE.LOAD || eventType == PROJECT_CHANGE_TYPE.CREATE) {
			character_limiter.start();
		}
	}
}