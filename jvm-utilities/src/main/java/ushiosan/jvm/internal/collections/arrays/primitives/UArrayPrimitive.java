package ushiosan.jvm.internal.collections.arrays.primitives;

import org.jetbrains.annotations.NotNull;
import ushiosan.jvm.UClass;
import ushiosan.jvm.internal.validators.UArrayValidator;

import static ushiosan.jvm.UObject.cast;
import static ushiosan.jvm.UObject.requireNotNull;

public abstract class UArrayPrimitive extends UDoubleArray {
	
	/**
	 * Convert any array to an object array.
	 *
	 * @param array the array to convert
	 * @param <T>   generic member type
	 * @return a converted array object
	 */
	public static <T> Object @NotNull [] toGenericObjectArray(T @NotNull [] array) {
		return toObjectArrayImpl(array);
	}
	
	/* -----------------------------------------------------
	 * Internal methods
	 * ----------------------------------------------------- */
	
	/**
	 * Convert any array to an object array. This also applies to primitive types.
	 *
	 * @param array the array to convert
	 * @return a converted array object
	 */
	protected static Object @NotNull [] toObjectArrayImpl(@NotNull Object array) {
		requireNotNull(array, "array");
		// Verify array class
		Class<?> cls = array.getClass();
		if (!cls.isArray()) {
			throw new IllegalArgumentException("The parameter is not a valid array");
		}
		
		// Check if array is a primitive type
		if (UClass.isArrayPrimitive(cls)) {
			// Iterate all conversion elements
			for (var converter : UArrayValidator.PRIMITIVE_ARRAYS_CONVERSIONS) {
				if (cls == converter.first) return converter.second.invoke(array);
			}
		}
		
		return cast(array);
	}
	
}
