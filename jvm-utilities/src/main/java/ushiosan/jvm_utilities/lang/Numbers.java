package ushiosan.jvm_utilities.lang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public final class Numbers {
	
	/**
	 * This class cannot be instantiated.
	 * <p>
	 * Singleton or utility class mode.
	 */
	private Numbers() {
	}
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Determine if a decimal number contains decimals after the floating point.
	 *
	 * @param number the number to evaluate
	 * @return {@code true} if the number contains decimals or {@code false} otherwise
	 */
	public static boolean isDecimal(double number) {
		return (number % 1) != 0;
	}
	
	/**
	 * Returns a new {@link Number} initialized to the value
	 * represented by the specified {@code String}.
	 *
	 * @param content the string to be parsed
	 * @return the {@link Number} value represented by the string argument
	 * @throws NumberFormatException if the string does not contain a
	 *                               parsable {@code Number}
	 */
	public static @NotNull @Unmodifiable Number parse(@NotNull CharSequence content) {
		return Double.parseDouble(content.toString());
	}
	
}
