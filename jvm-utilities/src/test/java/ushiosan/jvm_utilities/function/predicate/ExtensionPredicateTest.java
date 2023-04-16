package ushiosan.jvm_utilities.function.predicate;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import ushiosan.jvm_utilities.TestUtils;
import ushiosan.jvm_utilities.lang.io.IO;
import ushiosan.jvm_utilities.lang.io.ZipUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipFile;

import static ushiosan.jvm_utilities.lang.Obj.also;
import static ushiosan.jvm_utilities.lang.io.IO.pathOf;

public class ExtensionPredicateTest {
	
	private final JFileChooser chooser = also(new JFileChooser(), it -> {
		File home = new File(System.getProperty("user.home"));
		// Configure chooser
		it.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		it.setCurrentDirectory(home);
	});
	ClassLoader loader = ClassLoader
		.getSystemClassLoader();
	
	@Ignore
	@Test
	public void extensionPredicateTest() throws IOException {
		TestUtils.printSection();
		
		Assert.assertEquals("Action cancelled",
							JFileChooser.APPROVE_OPTION,
							chooser.showOpenDialog(null));
		// Get chooser file
		Path location = chooser.getSelectedFile()
			.toPath();
		
		try (var walk = IO.walkDir(location, false,
								   Files::isRegularFile,
								   ExtensionPredicate.of(false, "jar"))
		) {
			List<Path> files = walk.collect(Collectors.toUnmodifiableList());
			Assert.assertFalse("List cannot be empty", files.isEmpty());
			
			System.out.println(files);
			System.out.println();
		}
	}
	
	@Test
	public void extensionPredicateZipTest() throws IOException {
		TestUtils.printSection();
		
		URL location = loader.getResource("zip-example.zip");
		Assert.assertNotNull("Location not found.", location);
		
		Path filePath = pathOf(location);
		try (var zipFile = new ZipFile(filePath.toFile())) {
			var entries = zipFile.stream()
				.filter(ZipUtils::isRegularFile)
				.filter(ExtensionPredicate.of(false, "jar"))
				.collect(Collectors.toUnmodifiableList());
			
			Assert.assertFalse("Zip not contains *.jar files", entries.isEmpty());
			
			System.out.println(entries);
			System.out.println();
		}
	}
	
	@Test
	public void regexPredicateZip() throws IOException {
		TestUtils.printSection();
		
		URL location = loader.getResource("zip-example.zip");
		Assert.assertNotNull("Location not found.", location);
		
		Path filePath = pathOf(location);
		try (var zipFile = new ZipFile(filePath.toFile())) {
			var entries = zipFile.stream()
				.filter(RegexPredicate.of(true, "(sub folder)/.+"))
				.collect(Collectors.toUnmodifiableList());
			
			System.out.println(entries);
			System.out.println();
		}
	}
	
}