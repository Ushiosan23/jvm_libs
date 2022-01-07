package com.github.ushiosan23.jvm.collections;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public final class Arr {

	/* ------------------------------------------------------------------
	 * Constructors
	 * ------------------------------------------------------------------ */

	/**
	 * This class cannot be instantiated
	 */
	private Arr() {
	}

	/* ------------------------------------------------------------------
	 * Methods
	 * ------------------------------------------------------------------ */

	/**
	 * Generate an array from given values
	 *
	 * @param elements Target elements to add
	 * @param <T>      Generic array type. If you want an array with primitives
	 *                 use the wrapped classes
	 * @return Returns an array with all values
	 * @see Boolean
	 * @see Character
	 * @see Byte
	 * @see Short
	 * @see Integer
	 * @see Long
	 * @see Float
	 * @see Double
	 */
	@SafeVarargs
	public static <T> T[] of(T... elements) {
		return elements;
	}

	/**
	 * Generate a primitive array
	 *
	 * @param elements Array elements
	 * @return Returns a primitive array with all elements
	 * @see Boolean
	 */
	public static boolean[] ofBoolean(boolean... elements) {
		return elements;
	}

	/**
	 * Generate a primitive array
	 *
	 * @param elements Array elements
	 * @return Returns a primitive array with all elements
	 * @see Character
	 */
	public static char[] ofChars(char... elements) {
		return elements;
	}

	/**
	 * Generate a primitive array
	 *
	 * @param elements Array elements
	 * @return Returns a primitive array with all elements
	 * @see Byte
	 */
	public static byte @NotNull [] ofByte(Number @NotNull ... elements) {
		// Byte cannot generate from generic numbers
		byte[] data = new byte[elements.length];
		// Iterate all elements
		for (int i = 0; i < data.length; i++) {
			data[i] = elements[i].byteValue();
		}
		// Return result
		return data;
	}

	/**
	 * Generate a primitive array
	 *
	 * @param elements Array elements
	 * @return Returns a primitive array with all elements
	 * @see Short
	 */
	public static short @NotNull [] ofShort(Number @NotNull ... elements) {
		// Byte cannot generate from generic numbers
		short[] data = new short[elements.length];
		// Iterate all elements
		for (int i = 0; i < data.length; i++) {
			data[i] = elements[i].shortValue();
		}
		// Return result
		return data;
	}

	/**
	 * Generate a primitive array
	 *
	 * @param elements Array elements
	 * @return Returns a primitive array with all elements
	 * @see Integer
	 */
	public static int[] ofInt(int... elements) {
		return elements;
	}

	/**
	 * Generate a primitive array
	 *
	 * @param elements Array elements
	 * @return Returns a primitive array with all elements
	 * @see Long
	 */
	public static long[] ofLong(long... elements) {
		return elements;
	}

	/**
	 * Generate a primitive array
	 *
	 * @param elements Array elements
	 * @return Returns a primitive array with all elements
	 * @see Float
	 */
	public static float[] ofFloat(float... elements) {
		return elements;
	}

	/**
	 * Generate a primitive array
	 *
	 * @param elements Array elements
	 * @return Returns a primitive array with all elements
	 * @see Double
	 */
	public static double[] ofDouble(double... elements) {
		return elements;
	}

	/**
	 * Array string representation
	 *
	 * @param array Array to get representation
	 * @return Array string representation
	 * @throws IllegalArgumentException Error if {@code array} is not an array
	 */
	public static @NotNull String toString(@NotNull Object array) {
		// Check object class
		if (!array.getClass().isArray())
			throw new IllegalArgumentException(String.format("Invalid array type. %s given.", array.getClass()));
		// Check array type
		Class<?> objClass = array.getClass();
		// Check all kind of arrays
		if (objClass == boolean[].class)
			return Arrays.toString((boolean[]) array);
		if (objClass == char[].class)
			return Arrays.toString((char[]) array);
		if (objClass == byte[].class)
			return Arrays.toString((byte[]) array);
		if (objClass == short[].class)
			return Arrays.toString((short[]) array);
		if (objClass == int[].class)
			return Arrays.toString((int[]) array);
		if (objClass == long[].class)
			return Arrays.toString((long[]) array);
		if (objClass == float[].class)
			return Arrays.toString((float[]) array);
		if (objClass == double[].class)
			return Arrays.toString((double[]) array);

		return Arrays.toString((Object[]) array);
	}

}
