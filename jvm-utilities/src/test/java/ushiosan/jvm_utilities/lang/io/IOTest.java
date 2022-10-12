package ushiosan.jvm_utilities.lang.io;

import java.net.URL;
import java.nio.file.Path;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import ushiosan.jvm_utilities.lang.Obj;
import ushiosan.jvm_utilities.lang.collection.Arrs;

public class IOTest {

	ClassLoader loader = ClassLoader
		.getSystemClassLoader();

	@Test
	public void getBaseName() throws Exception {
		URL location = loader.getResource("simple.txt");
		Assert.assertNotNull("Location not found.", location);

		Path filePath = IO.pathOf(location);
		String basename = IO.getBaseName(filePath);

		Assert.assertEquals("simple", basename);
		System.out.println(
			Obj.toString(Arrs.of(filePath, basename))
		);
	}

	@Test
	public void getAllExtensions() throws Exception {
		URL location = loader.getResource("example.file.properties");
		Assert.assertNotNull("Location not found.", location);

		Path filePath = IO.pathOf(location);
		String[] extensions = IO.getAllExtensions(filePath);

		Assert.assertEquals("Inconsistent number of extensions.", 2, extensions.length);
		System.out.println(
			Obj.toString(Arrs.of(filePath, extensions))
		);
	}

	@Test
	public void getExtension() throws Exception {
		URL location = loader.getResource("example.file.properties");
		Assert.assertNotNull("Location not found.", location);

		Path filePath = IO.pathOf(location);
		Optional<String> extension = IO.getExtension(filePath);

		Assert.assertTrue("The file has no extensions", extension.isPresent());
		System.out.println(
			Obj.toString(Arrs.of(filePath, extension))
		);
	}

	@Test
	public void getExtensionUnsafe() throws Exception {
		URL location = loader.getResource("example.file.properties");
		Assert.assertNotNull("Location not found.", location);

		Path filePath = IO.pathOf(location);
		String extension = IO.getExtensionUnsafe(filePath);

		Assert.assertEquals("The file has no extensions", "properties", extension);
		System.out.println(
			Obj.toString(Arrs.of(filePath, extension))
		);
	}

}