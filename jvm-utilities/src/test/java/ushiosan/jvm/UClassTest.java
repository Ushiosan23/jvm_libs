package ushiosan.jvm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ushiosan.UTestUnit;
import ushiosan.jvm.collections.UArray;

import javax.swing.*;
import java.util.Arrays;

public class UClassTest extends UTestUnit {
	
	@SuppressWarnings("ConstantValue")
	@Test
	public void isPrimitiveTest() {
		sectionOf(() -> {
			// Primitives
			boolean primitiveInt = UClass.isPrimitive(12);
			boolean primitiveChar = UClass.isPrimitive('c');
			boolean primitiveBool = UClass.isPrimitive(false);
			// No primitives
			boolean textObject = UClass.isPrimitive("Hello, World!");
			
			// Assertions
			Assertions.assertTrue(primitiveInt,
								  "Is not a primitive type");
			Assertions.assertTrue(primitiveChar,
								  "Is not a primitive type");
			Assertions.assertTrue(primitiveBool,
								  "Is not a primitive type");
			Assertions.assertFalse(textObject,
								   "Is a primitive type");
			
			// Display information
			System.out.printf("%s is a primitive type: %s%n",
							  12, primitiveInt);
			System.out.printf("%s is a primitive type: %s%n",
							  "'c'", primitiveChar);
			System.out.printf("%s is a primitive type: %s%n",
							  false, primitiveBool);
			System.out.printf("%s is a primitive type: %s%n",
							  "\"Hello, World!\"", textObject);
		});
	}
	
	@Test
	public void toVarargTypesTest() {
		sectionOf(() -> {
			// Temporal variables
			var completeArray = UClass.toVarargTypes(
				UArray.<Object>of(12, 'c', "Hello"));
			var incompleteArray = UClass.toVarargTypes(
				UArray.<Object>of("XD", null, 3.145f));
			
			// Assertions
			Assertions.assertArrayEquals(
				UArray.of(Integer.class, Character.class, String.class),
				completeArray, "Incompatible arrays");
			Assertions.assertArrayEquals(
				UArray.of(String.class, Object.class, Float.class),
				incompleteArray, "Incompatible arrays");
			
			// Display information
			System.out.printf("%s array vararg types: %s%n",
							  "Complete", Arrays.toString(completeArray));
			System.out.printf("%s array vararg types: %s%n",
							  "Incomplete", Arrays.toString(incompleteArray));
		});
	}
	
	@Test
	public void classStackTest() {
		sectionOf(() -> {
			// Temporal variables with different deep
			var cls = JButton.class;
			var root = UClass.classStack(cls);
			var alone = UClass.classStack(cls, 1);
			var specific = UClass.classStack(cls, 4);
			
			System.out.printf("Root class:     %s%n", root);
			System.out.printf("Alone class:    %s%n", alone);
			System.out.printf("Specific deep:  %s%n", specific);
		});
	}
	
}