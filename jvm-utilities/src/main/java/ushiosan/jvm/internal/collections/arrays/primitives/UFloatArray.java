package ushiosan.jvm.internal.collections.arrays.primitives;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ushiosan.jvm.UObject;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;

abstract class UFloatArray extends ULongArray {
	
	/**
	 * Generate a primitive float array
	 *
	 * @param elements float elements
	 * @return an array with all float elements
	 */
	public static float[] makeFloat(float... elements) {
		return elements;
	}
	
	/**
	 * Generate a primitive float array
	 *
	 * @param elements float elements
	 * @return an array with all float elements
	 */
	@Contract(pure = true)
	public static float @NotNull [] makeFloatObj(Float @NotNull ... elements) {
		float[] result = new float[elements.length];
		int index = 0;
		
		for (var element : elements) {
			result[index++] = element;
		}
		
		return result;
	}
	
	/**
	 * Search elements in the array
	 *
	 * @param array   base array to search
	 * @param element the element to search
	 * @return the first index element or {@code -1} if the element not exists
	 */
	public static int primitiveIndexOf(float @NotNull [] array, float element) {
		UObject.requireNotNull(array, "array");
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
	 * @return the first index element or {@code -1} if the element not exists
	 */
	public static int primitiveLastIndexOf(float @NotNull [] array, float element) {
		UObject.requireNotNull(array, "array");
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
	public static boolean primitiveContains(float @NotNull [] array, float element) {
		return primitiveIndexOf(array, element) != INDEX_NOT_FOUND;
	}
	
	/**
	 * Returns the last element of the array
	 *
	 * @param array base array to search
	 * @return the last element of the array or {@link Optional#empty()} if array is empty
	 */
	public static @NotNull Optional<Float> primitiveLastElement(float @NotNull [] array) {
		UObject.requireNotNull(array, "array");
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
	public static float primitiveUnsafeLastElement(float @NotNull [] array) {
		UObject.requireNotNull(array, "array");
		if (array.length == 0) throw new IllegalStateException("The array is empty");
		return array[array.length - 1];
	}
	
	/**
	 * Convert any array to an object array. This also applies to primitive types.
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	public static Float @NotNull [] toObjectArray(float[] array) {
		return UObject.cast(UArrayPrimitive.toObjectArrayImpl(array),
							Float[].class);
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
	public static <T> T @NotNull [] primitiveTransform(float[] original, @NotNull Function<Float, T> mapper,
		@NotNull IntFunction<T[]> arrFn) {
		UObject.requireNotNull(mapper, "mapper");
		UObject.requireNotNull(arrFn, "arrFn");
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
	public static <T> Object @NotNull [] primitiveTransform(float[] original, @NotNull Function<Float, T> mapper) {
		UObject.requireNotNull(mapper, "mapper");
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
	public static boolean primitiveContentEquals(float[] ar1, float[] ar2) {
		return contentEquals(
			toObjectArray(ar1), toObjectArray(ar2));
	}
	
}
