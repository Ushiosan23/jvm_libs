package ushiosan.jvm_utilities.lang.reflection;

import org.jetbrains.annotations.NotNull;

import java.util.Stack;

/**
 * Class in charge of carrying out reflection processes to the classes
 */
public final class ClassUtils {
	
	/**
	 * Private constructor
	 */
	private ClassUtils() {}
	
	/* ---------------------------------------------------------
	 * Methods
	 * --------------------------------------------------------- */
	
	/**
	 * Returns the inheritance stack of a class. This excludes the {@link Object} class because its inclusion is redundant.
	 *
	 * @param cls       the class you want to inspect
	 * @param recursive Perform the action recursively until the start of the inheritance is reached
	 * @return the inheritance stack of a class
	 */
	public static @NotNull Stack<Class<?>> getClassStack(@NotNull Class<?> cls, boolean recursive) {
		Stack<Class<?>> result = new Stack<>();
		
		do {
			result.push(cls);
			cls = cls.getSuperclass();
		} while (cls != Object.class && recursive);
		return result;
	}
	
	/**
	 * Returns the inheritance stack of a class. This excludes the {@link Object} class because its inclusion is redundant.
	 *
	 * @param cls the class you want to inspect
	 * @return the inheritance stack of a class
	 */
	public static @NotNull Stack<Class<?>> getClassStack(@NotNull Class<?> cls) {
		return getClassStack(cls, true);
	}
	
}
