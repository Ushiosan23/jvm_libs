package ushiosan.jvm.http;

import org.jetbrains.annotations.NotNull;
import ushiosan.jvm.UR;

import java.net.URI;
import java.net.http.HttpRequest;

public final class UHttpRequest {
	
	/**
	 * The http tag that determines the user agent. This agent is mandatory for some websites, and
	 * by default it is not defined within each http request.
	 */
	private static final String USER_AGENT = "Java/%s (%s; %s; %s) jvm-utilities-http/%s";
	
	/* -----------------------------------------------------
	 * Properties
	 * ----------------------------------------------------- */
	
	/**
	 * This class cannot be instantiated directly
	 */
	private UHttpRequest() {}
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Generates a new http request configured for most websites to detect that it is valid.
	 *
	 * @return the http request configured to be valid
	 */
	public static @NotNull HttpRequest.Builder makeRequest() {
		// Temporal variables
		var javaVersion = System.getProperty("java.version");
		var javaVendor = System.getProperty("java.vendor");
		var javaOS = System.getProperty("os.name");
		var javaArch = System.getProperty("os.arch");
		var libraryVersion = UR.getInstance()
			.getProperty("jvm.utilities.version", "");
		
		// Generate request
		return HttpRequest.newBuilder()
			.setHeader("User-Agent",
					   String.format(USER_AGENT, javaVersion, javaVendor, javaOS, javaArch, libraryVersion));
	}
	
	/**
	 * Generates a new http request configured for most websites to detect that it is valid.
	 *
	 * @param uri the route to which you want to send the request
	 * @return the http request configured to be valid
	 */
	public static @NotNull HttpRequest.Builder makeRequest(@NotNull URI uri) {
		return makeRequest().uri(uri);
	}
	
	/**
	 * Generates a new http request configured for most websites to detect that it is valid.
	 *
	 * @param uri the route to which you want to send the request
	 * @return the http request configured to be valid
	 */
	public static @NotNull HttpRequest.Builder makeRequest(@NotNull String uri) {
		return makeRequest(URI.create(uri));
	}
	
}
