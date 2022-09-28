package ushiosan.jvm_utilities.lang.print;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import ushiosan.jvm_utilities.function.Apply;
import ushiosan.jvm_utilities.internal.print.instance.PrintInstance;
import ushiosan.jvm_utilities.internal.print.str.BasePrintObject;
import ushiosan.jvm_utilities.lang.collection.elements.Pair;
import ushiosan.jvm_utilities.lang.print.annotations.PrintExclude;
import ushiosan.jvm_utilities.lang.print.annotations.PrintOpts;

public final class PrintObj {

	/* -----------------------------------------------------
	 * Constructors
	 * ----------------------------------------------------- */

	/**
	 * This class cannot be instantiated.
	 * <p>
	 * Singleton or utility class mode.
	 */
	private PrintObj() {
	}

	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */

	/**
	 * Object string representation
	 *
	 * @param obj     object string representation
	 * @param verbose output verbose mode
	 * @return object string representation
	 */
	public static @NotNull String toString(@Nullable Object obj, boolean verbose) {
		return BasePrintObject
			.getInstance(verbose)
			.toString(obj);
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
		return PrintInstance
			.getInstance()
			.toString(instance);
	}

	/**
	 * Insert new printable extension to the instance
	 *
	 * @param verbose   determines the level of detail in the output of the content
	 * @param extension the extension to insert
	 */
	public void attachExtension(boolean verbose, @NotNull Pair<Apply.Result<Object, String>, Class<?>[]> extension) {
		BasePrintObject.getInstance(verbose)
			.attachExtension(extension);
	}

	/**
	 * Insert new printable extension to the instance
	 *
	 * @param verbose   determines the level of detail in the output of the content
	 * @param action    the extension action
	 * @param supported supported elements
	 */
	public void attachExtension(boolean verbose, @NotNull Apply.Result<Object, String> action, Class<?> @NotNull ... supported) {
		BasePrintObject.getInstance(verbose)
			.attachExtension(action, supported);
	}

}
