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

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.jcup.batcheditor.TestScriptLoader;

public class BatchScriptModelBuilderTest {

	private BatchScriptModelBuilder builderToTest;

	@Before
	public void before() {
		builderToTest = new BatchScriptModelBuilder();
	}
	/* --------------------------------------------- */
	/* -------------------- Variables -------------- */
	/* --------------------------------------------- */
	@Test
	public void text_empty_has_no_variables() {
	    /* prepare */
	    StringBuilder sb = new StringBuilder();
	    sb.append("");
	    String text = sb.toString();
	    
	    /* execute */
	    BatchScriptModel result = builderToTest.build(text);
	    
	    /* test */
	    assertNotNull(result);
	    assertTrue(result.getVariables().isEmpty());
	    
	}

	@Test
    public void text_set_variable1_equals_1_results_in_variable() {
        /* prepare */
        StringBuilder sb = new StringBuilder();
        sb.append("set variable1=1");
        
        String text = sb.toString();

        /* execute */
        BatchScriptModel result = builderToTest.build(text);

        /* test */
        assertNotNull(result);
        assertEquals(1,result.getVariables().size());
        
        BatchVariable variable = result.getVariables().iterator().next();
        assertEquals("variable1",variable.getName());
        assertEquals(0,variable.getPos());

    }

	@Test
	public void text_set_java_home_with_second_equals_character() {
	    /* prepare */
	    StringBuilder sb = new StringBuilder();
	    sb.append("set JAVA_HOME=%JAVA_HOME:\"=%");
	    
	    String text = sb.toString();
	    
	    /* execute */
	    BatchScriptModel result = builderToTest.build(text);
	    
	    /* test */
	    assertNotNull(result);
	    assertEquals(1,result.getVariables().size());
	    
	    BatchVariable variable = result.getVariables().iterator().next();
	    assertEquals("JAVA_HOME",variable.getName());
	    assertEquals(0,variable.getPos());
	    
	}
	@Test
    public void text_set_lash_p_variable1_equals_1_results_in_variable() {
        /* prepare */
        StringBuilder sb = new StringBuilder();
        sb.append("set /p variable1=1");
        
        String text = sb.toString();

        /* execute */
        BatchScriptModel result = builderToTest.build(text);

        /* test */
        assertNotNull(result);
        assertEquals(1,result.getVariables().size());
        
        BatchVariable variable = result.getVariables().iterator().next();
        assertEquals("variable1",variable.getName());
        assertEquals(0,variable.getPos());

    }
	
	@Test
    public void text_12345677890_newline_set_lash_p_variable1_equals_1_results_in_variable() {
        /* prepare */
        StringBuilder sb = new StringBuilder();
        sb.append("1234567890\nset /p variable1=1");
        
        String text = sb.toString();

        /* execute */
        BatchScriptModel result = builderToTest.build(text);

        /* test */
        assertNotNull(result);
        assertEquals(1,result.getVariables().size());
        
        BatchVariable variable = result.getVariables().iterator().next();
        assertEquals("variable1",variable.getName());
        assertEquals(11,variable.getPos());

    }
    
	
	@Test
    public void text_set_slash_a_variable1_equals_1_results_in_variable() {
        /* prepare */
        StringBuilder sb = new StringBuilder();
        sb.append("set /a variable1=1");
        
        String text = sb.toString();

        /* execute */
        BatchScriptModel result = builderToTest.build(text);

        /* test */
        assertNotNull(result);
        assertEquals(1,result.getVariables().size());
        
        BatchVariable variable = result.getVariables().iterator().next();
        assertEquals("variable1",variable.getName());
        assertEquals(0,variable.getPos());

    }
	
	
	
	/* --------------------------------------------- */
	/* -------------------- Labels ----------------- */
	/* --------------------------------------------- */

	@Test
	public void colon_space__is_not_returned_as_label() {
		/* execute */
		BatchScriptModel result = builderToTest.build(": ");

		/* test */
		assertTrue(result.getLabels().isEmpty());

	}

	@Test
	public void colon_label_space__is_returned_as_label_pos_1_end_5() {
		/* execute */
		BatchScriptModel result = builderToTest.build(":label ");

		/* test */
		assertEquals(1, result.getLabels().size());
		BatchLabel label = result.getLabels().iterator().next();
		assertEquals("label", label.getName());
		assertEquals(1, label.getPosition());
		assertEquals(5, label.getEnd());

	}

	@Test
	public void colon_label_is_returned_as_label_pos_1_end_5() {
		/* execute */
		BatchScriptModel result = builderToTest.build(":label");

		/* test */
		assertEquals(1, result.getLabels().size());
		BatchLabel label = result.getLabels().iterator().next();
		assertEquals("label", label.getName());
		assertEquals(1, label.getPosition());
		assertEquals(5, label.getEnd());

	}

	@Test
	public void bugfix_11_script_has_3_labels() throws Exception {
		/* execute */
		String script = TestScriptLoader.loadScriptFromTestScripts("bugfix_11.bat");
		BatchScriptModel result = builderToTest.build(script);

		/* test */
		assertNotNull(result);
		List<BatchLabel> labels = result.getLabels();
		assertEquals(3, labels.size());
	}

	@Test
	public void text_empty_returns_model_without_labels() {
		/* execute */
		BatchScriptModel result = builderToTest.build(null);

		/* test */
		assertNotNull(result);
		assertTrue(result.getLabels().isEmpty());
	}

	@Test
	public void text_null_returns_model_without_labels() {
		/* execute */
		BatchScriptModel result = builderToTest.build(null);

		/* test */
		assertNotNull(result);
		assertTrue(result.getLabels().isEmpty());
	}

	@Test
	public void text_test_returns_model_without_labels() {
		/* prepare */
		StringBuilder sb = new StringBuilder();
		sb.append("test");
		String text = sb.toString();

		/* execute */
		BatchScriptModel result = builderToTest.build(text);

		/* test */
		assertNotNull(result);
		assertTrue(result.getLabels().isEmpty());
	}

	@Test
	public void text_colon_test_returns_model_with_label_test() {
		/* prepare */
		StringBuilder sb = new StringBuilder();
		sb.append(":test");
		// 01234
		String text = sb.toString();

		/* execute */
		BatchScriptModel result = builderToTest.build(text);

		/* test */
		assertNotNull(result);
		assertEquals(1, result.getLabels().size());
		BatchLabel label = result.getLabels().iterator().next();
		assertNotNull(label);

		assertEquals("test", label.getName());
		assertEquals(1, label.getPosition());
		assertEquals(4, label.getEnd());
		assertEquals(4, label.getLengthToNameEnd());
	}

	@Test
	public void text_space_colon_test_returns_model_with_no_labels() {
		/* prepare */
		StringBuilder sb = new StringBuilder();
		sb.append(" :test");
		// 012345
		String text = sb.toString();

		/* execute */
		BatchScriptModel result = builderToTest.build(text);

		/* test */
		assertNotNull(result);
		assertTrue(result.getLabels().isEmpty());

	}

	@Test
	public void text_abc_colon_test_returns_model_with_no_labels() {
		/* prepare */
		StringBuilder sb = new StringBuilder();
		sb.append("abc:test");

		String text = sb.toString();

		/* execute */
		BatchScriptModel result = builderToTest.build(text);

		/* test */
		assertNotNull(result);
		assertTrue(result.getLabels().isEmpty());

	}

	@Test
	public void text_colon_colon_test_returns_model_with_no_labels() {
		/* prepare */
		StringBuilder sb = new StringBuilder();
		sb.append("::test");

		String text = sb.toString();

		/* execute */
		BatchScriptModel result = builderToTest.build(text);

		/* test */
		assertNotNull(result);
		assertTrue(result.getLabels().isEmpty());

	}

	@Test
	public void text_abc_new_line_colon_test_returns_model_with_label_test() {
		/* prepare */
		StringBuilder sb = new StringBuilder();
		sb.append("abc\n");
		// 0123
		sb.append(":test2");
		// 456789
		String text = sb.toString();

		/* execute */
		BatchScriptModel result = builderToTest.build(text);

		/* test */
		assertNotNull(result);
		List<BatchLabel> labels = result.getLabels();
		assertEquals(1, labels.size());
		BatchLabel label = labels.iterator().next();
		assertNotNull(label);

		assertEquals("test2", label.getName());
		assertEquals(5, label.getPosition());
		assertEquals(9, label.getEnd());
		assertEquals(5, label.getLengthToNameEnd());

	}

	@Test
	public void text_ab_cr_new_line_colon_test_returns_model_with_label_test() {
		/* prepare */
		StringBuilder sb = new StringBuilder();
		sb.append("ab\r\n");
		// 012 3
		sb.append(":test2");
		// 456789
		String text = sb.toString();

		/* execute */
		BatchScriptModel result = builderToTest.build(text);

		/* test */
		assertNotNull(result);
		List<BatchLabel> labels = result.getLabels();
		assertEquals(1, labels.size());
		BatchLabel label = labels.iterator().next();
		assertNotNull(label);

		assertEquals("test2", label.getName());
		assertEquals(5, label.getPosition());
		assertEquals(9, label.getEnd());
		assertEquals(5, label.getLengthToNameEnd());

	}

	@Test
	public void text_abc_new_line_colon_test_new_line_def_bla_newline_colon_test2_returns_model_with_labels_test_and_test2() {
		/* prepare */
		StringBuilder sb = new StringBuilder();
		sb.append("abc\n");
		// 0123
		sb.append(":test\n");
		// 456789
		sb.append("def bla\n");
		// 01234567
		sb.append(":test2\n");
		// 8901234

		String text = sb.toString();

		/* execute */
		BatchScriptModel result = builderToTest.build(text);

		/* test */
		assertNotNull(result);
		assertEquals(2, result.getLabels().size());
		Iterator<BatchLabel> iterator = result.getLabels().iterator();
		BatchLabel label = iterator.next();
		assertNotNull(label);

		assertEquals("test", label.getName());
		assertEquals(5, label.getPosition());
		assertEquals(8, label.getEnd());
		assertEquals(4, label.getLengthToNameEnd());

		label = iterator.next();
		assertEquals("test2", label.getName());
		assertEquals(19, label.getPosition());
		assertEquals(23, label.getEnd());
		assertEquals(5, label.getLengthToNameEnd());

	}

	@Test
	public void text_ab_cr_new_line_colon_tes_cr_new_line_def_bla_newline_colon_test2_returns_model_with_labels_test_and_test2() {
		/* prepare */
		StringBuilder sb = new StringBuilder();
		sb.append("ab\r\n");
		// 0123
		sb.append(":test\r\n");
		// 456789 0
		sb.append("def bl\r\n");
		// 1234567 8
		sb.append(":test2\r\n");
		// 9012345 6

		String text = sb.toString();

		/* execute */
		BatchScriptModel result = builderToTest.build(text);

		/* test */
		assertNotNull(result);
		assertEquals(2, result.getLabels().size());
		Iterator<BatchLabel> iterator = result.getLabels().iterator();
		BatchLabel label = iterator.next();
		assertNotNull(label);

		assertEquals("test", label.getName());
		assertEquals(5, label.getPosition());
		assertEquals(8, label.getEnd());
		assertEquals(4, label.getLengthToNameEnd());

		label = iterator.next();
		assertEquals("test2", label.getName());
		assertEquals(20, label.getPosition());
		assertEquals(24, label.getEnd());
		assertEquals(5, label.getLengthToNameEnd());

	}
}
