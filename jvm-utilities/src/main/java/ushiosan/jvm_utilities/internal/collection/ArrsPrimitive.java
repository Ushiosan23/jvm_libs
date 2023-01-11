package ushiosan.jvm_utilities.internal.collection;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ushiosan.jvm_utilities.function.Apply;
import ushiosan.jvm_utilities.lang.collection.Arrs;
import ushiosan.jvm_utilities.lang.collection.elements.Pair;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;

import static ushiosan.jvm_utilities.lang.Obj.cast;

/**
 * Auxiliary class that is in charge of handling primitive arrays
 *
 * @see ArrsGeneric
 */
public abstract class ArrsPrimitive extends ArrsGeneric {
	
	/* ---------------------------------------------------------
	 * Properties
	 * --------------------------------------------------------- */
	
	/**
	 * Map of actions for the conversion of numerical data
	 */
	private static final Pair<Class<?>, Apply.Result<Object, Object[]>>[] NUMBER_CONVERSION_MAP = of(
		Pair.of(byte[].class, ArrsPrimitive::toByteObjectArray),
		Pair.of(short[].class, ArrsPrimitive::toShortObjectArray),
		Pair.of(int[].class, ArrsPrimitive::toIntegerObjectArray),
		Pair.of(long[].class, ArrsPrimitive::toLongObjectArray),
		Pair.of(float[].class, ArrsPrimitive::toFloatObjectArray),
		Pair.of(double[].class, ArrsPrimitive::toDoubleObjectArray)
	);
	
	/**
	 * Map of actions for data conversion
	 */
	protected static final Pair<Class<?>, Apply.Result<Object, Object[]>>[] CONVERSION_MAP = join(
		NUMBER_CONVERSION_MAP, of(
			Pair.of(boolean[].class, ArrsPrimitive::toBooleanObjectArray),
			Pair.of(char[].class, ArrsPrimitive::toCharObjectArray)
		));
	
	/* -----------------------------------------------------
	 * Generator methods
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
	 * Search elements in the array
	 *
	 * @param array   base array to search
	 * @param element the element to search
	 * @return the first index element or {@code -1} if element not exists
	 */
	@Contract(pure = true)
	public static int indexOf(char @NotNull [] array, char element) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == element) return i;
		}
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
	public static int lastIndexOf(char @NotNull [] array, char element) {
		int maxSize = array.length - 1;
		for (int i = maxSize; i >= 0; i--) {
			if (array[i] == element) return i;
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
	public static boolean contains(char @NotNull [] array, char element) {
		return indexOf(array, element) != INDEX_NOT_FOUND;
	}
	
	/**
	 * Returns the last element of the array
	 *
	 * @param array base array to search
	 * @return the last element of the array or {@link Optional#empty()} if array is empty
	 */
	public static @NotNull Optional<Character> lastElement(char @NotNull [] array) {
		if (array.length == 0) return Optional.empty();
		return Optional.of(array[array.length - 1]);
	}
	
	/**
	 * Returns the last element of the array
	 *
	 * @param array base array to search
	 * @return the last element of the array
	 * @throws IllegalStateException if the array is empty
	 */
	public static char unsafeLastElement(char @NotNull [] array) {
		if (array.length == 0) throw new IllegalStateException("The array is empty");
		return array[array.length - 1];
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
		for (int i = 0; i < elements.length; ++i) {
			result[i] = elements[i].byteValue();
		}
		return result;
	}
	
	/**
	 * Search elements in the array
	 *
	 * @param array   base array to search
	 * @param element the element to search
	 * @return the first index element or {@code -1} if element not exists
	 */
	@Contract(pure = true)
	public static int indexOf(byte @NotNull [] array, byte element) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == element) return i;
		}
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
	public static int lastIndexOf(byte @NotNull [] array, byte element) {
		int maxSize = array.length - 1;
		for (int i = maxSize; i >= 0; i--) {
			if (array[i] == element) return i;
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
	public static boolean contains(byte @NotNull [] array, byte element) {
		return indexOf(array, element) != INDEX_NOT_FOUND;
	}
	
	/**
	 * Returns the last element of the array
	 *
	 * @param array base array to search
	 * @return the last element of the array or {@link Optional#empty()} if array is empty
	 */
	public static @NotNull Optional<Byte> lastElement(byte @NotNull [] array) {
		if (array.length == 0) return Optional.empty();
		return Optional.of(array[array.length - 1]);
	}
	
	/**
	 * Returns the last element of the array
	 *
	 * @param array base array to search
	 * @return the last element of the array
	 * @throws IllegalStateException if the array is empty
	 */
	public static byte unsafeLastElement(byte @NotNull [] array) {
		if (array.length == 0) throw new IllegalStateException("The array is empty");
		return array[array.length - 1];
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
		for (int i = 0; i < elements.length; ++i) {
			result[i] = elements[i].shortValue();
		}
		return result;
	}
	
	/**
	 * Search elements in the array
	 *
	 * @param array   base array to search
	 * @param element the element to search
	 * @return the first index element or {@code -1} if element not exists
	 */
	@Contract(pure = true)
	public static int indexOf(short @NotNull [] array, short element) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == element) return i;
		}
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
	public static int lastIndexOf(short @NotNull [] array, short element) {
		int maxSize = array.length - 1;
		for (int i = maxSize; i >= 0; i--) {
			if (array[i] == element) return i;
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
	public static boolean contains(short @NotNull [] array, short element) {
		return indexOf(array, element) != INDEX_NOT_FOUND;
	}
	
	/**
	 * Returns the last element of the array
	 *
	 * @param array base array to search
	 * @return the last element of the array or {@link Optional#empty()} if array is empty
	 */
	public static @NotNull Optional<Short> lastElement(short @NotNull [] array) {
		if (array.length == 0) return Optional.empty();
		return Optional.of(array[array.length - 1]);
	}
	
	/**
	 * Returns the last element of the array
	 *
	 * @param array base array to search
	 * @return the last element of the array
	 * @throws IllegalStateException if the array is empty
	 */
	public static short unsafeLastElement(short @NotNull [] array) {
		if (array.length == 0) throw new IllegalStateException("The array is empty");
		return array[array.length - 1];
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
	 * Search elements in the array
	 *
	 * @param array   base array to search
	 * @param element the element to search
	 * @return the first index element or {@code -1} if element not exists
	 */
	@Contract(pure = true)
	public static int indexOf(int @NotNull [] array, int element) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == element) return i;
		}
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
	public static int lastIndexOf(int @NotNull [] array, int element) {
		int maxSize = array.length - 1;
		for (int i = maxSize; i >= 0; i--) {
			if (array[i] == element) return i;
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
	public static boolean contains(int @NotNull [] array, int element) {
		return indexOf(array, element) != INDEX_NOT_FOUND;
	}
	
	/**
	 * Returns the last element of the array
	 *
	 * @param array base array to search
	 * @return the last element of the array or {@link Optional#empty()} if array is empty
	 */
	public static @NotNull Optional<Integer> lastElement(int @NotNull [] array) {
		if (array.length == 0) return Optional.empty();
		return Optional.of(array[array.length - 1]);
	}
	
	/**
	 * Returns the last element of the array
	 *
	 * @param array base array to search
	 * @return the last element of the array
	 * @throws IllegalStateException if the array is empty
	 */
	public static int unsafeLastElement(int @NotNull [] array) {
		if (array.length == 0) throw new IllegalStateException("The array is empty");
		return array[array.length - 1];
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
	 * Search elements in the array
	 *
	 * @param array   base array to search
	 * @param element the element to search
	 * @return the first index element or {@code -1} if element not exists
	 */
	@Contract(pure = true)
	public static int indexOf(long @NotNull [] array, long element) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == element) return i;
		}
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
	public static int lastIndexOf(long @NotNull [] array, long element) {
		int maxSize = array.length - 1;
		for (int i = maxSize; i >= 0; i--) {
			if (array[i] == element) return i;
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
	public static boolean contains(long @NotNull [] array, long element) {
		return indexOf(array, element) != INDEX_NOT_FOUND;
	}
	
	/**
	 * Returns the last element of the array
	 *
	 * @param array base array to search
	 * @return the last element of the array or {@link Optional#empty()} if array is empty
	 */
	public static @NotNull Optional<Long> lastElement(long @NotNull [] array) {
		if (array.length == 0) return Optional.empty();
		return Optional.of(array[array.length - 1]);
	}
	
	/**
	 * Returns the last element of the array
	 *
	 * @param array base array to search
	 * @return the last element of the array
	 * @throws IllegalStateException if the array is empty
	 */
	public static long unsafeLastElement(long @NotNull [] array) {
		if (array.length == 0) throw new IllegalStateException("The array is empty");
		return array[array.length - 1];
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
	 * Search elements in the array
	 *
	 * @param array   base array to search
	 * @param element the element to search
	 * @return the first index element or {@code -1} if element not exists
	 */
	@Contract(pure = true)
	public static int indexOf(float @NotNull [] array, float element) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == element) return i;
		}
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
	public static int lastIndexOf(float @NotNull [] array, float element) {
		int maxSize = array.length - 1;
		for (int i = maxSize; i >= 0; i--) {
			if (array[i] == element) return i;
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
	public static boolean contains(float @NotNull [] array, float element) {
		return indexOf(array, element) != INDEX_NOT_FOUND;
	}
	
	/**
	 * Returns the last element of the array
	 *
	 * @param array base array to search
	 * @return the last element of the array or {@link Optional#empty()} if array is empty
	 */
	public static @NotNull Optional<Float> lastElement(float @NotNull [] array) {
		if (array.length == 0) return Optional.empty();
		return Optional.of(array[array.length - 1]);
	}
	
	/**
	 * Returns the last element of the array
	 *
	 * @param array base array to search
	 * @return the last element of the array
	 * @throws IllegalStateException if the array is empty
	 */
	public static float unsafeLastElement(float @NotNull [] array) {
		if (array.length == 0) throw new IllegalStateException("The array is empty");
		return array[array.length - 1];
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
	 * Search elements in the array
	 *
	 * @param array   base array to search
	 * @param element the element to search
	 * @return the first index element or {@code -1} if element not exists
	 */
	@Contract(pure = true)
	public static int indexOf(double @NotNull [] array, double element) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == element) return i;
		}
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
	public static int lastIndexOf(double @NotNull [] array, double element) {
		int maxSize = array.length - 1;
		for (int i = maxSize; i >= 0; i--) {
			if (array[i] == element) return i;
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
	public static boolean contains(double @NotNull [] array, double element) {
		return indexOf(array, element) != INDEX_NOT_FOUND;
	}
	
	/**
	 * Returns the last element of the array
	 *
	 * @param array base array to search
	 * @return the last element of the array or {@link Optional#empty()} if array is empty
	 */
	public static @NotNull Optional<Double> lastElement(double @NotNull [] array) {
		if (array.length == 0) return Optional.empty();
		return Optional.of(array[array.length - 1]);
	}
	
	/**
	 * Returns the last element of the array
	 *
	 * @param array base array to search
	 * @return the last element of the array
	 * @throws IllegalStateException if the array is empty
	 */
	public static double unsafeLastElement(double @NotNull [] array) {
		if (array.length == 0) throw new IllegalStateException("The array is empty");
		return array[array.length - 1];
	}
	
	/* ---------------------------------------------------------
	 * Transform char methods
	 * --------------------------------------------------------- */
	
	/**
	 * Converts one array to another but with a different data type.
	 *
	 * @param original the original array that you want to convert
	 * @param mapper   function in charge of transforming each element of the array
	 * @param arrFn    function that generates the required type of array
	 * @param <T>      the original data type
	 * @return the new array with the converted data
	 */
	public static <T> T @NotNull [] transformChar(char[] original, @NotNull Function<Character, T> mapper,
		@NotNull IntFunction<T[]> arrFn) {
		return Arrays.stream(Arrs.toObjectArray(original))
			.map(mapper)
			.toArray(arrFn);
	}
	
	/**
	 * Converts one array to another but with a different data type.
	 *
	 * @param original the original array that you want to convert
	 * @param mapper   function in charge of transforming each element of the array
	 * @param <T>      the original data type
	 * @return the new array with the converted data
	 */
	public static <T> Object @NotNull [] transformChar(char[] original, @NotNull Function<Character, T> mapper) {
		return Arrays.stream(Arrs.toObjectArray(original))
			.map(mapper)
			.toArray();
	}
	
	/* ---------------------------------------------------------
	 * Equality methods
	 * --------------------------------------------------------- */
	
	/**
	 * Verify that the contents of two arrays are the same
	 *
	 * @param ar1 the array to check
	 * @param ar2 the array to check
	 * @return {@code true} if the content is the same or {@code false} if
	 * 	the size is different or the content is different
	 */
	public static boolean contentEquals(char[] ar1, char[] ar2) {
		return contentEquals(Arrs.toObjectArray(ar1), Arrs.toObjectArray(ar2));
	}
	
	/**
	 * Verify that the contents of two arrays are the same
	 *
	 * @param ar1 the array to check
	 * @param ar2 the array to check
	 * @return {@code true} if the content is the same or {@code false} if
	 * 	the size is different or the content is different
	 */
	public static boolean contentEquals(byte[] ar1, byte[] ar2) {
		return contentEquals(Arrs.toObjectArray(ar1), Arrs.toObjectArray(ar2));
	}
	
	/**
	 * Verify that the contents of two arrays are the same
	 *
	 * @param ar1 the array to check
	 * @param ar2 the array to check
	 * @return {@code true} if the content is the same or {@code false} if
	 * 	the size is different or the content is different
	 */
	public static boolean contentEquals(short[] ar1, short[] ar2) {
		return contentEquals(Arrs.toObjectArray(ar1), Arrs.toObjectArray(ar2));
	}
	
	/**
	 * Verify that the contents of two arrays are the same
	 *
	 * @param ar1 the array to check
	 * @param ar2 the array to check
	 * @return {@code true} if the content is the same or {@code false} if
	 * 	the size is different or the content is different
	 */
	public static boolean contentEquals(int[] ar1, int[] ar2) {
		return contentEquals(Arrs.toObjectArray(ar1), Arrs.toObjectArray(ar2));
	}
	
	/**
	 * Verify that the contents of two arrays are the same
	 *
	 * @param ar1 the array to check
	 * @param ar2 the array to check
	 * @return {@code true} if the content is the same or {@code false} if
	 * 	the size is different or the content is different
	 */
	public static boolean contentEquals(long[] ar1, long[] ar2) {
		return contentEquals(Arrs.toObjectArray(ar1), Arrs.toObjectArray(ar2));
	}
	
	/**
	 * Verify that the contents of two arrays are the same
	 *
	 * @param ar1 the array to check
	 * @param ar2 the array to check
	 * @return {@code true} if the content is the same or {@code false} if
	 * 	the size is different or the content is different
	 */
	public static boolean contentEquals(float[] ar1, float[] ar2) {
		return contentEquals(Arrs.toObjectArray(ar1), Arrs.toObjectArray(ar2));
	}
	
	/**
	 * Verify that the contents of two arrays are the same
	 *
	 * @param ar1 the array to check
	 * @param ar2 the array to check
	 * @return {@code true} if the content is the same or {@code false} if
	 * 	the size is different or the content is different
	 */
	public static boolean contentEquals(double[] ar1, double[] ar2) {
		return contentEquals(Arrs.toObjectArray(ar1), Arrs.toObjectArray(ar2));
	}
	
	/* ---------------------------------------------------------
	 * Transform number methods
	 * --------------------------------------------------------- */
	
	/**
	 * Convert any array of primitive numbers to an array of numeric objects
	 *
	 * @param array the array to convert
	 * @return an array with all numeric objects
	 */
	public static Number @NotNull [] toNumberArray(byte[] array) {
		return toNumberArrayImpl(array);
	}
	
	/**
	 * Convert any array of primitive numbers to an array of numeric objects
	 *
	 * @param array the array to convert
	 * @return an array with all numeric objects
	 */
	public static Number @NotNull [] toNumberArray(short[] array) {
		return toNumberArrayImpl(array);
	}
	
	/**
	 * Convert any array of primitive numbers to an array of numeric objects
	 *
	 * @param array the array to convert
	 * @return an array with all numeric objects
	 */
	public static Number @NotNull [] toNumberArray(int[] array) {
		return toNumberArrayImpl(array);
	}
	
	/**
	 * Convert any array of primitive numbers to an array of numeric objects
	 *
	 * @param array the array to convert
	 * @return an array with all numeric objects
	 */
	public static Number @NotNull [] toNumberArray(long[] array) {
		return toNumberArrayImpl(array);
	}
	
	/**
	 * Convert any array of primitive numbers to an array of numeric objects
	 *
	 * @param array the array to convert
	 * @return an array with all numeric objects
	 */
	public static Number @NotNull [] toNumberArray(float[] array) {
		return toNumberArrayImpl(array);
	}
	
	/**
	 * Convert any array of primitive numbers to an array of numeric objects
	 *
	 * @param array the array to convert
	 * @return an array with all numeric objects
	 */
	public static Number @NotNull [] toNumberArray(double[] array) {
		return toNumberArrayImpl(array);
	}
	
	/* ---------------------------------------------------------
	 * Transform byte methods
	 * --------------------------------------------------------- */
	
	/**
	 * Converts one array to another but with a different data type.
	 *
	 * @param original the original array that you want to convert
	 * @param mapper   function in charge of transforming each element of the array
	 * @param arrFn    function that generates the required type of array
	 * @param <T>      the original data type
	 * @return the new array with the converted data
	 */
	public static <T> T @NotNull [] transformByte(byte[] original, @NotNull Function<Byte, T> mapper,
		@NotNull IntFunction<T[]> arrFn) {
		return Arrays.stream(Arrs.toObjectArray(original))
			.map(mapper)
			.toArray(arrFn);
	}
	
	/**
	 * Converts one array to another but with a different data type.
	 *
	 * @param original the original array that you want to convert
	 * @param mapper   function in charge of transforming each element of the array
	 * @param <T>      the original data type
	 * @return the new array with the converted data
	 */
	public static <T> Object @NotNull [] transformByte(byte[] original, @NotNull Function<Byte, T> mapper) {
		return Arrays.stream(Arrs.toObjectArray(original))
			.map(mapper)
			.toArray();
	}
	
	/* ---------------------------------------------------------
	 * Transform short methods
	 * --------------------------------------------------------- */
	
	/**
	 * Converts one array to another but with a different data type.
	 *
	 * @param original the original array that you want to convert
	 * @param mapper   function in charge of transforming each element of the array
	 * @param arrFn    function that generates the required type of array
	 * @param <T>      the original data type
	 * @return the new array with the converted data
	 */
	public static <T> T @NotNull [] transformShort(short[] original, @NotNull Function<Short, T> mapper,
		@NotNull IntFunction<T[]> arrFn) {
		return Arrays.stream(Arrs.toObjectArray(original))
			.map(mapper)
			.toArray(arrFn);
	}
	
	/**
	 * Converts one array to another but with a different data type.
	 *
	 * @param original the original array that you want to convert
	 * @param mapper   function in charge of transforming each element of the array
	 * @param <T>      the original data type
	 * @return the new array with the converted data
	 */
	public static <T> Object @NotNull [] transformShort(short[] original, @NotNull Function<Short, T> mapper) {
		return Arrays.stream(Arrs.toObjectArray(original))
			.map(mapper)
			.toArray();
	}
	
	/* ---------------------------------------------------------
	 * Transform int methods
	 * --------------------------------------------------------- */
	
	/**
	 * Converts one array to another but with a different data type.
	 *
	 * @param original the original array that you want to convert
	 * @param mapper   function in charge of transforming each element of the array
	 * @param arrFn    function that generates the required type of array
	 * @param <T>      the original data type
	 * @return the new array with the converted data
	 */
	public static <T> T @NotNull [] transformInt(int[] original, @NotNull Function<Integer, T> mapper,
		@NotNull IntFunction<T[]> arrFn) {
		return Arrays.stream(Arrs.toObjectArray(original))
			.map(mapper)
			.toArray(arrFn);
	}
	
	/**
	 * Converts one array to another but with a different data type.
	 *
	 * @param original the original array that you want to convert
	 * @param mapper   function in charge of transforming each element of the array
	 * @param <T>      the original data type
	 * @return the new array with the converted data
	 */
	public static <T> Object @NotNull [] transformInt(int[] original, @NotNull Function<Integer, T> mapper) {
		return Arrays.stream(Arrs.toObjectArray(original))
			.map(mapper)
			.toArray();
	}
	
	/* ---------------------------------------------------------
	 * Transform long methods
	 * --------------------------------------------------------- */
	
	/**
	 * Converts one array to another but with a different data type.
	 *
	 * @param original the original array that you want to convert
	 * @param mapper   function in charge of transforming each element of the array
	 * @param arrFn    function that generates the required type of array
	 * @param <T>      the original data type
	 * @return the new array with the converted data
	 */
	public static <T> T @NotNull [] transformLong(long[] original, @NotNull Function<Long, T> mapper,
		@NotNull IntFunction<T[]> arrFn) {
		return Arrays.stream(Arrs.toObjectArray(original))
			.map(mapper)
			.toArray(arrFn);
	}
	
	/**
	 * Converts one array to another but with a different data type.
	 *
	 * @param original the original array that you want to convert
	 * @param mapper   function in charge of transforming each element of the array
	 * @param <T>      the original data type
	 * @return the new array with the converted data
	 */
	public static <T> Object @NotNull [] transformLong(long[] original, @NotNull Function<Long, T> mapper) {
		return Arrays.stream(Arrs.toObjectArray(original))
			.map(mapper)
			.toArray();
	}
	
	/* ---------------------------------------------------------
	 * Transform float methods
	 * --------------------------------------------------------- */
	
	/**
	 * Converts one array to another but with a different data type.
	 *
	 * @param original the original array that you want to convert
	 * @param mapper   function in charge of transforming each element of the array
	 * @param arrFn    function that generates the required type of array
	 * @param <T>      the original data type
	 * @return the new array with the converted data
	 */
	public static <T> T @NotNull [] transformFloat(float[] original, @NotNull Function<Float, T> mapper,
		@NotNull IntFunction<T[]> arrFn) {
		return Arrays.stream(Arrs.toObjectArray(original))
			.map(mapper)
			.toArray(arrFn);
	}
	
	/**
	 * Converts one array to another but with a different data type.
	 *
	 * @param original the original array that you want to convert
	 * @param mapper   function in charge of transforming each element of the array
	 * @param <T>      the original data type
	 * @return the new array with the converted data
	 */
	public static <T> Object @NotNull [] transformFloat(float[] original, @NotNull Function<Float, T> mapper) {
		return Arrays.stream(Arrs.toObjectArray(original))
			.map(mapper)
			.toArray();
	}
	
	/* ---------------------------------------------------------
	 * Transform double methods
	 * --------------------------------------------------------- */
	
	/**
	 * Converts one array to another but with a different data type.
	 *
	 * @param original the original array that you want to convert
	 * @param mapper   function in charge of transforming each element of the array
	 * @param arrFn    function that generates the required type of array
	 * @param <T>      the original data type
	 * @return the new array with the converted data
	 */
	public static <T> T @NotNull [] transformDouble(double[] original, @NotNull Function<Double, T> mapper,
		@NotNull IntFunction<T[]> arrFn) {
		return Arrays.stream(Arrs.toObjectArray(original))
			.map(mapper)
			.toArray(arrFn);
	}
	
	/**
	 * Converts one array to another but with a different data type.
	 *
	 * @param original the original array that you want to convert
	 * @param mapper   function in charge of transforming each element of the array
	 * @param <T>      the original data type
	 * @return the new array with the converted data
	 */
	public static <T> Object @NotNull [] transformDouble(double[] original, @NotNull Function<Double, T> mapper) {
		return Arrays.stream(Arrs.toObjectArray(original))
			.map(mapper)
			.toArray();
	}
	
	/* ---------------------------------------------------------
	 * Internal methods
	 * --------------------------------------------------------- */
	
	/**
	 * Convert any array to object array. This also applies to primitive types.
	 * <p>
	 * Examples
	 * <pre>{@code
	 * // For numeric primitives always use Number class
	 * Integer[] intArr = (Integer[]) ArraysImpl.toObjectArray(new int[] {2, 4, 6, 8});
	 * Short[] shortArr = (Short[]) ArraysImpl.toObjectArray(new short[] {12, 21, 42, 120, 0xFF});
	 * Float[] floatArr = (Float[]) ArraysImpl.toObjectArray(new float[] {1f, 2f, 3f, 120.2234f});
	 * // Wrapped clases
	 * Boolean[] boolArr = (Boolean[]) ArraysImpl.toObjectArray(new boolean[] {true, false, false, true});
	 * Character[] intArr = (Character[]) ArraysImpl.toObjectArray(new char[] {'H', 'e', 'l', 'l', 'o'});
	 * }</pre>
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	@Contract(pure = true)
	private static Object @NotNull [] toBooleanObjectArray(@NotNull Object array) {
		boolean[] arr = cast(array);
		Boolean[] result = new Boolean[arr.length];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}
	
	/**
	 * Convert any array to object array. This also applies to primitive types.
	 * <p>
	 * Examples
	 * <pre>{@code
	 * // For numeric primitives always use Number class
	 * Integer[] intArr = (Integer[]) ArraysImpl.toObjectArray(new int[] {2, 4, 6, 8});
	 * Short[] shortArr = (Short[]) ArraysImpl.toObjectArray(new short[] {12, 21, 42, 120, 0xFF});
	 * Float[] floatArr = (Float[]) ArraysImpl.toObjectArray(new float[] {1f, 2f, 3f, 120.2234f});
	 * // Wrapped clases
	 * Boolean[] boolArr = (Boolean[]) ArraysImpl.toObjectArray(new boolean[] {true, false, false, true});
	 * Character[] intArr = (Character[]) ArraysImpl.toObjectArray(new char[] {'H', 'e', 'l', 'l', 'o'});
	 * }</pre>
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	@Contract(pure = true)
	private static Object @NotNull [] toCharObjectArray(@NotNull Object array) {
		char[] arr = cast(array);
		Character[] result = new Character[arr.length];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}
	
	/**
	 * Convert any array to object array. This also applies to primitive types.
	 * <p>
	 * Examples
	 * <pre>{@code
	 * // For numeric primitives always use Number class
	 * Integer[] intArr = (Integer[]) ArraysImpl.toObjectArray(new int[] {2, 4, 6, 8});
	 * Short[] shortArr = (Short[]) ArraysImpl.toObjectArray(new short[] {12, 21, 42, 120, 0xFF});
	 * Float[] floatArr = (Float[]) ArraysImpl.toObjectArray(new float[] {1f, 2f, 3f, 120.2234f});
	 * // Wrapped clases
	 * Boolean[] boolArr = (Boolean[]) ArraysImpl.toObjectArray(new boolean[] {true, false, false, true});
	 * Character[] intArr = (Character[]) ArraysImpl.toObjectArray(new char[] {'H', 'e', 'l', 'l', 'o'});
	 * }</pre>
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	@Contract(pure = true)
	private static Object @NotNull [] toByteObjectArray(@NotNull Object array) {
		byte[] arr = cast(array);
		Byte[] result = new Byte[arr.length];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}
	
	/**
	 * Convert any array to object array. This also applies to primitive types.
	 * <p>
	 * Examples
	 * <pre>{@code
	 * // For numeric primitives always use Number class
	 * Integer[] intArr = (Integer[]) ArraysImpl.toObjectArray(new int[] {2, 4, 6, 8});
	 * Short[] shortArr = (Short[]) ArraysImpl.toObjectArray(new short[] {12, 21, 42, 120, 0xFF});
	 * Float[] floatArr = (Float[]) ArraysImpl.toObjectArray(new float[] {1f, 2f, 3f, 120.2234f});
	 * // Wrapped clases
	 * Boolean[] boolArr = (Boolean[]) ArraysImpl.toObjectArray(new boolean[] {true, false, false, true});
	 * Character[] intArr = (Character[]) ArraysImpl.toObjectArray(new char[] {'H', 'e', 'l', 'l', 'o'});
	 * }</pre>
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	@Contract(pure = true)
	private static Object @NotNull [] toShortObjectArray(@NotNull Object array) {
		short[] arr = cast(array);
		Short[] result = new Short[arr.length];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}
	
	/**
	 * Convert any array to object array. This also applies to primitive types.
	 * <p>
	 * Examples
	 * <pre>{@code
	 * // For numeric primitives always use Number class
	 * Integer[] intArr = (Integer[]) ArraysImpl.toObjectArray(new int[] {2, 4, 6, 8});
	 * Short[] shortArr = (Short[]) ArraysImpl.toObjectArray(new short[] {12, 21, 42, 120, 0xFF});
	 * Float[] floatArr = (Float[]) ArraysImpl.toObjectArray(new float[] {1f, 2f, 3f, 120.2234f});
	 * // Wrapped clases
	 * Boolean[] boolArr = (Boolean[]) ArraysImpl.toObjectArray(new boolean[] {true, false, false, true});
	 * Character[] intArr = (Character[]) ArraysImpl.toObjectArray(new char[] {'H', 'e', 'l', 'l', 'o'});
	 * }</pre>
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	@Contract(pure = true)
	private static Object @NotNull [] toIntegerObjectArray(@NotNull Object array) {
		int[] arr = cast(array);
		Integer[] result = new Integer[arr.length];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}
	
	/**
	 * Convert any array to object array. This also applies to primitive types.
	 * <p>
	 * Examples
	 * <pre>{@code
	 * // For numeric primitives always use Number class
	 * Integer[] intArr = (Integer[]) ArraysImpl.toObjectArray(new int[] {2, 4, 6, 8});
	 * Short[] shortArr = (Short[]) ArraysImpl.toObjectArray(new short[] {12, 21, 42, 120, 0xFF});
	 * Float[] floatArr = (Float[]) ArraysImpl.toObjectArray(new float[] {1f, 2f, 3f, 120.2234f});
	 * // Wrapped clases
	 * Boolean[] boolArr = (Boolean[]) ArraysImpl.toObjectArray(new boolean[] {true, false, false, true});
	 * Character[] intArr = (Character[]) ArraysImpl.toObjectArray(new char[] {'H', 'e', 'l', 'l', 'o'});
	 * }</pre>
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	@Contract(pure = true)
	private static Object @NotNull [] toLongObjectArray(@NotNull Object array) {
		long[] arr = cast(array);
		Long[] result = new Long[arr.length];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}
	
	/**
	 * Convert any array to object array. This also applies to primitive types.
	 * <p>
	 * Examples
	 * <pre>{@code
	 * // For numeric primitives always use Number class
	 * Integer[] intArr = (Integer[]) ArraysImpl.toObjectArray(new int[] {2, 4, 6, 8});
	 * Short[] shortArr = (Short[]) ArraysImpl.toObjectArray(new short[] {12, 21, 42, 120, 0xFF});
	 * Float[] floatArr = (Float[]) ArraysImpl.toObjectArray(new float[] {1f, 2f, 3f, 120.2234f});
	 * // Wrapped clases
	 * Boolean[] boolArr = (Boolean[]) ArraysImpl.toObjectArray(new boolean[] {true, false, false, true});
	 * Character[] intArr = (Character[]) ArraysImpl.toObjectArray(new char[] {'H', 'e', 'l', 'l', 'o'});
	 * }</pre>
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	@Contract(pure = true)
	private static Object @NotNull [] toFloatObjectArray(@NotNull Object array) {
		float[] arr = cast(array);
		Float[] result = new Float[arr.length];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}
	
	/**
	 * Convert any array to object array. This also applies to primitive types.
	 * <p>
	 * Examples
	 * <pre>{@code
	 * // For numeric primitives always use Number class
	 * Integer[] intArr = (Integer[]) ArraysImpl.toObjectArray(new int[] {2, 4, 6, 8});
	 * Short[] shortArr = (Short[]) ArraysImpl.toObjectArray(new short[] {12, 21, 42, 120, 0xFF});
	 * Float[] floatArr = (Float[]) ArraysImpl.toObjectArray(new float[] {1f, 2f, 3f, 120.2234f});
	 * // Wrapped clases
	 * Boolean[] boolArr = (Boolean[]) ArraysImpl.toObjectArray(new boolean[] {true, false, false, true});
	 * Character[] intArr = (Character[]) ArraysImpl.toObjectArray(new char[] {'H', 'e', 'l', 'l', 'o'});
	 * }</pre>
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	@Contract(pure = true)
	private static Object @NotNull [] toDoubleObjectArray(@NotNull Object array) {
		double[] arr = cast(array);
		Double[] result = new Double[arr.length];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}
	
	/**
	 * Convert any array of primitive numbers to an array of numeric objects
	 *
	 * @param array the array to convert
	 * @return an array with all numeric objects
	 * @throws IllegalArgumentException Error if the array is not valid
	 */
	private static Number @NotNull [] toNumberArrayImpl(@NotNull Object array) {
		// Validate array content
		Class<?> cls = array.getClass();
		if (!cls.isArray()) throw new IllegalArgumentException("Invalid array type.");
		
		// Check if array is a primitive type
		for (var item : NUMBER_CONVERSION_MAP) {
			if (cls == item.first) return cast(item.second.apply(array));
		}
		throw new IllegalArgumentException("array is not a valid numeric type");
	}
	
}
