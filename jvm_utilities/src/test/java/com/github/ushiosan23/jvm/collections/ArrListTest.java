package com.github.ushiosan23.jvm.collections;

import org.junit.After;
import org.junit.Test;

import java.util.List;

public class ArrListTest {

	@Test(expected = UnsupportedOperationException.class)
	@SuppressWarnings("ConstantConditions")
	public void runOfTest() {
		List<String> immutableList = ArrList.of("Hello", ", ", "World!");

		System.err.println(immutableList);
		immutableList.add("Invalid action");
		System.err.println(immutableList);
	}

	@After
	public void runMutableOfTest() {
		List<String> immutableList = ArrList.mutableOf("Hello", ", ", "World!");
		immutableList.add("Valid insert");
		System.err.println(immutableList);
	}

}
