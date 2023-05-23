package ushiosan.jvm.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ushiosan.UTestUnit;
import ushiosan.jvm.print.UToStringManager;

import java.util.Arrays;

import static ushiosan.jvm.collections.UArrays.toObjectArray;

public class UArraysTest extends UTestUnit {
	
	/* -----------------------------------------------------
	 * Global properties
	 * ----------------------------------------------------- */
	
	// Generic array generation
	private final String[] genericArray = UArrays.of("Hello", ", ", "World", "!");
	// Primitive array generators
	private final boolean[] pBooleanArray = UArrays.booleanOf(true, false, false, false, true, true);
	private final char[] pCharArray = UArrays.charOf('H', 'e', 'l', 'l', 'o', '!');
	private final byte[] pByteArray = UArrays.byteOf(0, 1, 2, 4, 8, 16, 32, 64, 127);
	private final short[] pShortArray = UArrays.shortOf(2, 4, 6, 8, 10, 12, 14, 16, 18, 20);
	private final int[] pIntegerArray = UArrays.intOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12903, Integer.MAX_VALUE);
	private final long[] pLongArray = UArrays.longOf(890128093, 98791829, 98792845, 1090128);
	private final float[] pFloatArray = UArrays.floatOf(1234.1234f, 365f);
	private final double[] pDoubleArray = UArrays.doubleOf(Math.PI, 1289.198279182739123, Math.E);
	
	/* -----------------------------------------------------
	 * Test methods
	 * ----------------------------------------------------- */
	
	@Test
	public void ofTest() {
		sectionOf(() -> {
			// Assertions
			Assertions.assertEquals(4, genericArray.length,
									"Invalid array size");
			Assertions.assertEquals(6, pBooleanArray.length,
									"Invalid array size");
			Assertions.assertEquals(6, pCharArray.length,
									"Invalid array size");
			Assertions.assertEquals(9, pByteArray.length,
									"Invalid array size");
			Assertions.assertEquals(10, pShortArray.length,
									"Invalid array size");
			Assertions.assertEquals(13, pIntegerArray.length,
									"Invalid array size");
			Assertions.assertEquals(4, pLongArray.length,
									"Invalid array size");
			Assertions.assertEquals(2, pFloatArray.length,
									"Invalid array size");
			Assertions.assertEquals(3, pDoubleArray.length,
									"Invalid array size");
			
			// Display information
			System.out.printf("Generic array:   %s%n", Arrays.toString(genericArray));
			System.out.printf("Boolean array:   %s%n", Arrays.toString(pBooleanArray));
			System.out.printf("Character array: %s%n", Arrays.toString(pCharArray));
			System.out.printf("Byte array:      %s%n", Arrays.toString(pByteArray));
			System.out.printf("Short array:     %s%n", Arrays.toString(pShortArray));
			System.out.printf("Integer array:   %s%n", Arrays.toString(pIntegerArray));
			System.out.printf("Long array:      %s%n", Arrays.toString(pLongArray));
			System.out.printf("Float array:     %s%n", Arrays.toString(pFloatArray));
			System.out.printf("Double array:    %s%n", Arrays.toString(pDoubleArray));
		});
	}
	
	@Test
	public void indexOfTest() {
		sectionOf(() -> {
			// Temporal variables
			int genericArrayIndex = UArrays.indexOf(genericArray, "!");
			int pByteArrayIndex = UArrays.primitiveIndexOf(pByteArray, 64);
			int pShortArrayIndex = UArrays.primitiveIndexOf(pShortArray, 4);
			int pIntegerArrayIndex = UArrays.primitiveIndexOf(pIntegerArray, 12903);
			int pLongArrayIndex = UArrays.primitiveIndexOf(pLongArray, 890128093);
			int pFloatArrayIndex = UArrays.primitiveIndexOf(pFloatArray, 365.0f);
			int pDoubleArrayIndex = UArrays.primitiveIndexOf(pDoubleArray, Math.PI);
			
			// Assertions
			Assertions.assertEquals(genericArrayIndex, 3,
									"The array does not contain the specified value");
			Assertions.assertEquals(pByteArrayIndex, 7,
									"The array does not contain the specified value");
			Assertions.assertEquals(pShortArrayIndex, 1,
									"The array does not contain the specified value");
			Assertions.assertEquals(pIntegerArrayIndex, 11,
									"The array does not contain the specified value");
			Assertions.assertEquals(pLongArrayIndex, 0,
									"The array does not contain the specified value");
			Assertions.assertEquals(pFloatArrayIndex, 1,
									"The array does not contain the specified value");
			Assertions.assertEquals(pDoubleArrayIndex, 0,
									"The array does not contain the specified value");
			
			// Display information
			System.out.printf("Index of element %9s from %57s array is: %d%n",
							  "!", Arrays.toString(genericArray), genericArrayIndex);
			System.out.printf("Index of element %9d from %57s array is: %d%n",
							  64, Arrays.toString(pByteArray), pByteArrayIndex);
			System.out.printf("Index of element %9d from %57s array is: %d%n",
							  4, Arrays.toString(pShortArray), pShortArrayIndex);
			System.out.printf("Index of element %9d from %57s array is: %d%n",
							  12903, Arrays.toString(pIntegerArray), pIntegerArrayIndex);
			System.out.printf("Index of element %9d from %57s array is: %d%n",
							  890128093, Arrays.toString(pLongArray), pLongArrayIndex);
			System.out.printf("Index of element %3.5f from %57s array is: %d%n",
							  365.0f, Arrays.toString(pFloatArray), pFloatArrayIndex);
			System.out.printf("Index of element %1.7f from %57s array is: %d%n",
							  Math.PI, Arrays.toString(pDoubleArray), pDoubleArrayIndex);
		});
	}
	
	@Test
	public void lastIndexOfTest() {
		sectionOf(() -> {
			// Temporal variables
			int genericArrayIndex = UArrays.lastIndexOf(genericArray, "!");
			int pByteArrayIndex = UArrays.primitiveLastIndexOf(pByteArray, 64);
			int pShortArrayIndex = UArrays.primitiveLastIndexOf(pShortArray, 4);
			int pIntegerArrayIndex = UArrays.primitiveLastIndexOf(pIntegerArray, 12903);
			int pLongArrayIndex = UArrays.primitiveLastIndexOf(pLongArray, 890128093);
			int pFloatArrayIndex = UArrays.primitiveLastIndexOf(pFloatArray, 365.0f);
			int pDoubleArrayIndex = UArrays.primitiveLastIndexOf(pDoubleArray, Math.PI);
			
			// Assertions
			Assertions.assertEquals(genericArrayIndex, 3,
									"The array does not contain the specified value");
			Assertions.assertEquals(pByteArrayIndex, 7,
									"The array does not contain the specified value");
			Assertions.assertEquals(pShortArrayIndex, 1,
									"The array does not contain the specified value");
			Assertions.assertEquals(pIntegerArrayIndex, 11,
									"The array does not contain the specified value");
			Assertions.assertEquals(pLongArrayIndex, 0,
									"The array does not contain the specified value");
			Assertions.assertEquals(pFloatArrayIndex, 1,
									"The array does not contain the specified value");
			Assertions.assertEquals(pDoubleArrayIndex, 0,
									"The array does not contain the specified value");
			
			// Display information
			System.out.printf("Index of element %9s from %57s array is: %d%n",
							  "!", Arrays.toString(genericArray), genericArrayIndex);
			System.out.printf("Index of element %9d from %57s array is: %d%n",
							  64, Arrays.toString(pByteArray), pByteArrayIndex);
			System.out.printf("Index of element %9d from %57s array is: %d%n",
							  4, Arrays.toString(pShortArray), pShortArrayIndex);
			System.out.printf("Index of element %9d from %57s array is: %d%n",
							  12903, Arrays.toString(pIntegerArray), pIntegerArrayIndex);
			System.out.printf("Index of element %9d from %57s array is: %d%n",
							  890128093, Arrays.toString(pLongArray), pLongArrayIndex);
			System.out.printf("Index of element %3.5f from %57s array is: %d%n",
							  365.0f, Arrays.toString(pFloatArray), pFloatArrayIndex);
			System.out.printf("Index of element %1.7f from %57s array is: %d%n",
							  Math.PI, Arrays.toString(pDoubleArray), pDoubleArrayIndex);
		});
	}
	
	@SuppressWarnings("ConstantValue")
	@Test
	public void containsTest() {
		sectionOf(() -> {
			boolean containsGeneric = UArrays.contains(genericArray, "!");
			boolean containsByte = UArrays.primitiveContains(pByteArray, 64);
			boolean containsShort = UArrays.primitiveContains(pShortArray, 4);
			boolean containsInt = UArrays.primitiveContains(pIntegerArray, 12903);
			boolean containsLong = UArrays.primitiveContains(pLongArray, 890128093);
			boolean containsFloat = UArrays.primitiveContains(pFloatArray, 365.0f);
			boolean containsDouble = UArrays.primitiveContains(pDoubleArray, Math.PI);
			
			// Assertions
			Assertions.assertTrue(containsGeneric,
								  "The array does not contain the specified value");
			Assertions.assertTrue(containsByte,
								  "The array does not contain the specified value");
			Assertions.assertTrue(containsShort,
								  "The array does not contain the specified value");
			Assertions.assertTrue(containsInt,
								  "The array does not contain the specified value");
			Assertions.assertTrue(containsLong,
								  "The array does not contain the specified value");
			Assertions.assertTrue(containsFloat,
								  "The array does not contain the specified value");
			Assertions.assertTrue(containsDouble,
								  "The array does not contain the specified value");
			
			// Display information
			System.out.printf("The element %9s exists in %57s array?: %s%n",
							  "!", Arrays.toString(genericArray), containsGeneric);
			System.out.printf("The element %9s exists in %57s array?: %s%n",
							  64, Arrays.toString(pByteArray), containsByte);
			System.out.printf("The element %9s exists in %57s array?: %s%n",
							  4, Arrays.toString(pShortArray), containsShort);
			System.out.printf("The element %9s exists in %57s array?: %s%n",
							  12903, Arrays.toString(pIntegerArray), containsInt);
			System.out.printf("The element %9s exists in %57s array?: %s%n",
							  890128093, Arrays.toString(pLongArray), containsLong);
			System.out.printf("The element %3.5f exists in %57s array?: %s%n",
							  365.0f, Arrays.toString(pFloatArray), containsFloat);
			System.out.printf("The element %1.7f exists in %57s array?: %s%n",
							  Math.PI, Arrays.toString(pDoubleArray), containsDouble);
		});
	}
	
	@Test
	public void lastElementTest() {
		sectionOf(() -> {
			// Temporal variables
			var genericLast = UArrays.lastElement(genericArray);
			var charLast = UArrays.primitiveLastElement(pCharArray);
			var byteLast = UArrays.primitiveLastElement(pByteArray);
			var shortLast = UArrays.primitiveLastElement(pShortArray);
			var intLast = UArrays.primitiveLastElement(pIntegerArray);
			var longLast = UArrays.primitiveLastElement(pLongArray);
			var floatLast = UArrays.primitiveLastElement(pFloatArray);
			var doubleLast = UArrays.primitiveLastElement(pDoubleArray);
			
			// Assertions
			Assertions.assertTrue(genericLast.isPresent() && genericLast.get().equals("!"),
								  "Invalid last element");
			Assertions.assertTrue(charLast.isPresent() && charLast.get().equals('!'),
								  "Invalid last element");
			Assertions.assertTrue(byteLast.isPresent() && byteLast.get().equals((byte) 127),
								  "Invalid last element");
			Assertions.assertTrue(shortLast.isPresent() && shortLast.get().equals((short) 20),
								  "Invalid last element");
			Assertions.assertTrue(intLast.isPresent() && intLast.get().equals(Integer.MAX_VALUE),
								  "Invalid last element");
			Assertions.assertTrue(longLast.isPresent() && longLast.get().equals(1090128L),
								  "Invalid last element");
			Assertions.assertTrue(floatLast.isPresent() && floatLast.get().equals(365.0f),
								  "Invalid last element");
			Assertions.assertTrue(doubleLast.isPresent() && doubleLast.get().equals(Math.E),
								  "Invalid last element");
			
			// Display information
			System.out.printf("Last element of %9s array is: %s%n",
							  "String", genericLast.get());
			System.out.printf("Last element of %9s array is: %s%n",
							  "Character", charLast.get());
			System.out.printf("Last element of %9s array is: %s%n",
							  "Byte", byteLast.get());
			System.out.printf("Last element of %9s array is: %s%n",
							  "Short", shortLast.get());
			System.out.printf("Last element of %9s array is: %s%n",
							  "Integer", intLast.get());
			System.out.printf("Last element of %9s array is: %s%n",
							  "Long", longLast.get());
			System.out.printf("Last element of %9s array is: %s%n",
							  "Float", floatLast.get());
			System.out.printf("Last element of %9s array is: %s%n",
							  "Double", doubleLast.get());
		});
	}
	
	@Test
	public void toObjectArrayTest() {
		sectionOf(() -> {
			// Temporal variables
			Object[] genericObjArr = UArrays.toGenericObjectArray(genericArray);
			Character[] charObjArr = UArrays.toObjectArray(pCharArray);
			Boolean[] boolObjArr = UArrays.toObjectArray(pBooleanArray);
			Byte[] byteObjArr = UArrays.toObjectArray(pByteArray);
			Short[] shortObjArr = UArrays.toObjectArray(pShortArray);
			Integer[] intObjArr = UArrays.toObjectArray(pIntegerArray);
			Long[] longObjArr = UArrays.toObjectArray(pLongArray);
			Float[] floatObjArr = UArrays.toObjectArray(pFloatArray);
			Double[] doubleObjArr = UArrays.toObjectArray(pDoubleArray);
			
			// Assertions
			Assertions.assertEquals(genericObjArr.getClass(), String[].class,
									"Invalid array type");
			Assertions.assertEquals(charObjArr.getClass(), Character[].class,
									"Invalid array type");
			Assertions.assertEquals(boolObjArr.getClass(), Boolean[].class,
									"Invalid array type");
			Assertions.assertEquals(byteObjArr.getClass(), Byte[].class,
									"Invalid array type");
			Assertions.assertEquals(shortObjArr.getClass(), Short[].class,
									"Invalid array type");
			Assertions.assertEquals(intObjArr.getClass(), Integer[].class,
									"Invalid array type");
			Assertions.assertEquals(longObjArr.getClass(), Long[].class,
									"Invalid array type");
			Assertions.assertEquals(floatObjArr.getClass(), Float[].class,
									"Invalid array type");
			Assertions.assertEquals(doubleObjArr.getClass(), Double[].class,
									"Invalid array type");
			
			// Display information
			System.out.printf("array of type \"%s\" was transformed to class \"%s\"%n",
							  genericArray.getClass().getCanonicalName(), genericObjArr.getClass().getCanonicalName());
			System.out.printf("array of type \"%s\" was transformed to class \"%s\"%n",
							  pBooleanArray.getClass().getCanonicalName(), boolObjArr.getClass().getCanonicalName());
			System.out.printf("array of type \"%s\" was transformed to class \"%s\"%n",
							  pCharArray.getClass().getCanonicalName(), charObjArr.getClass().getCanonicalName());
			System.out.printf("array of type \"%s\" was transformed to class \"%s\"%n",
							  pByteArray.getClass().getCanonicalName(), byteObjArr.getClass().getCanonicalName());
			System.out.printf("array of type \"%s\" was transformed to class \"%s\"%n",
							  pShortArray.getClass().getCanonicalName(), shortObjArr.getClass().getCanonicalName());
			System.out.printf("array of type \"%s\" was transformed to class \"%s\"%n",
							  pIntegerArray.getClass().getCanonicalName(), intObjArr.getClass().getCanonicalName());
			System.out.printf("array of type \"%s\" was transformed to class \"%s\"%n",
							  pLongArray.getClass().getCanonicalName(), longObjArr.getClass().getCanonicalName());
			System.out.printf("array of type \"%s\" was transformed to class \"%s\"%n",
							  pFloatArray.getClass().getCanonicalName(), floatObjArr.getClass().getCanonicalName());
			System.out.printf("array of type \"%s\" was transformed to class \"%s\"%n",
							  pDoubleArray.getClass().getCanonicalName(), doubleObjArr.getClass().getCanonicalName());
		});
	}
	
	@Test
	public void transformTest() {
		sectionOf(() -> {
			// Temporal variables
			var genericTransform = UArrays.transform(genericArray,
													 it -> UArrays.toObjectArray(it.getBytes()),
													 Byte[][]::new);
			var booleanTransform = UArrays.primitiveTransform(pBooleanArray,
															  it -> (byte) (it ? 1 : 0),
															  Byte[]::new);
			
			// Assertions
			Assertions.assertArrayEquals(UArrays.toObjectArray(UArrays.byteOf(1, 0, 0, 0, 1, 1)),
										 booleanTransform, "Invalid array conversion");
			Assertions.assertArrayEquals(
				UArrays.of(toObjectArray(genericArray[0].getBytes()), toObjectArray(genericArray[1].getBytes()),
						   toObjectArray(genericArray[2].getBytes()), toObjectArray(genericArray[3].getBytes())),
				genericTransform, "Invalid array conversion");
			
			System.out.println(UToStringManager.getInstance().toString(genericTransform));
			System.out.println(UToStringManager.getInstance().toString(booleanTransform));
		});
	}
	
}