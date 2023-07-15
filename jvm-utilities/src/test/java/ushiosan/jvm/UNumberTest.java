package ushiosan.jvm;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ushiosan.jvm.collections.UArray;
import ushiosan.jvm.print.UToStringManager;
import ushiosan.jvm.test.UTestUnit;

import java.util.Random;

public class UNumberTest extends UTestUnit {
	
	/**
	 * The name of the module where the tests are being done
	 *
	 * @return the module name
	 */
	@Override
	public @NotNull String module() {
		return Constants.LIB_MODULE;
	}
	
	@SuppressWarnings("ConstantValue")
	@Test
	public void isDecimalTest() {
		makeSection(() -> {
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
			println("Valid %f decimal: %s", Math.PI, validDecimal);
			println("Valid %f decimal: %s", 12783.1, validDecimal2);
			
			println("Invalid %f decimal: %s", 1.0, invalidDecimal);
			println("Invalid %f decimal: %s", 365.0, invalidDecimal2);
		});
	}
	
	@Test
	public void parseTest() throws NumberFormatException {
		makeSectionError(() -> {
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
			println("%s to number is: %s", validDecimal, validDecimalNumber);
			println("%s to number is: %s", validInteger, validIntegerNumber);
			println("%s to number is: %s", invalidInteger, invalidIntegerNumber);
		});
	}
	
	@Test
	public void randomRangeTest() {
		makeSection(() -> {
			Random random = new Random(System.nanoTime());
			
			for (int i = 0; i < 100; i++) {
				var range = UNumber.randomRange(random, 0, 1000, 50, false);
				
				// Assertions
				Assertions.assertEquals(50, range.length,
										"Invalid range size");
				Assertions.assertFalse(UArray.primitiveContains(range, 1000),
									   "The range contains a boundary element");
				
				// Display information
				println("Range %d content: %s", i,
						UToStringManager.getInstance().toString(range));
			}
		});
	}
	
	@Test
	public void getByteBitTest() {
		makeSection(() -> {
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
			println("content information: %s -> %s", content, UNumber.toBinaryString(content));
		});
	}
	
	@Test
	public void setByteBitTest() {
		makeSection(() -> {
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
			println("content information: %s", content);
			println("new content information: %s", newContent);
		});
	}
	
	@Test
	public void toBinaryStringTest() {
		makeSection(() -> {
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
			println("The number %s binary representation: %s",
					num1, num1Str);
			println("The number %s binary representation: %s",
					num2, num2Str);
			println("The number %s binary representation: %s",
					num3, num3Str);
		});
	}
	
}