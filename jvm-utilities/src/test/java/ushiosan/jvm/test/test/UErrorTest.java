package ushiosan.jvm.test.test;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ushiosan.jvm.UError;
import ushiosan.jvm.test.UTestUnit;

public class UErrorTest extends UTestUnit {
	
	/**
	 * The name of the module where the tests are being done
	 *
	 * @return the module name
	 */
	@Override
	public @NotNull String module() {
		return Constants.LIB_MODULE;
	}
	
	@Test
	public void extractTraceTest() {
		makeSection(() -> {
			Throwable error = new RuntimeException("Example Error");
			String extractedTrace = UError.extractTrace(error);
			String validTrace = "java.lang.RuntimeException: Example Error";
			
			// Assertions
			Assertions.assertTrue(extractedTrace.startsWith(validTrace),
								  "Invalid Stack Trace information");
			
			// Display information
			println("Stack trace: %s", extractedTrace);
		});
	}
	
}