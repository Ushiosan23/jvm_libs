package ushiosan.jvm_utilities.lang;

import org.junit.Assert;
import org.junit.Test;
import ushiosan.jvm_utilities.TestUtils;

public class StringsTest {
	
	@Test
	public void capitalizeTest() {
		TestUtils.printSection();
		
		String word = "   hello, world!";
		String capitalizeAll = Strings.capitalize(word);
		String capitalizeFirst = Strings.capitalize(word, false);
		String capitalizeClean = Strings.capitalizeWord(word);
		
		Assert.assertEquals("Invalid capitalization", "   Hello, World!", capitalizeAll);
		Assert.assertEquals("Invalid capitalization", "   Hello, world!", capitalizeFirst);
		Assert.assertEquals("Invalid capitalization", "Hello, world!", capitalizeClean);
		
		System.out.println(Strings.capitalize(word));
		System.out.println(Strings.capitalize(word, false));
		System.out.println(Strings.capitalizeWord(word));
		System.out.println();
	}
	
}