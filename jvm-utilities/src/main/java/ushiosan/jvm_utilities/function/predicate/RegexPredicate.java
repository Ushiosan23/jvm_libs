package ushiosan.jvm_utilities.function.predicate;

import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface RegexPredicate<T extends Path> extends Predicate<T> {

	/**
	 * Get predicate pattern
	 *
	 * @return compiled regex predicate
	 */
	Pattern getPattern();

	/**
	 * Determines whether to inspect the full path or just the filename
	 *
	 * @return {@code true} if the full path is inspected or {@code false} if
	 * only the file name is inspected
	 */
	default boolean isFullPathInspect() {
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
		Path realPath = isFullPathInspect() ? path:path.getFileName();
		Matcher matcher = getPattern().matcher(realPath.toString());

		return matcher.find();
	}

	/* -----------------------------------------------------
	 * Static methods
	 * ----------------------------------------------------- */

	/**
	 * Generate instance of {@link RegexPredicate}
	 *
	 * @param fullPath determines whether to inspect the full path or just the filename
	 * @param regex    regular expression to check each element
	 * @param <T>      predicate object type
	 * @return a configured instance of {@link RegexPredicate}
	 */
	static <T extends Path> @NotNull Predicate<T> of(boolean fullPath, @NotNull @RegExp String regex) {
		final Pattern pattern = Pattern.compile(regex);
		return new RegexPredicate<T>() {

			@Override
			public boolean isFullPathInspect() {
				return fullPath;
			}

			@Override
			public Pattern getPattern() {
				return pattern;
			}
		};
	}

	/**
	 * Generate instance of {@link RegexPredicate}
	 *
	 * @param regex regular expression to check each element
	 * @param <T>   predicate object type
	 * @return a configured instance of {@link RegexPredicate}
	 */
	static <T extends Path> @NotNull Predicate<T> of(@NotNull @RegExp String regex) {
		return of(true, regex);
	}

}
