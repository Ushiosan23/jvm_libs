package com.github.ushiosan23.jvm.system.error;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;

public class ExceptionUtilsTest {

	private void generateError() throws Exception {
		try (FileInputStream stream = new FileInputStream("file_not_found.txt")) {
			System.out.println(stream.getChannel());
		}
	}

	@Test
	public void getFullStackTraceString() {
		try {
			generateError();
		} catch (Exception e) {
			String stackTrace = ExceptionUtils.toString(e, true);
			Assert.assertNotNull(stackTrace);
			System.err.println(stackTrace);
		}
	}

	@Test
	public void getShortStackTraceString() {
		try {
			generateError();
		} catch (Exception e) {
			String stackTrace = ExceptionUtils.toString(e, false);
			Assert.assertNotNull(stackTrace);
			System.err.println(stackTrace);
		}
	}

}
