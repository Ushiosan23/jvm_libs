package ushiosan.jvm.print;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ushiosan.UTestUnit;
import ushiosan.jvm.collections.UArray;
import ushiosan.jvm.collections.UList;

import java.awt.*;
import java.util.HashMap;

class UToStringManagerTest extends UTestUnit {
	
	
	@AfterAll
	public static void registerExtensionTest() {
		sectionOf(() -> {
			var extension = new UToStringComponent() {
				
				@Override
				public boolean arraysOnly() {
					return false;
				}
				
				@Override
				public Class<?>[] supportedElements() {
					return UArray.of(RecursiveCallClass.class);
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
			Assertions.assertEquals("ushiosan.jvm.print.UToStringManagerTest.RecursiveCallClass{}",
									recursiveCallClassStrV, "Invalid component selection");
			
			System.out.printf("Verbose mode off: %s%n", recursiveCallClassStr);
			System.out.printf("Verbose mode on: %s%n", recursiveCallClassStrV);
		});
	}
	
	@Test
	public void toStringClassTest() {
		sectionOf(() -> {
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
			System.out.printf("Class verbose mode off: %s%n", clsStr);
			System.out.printf("Class verbose mode on:  %s%n%n", clsStrVerbose);
			
			System.out.printf("Class array verbose mode off: %s%n", arrayClsStr);
			System.out.printf("Class array verbose mode on:  %s%n", arrayClsStrV);
		});
	}
	
	@Test
	public void toStringStringTest() {
		sectionOf(() -> {
			var content = "Hello, World!";
			var contentStr = UToStringManager.getInstance()
				.toString(content, false);
			
			
			System.out.printf("Original string:       %s%n", content);
			System.out.printf("String representation: %s%n", contentStr);
		});
	}
	
	@Test
	public void toStringObjectTest() {
		sectionOf(() -> {
			var anyObject = new Dimension(1000, 600);
			var anyObjectStr = UToStringManager.getInstance()
				.toString(anyObject);
			
			Assertions.assertEquals(anyObject.toString(), anyObjectStr,
									"Invalid Object string representation");
			
			System.out.printf("Original string:  %s%n", anyObject);
			System.out.printf("Generated string: %s%n", anyObjectStr);
		});
	}
	
	@Test
	public void toStringObjectRecursiveTest() {
		sectionOf(() -> {
			var recursiveObj = new RecursiveCallClass();
			var recursiveObjStr = UToStringManager.getInstance()
				.toString(recursiveObj);
			
			Assertions.assertTrue(recursiveObjStr.startsWith("E("),
								  "Invalid recursive call");
			
			System.out.println(recursiveObjStr);
		});
	}
	
	@Test
	public void toStringCollectionTest() {
		sectionOf(() -> {
			var dimensionalArray = UArray.of(
				UArray.intOf(1, 2),
				UArray.intOf(1, 2),
				UArray.intOf(1, 2));
			var list = UList.listOf(2, 4, 6, 8, Integer.class, new Object(), new Object() {});
			var map = new HashMap<>();
			map.put(1, "Hello");
			map.put(String.class, UArray.of(2, 4, 6, 8, 10, 12));
			
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
			
			System.out.printf("Dimensional array string representation:           %s%n", dimensionalArrayStr);
			System.out.printf("Dimensional array string representation (verbose): %s%n%n", dimensionalArrayStrV);
			
			System.out.printf("List string representation:           %s%n", listStr);
			System.out.printf("List string representation (verbose): %s%n", listStrV);
			System.out.printf("Map string representation:            %s%n", mapStr);
			System.out.printf("Map string representation (verbose):  %s%n", mapStrV);
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