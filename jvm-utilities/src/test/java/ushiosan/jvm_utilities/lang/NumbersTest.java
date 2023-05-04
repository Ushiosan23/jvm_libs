package ushiosan.jvm_utilities.lang;

import org.junit.Assert;
import org.junit.Test;
import ushiosan.jvm_utilities.TestUtils;

import java.util.regex.Pattern;

public class NumbersTest {
	
	@Test
	public void isDecimalTest() {
		TestUtils.printSection();
		// Temporal variables
		double validDecimal = 3.1;
		double invalidDecimal = 365;
		
		// Assertions
		Assert.assertTrue("Invalid decimal number",
						  Numbers.isDecimal(validDecimal));
		Assert.assertFalse("Valid decimal number",
						   Numbers.isDecimal(invalidDecimal));
		
		// Display information
		System.out.printf("Valid decimal number: %f%n", validDecimal);
		System.out.printf("Invalid decimal number: %f%n", invalidDecimal);
		System.out.println();
	}
	
	@Test
	public void parseTest() {
		TestUtils.printSection();
		// Temporal variables
		CharSequence validNumber = "3.1416";
		CharSequence invalidNumber = "e";
		
		// Assertions
		var validOpt = Numbers.parse(validNumber);
		var invalidOpt = Numbers.parse(invalidNumber);
		
		Assert.assertTrue("Invalid number",
						  validOpt.isPresent());
		Assert.assertTrue("Valid number",
						  invalidOpt.isEmpty());
		
		// Display information
		System.out.printf("Valid number: %s -> %s%n", validNumber, validOpt);
		System.out.printf("Invalid number: %s -> %s%n", invalidNumber, invalidOpt);
		System.out.println();
	}
	
	@Test
	public void getByteBitTest() {
		TestUtils.printSection();
		// Temporal variables
		byte value = (byte) 0b0100_1010;
		
		// Assertions
		Assert.assertTrue("Invalid byte value", Numbers.getByteBit(value, 1));
		Assert.assertTrue("Invalid byte value", Numbers.getByteBit(value, 3));
		Assert.assertTrue("Invalid byte value", Numbers.getByteBit(value, 6));
		
		Assert.assertFalse("Invalid byte value", Numbers.getByteBit(value, 0));
		Assert.assertFalse("Invalid byte value", Numbers.getByteBit(value, 2));
		Assert.assertFalse("Invalid byte value", Numbers.getByteBit(value, 4));
		Assert.assertFalse("Invalid byte value", Numbers.getByteBit(value, 5));
		Assert.assertFalse("Invalid byte value", Numbers.getByteBit(value, 7));
		
		// Display information
		System.out.printf("Byte value: %d -> %s%n%n", value, Integer.toBinaryString(value));
	}
	
	@Test
	public void toBinaryStringTest() {
		TestUtils.printSection();
		// Temporal variables
		byte minByte = (byte) 0b1000_0000;
		String minByteStr = Numbers.toBinaryString(minByte);
		byte maxByte = (byte) 0b0111_1111;
		String maxByteStr = Numbers.toBinaryString(maxByte);
		
		// Assertions
		Assert.assertEquals("Invalid string representation",
							"1000_0000",
							minByteStr);
		Assert.assertEquals("Invalid string representation",
							"0111_1111",
							maxByteStr);
		
		// Display information
		System.out.printf("Min byte binary representation %d is: %s%n",
						  minByte,
						  minByteStr);
		System.out.printf("Max byte binary representation %4d is: %s%n%n",
						  maxByte,
						  maxByteStr);
	}
	
	@Test
	public void setByteBitTest() {
		TestUtils.printSection();
		// Temporal variables
		byte value = (byte) 0b0000_0000;
		
		// Operations
		value = Numbers.setByteBit(value, 0, true);
		value = Numbers.setByteBit(value, 1, true);
		value = Numbers.setByteBit(value, 2, true);
		value = Numbers.setByteBit(value, 3, true);
		value = Numbers.setByteBit(value, 4, true);
		value = Numbers.setByteBit(value, 5, true);
		
		// Assertions
		Assert.assertEquals("Invalid operation",
							"0011_1111", Numbers.toBinaryString(value));
		
		// Display information
		System.out.printf("Byte result: %d -> %s%n", value,
						  Numbers.toBinaryString(value));
		
		// Operations
		value = Numbers.setByteBit(value, 6, true);
		value = Numbers.setByteBit(value, 7, true);
		
		// Assertions
		Assert.assertEquals("Invalid operation",
							"1111_1111", Numbers.toBinaryString(value));
		
		// Display information
		System.out.printf("Byte result: %d -> %s%n%n", value,
						  Numbers.toBinaryString(value));
	}
	
	@Test
	public void asFlagsTest() {
		TestUtils.printSection();
		// Temporal variables
		int patternFlags = Pattern.MULTILINE | Pattern.CASE_INSENSITIVE;
		int autoFlags = Numbers.asFlags(Pattern.MULTILINE, Pattern.CASE_INSENSITIVE);
		
		// Assertions
		Assert.assertEquals("Invalid flags",
							patternFlags, autoFlags);
		
		// Display information
		System.out.printf("Pattern.MULTILINE:        %d -> %s%n",
						  Pattern.MULTILINE, Numbers.toBinaryString(Pattern.MULTILINE));
		System.out.printf("Pattern.CASE_INSENSITIVE: %d -> %s%n%n",
						  Pattern.CASE_INSENSITIVE, Numbers.toBinaryString(Pattern.CASE_INSENSITIVE));
		
		System.out.printf("Original pattern flags: %d -> %s%n",
						  patternFlags, Numbers.toBinaryString(patternFlags));
		System.out.printf("Auto pattern flags:     %d -> %s%n%n",
						  autoFlags, Numbers.toBinaryString(autoFlags));
	}
	
}