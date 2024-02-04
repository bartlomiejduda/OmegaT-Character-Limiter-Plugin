package org.omegat.plugins.characterlimiter;

import org.omegat.core.Core;
import org.omegat.core.CoreEvents;
import org.omegat.core.data.NotLoadedProject;
import org.omegat.core.events.IApplicationEventListener;

public class PluginApplicationEventListener implements IApplicationEventListener {

	CharacterLimiterPlugin character_limiter;

	public PluginApplicationEventListener(CharacterLimiterPlugin character_limiter) {
		this.character_limiter = character_limiter;
	}

	@Override
	public void onApplicationStartup() {
		PluginActionListener listener = new PluginActionListener(character_limiter);
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
