package ushiosan.jvm_utilities.lang;

import org.junit.Assert;
import org.junit.Test;
import ushiosan.jvm_utilities.lang.collection.Arrs;

import java.awt.Dimension;
import java.util.List;

public class ClsTest {
	
	@Test
	public void isPrimitiveTest() {
		Class<?> primitiveType = int.class;
		Class<?> primitiveChar = char.class;
		Class<?> primitiveWrapper = Integer.class;
		Class<?> notPrimitive = List.class;
		
		Assert.assertTrue("Invalid primitive", Cls.isPrimitive(primitiveType));
		Assert.assertTrue("Invalid primitive", Cls.isPrimitive(primitiveChar));
		Assert.assertTrue("Invalid primitive", Cls.isPrimitive(primitiveWrapper));
		Assert.assertFalse("Valid primitive", Cls.isPrimitive(notPrimitive));
		
		String primitiveTypeStr = Obj.toString(primitiveType);
		String primitiveCharStr = Obj.toString(primitiveChar);
		String primitiveWrapperStr = Obj.toString(primitiveWrapper);
		String notPrimitiveStr = Obj.toString(notPrimitive);
		
		System.out.printf("\n%s is primitive: %s\n", primitiveTypeStr, Cls.isPrimitive(primitiveType));
		System.out.printf("%s is primitive: %s\n", primitiveCharStr, Cls.isPrimitive(primitiveChar));
		System.out.printf("%s is primitive: %s\n", primitiveWrapperStr, Cls.isPrimitive(primitiveWrapper));
		System.out.printf("%s is primitive: %s\n", notPrimitiveStr, Cls.isPrimitive(notPrimitive));
	}
	
	@Test
	public void isPrimitiveArrayTest() {
		Class<?> primitiveArray = int[].class;
		Class<?> notPrimitiveArray = String[].class;
		
		Assert.assertTrue("Invalid primitive array", Cls.isPrimitiveArray(primitiveArray));
		Assert.assertFalse("Is primitive array", Cls.isPrimitiveArray(notPrimitiveArray));
		
		String primArrStr = Obj.toString(primitiveArray);
		String notPrimArrStr = Obj.toString(notPrimitiveArray);
		System.out.printf("\n%s is primitive array: %s\n", primArrStr, Cls.isPrimitiveArray(primitiveArray));
		System.out.printf("%s is primitive array: %s\n", notPrimArrStr, Cls.isPrimitiveArray(notPrimitiveArray));
	}
	
	@Test
	public void toTypeArgsTest() {
		Object[] args = Arrs.of("Hello", 12, new Dimension());
		Class<?>[] typeArgs = Cls.toTypeArgs(args);
		
		System.out.println();
		System.out.println(Obj.toString(typeArgs));
	}
	
	@Test
	public void getArrayIndividualClassTest() {
		int[] arr = new int[0];
		var cls = Cls.getArrayMultipleIndividualClass(arr);
		
		Assert.assertEquals("Invalid type class", cls, int.class);
		System.err.println(cls);
	}
	
}