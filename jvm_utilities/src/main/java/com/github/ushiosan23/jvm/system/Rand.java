package com.github.ushiosan23.jvm.system;

import com.github.ushiosan23.jvm.collections.Arr;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Random;

public final class Rand {

	/* ------------------------------------------------------------------
	 * Properties
	 * ------------------------------------------------------------------ */

	/**
	 * System random object
	 */
	private static Random systemRandom;

	/**
	 * Current random seed
	 */
	private static long randomSeed = -1L;

	/**
	 * Data range for all letters
	 */
	public static final int[] RAND_LETTERS = Arr.ofInt(65, 123);

	/**
	 * Data range for all numbers
	 */
	public static final int[] RAND_NUMBERS = Arr.ofInt(48, 58);

	/**
	 * Data range for alphanumeric letters
	 */
	public static final int[] RAND_ALPHANUMERIC = Arr.ofInt(48, 123);

	/**
	 * Data range for all valid symbols
	 */
	public static final int[] RAND_ALL_SYMBOLS = Arr.ofInt(33, 126);

	/* ------------------------------------------------------------------
	 * Constructors
	 * ------------------------------------------------------------------ */

	/**
	 * This class cannot be instantiated
	 */
	private Rand() {
	}

	/* ------------------------------------------------------------------
	 * Methods
	 * ------------------------------------------------------------------ */

	/**
	 * Return a default system random object
	 *
	 * @param seed Seed for the "Random" item.
	 *             It is recommended to use the default seed and only apply it for test use.
	 * @return Returns a default system random
	 * @see #getSystemRandom()
	 */
	public static Random getSystemRandom(long seed) {
		if (systemRandom == null) {
			randomSeed = seed;
			systemRandom = new Random(randomSeed);
		} else {
			if (seed != randomSeed)
				systemRandom.setSeed(seed);
		}

		return systemRandom;
	}

	/**
	 * Return a default system random object. This method applies the seed due to time.
	 * It is recommended for use in production.
	 *
	 * @return Returns a default system random
	 */
	public static Random getSystemRandom() {
		// Check seed value
		if (randomSeed == -1L)
			randomSeed = Calendar.getInstance().getTimeInMillis();
		// Get random system
		return getSystemRandom(randomSeed);
	}

	/**
	 * Generate a random string with an adjustable size.
	 *
	 * @param size       String size
	 * @param charLimits Limit in which the characters will be generated.
	 * @return Returns a random string
	 * @see Rand#RAND_ALL_SYMBOLS
	 * @see Rand#RAND_ALPHANUMERIC
	 * @see Rand#RAND_LETTERS
	 * @see Rand#RAND_NUMBERS
	 */
	public static @NotNull String getRandomString(int size, int @NotNull [] charLimits) {
		if (charLimits.length != 2)
			throw new IndexOutOfBoundsException("charLimits only can contains 2 elements");
		// Generate random string
		return getSystemRandom()
			.ints(charLimits[0], charLimits[1])
			.filter(n -> (n <= 57 || n >= 65) && (n <= 90 || n >= 97))
			.limit(size)
			.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			.toString();
	}

	/**
	 * Generate a random string with an adjustable size. By default, this method use {@link #RAND_ALL_SYMBOLS} limits
	 *
	 * @param size String size
	 * @return Returns a random string
	 * @see Rand#RAND_ALL_SYMBOLS
	 * @see Rand#RAND_ALPHANUMERIC
	 * @see Rand#RAND_LETTERS
	 * @see Rand#RAND_NUMBERS
	 */
	public static @NotNull String getRandomString(int size) {
		return getRandomString(size, RAND_ALL_SYMBOLS);
	}

	/**
	 * Generate a random string with an adjustable size. By default, this method use {@link #RAND_ALL_SYMBOLS} limits
	 * and size is 18
	 *
	 * @return Returns a random string
	 * @see Rand#RAND_ALL_SYMBOLS
	 * @see Rand#RAND_ALPHANUMERIC
	 * @see Rand#RAND_LETTERS
	 * @see Rand#RAND_NUMBERS
	 */
	public static @NotNull String getRandomString() {
		return getRandomString(18);
	}

}
