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
package de.jcup.batcheditor;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;

import de.jcup.batcheditor.preferences.BatchEditorPreferences;

public class BatchEditorUtil {

	private static UnpersistedMarkerHelper scriptProblemMarkerHelper = new UnpersistedMarkerHelper(
			"de.jcup.batcheditor.script.problem");

	public static BatchEditorPreferences getPreferences() {
		return BatchEditorPreferences.getInstance();
	}

	public static void logInfo(String info) {
		getLog().log(new Status(IStatus.INFO, BatchEditorActivator.PLUGIN_ID, info));
	}

	public static void logWarning(String warning) {
		getLog().log(new Status(IStatus.WARNING, BatchEditorActivator.PLUGIN_ID, warning));
	}

	public static void logError(String error, Throwable t) {
		getLog().log(new Status(IStatus.ERROR, BatchEditorActivator.PLUGIN_ID, error, t));
	}

	public static void removeScriptErrors(IEditorPart editor) {
		if (editor == null) {
			return;
		}
		IEditorInput input = editor.getEditorInput();
		if (input == null) {
			return;
		}

	}

	private static ILog getLog() {
		ILog log = BatchEditorActivator.getDefault().getLog();
		return log;
	}

}
