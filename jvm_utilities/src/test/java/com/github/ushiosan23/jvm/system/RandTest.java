package com.github.ushiosan23.jvm.system;

import org.junit.After;
import org.junit.Test;

import java.util.Random;

public class RandTest {

	@Test
	public void runGetSystemRandomTest() {
		Random random = Rand.getSystemRandom();

		System.err.println(random);
	}

	@After
	public void runGetRandomStringTest() {
		String defaultRandom = Rand.getRandomString();
		String randomAllSymbols = Rand.getRandomString(10, Rand.RAND_ALL_SYMBOLS);
		String randomNumeric = Rand.getRandomString(10, Rand.RAND_NUMBERS);
		String randomLetters = Rand.getRandomString(10, Rand.RAND_LETTERS);
		String randomAlphaNumeric = Rand.getRandomString(10, Rand.RAND_ALPHANUMERIC);

		System.out.println();
		System.out.println(defaultRandom);
		System.out.println(randomAllSymbols);
		System.out.println(randomNumeric);
		System.out.println(randomLetters);
		System.out.println(randomAlphaNumeric);
	}

}
