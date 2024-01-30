package org.omegat.plugins.characterlimiter;

import org.omegat.core.Core;
import org.omegat.core.CoreEvents;
import org.omegat.core.data.NotLoadedProject;
import org.omegat.core.events.IApplicationEventListener;

public class ApplicationEventListenerSessionLog implements IApplicationEventListener {

	CharacterLimiterPlugin character_limiter;

	public ApplicationEventListenerSessionLog(CharacterLimiterPlugin character_limiter) {
		this.character_limiter = character_limiter;
	}

	@Override
	public void onApplicationStartup() {
		ActionListener listener = new ActionListener(character_limiter);
		CoreEvents.registerProjectChangeListener(listener);
		CoreEvents.registerEntryEventListener(listener);
		CoreEvents.registerEditorEventListener(listener);
		CoreEvents.registerFontChangedEventListener(listener);
	}

	@Override
	public void onApplicationShutdown() {
		if (!(Core.getProject() instanceof NotLoadedProject) && character_limiter.getMenu().is_limiter_enabled())
			character_limiter.close();
	}
}
