package ushiosan.jvm_utilities.lang.io;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ushiosan.jvm_utilities.internal.io.IOImpl;
import ushiosan.jvm_utilities.lang.Obj;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class IO extends IOImpl {
	
	/* -----------------------------------------------------
	 * Properties
	 * ----------------------------------------------------- */
	
	/**
	 * Empty extensions array
	 */
	public static final String[] EMPTY_EXTENSIONS = new String[0];
	/**
	 * Character used to identify all file extensions
	 */
	public static final char EXTENSION_IDENTIFIER = '.';
	/**
	 * Error message to invalid file type
	 */
	private static final String INVALID_FILE_TYPE =
		"The argument is not valid \"%s\" type. A \"%s\" given";
	
	/* -----------------------------------------------------
	 * Constructors
	 * ----------------------------------------------------- */
	
	/**
	 * This class cannot be instantiated.
	 * <p>
	 * Singleton or utility class mode.
	 */
	private IO() {
	}
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Get the file base name, without any extension.
	 * <p>
	 * Example:
	 * <pre>{@code
	 * "build.gradle.kts" -> build
	 * "example.file.java" -> example
	 * }</pre>
	 *
	 * @param path the path to analyze
	 * @return the file base name
	 */
	public static @NotNull String getBaseName(@NotNull Path path) {
		// Temporal variables
		String pathStr = path.getFileName()
			.toString();
		// The directories object don't have extensions
		if (java.nio.file.Files.isDirectory(path)) {
			return pathStr;
		}
		
		// Resolve regular file name
		int extIndex = pathStr.indexOf(EXTENSION_IDENTIFIER);
		if (extIndex != -1) {
			pathStr = pathStr.substring(0, extIndex);
		}
		return pathStr;
	}
	
	/**
	 * Returns all existing extensions in a file.
	 * <p>
	 * Example:
	 * <pre>{@code
	 * "build.gradle.kts" -> [gradle, kts]
	 * "example.file.java" -> [file, java]
	 * }</pre>
	 *
	 * @param path the path to analyze
	 * @return all file extensions
	 */
	@Contract("_ -> new")
	public static String @NotNull [] getAllExtensions(@NotNull Path path) {
		// Validate regular file
		if (Files.isDirectory(path)) {
			throw new IllegalArgumentException(String.format(INVALID_FILE_TYPE, "Regular File", "Directory"));
		}
		// Temporal variables
		String baseName = getBaseName(path);
		String pathStr = path.getFileName()
			.toString();
		int extIndex = pathStr.indexOf(EXTENSION_IDENTIFIER);
		
		// Validate index
		if (extIndex == -1) {
			return EMPTY_EXTENSIONS;
		}
		// Generate result
		String pattern = "\\" + EXTENSION_IDENTIFIER;
		return Arrays.stream(pathStr.split(pattern))
			.filter(it -> !baseName.contentEquals(it))
			.toArray(String[]::new);
	}
	
	/**
	 * Returns the file extension.
	 * <p>
	 * Example:
	 * <pre>{@code
	 * "build.gradle.kts" -> Optional["kts"]
	 * "example.file.java" -> Optional["java"]
	 * "binary_file" -> Optional[empty]
	 * }</pre>
	 *
	 * @param path the path to analyze
	 * @return returns the file extension or {@link Optional#empty()} if the extension not exists
	 */
	public static @NotNull Optional<String> getExtension(@NotNull Path path) {
		// Store all extensions
		String[] extensions = getAllExtensions(path);
		if (extensions.length == 0) return Optional.empty();
		// Get only the last extension, because it is the extension
		// that defines the file format
		int lastIndex = extensions.length - 1;
		return Optional.of(extensions[lastIndex]);
	}
	
	/**
	 * Returns the file extension
	 *
	 * @param path the path to analyze
	 * @return returns the file extension or empty string if the extension not exists
	 */
	public static @NotNull String getExtensionUnsafe(@NotNull Path path) {
		return getExtension(path).orElse("");
	}
	
	/* -----------------------------------------------------
	 * User methods
	 * ----------------------------------------------------- */
	
	/**
	 * Returns a specific path within the user's folder.
	 *
	 * @param location the path to resolve
	 * @return the resolved user relative path
	 */
	public static @NotNull Path resolveUserPath(String @NotNull ... location) {
		return Path.of(System.getProperty("user.home"), location);
	}
	
	/**
	 * Depending on the operating system, it returns the path of the current user.
	 *
	 * @return the path to the current user's folder.
	 */
	public static @NotNull Path getUserPath() {
		return resolveUserPath();
	}
	
	/* -----------------------------------------------------
	 * Directory walk methods
	 * ----------------------------------------------------- */
	
	/**
	 * Generates a stream where it recursively iterates through all the directories within the given destination.
	 *
	 * @param location   base directory
	 * @param recursive  determines if the stream is required to be recursive or just the first directory
	 * @param predicates filters applied to the stream
	 * @return a stream with all the given settings
	 * @throws IOException error if directory not exists or location is not a valid directory
	 * @see Predicate
	 */
	@SafeVarargs
	public static @NotNull Stream<Path> walkDir(
		@NotNull Path location,
		boolean recursive,
		Predicate<Path> @NotNull ... predicates
	) throws IOException {
		// Generate walk object depending on recursive parameter
		Stream<Path> walkObj = recursive ?
							   Files.walk(location) :
							   Files.walk(location, 1);
		// configure walk object
		return Obj.apply(walkObj, it -> {
			// Attach filters
			for (Predicate<Path> predicate : predicates) {
				it = it.filter(predicate);
			}
			// The iterated stream should be returned because a condition
			// has already been applied, and it becomes a new stream.
			return it;
		});
	}
	
	/**
	 * Generates an array where it recursively iterates through all the directories within the given destination.
	 *
	 * @param location   base directory
	 * @param recursive  determines if the stream is required to be recursive or just the first directory
	 * @param predicates filters applied to the stream
	 * @return an array with all files with the given data configuration
	 * @throws IOException error if directory not exists or location is not a valid directory
	 * @see Predicate
	 */
	@SafeVarargs
	public static Path @NotNull [] walkDirArr(
		@NotNull Path location,
		boolean recursive,
		Predicate<Path> @NotNull ... predicates
	) throws IOException {
		try (Stream<Path> walk = walkDir(location, recursive, predicates)) {
			return walk.toArray(Path[]::new);
		}
	}
	
	/* -----------------------------------------------------
	 * Filesystem methods
	 * ----------------------------------------------------- */
	
	/**
	 * Returns a valid file system depending on the scheme of the given url
	 *
	 * @param url the url to inspect
	 * @return valid filesystem
	 * @throws IOException error if url schema is not supported
	 */
	public static @NotNull FileSystem getFilesystem(@NotNull URL url) throws IOException {
		return getValidFilesystem(url);
	}
	
	/**
	 * Returns a valid file system depending on the scheme of the given url
	 *
	 * @param uri the url to inspect
	 * @return valid filesystem
	 * @throws IOException error if url schema is not supported
	 */
	public static @NotNull FileSystem getFilesystem(@NotNull URI uri) throws IOException {
		return getValidFilesystem(uri);
	}
	
	/* -----------------------------------------------------
	 * Conversion methods
	 * ----------------------------------------------------- */
	
	/**
	 * Returns the path of an url in the file system (if the scheme is supported).
	 * <p>
	 * If the schema is a jar file, the virtual representation of the file as a directory will be returned.
	 *
	 * @param url the url to inspect
	 * @return a valid file path
	 * @throws IOException error if given url is not supported
	 */
	public static @NotNull Path pathOf(@NotNull URL url) throws IOException {
		return getValidPath(url);
	}
	
	/**
	 * Returns the path of an url in the file system (if the scheme is supported).
	 * <p>
	 * If the schema is a jar file, the virtual representation of the file as a directory will be returned.
	 *
	 * @param uri the url to inspect
	 * @return a valid file path
	 * @throws IOException error if given url is not supported
	 */
	public static @NotNull Path pathOf(@NotNull URI uri) throws IOException {
		return getValidPath(uri);
	}
	
}
