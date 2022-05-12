package com.github.ushiosan23.jvm.io;

import com.github.ushiosan23.jvm.collections.Arr;
import com.github.ushiosan23.jvm.collections.Containers;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class PrintUtilsTest {

	@Test
	public void runTest() {
		Map<String, Object> map = Containers.mapOf(
			Containers.entry("One", 12),
			Containers.entry("Two", Arr.of(1, 2, 3, 4)),
			Containers.entry("Tree", true),
			Containers.entry("Four", Containers.entry(1, 2)),
			Containers.entry("Five", "Xd"),
			Containers.entry("Six", new Dimension(1000, 600))
		);

		System.out.println(
			PrintUtils.toString(map)
		);
		System.out.println(
			PrintUtils.toString(new JButton("XD"))
		);
	}

}
