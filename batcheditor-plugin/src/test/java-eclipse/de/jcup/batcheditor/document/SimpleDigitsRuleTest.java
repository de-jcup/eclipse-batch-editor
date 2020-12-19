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

import static org.junit.Assert.*;

import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;
import org.junit.Before;
import org.junit.Test;

/**
 * Sorrowly not executable by gradle because of eclipse dependencies. But
 * at least executable in eclipse environment. Tests exact word pattern rule works
 * @author Albert Tregnaghi
 *
 */
public class SimpleDigitsRuleTest {
	
	private IToken token;
	private OneLineSimpleTestCharacterScanner scanner;

	@Before
	public void before(){
		token = new Token("mocked");
	}

	@Test
	public void number_1234_is_found() {
		/* prepare */
		scanner = new OneLineSimpleTestCharacterScanner("1234");
		SimpleDigitsRule rule = new SimpleDigitsRule(token);
		
		/* execute */
		IToken tokenResult = rule.evaluate(scanner);
		
		/* test */
		assertEquals(3,scanner.column);
		assertEquals(token,tokenResult);
		
	}
	
	@Test
    public void number_1234_newline_is_found() {
        /* prepare */
        scanner = new OneLineSimpleTestCharacterScanner("1234\n  ");
        SimpleDigitsRule rule = new SimpleDigitsRule(token);
        
        /* execute */
        IToken tokenResult = rule.evaluate(scanner);
        
        /* test */
        assertEquals(5,scanner.column);
        assertEquals(token,tokenResult);
        
    }
	
	@Test
    public void number_1234A_is_not_found() {
        /* prepare */
        scanner = new OneLineSimpleTestCharacterScanner("A1234");
        SimpleDigitsRule rule = new SimpleDigitsRule(token);
        
        /* execute */
        IToken tokenResult = rule.evaluate(scanner);
        
        /* test */
        assertEquals(0,scanner.column);
        assertEquals(Token.UNDEFINED,tokenResult);
        
    }
	
	@Test
    public void number_A1234_is_not_found() {
        /* prepare */
        scanner = new OneLineSimpleTestCharacterScanner("A1234");
        SimpleDigitsRule rule = new SimpleDigitsRule(token);
        
        /* execute */
        IToken tokenResult = rule.evaluate(scanner);
        
        /* test */
        assertEquals(0,scanner.column);
        assertEquals(Token.UNDEFINED,tokenResult);
        
    }
	
	@Test
    public void number_A1234_is_not_found_when_scanner_on_pos1() {
        /* prepare */
        scanner = new OneLineSimpleTestCharacterScanner("A1234");
        SimpleDigitsRule rule = new SimpleDigitsRule(token);
        scanner.read();
        
        int columnBeforeTest = 1;
        assertEquals(columnBeforeTest,scanner.column);
        
        /* execute */
        IToken tokenResult = rule.evaluate(scanner);
        
        /* test */
        assertEquals(columnBeforeTest,scanner.column);
        assertEquals(Token.UNDEFINED,tokenResult);
        
    }
	

}
