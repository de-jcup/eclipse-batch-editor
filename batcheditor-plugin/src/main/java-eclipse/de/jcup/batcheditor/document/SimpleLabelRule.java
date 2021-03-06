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
package de.jcup.batcheditor.document;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

/**
 * A special rule to scan for labels
 * 
 * @author Albert Tregnaghi
 *
 */
public class SimpleLabelRule implements IPredicateRule {

    private IToken token;

    public SimpleLabelRule(IToken token) {
        this.token = token;
    }

    @Override
    public IToken getSuccessToken() {
        return token;
    }

    @Override
    public IToken evaluate(ICharacterScanner scanner) {
        return evaluate(scanner, false);
    }

    @Override
    public IToken evaluate(ICharacterScanner scanner, boolean resume) {
        char start = (char) scanner.read();
        if (start!=':') {
            scanner.unread();
            return Token.UNDEFINED;
        }
        /* we must ensure we got not something like a:mylabel and scanner has read a before.. When column = 1, this means we have alread read first char and we do not need to check*/
        if (scanner.getColumn()>1) {
            scanner.unread(); // at first char again
            scanner.unread(); // af char before
            int before = scanner.read();
            if (!isNewLine(before)) {
                /* not new line so skip */
                return Token.UNDEFINED;
            }
            scanner.read();//again read first char
        }
        
        /* okay it could be a number , so read until end reached */
        do {
            int read = scanner.read(); // use int for EOF detection, char makes
                                       // problems here!
            if (ICharacterScanner.EOF == read) {
                scanner.unread();
                break;
            }
            if (isNewLine(read)) {
                scanner.unread();
                break;
            }
        } while (true);
        return  getSuccessToken();

    }
    
    private boolean isNewLine(int value) {
        return value ==10 || value==13;
    }    

}
