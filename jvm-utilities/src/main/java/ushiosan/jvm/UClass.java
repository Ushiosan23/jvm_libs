package ushiosan.jvm;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ushiosan.jvm.collections.UArrays;
import ushiosan.jvm.internal.validators.UClassValidator;

import java.util.Arrays;
import java.util.Stack;

import static ushiosan.jvm.UObject.cast;
import static ushiosan.jvm.UObject.requireNotNull;
import static ushiosan.jvm.internal.collections.arrays.UArraysConstants.INDEX_NOT_FOUND;

/**
 * Class containing helper methods for working with objects of type {@link Class}
 */
public final class UClass extends UClassValidator {
	
	/**
	 * Property used to return the entire inheritance stack of a class.
	 */
	public static final int FULL_CLASS_STACK = 0;
	
	/* -----------------------------------------------------
	 * Properties
	 * ----------------------------------------------------- */
	/**
	 * Property used to return only the current class from the inheritance stack.
	 */
	public static final int ALONE_CLASS_STACK = 1;
	
	/**
	 * This class cannot be instantiated
	 */
	private UClass() {}
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Checks whether the class of the argument is of some primitive type or some
	 * wrapper class corresponding to the primitive type.
	 *
	 * @param cls the class to inspect
	 * @return {@code true} if the class is a primitive type or {@code false} otherwise
	 */
	public static boolean isPrimitive(@NotNull Class<?> cls) {
		requireNotNull(cls, "cls");
		return cls.isPrimitive() || UArrays.contains(PRIMITIVE_WRAPPED_CLASSES, cls);
	}
	
	/**
	 * Checks whether the class of the argument is of some primitive type or some
	 * wrapper class corresponding to the primitive type.
	 *
	 * @param obj the object to inspect
	 * @return {@code true} if the class is a primitive type or {@code false} otherwise
	 */
	public static boolean isPrimitive(@Nullable Object obj) {
		return UObject.isNotNull(obj) && isPrimitive(obj.getClass());
	}
	
	/**
	 * Returns the inheritance of a class recursively. In this way, it is possible to know
	 * from how many classes a class inherits functionalities. Although it is possible to
	 * only obtain a certain number of elements of that inheritance.
	 *
	 * @param cls  the class you want to inspect
	 * @param deep the inheritance boundary that you want to traverse. In addition to the limit, there are
	 *             2 special cases, which are used for other behavior:
	 *             <ul>
	 *             <li><b>Case 0</b>: This case makes the inheritance recursive until reaching the "Object" class,
	 *             which is the limit of all Java classes.</li>
	 *             <li><b>Case 1</b>: This case causes only the current class to be listed and no other.</li>
	 *             </ul>
	 * @return a stack with all the inheritance elements of the chosen class
	 */
	public static @NotNull Stack<Class<?>> classStack(@NotNull Class<?> cls, int deep) {
		requireNotNull(cls, "cls");
		// Temporal variables
		var result = new Stack<Class<?>>();
		int step = 0;
		
		// Iterate all elements
		do {
			result.push(cls);
			// Get parent class
			cls = cls.getSuperclass();
			
			// Only update if deep is not 0
			if (deep > 0) step++;
		} while (cls != Object.class && (deep == FULL_CLASS_STACK || step < deep));
		
		// Get stack result
		return result;
	}
	
	/**
	 * Returns the inheritance of a class recursively. In this way, it is possible to know
	 * from how many classes a class inherits functionalities. Although it is possible to
	 * only obtain a certain number of elements of that inheritance.
	 *
	 * @param cls the class you want to inspect
	 * @return a stack with all the inheritance elements of the chosen class
	 */
	public static @NotNull Stack<Class<?>> classStack(@NotNull Class<?> cls) {
		return classStack(cls, FULL_CLASS_STACK);
	}
	
	/* -----------------------------------------------------
	 * Array methods
	 * ----------------------------------------------------- */
	
	/**
	 * Checks if the class of the argument is of some primitive type or some
	 * wrapper class corresponding to the primitive type (version for arrays).
	 *
	 * @param cls the class to inspect
	 * @return {@code true} if the class is a primitive type or {@code false} otherwise
	 */
	public static boolean isArrayPrimitive(@NotNull Class<?> cls) {
		requireNotNull(cls, "cls");
		return cls.isArray() && UArrays.contains(PRIMITIVE_ARRAY_CLASSES, cls);
	}
	
	/**
	 * Checks if the class of the argument is of some primitive type or some
	 * wrapper class corresponding to the primitive type (version for arrays).
	 *
	 * @param obj the object to inspect
	 * @return {@code true} if the class is a primitive type or {@code false} otherwise
	 */
	public static boolean isArrayPrimitive(@Nullable Object obj) {
		return !UObject.isNull(obj) && isArrayPrimitive(obj.getClass());
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
	
	/* -----------------------------------------------------
	 * Conversion methods
	 * ----------------------------------------------------- */
	
	/**
	 * Returns the data type of all passed elements. This method is useful for knowing
	 * through reflection which method you want to call.
	 *
	 * @param args the arguments to convert
	 * @return an array with all element types
	 */
	public static Class<?> @NotNull [] toVarargTypes(Object... args) {
		return Arrays.stream(args)
			.map(it -> it == null ? Object.class : it.getClass())
			.toArray(Class[]::new);
	}
	
	/* -----------------------------------------------------
	 * Internal methods
	 * ----------------------------------------------------- */
	
	/**
	 * Returns the individual name of a class
	 *
	 * @param cls the class you want to identify
	 * @param <T> the class of the data type
	 * @return the data type of the elements individually
	 */
	private static <T> @NotNull Class<T> getArrayIndividualClassImpl(@NotNull Class<?> cls) {
		requireNotNull(cls, "cls");
		// Check if class is an array
		if (!cls.isArray()) throw new IllegalArgumentException("Invalid array type");
		String generalName = cls.getCanonicalName();
		int index = UArrays.indexOf(PRIMITIVE_ARRAY_CLASSES, cls);
		
		// Only for primitive types
		if (index != INDEX_NOT_FOUND) {
			return cast(PRIMITIVE_ARRAY_INDIVIDUAL[index]);
		}
		
		// Clean class name
		generalName = generalName.replaceAll("\\[]", "")
			.trim();
		try {
			return cast(Class.forName(generalName));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
