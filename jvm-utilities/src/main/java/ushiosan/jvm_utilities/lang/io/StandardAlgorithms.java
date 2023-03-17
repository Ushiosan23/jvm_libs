package ushiosan.jvm_utilities.lang.io;

import org.jetbrains.annotations.NotNull;
import ushiosan.jvm_utilities.lang.collection.Collections;

import java.security.Provider;
import java.security.Security;
import java.util.Set;

/**
 * Class that contains only the name of the algorithms used for the data hash.
 */
public final class StandardAlgorithms {
	
	/**
	 * MD5 Algorithm
	 *
	 * @see java.security.MessageDigest
	 */
	public static final String MD5 = "MD5";
	
	/* -----------------------------------------------------
	 * Properties
	 * ----------------------------------------------------- */
	/**
	 * SHA-1 Algorithm
	 *
	 * @see java.security.MessageDigest
	 */
	public static final String SHA1 = "SHA-1";
	/**
	 * SHA-256 Algorithm
	 *
	 * @see java.security.MessageDigest
	 */
	public static final String SHA256 = "SHA-256";
	/**
	 * SHA-512 Algorithm
	 *
	 * @see java.security.MessageDigest
	 */
	public static final String SHA512 = "SHA-512";
	/**
	 * MessageDigest algorithm pattern
	 */
	private static final String MESSAGE_DIGEST_PATTERN = "MessageDigest.";
	
	/**
	 * This class cannot be instantiated
	 */
	private StandardAlgorithms() {}
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Returns all the algorithms available in the JVM for use in the {@link java.security.MessageDigest} objects
	 *
	 * @return all JVM algorithms
	 */
	public static @NotNull Set<String> getSupportedAlgorithms() {
		// Get all providers
		Provider[] providers = Security.getProviders();
		Set<String> algorithms = Collections.mutableSetOf();
		
		// Iterate all providers
		for (Provider provider : providers) {
			// Obtain all algorithms
			Set<String> internals = provider.stringPropertyNames();
			for (String internal : internals) {
				if (internal.startsWith(MESSAGE_DIGEST_PATTERN)) {
					// Ignore internal algorithms or variations
					if (internal.contains("/") || internal.contains(" ")) continue;
					
					// Clean algorithm name
					String algorithmName = internal.substring(MESSAGE_DIGEST_PATTERN.length());
					algorithms.add(algorithmName);
				}
			}
		}
		
		return algorithms;
	}
	
}
