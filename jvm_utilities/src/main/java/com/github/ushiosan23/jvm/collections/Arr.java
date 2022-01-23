package com.github.ushiosan23.jvm.collections;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
	 * Generate a number array
	 *
	 * @param elements Array elements
	 * @return Returns an abstract number object array
	 */
	public static Number @NotNull [] ofNumber(Number @NotNull ... elements) {
		return elements;
	}

	/**
	 * Convert any numeric array to number array
	 *
	 * @param array Target array
	 * @return Returns an abstract number object array
	 */
	public static Number @NotNull [] ofNumberExt(Object array) {
		return ArrInternal.convertToNumberArray(array);
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
		return ArrInternal.toString(array);
	}

	/**
	 * Array info string representation
	 *
	 * @param array Array to get representation
	 * @return Array info string representation
	 */
	public static @NotNull String toInfoString(@NotNull Object array) {
		return ArrInternal.toInfoString(array);
	}

	/* ------------------------------------------------------------------
	 * Search methods
	 * ------------------------------------------------------------------ */

	/**
	 * Search element in the array
	 *
	 * @param array  The array to search
	 * @param search Element to search
	 * @return Returns the first index element or {@code -1} if element not exists
	 */
	@Contract(pure = true)
	public static int indexOf(Object @NotNull [] array, @Nullable Object search) {
		for (int i = 0; i < array.length; i++) {
			// Store element
			Object indexObj = array[i];
			if (indexObj == null) {
				if (search == null) return i;
			} else {
				if (indexObj.equals(search)) return i;
			}
		}
		return -1;
	}

	/**
	 * Search element in the array
	 *
	 * @param array  The array to search
	 * @param search Element to search
	 * @return Returns the first index element or {@code -1} if element not exists
	 */
	@Contract(pure = true)
	public static int numberIndexOf(Number @NotNull [] array, @NotNull Number search) {
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(search)) return i;
		}
		return -1;
	}

	/**
	 * Search element in the array
	 *
	 * @param array  The array to search
	 * @param search Element to search
	 * @return Returns the first index element or {@code -1} if element not exists
	 */
	@Contract(pure = true)
	public static int byteIndexOf(byte @NotNull [] array, byte search) {
		Number[] target = Arr.ofNumberExt(array);
		return numberIndexOf(target, search);
	}

	/**
	 * Search element in the array
	 *
	 * @param array  The array to search
	 * @param search Element to search
	 * @return Returns the first index element or {@code -1} if element not exists
	 */
	@Contract(pure = true)
	public static int shortIndexOf(short @NotNull [] array, short search) {
		Number[] target = Arr.ofNumberExt(array);
		return numberIndexOf(target, search);
	}

	/**
	 * Search element in the array
	 *
	 * @param array  The array to search
	 * @param search Element to search
	 * @return Returns the first index element or {@code -1} if element not exists
	 */
	@Contract(pure = true)
	public static int intIndexOf(int @NotNull [] array, int search) {
		Number[] target = Arr.ofNumberExt(array);
		return numberIndexOf(target, search);
	}

	/**
	 * Search element in the array
	 *
	 * @param array  The array to search
	 * @param search Element to search
	 * @return Returns the first index element or {@code -1} if element not exists
	 */
	@Contract(pure = true)
	public static int longIndexOf(long @NotNull [] array, long search) {
		Number[] target = Arr.ofNumberExt(array);
		return numberIndexOf(target, search);
	}

	/**
	 * Search an element in the array. This method make the same process that {@link #indexOf(Object[], Object)} but
	 * inverted.
	 *
	 * @param array  The array to search
	 * @param search Element to search
	 * @return Returns the first index element or {@code -1} if element not exists
	 */
	@Contract(pure = true)
	public static int lastIndexOf(Object @NotNull [] array, @Nullable Object search) {
		for (int i = (array.length - 1); i >= 0; i--) {
			// Store element
			Object indexObj = array[i];
			if (indexObj == null) {
				if (search == null) return i;
			} else {
				if (indexObj.equals(search)) return i;
			}
		}
		return -1;
	}

	/**
	 * Search an element in the array. This method make the same process that {@link #indexOf(Object[], Object)} but
	 * inverted.
	 *
	 * @param array  The array to search
	 * @param search Element to search
	 * @return Returns the first index element or {@code -1} if element not exists
	 */
	@Contract(pure = true)
	public static int lastNumberIndexOf(Number @NotNull [] array, @NotNull Number search) {
		for (int i = (array.length - 1); i >= 0; i--) {
			if (array[i].equals(search)) return i;
		}
		return -1;
	}

	/**
	 * Search an element in the array. This method make the same process that {@link #indexOf(Object[], Object)} but
	 * inverted.
	 *
	 * @param array  The array to search
	 * @param search Element to search
	 * @return Returns the first index element or {@code -1} if element not exists
	 */
	@Contract(pure = true)
	public static int lastByteIndexOf(byte @NotNull [] array, byte search) {
		Number[] target = Arr.ofNumberExt(array);
		return lastNumberIndexOf(target, search);
	}

	/**
	 * Search an element in the array. This method make the same process that {@link #indexOf(Object[], Object)} but
	 * inverted.
	 *
	 * @param array  The array to search
	 * @param search Element to search
	 * @return Returns the first index element or {@code -1} if element not exists
	 */
	@Contract(pure = true)
	public static int lastShortIndexOf(short @NotNull [] array, short search) {
		Number[] target = Arr.ofNumberExt(array);
		return lastNumberIndexOf(target, search);
	}

	/**
	 * Search an element in the array. This method make the same process that {@link #indexOf(Object[], Object)} but
	 * inverted.
	 *
	 * @param array  The array to search
	 * @param search Element to search
	 * @return Returns the first index element or {@code -1} if element not exists
	 */
	@Contract(pure = true)
	public static int lastIntIndexOf(int @NotNull [] array, int search) {
		Number[] target = Arr.ofNumberExt(array);
		return lastNumberIndexOf(target, search);
	}

	/**
	 * Search an element in the array. This method make the same process that {@link #indexOf(Object[], Object)} but
	 * inverted.
	 *
	 * @param array  The array to search
	 * @param search Element to search
	 * @return Returns the first index element or {@code -1} if element not exists
	 */
	@Contract(pure = true)
	public static int lastLongIndexOf(long @NotNull [] array, long search) {
		Number[] target = Arr.ofNumberExt(array);
		return lastNumberIndexOf(target, search);
	}

}
