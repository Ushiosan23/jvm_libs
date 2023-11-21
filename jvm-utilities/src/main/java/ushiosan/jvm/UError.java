package ushiosan.jvm;

import org.jetbrains.annotations.NotNull;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class UError {
	
	/**
	 * This class cannot be instantiated directly
	 */
	private UError() {}
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Gets the base cause of the exception.
	 * This method tries to find the main or root cause of the
	 * exception, it returns the same object if the exception
	 * is already the main cause.
	 *
	 * @param exception base exception to analyze
	 * @param deep      the maximum size of steps before reaching
	 *                  the root of the error.
	 *                  If the value is {@code 0}, then it tries to return
	 *                  the original cause of the error (recursive).
	 * @return the root cause of error
	 */
	public static @NotNull Throwable rootError(@NotNull Throwable exception, int deep) {
		UObject.requireNotNull(exception, "exception");
		deep = Math.max(0, deep);
		// Temporal variables
		Throwable exceptionTmp, result = exception;
		int step = 0;
		
		// Iterate all elements
		while ((deep == 0 || step < deep) && (exceptionTmp = result.getCause()) != null) {
			result = exceptionTmp;
			step++;
		}
		
		return result;
	}
	
	/**
	 * Gets the base cause of the exception.
	 * This method tries to find the main or root cause of the
	 * exception, it returns the same object if the exception
	 * is already the main cause.
	 *
	 * @param exception base exception to analyze
	 * @return the root cause of error
	 * @see #rootError(Throwable, int)
	 */
	public static @NotNull Throwable rootError(@NotNull Throwable exception) {
		return rootError(exception, 0);
	}
	
	/**
	 * Gets the content of an exception in text format.
	 *
	 * @param exception base exception to analyze
	 * @param deep      the maximum size of steps before reaching
	 *                  the root of the error.
	 *                  If the value is {@code 0}, then it tries to return
	 *                  the original cause of the error (recursive).
	 * @return The content of the exception in text format
	 */
	public static @NotNull String extractTrace(@NotNull Throwable exception, int deep) {
		UObject.requireNotNull(exception, "exception");
		// Throwable extraction variables
		StringWriter writer = new StringWriter();
		PrintWriter printer = new PrintWriter(writer);
		Throwable exceptionTraced = rootError(exception, deep);
		
		// Extract all exception content
		exceptionTraced.printStackTrace(printer);
		printer.flush();
		
		// Get exception content
		return writer.toString();
	}
	
	/**
	 * Gets the content of an exception in text format.
	 *
	 * @param exception base exception to analyze
	 * @return The content of the exception in text format
	 */
	public static @NotNull String extractTrace(@NotNull Throwable exception) {
		return extractTrace(exception, 0);
	}
	
}
