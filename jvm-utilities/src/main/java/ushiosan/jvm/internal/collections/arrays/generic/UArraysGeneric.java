package ushiosan.jvm.internal.collections.arrays.generic;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ushiosan.jvm.UClass;
import ushiosan.jvm.collections.ULists;
import ushiosan.jvm.internal.collections.arrays.UArraysConstants;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Function;
import java.util.function.IntFunction;

import static ushiosan.jvm.UObject.cast;
import static ushiosan.jvm.UObject.requireNotNull;

public abstract class UArraysGeneric implements UArraysConstants {
	
	/* -----------------------------------------------------
	 * Generator methods
	 * ----------------------------------------------------- */
	
	/**
	 * Generate an array from given values. If you want to use primitive arrays,
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
	 * Generate an array from given values. If you want to use primitive arrays,
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
	public static Object @NotNull [] of(@NotNull Iterator<?> iterator) {
		requireNotNull(iterator, "iterator");
		List<?> iteratorList = ULists.listOf(iterator);
		return iteratorList.toArray();
	}
	
	/* -----------------------------------------------------
	 * Search methods
	 * ----------------------------------------------------- */
	
	/**
	 * Search elements in the array
	 *
	 * @param array   base array to search
	 * @param element the element to search
	 * @return the first index element or {@code -1} if an element not exists
	 */
	@Contract(pure = true)
	public static int indexOf(Object @NotNull [] array, @Nullable Object element) {
		requireNotNull(array, "array");
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
		// By default, returns INDEX_NOT_FOUND (-1)
		return INDEX_NOT_FOUND;
	}
	
	/**
	 * Search an element in the array. This method tries to search in reverse,
	 * first starting at the end of the array, to find the desired element.
	 *
	 * @param array   base array to search
	 * @param element the element to search
	 * @return the first index element or {@code -1} if an element not exists
	 */
	@Contract(pure = true)
	public static int lastIndexOf(Object @NotNull [] array, @Nullable Object element) {
		requireNotNull(array, "array");
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
		// By default, returns INDEX_NOT_FOUND (-1)
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
		requireNotNull(array, "array");
		if (array.length == 0) return null;
		return array[array.length - 1];
	}
	
	/* ---------------------------------------------------------
	 * Equality methods
	 * --------------------------------------------------------- */
	
	/**
	 * Verify that the contents of two arrays are the same
	 * <p>
	 * This method checks if two arrays have the same length and the same elements.
	 * It utilizes a labeled block and a conditional break to optimize the process
	 * and avoid redundant return statements. By using a single return statement
	 * outside the block, we achieve a more dynamic flow while minimizing code duplication.
	 *
	 * @param ar1 the array to check
	 * @param ar2 the array to check
	 * @param <T> the data type of each array
	 * @return {@code true} if the content is the same or {@code false} if
	 * 	the size is different or the content is different
	 */
	public static <T> boolean contentEquals(T @NotNull [] ar1, T @NotNull [] ar2) {
		requireNotNull(ar1, "ar1");
		requireNotNull(ar2, "ar2");
		boolean result = ar1.length == ar2.length;
		check:
		{
			if (!result) break check;
			
			// Iterate all elements
			for (int i = 0; i < ar1.length; i++) {
				T v1 = ar1[i];
				T v2 = ar2[i];
				
				// Check nullable elements
				if (Objects.equals(v1, v2)) continue;
				
				// Validate elements
				if (v1 == null || !v1.equals(v2)) {
					result = false;
					break;
				}
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
		requireNotNull(original, "original");
		requireNotNull(mapper, "mapper");
		requireNotNull(arrFn, "arrFn");
		// Transform process
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
	 * @return the new array with the converted data
	 */
	public static <T> Object @NotNull [] transform(@NotNull T[] original, @NotNull Function<T, Object> mapper) {
		requireNotNull(original, "original");
		requireNotNull(mapper, "mapper");
		// Transform process
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
		Class<T> cls = UClass.getArrayIndividualClass(a1);
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
		requireNotNull(a1, "a1");
		requireNotNull(a2, "a2");
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
		requireNotNull(cls, "cls");
		int totalSize = calculateTotalSize(arrays);
		int position = 0;
		// Generate new instance of an array
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
		Class<T> cls = UClass.getArrayIndividualClass(arrays);
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
