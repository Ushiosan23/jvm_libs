package ushiosan.jvm_utilities.lang.reflection;

import org.jetbrains.annotations.NotNull;

import java.util.Stack;

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
	 * @param cls the class you want to inspect
	 * @return the inheritance stack of a class
	 */
	public static @NotNull Stack<Class<?>> getClassStack(@NotNull Class<?> cls) {
		Stack<Class<?>> result = new Stack<>();
		
		do {
			result.push(cls);
			cls = cls.getSuperclass();
		} while (cls != Object.class);
		return result;
	}
	
}
