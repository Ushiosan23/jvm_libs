package ushiosan.jvm;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ushiosan.jvm.print.UToStringManager;

import java.util.logging.Level;
import java.util.logging.LogRecord;

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
	public static @NotNull String loggerName(@NotNull Class<?> cls) {
		UObject.requireNotNull(cls, "cls");
		// Generate logger instance
		return UToStringManager.getInstance()
			.toString(cls, true);
	}
	
	/* -----------------------------------------------------
	 * Logger extensions
	 * ----------------------------------------------------- */
	
	/**
	 * It generates an info-type record, and it is only necessary to pass the format, and
	 * it will automatically take care of generating the record.
	 *
	 * @param format message format
	 * @param args   format arguments
	 * @return a record instance configured with the selected items.
	 * @throws IllegalArgumentException error if {@code logger} or {@code format} are {@code null}
	 */
	public static @NotNull LogRecord logInfo(@NotNull String format, Object @Nullable ... args) {
		UObject.requireNotNull(format, "format");
		// Generate record
		return UAction.also(new LogRecord(Level.INFO, format), it -> it.setParameters(args));
	}
	
	/**
	 * It generates an info-type record, and it is only necessary to pass the format, and
	 * it will automatically take care of generating the record.
	 *
	 * @param error  the error used to generate the record
	 * @param format message format
	 * @param args   format arguments
	 * @return a record instance configured with the selected items.
	 * @throws IllegalArgumentException error if {@code logger} or {@code format} are {@code null}
	 */
	public static @NotNull LogRecord logInfo(@NotNull Throwable error, @Nullable String format, Object @Nullable ... args) {
		UObject.requireNotNull(error, "error");
		// Generate record
		String finalMsg = UObject.isNull(format) ? error.getMessage() : String.format(format, args);
		return UAction.also(new LogRecord(Level.INFO, finalMsg), it -> it.setThrown(error));
	}
	
	/**
	 * It generates an info-type record, and it is only necessary to pass the format, and
	 * it will automatically take care of generating the record.
	 *
	 * @param error the error used to generate the record
	 * @return a record instance configured with the selected items.
	 * @throws IllegalArgumentException error if {@code logger} or {@code format} are {@code null}
	 */
	public static @NotNull LogRecord logInfo(@NotNull Throwable error) {
		return logInfo(error, null);
	}
	
	/**
	 * It generates a warning-type record, and it is only necessary to pass the format, and
	 * it will automatically take care of generating the record.
	 *
	 * @param format message format
	 * @param args   format arguments
	 * @return a record instance configured with the selected items.
	 * @throws IllegalArgumentException error if {@code logger} or {@code format} are {@code null}
	 */
	public static @NotNull LogRecord logWarning(@NotNull String format, Object @Nullable ... args) {
		UObject.requireNotNull(format, "format");
		// Generate record
		return UAction.also(new LogRecord(Level.WARNING, format), it -> it.setParameters(args));
	}
	
	/**
	 * It generates a warning-type record, and it is only necessary to pass the error, and
	 * it will automatically take care of generating the record.
	 *
	 * @param error  the error used to generate the record
	 * @param format message format
	 * @param args   format arguments
	 * @return a record instance configured with the selected items.
	 * @throws IllegalArgumentException error if {@code logger} or {@code format} are {@code null}
	 */
	public static @NotNull LogRecord logWarning(@NotNull Throwable error, @Nullable String format, Object @Nullable ... args) {
		UObject.requireNotNull(error, "error");
		// Generate record
		String finalMsg = UObject.isNull(format) ? error.getMessage() : String.format(format, args);
		return UAction.also(new LogRecord(Level.WARNING, finalMsg), it -> it.setThrown(error));
	}
	
	/**
	 * It generates a warning-type record, and it is only necessary to pass the error, and
	 * it will automatically take care of generating the record.
	 *
	 * @param error the error used to generate the record
	 * @return a record instance configured with the selected items.
	 * @throws IllegalArgumentException error if {@code logger} or {@code format} are {@code null}
	 */
	public static @NotNull LogRecord logWarning(@NotNull Throwable error) {
		return logWarning(error, null);
	}
	
	/**
	 * It generates an error-type record, and it is only necessary to pass the format, and
	 * it will automatically take care of generating the record.
	 *
	 * @param format message format
	 * @param args   format arguments
	 * @return a record instance configured with the selected items.
	 * @throws IllegalArgumentException error if {@code logger} or {@code format} are {@code null}
	 */
	public static @NotNull LogRecord logError(@NotNull String format, Object @Nullable ... args) {
		UObject.requireNotNull(format, "format");
		// Generate record
		return UAction.also(new LogRecord(Level.SEVERE, format), it -> it.setParameters(args));
	}
	
	/**
	 * It generates an error-type record, and it is only necessary to pass the format, and
	 * it will automatically take care of generating the record.
	 *
	 * @param error  the error used to generate the record
	 * @param format message format
	 * @param args   format arguments
	 * @return a record instance configured with the selected items.
	 * @throws IllegalArgumentException error if {@code logger} or {@code format} are {@code null}
	 */
	public static @NotNull LogRecord logError(@NotNull Throwable error, @Nullable String format, Object @Nullable ... args) {
		UObject.requireNotNull(error, "error");
		// Generate record
		String finalMsg = UObject.isNull(format) ? error.getMessage() : String.format(format, args);
		return UAction.also(new LogRecord(Level.SEVERE, finalMsg), it -> it.setThrown(error));
	}
	
	/**
	 * It generates an error-type record, and it is only necessary to pass the format, and
	 * it will automatically take care of generating the record.
	 *
	 * @param error the error used to generate the record
	 * @return a record instance configured with the selected items.
	 * @throws IllegalArgumentException error if {@code logger} or {@code format} are {@code null}
	 */
	public static @NotNull LogRecord logError(@NotNull Throwable error) {
		return logError(error, null);
	}
	
}
