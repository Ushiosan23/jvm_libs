package ushiosan.jvm_utilities.lang.print;

import org.junit.Test;

import java.awt.Dimension;

import ushiosan.jvm_utilities.lang.collection.elements.Pair;
import ushiosan.jvm_utilities.system.Arch;
import ushiosan.jvm_utilities.system.Platform;

public class PrintObjTest {

	@Test
	public void testToInstanceString() {
		Pair<String, Integer> pair = Pair.of("Ushio Okasaki", 25);
		Dimension dimension = new Dimension(120, 12);
		Platform platform = Platform.getRunningPlatform();
		Arch arch = Arch.getRunningArch();

		System.out.println(PrintObj.toInstanceString(dimension));
		System.out.println(pair);
		System.out.println(platform);
		System.out.println(arch);
	}

}