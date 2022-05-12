package com.github.ushiosan23.jvm.base;

import com.github.ushiosan23.jvm.collections.Containers;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Utilities for the class {@link Class} or similar.
 */
public final class ClassUtils {

	/**
	 * All primitive wrapped classes
	 */
	private static final Set<Class<?>> PRIMITIVE_WRAPPED_TYPES = Containers.setOf(
		Boolean.class,
		Character.class,
		Byte.class,
		Short.class,
		Integer.class,
		Long.class,
		Float.class,
		Double.class,
		Void.class
	);

	/**
	 * This class cannot be instantiated
	 */
	private ClassUtils() {
	}

	/* ------------------------------------------------------------------
	 * Methods
	 * ------------------------------------------------------------------ */

	/**
	 * Checks if the given class is a primitive.
	 *
	 * @param cls The class to check
	 * @return Returns {@code true} if the type is a primitive or one of the
	 * wrapper classes, or {@code false} otherwise.
	 * @see Class#isPrimitive()
	 */
	public static boolean isPrimitive(@NotNull Class<?> cls) {
		return cls.isPrimitive() || PRIMITIVE_WRAPPED_TYPES.contains(cls);
	}

}
