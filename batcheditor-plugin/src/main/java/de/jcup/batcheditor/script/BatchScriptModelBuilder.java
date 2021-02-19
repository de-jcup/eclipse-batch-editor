/*
 * Copyright 2020 Albert Tregnaghi
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
package de.jcup.batcheditor.script;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BatchScriptModelBuilder {

    private class BatchscriptContext {
        int pos = 0;
        int labelStart = 0;
        int posAtLine = 0;
    }

    public BatchScriptModel build(String text) {
        BatchScriptModel model = new BatchScriptModel();
        if (text == null || text.trim().length() == 0) {
            return model;
        }
        String inspect = text + "\n";// a simple workaround to get the last
                                     // label accessed too, if there is no
                                     // next line...
        /*
         * very simple approach: a label is identified by being at first position of
         * line
         */
        collectLabels(model, inspect);
        collectVariables(model, inspect);
        return model;
    }

    private static Pattern SET_PATTERN = Pattern.compile("set .*=", Pattern.CASE_INSENSITIVE);

    private void collectVariables(BatchScriptModel model, String inspect) {
        Matcher matcher = SET_PATTERN.matcher(inspect);

        // use a set - we show only the first candidates in model
        Set<String> matches = new LinkedHashSet<>();
        while (matcher.find()) {
            String match = matcher.group(0);
            int indexForFirstEqual = match.indexOf('=');
            match = match.substring(0, indexForFirstEqual);
            String subString = match.substring(3).trim();
            if (matches.contains(subString)) {
                continue;
            }
            matches.add(subString);
            
            String[] splitted = subString.split(" ");
            String variableName = splitted[splitted.length - 1];// use last one
            BatchVariable variable = new BatchVariable(variableName);
            variable.pos = inspect.indexOf(match);
            variable.end = variable.pos + match.length();

            model.getVariables().add(variable);
        }

    }

    private void collectLabels(BatchScriptModel model, String inspect) {
        BatchscriptContext context = new BatchscriptContext();
        StringBuilder labelSb = null;
        for (char c : inspect.toCharArray()) {
            if (c == '\n' || c == '\r') {
                /* terminate search - got the label or none */
                addLabelDataWhenExisting(model, labelSb, context);
                labelSb = null;
                continue;
            }
            if (c == ':') {
                if (context.posAtLine == 0) {
                    labelSb = new StringBuilder();
                    context.labelStart = context.pos;
                } else {
                    /* :: detected - reset */
                    labelSb = null;
                }
            } else {
                if (labelSb != null) {
                    if (Character.isWhitespace(c)) {
                        addLabelDataWhenExisting(model, labelSb, context);
                        labelSb = null;
                        continue;
                    } else {
                        labelSb.append(c);
                    }
                }
            }
            context.pos++;
            context.posAtLine++;
        }
    }

    protected void addLabelDataWhenExisting(BatchScriptModel model, StringBuilder labelSb, BatchscriptContext context) {
        if (labelSb != null) {
            String labelName = labelSb.toString().trim();
            if (!labelName.isEmpty()) {

                BatchLabel label = new BatchLabel(labelName);
                label.pos = context.labelStart + 1;
                label.end = context.pos - 1;

                model.getLabels().add(label);
            }
        }
        context.pos++;
        context.posAtLine = 0;
    }

}
