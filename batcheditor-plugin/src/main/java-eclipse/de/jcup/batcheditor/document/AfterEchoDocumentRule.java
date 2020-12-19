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
package de.jcup.batcheditor.document;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

import de.jcup.batcheditor.script.EchoParserSupport;

public class AfterEchoDocumentRule implements IPredicateRule{

	private IToken token;
	private EchoParserSupport parserSupport;
	boolean trace;
	private static final String ECHO="echo";

	public AfterEchoDocumentRule(IToken token) {
		this.token=token;
		parserSupport=new EchoParserSupport();
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner) {
		return evaluate(scanner, false);
	}

	@Override
	public IToken getSuccessToken() {
		return token;
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner1, boolean resume) {
		if (!(scanner1 instanceof BatchDocumentPartitionScanner)){
			return Token.UNDEFINED;
		}
		BatchDocumentPartitionScanner scanner = (BatchDocumentPartitionScanner)scanner1;
		if (scanner.getOffset()<ECHO.length()){
			/* would be a problem on start on going back...*/
			return Token.UNDEFINED;
		}
		scanner.unread(); // go to character before
		int charBeforeAsInt = scanner.read(); // get character, moves to origin pos again
		/* fast guard closing:*/
		if (charBeforeAsInt!='o' && charBeforeAsInt!='O'){ // echo ECHO <- last character must be an o other wise guard close...
			return Token.UNDEFINED;
		}
		ICharacterScannerCodePosSupport codePosSupport = new ICharacterScannerCodePosSupport(scanner);
		if (parserSupport.isAfterEchoHandled(codePosSupport)){
			return getSuccessToken();
		}
		codePosSupport.resetToStartPos();
		return Token.UNDEFINED;
	}
	

}
