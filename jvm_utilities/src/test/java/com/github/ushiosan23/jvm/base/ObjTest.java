package com.github.ushiosan23.jvm.base;

import com.github.ushiosan23.jvm.collections.Arr;
import org.junit.After;
import org.junit.Test;

import java.util.Locale;

public class ObjTest {

	@Test
	public void runApplyTest() {
		String base = Obj.apply("Hello, World ", element -> element
			.toUpperCase(Locale.ROOT)
			.trim()
		);
		System.err.println(base);
	}

	@SuppressWarnings("ConstantConditions")
	@After
	public void runNotNull() {
		String invalid = null;
		String valid = Obj.notNull(invalid, "Hello, World!");

		System.err.printf("invalid var is null: %s\n", invalid);
		System.err.println(valid);
	}

	@After
	public void runTryCast() {
		Object specificObject = 12;
		Obj.tryCast(
			specificObject,
			Integer.class,
			System.err::println
		);
	}


	@After
	public void toStringTest() {
		int[] arr = Arr.ofInt(2, 4, 6, 8);
		String[] data = Arr.of("Hello,", "World", "!");
		String simpleData = "Hello";

		System.err.println(Obj.toInfoString(arr));
		System.err.println(Obj.toInfoString(data));
		System.err.println(Obj.toInfoString(simpleData));
	}


}
