package ushiosan.jvm_utilities.lang;

import org.junit.Assert;
import org.junit.Test;
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
		var location = applyErrNotNull(loader.getResource("simple.txt"),
									   IO::pathOf);
		Assert.assertTrue("Location cannot be null", location.isPresent());
		
		System.out.println("applyErrTest()");
		System.err.println(location.get());
		System.out.println();
	}
	
}