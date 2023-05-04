package ushiosan.jvm_utilities.lang;

import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;
import ushiosan.jvm_utilities.function.base.Function;
import ushiosan.jvm_utilities.lang.collection.Arrs;
import ushiosan.jvm_utilities.lang.collection.elements.Trio;

import java.util.Optional;

/**
 * Class containing functionality for general number manipulation
 */
public final class Numbers {
	
	/**
	 * Minimal representation of a binary number in Java
	 */
	private static final Number UNIT = 1;
	
	/**
	 * Functions used to convert numbers to text in binary format
	 */
	private static final Trio<Class<?>, Integer, Function.Function2<Boolean, Number, Integer>>[] TO_STRING_TRIOS =
		Arrs.of(
			Trio.of(Byte.class, Byte.SIZE, Numbers::getByteBit),
			Trio.of(Short.class, Short.SIZE, Numbers::getShortBit),
			Trio.of(Integer.class, Integer.SIZE, Numbers::getIntBit),
			Trio.of(Long.class, Long.SIZE, Numbers::getLongBit));
	
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
	 * @return the {@link Number} value represented by the string argument or
	 *    {@link Optional#empty()} if content is not valid number
	 */
	public static @NotNull Optional<Number> parse(@NotNull CharSequence content) {
		try {
			return Optional.of(
				Double.parseDouble(content.toString()));
		} catch (NumberFormatException ignore) {
		}
		return Optional.empty();
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
	public static boolean getIntBit(@NotNull Number value, @MagicConstant(
		intValues = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28,
			29, 30, 31}) int index) {
		checkRange(index, Integer.SIZE, "Integer");
		return ((value.intValue() >> index) & UNIT.intValue()) == UNIT.intValue();
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
	public static int setIntBit(@NotNull Number value, @MagicConstant(
		intValues = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28,
			29, 30, 31}) int index, boolean status) {
		// Generate number mask
		checkRange(index, Integer.SIZE, "Integer");
		int mask = status ? (UNIT.intValue() << index) :
				   ~(UNIT.intValue() << index);
		
		return status ? (value.intValue() | mask) :
			   (value.intValue() & mask);
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
	public static boolean getLongBit(@NotNull Number value, @MagicConstant(
		intValues = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28,
			29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57,
			58, 59, 60, 61, 62, 63}) int index) {
		checkRange(index, Long.SIZE, "Long");
		return ((value.longValue() >> index) & UNIT.longValue()) == UNIT.longValue();
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
	public static long setLongBit(@NotNull Number value, @MagicConstant(
		intValues = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28,
			29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57,
			58, 59, 60, 61, 62, 63}) int index, boolean status) {
		// Generate number mask
		checkRange(index, Long.SIZE, "Long");
		long mask = status ? (UNIT.longValue() << index) :
					~(UNIT.longValue() << index);
		
		return (status ? (value.longValue() | mask) :
				(value.longValue() & mask));
	}
	
	/**
	 * Converts a binary number to a bit-text representation
	 *
	 * @param number    the number to convert
	 * @param separator every 4 bits add a separator to better visualize the numbers
	 * @return binary string representation
	 */
	public static @NotNull String toBinaryString(@NotNull Number number, boolean separator) {
		// Check class type
		Class<?> cls = number.getClass();
		
		// Check class type
		for (var trio : TO_STRING_TRIOS) {
			// Check if class is valid
			if (cls != trio.first) continue;
			
			// Convert the number
			StringBuilder builder = new StringBuilder();
			for (int i = trio.second - 1; i >= 0; i--) {
				boolean bit = trio.third.invoke(number, i);
				builder.append(bit ? "1" : "0");
				if ((trio.second - i) % 4 == 0 && i != 0 && separator) {
					builder.append("_");
				}
			}
			
			return builder.toString();
		}
		
		// Launch error
		throw new IllegalArgumentException("The argument is not a valid integer type");
	}
	
	/**
	 * Converts a binary number to a bit-text representation
	 *
	 * @param number the number to convert
	 * @return binary string representation
	 * @see #toBinaryString(Number, boolean)
	 */
	public static @NotNull String toBinaryString(@NotNull Number number) {
		return toBinaryString(number, true);
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
