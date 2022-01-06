package com.github.ushiosan23.jvm.base;

public final class MathExtra {

	/* ------------------------------------------------------------------
	 * Constructors
	 * ------------------------------------------------------------------ */

	/**
	 * This class cannot be instantiated
	 */
	private MathExtra() {
	}

	/* ------------------------------------------------------------------
	 * Methods
	 * ------------------------------------------------------------------ */

	/**
	 * This method clamp a number within a range of numbers.
	 * If the value is greater than the range, it returns the highest number and not the overflow number.
	 * If the value is less than the range, it returns the smallest number and not the overflow number.
	 *
	 * @param value Value to analyze
	 * @param min   Minimum range value
	 * @param max   Maximum range value
	 * @return Returns a value within the given range
	 */
	public static double clamp(double value, double min, double max) {
		value = Math.min(value, max);
		value = Math.max(value, min);

		return value;
	}

	/**
	 * Find the percent of a number.
	 *
	 * @param value      Base value
	 * @param percentage Percentage to calculate
	 * @return Returns a percentage of a number
	 */
	public static double percentage(double value, double percentage) {
		return (value * percentage) / 100.0;
	}

}
