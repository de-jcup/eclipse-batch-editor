package de.jcup.batcheditor.script;

import java.util.ArrayList;
import java.util.List;

public class BatchScriptModel {

	private List<BatchLabel> labels;
	
	public BatchScriptModel() {
		labels=new ArrayList<>();
	}
	
	public List<BatchLabel> getLabels() {
		return labels;
	}

	public boolean hasErrors() {
		return false;
	}

}
