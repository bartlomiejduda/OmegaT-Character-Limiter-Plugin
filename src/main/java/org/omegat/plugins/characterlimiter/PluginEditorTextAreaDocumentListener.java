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

		if (percent >= 60 && percent <= 100)
		{
			percent_color = "ORANGE";
		}
		else if (percent > 100)
		{
			percent_color = "RED";
		}

		StringBuilder dockable_text = new StringBuilder();

		dockable_text.append("<html>");
		dockable_text.append("Source text length: ").append(source_length);
		dockable_text.append("<br>");
		dockable_text.append("Translation text length: ");
		dockable_text.append(String.format("<FONT COLOR=%s>", percent_color));
		dockable_text.append(translation_length);
		dockable_text.append(" (" + percent + "%)");
		dockable_text.append("</FONT>");
		dockable_text.append("</html>");


		return dockable_text.toString();
	}

	private void limit_text_on_update(String translation_text, int character_limit) {
		Runnable do_text_limiting = new Runnable() {
			@Override
			public void run() {
				String text_to_replace = translation_text.substring(0, character_limit);
				Core.getEditor().replaceEditText(text_to_replace);
			}
		};
		SwingUtilities.invokeLater(do_text_limiting);
	}
	@Override
	public void changedUpdate(DocumentEvent e) {
		logger.info("[PLUGIN] Initializing changedUpdate");
		if (Core.getEditor().getCurrentTranslation() != null)
		{
			String source_text = Core.getEditor().getCurrentEntry().getSrcText();
			String translation_text = Core.getEditor().getCurrentTranslation();
			int character_limit = source_text.length();  // TODO - logic for global limit
			String dockable_text = get_dockable_text(source_text.length(), translation_text.length());
			character_limiter.set_plugin_dockable_text(dockable_text);

			if (translation_text.length() > character_limit)
			{
				logger.info("[PLUGIN] Limiting text in segment to " + character_limit + " characters");
				limit_text_on_update(translation_text, character_limit);
			}
		}
	}

}