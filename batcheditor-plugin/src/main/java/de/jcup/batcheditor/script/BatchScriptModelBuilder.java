package de.jcup.batcheditor.script;

public class BatchScriptModelBuilder {

	public BatchScriptModel build(String text) {
		BatchScriptModel model = new BatchScriptModel();
		if (text == null || text.trim().length() == 0) {
			return model;
		}
		String inspect = text + "\n";// a simple workaround to get the last
										// label accessed too, if there is no
										// next line...
		/*
		 * very simple approach: a label is identified by being at first
		 * position of line
		 */
		int pos = 0;
		int labelStart = 0;
		int posAtLine = 0;
		StringBuilder labelSb = null;
		for (char c : inspect.toCharArray()) {
			if (c == '\n' || c == '\r') {
				if (labelSb != null) {
					BatchLabel label = new BatchLabel(labelSb.toString());
					label.pos = labelStart + 1;
					label.end = pos - 1;
					model.getLabels().add(label);
				}
				pos++;
				posAtLine = 0;
				labelSb = null;
				continue;
			}
			if (c == ':') {
				if (posAtLine==0){
					labelSb = new StringBuilder();
					labelStart = pos;
				}else{
					/* :: detected - reset */
					labelSb=null;
				}
			} else {
				if (labelSb != null) {
					if (Character.isWhitespace(c)) {
						labelSb = null;
					} else {
						labelSb.append(c);
					}
				}
			}
			pos++;
			posAtLine++;
		}

		return model;
	}

}
