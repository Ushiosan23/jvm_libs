package ushiosan.jvm_utilities.lang;

/**
 * Class that contains extra utilities for mathematical operations
 */
public final class Maths {
	
	/**
	 * Default decimal operations tolerance
	 * <p>
	 * 32-bit number tolerance
	 */
	public static final double DECIMAL_TOLERANCE = 0.000001;
	
	/* -----------------------------------------------------
	 * Properties
	 * ----------------------------------------------------- */
	/**
	 * A radian represented in degrees
	 */
	private static final double RADIANS_DEGREE = 57.295779513082320876798154814105;
	
	/**
	 * A degree represented in radians
	 */
	private static final double DEGREE_RADIANS = 0.017453292519943295769236907684886;
	
	/**
	 * This class cannot be instantiated.
	 * <p>
	 * Singleton or utility class mode.
	 */
	private Maths() {
	}
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Linear interpolation between two numbers.
	 *
	 * @param base      the base number
	 * @param objective the destination number
	 * @param amount    the amount of distance in each iteration
	 * @return the result of the iteration
	 */
	public static double lerp(double base, double objective, double amount) {
		return base + amount * (objective - base);
	}
	
	/**
	 * Linear interpolation between two numbers.
	 * <p>
	 * Unlike {@link #lerp(double, double, double)}, this method is more precise and performs an
	 * intermediate comparison (but has a slightly higher performance cost).
	 *
	 * @param base      the base number
	 * @param objective the destination number
	 * @param amount    the amount of distance in each iteration
	 * @return the result of the iteration
	 */
	public static double lerpPrecise(double base, double objective, double amount) {
		return (1.0 - amount) * base + amount * objective;
	}
	
	/**
	 * Returns the distance between two values.
	 *
	 * @param v1 value 1
	 * @param v2 value 2
	 * @return the distance between two values
	 */
	public static double distance(double v1, double v2) {
		return Math.abs(v1 - v2);
	}
	
	/**
	 * Linearly normalizes two numbers. This range must not be empty.
	 *
	 * @param start   the start range
	 * @param end     the end of range
	 * @param current the value to normalize
	 * @return the normalized value.
	 */
	public static double normalize(double start, double end, double current) {
		return (current - start) / (end - start);
	}
	
	/**
	 * Keeps the value within a specified range.
	 *
	 * @param value the number to clamp
	 * @param min   the minimum value of the range
	 * @param max   the maximum value of the range
	 * @return the constrained value within the given range
	 */
	public static double clamp(double value, double min, double max) {
		value = Math.min(value, max);
		value = Math.max(value, min);
		
		return value;
	}
	
	/**
	 * Check if two decimal numbers are equal.
	 * Decimal number operations can lead to many errors because they take into account
	 * decimals and operations that may seem the same, in reality they are not:
	 * <pre>{@code
	 * // JVM Operations
	 * float x = 0.2f;
	 * float y = x - 0.1f;
	 * if(y == 0.1){ // can be "false" because "y" is 0.99999999999999...
	 *     // ...
	 * }
	 * }</pre>
	 * that is why this method uses tolerance, to avoid this type of problem.
	 *
	 * @param v1        first value
	 * @param v2        second value
	 * @param tolerance operation tolerance
	 * @return {@code true} if the numbers are equal or {@code false} otherwise
	 */
	public static boolean equals(double v1, double v2, double tolerance) {
		return distance(v1, v2) <= tolerance;
	}
	
	/**
	 * Check if two decimal numbers are equal.
	 *
	 * @param v1 first value
	 * @param v2 second value
	 * @return {@code true} if the numbers are equal or {@code false} otherwise
	 * @see #DECIMAL_TOLERANCE
	 * @see #equals(double, double, double)
	 */
	public static boolean equals(double v1, double v2) {
		return equals(v1, v2, DECIMAL_TOLERANCE);
	}
	
	/**
	 * Checks if a decimal number is zero
	 *
	 * @param value     the value to check
	 * @param tolerance inspection tolerance
	 * @return {@code true} if the number is {@code 0} or {@code false} otherwise
	 */
	public static boolean isZero(double value, double tolerance) {
		return Math.abs(value) <= tolerance;
	}
	
	/**
	 * Checks if a decimal number is zero
	 *
	 * @param value the value to check
	 * @return {@code true} if the number is {@code 0} or {@code false} otherwise
	 * @see #DECIMAL_TOLERANCE
	 * @see #isZero(double, double)
	 */
	public static boolean isZero(double value) {
		return isZero(value, DECIMAL_TOLERANCE);
	}
	
	/* -----------------------------------------------------
	 * Conversion methods
	 * ----------------------------------------------------- */
	
	/**
	 * Performs a conversion from radians to degrees
	 *
	 * @param radians the radians to convert
	 * @return the conversion from radians to degrees
	 * @see #RADIANS_DEGREE
	 */
	public static double toDegrees(double radians) {
		return radians * RADIANS_DEGREE;
	}
	
	/**
	 * Performs a conversion from degrees to radians
	 *
	 * @param degrees the degrees to convert
	 * @return the conversion from degrees to radians
	 * @see #DEGREE_RADIANS
	 */
	public static double toRadians(double degrees) {
		return degrees * DEGREE_RADIANS;
	}
	
	/**
	 * Calculates the percentage of an amount.
	 * <p>
	 * Example:
	 * <pre>{@code
	 * double fiftyPercent = Maths.percentage(500, 50); // 50% -> 250
	 * double doubleValue = Maths.percentage(120, 200); // 200% -> 240
	 * }</pre>
	 *
	 * @param value      the value to convert
	 * @param percentage the percentage to calculate
	 * @return the percentage calculated based on the given amount
	 * @see #percentageValue(double)
	 */
	public static double percentage(double value, double percentage) {
		return value * percentageValue(percentage);
	}
	
	/**
	 * Converts a percent based on hundreds to a decimal percent.
	 * <p>
	 * Example:
	 * <pre>{@code
	 * double fifty = Maths.percentageValue(50); // 50% -> 0.5
	 * double ten = Maths.percentageValue(1);    // 10% -> 0.1
	 * double one = Maths.percentageValue(1);    // 01% -> 0.01
	 * }</pre>
	 *
	 * @param percentage the percentage to calculate
	 * @return the decimal percentage value
	 * @see #percentage(double, double)
	 */
	public static double percentageValue(double percentage) {
		return percentage / 100f;
	}
	
}
