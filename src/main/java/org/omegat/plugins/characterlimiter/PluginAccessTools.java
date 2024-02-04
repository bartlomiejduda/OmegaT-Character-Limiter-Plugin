package org.omegat.plugins.characterlimiter;

import java.lang.reflect.Field;


import org.omegat.core.Core;

import org.omegat.gui.editor.EditorController;
import org.omegat.gui.editor.EditorTextArea3;



public class PluginAccessTools {


	private static EditorTextArea3 editor_text_area = null;


	public static EditorTextArea3 getEditorTextArea() {
		if (editor_text_area == null) {
			EditorController controller = (EditorController) Core.getEditor();

			Field editor;
			try {
				editor = EditorController.class.getDeclaredField("editor");

				editor.setAccessible(true);
				try {
					editor_text_area = (EditorTextArea3) editor.get(controller);
				} catch (IllegalAccessException error) {
					error.printStackTrace(System.err);
					System.exit(-1);
				}
			} catch (NoSuchFieldException error) {
				error.printStackTrace(System.err);
				System.exit(-1);
			}
		}

		return editor_text_area;
	}


}