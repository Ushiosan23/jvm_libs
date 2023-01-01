package ushiosan.jvm_utilities.internal.print.instance;

import org.junit.Assert;
import org.junit.Test;
import ushiosan.jvm_utilities.lang.print.annotations.PrintExclude;
import ushiosan.jvm_utilities.lang.print.annotations.PrintOpts;

import static ushiosan.jvm_utilities.lang.Obj.toInstanceString;

public class PrintInstanceTest {
	
	@Test
	public void runTest() throws Exception {
		ExampleClass example = new ExampleClass3();
		String instanceStr = toInstanceString(example);
		
		Assert.assertEquals("Invalid content",
							"ushiosan.jvm_utilities.internal.print.instance.PrintInstanceTest$ExampleClass3{}",
							instanceStr);
		
		System.err.println(instanceStr);
	}
	
	/* ---------------------------------------------------------
	 * Extra types
	 * --------------------------------------------------------- */
	
	@PrintOpts(shortName = false, privateFieldsAccess = true)
	static class ExampleClass {
		
		@PrintExclude
		private final Void nothing = null;
		
	}
	
	static class ExampleClass2 extends ExampleClass {}
	
	static class ExampleClass3 extends ExampleClass2 {}
	
}