package com.github.ushiosan23.jvm.collections;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

final class ArrInternal {

	/**
	 * This class cannot be instantiated
	 */
	private ArrInternal() {
	}

	/* ------------------------------------------------------------------
	 * Methods
	 * ------------------------------------------------------------------ */

	/**
	 * Convert any array to string representation
	 *
	 * @param array Target array to convert
	 * @return Returns an array string representation
	 * @throws IllegalArgumentException Error if {@code array} is not a valid array type
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

	/**
	 * Convert any numeric array to number array
	 *
	 * @param array Target array to convert
	 * @return Returns an abstract number array
	 * @throws IllegalArgumentException Error if {@code array} is not a valid array type or
	 *                                  the array is not a valid numeric type
	 */
	public static Number @NotNull [] convertToNumberArray(@NotNull Object array) {
		Class<?> objClass = array.getClass();
		// Check if is an array
		if (!array.getClass().isArray())
			throw new IllegalArgumentException("Invalid array param.");
		// Check array and get result
		if (objClass == byte[].class)
			return byteOf((byte[]) array);
		if (objClass == short[].class)
			return shortOf((short[]) array);
		if (objClass == int[].class)
			return intOf((int[]) array);
		if (objClass == long[].class)
			return longOf((long[]) array);
		if (objClass == float[].class)
			return floatOf((float[]) array);
		if (objClass == double[].class)
			return doubleOf((double[]) array);
		// Invalid number type
		throw new IllegalArgumentException("Invalid numeric array");
	}

	/* ------------------------------------------------------------------
	 * Internal methods
	 * ------------------------------------------------------------------ */

	@Contract(pure = true)
	private static Number @NotNull [] byteOf(byte @NotNull [] numbers) {
		Number[] result = new Number[numbers.length];
		for (int i = 0; i < numbers.length; i++) {
			result[i] = numbers[i];
		}
		return result;
	}

	@Contract(pure = true)
	private static Number @NotNull [] shortOf(short @NotNull [] numbers) {
		Number[] result = new Number[numbers.length];
		for (int i = 0; i < numbers.length; i++) {
			result[i] = numbers[i];
		}
		return result;
	}

	@Contract(pure = true)
	private static Number @NotNull [] intOf(int @NotNull [] numbers) {
		Number[] result = new Number[numbers.length];
		for (int i = 0; i < numbers.length; i++) {
			result[i] = numbers[i];
		}
		return result;
	}

	@Contract(pure = true)
	private static Number @NotNull [] longOf(long @NotNull [] numbers) {
		Number[] result = new Number[numbers.length];
		for (int i = 0; i < numbers.length; i++) {
			result[i] = numbers[i];
		}
		return result;
	}

	@Contract(pure = true)
	private static Number @NotNull [] floatOf(float @NotNull [] numbers) {
		Number[] result = new Number[numbers.length];
		for (int i = 0; i < numbers.length; i++) {
			result[i] = numbers[i];
		}
		return result;
	}

	@Contract(pure = true)
	private static Number @NotNull [] doubleOf(double @NotNull [] numbers) {
		Number[] result = new Number[numbers.length];
		for (int i = 0; i < numbers.length; i++) {
			result[i] = numbers[i];
		}
		return result;
	}

}