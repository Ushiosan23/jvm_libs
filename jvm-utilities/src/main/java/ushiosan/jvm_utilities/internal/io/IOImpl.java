package ushiosan.jvm_utilities.internal.io;

import org.jetbrains.annotations.NotNull;
import ushiosan.jvm_utilities.lang.collection.Collections;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * Base class containing functionality for input and output elements
 */
public abstract class IOImpl {
	
	/**
	 * Filesystem path root
	 */
	public static final String FILESYSTEM_ROOT = "/";
	
	/**
	 * This class cannot be instantiated.
	 * <p>
	 * Singleton or utility class mode.
	 */
	protected IOImpl() {
	}
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Returns a valid file system depending on the scheme of the given url
	 *
	 * @param url the url to inspect
	 * @return valid filesystem
	 * @throws IOException error if url schema is not supported
	 */
	protected static @NotNull FileSystem getValidFilesystem(@NotNull URL url) throws IOException {
		try {
			return getValidFilesystem(url.toURI());
		} catch (Exception e) {
			throw new IOException(e);
		}
	}
	
	/**
	 * Returns a valid file system depending on the scheme of the given url
	 *
	 * @param uri the url to inspect
	 * @return valid filesystem
	 * @throws IOException error if url schema is not supported
	 */
	protected static @NotNull FileSystem getValidFilesystem(@NotNull URI uri) throws IOException {
		switch (uri.getScheme()) {
			case "file":
				return Path.of(uri).getFileSystem();
			case "jar":
				return FileSystems.newFileSystem(uri, Collections.mapOf());
			default:
				throw new IOException(String.format("Scheme \"%s\" is not supported.", uri.getScheme()));
		}
	}
	
	/**
	 * Returns the path of an url in the file system (if the scheme is supported).
	 * <p>
	 * If the schema is a jar file, the virtual representation of the file as a directory will be returned.
	 *
	 * @param url the url to inspect
	 * @return a valid file path
	 * @throws IOException error if given url is not supported
	 */
	protected static @NotNull Path getValidPath(@NotNull URL url) throws IOException {
		try {
			return getValidPath(url.toURI());
		} catch (Exception e) {
			throw new IOException(e);
		}
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
	protected static @NotNull Path getValidPath(@NotNull URI uri) throws IOException {
		switch (uri.getScheme()) {
			case "file":
				return Path.of(uri);
			case "jar":
				FileSystem fileSystem = getValidFilesystem(uri);
				return fileSystem.getPath(FILESYSTEM_ROOT);
			default:
				throw new IOException(String.format("Scheme \"%s\" is not supported.", uri.getScheme()));
		}
	}
	
}
