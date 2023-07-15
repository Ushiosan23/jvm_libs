package ushiosan.jvm;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ushiosan.jvm.test.UTestUnit;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.atomic.AtomicBoolean;

public class UObjectTest extends UTestUnit {
	
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
	public void tryCastTest() throws IOException {
		makeSectionError(() -> {
			// Try to cast some elements
			URL googleURL = new URL("https://google.com");
			URLConnection connection = googleURL.openConnection();
			AtomicBoolean valid = new AtomicBoolean(false);
			
			UObject.tryCast(connection, HttpURLConnection.class, httpConnection -> valid.set(true));
			
			// Assertions
			Assertions.assertTrue(valid.get(), "Invalid cast");
			
			// Display information
			println("URL: %s", googleURL);
			println("Connection: %s", connection);
			println("Valid result: %s", valid);
		});
	}
	
}