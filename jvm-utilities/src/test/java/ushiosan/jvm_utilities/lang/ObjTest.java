package ushiosan.jvm_utilities.lang;

import static ushiosan.jvm_utilities.lang.Obj.toDetailString;
import static ushiosan.jvm_utilities.lang.Obj.toObjString;

import org.junit.Test;

import java.awt.Dimension;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import ushiosan.jvm_utilities.lang.collection.Arrs;
import ushiosan.jvm_utilities.lang.collection.Collections;
import ushiosan.jvm_utilities.lang.collection.elements.Pair;

public class ObjTest {

	@Test
	public void runTest() {
		List<String> strings = Collections.listOf("Hello", ",", "World!");
		int[] ints = Arrs.intOf(2, 4, 6, 8, 10);
		IntStream intStream = java.util.Arrays.stream(ints);
		Map<Character, Object> objectMap = Collections.mapOf(
			Pair.of('X', null),
			Pair.of('m', new Dimension()),
			Pair.of('t', this)
		);
		byte[] bytes = Arrs.byteOf(0x0, 0xb1f, 12);
		Object[] all = Arrs.of(strings, ints, objectMap, bytes, intStream);

		System.out.println(toObjString(all));
		System.out.println(toDetailString(all));
	}

}