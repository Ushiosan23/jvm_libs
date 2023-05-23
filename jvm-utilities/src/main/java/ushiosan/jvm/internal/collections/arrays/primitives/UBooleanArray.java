package ushiosan.jvm.internal.collections.arrays.primitives;

import org.jetbrains.annotations.NotNull;
import ushiosan.jvm.internal.collections.arrays.generic.UArraysGeneric;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.IntFunction;

import static ushiosan.jvm.UObject.cast;
import static ushiosan.jvm.UObject.requireNotNull;

abstract class UBooleanArray extends UArraysGeneric {
	
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
	 * Convert any array to an object array. This also applies to primitive types.
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	public static Boolean[] toObjectArray(boolean[] array) {
		return cast(UArraysPrimitive.toObjectArrayImpl(array), Boolean[].class);
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
	public static <T> T @NotNull [] primitiveTransform(boolean[] original, @NotNull Function<Boolean, T> mapper,
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
	public static <T> Object @NotNull [] primitiveTransform(boolean[] original, @NotNull Function<Boolean, T> mapper) {
		requireNotNull(mapper, "mapper");
		return Arrays.stream(toObjectArray(original))
			.map(mapper)
			.toArray();
	}
	
}
