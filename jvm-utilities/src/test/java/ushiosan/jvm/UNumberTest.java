package ushiosan.jvm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ushiosan.UTestUnit;
import ushiosan.jvm.collections.UArray;
import ushiosan.jvm.print.UToStringManager;

import java.util.Random;

public class UNumberTest extends UTestUnit {
	
	@SuppressWarnings("ConstantValue")
	@Test
	public void isDecimalTest() {
		sectionOf(() -> {
			boolean validDecimal = UNumber.isDecimal(Math.PI);
			boolean validDecimal2 = UNumber.isDecimal(12783.1);
			boolean invalidDecimal = UNumber.isDecimal(1.0);
			boolean invalidDecimal2 = UNumber.isDecimal(365);
			
			// Assertions
			Assertions.assertTrue(validDecimal,
								  "Invalid decimal result");
			Assertions.assertTrue(validDecimal2,
								  "Invalid decimal result");
			Assertions.assertFalse(invalidDecimal,
								   "Invalid decimal result");
			Assertions.assertFalse(invalidDecimal2,
								   "Invalid decimal result");
			
			// Display information
			System.out.printf("Valid %f decimal: %s%n", Math.PI, validDecimal);
			System.out.printf("Valid %f decimal: %s%n", 12783.1, validDecimal2);
			
			System.out.printf("Invalid %f decimal: %s%n", 1.0, invalidDecimal);
			System.out.printf("Invalid %f decimal: %s%n", 365.0, invalidDecimal2);
		});
	}
	
	@Test
	public void parseTest() throws NumberFormatException {
		sectionErrOf(() -> {
			String validDecimal = "365.128934";
			String validInteger = "18923";
			String invalidInteger = "a17283";
			
			var validDecimalNumber = UNumber.parse(validDecimal);
			var validIntegerNumber = UNumber.parse(validInteger);
			var invalidIntegerNumber = UNumber.parse(invalidInteger);
			
			// Assertions
			Assertions.assertTrue(validDecimalNumber.isPresent(),
								  "Invalid numeric information");
			Assertions.assertTrue(validIntegerNumber.isPresent(),
								  "Invalid numeric information");
			Assertions.assertTrue(invalidIntegerNumber.isEmpty(),
								  "Invalid number is present");
			
			// Display information
			System.out.printf("%s to number is: %s%n", validDecimal, validDecimalNumber);
			System.out.printf("%s to number is: %s%n", validInteger, validIntegerNumber);
			System.out.printf("%s to number is: %s%n", invalidInteger, invalidIntegerNumber);
		});
	}
	
	@Test
	public void randomRangeTest() {
		sectionOf(() -> {
			Random random = new Random(System.nanoTime());
			
			for (int i = 0; i < 100; i++) {
				var range = UNumber.randomRange(random, 0, 1000, 50, false);
				
				// Assertions
				Assertions.assertEquals(50, range.length,
										"Invalid range size");
				Assertions.assertFalse(UArray.primitiveContains(range, 1000),
									   "The range contains a boundary element");
				
				// Display information
				System.out.printf("Range %d content: %s%n", i,
								  UToStringManager.getInstance().toString(range));
			}
		});
	}
	
	@Test
	public void getByteBitTest() {
		sectionOf(() -> {
			byte content = Byte.MAX_VALUE;
			boolean lastBit = UNumber.getByteBit(content, 7);
			
			// Assertions
			Assertions.assertFalse(lastBit,
								   "Invalid bit value");
			for (int i = 0; i < 7; i++) {
				Assertions.assertTrue(UNumber.getByteBit(content, i),
									  "Invalid bit value");
			}
			
			// Display information
			System.out.printf("content information: %s%n", content);
		});
	}
	
	@Test
	public void setByteBitTest() {
		sectionOf(() -> {
			byte content = Byte.MAX_VALUE;
			byte newContent = UNumber.setByteBit(content, 7, true);
			for (int i = 0; i < 7; i++) {
				newContent = UNumber.setByteBit(newContent, i, false);
			}
			
			// Assertions
			Assertions.assertNotEquals(content, newContent,
									   "The values are the same");
			Assertions.assertEquals(Byte.MIN_VALUE, newContent,
									"The value is not valid");
			
			// Display information
			System.out.printf("content information: %s%n", content);
			System.out.printf("new content information: %s%n", newContent);
		});
	}
	
	@Test
	public void toBinaryStringTest() {
		sectionOf(() -> {
			byte num1 = 0b0000_1100;
			int num2 = 0xFF;
			long num3 = 0xFFFF;
			
			String num1Str = UNumber.toBinaryString(num1);
			String num2Str = UNumber.toBinaryString(num2);
			String num3Str = UNumber.toBinaryString(num3);
			
			// Assertions
			Assertions.assertEquals("0000_1100", num1Str,
									"Invalid binary representation");
			Assertions.assertEquals("0000_0000_0000_0000_0000_0000_1111_1111", num2Str,
									"Invalid binary representation");
			Assertions.assertEquals("0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_1111_1111_1111_1111",
									num3Str, "Invalid binary representation");
			
			// Display information
			System.out.printf("The number %s binary representation: %s%n",
							  num1, num1Str);
			System.out.printf("The number %s binary representation: %s%n",
							  num2, num2Str);
			System.out.printf("The number %s binary representation: %s%n",
							  num3, num3Str);
		});
	}
	
}