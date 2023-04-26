package ushiosan.jvm_utilities.lang.reflection;

import org.junit.Assert;
import org.junit.Test;
import ushiosan.jvm_utilities.TestUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ReflectionUtilsTest {
	
	@Test
	public void requireModifiers() {
		TestUtils.printSection();
		
		Field[] fields = ExampleDataClass.class
			.getDeclaredFields();
		Predicate<Field> predicate = ReflectionUtils
			.requireModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL);
		
		// Generate result
		List<Field> result = Arrays.stream(fields)
			.filter(predicate)
			.collect(Collectors.toUnmodifiableList());
		
		// Assertion
		Assert.assertEquals("Invalid search", 1, result.size());
		
		result.forEach(System.out::println);
		System.out.println();
	}
	
	@Test
	public void requireModifiersInverted() {
		TestUtils.printSection();
		
		Field[] fields = ExampleDataClass.class
			.getDeclaredFields();
		Predicate<Field> predicate = ReflectionUtils
			.requireModifiers(true, Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL);
		
		// Generate result
		List<Field> result = Arrays.stream(fields)
			.filter(predicate)
			.collect(Collectors.toUnmodifiableList());
		
		// Assertion
		Assert.assertEquals("Invalid search", 2, result.size());
		
		result.forEach(System.out::println);
		System.out.println();
	}
	
	/* -----------------------------------------------------
	 * Example data
	 * ----------------------------------------------------- */
	
	static class ExampleDataClass {
		
		public static final String publicStaticFinalString = "";
		private static final int privateStaticFinalInt = -1;
		public static String publicStaticString;
		public static int publicStaticInt;
		public String publicString;
		public int publicInt;
		private String privateString;
		private int privateInt;
		
		
	}
	
	
}