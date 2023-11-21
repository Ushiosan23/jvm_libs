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
		// Generate User-Agent content
		String userAgentStr = String.format(USER_AGENT,
											System.getProperty("java.version"),
											System.getProperty("java.vendor"),
											System.getProperty("os.name"),
											System.getProperty("os.arch"),
											UR.getInstance().getProperty("jvm.utilities.version", ""));
		
		// Generate and update HTTP request headers
		return HttpRequest.newBuilder().setHeader("User-Agent", userAgentStr);
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
