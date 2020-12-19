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

/**
 * Constant definitions for plug-in preferences
 */
public enum BatchEditorSyntaxColorPreferenceConstants implements PreferenceIdentifiable, PreferenceLabeled{
	COLOR_NORMAL_TEXT("colorNormalText","Normal text color"),
	COLOR_BATCH_INTERNAL_KEYWORD("colorBatchKeywords", "Batch keywords"),
	COLOR_GSTRING("colorDoubleStrings", "Double quoted strings"),
	COLOR_COMMENT("colorComments", "Comment"),
	COLOR_EXTERNAL_COMMAND("colorCommands","External commands"),
	COLOR_KNOWN_VARIABLES("colorKnownVariables","Variables"),
	COLOR_PARAMETERS("colorParameters","Parameters"),
	COLOR_ECHO_OUTPUT("colorEchoOutput","Echo output"),
	COLOR_NUMBERS("colorNumbers","Numbers"),
	COLOR_LABELS("colorLabels","Labels"),
	
	;

	private String id;
	private String labelText;

	private BatchEditorSyntaxColorPreferenceConstants(String id, String labelText) {
		this.id = id;
		this.labelText=labelText;
	}

	public String getLabelText() {
		return labelText;
	}
	
	public String getId() {
		return id;
	}

}
