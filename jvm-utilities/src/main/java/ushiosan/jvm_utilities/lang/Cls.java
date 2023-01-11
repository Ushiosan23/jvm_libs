package ushiosan.jvm_utilities.lang;

import org.jetbrains.annotations.NotNull;
import ushiosan.jvm_utilities.lang.collection.Arrs;

import static ushiosan.jvm_utilities.lang.Obj.cast;

/**
 * Class containing helper methods for working with objects of type {@link Class}
 */
public final class Cls {
	
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
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
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
	
	/**
	 * Reference to the wrapper class for primitive types in arrays.
	 */
	private static final Class<?>[] PRIMITIVE_ARRAY_INDIVIDUAL = new Class[]{
		boolean.class,
		char.class,
		byte.class,
		short.class,
		int.class,
		long.class,
		float.class,
		double.class
	};
	
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
	
	/**
	 * Returns the class of the individual object in an array.
	 * <p>
	 * <b>Example:</b>
	 * <pre>{@code
	 * String[] array = new String[] {"Hello", "World", "!"};
	 * Class<?> cls = Cls.getArrayIndividualClass(array);
	 *
	 * // Output -> class java.lang.String
	 * }</pre>
	 *
	 * @param array the array you want to identify
	 * @param <T>   the class of the data type
	 * @return the data type of the elements individually
	 */
	public static <T> @NotNull Class<T> getArrayIndividualClass(T @NotNull [] array) {
		Class<?> generalClass = array.getClass();
		return getArrayIndividualClassImpl(generalClass);
	}
	
	/**
	 * Returns the class of the individual object in an array.
	 * <p>
	 * <b>Example:</b>
	 * <pre>{@code
	 * String[][] array = new String[0][0];
	 * Class<?> cls = Cls.getArrayIndividualClass(array);
	 *
	 * // Output -> class java.lang.String
	 * }</pre>
	 *
	 * @param array the array you want to identify
	 * @param <T>   the class of the data type
	 * @return the data type of the elements individually
	 */
	public static <T> @NotNull Class<T> getArrayIndividualClass(T @NotNull [] @NotNull [] array) {
		Class<?> generalClass = array.getClass();
		return getArrayIndividualClassImpl(generalClass);
	}
	
	/**
	 * Returns the class of the individual object in an array.
	 * <p>
	 * <b>Example:</b>
	 * <pre>{@code
	 * String[][][] array = new String[0][0][0]; // or more
	 * Class<?> cls = Cls.getArrayIndividualClass(array);
	 *
	 * // Output -> class java.lang.String
	 * }</pre>
	 *
	 * @param array the array you want to identify
	 * @param <T>   the class of the data type
	 * @return the data type of the elements individually
	 */
	public static <T> @NotNull Class<T> getArrayMultipleIndividualClass(@NotNull Object array) {
		Class<?> cls = array.getClass();
		// Check if is a valid array
		if (!cls.isArray()) throw new IllegalArgumentException("Is not valid array object");
		return getArrayIndividualClassImpl(cls);
	}
	
	/* ---------------------------------------------------------
	 * Internal methods
	 * --------------------------------------------------------- */
	
	/**
	 * Returns the individual name of a class
	 *
	 * @param cls the class you want to identify
	 * @param <T> the class of the data type
	 * @return the data type of the elements individually
	 */
	private static <T> @NotNull Class<T> getArrayIndividualClassImpl(@NotNull Class<?> cls) {
		// Check if class is an array
		if (!cls.isArray()) throw new IllegalArgumentException("Invalid array type");
		String generalName = cls.getCanonicalName();
		int index = Arrs.indexOf(PRIMITIVE_ARRAY_CLASSES, cls);
		
		// Only for primitive types
		if (index != Arrs.INDEX_NOT_FOUND) {
			return cast(PRIMITIVE_ARRAY_INDIVIDUAL[index]);
		}
		
		// Clean class name
		generalName = generalName.replaceAll("\\[]", "").trim();
		try {
			return cast(Class.forName(generalName));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
