package ushiosan.jvm_utilities.lang;

import org.junit.Assert;
import org.junit.Test;
import ushiosan.jvm_utilities.TestUtils;
import ushiosan.jvm_utilities.lang.collection.Arrs;
import ushiosan.jvm_utilities.lang.collection.Collections;
import ushiosan.jvm_utilities.lang.collection.elements.Pair;
import ushiosan.jvm_utilities.lang.io.IO;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static ushiosan.jvm_utilities.lang.Obj.applyErrNotNull;
import static ushiosan.jvm_utilities.lang.Obj.toDetailString;
import static ushiosan.jvm_utilities.lang.Obj.toObjString;

public class ObjTest {
	
	ClassLoader loader = ClassLoader.getSystemClassLoader();
	
	@Test
	public void toObjectStringTest() {
		TestUtils.printSection();
		
		List<String> strings = Collections.listOf("Hello", ",", "World!");
		int[] ints = Arrs.intOf(2, 4, 6, 8, 10);
		IntStream intStream = Arrays.stream(ints);
		Map<Character, Object> objectMap = Collections.mapOf(
			Pair.of('X', null),
			Pair.of('m', new Dimension()),
			Pair.of('t', this)
		);
		byte[] bytes = Arrs.byteOf(0x0, 0xb1f, 12);
		Object[] all = Arrs.of(strings, ints, objectMap, bytes, intStream);
		
		System.out.println("toObjectStringTest()");
		System.out.println(toObjString(all));
		System.out.println(toDetailString(all));
		System.out.println();
	}
	
	@Test
	public void applyErrTest() throws IOException {
		TestUtils.printSection();
		
		var location = applyErrNotNull(loader.getResource("simple.txt"),
									   IO::pathOf);
		Assert.assertTrue("Location cannot be null", location.isPresent());
		
		System.err.println(location.get());
		System.out.println();
	}
	
	@Test
	public void toStringArrTest() {
		TestUtils.printSection();
		
		var array = Arrs.of(new Example(), new Example(), new Example());
		var current = Obj.toObjString(array);
		var expected = "[Example{}, Example{}, Example{}]";
		
		Assert.assertEquals("Invalid result", expected, current);
		System.out.println(current);
		System.out.println();
	}
	
	@Test
	public void toStringInvalidTest() {
		TestUtils.printSection();
		
		var array = Arrs.of(new Example2(), new Example2(), new Example2());
		var current = Obj.toObjString(array);
		var expected = String.format("[(@%X) Example2, (@%X) Example2, (@%X) Example2]",
									 array[0].hashCode(),
									 array[1].hashCode(),
									 array[2].hashCode());
		
		Assert.assertNotEquals("Invalid result", expected, current);
		
		System.out.println(current);
		System.out.println();
	}
	
	/* -----------------------------------------------------
	 * Internal types
	 * ----------------------------------------------------- */
	
	static class Example {
		
		@Override
		public String toString() {
			return "Example{}";
		}
		
	}
	
	static class Example2 {
		
		@Override
		public String toString() {
			// Recursive call
			return Obj.toString(this);
		}
		
	}
	
}