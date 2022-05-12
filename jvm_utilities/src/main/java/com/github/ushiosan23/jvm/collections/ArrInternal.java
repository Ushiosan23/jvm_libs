package com.github.ushiosan23.jvm.collections;

import com.github.ushiosan23.jvm.io.PrintUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
	public static @NotNull String toString(@Nullable Object array) {
		if (array == null) return "null";
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

		return toStringImpl((Object[]) array);
	}

	/**
	 * Convert any array to info string representation
	 *
	 * @param array Target array to convert
	 * @return Returns an array info string representation
	 * @throws IllegalArgumentException Error if {@code array} is not a valid array type
	 */
	@SuppressWarnings("ConstantConditions")
	public static @NotNull String toInfoString(@Nullable Object array) {
		if (array == null) return "null";
		// Check object class
		if (!array.getClass().isArray())
			throw new IllegalArgumentException(String.format("Invalid array type. %s given.", array.getClass()));
		// Check array type
		Class<?> objClass = array.getClass();
		String name = "";
		int size = 0;
		// Check all kind of arrays
		if (objClass == boolean[].class) {
			name = "boolean";
			size = ((boolean[]) array).length;
		}
		if (objClass == char[].class) {
			name = "char";
			size = ((char[]) array).length;
		}
		if (objClass == byte[].class) {
			name = "byte";
			size = ((byte[]) array).length;
		}
		if (objClass == short[].class) {
			name = "short";
			size = ((short[]) array).length;
		}
		if (objClass == int[].class) {
			name = "int";
			size = ((int[]) array).length;
		}
		if (objClass == long[].class) {
			name = "long";
			size = ((long[]) array).length;
		}
		if (objClass == float[].class) {
			name = "float";
			size = ((float[]) array).length;
		}
		if (objClass == double[].class) {
			name = "double";
			size = ((double[]) array).length;
		}
		if (name.isBlank()) {
			name = objClass.getCanonicalName();
			if (name.endsWith("[]"))
				name = name.substring(0, name.length() - 2);
			size = ((Object[]) array).length;
		}
		return name + "[" + size + "]";
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

	private static @NotNull String toStringImpl(Object @NotNull [] array) {
		// Check empty array
		if (array.length == 0) return "[]";
		// Generate result
		StringBuilder builder = new StringBuilder("[");
		int max = array.length - 1;

		for (int i = 0; i < array.length; i++) {
			builder.append(PrintUtils.toString(array[i]));
			if (i != max) builder.append(", ");
		}

		return builder.append("]").toString();
	}

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
