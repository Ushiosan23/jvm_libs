package ushiosan.jvm_utilities.error;

import org.junit.Assert;
import org.junit.Test;

public class ErrorsTest {
	
	@Test(expected = RuntimeException.class)
	public void launchErrorTest() {
		Errors.launch(RuntimeException.class, "Example error");
	}
	
	@Test
	public void rootCauseTest() {
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
			
			System.out.println("rootCauseTest()");
			System.err.println(rootStr);
			System.err.println(eStr);
			System.out.println();
		}
	}
	
	@Test
	public void toStringTest() {
		Exception exception = new Exception("Example error");
		
		System.out.println("toStringTest()");
		System.err.println(Errors.toString(exception, true));
		System.out.println();
	}
	
}