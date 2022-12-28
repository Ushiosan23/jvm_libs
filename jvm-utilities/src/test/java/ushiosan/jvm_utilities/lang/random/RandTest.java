package ushiosan.jvm_utilities.lang.random;

import org.junit.Test;
import ushiosan.jvm_utilities.lang.Obj;

public class RandTest {
	
	@Test
	public void GetRandomStringTest() {
		for (int i = 0; i < 4; i++) {
			String passwordRandString = Rand.getRandomString(100, TextType.LETTERS);
			String passwordSecure = Rand.getRandomString(100, "*.,-'%\"(){}!#1234567890|$/+&".toCharArray());
			
			System.out.println();
			System.out.println(passwordRandString);
			System.out.println(passwordSecure);
		}
	}
	
	@Test
	public void testGetRandomRangeTest() {
		int[] intRange = Rand.getRandomRange(0, 10, 5);
		long[] longRange = Rand.getRandomRange(0L, Long.MAX_VALUE, 5);
		
		System.out.println(Obj.toString(intRange));
		System.out.println(Obj.toString(longRange));
	}
	
}