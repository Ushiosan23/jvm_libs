package ushiosan.jvm.internal.validators;

import org.jetbrains.annotations.NotNull;
import ushiosan.jvm.UObject;
import ushiosan.jvm.collections.UArray;
import ushiosan.jvm.content.UPair;
import ushiosan.jvm.function.UFun;

public final class UArrayValidator {
	
	/* -----------------------------------------------------
	 * Constants
	 * ----------------------------------------------------- */
	
	/**
	 * Object pairs used for conversion from primitive arrays to object arrays
	 */
	public static final UPair<Class<?>, UFun.UFun1<Object[], Object>>[] NUMERIC_ARRAYS_OBJ_CONVERSIONS =
		UArray.make(
			UPair.make(byte[].class, UArrayValidator::toByteArrayObj),
			UPair.make(short[].class, UArrayValidator::toShortArrayObj),
			UPair.make(int[].class, UArrayValidator::toIntegerArrayObj),
			UPair.make(long[].class, UArrayValidator::toLongArrayObj),
			UPair.make(float[].class, UArrayValidator::toFloatArrayObj),
			UPair.make(double[].class, UArrayValidator::toDoubleArrayObj));
	
	/**
	 * Object pairs used for conversion from primitive arrays to object arrays
	 */
	public static final UPair<Class<?>, UFun.UFun1<Object[], Object>>[] PRIMITIVE_ARRAYS_CONVERSIONS =
		UArray.join(NUMERIC_ARRAYS_OBJ_CONVERSIONS, UArray.make(
			UPair.make(boolean[].class, UArrayValidator::toBooleanArrayObj),
			UPair.make(char[].class, UArrayValidator::toCharArrayObj)));
	
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
		boolean[] arr = UObject.cast(array);
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
		char[] arr = UObject.cast(array);
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
		byte[] arr = UObject.cast(array);
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
		short[] arr = UObject.cast(array);
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
		int[] arr = UObject.cast(array);
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
		long[] arr = UObject.cast(array);
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
		float[] arr = UObject.cast(array);
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
		double[] arr = UObject.cast(array);
		Double[] result = new Double[arr.length];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}
	
}