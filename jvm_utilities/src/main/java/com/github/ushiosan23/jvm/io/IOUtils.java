package com.github.ushiosan23.jvm.io;

import com.github.ushiosan23.jvm.base.Obj;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class IOUtils {

	/* ------------------------------------------------------------------
	 * Constructors
	 * ------------------------------------------------------------------ */

	/**
	 * This class cannot be instantiated
	 */
	private IOUtils() {
	}

	/* ------------------------------------------------------------------
	 * Methods
	 * ------------------------------------------------------------------ */

	/**
	 * Returns a text string with all the extensions of a file.
	 * <p>
	 * Example:
	 * <pre>{@code
	 * Path exampleFile = Path.of("build.gradle.kts");
	 * String extensions = IOUtils.getAllExtensions(exampleFile);
	 *
	 * System.out.println(extensions); // Result: ".gradle.kts"
	 * }</pre>
	 *
	 * @param path Route to evaluate
	 * @return Return all extensions of a given file
	 * @throws IllegalArgumentException Error if path is not a valid file
	 */
	public static @NotNull String getAllExtensions(Path path) {
		// Validate path
		if (Files.isDirectory(path))
			throw new IllegalArgumentException(String.format("%s is not valid file. Directory given.", path));
		// Get file name
		String filename = path.getFileName().toString();
		int lastIndex = filename.indexOf(".");
		// Check last index
		if (lastIndex == -1) return "";
		// Get extension
		return filename.substring(lastIndex);
	}

	/**
	 * Returns the extension of a given file. If the path is a directory it generates an error.
	 *
	 * @param path Route to evaluate
	 * @return Returns the extension of a given file.
	 * @throws IllegalArgumentException Error if path is not a valid file
	 */
	public static @NotNull String getExtension(Path path) {
		// Get all extensions
		String extensions = getAllExtensions(path);
		int lastIndex = extensions.lastIndexOf(".");
		// Ignore empty extensions
		if (lastIndex == -1) return "";
		// Return last extension
		return extensions.substring(lastIndex + 1);
	}

	/**
	 * Depending on the operating system, it returns the path of the current user.
	 *
	 * @return Returns the path to the current user's folder.
	 * @see Path#of(String, String...)
	 */
	public static @NotNull Path getUserPath() {
		return Path.of(System.getProperty("user.home"));
	}

	/**
	 * Returns a specific path within the user's folder.
	 *
	 * @param location Path to search
	 * @return Returns the resolved path
	 * @see Path#of(String, String...)
	 */
	public static @NotNull Path resolveUserPath(String @NotNull ... location) {
		return Path.of(System.getProperty("user.home"), location);
	}

	/**
	 * Generates a stream where it recursively iterates through all the directories within the given destination.
	 *
	 * @param location  Base directory
	 * @param recursive Determines if the stream is required to be recursive or just the first directory.
	 * @param filters   Filters applied to the stream.
	 * @return Returns a stream with all the given settings
	 * @throws IOException Error if directory not exists
	 * @see Files
	 * @see Files#isRegularFile(Path, LinkOption...)
	 * @see Files#isDirectory(Path, LinkOption...)
	 * @see Files#isExecutable(Path)
	 */
	@SafeVarargs
	public static @NotNull Stream<Path> directoryWalk(
		@NotNull Path location,
		boolean recursive,
		Predicate<Path> @NotNull ... filters
	) throws IOException {
		// Validate directory
		if (!Files.isReadable(location))
			throw new IOException(String.format("Cannot access to current location %s. Insufficient permissions.", location));
		if (!Files.isDirectory(location))
			throw new IllegalArgumentException(String.format("%s is not valid directory", location));
		// Generate walk
		Stream<Path> pathStream = recursive ?
			Files.walk(location) :
			Files.walk(location, 1);
		// Apply filters
		return Obj.apply(pathStream, element -> {
			// Iterate filters
			for (Predicate<Path> it : filters) {
				element = element.filter(it);
			}
			// Get result
			return element;
		});
	}

	/**
	 * Generates a stream where it recursively iterates through all the directories within the given destination.
	 *
	 * @param location  Base directory
	 * @param recursive Determines if the stream is required to be recursive or just the first directory.
	 * @param filters   Filters applied to the stream.
	 * @return Returns an array with all the given settings
	 * @throws IOException Error if directory not exists
	 * @see Files
	 * @see Files#isRegularFile(Path, LinkOption...)
	 * @see Files#isDirectory(Path, LinkOption...)
	 * @see Files#isExecutable(Path)
	 */
	@SafeVarargs
	public static Path @NotNull [] directoryWalkArr(
		@NotNull Path location,
		boolean recursive,
		Predicate<Path> @NotNull ... filters
	) throws IOException {
		// Call base behavior
		Stream<Path> pathStream = directoryWalk(location, recursive, filters);
		// Get array result
		Path[] result = pathStream.toArray(Path[]::new);
		// Close stream
		pathStream.close();
		return result;
	}

}
