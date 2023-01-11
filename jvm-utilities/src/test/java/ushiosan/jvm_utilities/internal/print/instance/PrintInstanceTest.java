package ushiosan.jvm_utilities.internal.print.instance;

import org.junit.Assert;
import org.junit.Test;
import ushiosan.jvm_utilities.lang.Obj;
import ushiosan.jvm_utilities.lang.print.annotations.PrintExclude;
import ushiosan.jvm_utilities.lang.print.annotations.PrintOpts;

import static ushiosan.jvm_utilities.lang.Obj.toInstanceString;

public class PrintInstanceTest {
	
	@Test
	public void runTest() throws Exception {
		ExampleClass example = new ExampleClass3();
		String instanceStr = toInstanceString(example);
		
		Assert.assertEquals("Invalid content",
							"ExampleClass3{nothing=\"Hello, World!\", getNothing()=\"Hello, World!\"}",
							instanceStr);
		
		System.err.println(example);
		System.err.println(instanceStr);
	}
	
	/* ---------------------------------------------------------
	 * Extra types
	 * --------------------------------------------------------- */
	
	@PrintOpts(privateFieldsAccess = true, getterAccess = true)
	static class ExampleClass {
		
		@PrintExclude
		public final boolean exampleB = true;
		
		@SuppressWarnings("FieldCanBeLocal")
		private final String nothing = "Hello, World!";
		
		public String getNothing() {
			return nothing;
		}
		
	}
	
	static class ExampleClass2 extends ExampleClass {}
	
	static class ExampleClass3 extends ExampleClass2 {
		
		@Override
		public String toString() {
			return Obj.toInstanceString(this);
		}
		
	}
	
}