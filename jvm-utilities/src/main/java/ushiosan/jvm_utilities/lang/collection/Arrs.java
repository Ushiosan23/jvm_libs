package ushiosan.jvm_utilities.lang.collection;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ushiosan.jvm_utilities.internal.collection.ArrsImpl;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public final class Arrs {
	
	/**
	 * number to represent not found operation in the arrays
	 */
	public static final int INDEX_NOT_FOUND = -1;
	
	/**
	 * This class cannot be instantiated.
	 * <p>
	 * Singleton or utility class mode.
	 */
	private Arrs() {
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
			if (element == null && it == null) {
				return i;
			}
			if (it != null && it.equals(element)) {
				return i;
			}
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
			if (element == null && it == null) {
				return i;
			}
			if (it != null && it.equals(element)) {
				return i;
			}
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
	 * Returns the last element of the array
	 *
	 * @param array base array to search
	 * @param <T>   generic array type
	 * @return the last element of the array or {@link Optional#empty()} if array is empty
	 */
	public static <T> @NotNull Optional<T> lastElement(T @NotNull [] array) {
		return Optional.ofNullable(unsafeLastElement(array));
	}
	
	/**
	 * Returns the last element of the array
	 *
	 * @param array base array to search
	 * @param <T>   generic array type
	 * @return the last element of the array or {@code null} if array is empty
	 */
	public static <T> @Nullable T unsafeLastElement(T @NotNull [] array) {
		if (array.length == 0) return null;
		return array[array.length - 1];
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
		return ArrsImpl.toObjectArray(array);
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
	 * Convert the primitive byte array to a numeric array
	 *
	 * @param array the array to convert
	 * @return an array with all numeric objects
	 * @see Number
	 */
	public static Number @NotNull [] toNumberOf(byte[] array) {
		return ArrsImpl.toNumberArray(array);
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
	 * Convert the primitive short array to a numeric array
	 *
	 * @param array the array to convert
	 * @return an array with all numeric objects
	 * @see Number
	 */
	public static Number @NotNull [] toNumberOf(short[] array) {
		return ArrsImpl.toNumberArray(array);
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
	 * Convert the primitive int array to a numeric array
	 *
	 * @param array the array to convert
	 * @return an array with all numeric objects
	 * @see Number
	 */
	public static Number @NotNull [] toNumberOf(int[] array) {
		return ArrsImpl.toNumberArray(array);
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
	 * Convert the primitive long array to a numeric array
	 *
	 * @param array the array to convert
	 * @return an array with all numeric objects
	 * @see Number
	 */
	public static Number @NotNull [] toNumberOf(long[] array) {
		return ArrsImpl.toNumberArray(array);
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
	 * Convert the primitive float array to a numeric array
	 *
	 * @param array the array to convert
	 * @return an array with all numeric objects
	 * @see Number
	 */
	public static Number @NotNull [] toNumberOf(float[] array) {
		return ArrsImpl.toNumberArray(array);
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
	 * Convert the primitive double array to a numeric array
	 *
	 * @param array the array to convert
	 * @return an array with all numeric objects
	 * @see Number
	 */
	public static Number @NotNull [] toNumberOf(double[] array) {
		return ArrsImpl.toNumberArray(array);
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
	
}
