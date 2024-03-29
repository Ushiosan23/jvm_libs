package ushiosan.jvm.internal.collections.arrays.primitives;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import ushiosan.jvm.UNumber;
import ushiosan.jvm.UObject;
import ushiosan.jvm.internal.collections.arrays.UArraysConstants;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;

abstract class UByteArray extends UCharArray {
	
	/**
	 * Generate a primitive byte array
	 *
	 * @param elements byte elements
	 * @return an array with all bytes
	 */
	public static byte @NotNull [] makeByte(Number @NotNull ... elements) {
		// Temporal variables
		byte[] result = new byte[elements.length];
		
		// Copy elements
		for (int i = 0; i < elements.length; ++i) {
			result[i] = elements[i].byteValue();
		}
		return result;
	}
	
	/**
	 * Generate a primitive byte array
	 *
	 * @param elements byte elements
	 * @return an array with all bytes
	 */
	public static byte @NotNull [] makeByteObj(Byte @NotNull ... elements) {
		// Temporal variables
		byte[] result = new byte[elements.length];
		
		// Copy elements
		for (int i = 0; i < elements.length; ++i) {
			result[i] = elements[i];
		}
		return result;
	}
	
	/**
	 * Search elements in the array
	 *
	 * @param array   base array to search
	 * @param element the element to search
	 * @return the first index element or {@code -1} if an element not exists
	 */
	public static int primitiveIndexOf(byte @NotNull [] array, @Range(from = Byte.MIN_VALUE, to = Byte.MAX_VALUE) int element) {
		UObject.requireNotNull(array, "array");
		for (int i = 0; i < array.length; i++) {
			if (array[i] == ((byte) element)) return i;
		}
		return INDEX_NOT_FOUND;
	}
	
	/**
	 * Search an element in the array. This method tries to search in reverse,
	 * first starting at the end of the array, to find the desired element.
	 *
	 * @param array   base array to search
	 * @param element the element to search
	 * @return the first index element or {@link UArraysConstants#INDEX_NOT_FOUND} if an element not exists
	 */
	public static int primitiveLastIndexOf(byte @NotNull [] array,
		@Range(from = Byte.MIN_VALUE, to = Byte.MAX_VALUE) int element) {
		UObject.requireNotNull(array, "array");
		int maxSize = array.length - 1;
		for (int i = maxSize; i >= 0; i--) {
			if (array[i] == ((byte) element)) return i;
		}
		// By default, returns an error result
		return INDEX_NOT_FOUND;
	}
	
	/**
	 * Checks if the array contains the selected element.
	 *
	 * @param array   base array to search
	 * @param element the element to search
	 * @return {@code true} if the element exists or {@code false} otherwise
	 */
	public static boolean primitiveContains(byte @NotNull [] array,
		@Range(from = Byte.MIN_VALUE, to = Byte.MAX_VALUE) int element) {
		return primitiveIndexOf(array, (byte) element) != INDEX_NOT_FOUND;
	}
	
	/**
	 * Returns the last element of the array
	 *
	 * @param array base array to search
	 * @return the last element of the array or {@link Optional#empty()} if array is empty
	 */
	public static @NotNull Optional<Byte> primitiveLastElement(byte @NotNull [] array) {
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
	public static byte primitiveUnsafeLastElement(byte @NotNull [] array) {
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
	public static Byte @NotNull [] toObjectArray(byte[] array) {
		return UObject.cast(UArrayPrimitive.toObjectArrayImpl(array), Byte[].class);
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
	public static <T> T @NotNull [] primitiveTransform(byte[] original, @NotNull Function<Byte, T> mapper,
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
	public static <T> Object @NotNull [] primitiveTransform(byte[] original, @NotNull Function<Byte, T> mapper) {
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
	public static boolean primitiveContentEquals(byte[] ar1, byte[] ar2) {
		return contentEquals(toObjectArray(ar1), toObjectArray(ar2));
	}
	
	/**
	 * Gets a new array but removing the sign to only get positive integers and not
	 * get invalid data.
	 *
	 * @param array the array you want to transform
	 * @return the result of the conversion to positive unsigned integers
	 */
	public static int @NotNull [] primitiveUnsignedByteArray(byte[] array) {
		// Convert all array content to a valid unsigned integers
		Integer[] transform = primitiveTransform(array, UNumber::toUnsignedByte, Integer[]::new);
		// Generate primitive array
		return UIntegerArray.makeIntObj(transform);
	}
	
}
