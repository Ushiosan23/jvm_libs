package ushiosan.jvm_utilities.internal.collection;

import static ushiosan.jvm_utilities.lang.Obj.cast;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public final class ArrsImpl {

	/**
	 * This class cannot be instantiated.
	 * <p>
	 * Singleton or utility class mode.
	 */
	private ArrsImpl() {
	}

	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */

	/**
	 * Convert any array of primitive numbers to an array of numeric objects
	 *
	 * @param array the array to convert
	 * @return an array with all numeric objects
	 * @throws IllegalArgumentException Error if the array is not valid
	 */
	public static Number @NotNull [] toNumberArray(@NotNull Object array) {
		// Array validations
		Class<?> refClass = array.getClass();
		if (!refClass.isArray())
			throw new IllegalArgumentException("Invalid array type.");

		// Check array type
		if (refClass == byte[].class)
			return toNumberArrayFromPrimitive(cast(array, byte[].class));
		if (refClass == short[].class)
			return toNumberArrayFromPrimitive(cast(array, short[].class));
		if (refClass == int[].class)
			return toNumberArrayFromPrimitive(cast(array, int[].class));
		if (refClass == long[].class)
			return toNumberArrayFromPrimitive(cast(array, long[].class));
		if (refClass == float[].class)
			return toNumberArrayFromPrimitive(cast(array, float[].class));
		if (refClass == double[].class)
			return toNumberArrayFromPrimitive(cast(array, double[].class));

		throw new IllegalArgumentException("Invalid numeric array.");
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
	 * Number[] intArr = (Number[]) ArraysImpl.toObjectArray(new int[] {2, 4, 6, 8});
	 * Number[] shortArr = (Number[]) ArraysImpl.toObjectArray(new short[] {12, 21, 42, 120, 0xFF});
	 * Number[] floatArr = (Number[]) ArraysImpl.toObjectArray(new float[] {1f, 2f, 3f, 120.2234f});
	 * // Wrapped clases
	 * Boolean[] boolArr = (Boolean[]) ArraysImpl.toObjectArray(new boolean[] {true, false, false, true});
	 * Character[] intArr = (Character[]) ArraysImpl.toObjectArray(new char[] {'H', 'e', 'l', 'l', 'o'});
	 * }</pre>
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	public static Object @NotNull [] toObjectArray(@NotNull Object array) {
		// Array validations
		Class<?> refClass = array.getClass();
		if (!refClass.isArray())
			throw new IllegalArgumentException("Invalid array type.");

		// Check if is another primitive type
		if (refClass == boolean[].class)
			return toObjectArrayImpl(cast(array, boolean[].class));
		if (refClass == char[].class)
			return toObjectArrayImpl(cast(array, char[].class));

		// Check numeric types
		if (refClass == byte[].class || refClass == short[].class || refClass == int[].class ||
			refClass == long[].class || refClass == float[].class || refClass == double[].class)
			return toNumberArray(array);

		return cast(array);
	}

	/* -----------------------------------------------------
	 * Primitive conversions
	 * ----------------------------------------------------- */

	/**
	 * Convert a boolean primitive array to object array
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	@Contract(pure = true)
	private static Boolean @NotNull [] toObjectArrayImpl(boolean @NotNull [] array) {
		// Generate result
		final int total = array.length;
		Boolean[] result = new Boolean[total];

		// Copy the array
		for (int i = 0; i < total; i++) {
			result[i] = array[i];
		}
		return result;
	}

	/**
	 * Convert a char primitive array to object array
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	@Contract(pure = true)
	private static Character @NotNull [] toObjectArrayImpl(char @NotNull [] array) {
		// Generate result
		final int total = array.length;
		Character[] result = new Character[total];

		// Copy the array
		for (int i = 0; i < total; i++) {
			result[i] = array[i];
		}
		return result;
	}

	/* -----------------------------------------------------
	 * Number conversions
	 * ----------------------------------------------------- */

	/**
	 * Convert the primitive array to a numeric array
	 *
	 * @param array the array to convert
	 * @return an array with all numeric objects
	 * @see Number
	 */
	@Contract(pure = true)
	private static Number @NotNull [] toNumberArrayFromPrimitive(byte @NotNull [] array) {
		// Generate result
		final int total = array.length;
		Number[] result = new Number[total];
		// Copy all content
		// Streams are not used, because byte arrays are not supported.
		for (int i = 0; i < total; i++) {
			result[i] = array[i];
		}
		return result;
	}

	/**
	 * Convert the primitive array to a numeric array
	 *
	 * @param array the array to convert
	 * @return an array with all numeric objects
	 * @see Number
	 */
	@Contract(pure = true)
	private static Number @NotNull [] toNumberArrayFromPrimitive(short @NotNull [] array) {
		// Generate result
		final int total = array.length;
		Number[] result = new Number[total];
		// Copy all content
		// Streams are not used, because byte arrays are not supported.
		for (int i = 0; i < total; i++) {
			result[i] = array[i];
		}
		return result;
	}

	/**
	 * Convert the primitive array to a numeric array
	 *
	 * @param array the array to convert
	 * @return an array with all numeric objects
	 * @see Number
	 */
	@Contract(pure = true)
	private static Number @NotNull [] toNumberArrayFromPrimitive(int @NotNull [] array) {
		return Arrays.stream(array)
			.boxed()
			.toArray(Number[]::new);
	}

	/**
	 * Convert the primitive array to a numeric array
	 *
	 * @param array the array to convert
	 * @return an array with all numeric objects
	 * @see Number
	 */
	@Contract(pure = true)
	private static Number @NotNull [] toNumberArrayFromPrimitive(long @NotNull [] array) {
		return Arrays.stream(array)
			.boxed()
			.toArray(Number[]::new);
	}

	/**
	 * Convert the primitive array to a numeric array
	 *
	 * @param array the array to convert
	 * @return an array with all numeric objects
	 * @see Number
	 */
	@Contract(pure = true)
	private static Number @NotNull [] toNumberArrayFromPrimitive(float @NotNull [] array) {
		// Generate result
		final int total = array.length;
		Number[] result = new Number[total];
		// Copy all content
		// Streams are not used, because byte arrays are not supported.
		for (int i = 0; i < total; i++) {
			result[i] = array[i];
		}
		return result;
	}

	/**
	 * Convert the primitive array to a numeric array
	 *
	 * @param array the array to convert
	 * @return an array with all numeric objects
	 * @see Number
	 */
	@Contract(pure = true)
	private static Number @NotNull [] toNumberArrayFromPrimitive(double @NotNull [] array) {
		return Arrays.stream(array)
			.boxed()
			.toArray(Number[]::new);
	}

}
