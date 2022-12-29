package ushiosan.jvm_utilities.lang.io;

import org.junit.Assert;
import org.junit.Test;
import ushiosan.jvm_utilities.lang.Obj;
import ushiosan.jvm_utilities.lang.collection.Arrs;

import java.net.URL;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.ZipFile;

import static ushiosan.jvm_utilities.lang.io.IO.pathOf;

public class IOTest {
	
	ClassLoader loader = ClassLoader
		.getSystemClassLoader();
	
	@Test
	public void getBaseName() throws Exception {
		URL location = loader.getResource("simple.txt");
		Assert.assertNotNull("Location not found.", location);
		
		Path filePath = pathOf(location);
		String basename = IO.getBaseName(filePath);
		
		Assert.assertEquals("simple", basename);
		System.out.println(
			Obj.toString(Arrs.of(filePath, basename))
		);
	}
	
	@Test
	public void getZipBaseName() throws Exception {
		URL location = loader.getResource("zip-example.zip");
		Assert.assertNotNull("Location not found.", location);
		
		Path filePath = pathOf(location);
		try (var zipFile = new ZipFile(filePath.toFile())) {
			var entries = zipFile.stream()
				.map(IO::getBaseName)
				.collect(Collectors.toUnmodifiableList());
			
			for (var entry : entries) {
				Assert.assertTrue("Entry has extensions",
								  IO.getExtensionImpl(IO.getAllExtensionsImpl(entry)).isEmpty());
			}
			
			System.out.println(entries);
		}
	}
	
	@Test
	public void getAllExtensions() throws Exception {
		URL location = loader.getResource("example.file.properties");
		Assert.assertNotNull("Location not found.", location);
		
		Path filePath = pathOf(location);
		String[] extensions = IO.getAllExtensions(filePath);
		
		Assert.assertEquals("Inconsistent number of extensions.", 2, extensions.length);
		System.out.println(
			Obj.toString(Arrs.of(filePath, extensions))
		);
	}
	
	@Test
	public void getZipAllExtensions() throws Exception {
		URL location = loader.getResource("zip-example.zip");
		Assert.assertNotNull("Location not found.", location);
		
		Path filePath = pathOf(location);
		try (var zipFile = new ZipFile(filePath.toFile())) {
			var entries = zipFile.stream()
				.filter(it -> !it.isDirectory())
				.map(IO::getAllExtensions)
				.collect(Collectors.toUnmodifiableList());
			
			System.out.println(Obj.toString(entries));
		}
	}
	
	@Test
	public void getExtension() throws Exception {
		URL location = loader.getResource("example.file.properties");
		Assert.assertNotNull("Location not found.", location);
		
		Path filePath = pathOf(location);
		Optional<String> extension = IO.getExtension(filePath);
		
		Assert.assertTrue("The file has no extensions", extension.isPresent());
		System.out.println(
			Obj.toString(Arrs.of(filePath, extension))
		);
	}
	
	@Test
	public void getZipExtension() throws Exception {
		URL location = loader.getResource("zip-example.zip");
		Assert.assertNotNull("Location not found.", location);
		
		Path filePath = pathOf(location);
		try (var zipFile = new ZipFile(filePath.toFile())) {
			var entries = zipFile.stream()
				.filter(it -> !it.isDirectory())
				.map(IO::getExtension)
				.collect(Collectors.toUnmodifiableList());
			
			System.out.println(entries);
		}
	}
	
	@Test
	public void getExtensionUnsafe() throws Exception {
		URL location = loader.getResource("example.file.properties");
		Assert.assertNotNull("Location not found.", location);
		
		Path filePath = pathOf(location);
		String extension = IO.getExtensionUnsafe(filePath);
		
		Assert.assertEquals("The file has no extensions", "properties", extension);
		System.out.println(
			Obj.toString(Arrs.of(filePath, extension))
		);
	}
	
	@Test
	public void getZipExtensionUnsafe() throws Exception {
		URL location = loader.getResource("zip-example.zip");
		Assert.assertNotNull("Location not found.", location);
		
		Path filePath = pathOf(location);
		try (var zipFile = new ZipFile(filePath.toFile())) {
			var entries = zipFile.stream()
				.filter(it -> !it.isDirectory())
				.map(IO::getExtensionUnsafe)
				.collect(Collectors.toUnmodifiableList());
			
			System.out.println(entries);
		}
	}
	
}