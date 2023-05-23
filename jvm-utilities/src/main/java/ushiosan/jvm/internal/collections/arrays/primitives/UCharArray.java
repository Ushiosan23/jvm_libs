package ushiosan.jvm.internal.collections.arrays.primitives;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;

import static ushiosan.jvm.UObject.cast;
import static ushiosan.jvm.UObject.requireNotNull;

abstract class UCharArray extends UBooleanArray {
	
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
	 * @return the first index element or {@code -1} if an element not exists
	 */
	public static int primitiveIndexOf(char @NotNull [] array, char element) {
		requireNotNull(array, "array");
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
	 * @return the first index element or {@code -1} if an element not exists
	 */
	public static int primitiveLastIndexOf(char @NotNull [] array, char element) {
		requireNotNull(array, "array");
		int maxSize = array.length - 1;
		for (int i = maxSize; i >= 0; i--) {
			if (array[i] == element) return i;
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
	public static boolean primitiveContains(char @NotNull [] array, char element) {
		return primitiveIndexOf(array, element) != INDEX_NOT_FOUND;
	}
	
	/**
	 * Returns the last element of the array
	 *
	 * @param array base array to search
	 * @return the last element of the array or {@link Optional#empty()} if array is empty
	 */
	public static @NotNull Optional<Character> primitiveLastElement(char @NotNull [] array) {
		requireNotNull(array, "array");
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
	public static char primitiveUnsafeLastElement(char @NotNull [] array) {
		requireNotNull(array, "array");
		if (array.length == 0) throw new IllegalStateException("The array is empty");
		return array[array.length - 1];
	}
	
	/**
	 * Convert any array to an object array. This also applies to primitive types.
	 * <p>
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	public static Character @NotNull [] toObjectArray(char[] array) {
		return cast(UArrayPrimitive.toObjectArrayImpl(array), Character[].class);
	}
	
	/**
	 * Converts one array to another but with a different data type.
	 *
	 * @param original the original array that you want to convert
	 * @param mapper   function in charge of transforming each element of the array
	 * @param arrFn    function that generates the required type of array
	 * @param <T>      the original data type
	 * @return the new array with the converted data
	 */
	public static <T> T @NotNull [] PrimitiveTransform(char[] original, @NotNull Function<Character, T> mapper,
		@NotNull IntFunction<T[]> arrFn) {
		requireNotNull(mapper, "mapper");
		requireNotNull(arrFn, "arrFn");
		return Arrays.stream(toObjectArray(original))
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
	public static <T> Object @NotNull [] PrimitiveTransform(char[] original, @NotNull Function<Character, T> mapper) {
		requireNotNull(mapper, "mapper");
		return Arrays.stream(toObjectArray(original))
			.map(mapper)
			.toArray();
	}
	
	/**
	 * Verify that the contents of two arrays are the same
	 *
	 * @param ar1 the array to check
	 * @param ar2 the array to check
	 * @return {@code true} if the content is the same or {@code false} if
	 * 	the size is different or the content is different
	 */
	public static boolean primitiveContentEquals(char[] ar1, char[] ar2) {
		return contentEquals(
			toObjectArray(ar1), toObjectArray(ar2));
	}
	
}
