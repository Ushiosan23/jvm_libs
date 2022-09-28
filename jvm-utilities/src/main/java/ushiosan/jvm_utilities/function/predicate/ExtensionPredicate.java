package ushiosan.jvm_utilities.function.predicate;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import ushiosan.jvm_utilities.lang.collection.Arrays;
import ushiosan.jvm_utilities.lang.io.IO;

public interface ExtensionPredicate<T extends Path> extends Predicate<T> {

	/**
	 * Get current predicate accepted extensions
	 *
	 * @return an array with all predicate extensions
	 */
	String @NotNull [] getExtensions();

	/**
	 * Determines whether the predicate also scans directories
	 *
	 * @return directory scan status
	 */
	default boolean accepDirectoryObjects() {
		// By default, the result is true
		return true;
	}

	/**
	 * Evaluates this predicate on the given argument.
	 *
	 * @param path the input argument
	 * @return {@code true} if the input argument matches the predicate,
	 * otherwise {@code false}
	 */
	@Override
	default boolean test(@NotNull T path) {
		// If acceptDirectoryObjects is true, all directories are valid
		if (Files.isDirectory(path)) return accepDirectoryObjects();
		// Temporal variables
		String[] extensions = getExtensions();
		Optional<String> extension = IO.getExtension(path);
		// Check if the extension exists
		if (extension.isEmpty()) return false;
		return Arrays.contains(extensions, extension);
	}

	/* -----------------------------------------------------
	 * Static methods
	 * ----------------------------------------------------- */

	/**
	 * Generate instance of {@link ExtensionPredicate}
	 *
	 * @param acceptDirs determines whether the predicate also scans directories
	 * @param extensions all accepted extensions without "." dot character
	 * @param <T>        predicate object type
	 * @return a configured instance of {@link ExtensionPredicate}
	 */
	static <T extends Path> @NotNull Predicate<T> of(boolean acceptDirs, String @NotNull ... extensions) {
		return new ExtensionPredicate<>() {

			@Override
			public boolean accepDirectoryObjects() {
				return acceptDirs;
			}

			@Override
			public String @NotNull [] getExtensions() {
				return extensions;
			}

		};
	}

	/**
	 * Generate instance of {@link ExtensionPredicate}
	 *
	 * @param extensions all accepted extensions without "." dot character
	 * @param <T>        predicate object type
	 * @return a configured instance of {@link ExtensionPredicate}
	 */
	static <T extends Path> @NotNull Predicate<T> of(String @NotNull ... extensions) {
		return of(true, extensions);
	}

}
