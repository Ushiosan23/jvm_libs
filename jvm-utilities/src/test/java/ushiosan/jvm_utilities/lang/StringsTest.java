package ushiosan.jvm_utilities.lang;

import org.junit.Test;

public class StringsTest {
	
	@Test
	public void runTest() {
		String word = "   hello, world!";
		
		System.out.println(Strings.capitalize(word));
		System.out.println(Strings.capitalize(word, false));
		System.out.println(Strings.capitalizeWord(word));
	}
	
}