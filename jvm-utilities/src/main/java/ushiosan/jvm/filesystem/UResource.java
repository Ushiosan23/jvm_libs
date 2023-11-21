package ushiosan.jvm.filesystem;

import org.jetbrains.annotations.NotNull;
import ushiosan.jvm.UAction;
import ushiosan.jvm.ULogger;
import ushiosan.jvm.UObject;
import ushiosan.jvm.collections.UArray;
import ushiosan.jvm.error.UCommonErrorMessages;
import ushiosan.jvm.internal.collections.arrays.UArraysConstants;
import ushiosan.jvm.internal.filesystem.UResourceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public final class UResource extends UResourceImpl {
	
	/**
	 * Current instance logger
	 */
	private static final Logger LOG = Logger.getLogger(ULogger.loggerName(UResource.class));
	
	/* -----------------------------------------------------
	 * Internal properties
	 * ----------------------------------------------------- */
	/**
	 * Container that holds all the file systems created by the jar or jrt files virtually.
	 * These filesystems can be released with the {@link #disposeFilesystems()} method when they are no longer needed.
	 */
	private static final Set<FileSystem> cachedFileSystems = Collections.synchronizedSet(new HashSet<>());
	
	/**
	 * This class cannot be instantiated directly
	 */
	private UResource() {}
	
	/* -----------------------------------------------------
	 * Common resource locations
	 * ----------------------------------------------------- */
	
	/**
	 * Gets a specific path within the user's folder.
	 *
	 * @param items the relative user's folder location
	 * @return the resolved user relative path
	 */
	public static @NotNull Path userDirectory(String @NotNull ... items) {
		return Path.of(System.getProperty("user.home"), items);
	}
	
	/* -----------------------------------------------------
	 * Walk methods
	 * ----------------------------------------------------- */
	
	/**
	 * Generates a stream where it recursively iterates through all the directories within the given destination.
	 *
	 * @param path       base directory
	 * @param recursive  determines if the stream is required to be recursive or just the first directory
	 * @param predicates filters applied to the stream
	 * @return a stream with all the given settings
	 * @throws IOException error if directory not exists or location is not a valid directory
	 * @see Predicate
	 */
	@SafeVarargs
	public static @NotNull Stream<Path> resourceWalk(@NotNull Path path, boolean recursive,
		Predicate<Path> @NotNull ... predicates) throws IOException {
		return UAction.applyNotNull(recursive ? Files.walk(path) : Files.walk(path, 1), stream -> {
			for (var predicate : predicates) {
				stream = stream.filter(predicate);
			}
			// Get last stream result
			return stream;
		});
	}
	
	/* -----------------------------------------------------
	 * Resource name methods
	 * ----------------------------------------------------- */
	
	/**
	 * Gets the resource name without the slashes and the absolute path
	 *
	 * @param path the resource location to analyze
	 * @return the resource without the slashes
	 */
	public static @NotNull String resourceName(@NotNull Path path) {
		UObject.requireNotNull(path, "path");
		return nameWithoutSlashesImpl(path.getFileName().toString());
	}
	
	/**
	 * Gets the resource name without the slashes and the absolute path
	 *
	 * @param file the resource location to analyze
	 * @return the resource without the slashes
	 */
	public static @NotNull String resourceName(@NotNull File file) {
		UObject.requireNotNull(file, "file");
		return nameWithoutSlashesImpl(file.getAbsolutePath());
	}
	
	/**
	 * Gets the resource name without the slashes and the absolute path
	 *
	 * @param entry the resource location to analyze
	 * @return the resource without the slashes
	 */
	public static @NotNull String resourceName(@NotNull ZipEntry entry) {
		UObject.requireNotNull(entry, "entry");
		return nameWithoutSlashesImpl(entry.getName());
	}
	
	/* -----------------------------------------------------
	 * Basename methods
	 * ----------------------------------------------------- */
	
	/**
	 * Gets the resource basename, without any extension.
	 *
	 * @param path    the resource location to analyze
	 * @param partial returns the full or partial name
	 * @return the resource basename
	 */
	public static @NotNull String basename(@NotNull Path path, boolean partial) {
		UObject.requireNotNull(path, "path");
		return basenameImpl(path.getFileName().toString(),
							Files.isDirectory(path), partial);
	}
	
	/**
	 * Gets the resource basename, without any extension.
	 *
	 * @param path the resource location to analyze
	 * @return the resource basename
	 */
	public static @NotNull String basename(@NotNull Path path) {
		return basename(path, true);
	}
	
	/**
	 * Gets the resource basename, without any extension.
	 *
	 * @param file    the resource location to analyze
	 * @param partial returns the full or partial name
	 * @return the resource basename
	 */
	public static @NotNull String basename(@NotNull File file, boolean partial) {
		UObject.requireNotNull(file, "file");
		return basenameImpl(file.getName(),
							file.isDirectory(), partial);
	}
	
	/**
	 * Gets the resource basename, without any extension.
	 *
	 * @param file the resource location to analyze
	 * @return the resource basename
	 */
	public static @NotNull String basename(@NotNull File file) {
		return basename(file, true);
	}
	
	/**
	 * Gets the resource basename, without any extension.
	 *
	 * @param entry   the resource location to analyze
	 * @param partial returns the full or partial name
	 * @return the resource basename
	 */
	public static @NotNull String basename(@NotNull ZipEntry entry, boolean partial) {
		UObject.requireNotNull(entry, "entry");
		return basenameImpl(entry.getName(),
							entry.isDirectory(), partial);
	}
	
	/**
	 * Gets the resource basename, without any extension.
	 *
	 * @param entry the resource location to analyze
	 * @return the resource basename
	 */
	public static @NotNull String basename(@NotNull ZipEntry entry) {
		return basename(entry, true);
	}
	
	/* -----------------------------------------------------
	 * All extensions methods
	 * ----------------------------------------------------- */
	
	/**
	 * Gets all existing extensions in a resource location.
	 *
	 * @param path the resource location to analyze
	 * @return all resource extensions
	 * @throws IllegalArgumentException error if resource is an invalid
	 *                                  regular file
	 * @see Files#isRegularFile(Path, LinkOption...)
	 */
	public static String @NotNull [] allExtensions(@NotNull Path path) {
		UObject.requireNotNull(path, "path");
		// Check if resource is a directory
		if (Files.isDirectory(path)) {
			String errorMsg = UCommonErrorMessages.resourceTypeError("Regular File", "Directory");
			throw new IllegalArgumentException(errorMsg);
		}
		return allExtensionsImpl(path.getFileName().toString());
	}
	
	/**
	 * Gets all existing extensions in a resource location.
	 *
	 * @param file the resource location to analyze
	 * @return all resource extensions
	 * @throws IllegalArgumentException error if resource is an invalid
	 *                                  regular file
	 * @see File#isDirectory()
	 */
	public static String @NotNull [] allExtensions(@NotNull File file) {
		UObject.requireNotNull(file, "file");
		// Check if resource is a directory
		if (file.isDirectory()) {
			String errorMsg = UCommonErrorMessages.resourceTypeError("Regular File", "Directory");
			throw new IllegalArgumentException(errorMsg);
		}
		return allExtensionsImpl(file.getName());
	}
	
	/**
	 * Gets all existing extensions in a resource location.
	 *
	 * @param entry the resource location to analyze
	 * @return all resource extensions
	 * @throws IllegalArgumentException error if resource is an invalid
	 *                                  regular file
	 * @see ZipEntry#isDirectory()
	 */
	public static String @NotNull [] allExtensions(@NotNull ZipEntry entry) {
		UObject.requireNotNull(entry, "entry");
		// Check if resource is a directory
		if (entry.isDirectory()) {
			String errorMsg = UCommonErrorMessages.resourceTypeError("Regular File", "Directory");
			throw new IllegalArgumentException(errorMsg);
		}
		return allExtensionsImpl(entry.getName());
	}
	
	/* -----------------------------------------------------
	 * Extension methods
	 * ----------------------------------------------------- */
	
	/**
	 * Gets the resource extension.
	 *
	 * @param path the resource location to analyze
	 * @return returns the resource extension or {@link Optional#empty()} if the extension not exists
	 * @throws IllegalArgumentException error if resource is an invalid
	 *                                  regular file
	 * @see Files#isRegularFile(Path, LinkOption...)
	 */
	public static @NotNull Optional<String> extension(@NotNull Path path) {
		return extensionImpl(allExtensions(path));
	}
	
	/**
	 * Gets the resource extension.
	 *
	 * @param file the resource location to analyze
	 * @return returns the resource extension or {@link Optional#empty()} if the extension not exists
	 * @throws IllegalArgumentException error if resource is an invalid
	 *                                  regular file
	 * @see File#isDirectory()
	 */
	public static @NotNull Optional<String> extension(@NotNull File file) {
		return extensionImpl(allExtensions(file));
	}
	
	/**
	 * Gets the resource extension.
	 *
	 * @param entry the resource location to analyze
	 * @return returns the resource extension or {@link Optional#empty()} if the extension not exists
	 * @throws IllegalArgumentException error if resource is an invalid
	 *                                  regular file
	 * @see ZipEntry#isDirectory()
	 */
	public static @NotNull Optional<String> extension(@NotNull ZipEntry entry) {
		return extensionImpl(allExtensions(entry));
	}
	
	/* -----------------------------------------------------
	 * Hash methods
	 * ----------------------------------------------------- */
	
	/**
	 * Get the hash of the given stream
	 *
	 * @param stream    the content stream
	 * @param algorithm hash algorithm
	 * @return the stream hash
	 * @throws NoSuchAlgorithmException error if algorithm not exists
	 * @throws IOException              error to read stream content
	 */
	public static byte[] resourceHash(@NotNull InputStream stream, @NotNull String algorithm) throws IOException,
		NoSuchAlgorithmException {
		UObject.requireNotNull(stream, "stream");
		return resourceHashImpl(stream, algorithm);
	}
	
	/**
	 * Get the hash of the given stream
	 *
	 * @param stream the content stream
	 * @return the stream hash
	 * @throws NoSuchAlgorithmException error if algorithm not exists
	 * @throws IOException              error to read stream content
	 */
	public static byte[] resourceHash(@NotNull InputStream stream) throws IOException, NoSuchAlgorithmException {
		return resourceHash(stream, FS_DEFAULT_ALGORITHM);
	}
	
	/**
	 * Get the hash of the given resource
	 *
	 * @param path      the resource location
	 * @param algorithm hash algorithm
	 * @return the resource hash
	 * @throws NoSuchAlgorithmException error if algorithm not exists
	 * @throws IOException              error to read stream content
	 */
	public static byte[] resourceHash(@NotNull Path path, @NotNull String algorithm) throws IOException,
		NoSuchAlgorithmException {
		UObject.requireNotNull(path, "path");
		return resourceHashImpl(Files.newInputStream(path), algorithm);
	}
	
	/**
	 * Get the hash of the given resource
	 *
	 * @param path the resource location
	 * @return the resource hash
	 * @throws NoSuchAlgorithmException error if algorithm not exists
	 * @throws IOException              error to read stream content
	 */
	public static byte[] resourceHash(@NotNull Path path) throws IOException, NoSuchAlgorithmException {
		return resourceHash(path, FS_DEFAULT_ALGORITHM);
	}
	
	/**
	 * Get the hash of the given resource
	 *
	 * @param file      the resource location
	 * @param algorithm hash algorithm
	 * @return the resource hash
	 * @throws NoSuchAlgorithmException error if algorithm not exists
	 * @throws IOException              error to read stream content
	 */
	public static byte[] resourceHash(@NotNull File file, @NotNull String algorithm) throws IOException,
		NoSuchAlgorithmException {
		UObject.requireNotNull(file, "file");
		return resourceHashImpl(new FileInputStream(file), algorithm);
	}
	
	/**
	 * Get the hash of the given resource
	 *
	 * @param file the resource location
	 * @return the resource hash
	 * @throws NoSuchAlgorithmException error if algorithm not exists
	 * @throws IOException              error to read stream content
	 */
	public static byte[] resourceHash(@NotNull File file) throws IOException, NoSuchAlgorithmException {
		return resourceHash(file, FS_DEFAULT_ALGORITHM);
	}
	
	/**
	 * Get the hash of the given resource
	 *
	 * @param file      the root zip file
	 * @param entry     the resource location
	 * @param algorithm hash algorithm
	 * @return the resource hash
	 * @throws NoSuchAlgorithmException error if algorithm not exists
	 * @throws IOException              error to read stream content
	 */
	public static byte[] resourceHash(@NotNull ZipFile file, @NotNull ZipEntry entry, @NotNull String algorithm) throws
		IOException, NoSuchAlgorithmException {
		UObject.requireNotNull(file, "file");
		UObject.requireNotNull(entry, "entry");
		return resourceHashImpl(file.getInputStream(entry), algorithm);
	}
	
	/**
	 * Get the hash of the given resource
	 *
	 * @param file  the root zip file
	 * @param entry the resource location
	 * @return the resource hash
	 * @throws NoSuchAlgorithmException error if algorithm not exists
	 * @throws IOException              error to read stream content
	 */
	public static byte[] resourceHash(@NotNull ZipFile file, @NotNull ZipEntry entry) throws IOException,
		NoSuchAlgorithmException {
		return resourceHash(file, entry, FS_DEFAULT_ALGORITHM);
	}
	
	/* -----------------------------------------------------
	 * Hash string methods
	 * ----------------------------------------------------- */
	
	/**
	 * Get the hash of the given resource
	 * This method returns the hash as hexadecimal string.
	 *
	 * @param hash resource hash
	 * @return hash as hexadecimal string
	 */
	public static @NotNull String resourceHashStr(byte[] hash) {
		UObject.requireNotNull(hash, "hash");
		return Arrays.stream(UArray.toObjectArray(hash))
			.map(it -> String.format("%02x", it))
			.collect(Collectors.joining());
	}
	
	/**
	 * Get the hash of the given resource
	 * This method returns the hash as hexadecimal string.
	 *
	 * @param stream    the content stream
	 * @param algorithm hash algorithm
	 * @return hash as hexadecimal string
	 * @throws NoSuchAlgorithmException error if algorithm not exists
	 * @throws IOException              error to read stream content
	 */
	public static @NotNull String resourceHashStr(@NotNull InputStream stream, @NotNull String algorithm) throws IOException,
		NoSuchAlgorithmException {
		return resourceHashStr(resourceHash(stream, algorithm));
	}
	
	/**
	 * Get the hash of the given resource
	 * This method returns the hash as hexadecimal string.
	 *
	 * @param stream the content stream
	 * @return hash as hexadecimal string
	 * @throws NoSuchAlgorithmException error if algorithm not exists
	 * @throws IOException              error to read stream content
	 */
	public static @NotNull String resourceHashStr(@NotNull InputStream stream) throws IOException,
		NoSuchAlgorithmException {
		return resourceHashStr(resourceHash(stream));
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
	public static @NotNull FileSystem fileSystem(@NotNull URL url) throws IOException {
		try {
			return fileSystem(url.toURI());
		} catch (Exception e) {
			throw new IOException(e);
		}
	}
	
	/**
	 * Gets a valid file system depending on the scheme of the given url
	 *
	 * @param uri the url to inspect
	 * @return valid filesystem
	 * @throws IOException error if url schema is not supported
	 */
	public static @NotNull FileSystem fileSystem(@NotNull URI uri) throws IOException {
		String resourceScheme = uri.getScheme();
		
		switch (resourceScheme) {
			case "file":
				return Path.of(uri).getFileSystem();
			case "jar":
				FileSystem fs = FileSystems.newFileSystem(uri, Collections.emptyMap());
				// Check if filesystem is defined
				Optional<FileSystem> fsExists = cachedFileSystems.stream()
					.filter(fs::equals)
					.findFirst();
				
				// Close the newest filesystem
				if (fsExists.isPresent()) {
					fs.close();
					return fsExists.get();
				}
				
				cachedFileSystems.add(fs);
				return fs;
			default:
				String messageError = UCommonErrorMessages.schemeNotSupportedError(resourceScheme);
				throw new IOException(messageError);
		}
	}
	
	/**
	 * Releases connections to all virtual file systems created by the {@link #pathOf(URI)} method for jar or jrt files.
	 * <p>
	 * If you do not want to free all file systems then use the {@link #disposeFilesystemsIf(Function)} method.
	 */
	public static void disposeFilesystems() {
		disposeFilesystemsIf(it -> true);
	}
	
	/**
	 * Releases connections to all virtual file systems created by the {@link #pathOf(URI)} method for jar or jrt files.
	 *
	 * @param filter The action responsible for filtering the elements that must be deleted.
	 */
	public static void disposeFilesystemsIf(@NotNull Function<FileSystem, Boolean> filter) {
		// Generate iterator
		var iterator = cachedFileSystems.iterator();
		
		// We iterate all the elements in this way because it is easier to eliminate
		// elements at the same time as iterating.
		while (iterator.hasNext()) {
			FileSystem fs = iterator.next();
			
			if (filter.apply(fs)) {
				try {
					if (fs.isOpen()) fs.close();
				} catch (Exception e) {
					LOG.log(ULogger.logError(e));
				}
				iterator.remove();
			}
		}
	}
	
	/* -----------------------------------------------------
	 * Path methods
	 * ----------------------------------------------------- */
	
	/**
	 * Gets the path of an url in the file system (if the scheme is supported).
	 * <p>
	 * If the schema is a jar file, the virtual representation of the file as a directory will be returned.
	 *
	 * @param url the url to inspect
	 * @return a valid file path
	 * @throws IOException error if given url is not supported
	 */
	public static @NotNull Path pathOf(@NotNull URL url) throws IOException {
		try {
			return pathOf(url.toURI());
		} catch (Exception e) {
			throw new IOException(e);
		}
	}
	
	/**
	 * Gets the path of an uri in the file system (if the scheme is supported).
	 * <p>
	 * If the schema is a jar file, the virtual representation of the file as a directory will be returned.
	 * <p>
	 * <strong>Important</strong><p>
	 * For files within a {@code "jrt"} file system it is necessary to recreate the URI object for it to work correctly. If it is
	 * accessed from the {@link URI} object itself, the path structure would be as follows:
	 * <pre>{@code /jrt:/<module-name>/<file-path>}</pre>
	 * which is incorrect and the correct way would be the following:
	 * <pre>{@code /modules/<module-name>/<file-path>}</pre>
	 * I still don't know why the {@link Path} class does this, but the most reliable thing for now is to recreate
	 * the {@link URI} with the correct information. When I find a more efficient way, I'll include it in future updates.
	 *
	 * @param uri the url to inspect
	 * @return a valid file path
	 * @throws IOException error if given url is not supported
	 */
	@SuppressWarnings("resource")
	public static @NotNull Path pathOf(@NotNull URI uri) throws IOException {
		String resourceScheme = uri.getScheme();
		
		switch (resourceScheme) {
			case "file":
				return Path.of(uri);
			case "jar":
				FileSystem fs = fileSystem(uri);
				// Temporal variables
				String uriString = uri.toString();
				int idx = uriString.lastIndexOf('!');
				String resourceLocation = "/";
				
				// Check if the resource index exists
				if (idx != UArraysConstants.INDEX_NOT_FOUND) {
					resourceLocation = (idx + 1) >= uriString.length() ?
									   uriString.substring(idx) :
									   uriString.substring(idx + 1);
				}
				
				// Get resource root
				return fs.getPath(resourceLocation);
			case "jrt":
				// Generate jrt module access like
				// jrt:/<module-name>/<file-access>
				String uriResult = "jrt:/";
				// Extract the module name from uri
				Optional<String> moduleName = jrtModuleName(uri);
				String fileAccess = jrtFileAccess(uri);
				
				// Check module access
				if (moduleName.isPresent()) {
					uriResult += moduleName.get();
					if (!fileAccess.isEmpty() && !fileAccess.startsWith("/")) {
						uriResult += "/";
					}
				}
				
				// Check file access
				if (!fileAccess.isEmpty()) {
					uriResult += fileAccess;
				}
				
				URI uriObj = URI.create(uriResult);
				return Paths.get(uriObj);
			default:
				String messageError = UCommonErrorMessages.schemeNotSupportedError(resourceScheme);
				throw new IOException(messageError);
		}
	}
	
}