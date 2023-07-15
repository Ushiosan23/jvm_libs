package ushiosan.jvm.http;

import org.jetbrains.annotations.NotNull;
import ushiosan.jvm.ULogger;
import ushiosan.jvm.collections.UMap;
import ushiosan.jvm.collections.UStack;
import ushiosan.jvm.content.UPair;
import ushiosan.jvm.function.UFunErr;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

import static ushiosan.jvm.ULogger.logInfo;
import static ushiosan.jvm.UObject.requireNotNull;
import static ushiosan.jvm.http.UHttpRequest.makeRequest;

public final class UHttpResponse {
	
	private static final Logger LOG = ULogger.getLogger(UHttpResponse.class);
	/**
	 * All possible error codes for an http request
	 */
	private static final Map<Integer, String> HTTP_INVALID_CODES = UMap.make(
		// Client error responses
		UPair.make(401, "Unauthorized"),
		UPair.make(400, "Bad request"),
		UPair.make(402, "Payment required"),
		UPair.make(403, "Forbidden"),
		UPair.make(404, "Not found"),
		UPair.make(405, "Method not allowed"),
		UPair.make(406, "Not acceptable"),
		UPair.make(407, "Proxy authentication required"),
		UPair.make(408, "Request timeout"),
		UPair.make(409, "Conflict"),
		UPair.make(410, "Gone"),
		UPair.make(411, "Length Required"),
		UPair.make(412, "Precondition Failed"),
		UPair.make(413, "Payload Too Large"),
		UPair.make(414, "URI Too Long"),
		UPair.make(415, "Unsupported Media Type"),
		UPair.make(416, "Range Not Satisfiable"),
		UPair.make(417, "Expectation Failed"),
		UPair.make(418, "I'm a teapot"),
		UPair.make(421, "Misdirected Request"),
		UPair.make(422, "Unprocessable Content"),
		UPair.make(423, "Locked"),
		UPair.make(424, "Failed Dependency"),
		UPair.make(425, "Too Early"),
		UPair.make(426, "Upgrade Required"),
		UPair.make(428, "Precondition Required"),
		UPair.make(429, "Too Many Requests"),
		UPair.make(431, "Request Header Fields Too Large"),
		UPair.make(451, "Unavailable For Legal Reasons"),
		// Server error responses
		UPair.make(500, "Internal Server Error"),
		UPair.make(501, "Not Implemented"),
		UPair.make(502, "Bad Gateway"),
		UPair.make(503, "Service Unavailable"),
		UPair.make(504, "Gateway Timeout"),
		UPair.make(505, "HTTP Version Not Supported"),
		UPair.make(506, "Variant Also Negotiates"),
		UPair.make(507, "Insufficient Storage"),
		UPair.make(508, "Loop Detected"),
		UPair.make(510, "Not Extended"),
		UPair.make(511, "Network Authentication Required"));
	
	/* -----------------------------------------------------
	 * Properties
	 * ----------------------------------------------------- */
	/**
	 * All possible redirect codes for an http request
	 */
	private static final Map<Integer, String> HTTP_REDIRECT_CODES = UMap.make(
		UPair.make(300, "Not Extended"),
		UPair.make(301, "Moved Permanently"),
		UPair.make(302, "Found"),
		UPair.make(303, "See Other"),
		UPair.make(304, "Not Modified"),
		UPair.make(305, "Use Proxy"),
		UPair.make(306, "Unused"),
		UPair.make(307, "Temporary Redirect"),
		UPair.make(308, "Permanent Redirect"));
	
	/**
	 * This class cannot be instantiated directly
	 */
	private UHttpResponse() {}
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Detect if there is a possible error within the http response
	 *
	 * @param response the http response to be verified
	 * @return the error code contained in the http response or {@link Optional#empty()} if the http response is valid
	 */
	public static @NotNull Optional<UPair<Integer, String>> detectError(@NotNull HttpResponse<?> response) {
		return HTTP_INVALID_CODES.entrySet().stream()
			.map(UPair::copyOf)
			.filter(it -> it.first == response.statusCode())
			.findFirst();
	}
	
	/**
	 * Detect if there is a possible redirection within the http response
	 *
	 * @param response the http response to be verified
	 * @return the redirection code contained in the http response or {@link Optional#empty()} if there is no redirect code
	 */
	public static @NotNull Optional<UPair<Integer, String>> detectRedirect(@NotNull HttpResponse<?> response) {
		return HTTP_REDIRECT_CODES.entrySet().stream()
			.map(UPair::copyOf)
			.filter(it -> it.first == response.statusCode())
			.findFirst();
	}
	
	/**
	 * Send an HTTP request (HEAD) with no content and no response, to get the headers of the requested content.
	 *
	 * @param uri           the route to which you want to send the request
	 * @param client        the http client to send the request
	 * @param preventErrors option used to throw an error if the http response is not valid (error codes 400 or 500)
	 * @return the response without content of the http request (This response is only used to get the headers)
	 */
	public static @NotNull CompletableFuture<HttpResponse<Void>> requestHeaders(@NotNull URI uri,
		@NotNull HttpClient client, boolean preventErrors) {
		return supplyAsyncErr(() -> {
			// Generate request
			HttpRequest request = makeRequest(uri)
				.method("HEAD", HttpRequest.BodyPublishers.noBody())
				.build();
			
			// Generate http response
			HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
			var detectedError = detectError(response);
			
			// Log info
			logInfo(LOG, "HTTP Request \"%s\" status code: %d", uri, response.statusCode());
			if (detectedError.isPresent() && preventErrors) {
				var error = detectedError.get();
				throw new CancellationException(String.format("HTTP Error %d: %s", error.first, error.second));
			}
			
			return response;
		});
	}
	
	/**
	 * Send an HTTP request (HEAD) with no content and no response, to get the headers of the requested content.
	 *
	 * @param uri    the route to which you want to send the request
	 * @param client the http client to send the request
	 * @return the response without content of the http request (This response is only used to get the headers)
	 */
	public static @NotNull CompletableFuture<HttpResponse<Void>> requestHeaders(@NotNull URI uri, @NotNull HttpClient client) {
		return requestHeaders(uri, client, true);
	}
	
	/**
	 * Method used to validate the url.
	 * <p>
	 * This means that it will be verified if the url does not return a 400 or 500 errors and
	 * also if this is not a redirect url.
	 * If it is a redirect url, then the url of the content being redirected to is returned instead
	 * of the original url (recursively).
	 *
	 * @param uri           the route to which you want to send the request
	 * @param client        the http client to send the request
	 * @param preventErrors option used to throw an error if the http response is not valid (error codes 400 or 500)
	 * @return the valid url to send an http request
	 */
	public static @NotNull CompletableFuture<URI> validateUri(@NotNull URI uri, @NotNull HttpClient client,
		boolean preventErrors) {
		return supplyAsyncErr(() -> {
			// Generate uri stack
			Stack<URI> uriStack = UStack.make(uri);
			URI result = null;
			
			// Iterate all redirections
			do {
				URI current = uriStack.pop();
				var responseHeaders = requestHeaders(current, client, preventErrors).get();
				
				// Detect any redirection
				var redirection = detectRedirect(responseHeaders);
				if (redirection.isPresent()) {
					var redirectUri = responseHeaders.headers()
						.firstValue("Location");
					if (redirectUri.isEmpty()) {
						throw new CancellationException("The redirection is not valid");
					}
					
					uriStack.push(URI.create(redirectUri.get()));
					continue;
				}
				
				result = current;
			} while (!uriStack.isEmpty());
			
			requireNotNull(result, "HttpHeaders");
			return result;
		});
	}
	
	/**
	 * Method used to validate the url.
	 * <p>
	 * This means that it will be verified if the url does not return a 400 or 500 errors and
	 * also if this is not a redirect url.
	 * If it is a redirect url, then the url of the content being redirected to is returned instead
	 * of the original url (recursively).
	 *
	 * @param uri    the route to which you want to send the request
	 * @param client the http client to send the request
	 * @return the valid url to send an http request
	 */
	public static @NotNull CompletableFuture<URI> validateUri(@NotNull URI uri, @NotNull HttpClient client) {
		return validateUri(uri, client, true);
	}
	
	/* -----------------------------------------------------
	 * Internal methods
	 * ----------------------------------------------------- */
	
	/**
	 * Sends an http request, but the passed lambda expression can throw exceptions and be handled automatically.
	 *
	 * @param action the action you want to execute
	 * @param <T>    the result of the http request
	 * @return a future task with the response of the http request
	 */
	private static <T> @NotNull CompletableFuture<T> supplyAsyncErr(@NotNull UFunErr<T, ? extends Throwable> action) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				return action.invoke();
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		});
	}
	
}
