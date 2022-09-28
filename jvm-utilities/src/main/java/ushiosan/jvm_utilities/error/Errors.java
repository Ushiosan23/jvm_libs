package ushiosan.jvm_utilities.error;

import static ushiosan.jvm_utilities.lang.Obj.cast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import org.jetbrains.annotations.NotNull;
import ushiosan.jvm_utilities.lang.Cls;

public final class Errors {

	/**
	 * This class cannot be instantiated.
	 * <p>
	 * Singleton or utility class mode.
	 */
	private Errors() {
	}

	/* -----------------------------------------------------
	 * Launch methods
	 * ----------------------------------------------------- */

	/**
	 * Launch specific exception.
	 * <p>
	 * Not recommended for use as manual instance substitution, as it can be very
	 * unsafe when invoking constructors.
	 *
	 * @param clazz the exception to invoke
	 * @param args  exception arguments
	 * @param <T>   exception type
	 * @throws RuntimeException error if the instance could not be created
	 * @throws T                selected error generation
	 */
	public static <T extends Throwable> void launch(@NotNull Class<T> clazz, Object... args) throws T {
		T errorInstance;
		try {
			Constructor<T> constructor = clazz.getDeclaredConstructor(Cls.toTypeArgs(args));
			errorInstance = cast(constructor.newInstance(args), clazz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		throw errorInstance;
	}

	/* -----------------------------------------------------
	 * Information methods
	 * ----------------------------------------------------- */

	/**
	 * Returns the base cause of the exception. This method tries to find the
	 * main or root cause of the exception, it returns the same object if
	 * the exception is already the main cause.
	 *
	 * @param base base exception to analyze
	 * @return returns the root cause of exception
	 */
	public static @NotNull Throwable getRootCause(@NotNull Throwable base) {
		// Store temporal result
		Throwable parent, result = base;

		// Get root cause
		while ((parent = result.getCause()) != null) {
			result = parent;
		}
		return result;
	}

	/**
	 * Returns the exception stack trace in text format.
	 *
	 * @param error    The exception to analyze
	 * @param simplify if state is {@code true} only the main cause will be taken into account
	 *                 or all the error will be taken into account if the state is {@code false}
	 * @return Returns the exception stack trace string
	 */
	public static @NotNull String toString(@NotNull Throwable error, boolean simplify) {
		// Temporal variables
		Throwable realError = simplify ? getRootCause(error) : error;
		StringWriter writer = new StringWriter();
		PrintWriter printer = new PrintWriter(writer);

		// Extract the error stack-trace
		realError.printStackTrace(printer);
		printer.flush(); // print possible buffered text

		return writer.toString();
	}

}
