package ushiosan.jvm;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ushiosan.jvm.collections.UArray;
import ushiosan.jvm.test.UTestUnit;

import javax.swing.*;

public class UClassTest extends UTestUnit {
	
	/**
	 * The name of the module where the tests are being done
	 *
	 * @return the module name
	 */
	@Override
	public @NotNull String module() {
		return Constants.LIB_MODULE;
	}
	
	@SuppressWarnings("ConstantValue")
	@Test
	public void isPrimitiveTest() {
		makeSection(() -> {
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
			println("%s is a primitive type: %s",
					12, primitiveInt);
			println("'%s' is a primitive type: %s",
					'c', primitiveChar);
			println("<%s> is a primitive type: %s",
					false, primitiveBool);
			println("%s is a primitive type: %s",
					"Hello, World!", textObject);
		});
	}
	
	@Test
	public void toVarargTypesTest() {
		makeSection(() -> {
			// Temporal variables
			var completeArray = UClass.toVarargTypes(
				UArray.<Object>make(12, 'c', "Hello"));
			var incompleteArray = UClass.toVarargTypes(
				UArray.<Object>make("XD", null, 3.145f));
			
			// Assertions
			Assertions.assertArrayEquals(
				UArray.make(Integer.class, Character.class, String.class),
				completeArray, "Incompatible arrays");
			Assertions.assertArrayEquals(
				UArray.make(String.class, Object.class, Float.class),
				incompleteArray, "Incompatible arrays");
			
			// Display information
			println("%s array vararg types: %s",
					"Complete", completeArray);
			println("%s array vararg types: %s",
					"Incomplete", incompleteArray);
		});
	}
	
	@Test
	public void classStackTest() {
		makeSection(() -> {
			// Temporal variables with different deep
			var cls = JButton.class;
			var root = UClass.classStack(cls);
			var alone = UClass.classStack(cls, 1);
			var specific = UClass.classStack(cls, 4);
			
			println("Root class:     %s", root);
			println("Alone class:    %s", alone);
			println("Specific deep:  %s", specific);
		});
	}
	
}