package ushiosan.jvm_utilities.system;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class ArchTest {
	
	@Test
	public void runTest() {
		String rawArch = Arch.getRawArch();
		Arch arch = Arch.getRunningArch();
		// Test raw value
		assertNotNull("Arch name cannot be null", rawArch);
		assertNotEquals("Arch cannot be UNKNOWN", Arch.UNKNOWN, arch);
		
		System.out.println(rawArch);
		System.out.println(arch);
	}
	
}