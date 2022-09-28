package ushiosan.jvm_utilities.lang.collection;

import org.junit.Assert;
import org.junit.Test;

public class ArrsTest {

	@Test
	public void runTest() {
		boolean[] boolArr = Arrs.booleanOf(true, false, false, false);
		char[] charArr = Arrs.charOf('H', 'e', 'l', 'l', 'o', '\0');
		byte[] byteArr = Arrs.byteOf(12, 24, 12, 21);
		short[] shorArr = Arrs.shortOf(12, 21, 134, 435);
		int[] intArr = Arrs.intOf(2, 4, 6, 12893, 1213, 1213);

		System.out.println(java.util.Arrays.toString(boolArr));
		System.out.println(java.util.Arrays.toString(charArr));
		System.out.println(java.util.Arrays.toString(byteArr));
		System.out.println(java.util.Arrays.toString(shorArr));
		System.out.println(java.util.Arrays.toString(intArr));
	}

	@Test
	public void conversionTest() {
		boolean[] boolArr = Arrs.booleanOf(true, false, false, false);
		char[] charArr = Arrs.charOf('H', 'e', 'l', 'l', 'o', '\0');
		byte[] byteArr = Arrs.byteOf(12, 24, 12, 21);
		short[] shorArr = Arrs.shortOf(12, 21, 134, 435);
		int[] intArr = Arrs.intOf(2, 4, 6, 12893, 1213, 1213);

		Object[] boolArrObj = Arrs.toObjectArray(boolArr);
		Assert.assertEquals("Invalid conversion", boolArr.length, boolArrObj.length);
		Object[] charArrObj = Arrs.toObjectArray(charArr);
		Assert.assertEquals("Invalid conversion", charArr.length, charArrObj.length);
		Object[] byteArrObj = Arrs.toObjectArray(byteArr);
		Assert.assertEquals("Invalid conversion", byteArr.length, byteArrObj.length);
		Object[] shorArrObj = Arrs.toObjectArray(shorArr);
		Assert.assertEquals("Invalid conversion", shorArr.length, shorArrObj.length);
		Object[] intArrObj = Arrs.toObjectArray(intArr);
		Assert.assertEquals("Invalid conversion", intArr.length, intArrObj.length);
	}

	@Test
	public void indexOfTest() {
		// Primitives
		int[] intArr = Arrs.intOf(2, 4, 6, 12893, 1213, 1213);
		float[] floatArr = Arrs.floatOf(12.2f, 2123f, 31212.21f);
		// Objects
		Number[] intArrObj = (Number[]) Arrs.toObjectArray(intArr);
		Number[] floatArrObj = (Number[]) Arrs.toObjectArray(floatArr);

		Assert.assertTrue("intIndexOf not found",
			Arrs.contains(intArrObj, 1213));
		Assert.assertTrue("floatIndexOf not found",
			Arrs.contains(floatArrObj, 31212.21f));
	}

}