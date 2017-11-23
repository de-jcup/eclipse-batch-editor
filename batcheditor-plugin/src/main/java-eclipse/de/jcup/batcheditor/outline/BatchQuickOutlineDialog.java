/*
 * Copyright 2016 Albert Tregnaghi
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
package de.jcup.batcheditor.outline;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import de.jcup.batcheditor.AbstractFilterableTreeQuickDialog;
import de.jcup.batcheditor.AbstractTreeViewerFilter;
import de.jcup.batcheditor.BatchEditor;
import de.jcup.batcheditor.BatchEditorActivator;
import de.jcup.batcheditor.FilterPatternMatcher;
import de.jcup.batcheditor.outline.Item;
import de.jcup.batcheditor.outline.ItemTextMatcher;

/**
 * This dialog is inspired by: <a href=
 * "https://github.com/eclipse/eclipse.jdt.ui/blob/master/org.eclipse.jdt.ui/ui/org/eclipse/jdt/internal/ui/text/AbstractInformationControl.java">org.eclipse.jdt.internal.ui.text.AbstractInformationControl</a>
 * and <a href=
 * "https://github.com/eclipse/eclipse.jdt.ui/blob/master/org.eclipse.jdt.ui/ui/org/eclipse/jdt/internal/ui/text/JavaOutlineInformationControl.java">org.eclipse.jdt.internal.ui.text.JavaOutlineInformationControl</a>
 * 
 * @author Albert Tregnaghi
 *
 */
public class BatchQuickOutlineDialog extends AbstractFilterableTreeQuickDialog<Item> {

	private static final int MIN_WIDTH = 400;
	private static final int MIN_HEIGHT = 300;

	private BatchEditor editor;

	/**
	 * Creates a quick outline dialog
	 * 
	 * @param adaptable
	 *            an adapter which should be able to provide a tree content
	 *            provider and gradle editor. If gradle editor is not set a
	 *            selected item will only close the dialog but do not select
	 *            editor parts..
	 * @param parent
	 *            shell to use is null the outline will have no content! If the
	 *            gradle editor is null location setting etc. will not work.
	 * @param infoText
	 *            information to show at bottom of dialog
	 */
	public BatchQuickOutlineDialog(IAdaptable adaptable, Shell parent, String infoText) {
		super(adaptable, parent, "Batch quick outline", MIN_WIDTH, MIN_HEIGHT, infoText);
		this.editor = adaptable.getAdapter(BatchEditor.class);
	}

	@Override
	protected ITreeContentProvider createTreeContentProvider(IAdaptable adaptable) {
		return adaptable.getAdapter(ITreeContentProvider.class);
	}

	@Override
	protected void openSelectionImpl(ISelection selection, String filterText) {
		if (editor == null) {
			return;
		}
		BatchEditorContentOutlinePage outlinePage = editor.getOutlinePage();
		boolean outlineAvailable = outlinePageVisible(outlinePage);
		if (outlineAvailable){
			/*
			 * select part in editor - grab focus not necessary, because this will
			 * close quick outline dialog as well, so editor will get focus back
			 */
			editor.openSelectedTreeItemInEditor(selection, false);
		}else{
			outlinePage.setSelection(selection);
		}
		
	}

	protected boolean outlinePageVisible(BatchEditorContentOutlinePage outlinePage) {
		Control control = outlinePage.getControl();
		/* when control is not available - means outline view is not visible, */
		boolean controlAvailable = control==null || control.isDisposed() || ! control.isVisible();
		return controlAvailable;
	}

	@Override
	protected AbstractUIPlugin getUIPlugin() {
		BatchEditorActivator editorActivator = BatchEditorActivator.getDefault();
		return editorActivator;
	}

	@Override
	protected Item getInitialSelectedItem() {
		if (editor == null) {
			return null;
		}
		Item item = editor.getItemAtCarretPosition();
		return item;
	}

	@Override
	protected FilterPatternMatcher<Item> createItemMatcher() {
		return new ItemTextMatcher();
	}

	@Override
	protected IBaseLabelProvider createLabelProvider() {
		BatchEditorOutlineLabelProvider labelProvider = new BatchEditorOutlineLabelProvider();
		return new DelegatingStyledCellLabelProvider(labelProvider);
	}

	@Override
	protected AbstractTreeViewerFilter<Item> createFilter() {
		return new ItemTextViewerFilter();
	}

}
