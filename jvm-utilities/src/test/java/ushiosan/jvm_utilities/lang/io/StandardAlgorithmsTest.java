package ushiosan.jvm_utilities.lang.io;

import org.junit.Assert;
import org.junit.Test;
import ushiosan.jvm_utilities.TestUtils;

import java.util.Set;

public class StandardAlgorithmsTest {
	
	@Test
	public void getSupportedAlgorithmsTest() {
		TestUtils.printSection();
		
		Set<String> supported = StandardAlgorithms.getSupportedAlgorithms();
		Assert.assertFalse("Invalid supported results", supported.isEmpty());
		
		System.out.println(supported);
		System.out.println();
	}
	
}