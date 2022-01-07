package com.github.ushiosan23.jvm.base;

public final class Numbers {

	/* ------------------------------------------------------------------
	 * Constructors
	 * ------------------------------------------------------------------ */

	/**
	 * This class cannot be instantiated
	 */
	private Numbers() {
	}

	/* ------------------------------------------------------------------
	 * Methods
	 * ------------------------------------------------------------------ */

	/**
	 * Determine if a decimal number contains decimals after the floating point.
	 *
	 * @param number Number to evaluate
	 * @return Returns {@code true} if the number contains decimals or {@code false} otherwise
	 */
	public static boolean hasDecimals(double number) {
		return (number % 1) != 0;
	}

	/**
	 * Determine if a decimal number contains decimals after the floating point.
	 *
	 * @param number Number to evaluate
	 * @return Returns {@code true} if the number contains decimals or {@code false} otherwise
	 */
	public static boolean hasDecimals(float number) {
		return hasDecimals((double) number);
	}

}
