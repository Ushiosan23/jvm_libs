package com.github.ushiosan23.jvm.io;

import com.github.ushiosan23.jvm.base.Obj;
import com.github.ushiosan23.jvm.collections.Arr;
import com.github.ushiosan23.jvm.io.predicates.IPredicate;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class IOUtilsTest {

	@Test
	public void runGetUserPathTest() {
		Path userLocation = IOUtils.getUserPath();
		Path downloadsPath = IOUtils.resolveUserPath("Downloads");

		System.err.println(userLocation);
		System.err.println(downloadsPath);
	}

	@After
	public void walkDirectoryTest() throws IOException {
		Path location = IOUtils.getUserPath();
		Path[] paths = IOUtils.directoryWalkArr(
			location,
			false
		);

		System.out.println("\n\n");
		for (Path item : paths) {
			System.err.println(item);
		}
	}

	@After
	public void walkRecursiveDirectoryTest() throws IOException {
		Path location = IOUtils.resolveUserPath("Downloads");
		Object[] paths = IOUtils.directoryWalk(
				location,
				true,
				Files::isRegularFile,
				IPredicate.extensionsOf("png", "jpg", "svg")
			).map(it -> Arr.of(it, IOUtils.getBaseName(it)))
			.toArray();

		System.out.println("\n\n");
		for (Object item : paths) {
			System.err.println(Obj.toBaseString(item));
		}
	}

	@After
	public void walkRecursiveRegexPredicateTest() throws IOException {
		Path location = IOUtils.resolveUserPath(".gradle");
		Path[] paths = IOUtils.directoryWalkArr(
			location,
			true,
			IPredicate.regexOf("\\.jar$")
		);

		System.out.println("\n\n");
		for (Path item : paths) {
			System.err.println(item);
		}
	}

}
