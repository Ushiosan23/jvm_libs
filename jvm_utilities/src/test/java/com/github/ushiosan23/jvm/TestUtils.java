package com.github.ushiosan23.jvm;

import java.io.InputStream;
import java.net.URL;

public final class TestUtils {

	static ClassLoader loader = ClassLoader.getSystemClassLoader();

	/**
	 * This class cannot be instantiated
	 */
	private TestUtils() {
	}

	public static URL getResource(String name) {
		return loader.getResource(name);
	}

	public static InputStream getResourceAsStream(String name) {
		return loader.getResourceAsStream(name);
	}

}
