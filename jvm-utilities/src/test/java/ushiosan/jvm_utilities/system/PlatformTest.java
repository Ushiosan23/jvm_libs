package ushiosan.jvm_utilities.system;

import org.junit.Assert;
import org.junit.Test;
import ushiosan.jvm_utilities.TestUtils;

public class PlatformTest {
	
	@Test
	public void getRunningPlatformTest() {
		TestUtils.printSection();
		
		// Check current platform
		Platform platform = Platform
			.getRunningPlatform();
		
		Assert.assertNotEquals("Platform cannot be UNKNOWN", Platform.UNKNOWN, platform);
		// Print platform version
		
		System.out.println("the platform is Unix-like: " + platform.isUnix());
		System.out.println("platform type: " + platform);
		System.out.println();
	}
	
}