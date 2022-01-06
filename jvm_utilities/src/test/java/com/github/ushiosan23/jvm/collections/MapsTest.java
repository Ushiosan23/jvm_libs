package com.github.ushiosan23.jvm.collections;

import org.junit.Test;

import java.util.Map;

public class MapsTest {

	@Test
	public void runTest() throws Exception {
		Map<String, String> map = Maps.of(
			Maps.entry("Hello", null),
			Maps.mutableEntry("XD", "World")
		);

		System.err.println(map);
	}

}
