package ushiosan.jvm.test.test.http;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ushiosan.jvm.http.UHttpResponse;
import ushiosan.jvm.test.UTestUnit;
import ushiosan.jvm.test.test.Constants;

import java.net.URI;
import java.net.http.HttpClient;
import java.time.Duration;

class UHttpResponseTest extends UTestUnit {
	
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
	public void validateUriTest() throws Exception {
		makeSectionError(() -> {
			HttpClient httpClient = HttpClient.newBuilder()
				.version(HttpClient.Version.HTTP_2)
				.connectTimeout(Duration.ofSeconds(3))
				.build();
			
			// Generate invalid redirect uri
			URI googleUri = URI.create("https://www.google.com");
			// Generate valid redirect uri
			URI githubFileUri = URI.create(
				"https://github.com/godotengine/godot/releases/download/4.1-stable/Godot_v4.1-stable_export_templates.tpz");
			
			// Validate URLs
			URI validGoogleUri = UHttpResponse.validateUri(googleUri, httpClient).get();
			URI validGithubUri = UHttpResponse.validateUri(githubFileUri, httpClient).get();
			
			// Assertions
			Assertions.assertEquals(googleUri, validGoogleUri,
									"A redirect was not expected here");
			Assertions.assertNotEquals(githubFileUri, validGithubUri,
									   "A redirect was expected here");
			
			// Display information
			println("Google URI: \n\t%s\n\t%s",
					googleUri, validGoogleUri);
			println("Github URI: \n\t%s\n\t%s",
					githubFileUri, validGithubUri);
		});
	}
	
}