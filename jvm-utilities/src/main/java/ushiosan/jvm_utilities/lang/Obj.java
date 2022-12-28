package ushiosan.jvm_utilities.lang;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ushiosan.jvm_utilities.function.Apply;
import ushiosan.jvm_utilities.lang.collection.elements.Pair;
import ushiosan.jvm_utilities.lang.print.PrintObj;
import ushiosan.jvm_utilities.lang.print.annotations.PrintExclude;
import ushiosan.jvm_utilities.lang.print.annotations.PrintOpts;

import java.lang.ref.WeakReference;
import java.util.Optional;

public final class Obj {
	
	/**
	 * This class cannot be instantiated.
	 * <p>
	 * Singleton or utility class mode.
	 */
	private Obj() {
	}
	
	/* -----------------------------------------------------
	 * Object string methods
	 * ----------------------------------------------------- */
	
	/**
	 * converts any object to a generic object. Even for primitive types.
	 *
	 * @param obj the object to convert
	 * @return the same object but with different type.
	 * 	For primitive types, these are wrapped in their wrapper
	 * 	classes, and it is possible to perform the operations of a normal object.
	 */
	public static @NotNull Object toObject(@NotNull Object obj) {
		return obj;
	}
	
	/**
	 * Object string representation
	 *
	 * @param obj object string representation
	 * @return object string representation
	 * @see PrintObj#toString(Object, boolean)
	 * @see #toObjString(Object)
	 */
	public static @NotNull String toString(@Nullable Object obj) {
		return PrintObj.toString(obj, false);
	}
	
	/**
	 * Object string representation.
	 * <p>
	 * The same behavior as the {@link #toString(Object)} method but with a different name to be able to do the following:
	 * <pre>{@code
	 * import static ushiosan.jvm_utilities.lang.Obj.toObjString;
	 *
	 * // File content...
	 *
	 * public void myMethod() {
	 *     // Some code ...
	 *     System.out.println(toObjString(myVar));
	 * }
	 * }</pre>
	 *
	 * @param obj object string representation
	 * @return object string representation
	 * @see PrintObj#toString(Object, boolean)
	 * @see #toString(Object)
	 */
	public static @NotNull String toObjString(@Nullable Object obj) {
		return toString(obj);
	}
	
	/**
	 * Loop through the entire object and create a representation of the
	 * object in a text string. This behavior can be configured using class annotations
	 * such as {@link PrintOpts} and {@link PrintExclude}.
	 *
	 * @param instance the object to transform
	 * @return object string representation
	 */
	public static @NotNull String toInstanceString(@NotNull Object instance) {
		return PrintObj.toInstanceString(instance);
	}
	
	/**
	 * Object string representation.
	 * <p>
	 * This method displays more detailed information about the object.
	 *
	 * @param obj object string representation
	 * @return object string representation
	 * @see PrintObj#toString(Object, boolean)
	 */
	public static @NotNull String toDetailString(@Nullable Object obj) {
		return PrintObj.toString(obj, true);
	}
	
	/* -----------------------------------------------------
	 * Nullable methods
	 * ----------------------------------------------------- */
	
	/**
	 * Checks if an object or reference is null. This method also
	 * checks if an object of type {@link Optional} is empty (this means that the reference is null).
	 *
	 * @param obj the object to inspect
	 * @return {@code true} if reference is {@code null} or {@code false} otherwise
	 * @see #isNotNull(Object)
	 */
	public static boolean isNull(@Nullable Object obj) {
		if (obj == null) return true;
		// Weak reference object check
		if (canCastNotNull(obj, WeakReference.class)) {
			return cast(obj, WeakReference.class).get() == null;
		}
		if (canCastNotNull(obj, Optional.class)) {
			return cast(obj, Optional.class).isEmpty();
		}
		return false;
	}
	
	/**
	 * Checks if an object or reference is not null. This method also
	 * checks if an object of type {@link Optional} is empty (this means that the reference is null).
	 *
	 * @param obj the object to inspect
	 * @return {@code true} if reference is not {@code null} or {@code false} otherwise
	 * @see #isNull(Object)
	 */
	public static boolean isNotNull(@Nullable Object obj) {
		return !isNull(obj);
	}
	
	/**
	 * Method used to prevent null references and avoid errors for this type of objects.
	 *
	 * @param obj        the object to inspect
	 * @param defaultVal default value used, when reference is null
	 * @param <T>        generic object type
	 * @return the original reference if not {@code null} or the default value otherwise
	 */
	public static <T> @NotNull T notNull(@Nullable T obj, @NotNull T defaultVal) {
		return isNull(obj) ? defaultVal : obj;
	}
	
	/**
	 * Method used to prevent null references and avoid errors for this type of objects.
	 *
	 * @param obj    the object to inspect
	 * @param action the action that is executed when the reference is not null
	 * @param <T>    generic object type
	 */
	public static <T> void notNull(@Nullable T obj, @NotNull Apply.Empty<T> action) {
		if (!isNull(obj)) action.apply(obj);
	}
	
	/* -----------------------------------------------------
	 * Casting methods
	 * ----------------------------------------------------- */
	
	/**
	 * Recast the object towards the assigned destination.
	 * <p>
	 * <b>Note: </b> this method returns the result depending on the context
	 * <p>
	 * <b>Be careful: </b> this method must be sure that the object is
	 * of the desired type, or it will generate an error.
	 *
	 * @param obj the object to convert
	 * @param <T> class cast type
	 * @return the transformed object
	 * @throws ClassCastException error if object is not compatible type
	 */
	@SuppressWarnings("unchecked")
	public static <T> T cast(@Nullable Object obj) {
		return (T) obj;
	}
	
	/**
	 * Recast the object towards the assigned destination.
	 * <p>
	 * <b>Be careful: </b> this method must be sure that the object is
	 * of the desired type, or it will generate an error.
	 *
	 * @param obj   the object to convert
	 * @param clazz target class to convert
	 * @param <T>   class cast type
	 * @return the transformed object
	 * @throws ClassCastException error if object is not compatible type
	 */
	@Contract(value = "_, _ -> param1", pure = true)
	public static <T> T cast(@Nullable Object obj, @NotNull Class<T> clazz) {
		if (obj == null) return cast(null);
		Class<?> objCls = obj.getClass();
		if (canCast(obj, clazz)) {
			return cast(obj);
		}
		throw new ClassCastException("Cannot cast " + objCls.getName() + " to " + clazz.getName());
	}
	
	public static <T> T pairCast(@NotNull Pair<?, Class<T>> pair) {
		return cast(pair.first, pair.second);
	}
	
	/**
	 * Tries to recast the object towards the assigned destination, but does not generate an error.
	 * Instead, the action passed as the third parameter is executed, as long as the condition is true.
	 *
	 * @param obj    the object to convert
	 * @param clazz  target class to convert
	 * @param action the action to execute if casting is valid
	 * @param <T>    class cast type
	 */
	public static <T> void tryCast(@Nullable Object obj, @NotNull Class<T> clazz, Apply.Empty<T> action) {
		if (canCast(obj, clazz)) action.apply(cast(obj, clazz));
	}
	
	/**
	 * Tries to recast the object towards the assigned destination, but does not generate an error.
	 * Instead, the action passed as the third parameter is executed, as long as the condition is true.
	 *
	 * @param obj   the object to convert
	 * @param clazz target class to convert
	 * @param <T>   class cast type
	 * @return the transformed object or {@link Optional#empty()} if object is not compatible type
	 */
	public static <T> Optional<T> tryCast(@Nullable Object obj, @NotNull Class<T> clazz) {
		if (canCast(obj, clazz)) {
			return Optional.ofNullable(cast(obj, clazz));
		} else {
			return Optional.empty();
		}
	}
	
	/**
	 * Check if one object can be cast to another type.
	 * <p>
	 * <b>Be careful: </b> Remember that null values are valid candidates to change to
	 * any data type (except primitive types).
	 *
	 * @param obj   the object to check
	 * @param clazz target class to convert
	 * @param <T>   class type
	 * @return return {@code true} if the object can be converted to the desired type or {@code false} otherwise
	 */
	public static <T> boolean canCast(@Nullable Object obj, @NotNull Class<T> clazz) {
		return canCastImpl(obj, clazz, true);
	}
	
	/**
	 * Check if one object can be cast to another type.
	 *
	 * @param obj   the object to check
	 * @param clazz target class to convert
	 * @param <T>   class type
	 * @return return {@code true} if the object can be converted to the desired type or {@code false} otherwise
	 */
	public static <T> boolean canCastNotNull(@Nullable Object obj, @NotNull Class<T> clazz) {
		return canCastImpl(obj, clazz, false);
	}
	
	/* -----------------------------------------------------
	 * Apply methods
	 * ----------------------------------------------------- */
	
	/**
	 * Applies configuration to an object based on a local context. Returns the
	 * same object but with the configuration already applied.
	 *
	 * @param obj    the base object to configure
	 * @param action the action to execute
	 * @param <T>    object type
	 * @return the same object but with the configuration already applied
	 */
	@Contract("_, _ -> param1")
	public static <T> @NotNull T also(@NotNull T obj, Apply.@NotNull Empty<T> action) {
		action.apply(obj);
		return obj;
	}
	
	/**
	 * Applies configuration to an object based on a local context. Returns a
	 * different object depending on the applied configuration.
	 *
	 * @param obj    the base object to configure
	 * @param action the action to execute
	 * @param <T>    object type
	 * @param <V>    result type
	 * @return a different object depending on the applied configuration.
	 */
	public static <T, V> @NotNull V apply(@NotNull T obj, Apply.@NotNull Result<T, V> action) {
		return action.apply(obj);
	}
	
	/* -----------------------------------------------------
	 * Internal methods
	 * ----------------------------------------------------- */
	
	/**
	 * Check if one object can be cast to another type.
	 * <p>
	 * <b>Be careful: </b> Remember that null values are valid candidates to change to
	 * any data type (except primitive types).
	 *
	 * @param obj      the object to check
	 * @param clazz    target class to convert
	 * @param nullable param used to accept null objects
	 * @param <T>      class type
	 * @return return {@code true} if the object can be converted to the desired type or {@code false} otherwise
	 */
	private static <T> boolean canCastImpl(@Nullable Object obj, @NotNull Class<T> clazz, boolean nullable) {
		if (obj == null) return nullable;
		Class<?> objCls = obj.getClass();
		return objCls.isAssignableFrom(clazz) || clazz.isInstance(obj);
	}
	
}
