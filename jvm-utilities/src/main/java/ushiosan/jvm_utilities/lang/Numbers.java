package ushiosan.jvm_utilities.lang;

import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

/**
 * Class containing functionality for general number manipulation
 */
public final class Numbers {
	
	/**
	 * Minimal representation of a binary number in Java
	 */
	private static final Number UNIT = 1;
	
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
	
	/* -----------------------------------------------------
	 * Bit operations
	 * ----------------------------------------------------- */
	
	/**
	 * Gets the value of the specific bit of a given number
	 *
	 * @param value the number you want to analyze
	 * @param index the index of the bit to obtain. It is necessary to clarify that,
	 *              like arrays, bit indices begin with the number {@code 0}
	 * @return {@code true} if the bit is {@code 1} or {@code false} if the bit is {@code 0}
	 * @throws IndexOutOfBoundsException error when the index is larger or smaller than
	 *                                   the size allowed by the data type
	 */
	public static boolean getByteBit(@NotNull Number value, @MagicConstant(intValues = {0, 1, 2, 3, 4, 5, 6, 7}) int index) {
		checkRange(index, Byte.SIZE, "Byte");
		return ((value.byteValue() >> index) & UNIT.byteValue()) == UNIT.intValue();
	}
	
	/**
	 * Method used to change a specific bit of a given number.
	 *
	 * @param value  the value you want to manipulate
	 * @param index  the index of the bit you want to change. It is necessary to clarify that,
	 *               like arrays, bit indices begin with the number {@code 0}
	 * @param status the new value of the specified bit. the new value of the specified bit.
	 *               Let's remember that a bit is the smallest unit in computing and can only
	 *               have 2 values, {@code 0} or {@code 1}, where {@code 0} is {@code false} and
	 *               {@code 1} is {@code true}.
	 * @return the new value with the bit changed
	 * @throws IndexOutOfBoundsException error when the index is larger or smaller than
	 *                                   the size allowed by the data type
	 */
	public static byte setByteBit(@NotNull Number value, @MagicConstant(intValues = {0, 1, 2, 3, 4, 5, 6, 7}) int index,
		boolean status) {
		// Generate number mask
		checkRange(index, Byte.SIZE, "Byte");
		byte mask = (byte) (status ? (UNIT.byteValue() << index) :
							~(UNIT.byteValue() << index));
		
		return (byte) (status ? (value.byteValue() | mask) :
					   (value.byteValue() & mask));
	}
	
	/**
	 * Gets the value of the specific bit of a given number
	 *
	 * @param value the number you want to analyze
	 * @param index the index of the bit to obtain. It is necessary to clarify that,
	 *              like arrays, bit indices begin with the number {@code 0}
	 * @return {@code true} if the bit is {@code 1} or {@code false} if the bit is {@code 0}
	 * @throws IndexOutOfBoundsException error when the index is larger or smaller than
	 *                                   the size allowed by the data type
	 */
	public static boolean getShortBit(@NotNull Number value,
		@MagicConstant(intValues = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}) int index) {
		checkRange(index, Short.SIZE, "Short");
		return ((value.shortValue() >> index) & UNIT.shortValue()) == UNIT.intValue();
	}
	
	/**
	 * Method used to change a specific bit of a given number.
	 *
	 * @param value  the value you want to manipulate
	 * @param index  the index of the bit you want to change. It is necessary to clarify that,
	 *               like arrays, bit indices begin with the number {@code 0}
	 * @param status the new value of the specified bit. the new value of the specified bit.
	 *               Let's remember that a bit is the smallest unit in computing and can only
	 *               have 2 values, {@code 0} or {@code 1}, where {@code 0} is {@code false} and
	 *               {@code 1} is {@code true}.
	 * @return the new value with the bit changed
	 * @throws IndexOutOfBoundsException error when the index is larger or smaller than
	 *                                   the size allowed by the data type
	 */
	public static short setShortBit(@NotNull Number value,
		@MagicConstant(intValues = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}) int index, boolean status) {
		// Generate number mask
		checkRange(index, Short.SIZE, "Short");
		short mask = (short) (status ? (UNIT.shortValue() << index) :
							  ~(UNIT.shortValue() << index));
		
		return (short) (status ? (value.shortValue() | mask) :
						(value.shortValue() & mask));
	}
	
	/**
	 * Gets the value of the specific bit of a given number
	 *
	 * @param value the number you want to analyze
	 * @param index the index of the bit to obtain. It is necessary to clarify that,
	 *              like arrays, bit indices begin with the number {@code 0}
	 * @return {@code true} if the bit is {@code 1} or {@code false} if the bit is {@code 0}
	 * @throws IndexOutOfBoundsException error when the index is larger or smaller than
	 *                                   the size allowed by the data type
	 */
	public static boolean getIntBit(int value, @MagicConstant(
		intValues = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28,
			29, 30, 31}) int index) {
		checkRange(index, Integer.SIZE, "Integer");
		return ((value >> index) & UNIT.intValue()) == UNIT.intValue();
	}
	
	/**
	 * Method used to change a specific bit of a given number.
	 *
	 * @param value  the value you want to manipulate
	 * @param index  the index of the bit you want to change. It is necessary to clarify that,
	 *               like arrays, bit indices begin with the number {@code 0}
	 * @param status the new value of the specified bit. the new value of the specified bit.
	 *               Let's remember that a bit is the smallest unit in computing and can only
	 *               have 2 values, {@code 0} or {@code 1}, where {@code 0} is {@code false} and
	 *               {@code 1} is {@code true}.
	 * @return the new value with the bit changed
	 * @throws IndexOutOfBoundsException error when the index is larger or smaller than
	 *                                   the size allowed by the data type
	 */
	public static int setIntBit(int value, @MagicConstant(
		intValues = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28,
			29, 30, 31}) int index, boolean status) {
		// Generate number mask
		checkRange(index, Integer.SIZE, "Integer");
		int mask = status ? (UNIT.intValue() << index) :
				   ~(UNIT.intValue() << index);
		
		return status ? (value | mask) :
			   (value & mask);
	}
	
	/**
	 * Gets the value of the specific bit of a given number
	 *
	 * @param value the number you want to analyze
	 * @param index the index of the bit to obtain. It is necessary to clarify that,
	 *              like arrays, bit indices begin with the number {@code 0}
	 * @return {@code true} if the bit is {@code 1} or {@code false} if the bit is {@code 0}
	 * @throws IndexOutOfBoundsException error when the index is larger or smaller than
	 *                                   the size allowed by the data type
	 */
	public static boolean getLongBit(long value, @MagicConstant(
		intValues = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28,
			29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57,
			58, 59, 60, 61, 62, 63}) int index) {
		checkRange(index, Long.SIZE, "Long");
		return ((value >> index) & UNIT.longValue()) == UNIT.longValue();
	}
	
	/**
	 * Method used to change a specific bit of a given number.
	 *
	 * @param value  the value you want to manipulate
	 * @param index  the index of the bit you want to change. It is necessary to clarify that,
	 *               like arrays, bit indices begin with the number {@code 0}
	 * @param status the new value of the specified bit. the new value of the specified bit.
	 *               Let's remember that a bit is the smallest unit in computing and can only
	 *               have 2 values, {@code 0} or {@code 1}, where {@code 0} is {@code false} and
	 *               {@code 1} is {@code true}.
	 * @return the new value with the bit changed
	 * @throws IndexOutOfBoundsException error when the index is larger or smaller than
	 *                                   the size allowed by the data type
	 */
	public static long setLongBit(long value, @MagicConstant(
		intValues = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28,
			29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57,
			58, 59, 60, 61, 62, 63}) int index, boolean status) {
		// Generate number mask
		checkRange(index, Long.SIZE, "Long");
		long mask = status ? (UNIT.longValue() << index) :
					~(UNIT.longValue() << index);
		
		return (status ? (value | mask) :
				(value & mask));
	}
	
	/* -----------------------------------------------------
	 * Flags methods
	 * ----------------------------------------------------- */
	
	/**
	 * converts an array of flags to a single value
	 *
	 * @param flags all elements to convert
	 * @return result of conversion of all flags
	 */
	public static int asFlags(int @NotNull ... flags) {
		int result = 0;
		for (int flag : flags) {
			result |= flag;
		}
		return result;
	}
	
	/* -----------------------------------------------------
	 * Internal methods
	 * ----------------------------------------------------- */
	
	/**
	 * Check that an index is within a specified size
	 *
	 * @param index the index you want to analyze
	 * @param size  the maximum size allowed
	 * @param type  the type of data being analyzed
	 * @throws IndexOutOfBoundsException error when the index is larger or smaller than
	 *                                   the size allowed by the data type
	 */
	private static void checkRange(long index, long size, @NotNull String type) {
		size = size - 1;
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException(
				String.format("The range of the numbers of type \"%s\" goes from \"%d\" to \"%d\". \"%d\" given",
							  type, 0, size, index));
		}
	}
	
}
