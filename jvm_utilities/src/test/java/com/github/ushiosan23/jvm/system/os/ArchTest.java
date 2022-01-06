package com.github.ushiosan23.jvm.system.os;

import org.junit.Test;

public class ArchTest {

	@Test
	public void runTest() {
		Arch currentArch = Arch.getCurrent();
		System.err.println(currentArch);
	}

}
