package ushiosan.jvm_utilities.lang.print;

import org.junit.Test;
import ushiosan.jvm_utilities.TestUtils;
import ushiosan.jvm_utilities.lang.collection.elements.Pair;
import ushiosan.jvm_utilities.system.Arch;
import ushiosan.jvm_utilities.system.Platform;

import java.awt.*;

public class PrintObjTest {
	
	@Test
	public void toInstanceStringTest() {
		TestUtils.printSection();
		
		Pair<String, Integer> pair = Pair.of("Ushio Okasaki", 25);
		Dimension dimension = new Dimension(120, 12);
		Platform platform = Platform.getRunningPlatform();
		Arch arch = Arch.getRunningArch();
		
		String pairStr = PrintObj.toInstanceString(pair);
		String dimensionStr = PrintObj.toInstanceString(dimension);
		String platformStr = PrintObj.toInstanceString(platform);
		String archStr = PrintObj.toInstanceString(arch);
		
		System.out.println(pairStr);
		System.out.println(dimensionStr);
		System.out.println(platformStr);
		System.out.println(archStr);
		System.out.println();
	}
	
}