package ushiosan.jvm_utilities.lang.reflection;

import org.junit.Test;
import ushiosan.jvm_utilities.TestUtils;
import ushiosan.jvm_utilities.lang.Obj;
import ushiosan.jvm_utilities.lang.print.annotations.PrintOpts;
import ushiosan.jvm_utilities.lang.reflection.options.ReflectionOpts;

import java.lang.reflect.Field;

public class FieldUtilsTest {
	
	@Test
	public void findFieldObjTest() throws NoSuchFieldException, IllegalAccessException {
		TestUtils.printSection();
		
		ExampleClass instance = new ExampleClass3();
		var opts = ReflectionOpts.<Field>getDefault()
			.setRecursive(false)
			.setOnlyPublic(false);
		Field field = FieldUtils.findFieldObj(instance, "notContentField", opts);
		
		System.err.println(opts);
		System.err.println(instance);
		
		// Change value
		field.setAccessible(true);
		field.set(instance, "Some text in notContentField Field");
		
		System.err.println(instance);
		System.out.println();
	}
	
	
	/* ---------------------------------------------------------
	 * Internal types
	 * --------------------------------------------------------- */
	
	@PrintOpts(privateFieldsAccess = true)
	static class ExampleClass {
		
		private final int baseField = -1;
		
		@Override
		public String toString() {
			return Obj.toInstanceString(this);
		}
		
	}
	
	static class ExampleClass2 extends ExampleClass {
		
		private final String exampleField = "Some text";
		
	}
	
	static class ExampleClass3 extends ExampleClass2 {
		
		private final String notContentField = null;
		
	}
	
	
}