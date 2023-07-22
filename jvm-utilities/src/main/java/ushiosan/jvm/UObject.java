package ushiosan.jvm;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ushiosan.jvm.collections.UArray;
import ushiosan.jvm.function.UEmptyFun;
import ushiosan.jvm.internal.validators.UObjectValidators;
import ushiosan.jvm.print.UToStringManager;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Optional;

import static ushiosan.jvm.error.UCommonErrorMessages.requireNotNullError;

public final class UObject extends UObjectValidators {
	
	/**
	 * This class cannot be instantiated
	 */
	private UObject() {}
	
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
		// Check a simple null type
		if (obj == null) return true;
		
		// Check other types
		for (var validator : NULLABLE_VALIDATORS) {
			if (obj.getClass() == validator.first) {
				return validator.second.invoke(obj);
			}
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
	 * @param obj          the object to inspect
	 * @param defaultValue default value used when reference is null
	 * @param <T>          generic object type
	 * @return the original reference if not {@code null} or the default value otherwise
	 * @throws IllegalArgumentException error if {@code defaultValue} is {@code null}
	 */
	public static <T> @NotNull T notNull(@Nullable T obj, @NotNull T defaultValue) {
		requireNotNull(defaultValue, "defaultValue");
		// Check result
		return isNull(obj) ? defaultValue : obj;
	}
	
	/**
	 * Method used to prevent null references and avoid errors for this type of objects.
	 *
	 * @param obj    the object to inspect
	 * @param action the action that is executed when the reference is not null
	 * @param <T>    generic object type
	 * @throws IllegalArgumentException error if {@code action} is {@code null}
	 */
	public static <T> void notNull(@Nullable T obj, UEmptyFun.@NotNull UEmptyFun1<T> action) {
		requireNotNull(action, "action");
		// Invoke the action only if the obj is not null
		if (isNotNull(obj)) action.invoke(obj);
	}
	
	/**
	 * Checks that a given argument is not null
	 *
	 * @param obj  the object to inspect
	 * @param name the parameter name
	 * @throws IllegalArgumentException error when the value passed as a parameter is a null value
	 */
	public static void requireNotNull(@Nullable Object obj, @Nullable String name) {
		if (obj != null) return;
		throw new IllegalArgumentException(requireNotNullError(name));
	}
	
	/**
	 * Checks that a given argument is not null
	 *
	 * @param obj the object to inspect
	 */
	public static void requireNotNull(@Nullable Object obj) {
		requireNotNull(obj, null);
	}
	
	/* -----------------------------------------------------
	 * Casting methods
	 * ----------------------------------------------------- */
	
	/**
	 * Check if one object can be cast to another type.
	 * <p>
	 * <b>Be careful: </b> Remember that null values are valid candidates to change to
	 * any data type (except primitive types).
	 *
	 * @param obj      the object to check
	 * @param cls      target class to convert
	 * @param nullable param used to accept null objects
	 * @return return {@code true} if the object can be converted to the desired type or {@code false} otherwise
	 */
	public static boolean canCast(@Nullable Object obj, @NotNull Class<?> cls, boolean nullable) {
		requireNotNull(cls, "cls");
		// Check if an object is null
		if (obj == null) return nullable;
		// Try to cast the object
		Class<?> objCls = obj.getClass();
		return objCls.isAssignableFrom(cls) || cls.isInstance(obj);
	}
	
	/**
	 * Check if one object can be cast to another type.
	 * <p>
	 * <b>Be careful: </b> Remember that null values are valid candidates to change to
	 * any data type (except primitive types).
	 *
	 * @param obj the object to check
	 * @param cls target class to convert
	 * @return return {@code true} if the object can be converted to the desired type or {@code false} otherwise
	 */
	public static boolean canCast(@Nullable Object obj, @NotNull Class<?> cls) {
		return canCast(obj, cls, true);
	}
	
	/**
	 * Check if one object can be cast to another type.
	 * <p>
	 * <b>Be careful: </b> Remember that null values are valid candidates to change to
	 * any data type (except primitive types).
	 *
	 * @param obj the object to check
	 * @param cls target class to convert
	 * @return return {@code true} if the object can be converted to the desired type or {@code false} otherwise
	 */
	public static boolean canCastNotNull(@Nullable Object obj, @NotNull Class<?> cls) {
		return canCast(obj, cls, false);
	}
	
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
	 * @param obj the object to convert
	 * @param cls target class to convert
	 * @param <T> class cast type
	 * @return the transformed object
	 * @throws ClassCastException error if object is not compatible type
	 */
	public static <T> T cast(@Nullable Object obj, @NotNull Class<T> cls) {
		requireNotNull(cls, "cls");
		// Check null
		if (obj == null) return cast(null);
		// Try to cast
		Class<?> clsObj = obj.getClass();
		if (canCast(obj, clsObj, false)) {
			return cast(obj);
		}
		// Launch an error
		throw new ClassCastException(String.format(
			"Cannot cast %s to %s", clsObj.getName(), cls.getName()));
	}
	
	/**
	 * Tries to recast the object towards the assigned destination, but does not generate an error.
	 * Instead, the action passed as the third parameter is executed, as long as the condition is true.
	 *
	 * @param obj    the object to convert
	 * @param cls    the class to which you want to promote
	 * @param action the action to execute if casting is valid
	 * @param <T>    class cast type
	 * @throws IllegalArgumentException error if {@code cls} or {@code action} are {@code null}
	 */
	public static <T> void tryCast(@Nullable Object obj, @NotNull Class<T> cls, UEmptyFun.@NotNull UEmptyFun1<T> action) {
		requireNotNull(cls, "cls");
		requireNotNull(action, "action");
		// Verify that the object can be promoted to the defined class
		if (canCast(obj, cls)) action.invoke(cast(obj));
	}
	
	/**
	 * Tries to recast the object towards the assigned destination, but does not generate an error.
	 * Instead, the action passed as the third parameter is executed, as long as the condition is true.
	 *
	 * @param obj the object to convert
	 * @param cls the class to which you want to promote
	 * @param <T> class cast type
	 * @return the object to the target data type or {@link Optional#empty()} if the cast is invalid
	 * @throws IllegalArgumentException error if {@code cls} is {@code null}
	 */
	public static <T> @NotNull Optional<T> tryCast(@Nullable Object obj, @NotNull Class<T> cls) {
		requireNotNull(cls, "cls");
		// Verify that the object can be promoted to the defined class
		return canCast(obj, cls) ? Optional.ofNullable(cast(obj)) :
			   Optional.empty();
	}
	
	/* -----------------------------------------------------
	 * ToString object
	 * ----------------------------------------------------- */
	
	/**
	 * Generates a text with the representation of the object.
	 * Very similar to what the {@link Object#toString()} method does, but it ensures
	 * that all objects have an easily identifiable representation.
	 *
	 * @param object  the object that you want to get the text representation
	 * @param verbose option used to determine if the output will be long or simple
	 * @return object string representation
	 */
	public static @NotNull String toString(@Nullable Object object, boolean verbose) {
		return UToStringManager.getInstance()
			.toString(object, verbose);
	}
	
	/**
	 * Generates a text with the representation of the object.
	 * Very similar to what the {@link Object#toString()} method does, but it ensures
	 * that all objects have an easily identifiable representation.
	 *
	 * @param object the object that you want to get the text representation
	 * @return object string representation
	 */
	public static @NotNull String toString(@Nullable Object object) {
		return toString(object, false);
	}
	
	/* -----------------------------------------------------
	 * Simple print methods
	 * ----------------------------------------------------- */
	
	/**
	 * Write information into a data stream
	 *
	 * @param printStream the data stream you want to write
	 * @param format      the data writing format
	 * @param args        the data format arguments
	 * @see #toString(Object)
	 * @see PrintStream#printf(String, Object...)
	 */
	public static void printRef(@NotNull PrintStream printStream, @Nullable Object format, Object @Nullable ... args) {
		printRefImpl(printStream, format, null, false, args);
	}
	
	/**
	 * Write information into a data stream (insert a line break at the end of the content)
	 *
	 * @param printStream the data stream you want to write
	 * @param format      the data writing format
	 * @param args        the data format arguments
	 * @see #toString(Object)
	 * @see PrintStream#printf(String, Object...)
	 */
	public static void printlnRef(@NotNull PrintStream printStream, @Nullable Object format, Object @Nullable ... args) {
		printRefImpl(printStream, format, "%n", false, args);
	}
	
	/**
	 * Write information into a data stream
	 *
	 * @param format the data writing format
	 * @param args   the data format arguments
	 * @see #printRef(PrintStream, Object, Object...)
	 */
	public static void print(@Nullable Object format, Object @Nullable ... args) {
		printRef(System.out, format, args);
	}
	
	/**
	 * Write information into a data stream
	 *
	 * @param format the data writing format
	 * @param args   the data format arguments
	 * @see #printRef(PrintStream, Object, Object...)
	 */
	public static void printErr(@Nullable Object format, Object @Nullable ... args) {
		printRef(System.err, format, args);
	}
	
	/**
	 * Write information into a data stream (insert a line break at the end of the content)
	 *
	 * @param format the data writing format
	 * @param args   the data format arguments
	 * @see #printlnRef(PrintStream, Object, Object...)
	 */
	public static void println(@NotNull Object format, Object @Nullable ... args) {
		printlnRef(System.out, format, args);
	}
	
	/**
	 * Write information into a data stream (insert a line break at the end of the content)
	 *
	 * @param format the data writing format
	 * @param args   the data format arguments
	 * @see #printlnRef(PrintStream, Object, Object...)
	 */
	public static void printlnErr(@NotNull Object format, Object @Nullable ... args) {
		printlnRef(System.err, format, args);
	}
	
	/**
	 * Write information into a data stream (insert a line break at the end of the content)
	 *
	 * @see #printlnRef(PrintStream, Object, Object...)
	 */
	public static void println() {
		println("");
	}
	
	/* -----------------------------------------------------
	 * Verbose print methods
	 * ----------------------------------------------------- */
	
	/**
	 * Write information into a data stream
	 *
	 * @param printStream the data stream you want to write
	 * @param format      the data writing format
	 * @param args        the data format arguments
	 * @see #toString(Object)
	 * @see PrintStream#printf(String, Object...)
	 */
	public static void printRefV(@NotNull PrintStream printStream, @Nullable Object format, Object @Nullable ... args) {
		printRefImpl(printStream, format, null, true, args);
	}
	
	/**
	 * Write information into a data stream (insert a line break at the end of the content)
	 *
	 * @param printStream the data stream you want to write
	 * @param format      the data writing format
	 * @param args        the data format arguments
	 * @see #toString(Object)
	 * @see PrintStream#printf(String, Object...)
	 */
	public static void printlnRefV(@NotNull PrintStream printStream, @Nullable Object format, Object @Nullable ... args) {
		printRefImpl(printStream, format, "%n", true, args);
	}
	
	/**
	 * Write information into a data stream
	 *
	 * @param format the data writing format
	 * @param args   the data format arguments
	 * @see #printRefV(PrintStream, Object, Object...)
	 */
	public static void printV(@Nullable Object format, Object @Nullable ... args) {
		printRefV(System.out, format, args);
	}
	
	/**
	 * Write information into a data stream
	 *
	 * @param format the data writing format
	 * @param args   the data format arguments
	 * @see #printRefV(PrintStream, Object, Object...)
	 */
	public static void printErrV(@Nullable Object format, Object @Nullable ... args) {
		printRefV(System.err, format, args);
	}
	
	/**
	 * Write information into a data stream (insert a line break at the end of the content)
	 *
	 * @param format the data writing format
	 * @param args   the data format arguments
	 * @see #printlnRefV(PrintStream, Object, Object...)
	 */
	public static void printlnV(@NotNull Object format, Object @Nullable ... args) {
		printlnRefV(System.out, format, args);
	}
	
	/**
	 * Write information into a data stream (insert a line break at the end of the content)
	 *
	 * @param format the data writing format
	 * @param args   the data format arguments
	 * @see #printlnRefV(PrintStream, Object, Object...)
	 */
	public static void printlnErrV(@NotNull Object format, Object @Nullable ... args) {
		printlnRefV(System.err, format, args);
	}
	
	/* -----------------------------------------------------
	 * Internal methods
	 * ----------------------------------------------------- */
	
	/**
	 * Write information into a data stream
	 *
	 * @param printStream the data stream you want to write
	 * @param format      the data writing format
	 * @param suffix      information inserted at the end of the content
	 * @param verbose     true if the information should be extensive or false otherwise
	 * @param args        the data format arguments
	 */
	private static void printRefImpl(@NotNull PrintStream printStream, @Nullable Object format, @Nullable String suffix,
		boolean verbose, Object @Nullable [] args) {
		requireNotNull(printStream, "printStream");
		// Generate output format
		String formatString;
		if (format == null || !canCastNotNull(format, CharSequence.class)) {
			formatString = toString(format, verbose);
		} else {
			formatString = cast(format, CharSequence.class).toString();
		}
		// Transform output arguments
		Object[] outArgs = Arrays.stream(notNull(args, UArray.OBJ_EMPTY))
			.map(it -> UClass.isPrimitive(it.getClass()) || canCastNotNull(it, CharSequence.class) ? it : toString(it, verbose))
			.toArray();
		// Send result
		suffix = notNull(suffix, "");
		printStream.printf(formatString + suffix, outArgs);
	}
	
}
