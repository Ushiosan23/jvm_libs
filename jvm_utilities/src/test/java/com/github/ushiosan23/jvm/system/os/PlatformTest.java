package com.github.ushiosan23.jvm.system.os;

import org.junit.Test;

public class PlatformTest {

	@Test
	public void runTest() {
		Platform platform = Platform.getCurrent();

		System.out.println(platform);
		System.out.printf("Is unix: %s\n", platform.isUnix());
	}


}
