package ushiosan.jvm_utilities.system;

import org.junit.Assert;
import org.junit.Test;

public class PlatformTest {
	
	@Test
	public void runTest() {
		// Check current platform
		Platform platform = Platform
			.getRunningPlatform();
		
		Assert.assertNotEquals("Platform cannot be UNKNOWN", Platform.UNKNOWN, platform);
		// Print platform version
		System.out.println(platform.isUnix());
		System.out.println(platform);
	}
	
}