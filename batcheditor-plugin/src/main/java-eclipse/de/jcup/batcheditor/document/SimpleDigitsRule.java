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
 * A special rule to scan for digits
 * 
 * @author Albert Tregnaghi
 *
 */
public class SimpleDigitsRule implements IPredicateRule {

    private IToken token;

    public SimpleDigitsRule(IToken token) {
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
        if (!Character.isDigit(start)) {
            scanner.unread();
            return Token.UNDEFINED;
        }
        /* we must ensure we got not something like A1234 and scanner has read A before.. When column = 1, this means we have alread read first char and we do not need to check*/
        if (scanner.getColumn()>1) {
            scanner.unread(); // at first char again
            scanner.unread(); // af char before
            int before = scanner.read();
            char bc = (char) before;
            if (! isAllowedCharacterBefore(bc)) {
                /* not a digit so break */
                return Token.UNDEFINED;
            }
            scanner.read();//again read first char
        }
        
        int readAmount = 1; // former read is counted too!
        /* okay it could be a number , so read until end reached */
        do {
            int read = scanner.read(); // use int for EOF detection, char makes
                                       // problems here!
            readAmount++;
            char c = (char) read;
            if (Character.isWhitespace(c)) {
                /* special variant for terminating % */
                break;
            }
            if (ICharacterScanner.EOF == read) {
                scanner.unread();
                break;
            }
            if (!Character.isDigit(c)) {
                /* so something like 123A - so do not accept */
                for (int i = 0; i < readAmount; i++) {
                    scanner.unread();
                }
                return Token.UNDEFINED;
            }
        } while (true);
        return  getSuccessToken();

    }

    private boolean isAllowedCharacterBefore(char bc) {
        return Character.isWhitespace(bc) || Character.isDigit(bc) || bc=='=';
    }

}
