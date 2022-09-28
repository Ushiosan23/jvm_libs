package ushiosan.jvm_utilities.lang.collection;

import org.junit.Assert;
import org.junit.Test;

public class ArraysTest {

	@Test
	public void runTest() {
		boolean[] boolArr = Arrays.booleanOf(true, false, false, false);
		char[] charArr = Arrays.charOf('H', 'e', 'l', 'l', 'o', '\0');
		byte[] byteArr = Arrays.byteOf(12, 24, 12, 21);
		short[] shorArr = Arrays.shortOf(12, 21, 134, 435);
		int[] intArr = Arrays.intOf(2, 4, 6, 12893, 1213, 1213);

		System.out.println(java.util.Arrays.toString(boolArr));
		System.out.println(java.util.Arrays.toString(charArr));
		System.out.println(java.util.Arrays.toString(byteArr));
		System.out.println(java.util.Arrays.toString(shorArr));
		System.out.println(java.util.Arrays.toString(intArr));
	}

	@Test
	public void conversionTest() {
		boolean[] boolArr = Arrays.booleanOf(true, false, false, false);
		char[] charArr = Arrays.charOf('H', 'e', 'l', 'l', 'o', '\0');
		byte[] byteArr = Arrays.byteOf(12, 24, 12, 21);
		short[] shorArr = Arrays.shortOf(12, 21, 134, 435);
		int[] intArr = Arrays.intOf(2, 4, 6, 12893, 1213, 1213);

		Object[] boolArrObj = Arrays.toObjectArray(boolArr);
		Assert.assertEquals("Invalid conversion", boolArr.length, boolArrObj.length);
		Object[] charArrObj = Arrays.toObjectArray(charArr);
		Assert.assertEquals("Invalid conversion", charArr.length, charArrObj.length);
		Object[] byteArrObj = Arrays.toObjectArray(byteArr);
		Assert.assertEquals("Invalid conversion", byteArr.length, byteArrObj.length);
		Object[] shorArrObj = Arrays.toObjectArray(shorArr);
		Assert.assertEquals("Invalid conversion", shorArr.length, shorArrObj.length);
		Object[] intArrObj = Arrays.toObjectArray(intArr);
		Assert.assertEquals("Invalid conversion", intArr.length, intArrObj.length);
	}

	@Test
	public void indexOfTest() {
		// Primitives
		int[] intArr = Arrays.intOf(2, 4, 6, 12893, 1213, 1213);
		float[] floatArr = Arrays.floatOf(12.2f, 2123f, 31212.21f);
		// Objects
		Number[] intArrObj = (Number[]) Arrays.toObjectArray(intArr);
		Number[] floatArrObj = (Number[]) Arrays.toObjectArray(floatArr);

		Assert.assertTrue("intIndexOf not found",
			Arrays.contains(intArrObj, 1213));
		Assert.assertTrue("floatIndexOf not found",
			Arrays.contains(floatArrObj, 31212.21f));
	}

}