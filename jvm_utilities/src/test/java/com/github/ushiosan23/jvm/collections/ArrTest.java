package com.github.ushiosan23.jvm.collections;

import org.junit.After;
import org.junit.Test;

import java.util.Arrays;

public class ArrTest {

	@Test
	public void runTest() {
		// Generate any array
		boolean[] boolArr = Arr.ofBoolean(true, false, false, true, true, true);
		char[] charArr = Arr.ofChars('\uFFFF', 'H', 'E', 'L', 'L', 'O', '\u000B');
		byte[] byteArr = Arr.ofByte(0, 120, 127, 2, 33, 25, 0);
		short[] shortArr = Arr.ofShort(12, 21, 42, 32, 64);
		int[] intArr = Arr.ofInt(2, 4, 6, 8, 10);
		long[] longArr = Arr.ofLong(2L, 4L, 6L, 8L, 8918297938912L);
		float[] floatArr = Arr.ofFloat(12, 21.6f, 42.3f, 3.14154221f, 0);
		double[] doubleArr = Arr.ofDouble(21, 42, 23, 12989183979313798123791283.0, 52);
		Object[] objArr = Arr.of("Hello", "World", "!");

		System.err.println(Arr.toString(boolArr));
		System.err.println(Arr.toString(charArr));
		System.err.println(Arr.toString(byteArr));
		System.err.println(Arr.toString(shortArr));
		System.err.println(Arr.toString(intArr));
		System.err.println(Arr.toString(longArr));
		System.err.println(Arr.toString(floatArr));
		System.err.println(Arr.toString(doubleArr));
		System.err.println(Arr.toString(objArr));
	}

	@After
	public void conversionTest() {
		byte[] baseArray = Arr.ofByte(2, 4, 6);
		Number[] conversion = ArrInternal.convertToNumberArray(baseArray);

		System.err.println(Arrays.toString(conversion));
	}

	@After
	public void indexOfTest() {
		// Base array
		int[] arr = Arr.ofInt(2, 4, 6, 8, 6, 12, 24);
		int index = Arr.intIndexOf(arr, 6);

		System.err.println("Index: " + index);
	}


}
