package ushiosan.jvm.internal.validators;

import org.jetbrains.annotations.NotNull;
import ushiosan.jvm.collections.UArray;
import ushiosan.jvm.content.UPair;
import ushiosan.jvm.function.UFun;

import static ushiosan.jvm.UObject.cast;

public final class UArrayValidator {
	
	/* -----------------------------------------------------
	 * Constants
	 * ----------------------------------------------------- */
	
	/**
	 * Object pairs used for conversion from primitive arrays to object arrays
	 */
	public static final UPair<Class<?>, UFun.UFun1<Object[], Object>>[] NUMERIC_ARRAYS_OBJ_CONVERSIONS =
		UArray.of(
			UPair.of(byte[].class, UArrayValidator::toByteArrayObj),
			UPair.of(short[].class, UArrayValidator::toShortArrayObj),
			UPair.of(int[].class, UArrayValidator::toIntegerArrayObj),
			UPair.of(long[].class, UArrayValidator::toLongArrayObj),
			UPair.of(float[].class, UArrayValidator::toFloatArrayObj),
			UPair.of(double[].class, UArrayValidator::toDoubleArrayObj));
	
	/**
	 * Object pairs used for conversion from primitive arrays to object arrays
	 */
	public static final UPair<Class<?>, UFun.UFun1<Object[], Object>>[] PRIMITIVE_ARRAYS_CONVERSIONS =
		UArray.join(NUMERIC_ARRAYS_OBJ_CONVERSIONS, UArray.of(
			UPair.of(boolean[].class, UArrayValidator::toBooleanArrayObj),
			UPair.of(char[].class, UArrayValidator::toCharArrayObj)));
	
	/* -----------------------------------------------------
	 * Primitive conversion methods
	 * ----------------------------------------------------- */
	
	/**
	 * Convert any array to an object array. This also applies to primitive types.
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	private static Object @NotNull [] toBooleanArrayObj(@NotNull Object array) {
		boolean[] arr = cast(array);
		Boolean[] result = new Boolean[arr.length];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}
	
	/**
	 * Convert any array to an object array. This also applies to primitive types.
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	private static Object @NotNull [] toCharArrayObj(@NotNull Object array) {
		char[] arr = cast(array);
		Character[] result = new Character[arr.length];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}
	
	/* -----------------------------------------------------
	 * Numeric conversions methods
	 * ----------------------------------------------------- */
	
	/**
	 * Convert any array to an object array. This also applies to primitive types.
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	private static Object @NotNull [] toByteArrayObj(@NotNull Object array) {
		byte[] arr = cast(array);
		Byte[] result = new Byte[arr.length];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}
	
	/**
	 * Convert any array to an object array. This also applies to primitive types.
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	private static Object @NotNull [] toShortArrayObj(@NotNull Object array) {
		short[] arr = cast(array);
		Short[] result = new Short[arr.length];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}
	
	/**
	 * Convert any array to an object array. This also applies to primitive types.
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	private static Object @NotNull [] toIntegerArrayObj(@NotNull Object array) {
		int[] arr = cast(array);
		Integer[] result = new Integer[arr.length];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}
	
	/**
	 * Convert any array to an object array. This also applies to primitive types.
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	private static Object @NotNull [] toLongArrayObj(@NotNull Object array) {
		long[] arr = cast(array);
		Long[] result = new Long[arr.length];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}
	
	/**
	 * Convert any array to an object array. This also applies to primitive types.
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	private static Object @NotNull [] toFloatArrayObj(@NotNull Object array) {
		float[] arr = cast(array);
		Float[] result = new Float[arr.length];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}
	
	/**
	 * Convert any array to an object array. This also applies to primitive types.
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	private static Object @NotNull [] toDoubleArrayObj(@NotNull Object array) {
		double[] arr = cast(array);
		Double[] result = new Double[arr.length];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}
	
}