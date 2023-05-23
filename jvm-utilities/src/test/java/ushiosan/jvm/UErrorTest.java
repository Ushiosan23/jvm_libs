package ushiosan.jvm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ushiosan.UTestUnit;

public class UErrorTest extends UTestUnit {
	
	@Test
	public void extractTraceTest() {
		sectionOf(() -> {
			Throwable error = new RuntimeException("Example Error");
			String extractedTrace = UError.extractTrace(error);
			String validTrace = "java.lang.RuntimeException: Example Error";
			
			// Assertions
			Assertions.assertTrue(extractedTrace.startsWith(validTrace),
								  "Invalid Stack Trace information");
			
			// Display information
			System.out.printf("Stack trace: %s%n", extractedTrace);
		});
	}
	
}