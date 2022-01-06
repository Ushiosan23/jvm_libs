package com.github.ushiosan23.jvm.base;

import org.junit.Test;

@SuppressWarnings("FieldCanBeLocal")
public class NumbersTest {

	private final float nonDecimal = 1298839.0f;
	private final double hasDecimal = 8192891312.888939912;

	@Test
	public void runHasDecimalTest() {
		System.err.printf("%f has decimals: %s\n", nonDecimal, Numbers.hasDecimals(nonDecimal));
		System.err.printf("%f has decimals: %s\n", hasDecimal, Numbers.hasDecimals(hasDecimal));
		System.err.println();
	}

}
