package com.github.ushiosan23.jvm.collections;

import org.junit.Test;

import java.util.Map;

public class MapsTest {

	@Test
	public void runTest() throws Exception {
		Map<String, String> map = Containers.mapOf(
			Containers.entry("Hello", null),
			Containers.mutableEntry("XD", "World")
		);

		System.err.println(map);
	}

}
