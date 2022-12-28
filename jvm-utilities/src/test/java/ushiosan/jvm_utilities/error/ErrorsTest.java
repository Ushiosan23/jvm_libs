package ushiosan.jvm_utilities.error;

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
			
			System.err.println(Errors.toString(root, false));
			System.err.println(Errors.toString(e, false));
		}
	}
	
	@Test
	public void toStringTest() {
		Exception exception = new Exception("Example error");
		System.err.println(Errors.toString(exception, true));
	}
	
}