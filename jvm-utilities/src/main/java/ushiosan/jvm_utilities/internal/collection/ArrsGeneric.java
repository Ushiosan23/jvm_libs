package ushiosan.jvm_utilities.internal.collection;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ushiosan.jvm_utilities.lang.Cls;
import ushiosan.jvm_utilities.lang.collection.Collections;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;

import static ushiosan.jvm_utilities.lang.Obj.cast;

/**
 * Base class used for manipulating arrays of type object.
 * This means that it has no methods for manipulating primitive types.
 *
 * @see ArrsPrimitive
 * @see IArrsConstants
 */
public abstract class ArrsGeneric implements IArrsConstants {
	
	/* -----------------------------------------------------
	 * Generator methods
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
	
	/* ---------------------------------------------------------
	 * Equality methods
	 * --------------------------------------------------------- */
	
	/**
	 * Verify that the contents of two arrays are the same
	 *
	 * @param ar1 the array to check
	 * @param ar2 the array to check
	 * @param <T> the data type of each array
	 * @return {@code true} if the content is the same or {@code false} if
	 * 	the size is different or the content is different
	 */
	public static <T> boolean contentEquals(T @NotNull [] ar1, T @NotNull [] ar2) {
		boolean result = ar1.length == ar2.length;
		if (!result) return false;
		
		// Iterate all elements
		for (int i = 0; i < ar1.length; i++) {
			T v1 = ar1[i];
			T v2 = ar2[i];
			// Check nullable elements
			if (v1 == null && v2 == null) continue;
			
			// Validate elements
			if (v1 != v2) {
				result = false;
				break;
			}
		}
		return result;
	}
	
	/* ---------------------------------------------------------
	 * Transform methods
	 * --------------------------------------------------------- */
	
	/**
	 * Converts one array to another but with a different data type.
	 *
	 * @param original the original array that you want to convert
	 * @param mapper   function in charge of transforming each element of the array
	 * @param arrFn    function that generates the required type of array
	 * @param <T>      the original data type
	 * @param <V>      the target data type
	 * @return the new array with the converted data
	 */
	public static <T, V> V @NotNull [] transform(@NotNull T[] original, @NotNull Function<T, V> mapper,
		@NotNull IntFunction<V[]> arrFn) {
		return Arrays.stream(original)
			.map(mapper)
			.toArray(arrFn);
	}
	
	/**
	 * Converts one array to another but with a different data type.
	 *
	 * @param original the original array that you want to convert
	 * @param mapper   function in charge of transforming each element of the array
	 * @param <T>      the original data type
	 * @param <V>      the target data type
	 * @return the new array with the converted data
	 */
	public static <T, V> Object @NotNull [] transform(@NotNull T[] original, @NotNull Function<T, V> mapper) {
		return Arrays.stream(original)
			.map(mapper)
			.toArray();
	}
	
	/* ---------------------------------------------------------
	 * Join methods
	 * --------------------------------------------------------- */
	
	/**
	 * Merge two arrays into one
	 *
	 * @param a1  array you want to combine
	 * @param a2  array you want to combine
	 * @param <T> the type of array that the method will generate
	 * @return a new array with all the content of the two arrays passed as parameter
	 */
	public static <T> T @NotNull [] join(@NotNull T[] a1, @NotNull T[] a2) {
		// Get result class
		Class<T> cls = Cls.getArrayIndividualClass(a1);
		return join(cls, a1, a2);
	}
	
	/**
	 * Merge two arrays into one
	 *
	 * @param cls the type of data to be processed
	 * @param a1  array you want to combine
	 * @param a2  array you want to combine
	 * @param <T> the type of array that the method will generate
	 * @return a new array with all the content of the two arrays passed as parameter
	 */
	public static <T> T @NotNull [] join(@NotNull Class<T> cls, @NotNull T[] a1, @NotNull T[] a2) {
		return joinAll(cls, a1, a2);
	}
	
	/**
	 * Combine multiple arrays into one
	 *
	 * @param cls    the type of data to be processed
	 * @param arrays all the arrays you want to combine
	 * @param <T>    the type of array that the method will generate
	 * @return a new array with all the content of the arrays passed as parameter
	 */
	@SafeVarargs
	public static <T> T @NotNull [] joinAll(@NotNull Class<T> cls, T @NotNull []... arrays) {
		int totalSize = calculateTotalSize(arrays);
		int position = 0;
		// Generate new instance of array
		T[] result = cast(Array.newInstance(cls, totalSize));
		
		// Copy all elements
		for (T[] array : arrays) {
			System.arraycopy(array, 0, result, position, array.length);
			position += array.length;
		}
		
		return result;
	}
	
	/**
	 * Combine multiple arrays into one
	 *
	 * @param arrays all the arrays you want to combine
	 * @param <T>    the type of array that the method will generate
	 * @return a new array with all the content of the arrays passed as parameter
	 */
	@SafeVarargs
	public static <T> T @NotNull [] joinAll(T @NotNull []... arrays) {
		Class<T> cls = Cls.getArrayIndividualClass(arrays);
		return joinAll(cls, arrays);
	}
	
	/* ---------------------------------------------------------
	 * Internal methods
	 * --------------------------------------------------------- */
	
	/**
	 * Returns the size of multiple arrays, simulating as if it were a single array
	 *
	 * @param arrays all the arrays you want to check
	 * @param <T>    the type of array that the method will generate
	 * @return the size of multiple arrays
	 */
	@SafeVarargs
	@Contract(pure = true)
	private static <T> int calculateTotalSize(T @NotNull [] @NotNull ... arrays) {
		int size = 0;
		for (T[] arr : arrays) {
			size += arr.length;
		}
		return size;
	}
	
}
