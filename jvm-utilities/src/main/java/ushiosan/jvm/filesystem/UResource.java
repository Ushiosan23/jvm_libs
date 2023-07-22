package ushiosan.jvm.filesystem;

import org.jetbrains.annotations.NotNull;
import ushiosan.jvm.collections.UArray;
import ushiosan.jvm.internal.filesystem.UResourceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.*;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static ushiosan.jvm.UAction.applyNotNull;
import static ushiosan.jvm.UObject.requireNotNull;
import static ushiosan.jvm.error.UCommonErrorMessages.resourceTypeError;
import static ushiosan.jvm.error.UCommonErrorMessages.schemeNotSupportedError;

public final class UResource extends UResourceImpl {
	
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
		return applyNotNull(recursive ? Files.walk(path) : Files.walk(path, 1), stream -> {
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
		requireNotNull(path, "path");
		return nameWithoutSlashesImpl(path.getFileName().toString());
	}
	
	/**
	 * Gets the resource name without the slashes and the absolute path
	 *
	 * @param file the resource location to analyze
	 * @return the resource without the slashes
	 */
	public static @NotNull String resourceName(@NotNull File file) {
		requireNotNull(file, "file");
		return nameWithoutSlashesImpl(file.getAbsolutePath());
	}
	
	/**
	 * Gets the resource name without the slashes and the absolute path
	 *
	 * @param entry the resource location to analyze
	 * @return the resource without the slashes
	 */
	public static @NotNull String resourceName(@NotNull ZipEntry entry) {
		requireNotNull(entry, "entry");
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
		requireNotNull(path, "path");
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
		requireNotNull(file, "file");
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
		requireNotNull(entry, "entry");
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
		requireNotNull(path, "path");
		// Check if resource is a directory
		if (Files.isDirectory(path)) {
			throw new IllegalArgumentException(resourceTypeError(
				"Regular File", "Directory"));
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
		requireNotNull(file, "file");
		// Check if resource is a directory
		if (file.isDirectory()) {
			throw new IllegalArgumentException(resourceTypeError(
				"Regular File", "Directory"));
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
		requireNotNull(entry, "entry");
		// Check if resource is a directory
		if (entry.isDirectory()) {
			throw new IllegalArgumentException(resourceTypeError(
				"Regular File", "Directory"));
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
		requireNotNull(stream, "stream");
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
		requireNotNull(path, "path");
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
		requireNotNull(file, "file");
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
		requireNotNull(file, "file");
		requireNotNull(entry, "entry");
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
		requireNotNull(hash, "hash");
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
				return FileSystems.newFileSystem(uri, new HashMap<>(0));
			default:
				throw new IOException(schemeNotSupportedError(resourceScheme));
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
	 *
	 * @param uri the url to inspect
	 * @return a valid file path
	 * @throws IOException error if given url is not supported
	 */
	public static @NotNull Path pathOf(@NotNull URI uri) throws IOException {
		String resourceScheme = uri.getScheme();
		switch (resourceScheme) {
			case "file":
				return Path.of(uri);
			case "jar":
				try (var fs = fileSystem(uri)) {
					return fs.getPath("/");
				}
			default:
				throw new IOException(schemeNotSupportedError(resourceScheme));
		}
	}
	
}