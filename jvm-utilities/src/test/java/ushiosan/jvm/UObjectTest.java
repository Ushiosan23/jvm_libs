package ushiosan.jvm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ushiosan.UTestUnit;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.atomic.AtomicBoolean;

public class UObjectTest extends UTestUnit {
	
	@Test
	public void tryCastTest() throws IOException {
		sectionErrOf(() -> {
			// Try to cast some elements
			URL googleURL = new URL("https://google.com");
			URLConnection connection = googleURL.openConnection();
			AtomicBoolean valid = new AtomicBoolean(false);
			
			UObject.tryCast(connection, HttpURLConnection.class, httpConnection -> valid.set(true));
			
			// Assertions
			Assertions.assertTrue(valid.get(), "Invalid cast");
			
			// Display information
			System.out.printf("URL: %s%n", googleURL);
			System.out.printf("Connection: %s%n", connection);
			System.out.printf("Valid result: %s%n", valid);
		});
	}
	
	
}