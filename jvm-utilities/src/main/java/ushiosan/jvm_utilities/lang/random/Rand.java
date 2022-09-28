package ushiosan.jvm_utilities.lang.random;

import java.util.Random;
import org.jetbrains.annotations.NotNull;
import ushiosan.jvm_utilities.lang.Obj;
import ushiosan.jvm_utilities.lang.collection.Arrs;

public final class Rand {

	/**
	 * System random object
	 */
	private static Random sysRandom;

	/* -----------------------------------------------------
	 * Properties
	 * ----------------------------------------------------- */

	/**
	 * Default random size
	 */
	private static final int DEFAULT_SIZE = 18;

	/**
	 * Current system random seed
	 */
	private static long randomSeed = -1L;

	/**
	 * This class cannot be instantiated.
	 * <p>
	 * Singleton or utility class mode.
	 */
	private Rand() {
	}

	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */

	/**
	 * Returns a pseudorandom object to use in the methods of this same class
	 *
	 * @param seed seed responsible for generating the pseudorandom values
	 * @return the {@link Random} instance
	 */
	public static @NotNull Random getRandom(long seed) {
		long oldSeed = randomSeed;
		if (randomSeed == -1L || seed != randomSeed) randomSeed = seed;
		// Check if random is defined
		if (Obj.isNull(sysRandom)) sysRandom = new Random(randomSeed);
		// Check if seed is different
		if (seed != oldSeed) sysRandom.setSeed(randomSeed);
		return sysRandom;
	}

	/**
	 * Returns a pseudorandom object to use in the methods of this same class
	 *
	 * @return the {@link Random} instance
	 */
	public static @NotNull Random getRandom() {
		return getRandom(randomSeed == -1L ? System.currentTimeMillis() : randomSeed);
	}

	/* -----------------------------------------------------
	 * Random string
	 * ----------------------------------------------------- */

	/**
	 * Returns a pseudo random text string, depending on the selected setting
	 *
	 * @param size   size of characters that the string will have
	 * @param type   the type of configuration used
	 * @param ignore all the characters you want to ignore within the result
	 * @return a pseudo random text string
	 * @see TextType
	 */
	public static @NotNull String getRandomString(int size, @NotNull TextType type, char... ignore) {
		return getRandom()
			.ints(type.start, type.end)
			// Prevent to use invalid characters
			.filter(n -> (n <= 57 || n >= 65) && (n <= 90 || n >= 97))
			// Ignored characters
			.filter(n -> !Arrs.contains(ignore, (char) n))
			.limit(size)
			.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			.toString();
	}

	/**
	 * Returns a pseudo random text string, depending on the selected setting
	 *
	 * @param size   size of characters that the string will have
	 * @param ignore all the characters you want to ignore within the result
	 * @return a pseudo random text string
	 * @see #getRandomString(int, TextType, char...)
	 * @see TextType
	 */
	public static @NotNull String getRandomString(int size, char... ignore) {
		return getRandomString(size, TextType.ALL_SYMBOLS, ignore);
	}

	/**
	 * Returns a pseudo random text string, depending on the selected setting
	 *
	 * @param ignore all the characters you want to ignore within the result
	 * @return a pseudo random text string
	 * @see #getRandomString(int, TextType, char...)
	 * @see #getRandomString(int, char...)
	 * @see TextType
	 */
	public static @NotNull String getRandomString(char... ignore) {
		return getRandomString(DEFAULT_SIZE, ignore);
	}

	/* -----------------------------------------------------
	 * Random range
	 * ----------------------------------------------------- */

	/**
	 * Returns an array of pseudorandom numbers within a specified range
	 *
	 * @param start     the start of the range (inclusive)
	 * @param end       the end of the range (no inclusive)
	 * @param limit     total elements in array
	 * @param inclusive if {@code true} the end of the range is included within
	 *                  the allowed values {@code false} if the end of the range
	 *                  is excluded from the allowed values
	 * @return an array of pseudorandom numbers
	 */
	public static int[] getRandomRange(int start, int end, long limit, boolean inclusive) {
		if (inclusive) {
			if (end > Integer.MIN_VALUE && end < Integer.MAX_VALUE)
				end = end < 0 ? end - 1 : end + 1;
		}
		return getRandom()
			.ints(start, end)
			.limit(limit)
			.toArray();
	}

	/**
	 * Returns an array of pseudorandom numbers within a specified range
	 *
	 * @param start the start of the range (inclusive)
	 * @param end   the end of the range (no inclusive)
	 * @param limit total elements in array
	 * @return an array of pseudorandom numbers
	 * @see #getRandomRange(int, int, long, boolean)
	 */
	public static int[] getRandomRange(int start, int end, long limit) {
		return getRandomRange(start, end, limit, true);
	}

	/**
	 * Returns an array of pseudorandom numbers within a specified range
	 *
	 * @param start     the start of the range (inclusive)
	 * @param end       the end of the range (no inclusive)
	 * @param limit     total elements in array
	 * @param inclusive if {@code true} the end of the range is included within
	 *                  the allowed values {@code false} if the end of the range
	 *                  is excluded from the allowed values
	 * @return an array of pseudorandom numbers
	 */
	public static long[] getRandomRange(long start, long end, long limit, boolean inclusive) {
		if (inclusive) {
			if (end > Long.MIN_VALUE && end < Long.MAX_VALUE)
				end = end < 0 ? end - 1 : end + 1;
		}
		return getRandom()
			.longs(start, end)
			.limit(limit)
			.toArray();
	}

	/**
	 * Returns an array of pseudorandom numbers within a specified range
	 *
	 * @param start the start of the range (inclusive)
	 * @param end   the end of the range (no inclusive)
	 * @param limit total elements in array
	 * @return an array of pseudorandom numbers
	 * @see #getRandomRange(long, long, long, boolean)
	 */
	public static long[] getRandomRange(long start, long end, long limit) {
		return getRandomRange(start, end, limit, true);
	}

}
