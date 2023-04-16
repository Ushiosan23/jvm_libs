package ushiosan.jvm_utilities.error;

import org.junit.Assert;
import org.junit.Test;
import ushiosan.jvm_utilities.TestUtils;

public class ErrorsTest {
	
	@Test(expected = RuntimeException.class)
	public void launchErrorTest() {
		TestUtils.printSection();
		
		Errors.launch(RuntimeException.class, "Example error");
		System.out.println();
	}
	
	@Test
	public void rootCauseTest() {
		TestUtils.printSection();
		
		try {
			Errors.launch(RuntimeException.class, new Object());
		} catch (Exception e) {
			Throwable root = Errors.getRootCause(e);
			String rootStr = Errors.toString(root, false);
			String eStr = Errors.toString(e, true);
			
			Assert.assertEquals(
				"The contents of the exception are not equal",
				rootStr,
				eStr);
			
			System.err.println(rootStr);
			System.err.println(eStr);
			System.out.println();
		}
	}
	
	@Test
	public void toStringTest() {
		TestUtils.printSection();
		
		Exception exception = new Exception("Example error");
		
		System.err.println(Errors.toString(exception, true));
		System.out.println();
	}
	
}