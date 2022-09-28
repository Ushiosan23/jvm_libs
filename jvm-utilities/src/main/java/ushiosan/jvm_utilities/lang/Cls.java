package ushiosan.jvm_utilities.lang;

import org.jetbrains.annotations.NotNull;

import ushiosan.jvm_utilities.lang.collection.Arrs;

public final class Cls {

	/**
	 * This class cannot be instantiated.
	 * <p>
	 * Singleton or utility class mode.
	 */
	private Cls() {
	}

	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */

	/**
	 * Java wrapped primitive types
	 */
	private static final Class<?>[] PRIMITIVE_WRAPPED_CLASSES = new Class[]{
		Boolean.class,
		Character.class,
		Byte.class,
		Short.class,
		Integer.class,
		Long.class,
		Float.class,
		Double.class,
		Void.class
	};

	/**
	 * Java primitive array types
	 */
	private static final Class<?>[] PRIMITIVE_ARRAY_CLASSES = new Class[]{
		boolean[].class,
		char[].class,
		byte[].class,
		short[].class,
		int[].class,
		long[].class,
		float[].class,
		double[].class
	};

	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */

	/**
	 * Checks whether the class of the argument is of some primitive type or some
	 * wrapper class corresponding to the primitive type.
	 *
	 * @param clazz the class to inspect
	 * @return {@code true} if the class is a primitive type or {@code false} otherwise
	 */
	public static boolean isPrimitive(@NotNull Class<?> clazz) {
		return clazz.isPrimitive() || Arrs.contains(PRIMITIVE_WRAPPED_CLASSES, clazz);
	}

	/**
	 * Checks whether the class of the argument is of some primitive type or some
	 * wrapper class corresponding to the primitive type.
	 *
	 * @param obj the object to inspect
	 * @return {@code true} if the class is a primitive type or {@code false} otherwise
	 */
	public static boolean isPrimitive(@NotNull Object obj) {
		return isPrimitive(obj.getClass());
	}

	/**
	 * Checks if the class of the argument is of some primitive type or some
	 * wrapper class corresponding to the primitive type (version for arrays).
	 *
	 * @param clazz the class to inspect
	 * @return {@code true} if the class is a primitive type or {@code false} otherwise
	 */
	public static boolean isPrimitiveArray(@NotNull Class<?> clazz) {
		return clazz.isArray() && Arrs.contains(PRIMITIVE_ARRAY_CLASSES, clazz);
	}

	/**
	 * Checks if the class of the argument is of some primitive type or some
	 * wrapper class corresponding to the primitive type (version for arrays).
	 *
	 * @param obj the object to inspect
	 * @return {@code true} if the class is a primitive type or {@code false} otherwise
	 */
	public static boolean isPrimitiveArray(@NotNull Object obj) {
		return isPrimitiveArray(obj.getClass());
	}

	/**
	 * Returns the type of all data passed as arguments
	 *
	 * @param args the arguments to convert
	 * @return an array with all element types
	 */
	public static Class<?> @NotNull [] toTypeArgs(Object @NotNull ... args) {
		return java.util.Arrays.stream(args)
			.map(Object::getClass)
			.toArray(Class[]::new);
	}

}
