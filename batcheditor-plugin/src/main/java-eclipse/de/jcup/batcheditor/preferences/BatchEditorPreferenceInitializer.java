package de.jcup.batcheditor.preferences;
/*
 * Copyright 2017 Albert Tregnaghi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 */
 

import static de.jcup.batcheditor.BatchEditorColorConstants.*;
import static de.jcup.batcheditor.BatchEditorUtil.*;
import static de.jcup.batcheditor.preferences.BatchEditorPreferenceConstants.*;
import static de.jcup.batcheditor.preferences.BatchEditorSyntaxColorPreferenceConstants.*;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Class used to initialize default preference values.
 */
public class BatchEditorPreferenceInitializer extends AbstractPreferenceInitializer {

	public void initializeDefaultPreferences() {
		BatchEditorPreferences preferences = getPreferences();
		IPreferenceStore store = preferences.getPreferenceStore();
		
		/* Outline */
		store.setDefault(P_LINK_OUTLINE_WITH_EDITOR.getId(), true);
		
		/* ++++++++++++ */
		/* + Brackets + */
		/* ++++++++++++ */
		/* bracket rendering configuration */
		store.setDefault(P_EDITOR_MATCHING_BRACKETS_ENABLED.getId(), true); // per default matching is enabled, but without the two other special parts
		store.setDefault(P_EDITOR_HIGHLIGHT_BRACKET_AT_CARET_LOCATION.getId(), false);
		store.setDefault(P_EDITOR_ENCLOSING_BRACKETS.getId(), false);
		store.setDefault(P_EDITOR_AUTO_CREATE_END_BRACKETSY.getId(), true);
		
		/* bracket color */
		preferences.setDefaultColor(P_EDITOR_MATCHING_BRACKETS_COLOR, GRAY_JAVA);
		
		/* +++++++++++++++++++ */
		/* + Code Assistence + */
		/* +++++++++++++++++++ */
		store.setDefault(P_CODE_ASSIST_ADD_KEYWORDS.getId(), true);
		store.setDefault(P_CODE_ASSIST_ADD_SIMPLEWORDS.getId(), true);
		
		/* +++++++++++++++++ */
		/* + Editor Colors + */
		/* +++++++++++++++++ */
		preferences.setDefaultColor(COLOR_NORMAL_TEXT, BLACK);

		preferences.setDefaultColor(COLOR_BATCH_INTERNAL_KEYWORD, KEYWORD_DEFAULT_PURPLE);
		
		preferences.setDefaultColor(COLOR_GSTRING, ROYALBLUE);
		preferences.setDefaultColor(COLOR_COMMENT, GREEN_JAVA);
		
		preferences.setDefaultColor(COLOR_EXTERNAL_COMMAND, TASK_DEFAULT_RED);
		preferences.setDefaultColor(COLOR_KNOWN_VARIABLES, ORANGE_DARK);
		preferences.setDefaultColor(COLOR_PARAMETERS, DARK_BLUE);
		
		preferences.setDefaultColor(COLOR_ECHO_OUTPUT, MEDIUM_GRAY);
		preferences.setDefaultColor(COLOR_NUMBERS, BLUE);
		preferences.setDefaultColor(COLOR_LABELS, DARK_THEME_NUMBER_BLUE);
		
	}
	
	
	
}
