package org.omegat.plugins.characterlimiter;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PluginCaretUpdateListener implements CaretListener {


	private CharacterLimiterPlugin character_limiter;
	private static final Logger logger = LoggerFactory.getLogger(PluginCaretUpdateListener.class);


	public PluginCaretUpdateListener(CharacterLimiterPlugin character_limiter) {
		this.character_limiter = character_limiter;
	}


	@Override
	public void caretUpdate(CaretEvent e) {

	}
}