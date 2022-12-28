package ushiosan.jvm_utilities.function.predicate;

import org.junit.Assert;
import org.junit.Test;
import ushiosan.jvm_utilities.lang.io.IO;

import javax.swing.JFileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static ushiosan.jvm_utilities.lang.Obj.also;

public class ExtensionPredicateTest {
	
	private final JFileChooser chooser = also(new JFileChooser(), it -> {
		File home = new File(System.getProperty("user.home"));
		// Configure chooser
		it.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		it.setCurrentDirectory(home);
	});
	
	@Test
	public void runTest() throws IOException {
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
		}
	}
	
}