package ushiosan.jvm.internal.validators;

import org.jetbrains.annotations.NotNull;
import ushiosan.jvm.collections.UArray;

import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Optional;

import static ushiosan.jvm.UObject.requireNotNull;
import static ushiosan.jvm.internal.collections.arrays.UArraysConstants.INDEX_NOT_FOUND;
import static ushiosan.jvm.platform.UArchitecture.X64;
import static ushiosan.jvm.platform.UArchitecture.platformRunningArch;

public abstract class UResourceValidator {
	
	/* -----------------------------------------------------
	 * Properties
	 * ----------------------------------------------------- */
	
	/**
	 * Empty extensions array
	 */
	protected static final String[] FS_EMPTY_EXTENSIONS = new String[0];
	
	/**
	 * Character used to identify all standard file separators
	 */
	protected static final char FS_STANDARD_FILE_SEPARATOR = '/';
	
	/**
	 * Character used to identify all file extensions
	 */
	protected static final char FS_EXTENSION_IDENTIFIER = '.';
	
	/**
	 * Standard size for data buffers
	 */
	protected static final int FS_RESOURCE_BUFFER_SIZE_STANDARD = 4096;
	
	/**
	 * Hash Algorithm for {@link #resourceHashImpl(InputStream, String)} Function.
	 * It must be remembered that 64-bit platforms work better with 64-bit algorithms
	 * such as SHA-512 and 32-bit platforms with SHA-256, but it is possible to use both
	 * on any platform, it is only done this way for performance reasons.
	 */
	protected static final String FS_DEFAULT_ALGORITHM = (platformRunningArch() == X64) ?
														 "SHA-512" : "SHA-256";
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Gets the document without the slashes and the absolute path
	 *
	 * @param location the file location to analyze
	 * @return the file without the slashes
	 */
	protected static @NotNull String nameWithoutSlashesImpl(@NotNull String location) {
		requireNotNull(location, "location");
		String fileSeparator = String.valueOf(FS_STANDARD_FILE_SEPARATOR);
		String cleanLocation = location.replaceAll("[/|\\\\]+", fileSeparator)
			.trim();
		
		// We check that the location does not end with a slash
		while (cleanLocation.endsWith(fileSeparator)) {
			cleanLocation = cleanLocation.substring(0, cleanLocation.length() - 1);
		}
		
		int lastSlashIndex = cleanLocation.lastIndexOf(FS_STANDARD_FILE_SEPARATOR);
		return cleanLocation.substring(lastSlashIndex == INDEX_NOT_FOUND ?
									   0 : lastSlashIndex + 1);
	}
	
	/**
	 * Gets the file base name, without any extension.
	 * <p>
	 * Example:
	 * <pre>{@code
	 * "build.gradle.kts" -> build | build.gradle
	 * "example.file.java" -> example | example.file
	 * }</pre>
	 *
	 * @param location  the file location to analyze
	 * @param directory determines if the resource is a directory
	 * @param partial   returns the full or partial name
	 * @return the file base name
	 */
	protected static @NotNull String basenameImpl(@NotNull String location, boolean directory, boolean partial) {
		String cleanLocation = nameWithoutSlashesImpl(location);
		// Directories or the like do not contain extensions.
		if (directory) return cleanLocation;
		
		// Clean regular file name
		int dotIndex = partial ? cleanLocation.indexOf(FS_EXTENSION_IDENTIFIER) :
					   cleanLocation.lastIndexOf(FS_EXTENSION_IDENTIFIER);
		return dotIndex == INDEX_NOT_FOUND ? cleanLocation :
			   cleanLocation.substring(0, dotIndex);
	}
	
	/**
	 * Gets all existing extensions in a location.
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
	protected static String @NotNull [] allExtensionsImpl(@NotNull String location) {
		// Temporal variables
		String basename = basenameImpl(location, false, false);
		String cleanLocation = nameWithoutSlashesImpl(location);
		int dotIndex = cleanLocation.indexOf(FS_EXTENSION_IDENTIFIER);
		
		check:
		{
			// Check if FS_EXTENSION_IDENTIFIER exists
			if (dotIndex == INDEX_NOT_FOUND) break check;
			
			// Generate result
			String[] extensionChunks = cleanLocation.split("\\.");
			return Arrays.stream(extensionChunks)
				.filter(it -> !basename.contentEquals(it))
				.toArray(String[]::new);
		}
		return FS_EMPTY_EXTENSIONS;
	}
	
	/**
	 * Gets the resource extension.
	 * <p>
	 * Example:
	 * <pre>{@code
	 * "build.gradle.kts" -> Optional["kts"]
	 * "example.file.java" -> Optional["java"]
	 * "binary_file" -> Optional[empty]
	 * }</pre>
	 *
	 * @param extensions all extensions extensions
	 * @return returns the file extension or {@link Optional#empty()} if the extension not exists
	 */
	protected static @NotNull Optional<String> extensionImpl(String @NotNull [] extensions) {
		return UArray.lastElement(extensions);
	}
	
	/**
	 * Get the hash of the given stream
	 *
	 * @param stream    the content stream
	 * @param algorithm hash algorithm
	 * @return the stream hash
	 * @throws NoSuchAlgorithmException error if algorithm not exists
	 * @throws IOException              error to read stream content
	 */
	@SuppressWarnings("StatementWithEmptyBody")
	protected static byte[] resourceHashImpl(@NotNull InputStream stream, @NotNull String algorithm) throws IOException,
		NoSuchAlgorithmException {
		requireNotNull(algorithm, "algorithm");
		// We use automatic closing of Java resources
		try (stream) {
			// Generate digest instance
			MessageDigest digest = MessageDigest.getInstance(algorithm);
			DigestInputStream digestStream = new DigestInputStream(stream, digest);
			byte[] digestBuffer = new byte[FS_RESOURCE_BUFFER_SIZE_STANDARD];
			
			// Generate hash file
			while (digestStream.read(digestBuffer) != INDEX_NOT_FOUND) {
				// Do nothing, because "digestStream" already does all the work automatically
			}
			return digest.digest();
		}
	}
	
}
