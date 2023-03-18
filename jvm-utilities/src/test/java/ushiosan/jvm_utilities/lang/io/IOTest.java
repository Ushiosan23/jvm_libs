package ushiosan.jvm_utilities.lang.io;

import org.junit.Assert;
import org.junit.Test;
import ushiosan.jvm_utilities.lang.Obj;
import ushiosan.jvm_utilities.lang.collection.Arrs;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.ZipFile;

import static ushiosan.jvm_utilities.lang.io.IO.getFileHashStr;
import static ushiosan.jvm_utilities.lang.io.IO.pathOf;

public class IOTest {
	
	ClassLoader loader = ClassLoader
		.getSystemClassLoader();
	
	@Test
	public void getBaseNameTest() throws Exception {
		URL location = loader.getResource("simple.txt");
		Assert.assertNotNull("File not found.", location);
		
		Path filePath = pathOf(location);
		String basename = IO.getBaseName(filePath);
		
		Assert.assertEquals("simple", basename);
		
		System.out.println("getBaseNameTest()");
		System.out.println(Obj.toString(Arrs.of(filePath, basename)));
		System.out.println();
	}
	
	@Test
	public void getZipBaseNameTest() throws Exception {
		URL location = loader.getResource("zip-example.zip");
		Assert.assertNotNull("File not found.", location);
		
		Path filePath = pathOf(location);
		try (var zipFile = new ZipFile(filePath.toFile())) {
			var entries = zipFile.stream()
				.map(IO::getBaseName)
				.collect(Collectors.toUnmodifiableList());
			
			for (var entry : entries) {
				Assert.assertTrue("Entry has extensions",
								  IO.getExtensionImpl(IO.getAllExtensionsImpl(entry)).isEmpty());
			}
			
			System.out.println("getZipBaseNameTest()");
			System.out.println(entries);
			System.out.println();
		}
	}
	
	@Test
	public void getAllExtensionsTest() throws Exception {
		URL location = loader.getResource("example.file.properties");
		Assert.assertNotNull("File not found.", location);
		
		Path filePath = pathOf(location);
		String[] extensions = IO.getAllExtensions(filePath);
		
		Assert.assertEquals("Inconsistent number of extensions.", 2, extensions.length);
		
		System.out.println("getAllExtensionsTest()");
		System.out.println(Obj.toString(Arrs.of(filePath, extensions)));
		System.out.println();
	}
	
	@Test
	public void getZipAllExtensionsTest() throws Exception {
		URL location = loader.getResource("zip-example.zip");
		Assert.assertNotNull("File not found.", location);
		
		Path filePath = pathOf(location);
		try (var zipFile = new ZipFile(filePath.toFile())) {
			var entries = zipFile.stream()
				.filter(it -> !it.isDirectory())
				.map(IO::getAllExtensions)
				.collect(Collectors.toUnmodifiableList());
			
			System.out.println("getZipAllExtensionsTest()");
			System.out.println(Obj.toString(entries));
			System.out.println();
		}
	}
	
	@Test
	public void getExtensionTest() throws Exception {
		URL location = loader.getResource("example.file.properties");
		Assert.assertNotNull("File not found.", location);
		
		Path filePath = pathOf(location);
		Optional<String> extension = IO.getExtension(filePath);
		
		Assert.assertTrue("The file does not contain extensions", extension.isPresent());
		
		System.out.println("getExtensionTest()");
		System.out.println(Obj.toString(Arrs.of(filePath, extension)));
		System.out.println();
	}
	
	@Test
	public void getZipExtensionTest() throws Exception {
		URL location = loader.getResource("zip-example.zip");
		Assert.assertNotNull("File not found.", location);
		
		Path filePath = pathOf(location);
		try (var zipFile = new ZipFile(filePath.toFile())) {
			var entries = zipFile.stream()
				.filter(it -> !it.isDirectory())
				.map(IO::getExtension)
				.collect(Collectors.toUnmodifiableList());
			
			System.out.println("getZipExtensionTest()");
			System.out.println(entries);
			System.out.println();
		}
	}
	
	@Test
	public void getExtensionUnsafeTest() throws Exception {
		URL location = loader.getResource("example.file.properties");
		Assert.assertNotNull("File not found.", location);
		
		Path filePath = pathOf(location);
		String extension = IO.getExtensionUnsafe(filePath);
		
		Assert.assertEquals("The file has no extensions", "properties", extension);
		
		System.out.println("getExtensionUnsafeTest()");
		System.out.println(Obj.toString(Arrs.of(filePath, extension)));
		System.out.println();
	}
	
	@Test
	public void getZipExtensionUnsafeTest() throws Exception {
		URL location = loader.getResource("zip-example.zip");
		Assert.assertNotNull("File not found.", location);
		
		Path filePath = pathOf(location);
		try (var zipFile = new ZipFile(filePath.toFile())) {
			var entries = zipFile.stream()
				.filter(it -> !it.isDirectory())
				.map(IO::getExtensionUnsafe)
				.collect(Collectors.toUnmodifiableList());
			
			System.out.println("getZipExtensionUnsafe()");
			System.out.println(entries);
			System.out.println();
		}
	}
	
	@Test
	public void getFileHashTest() throws IOException {
		URL location = loader.getResource("example.file.properties");
		Assert.assertNotNull("File not found", location);
		
		Path filePath = pathOf(location);
		String fileHash256 = getFileHashStr(filePath, StandardAlgorithms.SHA256);
		String fileHash5012 = getFileHashStr(filePath, StandardAlgorithms.SHA512);
		
		Assert.assertEquals(
			"Invalid File Hash",
			"343591a14d56d2108bf15877c599a9d5939fbf8253ec1e90f8375f39cf3a0c5ec648cd2a2e64191c269c3fb7ddad07864763e58142087ce381e9ff5a6f46c2b4",
			fileHash5012
		);
		Assert.assertEquals(
			"Invalid File Hash",
			"957e268487ff0c6092dfeda46199ba7aae34b234801471786853cb6471db1de8",
			fileHash256
		);
	}
	
}