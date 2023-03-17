package ushiosan.jvm_utilities.lang.io;

import org.junit.Test;

import java.util.Set;

public class StandardAlgorithmsTest {
	
	@Test
	public void getSupportedAlgorithmsTest() {
		Set<String> supported = StandardAlgorithms.getSupportedAlgorithms();
		//		Assert.assertTrue("Invalid supported results", supported.isEmpty());
		
		System.out.println(supported);
	}
	
}