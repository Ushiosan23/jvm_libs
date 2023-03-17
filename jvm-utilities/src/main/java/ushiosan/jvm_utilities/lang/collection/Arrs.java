package ushiosan.jvm_utilities.lang.collection;

import org.jetbrains.annotations.NotNull;
import ushiosan.jvm_utilities.internal.collection.ArrsPrimitive;
import ushiosan.jvm_utilities.lang.Cls;

import static ushiosan.jvm_utilities.lang.Obj.cast;

/**
 * Utility class for handling arrays
 */
public final class Arrs extends ArrsPrimitive {
	
	/**
	 * This class cannot be instantiated.
	 * <p>
	 * Singleton or utility class mode.
	 */
	private Arrs() {
	}
	
	/* ---------------------------------------------------------
	 * Conversion methods
	 * --------------------------------------------------------- */
	
	/**
	 * Convert any array to object array. This also applies to primitive types.
	 * <p>
	 * Examples
	 * <pre>{@code
	 * // For numeric primitives always use Number class
	 * Integer[] intArr = (Integer[]) ArraysImpl.toObjectArray(new int[] {2, 4, 6, 8});
	 * Short[] shortArr = (Short[]) ArraysImpl.toObjectArray(new short[] {12, 21, 42, 120, 0xFF});
	 * Float[] floatArr = (Float[]) ArraysImpl.toObjectArray(new float[] {1f, 2f, 3f, 120.2234f});
	 * // Wrapped clases
	 * Boolean[] boolArr = (Boolean[]) ArraysImpl.toObjectArray(new boolean[] {true, false, false, true});
	 * Character[] intArr = (Character[]) ArraysImpl.toObjectArray(new char[] {'H', 'e', 'l', 'l', 'o'});
	 * }</pre>
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 * @throws IllegalArgumentException Error if the object is not valid array
	 */
	public static Object @NotNull [] toUnsafeObjectArray(@NotNull Object array) {
		return toObjectArrayImpl(array);
	}
	
	/**
	 * Convert any array to object array. This also applies to primitive types.
	 * <p>
	 * Examples
	 * <pre>{@code
	 * // For numeric primitives always use Number class
	 * Integer[] intArr = (Integer[]) ArraysImpl.toObjectArray(new int[] {2, 4, 6, 8});
	 * Short[] shortArr = (Short[]) ArraysImpl.toObjectArray(new short[] {12, 21, 42, 120, 0xFF});
	 * Float[] floatArr = (Float[]) ArraysImpl.toObjectArray(new float[] {1f, 2f, 3f, 120.2234f});
	 * // Wrapped clases
	 * Boolean[] boolArr = (Boolean[]) ArraysImpl.toObjectArray(new boolean[] {true, false, false, true});
	 * Character[] intArr = (Character[]) ArraysImpl.toObjectArray(new char[] {'H', 'e', 'l', 'l', 'o'});
	 * }</pre>
	 *
	 * @param array the array to convert
	 * @param <T>   the data type of the array
	 * @return a converted array object
	 */
	public static <T> Object @NotNull [] toObjectArray(T @NotNull [] array) {
		return toObjectArrayImpl(array);
	}
	
	/**
	 * Convert any array to object array. This also applies to primitive types.
	 * <p>
	 * Examples
	 * <pre>{@code
	 * // For numeric primitives always use Number class
	 * Integer[] intArr = (Integer[]) ArraysImpl.toObjectArray(new int[] {2, 4, 6, 8});
	 * Short[] shortArr = (Short[]) ArraysImpl.toObjectArray(new short[] {12, 21, 42, 120, 0xFF});
	 * Float[] floatArr = (Float[]) ArraysImpl.toObjectArray(new float[] {1f, 2f, 3f, 120.2234f});
	 * // Wrapped clases
	 * Boolean[] boolArr = (Boolean[]) ArraysImpl.toObjectArray(new boolean[] {true, false, false, true});
	 * Character[] intArr = (Character[]) ArraysImpl.toObjectArray(new char[] {'H', 'e', 'l', 'l', 'o'});
	 * }</pre>
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	public static Boolean @NotNull [] toObjectArray(boolean[] array) {
		return cast(toObjectArrayImpl(array), Boolean[].class);
	}
	
	/**
	 * Convert any array to object array. This also applies to primitive types.
	 * <p>
	 * Examples
	 * <pre>{@code
	 * // For numeric primitives always use Number class
	 * Integer[] intArr = (Integer[]) ArraysImpl.toObjectArray(new int[] {2, 4, 6, 8});
	 * Short[] shortArr = (Short[]) ArraysImpl.toObjectArray(new short[] {12, 21, 42, 120, 0xFF});
	 * Float[] floatArr = (Float[]) ArraysImpl.toObjectArray(new float[] {1f, 2f, 3f, 120.2234f});
	 * // Wrapped clases
	 * Boolean[] boolArr = (Boolean[]) ArraysImpl.toObjectArray(new boolean[] {true, false, false, true});
	 * Character[] intArr = (Character[]) ArraysImpl.toObjectArray(new char[] {'H', 'e', 'l', 'l', 'o'});
	 * }</pre>
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	public static Character @NotNull [] toObjectArray(char[] array) {
		return cast(toObjectArrayImpl(array), Character[].class);
	}
	
	/**
	 * Convert any array to object array. This also applies to primitive types.
	 * <p>
	 * Examples
	 * <pre>{@code
	 * // For numeric primitives always use Number class
	 * Integer[] intArr = (Integer[]) ArraysImpl.toObjectArray(new int[] {2, 4, 6, 8});
	 * Short[] shortArr = (Short[]) ArraysImpl.toObjectArray(new short[] {12, 21, 42, 120, 0xFF});
	 * Float[] floatArr = (Float[]) ArraysImpl.toObjectArray(new float[] {1f, 2f, 3f, 120.2234f});
	 * // Wrapped clases
	 * Boolean[] boolArr = (Boolean[]) ArraysImpl.toObjectArray(new boolean[] {true, false, false, true});
	 * Character[] intArr = (Character[]) ArraysImpl.toObjectArray(new char[] {'H', 'e', 'l', 'l', 'o'});
	 * }</pre>
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	public static Byte @NotNull [] toObjectArray(byte[] array) {
		return cast(toObjectArrayImpl(array), Byte[].class);
	}
	
	/**
	 * Convert any array to object array. This also applies to primitive types.
	 * <p>
	 * Examples
	 * <pre>{@code
	 * // For numeric primitives always use Number class
	 * Integer[] intArr = (Integer[]) ArraysImpl.toObjectArray(new int[] {2, 4, 6, 8});
	 * Short[] shortArr = (Short[]) ArraysImpl.toObjectArray(new short[] {12, 21, 42, 120, 0xFF});
	 * Float[] floatArr = (Float[]) ArraysImpl.toObjectArray(new float[] {1f, 2f, 3f, 120.2234f});
	 * // Wrapped clases
	 * Boolean[] boolArr = (Boolean[]) ArraysImpl.toObjectArray(new boolean[] {true, false, false, true});
	 * Character[] intArr = (Character[]) ArraysImpl.toObjectArray(new char[] {'H', 'e', 'l', 'l', 'o'});
	 * }</pre>
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	public static Short @NotNull [] toObjectArray(short[] array) {
		return cast(toObjectArrayImpl(array), Short[].class);
	}
	
	/**
	 * Convert any array to object array. This also applies to primitive types.
	 * <p>
	 * Examples
	 * <pre>{@code
	 * // For numeric primitives always use Number class
	 * Integer[] intArr = (Integer[]) ArraysImpl.toObjectArray(new int[] {2, 4, 6, 8});
	 * Short[] shortArr = (Short[]) ArraysImpl.toObjectArray(new short[] {12, 21, 42, 120, 0xFF});
	 * Float[] floatArr = (Float[]) ArraysImpl.toObjectArray(new float[] {1f, 2f, 3f, 120.2234f});
	 * // Wrapped clases
	 * Boolean[] boolArr = (Boolean[]) ArraysImpl.toObjectArray(new boolean[] {true, false, false, true});
	 * Character[] intArr = (Character[]) ArraysImpl.toObjectArray(new char[] {'H', 'e', 'l', 'l', 'o'});
	 * }</pre>
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	public static Integer @NotNull [] toObjectArray(int[] array) {
		return cast(toObjectArrayImpl(array), Integer[].class);
	}
	
	/**
	 * Convert any array to object array. This also applies to primitive types.
	 * <p>
	 * Examples
	 * <pre>{@code
	 * // For numeric primitives always use Number class
	 * Integer[] intArr = (Integer[]) ArraysImpl.toObjectArray(new int[] {2, 4, 6, 8});
	 * Short[] shortArr = (Short[]) ArraysImpl.toObjectArray(new short[] {12, 21, 42, 120, 0xFF});
	 * Float[] floatArr = (Float[]) ArraysImpl.toObjectArray(new float[] {1f, 2f, 3f, 120.2234f});
	 * // Wrapped clases
	 * Boolean[] boolArr = (Boolean[]) ArraysImpl.toObjectArray(new boolean[] {true, false, false, true});
	 * Character[] intArr = (Character[]) ArraysImpl.toObjectArray(new char[] {'H', 'e', 'l', 'l', 'o'});
	 * }</pre>
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	public static Long @NotNull [] toObjectArray(long[] array) {
		return cast(toObjectArrayImpl(array), Long[].class);
	}
	
	/**
	 * Convert any array to object array. This also applies to primitive types.
	 * <p>
	 * Examples
	 * <pre>{@code
	 * // For numeric primitives always use Number class
	 * Integer[] intArr = (Integer[]) ArraysImpl.toObjectArray(new int[] {2, 4, 6, 8});
	 * Short[] shortArr = (Short[]) ArraysImpl.toObjectArray(new short[] {12, 21, 42, 120, 0xFF});
	 * Float[] floatArr = (Float[]) ArraysImpl.toObjectArray(new float[] {1f, 2f, 3f, 120.2234f});
	 * // Wrapped clases
	 * Boolean[] boolArr = (Boolean[]) ArraysImpl.toObjectArray(new boolean[] {true, false, false, true});
	 * Character[] intArr = (Character[]) ArraysImpl.toObjectArray(new char[] {'H', 'e', 'l', 'l', 'o'});
	 * }</pre>
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	public static Float @NotNull [] toObjectArray(float[] array) {
		return cast(toObjectArrayImpl(array), Float[].class);
	}
	
	/**
	 * Convert any array to object array. This also applies to primitive types.
	 * <p>
	 * Examples
	 * <pre>{@code
	 * // For numeric primitives always use Number class
	 * Integer[] intArr = (Integer[]) ArraysImpl.toObjectArray(new int[] {2, 4, 6, 8});
	 * Short[] shortArr = (Short[]) ArraysImpl.toObjectArray(new short[] {12, 21, 42, 120, 0xFF});
	 * Float[] floatArr = (Float[]) ArraysImpl.toObjectArray(new float[] {1f, 2f, 3f, 120.2234f});
	 * // Wrapped clases
	 * Boolean[] boolArr = (Boolean[]) ArraysImpl.toObjectArray(new boolean[] {true, false, false, true});
	 * Character[] intArr = (Character[]) ArraysImpl.toObjectArray(new char[] {'H', 'e', 'l', 'l', 'o'});
	 * }</pre>
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	public static Double @NotNull [] toObjectArray(double[] array) {
		return cast(toObjectArrayImpl(array), Double[].class);
	}
	
	/* ---------------------------------------------------------
	 * Internal methods
	 * --------------------------------------------------------- */
	
	/**
	 * Convert any array to object array. This also applies to primitive types.
	 * <p>
	 * Examples
	 * <pre>{@code
	 * // For numeric primitives always use Number class
	 * Integer[] intArr = (Integer[]) ArraysImpl.toObjectArray(new int[] {2, 4, 6, 8});
	 * Short[] shortArr = (Short[]) ArraysImpl.toObjectArray(new short[] {12, 21, 42, 120, 0xFF});
	 * Float[] floatArr = (Float[]) ArraysImpl.toObjectArray(new float[] {1f, 2f, 3f, 120.2234f});
	 * // Wrapped clases
	 * Boolean[] boolArr = (Boolean[]) ArraysImpl.toObjectArray(new boolean[] {true, false, false, true});
	 * Character[] intArr = (Character[]) ArraysImpl.toObjectArray(new char[] {'H', 'e', 'l', 'l', 'o'});
	 * }</pre>
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	private static Object @NotNull [] toObjectArrayImpl(@NotNull Object array) {
		// Check array content
		Class<?> cls = array.getClass();
		if (!cls.isArray()) throw new IllegalArgumentException("Invalid array type.");
		
		// Only for primitive types
		if (Cls.isPrimitiveArray(cls)) {
			// Check if array is a primitive type
			for (var item : CONVERSION_MAP) {
				if (cls == item.first) return item.second.apply(array);
			}
		}
		return cast(array);
	}
	
}
