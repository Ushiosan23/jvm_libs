package com.github.ushiosan23.swing_utilities.screen;

import com.github.ushiosan23.jvm.collections.Arr;
import org.junit.After;
import org.junit.Test;

public class AspectRatioTest {

	@Test
	public void runTest() {
		AspectRatio aspectRatio = new AspectRatio(9, 16);
		AspectRatio.registerAspect("9:16", aspectRatio);
	}

	@After
	public void runGetAspectRatioTest() {
		AspectRatio aspectRatio = AspectRatio.getAspect("16:9");
		int baseWidth = 1000;
		int resultHeight = (int) aspectRatio.getHeight(baseWidth);

		System.err.println(
			resultHeight
		);
	}

	@After
	public void runGetRegisteredAspectExpressionsTest() {
		String[] aspectExpressions = AspectRatio.getAllAspectExpressions();
		System.err.println(
			Arr.toString(aspectExpressions)
		);
	}

}
