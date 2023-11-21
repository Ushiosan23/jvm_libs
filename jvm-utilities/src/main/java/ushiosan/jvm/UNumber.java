package ushiosan.jvm;

import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;
import ushiosan.jvm.collections.UArray;
import ushiosan.jvm.content.UTrio;
import ushiosan.jvm.function.UFun;

import java.util.Optional;
import java.util.Random;

public final class UNumber {
	
	/**
	 * Minimal representation of a binary number in Java
	 */
	private static final Number UNIT = 1;
	
	/* -----------------------------------------------------
	 * Properties
	 * ----------------------------------------------------- */
	/**
	 * Functions used to convert numbers to text in binary format
	 */
	private static final UTrio<Class<?>, Integer, UFun.UFun2<Boolean, Number, Integer>>[] TO_STRING_TRIOS =
		UArray.make(
			UTrio.make(Byte.class, Byte.SIZE, UNumber::getByteBit),
			UTrio.make(Short.class, Short.SIZE, UNumber::getShortBit),
			UTrio.make(Integer.class, Integer.SIZE, UNumber::getIntBit),
			UTrio.make(Long.class, Long.SIZE, UNumber::getLongBit));
	
	/**
	 * This class cannot be instantiated
	 */
	private UNumber() {}
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Determine if a decimal number contains decimals after the floating point.
	 *
	 * @param number the number to evaluate
	 * @return {@code true} if the number contains decimals or {@code false} otherwise
	 */
	public static boolean isDecimal(@NotNull Number number) {
		UObject.requireNotNull(number, "number");
		return (number.doubleValue() % 1) != 0;
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
	
	/**
	 * Generate an array of pseudorandom numbers within a specified range
	 *
	 * @param random    thr random content generator instance
	 * @param start     the start of the range (inclusive)
	 * @param end       the end of the range (no inclusive)
	 * @param size      total elements in array
	 * @param inclusive if {@code true} the end of the range is included within
	 *                  the allowed values {@code false} if the end of the range
	 *                  is excluded from the allowed values
	 * @return an array of pseudorandom numbers
	 */
	public static int[] randomRange(@NotNull Random random, int start, int end, int size, boolean inclusive) {
		// When it is an inclusive sequence, it means that the limits fall
		// within the range of valid elements.
		if (inclusive) {
			if (end > Integer.MIN_VALUE && end < Integer.MAX_VALUE) {
				end = end < 0 ? end - 1 : end + 1;
			}
		}
		// Generate result
		return random.ints(start, end)
			.limit(size)
			.toArray();
	}
	
	/**
	 * Generate an array of pseudorandom numbers within a specified range
	 *
	 * @param random    thr random content generator instance
	 * @param start     the start of the range (inclusive)
	 * @param end       the end of the range (no inclusive)
	 * @param size      total elements in array
	 * @param inclusive if {@code true} the end of the range is included within
	 *                  the allowed values {@code false} if the end of the range
	 *                  is excluded from the allowed values
	 * @return an array of pseudorandom numbers
	 */
	public static long[] randomRange(@NotNull Random random, long start, long end, int size, boolean inclusive) {
		// When it is an inclusive sequence, it means that the limits fall
		// within the range of valid elements.
		if (inclusive) {
			if (end > Long.MIN_VALUE && end < Long.MAX_VALUE) {
				end = end < 0 ? end - 1 : end + 1;
			}
		}
		// Generate result
		return random.longs(start, end)
			.limit(size)
			.toArray();
	}
	
	/* -----------------------------------------------------
	 * Bit operations
	 * ----------------------------------------------------- */
	
	/**
	 * Converts a signed byte to an unsigned byte.
	 * Since bytes can only be signed, then the type is changed to an integer
	 * that can hold the value of an unsigned byte if an overflow occurs.
	 *
	 * @param data the byte you want to change
	 * @return returns the unsigned byte
	 */
	public static int toUnsignedByte(byte data) {
		return (data & 0xFF);
	}
	
	/**
	 * Gets the value of the bit in numeric format
	 *
	 * @param status the state of the bit you want to get
	 * @return {@code 1} if the bit is {@code true} or {@code 0} if it is {@code false}
	 */
	public static int getBitValue(boolean status) {
		return status ? 1 : 0;
	}
	
	/**
	 * Gets the value of the specific bit from the given number
	 *
	 * @param value the number you want to analyze
	 * @param index the index of the bit to obtain. It is necessary to clarify that,
	 *              like arrays, bit indices begin with the number {@code 0}
	 * @param mask  the mask to determine the value of the bit
	 * @return {@code true} if the bit is {@code 1} or {@code false} if the bit is {@code 0}
	 * @throws IndexOutOfBoundsException error when the index is larger or smaller than
	 *                                   the size allowed by the data type
	 */
	public static int getByteBit(@NotNull Number value, @MagicConstant(intValues = {0, 1, 2, 3, 4, 5, 6, 7}) int index,
		byte mask) {
		// Restrictions
		UObject.requireNotNull(value, "value");
		checkRange(index, Byte.SIZE, "Byte");
		
		// Check number mask
		return (value.byteValue() >> index) & mask;
	}
	
	/**
	 * Gets the value of the specific bit from the given number
	 *
	 * @param value the number you want to analyze
	 * @param index the index of the bit to obtain. It is necessary to clarify that,
	 *              like arrays, bit indices begin with the number {@code 0}
	 * @return {@code true} if the bit is {@code 1} or {@code false} if the bit is {@code 0}
	 * @throws IndexOutOfBoundsException error when the index is larger or smaller than
	 *                                   the size allowed by the data type
	 */
	public static boolean getByteBit(@NotNull Number value, @MagicConstant(intValues = {0, 1, 2, 3, 4, 5, 6, 7}) int index) {
		return getByteBit(value, index, UNIT.byteValue()) == UNIT.intValue();
	}
	
	/**
	 * Gets the value of the specific bit from the given number
	 *
	 * @param value the number you want to analyze
	 * @return {@code true} if the bit is {@code 1} or {@code false} if the bit is {@code 0}
	 * @throws IndexOutOfBoundsException error when the index is larger or smaller than
	 *                                   the size allowed by the data type
	 */
	public static boolean getByteBit(@NotNull Number value) {
		return getByteBit(value, 0);
	}
	
	/**
	 * Method used to change a specific bit from the given number
	 *
	 * @param value  the value you want to manipulate
	 * @param index  the index of the bit you want to change. It is necessary to clarify that,
	 *               like arrays, bit indices begin with the number {@code 0}
	 * @param status the new value of the specified bit. The new value of the specified bit.
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
		UObject.requireNotNull(value, "value");
		checkRange(index, Byte.SIZE, "Byte");
		byte mask = (byte) (status ? (UNIT.byteValue() << index) :
							~(UNIT.byteValue() << index));
		
		return (byte) (status ? (value.byteValue() | mask) :
					   (value.byteValue() & mask));
	}
	
	
	/**
	 * Gets the value of the specific bit from the given number
	 *
	 * @param value the number you want to analyze
	 * @param index the index of the bit to obtain. It is necessary to clarify that,
	 *              like arrays, bit indices begin with the number {@code 0}
	 * @param mask  the mask to determine the value of the bit
	 * @return {@code true} if the bit is {@code 1} or {@code false} if the bit is {@code 0}
	 * @throws IndexOutOfBoundsException error when the index is larger or smaller than
	 *                                   the size allowed by the data type
	 */
	public static int getShortBit(@NotNull Number value,
		@MagicConstant(intValues = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}) int index, short mask) {
		UObject.requireNotNull(value, "value");
		checkRange(index, Short.SIZE, "Short");
		return (value.shortValue() >> index) & mask;
	}
	
	/**
	 * Gets the value of the specific bit from the given number
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
		return getShortBit(value, index, UNIT.shortValue()) == UNIT.intValue();
	}
	
	/**
	 * Gets the value of the specific bit from the given number
	 *
	 * @param value the number you want to analyze
	 * @return {@code true} if the bit is {@code 1} or {@code false} if the bit is {@code 0}
	 * @throws IndexOutOfBoundsException error when the index is larger or smaller than
	 *                                   the size allowed by the data type
	 */
	public static boolean getShortBit(@NotNull Number value) {
		return getShortBit(value, 0);
	}
	
	/**
	 * Method used to change a specific bit from the given number
	 *
	 * @param value  the value you want to manipulate
	 * @param index  the index of the bit you want to change. It is necessary to clarify that,
	 *               like arrays, bit indices begin with the number {@code 0}
	 * @param status the new value of the specified bit. The new value of the specified bit.
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
		UObject.requireNotNull(value, "value");
		checkRange(index, Short.SIZE, "Short");
		short mask = (short) (status ? (UNIT.shortValue() << index) :
							  ~(UNIT.shortValue() << index));
		
		return (short) (status ? (value.shortValue() | mask) :
						(value.shortValue() & mask));
	}
	
	/**
	 * Gets the value of the specific bit from the given number
	 *
	 * @param value the number you want to analyze
	 * @param index the index of the bit to obtain. It is necessary to clarify that,
	 *              like arrays, bit indices begin with the number {@code 0}
	 * @param mask  the mask to determine the value of the bit
	 * @return {@code true} if the bit is {@code 1} or {@code false} if the bit is {@code 0}
	 * @throws IndexOutOfBoundsException error when the index is larger or smaller than
	 *                                   the size allowed by the data type
	 */
	public static int getIntBit(@NotNull Number value, @MagicConstant(
		intValues = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28,
			29, 30, 31}) int index, int mask) {
		UObject.requireNotNull(value, "value");
		checkRange(index, Integer.SIZE, "Integer");
		return (value.intValue() >> index) & mask;
	}
	
	/**
	 * Gets the value of the specific bit from the given number
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
		return getIntBit(value, index, UNIT.intValue()) == UNIT.intValue();
	}
	
	/**
	 * Gets the value of the specific bit from the given number
	 *
	 * @param value the number you want to analyze
	 * @return {@code true} if the bit is {@code 1} or {@code false} if the bit is {@code 0}
	 * @throws IndexOutOfBoundsException error when the index is larger or smaller than
	 *                                   the size allowed by the data type
	 */
	public static boolean getIntBit(@NotNull Number value) {
		return getIntBit(value, 0);
	}
	
	/**
	 * Method used to change a specific bit from the given number
	 *
	 * @param value  the value you want to manipulate
	 * @param index  the index of the bit you want to change. It is necessary to clarify that,
	 *               like arrays, bit indices begin with the number {@code 0}
	 * @param status the new value of the specified bit. The new value of the specified bit.
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
		UObject.requireNotNull(value, "value");
		checkRange(index, Integer.SIZE, "Integer");
		int mask = status ? (UNIT.intValue() << index) :
				   ~(UNIT.intValue() << index);
		
		return status ? (value.intValue() | mask) :
			   (value.intValue() & mask);
	}
	
	/**
	 * Gets the value of the specific bit from the given number
	 *
	 * @param value the number you want to analyze
	 * @param index the index of the bit to obtain. It is necessary to clarify that,
	 *              like arrays, bit indices begin with the number {@code 0}
	 * @param mask  the mask to determine the value of the bit
	 * @return {@code true} if the bit is {@code 1} or {@code false} if the bit is {@code 0}
	 * @throws IndexOutOfBoundsException error when the index is larger or smaller than
	 *                                   the size allowed by the data type
	 */
	public static long getLongBit(@NotNull Number value, @MagicConstant(
		intValues = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28,
			29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57,
			58, 59, 60, 61, 62, 63}) int index, long mask) {
		UObject.requireNotNull(value, "value");
		checkRange(index, Long.SIZE, "Long");
		return (value.longValue() >> index) & mask;
	}
	
	/**
	 * Gets the value of the specific bit from the given number
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
		UObject.requireNotNull(value, "value");
		checkRange(index, Long.SIZE, "Long");
		return getLongBit(value, index, UNIT.longValue()) == UNIT.longValue();
	}
	
	/**
	 * Gets the value of the specific bit from the given number
	 *
	 * @param value the number you want to analyze
	 * @return {@code true} if the bit is {@code 1} or {@code false} if the bit is {@code 0}
	 * @throws IndexOutOfBoundsException error when the index is larger or smaller than
	 *                                   the size allowed by the data type
	 */
	public static boolean getLongBit(@NotNull Number value) {
		return getLongBit(value, 0);
	}
	
	/**
	 * Method used to change a specific bit from the given number
	 *
	 * @param value  the value you want to manipulate
	 * @param index  the index of the bit you want to change. It is necessary to clarify that,
	 *               like arrays, bit indices begin with the number {@code 0}
	 * @param status the new value of the specified bit. The new value of the specified bit.
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
		UObject.requireNotNull(value, "value");
		checkRange(index, Long.SIZE, "Long");
		long mask = status ? (UNIT.longValue() << index) :
					~(UNIT.longValue() << index);
		
		return (status ? (value.longValue() | mask) :
				(value.longValue() & mask));
	}
	
	/* -----------------------------------------------------
	 * Binary string representation
	 * ----------------------------------------------------- */
	
	/**
	 * Converts a binary number to a bit-text representation
	 *
	 * @param number    the number to convert
	 * @param separator every 4 bits add a separator to better visualize the numbers
	 * @return binary string representation
	 * @throws IllegalArgumentException error if {@code number} is {@code null} or is
	 *                                  a decimal numeric type
	 */
	public static @NotNull String toBinaryString(@NotNull Number number, boolean separator) {
		UObject.requireNotNull(number, "number");
		
		// We iterate all registered number converters
		Class<?> cls = number.getClass();
		for (var trio : TO_STRING_TRIOS) {
			// Check if class is valid
			if (cls != trio.first) continue;
			
			// The process is done in reverse because the numbers in binary
			// start with the most significant bit to the least significant bit.
			StringBuilder builder = new StringBuilder();
			for (int i = trio.second - 1; i >= 0; i--) {
				// we get the bit of the number passed as a parameter
				// depending on the position and the size of the number.
				boolean bit = trio.third.invoke(number, i);
				builder.append(bit ? "1" : "0");
				
				// Every 4 bits a separator "_" is added only if the
				// parameter "separator" is true
				if ((trio.second - i) % 4 == 0 && i != 0 && separator) {
					builder.append("_");
				}
			}
			
			return builder.toString();
		}
		
		// We throw an error when the number passed
		// as a parameter is of a decimal type.
		throw new IllegalArgumentException("The argument is a decimal numeric type");
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
	 * Identifies if a flag is already within the specified value
	 *
	 * @param content value where you want to search for the flag
	 * @param flag    the flag you want to search for
	 * @return {@code true} if the flag is set or {@code false} otherwise
	 */
	public static boolean hasFlag(int content, int flag) {
		return (content & flag) == flag;
	}
	
	/**
	 * converts an array of flags to a single value
	 *
	 * @param flags all elements to convert
	 * @return result of conversion to all flags
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
		UObject.requireNotNull(type, "type");
		// Check bit range
		size = size - 1;
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException(
				String.format("The range of the numbers of type \"%s\" goes from \"%d\" to \"%d\". \"%d\" given",
							  type, 0, size, index));
		}
	}
	
}
