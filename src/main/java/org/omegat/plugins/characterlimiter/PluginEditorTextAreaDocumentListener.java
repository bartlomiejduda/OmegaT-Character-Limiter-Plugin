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


	String get_dockable_text(int source_length, int translation_length)
	{
		int percent = (translation_length * 100) / source_length;
		String percent_color = "GREEN";

		if (percent >= 50 && percent < 80)
		{
			percent_color = "ORANGE";
		}
		else if (percent >= 80)
		{
			percent_color = "RED";
		}

		StringBuilder dockable_text = new StringBuilder();

		dockable_text.append("<html>");
		dockable_text.append("Source text length: ").append(source_length);
		dockable_text.append("<br>");
		dockable_text.append("Translation length: ");
		dockable_text.append(String.format("<FONT COLOR=%s>", percent_color));
		dockable_text.append(translation_length);
		dockable_text.append(" (" + percent + "%)");
		dockable_text.append("</FONT>");
		dockable_text.append("</html>");


		return dockable_text.toString();
	}
	@Override
	public void changedUpdate(DocumentEvent e) {
		logger.info("[PLUGIN] Initializing changedUpdate");
		if (Core.getEditor().getCurrentTranslation() != null)
		{
			String source_text = "01234567891234567";
			String translation_text = Core.getEditor().getCurrentTranslation();
			String dockable_text = get_dockable_text(source_text.length(), translation_text.length());

			character_limiter.set_plugin_dockable_text(dockable_text);


		}
	}

}