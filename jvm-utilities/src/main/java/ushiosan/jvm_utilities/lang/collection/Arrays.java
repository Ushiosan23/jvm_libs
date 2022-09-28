package ushiosan.jvm_utilities.lang.collection;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

import ushiosan.jvm_utilities.internal.collection.ArraysImpl;

public final class Arrays {

	/**
	 * number to represent not found operation in the arrays
	 */
	public static final int INDEX_NOT_FOUND = -1;

	/**
	 * This class cannot be instantiated.
	 * <p>
	 * Singleton or utility class mode.
	 */
	private Arrays() {
	}

	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */

	/**
	 * Generate array from given values. If you want to use primitive arrays,
	 * you must use the classes that encapsulate those types.
	 *
	 * @param elements the elements to add
	 * @param <T>      element types
	 * @return an array with all values
	 * @see Boolean
	 * @see Byte
	 * @see Character
	 * @see Double
	 * @see Float
	 * @see Integer
	 * @see Long
	 * @see Short
	 */
	@SafeVarargs
	public static <T> T[] of(T... elements) {
		return elements;
	}

	/**
	 * Generate array from given values. If you want to use primitive arrays,
	 * you must use the classes that encapsulate those types.
	 *
	 * @param iterator the iterator to convert
	 * @return an array with all values
	 * @see Boolean
	 * @see Byte
	 * @see Character
	 * @see Double
	 * @see Float
	 * @see Integer
	 * @see Long
	 * @see Short
	 */
	public static Object @NotNull [] of(Iterator<?> iterator) {
		List<?> iteratorList = Collections.listOf(iterator);
		return iteratorList.toArray();
	}

	/**
	 * Search elements in the array
	 *
	 * @param array   base array to search
	 * @param element the element to search
	 * @return the first index element or {@code -1} if element not exists
	 */
	@Contract(pure = true)
	public static int indexOf(Object @NotNull [] array, @Nullable Object element) {
		for (int i = 0; i < array.length; i++) {
			Object it = array[i];
			// Check if the selected element exists
			if (element == null && it == null)
				return i;
			if (it != null && it.equals(element))
				return i;
		}
		// By default, returns -1
		return INDEX_NOT_FOUND;
	}

	/**
	 * Search an element in the array. This method tries to search in reverse,
	 * first starting at the end of the array, to find the desired element.
	 *
	 * @param array   base array to search
	 * @param element the element to search
	 * @return the first index element or {@code -1} if element not exists
	 */
	@Contract(pure = true)
	public static int lastIndexOf(Object @NotNull [] array, @Nullable Object element) {
		int maxSize = array.length - 1;
		for (int i = maxSize; i >= 0; i--) {
			Object it = array[i];
			// Check if the selected element exists
			if (element == null && it == null)
				return i;
			if (it != null && it.equals(element))
				return i;
		}
		// By default, returns -1
		return INDEX_NOT_FOUND;
	}

	/**
	 * Checks if the array contains the selected element.
	 *
	 * @param array   base array to search
	 * @param element the element to search
	 * @return {@code true} if the element exists or {@code false} otherwise
	 */
	public static boolean contains(Object @NotNull [] array, @Nullable Object element) {
		return indexOf(array, element) != INDEX_NOT_FOUND;
	}

	/**
	 * Convert any array to object array. This also applies to primitive types.
	 * <p>
	 * Be careful with this method because this method uses a trick to convert primitive
	 * types to numeric types and does not use wrapper classes. Instead, the {@link Number}
	 * class is used for any numeric type (except for the char type).
	 * <p>
	 * Examples
	 * <pre>{@code
	 * // For numeric primitives always use Number class
	 * Number[] intArr   = (Number[]) Arrays.toObjectArray(new int[] {2, 4, 6, 8});
	 * Number[] shortArr = (Number[]) Arrays.toObjectArray(new short[] {12, 21, 42, 120, 0xFF});
	 * Number[] floatArr = (Number[]) Arrays.toObjectArray(new float[] {1f, 2f, 3f, 120.2234f});
	 * // Wrapped clases
	 * Boolean[] boolArr  = (Boolean[]) Arrays.toObjectArray(new boolean[] {true, false, false, true});
	 * Character[] intArr = (Character[]) Arrays.toObjectArray(new char[] {'H', 'e', 'l', 'l', 'o'});
	 * }</pre>
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	public static Object @NotNull [] toObjectArray(Object array) {
		return ArraysImpl.toObjectArray(array);
	}

	/* -----------------------------------------------------
	 * Primitive methods
	 * ----------------------------------------------------- */

	/**
	 * Generates a generic array with numeric objects.
	 *
	 * @param elements numeric elements
	 * @return an array with all numeric elements
	 */
	public static Number @NotNull [] numberOf(Number @NotNull ... elements) {
		return elements;
	}

	/**
	 * Generate a primitive boolean array
	 *
	 * @param elements boolean elements
	 * @return an array with all boolean elements
	 */
	public static boolean[] booleanOf(boolean... elements) {
		return elements;
	}

	/**
	 * Generate a primitive char array
	 *
	 * @param elements char elements
	 * @return an array with all char elements
	 */
	public static char[] charOf(char... elements) {
		return elements;
	}

	/**
	 * Generate a primitive byte array
	 *
	 * @param elements byte elements
	 * @return an array with all bytes
	 */
	public static byte @NotNull [] byteOf(Number @NotNull ... elements) {
		// Temporal variables
		byte[] result = new byte[elements.length];

		// Copy elements
		for (int i = 0; i < elements.length; ++i)
			result[i] = elements[i].byteValue();
		return result;
	}

	/**
	 * Convert the primitive byte array to a numeric array
	 *
	 * @param array the array to convert
	 * @return an array with all numeric objects
	 * @see Number
	 */
	public static Number @NotNull [] toNumberOf(byte[] array) {
		return ArraysImpl.toNumberArray(array);
	}

	/**
	 * Generate a primitive short array
	 *
	 * @param elements short elements
	 * @return an array with all shot elements
	 */
	public static short @NotNull [] shortOf(Number @NotNull ... elements) {
		// Temporal variables
		short[] result = new short[elements.length];

		// Copy elements
		for (int i = 0; i < elements.length; ++i)
			result[i] = elements[i].shortValue();
		return result;
	}

	/**
	 * Convert the primitive short array to a numeric array
	 *
	 * @param array the array to convert
	 * @return an array with all numeric objects
	 * @see Number
	 */
	public static Number @NotNull [] toNumberOf(short[] array) {
		return ArraysImpl.toNumberArray(array);
	}

	/**
	 * Generate a primitive int array
	 *
	 * @param elements int elements
	 * @return an array with all int elements
	 */
	public static int[] intOf(int... elements) {
		return elements;
	}

	/**
	 * Convert the primitive int array to a numeric array
	 *
	 * @param array the array to convert
	 * @return an array with all numeric objects
	 * @see Number
	 */
	public static Number @NotNull [] toNumberOf(int[] array) {
		return ArraysImpl.toNumberArray(array);
	}

	/**
	 * Generate a primitive long array
	 *
	 * @param elements long elements
	 * @return an array with all long elements
	 */
	public static long[] longOf(long... elements) {
		return elements;
	}

	/**
	 * Convert the primitive long array to a numeric array
	 *
	 * @param array the array to convert
	 * @return an array with all numeric objects
	 * @see Number
	 */
	public static Number @NotNull [] toNumberOf(long[] array) {
		return ArraysImpl.toNumberArray(array);
	}

	/**
	 * Generate a primitive float array
	 *
	 * @param elements float elements
	 * @return an array with all float elements
	 */
	public static float[] floatOf(float... elements) {
		return elements;
	}

	/**
	 * Convert the primitive float array to a numeric array
	 *
	 * @param array the array to convert
	 * @return an array with all numeric objects
	 * @see Number
	 */
	public static Number @NotNull [] toNumberOf(float[] array) {
		return ArraysImpl.toNumberArray(array);
	}

	/**
	 * Generate a primitive double array
	 *
	 * @param elements double elements
	 * @return an array with double bytes
	 */
	public static double[] doubleOf(double... elements) {
		return elements;
	}

	/**
	 * Convert the primitive double array to a numeric array
	 *
	 * @param array the array to convert
	 * @return an array with all numeric objects
	 * @see Number
	 */
	public static Number @NotNull [] toNumberOf(double[] array) {
		return ArraysImpl.toNumberArray(array);
	}

}
