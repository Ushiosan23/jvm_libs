package com.github.ushiosan23.jvm.base;

import org.junit.Test;

public class StringUtilsTest {

	@Test
	public void runCapitalizeTest() {
		String message = "hello, world!";

		System.err.println(
			StringUtils.capitalize(message, true)
		);
	}

}
