package com.github.ushiosan23.jvm.system.error;

import org.jetbrains.annotations.NotNull;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class ExceptionUtils {

	/**
	 * This class cannot be instantiated
	 */
	private ExceptionUtils() {
	}

	/* ------------------------------------------------------------------
	 * Methods
	 * ------------------------------------------------------------------ */

	/**
	 * Returns the base cause of the exception. This method tries to find the
	 * main or root cause of the exception, it returns the same object if
	 * the exception is already the main cause.
	 *
	 * @param throwable The exception to be analyzed
	 * @return Returns the root cause of the exception
	 */
	public static @NotNull Throwable getDeepCause(@NotNull Throwable throwable) {
		Throwable result = throwable;

		while (result.getCause() != null) {
			result = result.getCause();
		}

		return result;
	}

	/**
	 * Returns the exception stack trace in text format.
	 *
	 * @param throwable The exception to be analyzed
	 * @param verbose   Determines if the result is going to be verbose or simple
	 * @return Returns the exception stack trace string
	 */
	public static @NotNull String toString(@NotNull Throwable throwable, boolean verbose) {
		// Store error
		Throwable error = verbose ? throwable : getDeepCause(throwable);
		// Initialize elements
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		error.printStackTrace(pw);
		pw.flush();

		return sw.toString();
	}

}
