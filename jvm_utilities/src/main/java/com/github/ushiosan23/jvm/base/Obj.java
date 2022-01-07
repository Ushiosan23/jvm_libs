package com.github.ushiosan23.jvm.base;

import com.github.ushiosan23.jvm.functions.apply.IApply;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Obj {

	/* ------------------------------------------------------------------
	 * Constructors
	 * ------------------------------------------------------------------ */

	/**
	 * This class cannot be instantiated
	 */
	private Obj() {
	}

	/* ------------------------------------------------------------------
	 * Methods
	 * ------------------------------------------------------------------ */

	/**
	 * Get not null value
	 *
	 * @param obj      Object to check if it's null
	 * @param defValue Default value if {@code obj} is null
	 * @param <T>      Generic object type
	 * @return Return {@code obj} if it's not null or {@code defValue} otherwise
	 */
	public static <T> T notNull(@Nullable T obj, T defValue) {
		return obj == null ? defValue : obj;
	}

	/**
	 * Apply configuration to the object and return it as a result
	 *
	 * @param obj    Object to which the action applies
	 * @param action Apply action to execute
	 * @param <T>    Generic object type
	 * @return Returns the same object is returned but with the changes made
	 */
	public static <T> T apply(T obj, @NotNull IApply.WithResult<T, T> action) {
		return action.run(obj);
	}

	/**
	 * It tries to recast the object towards the assigned destination, but does not generate an error.
	 * Instead, the action passed as the third parameter is executed, as long as the condition is true.
	 * <p>
	 * Example:
	 * <pre>{@code
	 *     Object unknownType = "Hello, World!";
	 *
	 *     Obj.tryCast(
	 *        unknownType, // Object to be evaluated
	 *        String.class, // Destination class
	 *        object -> System.out.println(object) // Action that is only executed if the recast is valid
	 *     );
	 * }</pre>
	 *
	 * @param obj    Object to analyze
	 * @param tClass Destination class
	 * @param action action that is executed when the recast is valid.
	 * @param <T>    Generic destination type
	 */
	public static <T> void tryCast(@NotNull Object obj, @NotNull Class<T> tClass, @NotNull IApply.EmptyResult<T> action) {
		// Get result
		T result = tryCast(obj, tClass);
		// Check if result is null
		if (result != null) action.run(result);
	}

	/**
	 * It tries to recast the object towards the assigned destination, but does not generate an error.
	 *
	 * @param obj    Object to analyze
	 * @param tClass Destination class
	 * @param <T>    Generic destination type
	 * @return Returns a recast object or {@code null} if the cast fails
	 */
	@SuppressWarnings("unchecked")
	public static <T> @Nullable T tryCast(@NotNull Object obj, @NotNull Class<T> tClass) {
		// Check if obj is instance of tClass
		if (obj.getClass().isAssignableFrom(tClass) || tClass.isInstance(obj))
			return (T) obj;
		// Return invalid value
		return null;
	}

}
