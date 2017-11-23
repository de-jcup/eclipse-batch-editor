package de.jcup.batcheditor.script;

public class BatchLabel {

	private String name;
	int pos;
	int end;

	public BatchLabel(String name){
		this.name=name;
	}
	
	public String getName() {
		return name;
	}

	public int getPosition() {
		return pos;
	}

	public int getLengthToNameEnd() {
		return name.length();
	}

	public int getEnd() {
		return end;
	}

}
