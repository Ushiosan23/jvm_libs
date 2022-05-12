package com.github.ushiosan23.jvm.collections;

import org.junit.After;
import org.junit.Test;

import java.util.List;

public class ContainersTest {

	@Test(expected = UnsupportedOperationException.class)
	@SuppressWarnings("ConstantConditions")
	public void runOfTest() {
		List<String> immutableList = Containers.listOf("Hello", ", ", "World!");

		System.err.println(immutableList);
		immutableList.add("Invalid action");
		System.err.println(immutableList);
	}

	@After
	public void runMutableOfTest() {
		List<String> immutableList = Containers.mutableListOf("Hello", ", ", "World!");
		immutableList.add("Valid insert");
		System.err.println(immutableList);
	}

}
