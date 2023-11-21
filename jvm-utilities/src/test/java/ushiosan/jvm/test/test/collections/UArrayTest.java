package ushiosan.jvm.test.test.collections;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ushiosan.jvm.collections.UArray;
import ushiosan.jvm.print.UToStringManager;
import ushiosan.jvm.test.UTestUnit;
import ushiosan.jvm.test.test.Constants;

public class UArrayTest extends UTestUnit {
	
	/**
	 * The name of the module where the tests are being done
	 *
	 * @return the module name
	 */
	@Override
	public @NotNull String module() {
		return Constants.LIB_MODULE;
	}
	
	/* -----------------------------------------------------
	 * Global properties
	 * ----------------------------------------------------- */
	
	// Generic array generation
	private final String[] genericArray = UArray.make("Hello", ", ", "World", "!");
	// Primitive array generators
	private final boolean[] pBooleanArray = UArray.makeBoolean(true, false, false, false, true, true);
	private final char[] pCharArray = UArray.makeChar('H', 'e', 'l', 'l', 'o', '!');
	private final byte[] pByteArray = UArray.makeByte(0, 1, 2, 4, 8, 16, 32, 64, 127);
	private final short[] pShortArray = UArray.makeShort(2, 4, 6, 8, 10, 12, 14, 16, 18, 20);
	private final int[] pIntegerArray = UArray.makeInt(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12903, Integer.MAX_VALUE);
	private final long[] pLongArray = UArray.makeLong(890128093, 98791829, 98792845, 1090128);
	private final float[] pFloatArray = UArray.makeFloat(1234.1234f, 365f);
	private final double[] pDoubleArray = UArray.makeDouble(Math.PI, 1289.198279182739123, Math.E);
	
	/* -----------------------------------------------------
	 * Test methods
	 * ----------------------------------------------------- */
	
	@Test
	public void ofTest() {
		makeSection(() -> {
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
			println("Generic array:   %s", (Object) genericArray);
			println("Boolean array:   %s", pBooleanArray);
			println("Character array: %s", pCharArray);
			println("Byte array:      %s", pByteArray);
			println("Short array:     %s", pShortArray);
			println("Integer array:   %s", pIntegerArray);
			println("Long array:      %s", pLongArray);
			println("Float array:     %s", pFloatArray);
			println("Double array:    %s", pDoubleArray);
		});
	}
	
	@Test
	public void indexOfTest() {
		makeSection(() -> {
			// Temporal variables
			int genericArrayIndex = UArray.indexOf(genericArray, "!");
			int pByteArrayIndex = UArray.primitiveIndexOf(pByteArray, 64);
			int pShortArrayIndex = UArray.primitiveIndexOf(pShortArray, 4);
			int pIntegerArrayIndex = UArray.primitiveIndexOf(pIntegerArray, 12903);
			int pLongArrayIndex = UArray.primitiveIndexOf(pLongArray, 890128093);
			int pFloatArrayIndex = UArray.primitiveIndexOf(pFloatArray, 365.0f);
			int pDoubleArrayIndex = UArray.primitiveIndexOf(pDoubleArray, Math.PI);
			
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
			println("Index of element %9s from %57s array is: %d",
					"!", genericArray, genericArrayIndex);
			println("Index of element %9d from %57s array is: %d",
					64, pByteArray, pByteArrayIndex);
			println("Index of element %9d from %57s array is: %d",
					4, pShortArray, pShortArrayIndex);
			println("Index of element %9d from %57s array is: %d",
					12903, pIntegerArray, pIntegerArrayIndex);
			println("Index of element %9d from %57s array is: %d",
					890128093, pLongArray, pLongArrayIndex);
			println("Index of element %3.5f from %57s array is: %d",
					365.0f, pFloatArray, pFloatArrayIndex);
			println("Index of element %1.7f from %57s array is: %d",
					Math.PI, pDoubleArray, pDoubleArrayIndex);
		});
	}
	
	@Test
	public void lastIndexOfTest() {
		makeSection(() -> {
			// Temporal variables
			int genericArrayIndex = UArray.lastIndexOf(genericArray, "!");
			int pByteArrayIndex = UArray.primitiveLastIndexOf(pByteArray, 64);
			int pShortArrayIndex = UArray.primitiveLastIndexOf(pShortArray, 4);
			int pIntegerArrayIndex = UArray.primitiveLastIndexOf(pIntegerArray, 12903);
			int pLongArrayIndex = UArray.primitiveLastIndexOf(pLongArray, 890128093);
			int pFloatArrayIndex = UArray.primitiveLastIndexOf(pFloatArray, 365.0f);
			int pDoubleArrayIndex = UArray.primitiveLastIndexOf(pDoubleArray, Math.PI);
			
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
			println("Index of element %9s from %57s array is: %d",
					"!", genericArray, genericArrayIndex);
			println("Index of element %9d from %57s array is: %d",
					64, pByteArray, pByteArrayIndex);
			println("Index of element %9d from %57s array is: %d",
					4, pShortArray, pShortArrayIndex);
			println("Index of element %9d from %57s array is: %d",
					12903, pIntegerArray, pIntegerArrayIndex);
			println("Index of element %9d from %57s array is: %d",
					890128093, pLongArray, pLongArrayIndex);
			println("Index of element %3.5f from %57s array is: %d",
					365.0f, pFloatArray, pFloatArrayIndex);
			println("Index of element %1.7f from %57s array is: %d",
					Math.PI, pDoubleArray, pDoubleArrayIndex);
		});
	}
	
	@SuppressWarnings("ConstantValue")
	@Test
	public void containsTest() {
		makeSection(() -> {
			boolean containsGeneric = UArray.contains(genericArray, "!");
			boolean containsByte = UArray.primitiveContains(pByteArray, 64);
			boolean containsShort = UArray.primitiveContains(pShortArray, 4);
			boolean containsInt = UArray.primitiveContains(pIntegerArray, 12903);
			boolean containsLong = UArray.primitiveContains(pLongArray, 890128093);
			boolean containsFloat = UArray.primitiveContains(pFloatArray, 365.0f);
			boolean containsDouble = UArray.primitiveContains(pDoubleArray, Math.PI);
			
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
			println("The element %9s exists in %57s array?: %s",
					"!", genericArray, containsGeneric);
			println("The element %9s exists in %57s array?: %s",
					64, pByteArray, containsByte);
			println("The element %9s exists in %57s array?: %s",
					4, pShortArray, containsShort);
			println("The element %9s exists in %57s array?: %s",
					12903, pIntegerArray, containsInt);
			println("The element %9s exists in %57s array?: %s",
					890128093, pLongArray, containsLong);
			println("The element %3.5f exists in %57s array?: %s",
					365.0f, pFloatArray, containsFloat);
			println("The element %1.7f exists in %57s array?: %s",
					Math.PI, pDoubleArray, containsDouble);
		});
	}
	
	@Test
	public void lastElementTest() {
		makeSection(() -> {
			// Temporal variables
			var genericLast = UArray.lastElement(genericArray);
			var charLast = UArray.primitiveLastElement(pCharArray);
			var byteLast = UArray.primitiveLastElement(pByteArray);
			var shortLast = UArray.primitiveLastElement(pShortArray);
			var intLast = UArray.primitiveLastElement(pIntegerArray);
			var longLast = UArray.primitiveLastElement(pLongArray);
			var floatLast = UArray.primitiveLastElement(pFloatArray);
			var doubleLast = UArray.primitiveLastElement(pDoubleArray);
			
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
			println("Last element of %9s array is: %s",
					"String", genericLast.get());
			println("Last element of %9s array is: %s",
					"Character", charLast.get());
			println("Last element of %9s array is: %s",
					"Byte", byteLast.get());
			println("Last element of %9s array is: %s",
					"Short", shortLast.get());
			println("Last element of %9s array is: %s",
					"Integer", intLast.get());
			println("Last element of %9s array is: %s",
					"Long", longLast.get());
			println("Last element of %9s array is: %s",
					"Float", floatLast.get());
			println("Last element of %9s array is: %s",
					"Double", doubleLast.get());
		});
	}
	
	@Test
	public void toObjectArrayTest() {
		makeSection(() -> {
			// Temporal variables
			Object[] genericObjArr = UArray.toGenericObjectArray(genericArray);
			Character[] charObjArr = UArray.toObjectArray(pCharArray);
			Boolean[] boolObjArr = UArray.toObjectArray(pBooleanArray);
			Byte[] byteObjArr = UArray.toObjectArray(pByteArray);
			Short[] shortObjArr = UArray.toObjectArray(pShortArray);
			Integer[] intObjArr = UArray.toObjectArray(pIntegerArray);
			Long[] longObjArr = UArray.toObjectArray(pLongArray);
			Float[] floatObjArr = UArray.toObjectArray(pFloatArray);
			Double[] doubleObjArr = UArray.toObjectArray(pDoubleArray);
			
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
			println("array of type %s was transformed to class %s",
					genericArray.getClass().getCanonicalName(), genericObjArr.getClass().getCanonicalName());
			println("array of type %s was transformed to class %s",
					pBooleanArray.getClass().getCanonicalName(), boolObjArr.getClass().getCanonicalName());
			println("array of type %s was transformed to class %s",
					pCharArray.getClass().getCanonicalName(), charObjArr.getClass().getCanonicalName());
			println("array of type %s was transformed to class %s",
					pByteArray.getClass().getCanonicalName(), byteObjArr.getClass().getCanonicalName());
			println("array of type %s was transformed to class %s",
					pShortArray.getClass().getCanonicalName(), shortObjArr.getClass().getCanonicalName());
			println("array of type %s was transformed to class %s",
					pIntegerArray.getClass().getCanonicalName(), intObjArr.getClass().getCanonicalName());
			println("array of type %s was transformed to class %s",
					pLongArray.getClass().getCanonicalName(), longObjArr.getClass().getCanonicalName());
			println("array of type %s was transformed to class %s",
					pFloatArray.getClass().getCanonicalName(), floatObjArr.getClass().getCanonicalName());
			println("array of type %s was transformed to class %s",
					pDoubleArray.getClass().getCanonicalName(), doubleObjArr.getClass().getCanonicalName());
		});
	}
	
	@Test
	public void transformTest() {
		makeSection(() -> {
			// Temporal variables
			var genericTransform = UArray.transform(genericArray,
													it -> UArray.toObjectArray(it.getBytes()),
													Byte[][]::new);
			var booleanTransform = UArray.primitiveTransform(pBooleanArray,
															 it -> (byte) (it ? 1 : 0),
															 Byte[]::new);
			
			// Assertions
			Assertions.assertArrayEquals(UArray.toObjectArray(UArray.makeByte(1, 0, 0, 0, 1, 1)),
										 booleanTransform, "Invalid array conversion");
			Assertions.assertArrayEquals(
				UArray.make(UArray.toObjectArray(genericArray[0].getBytes()), UArray.toObjectArray(genericArray[1].getBytes()),
							UArray.toObjectArray(genericArray[2].getBytes()), UArray.toObjectArray(genericArray[3].getBytes())),
				genericTransform, "Invalid array conversion");
			
			System.out.println(UToStringManager.getInstance().toString(genericTransform));
			System.out.println(UToStringManager.getInstance().toString(booleanTransform));
		});
	}
	
}