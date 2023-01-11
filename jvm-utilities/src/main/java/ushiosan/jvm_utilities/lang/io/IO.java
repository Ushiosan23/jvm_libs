package ushiosan.jvm_utilities.lang.io;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ushiosan.jvm_utilities.internal.io.IOImpl;
import ushiosan.jvm_utilities.lang.Obj;

import java.io.File;
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
import java.util.zip.ZipEntry;

import static ushiosan.jvm_utilities.lang.collection.Arrs.INDEX_NOT_FOUND;

/**
 * Class containing functionality for input and output elements.
 *
 * @see File
 * @see Path
 * @see ZipEntry
 * @see java.util.jar.JarEntry
 * @see java.util.jar.JarFile
 */
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
	 */
	private IO() {
	}
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Returns the location name
	 *
	 * @param location the location to inspect
	 * @return the name of the entry
	 */
	public static @NotNull String getFilename(@NotNull Path location) {
		return getNameWithoutSlashes(location.getFileName().toString());
	}
	
	/**
	 * Returns the file name
	 *
	 * @param file the file object
	 * @return the name of the entry
	 */
	public static @NotNull String getFilename(@NotNull File file) {
		return getNameWithoutSlashes(file.getAbsolutePath());
	}
	
	/**
	 * Returns the entry name of a zip file
	 *
	 * @param entry the entry file object
	 * @return the name of the entry
	 */
	public static @NotNull String getFilename(@NotNull ZipEntry entry) {
		return getNameWithoutSlashes(entry.getName());
	}
	
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
		return getBaseNameImpl(path.getFileName().toString(), Files.isDirectory(path));
	}
	
	/**
	 * Get the file base name, without any extension.
	 * <p>
	 * Example:
	 * <pre>{@code
	 * "build.gradle.kts" -> build
	 * "example.file.java" -> example
	 * }</pre>
	 *
	 * @param file the file to analyze
	 * @return the file base name
	 */
	public static @NotNull String getBaseName(@NotNull File file) {
		return getBaseNameImpl(file.getName(), file.isDirectory());
	}
	
	/**
	 * Get the file base name, without any extension.
	 * <p>
	 * Example:
	 * <pre>{@code
	 * "build.gradle.kts" -> build
	 * "example.file.java" -> example
	 * }</pre>
	 *
	 * @param entry the zip entry to analyze
	 * @return the entry base name
	 */
	public static @NotNull String getBaseName(@NotNull ZipEntry entry) {
		return getBaseNameImpl(entry.getName(), entry.isDirectory());
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
		// Returns result value
		return getAllExtensionsImpl(path.getFileName().toString());
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
	 * @param file the file to analyze
	 * @return all file extensions
	 */
	@Contract("_ -> new")
	public static String @NotNull [] getAllExtensions(@NotNull File file) {
		// Validate regular file
		if (file.isDirectory()) {
			throw new IllegalArgumentException(String.format(INVALID_FILE_TYPE, "Regular File", "Directory"));
		}
		// Returns result value
		return getAllExtensionsImpl(file.getName());
	}
	
	/**
	 * Returns all existing extensions in a zip entry object.
	 * <p>
	 * Example:
	 * <pre>{@code
	 * "build.gradle.kts" -> [gradle, kts]
	 * "example.file.java" -> [file, java]
	 * }</pre>
	 *
	 * @param entry the entry to analyze
	 * @return all file extensions
	 */
	@Contract("_ -> new")
	public static String @NotNull [] getAllExtensions(@NotNull ZipEntry entry) {
		// Validate regular file
		if (entry.isDirectory()) {
			throw new IllegalArgumentException(String.format(INVALID_FILE_TYPE, "Regular File", "Directory"));
		}
		// Returns result value
		return getAllExtensionsImpl(entry.getName());
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
		return getExtensionImpl(getAllExtensions(path));
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
	 * @param file the file to analyze
	 * @return returns the file extension or {@link Optional#empty()} if the extension not exists
	 */
	public static @NotNull Optional<String> getExtension(@NotNull File file) {
		return getExtensionImpl(getAllExtensions(file));
	}
	
	/**
	 * Returns the zip entry extension.
	 * <p>
	 * Example:
	 * <pre>{@code
	 * "build.gradle.kts" -> Optional["kts"]
	 * "example.file.java" -> Optional["java"]
	 * "binary_file" -> Optional[empty]
	 * }</pre>
	 *
	 * @param entry the zip entry to analyze
	 * @return returns the file extension or {@link Optional#empty()} if the extension not exists
	 */
	public static @NotNull Optional<String> getExtension(@NotNull ZipEntry entry) {
		return getExtensionImpl(getAllExtensions(entry));
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
	
	/**
	 * Returns the file extension
	 *
	 * @param file the path to analyze
	 * @return returns the file extension or empty string if the extension not exists
	 */
	public static @NotNull String getExtensionUnsafe(@NotNull File file) {
		return getExtension(file).orElse("");
	}
	
	/**
	 * Returns the zip entry extension
	 *
	 * @param entry the path to analyze
	 * @return returns the zip entry extension or empty string if the extension not exists
	 */
	public static @NotNull String getExtensionUnsafe(@NotNull ZipEntry entry) {
		return getExtension(entry).orElse("");
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
	
	/* ---------------------------------------------------------
	 * Internal methods
	 * --------------------------------------------------------- */
	
	/**
	 * Returns the document without the slashes and the absolute path
	 *
	 * @param location the file location to analyze
	 * @return the file without the slashes
	 */
	static @NotNull String getNameWithoutSlashes(@NotNull CharSequence location) {
		String realLocation = location.toString()
			.replaceAll("[/|\\\\]+", "/")
			.trim();
		// Only get the last element
		if (realLocation.endsWith("/")) {
			realLocation = realLocation.substring(0, realLocation.length() - 1);
		}
		
		int index = realLocation.lastIndexOf("/");
		return realLocation.substring(index == INDEX_NOT_FOUND ? 0 : index + 1);
	}
	
	/**
	 * Get the file base name, without any extension.
	 * <p>
	 * Example:
	 * <pre>{@code
	 * "build.gradle.kts" -> build
	 * "example.file.java" -> example
	 * }</pre>
	 *
	 * @param location the file location to analyze
	 * @return the file base name
	 */
	static @NotNull String getBaseNameImpl(@NotNull CharSequence location, boolean isDir) {
		String pathStr = getNameWithoutSlashes(location.toString().trim());
		// The directories object don't have extensions
		if (isDir) return pathStr;
		
		// Resolve regular file name
		int extIndex = pathStr.indexOf(EXTENSION_IDENTIFIER);
		if (extIndex != INDEX_NOT_FOUND) {
			pathStr = pathStr.substring(0, extIndex);
		}
		return pathStr;
	}
	
	/**
	 * Returns all existing extensions in a location.
	 * <p>
	 * Example:
	 * <pre>{@code
	 * "build.gradle.kts" -> [gradle, kts]
	 * "example.file.java" -> [file, java]
	 * }</pre>
	 *
	 * @param location the file location to analyze
	 * @return all file extensions
	 */
	static String @NotNull [] getAllExtensionsImpl(@NotNull CharSequence location) {
		// Temporal variables
		String baseName = getBaseNameImpl(location, false);
		String pathStr = getNameWithoutSlashes(location);
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
	 * Returns the location extension.
	 * <p>
	 * Example:
	 * <pre>{@code
	 * "build.gradle.kts" -> Optional["kts"]
	 * "example.file.java" -> Optional["java"]
	 * "binary_file" -> Optional[empty]
	 * }</pre>
	 *
	 * @param extensions all location extensions
	 * @return returns the file extension or {@link Optional#empty()} if the extension not exists
	 */
	static @NotNull Optional<String> getExtensionImpl(String @NotNull [] extensions) {
		if (extensions.length == 0) return Optional.empty();
		// Get only the last extension, because it is the extension
		// that defines the file format
		int lastIndex = extensions.length - 1;
		return Optional.of(extensions[lastIndex]);
	}
	
}
