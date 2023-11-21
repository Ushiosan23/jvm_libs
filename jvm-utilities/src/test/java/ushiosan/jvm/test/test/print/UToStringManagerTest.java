package ushiosan.jvm.test.test.print;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ushiosan.jvm.collections.UArray;
import ushiosan.jvm.collections.UList;
import ushiosan.jvm.print.UToStringComponent;
import ushiosan.jvm.print.UToStringManager;
import ushiosan.jvm.test.UTestUnit;
import ushiosan.jvm.test.test.Constants;

import java.awt.*;
import java.util.HashMap;

class UToStringManagerTest extends UTestUnit {
	
	@AfterAll
	public static void registerExtensionTest() {
		var access = instanceAccess(Constants.LIB_MODULE);
		access.makeSection(() -> {
			var extension = new UToStringComponent() {
				
				@Override
				public boolean arraysOnly() {
					return false;
				}
				
				@Override
				public Class<?>[] supportedElements() {
					return UArray.make(RecursiveCallClass.class);
				}
				
				@Override
				public @NotNull String toString(@NotNull Object object, boolean verbose) {
					return (verbose ? object.getClass().getCanonicalName() :
							object.getClass().getSimpleName()) + "{}";
				}
			};
			UToStringManager.getInstance()
				.registerComponent(extension);
			
			// Temporal variables
			RecursiveCallClass instance = new RecursiveCallClass();
			String recursiveCallClassStr = UToStringManager.getInstance()
				.toString(instance);
			String recursiveCallClassStrV = UToStringManager.getInstance()
				.toString(instance, true);
			
			// Assertions
			Assertions.assertEquals("RecursiveCallClass{}", recursiveCallClassStr,
									"Invalid component selection");
			Assertions.assertEquals("ushiosan.jvm.test.test.print.UToStringManagerTest.RecursiveCallClass{}",
									recursiveCallClassStrV, "Invalid component selection");
			
			access.println("Verbose mode off: %s", recursiveCallClassStr);
			access.println("Verbose mode on: %s", recursiveCallClassStrV);
		});
	}
	
	/**
	 * The name of the module where the tests are being done
	 *
	 * @return the module name
	 */
	@Override
	public @NotNull String module() {
		return Constants.LIB_MODULE;
	}
	
	@Test
	public void toStringClassTest() {
		makeSection(() -> {
			// Temporal variables
			Class<?> cls = String.class;
			String clsStr = UToStringManager.getInstance()
				.toString(cls, false);
			String clsStrVerbose = UToStringManager.getInstance()
				.toString(cls, true);
			
			Class<?> arrayCls = Object[].class;
			String arrayClsStr = UToStringManager.getInstance()
				.toString(arrayCls);
			String arrayClsStrV = UToStringManager.getInstance()
				.toString(arrayCls, true);
			
			// Assertions
			Assertions.assertEquals("String", clsStr,
									"Invalid string representation");
			Assertions.assertEquals("java.lang.String", clsStrVerbose,
									"Invalid string representation");
			Assertions.assertEquals("Object[]", arrayClsStr,
									"Invalid string representation");
			Assertions.assertEquals("java.lang.Object[]", arrayClsStrV,
									"Invalid string representation");
			
			// Display information
			println("Class verbose mode off: %s", clsStr);
			println("Class verbose mode on:  %s%n", clsStrVerbose);
			
			println("Class array verbose mode off: %s", arrayClsStr);
			println("Class array verbose mode on:  %s", arrayClsStrV);
		});
	}
	
	@Test
	public void toStringStringTest() {
		makeSection(() -> {
			var content = "Hello, World!";
			var contentStr = UToStringManager.getInstance()
				.toString(content, false);
			
			
			println("Original string:       %s", content);
			println("String representation: %s", contentStr);
		});
	}
	
	@Test
	public void toStringObjectTest() {
		makeSection(() -> {
			var anyObject = new Dimension(1000, 600);
			var anyObjectStr = UToStringManager.getInstance()
				.toString(anyObject);
			
			Assertions.assertEquals(anyObject.toString(), anyObjectStr,
									"Invalid Object string representation");
			
			println("Original string:  %s", anyObject);
			println("Generated string: %s", anyObjectStr);
		});
	}
	
	@Test
	public void toStringObjectRecursiveTest() {
		makeSection(() -> {
			var recursiveObj = new RecursiveCallClass();
			var recursiveObjStr = UToStringManager.getInstance()
				.toString(recursiveObj);
			
			Assertions.assertTrue(recursiveObjStr.startsWith("E("),
								  "Invalid recursive call");
			
			println(recursiveObjStr);
		});
	}
	
	@Test
	public void toStringCollectionTest() {
		makeSection(() -> {
			var dimensionalArray = UArray.make(
				UArray.makeInt(1, 2),
				UArray.makeInt(1, 2),
				UArray.makeInt(1, 2));
			var list = UList.make(2, 4, 6, 8, Integer.class, new Object(), new Object() {});
			var map = new HashMap<>();
			map.put(1, "Hello");
			map.put(String.class, UArray.make(2, 4, 6, 8, 10, 12));
			
			// Objects string
			var dimensionalArrayStr = UToStringManager.getInstance()
				.toString(dimensionalArray);
			var dimensionalArrayStrV = UToStringManager.getInstance()
				.toString(dimensionalArray, true);
			var listStr = UToStringManager.getInstance()
				.toString(list);
			var listStrV = UToStringManager.getInstance()
				.toString(list, true);
			var mapStr = UToStringManager.getInstance()
				.toString(map);
			var mapStrV = UToStringManager.getInstance()
				.toString(map, true);
			
			println("Dimensional array string representation:           %s", dimensionalArrayStr);
			println("Dimensional array string representation (verbose): %s%n", dimensionalArrayStrV);
			
			println("List string representation:           %s", listStr);
			println("List string representation (verbose): %s", listStrV);
			println("Map string representation:            %s", mapStr);
			println("Map string representation (verbose):  %s", mapStrV);
		});
	}
	
	/* -----------------------------------------------------
	 * Internal class
	 * ----------------------------------------------------- */
	
	public static class RecursiveCallClass {
		
		@Override
		public String toString() {
			return UToStringManager.getInstance()
				.toString(this);
		}
		
	}
	
}