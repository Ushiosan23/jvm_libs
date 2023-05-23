package ushiosan.jvm;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ushiosan.jvm.print.UToStringManager;

import java.util.logging.Level;
import java.util.logging.Logger;

import static ushiosan.jvm.UObject.requireNotNull;

public final class ULogger {
	
	/**
	 * This class cannot be instantiated
	 */
	private ULogger() {}
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Creates a new logger instance using the assigned class.
	 *
	 * @param cls the class that will register the logger
	 * @return the logger instance
	 * @throws IllegalArgumentException error if {@code cls} is null
	 */
	public static @NotNull Logger getLogger(@NotNull Class<?> cls) {
		requireNotNull(cls, "cls");
		// Generate logger instance
		return Logger.getLogger(UToStringManager.getInstance()
									.toString(cls, true));
	}
	
	/* -----------------------------------------------------
	 * Logger extensions
	 * ----------------------------------------------------- */
	
	/**
	 * It generates an info-type record, and it is only necessary to pass the format, and
	 * it will automatically take care of generating the record.
	 *
	 * @param logger the logger instance
	 * @param format message format
	 * @param args   format arguments
	 * @throws IllegalArgumentException error if {@code logger} or {@code format} are {@code null}
	 */
	public static void logInfo(@NotNull Logger logger, @NotNull String format, Object @Nullable ... args) {
		requireNotNull(logger, "logger");
		requireNotNull(format, "logger");
		// Generate record
		logger.log(Level.INFO, String.format(format, args));
	}
	
	/**
	 * It generates an info-type record, and it is only necessary to pass the error, and
	 * it will automatically take care of generating the record.
	 *
	 * @param logger the logger instance
	 * @param error  the error used to generate the record
	 * @throws IllegalArgumentException error if {@code logger} or {@code error} are {@code null}
	 */
	public static void logInfo(@NotNull Logger logger, @NotNull Throwable error) {
		requireNotNull(logger, "logger");
		requireNotNull(error, "error");
		// Display logger information
		logger.log(Level.INFO, error.getMessage(), error);
	}
	
	/**
	 * It generates a warning-type record, and it is only necessary to pass the format, and
	 * it will automatically take care of generating the record.
	 *
	 * @param logger the logger instance
	 * @param format message format
	 * @param args   format arguments
	 * @throws IllegalArgumentException error if {@code logger} or {@code format} are {@code null}
	 */
	public static void logWarning(@NotNull Logger logger, @NotNull String format, Object @Nullable ... args) {
		requireNotNull(logger, "logger");
		requireNotNull(format, "logger");
		// Generate record
		logger.log(Level.WARNING, String.format(format, args));
	}
	
	/**
	 * It generates a warning-type record, and it is only necessary to pass the error, and
	 * it will automatically take care of generating the record.
	 *
	 * @param logger the logger instance
	 * @param error  the error used to generate the record
	 * @throws IllegalArgumentException error if {@code logger} or {@code error} are {@code null}
	 */
	public static void logWarning(@NotNull Logger logger, @NotNull Throwable error) {
		requireNotNull(logger, "logger");
		requireNotNull(error, "error");
		// Display logger information
		logger.log(Level.WARNING, error.getMessage(), error);
	}
	
	/**
	 * It generates an error-type record, and it is only necessary to pass the format, and
	 * it will automatically take care of generating the record.
	 *
	 * @param logger the logger instance
	 * @param format message format
	 * @param args   format arguments
	 * @throws IllegalArgumentException error if {@code logger} or {@code format} are {@code null}
	 */
	public static void logError(@NotNull Logger logger, @NotNull String format, Object @Nullable ... args) {
		requireNotNull(logger, "logger");
		requireNotNull(format, "logger");
		// Generate record
		logger.log(Level.SEVERE, String.format(format, args));
	}
	
	/**
	 * It generates an error-type record, and it is only necessary to pass the error, and
	 * it will automatically take care of generating the record.
	 *
	 * @param logger the logger instance
	 * @param error  the error used to generate the record
	 * @throws IllegalArgumentException error if {@code logger} or {@code error} are {@code null}
	 */
	public static void logError(@NotNull Logger logger, @NotNull Throwable error) {
		requireNotNull(logger, "logger");
		requireNotNull(error, "error");
		// Display logger information
		logger.log(Level.SEVERE, error.getMessage(), error);
	}
	
}
