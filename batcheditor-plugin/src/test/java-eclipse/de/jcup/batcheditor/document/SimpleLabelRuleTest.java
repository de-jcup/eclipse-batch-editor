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
public class SimpleLabelRuleTest {
	
	private IToken token;
	private OneLineSimpleTestCharacterScanner scanner;

	@Before
	public void before(){
		token = new Token("mocked");
	}

	@Test
	public void colon_1234_is_found() {
		/* prepare */
		scanner = new OneLineSimpleTestCharacterScanner(":1234");
		SimpleLabelRule rule = new SimpleLabelRule(token);
		
		/* execute */
		IToken tokenResult = rule.evaluate(scanner);
		
		/* test */
		assertEquals(4,scanner.column);
		assertEquals(token,tokenResult);
		
	}
	
	@Test
    public void colon_the_label_is_found() {
        /* prepare */
        scanner = new OneLineSimpleTestCharacterScanner(":the label");
        SimpleLabelRule rule = new SimpleLabelRule(token);
        
        /* execute */
        IToken tokenResult = rule.evaluate(scanner);
        
        /* test */
        assertEquals(9,scanner.column);
        assertEquals(token,tokenResult);
        
    }
	
	@Test
    public void something_line_before_new_line_colon_the_label_is_found() {
        /* prepare */
        scanner = new OneLineSimpleTestCharacterScanner("a\n:the label");
        scanner.read();
        scanner.read();
        SimpleLabelRule rule = new SimpleLabelRule(token);
        
        /* execute */
        IToken tokenResult = rule.evaluate(scanner);
        
        /* test */
        assertEquals(11,scanner.column);
        assertEquals(token,tokenResult);
        
    }
	
	
	@Test
    public void something_line_before_new_line_space_colon_the_label_is_NOT_found() {
        /* prepare */
        scanner = new OneLineSimpleTestCharacterScanner("a\n :the label");
        scanner.read();
        scanner.read();
        scanner.read();
        SimpleLabelRule rule = new SimpleLabelRule(token);
        int columbBefore = scanner.column;
        
        /* execute */
        IToken tokenResult = rule.evaluate(scanner);
        
        /* test */
        assertEquals(columbBefore,scanner.column);
        assertEquals(Token.UNDEFINED,tokenResult);
        
    }
	
	@Test
    public void something_same_line_colon_the_label_is_found() {
        /* prepare */
        scanner = new OneLineSimpleTestCharacterScanner("a:the label");
        SimpleLabelRule rule = new SimpleLabelRule(token);
        
        /* execute */
        IToken tokenResult = rule.evaluate(scanner);
        
        /* test */
        assertEquals(0,scanner.column);
        assertEquals(Token.UNDEFINED,tokenResult);
        
    }
	
	@Test
    public void colon_the_label__newline_is_found() {
        /* prepare */
        scanner = new OneLineSimpleTestCharacterScanner(":the label\nxyz");
        SimpleLabelRule rule = new SimpleLabelRule(token);
        
        /* execute */
        IToken tokenResult = rule.evaluate(scanner);
        
        /* test */
        assertEquals(10,scanner.column);
        assertEquals(token,tokenResult);
        
    }
    
	

}
