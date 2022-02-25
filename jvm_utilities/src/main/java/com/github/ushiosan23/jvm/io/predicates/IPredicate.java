package com.github.ushiosan23.jvm.io.predicates;

import com.github.ushiosan23.jvm.io.IOUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface IPredicate {

	/* ------------------------------------------------------------------
	 * Extra interfaces
	 * ------------------------------------------------------------------ */

	interface IExtensionPredicate extends Predicate<Path> {

		/**
		 * Get all predicate extensions
		 *
		 * @return All predicate extensions
		 */
		String[] getExtensions();

		/**
		 * Accept directories
		 *
		 * @return Returns the status
		 */
		default boolean acceptDirectories() {
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
		default boolean test(Path path) {
			// By default directories are valid
			if (Files.isDirectory(path))
				return acceptDirectories();
			// Generate list from extensions
			List<String> extensions = List.of(getExtensions());
			String extension = IOUtils.getExtension(path);
			// Check result
			return extensions.contains(extension);
		}

	}

	interface IRegexPredicate extends Predicate<Path> {

		/**
		 * Get predicate pattern
		 *
		 * @return Regex predicate
		 */
		Pattern getPattern();

		/**
		 * Accept only file names
		 *
		 * @return Return current status
		 */
		default boolean onlyFileNames() {
			return false;
		}

		/**
		 * Evaluates this predicate on the given argument.
		 *
		 * @param path the input argument
		 * @return {@code true} if the input argument matches the predicate,
		 * otherwise {@code false}
		 */
		@Override
		default boolean test(@NotNull Path path) {
			Path testPath = onlyFileNames() ? path.getFileName() : path;
			Matcher matcher = getPattern().matcher(testPath.toString());

			return matcher.find();
		}

	}

	/* ------------------------------------------------------------------
	 * Methods
	 * ------------------------------------------------------------------ */

	/**
	 * Generate new extension predicate
	 *
	 * @param acceptDirectories Check if directories are valid
	 * @param extensions        All valid extensions to evaluate
	 * @return Returns a new predicate object
	 */
	@Contract(pure = true)
	static @NotNull Predicate<Path> extensionsOf(boolean acceptDirectories, String @NotNull ... extensions) {
		return new IExtensionPredicate() {

			@Override
			public boolean acceptDirectories() {
				return acceptDirectories;
			}

			@Override
			public String[] getExtensions() {
				return extensions;
			}
		};
	}

	/**
	 * Generate new extension predicate
	 *
	 * @param extensions All valid extensions to evaluate
	 * @return Returns a new predicate object
	 */
	static @NotNull Predicate<Path> extensionsOf(String @NotNull ... extensions) {
		return extensionsOf(true, extensions);
	}

	/**
	 * Generate new regex predicate
	 *
	 * @param regex    Regular expression
	 * @param onlyName Only checks the file name
	 * @return Returns a new predicate object
	 */
	static @NotNull Predicate<Path> regexOf(@NotNull String regex, boolean onlyName) {
		return new IRegexPredicate() {

			@Override
			public boolean onlyFileNames() {
				return onlyName;
			}

			@Override
			public Pattern getPattern() {
				return Pattern.compile(regex);
			}

		};
	}

	/**
	 * Generate new regex predicate
	 *
	 * @param regex Regular expression
	 * @return Returns a new predicate object
	 */
	static @NotNull Predicate<Path> regexOf(@NotNull String regex) {
		return regexOf(regex, false);
	}

}
