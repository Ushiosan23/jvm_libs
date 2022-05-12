package com.github.ushiosan23.jvm.io;

import com.github.ushiosan23.jvm.base.ClassUtils;
import com.github.ushiosan23.jvm.base.Obj;
import com.github.ushiosan23.jvm.collections.Arr;
import com.github.ushiosan23.jvm.system.error.ExceptionUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PrintUtils {

	/**
	 * Object detector with {@code toString} method by default
	 */
	@SuppressWarnings("RegExpSimplifiable")
	private static final Pattern RAW_OBJ_REGEX = Pattern.compile("@[0-9a-fA-F]+$");

	/**
	 * This class cannot be instantiated
	 */
	private PrintUtils() {
	}

	/**
	 * Determine the type of collection and how it will be printed.
	 */
	public enum CollectionType {
		LIST("[", "]"),
		MAP("{", "}");

		/**
		 * Start of printing
		 */
		public final String start;

		/**
		 * End of print
		 */
		public final String end;

		/**
		 * Default constructor
		 *
		 * @param s Start
		 * @param e End
		 */
		CollectionType(String s, String e) {
			start = s;
			end = e;
		}

	}

	/* ------------------------------------------------------------------
	 * Methods
	 * ------------------------------------------------------------------ */

	/**
	 * Checks if the object has the default "toString" method.
	 *
	 * <p>
	 * <i>Note:</i>
	 * <b>This method is quite slow when parsing many objects, so it
	 * is recommended to use it only in necessary places.</b>
	 *
	 * @param obj The object to analyze
	 * @return Returns {@code true} if the object has a
	 * {@link Object#toString} method of its own, or {@code false} if it has the default behavior.
	 */
	public static boolean isToStringOverwritten(@NotNull Object obj) {
		String str = obj.toString();
		Matcher matcher = RAW_OBJ_REGEX.matcher(str);

		return matcher.find();
	}

	/**
	 * Manages the printing process of an object. This in order to have an informative and clean
	 * way of displaying objects and avoid the default method that can be confusing when debugging.
	 *
	 * @param obj     The object to analyze
	 * @param verbose Determines whether the printer should be wide or short
	 * @return Returns the representation of the object in text
	 */
	@Contract(pure = true)
	public static @NotNull String toString(@Nullable Object obj, boolean verbose) {
		// Check if object is null
		if (obj == null) return "null";

		Class<?> cls = obj.getClass();
		// First check primitive objects
		if (cls.isArray())
			return Arr.toString(obj);
		if (ClassUtils.isPrimitive(cls))
			return obj.toString();

		// Check other types
		if (obj instanceof CharSequence)
			return Obj.castTo(obj, CharSequence.class).toString();
		if (obj instanceof Map<?, ?>)
			return toMapString(Obj.castTo(obj), verbose);
		if (obj instanceof Map.Entry<?, ?>)
			return toEntryString(Obj.castTo(obj), verbose);
		if (obj instanceof Collection<?>)
			return toCollectionString(Obj.castTo(obj), verbose);
		if (obj instanceof Class<?>)
			return toClassString(Obj.castTo(obj), verbose);
		if (obj instanceof Throwable)
			return ExceptionUtils.toString(Obj.castTo(obj), verbose);

		return toRawString(obj, verbose);
	}

	/**
	 * Manages the printing process of an object. This in order to have an informative and clean
	 * way of displaying objects and avoid the default method that can be confusing when debugging.
	 *
	 * @param obj The object to analyze
	 * @return Returns the representation of the object in text
	 */
	@Contract(pure = true)
	public static @NotNull String toString(@Nullable Object obj) {
		return toString(obj, true);
	}

	/* ------------------------------------------------------------------
	 * Printer methods
	 * ------------------------------------------------------------------ */

	/**
	 * {@link Class} object string representation
	 *
	 * @param cls     The class to convert
	 * @param verbose Determines if the result is going to be verbose or simple
	 * @return Returns the object string representation
	 */
	public static @NotNull String toClassString(@NotNull Class<?> cls, boolean verbose) {
		return verbose ? cls.getName() : cls.getSimpleName();
	}

	/**
	 * {@link Collection} object string representation.
	 * Method used for any object that implements the {@link Collection} interface commonly used for any collection of objects.
	 *
	 * @param obj          The collection to convert
	 * @param verbose      Determines if the result is going to be verbose or simple
	 * @param type         The type of collection to generate.
	 * @param verboseItems Property that determines whether each object in the
	 *                     collection will be presented verbose or simply.
	 * @return Returns the object string representation
	 */
	public static @NotNull String toCollectionString(
		@NotNull Collection<?> obj,
		boolean verbose,
		@NotNull CollectionType type,
		boolean verboseItems
	) {
		StringBuilder builder;
		if (type == CollectionType.MAP) {
			builder = new StringBuilder();
		} else {
			builder = baseStringBuilder(obj, verbose);
		}
		// Determine collection size
		builder.append("(").append(obj.size()).append(")");
		// Only verbose option show all list info
		if (!verbose) return builder.toString();

		int max = obj.size() - 1;
		int counter = 0;
		Iterator<?> iterator = obj.iterator();

		// Any collection starts with [
		builder.append(type.start);
		while (iterator.hasNext()) {
			Object it = iterator.next();

			// Check sequence
			builder.append(toString(it, verboseItems));
			if (counter != max) builder.append(", ");

			counter++;
		}
		builder.append(type.end);

		return builder.toString();
	}

	/**
	 * {@link Collection} object string representation.
	 * Method used for any object that implements the {@link Collection} interface commonly used for any collection of objects.
	 *
	 * @param obj     The collection to convert
	 * @param verbose Determines if the result is going to be verbose or simple
	 * @return Returns the object string representation
	 */
	public static @NotNull String toCollectionString(Collection<?> obj, boolean verbose) {
		return toCollectionString(obj, verbose, CollectionType.LIST, false);
	}

	/**
	 * {@link Map} object string representation.
	 *
	 * @param obj     The map to convert
	 * @param verbose Determines if the result is going to be verbose or simple
	 * @return Returns the object string representation
	 */
	public static @NotNull String toMapString(@NotNull Map<?, ?> obj, boolean verbose) {
		StringBuilder builder = baseStringBuilder(obj, verbose);
		// Insert map items
		builder.append(toCollectionString(obj.entrySet(), verbose, CollectionType.MAP, verbose));
		// Get result
		return builder.toString();
	}

	/**
	 * {@link Map.Entry} object string representation.
	 *
	 * @param obj     The entry to convert
	 * @param verbose Determines if the result is going to be verbose or simple
	 * @return Returns the object string representation
	 */
	public static @NotNull String toEntryString(@NotNull Map.Entry<?, ?> obj, boolean verbose) {
		return toString(obj.getKey(), verbose) + " = " + toString(obj.getValue(), verbose);
	}

	/**
	 * Any object string representation.
	 *
	 * @param obj     The object to convert
	 * @param verbose Determines if the result is going to be verbose or simple
	 * @return Returns the object string representation
	 */
	public static @NotNull String toRawString(@NotNull Object obj, boolean verbose) {
		Class<?> cls = obj.getClass();
		String classStr = toClassString(cls, verbose);
		String hasStr = Integer.toHexString(obj.hashCode());

		return String.format("(@%s) %s", hasStr, classStr);
	}

	/* ------------------------------------------------------------------
	 * Internal methods
	 * ------------------------------------------------------------------ */

	/**
	 * Generate an instance of {@link StringBuilder} with the base information for use in other parts of this class.
	 *
	 * @param obj     The base object to obtain the essential information.
	 * @param verbose Determines if the result is going to be verbose or simple
	 * @param prefix  Prefix used to indicate that the base result must have before
	 *                the information that will be inserted after.
	 * @return Returns a {@link StringBuilder} instance with base information
	 */
	private static @NotNull StringBuilder baseStringBuilder(@NotNull Object obj, boolean verbose, @Nullable String prefix) {
		StringBuilder result = new StringBuilder(toClassString(obj.getClass(), verbose));
		if (prefix != null) {
			result.append(prefix);
		}

		return result;
	}

	/**
	 * Generate an instance of {@link StringBuilder} with the base information for use in other parts of this class.
	 *
	 * @param obj     The base object to obtain the essential information.
	 * @param verbose Determines if the result is going to be verbose or simple
	 * @return Returns a {@link StringBuilder} instance with base information
	 */
	private static @NotNull StringBuilder baseStringBuilder(@NotNull Object obj, boolean verbose) {
		return baseStringBuilder(obj, verbose, null);
	}

}
