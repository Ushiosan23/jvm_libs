package ushiosan.jvm.http;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ushiosan.jvm.Constants;
import ushiosan.jvm.test.UTestUnit;

import java.net.http.HttpRequest;

class UHttpRequestTest extends UTestUnit {
	
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
	public void generateRequestTest() {
		makeSection(() -> {
			// Temporal variables
			var request = UHttpRequest.makeRequest("https://www.google.com")
				.method("HEAD", HttpRequest.BodyPublishers.noBody())
				.build();
			var headers = request.headers();
			var agent = headers.firstValue("User-Agent");
			
			// Assertions
			Assertions.assertTrue(agent.isPresent(),
								  "The \"User-Agent\" does not exist.");
			
			// Display information
			println("User-Agent: %s", agent.get());
		});
	}
	
	
}