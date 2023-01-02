package ushiosan.jvm_utilities.lang.reflection;

import org.junit.Test;
import ushiosan.jvm_utilities.lang.Obj;
import ushiosan.jvm_utilities.lang.print.annotations.PrintOpts;

import java.lang.reflect.Field;

public class FieldUtilsTest {
	
	@Test
	public void runTest() throws NoSuchFieldException, IllegalAccessException {
		ExampleClass instance = new ExampleClass3();
		String instanceStr = Obj.toInstanceString(instance);
		
		Field field = FieldUtils.findFieldObj(instance, "notContentField");
		
		System.err.println(instanceStr);
		System.err.println(field);
		
		// Change value
		field.setAccessible(true);
		field.set(instance, "Some text in notContentField Field");
		
		instanceStr = Obj.toInstanceString(instance);
		System.err.println(instanceStr);
		
		try {
			Field field2 = FieldUtils.findFieldObj(instance, "notValidField");
			System.err.println(field2);
		} catch (Exception ignored) {
		}
	}
	
	
	/* ---------------------------------------------------------
	 * Internal types
	 * --------------------------------------------------------- */
	
	@PrintOpts(privateFieldsAccess = true)
	static class ExampleClass {
		
		private final int baseField = -1;
		
	}
	
	static class ExampleClass2 extends ExampleClass {
		
		private final String exampleField = "Some text";
		
	}
	
	static class ExampleClass3 extends ExampleClass2 {
		
		private final String notContentField = null;
		
	}
	
	
}