package ushiosan.jvm_utilities.function.predicate;

import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;
import ushiosan.jvm_utilities.lang.io.IO;

import java.io.File;
import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;

import static ushiosan.jvm_utilities.lang.Obj.canCast;
import static ushiosan.jvm_utilities.lang.Obj.cast;
import static ushiosan.jvm_utilities.lang.Obj.isAnyTypeOf;

/**
 * Interface used as a data filter to search for files by regular expression
 *
 * @param <T> the type of data you want to filter
 * @see File
 * @see Path
 * @see ZipEntry
 */
public interface RegexPredicate<T> extends Predicate<T> {
	
	/**
	 * Generate instance of {@link RegexPredicate}
	 *
	 * @param fullPath determines whether to inspect the full path or just the filename
	 * @param regex    regular expression to check each element
	 * @param <T>      predicate object type
	 * @return a configured instance of {@link RegexPredicate}
	 */
	static <T> @NotNull Predicate<T> of(boolean fullPath, @NotNull @RegExp String regex) {
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
	static <T> @NotNull Predicate<T> of(@NotNull @RegExp String regex) {
		return of(true, regex);
	}
	
	/**
	 * Get predicate pattern
	 *
	 * @return compiled regex predicate
	 */
	Pattern getPattern();
	
	/* -----------------------------------------------------
	 * Static methods
	 * ----------------------------------------------------- */
	
	/**
	 * Determines whether to inspect the full path or just the filename
	 *
	 * @return {@code true} if the full path is inspected or {@code false} if
	 * 	only the file name is inspected
	 */
	default boolean isFullPathInspect() {
		return true;
	}
	
	/**
	 * Evaluates this predicate on the given argument.
	 *
	 * @param path the input argument
	 * @return {@code true} if the input argument matches the predicate,
	 * 	otherwise {@code false}
	 */
	@Override
	default boolean test(@NotNull T path) {
		// Check types
		if (!isAnyTypeOf(path, Path.class, File.class, ZipEntry.class)) {
			throw new IllegalArgumentException("Invalid argument type");
		}
		
		if (canCast(path, Path.class)) {
			return testPath(cast(path));
		}
		if (canCast(path, File.class)) {
			return testFile(cast(path));
		}
		if (canCast(path, ZipEntry.class)) {
			return testZipEntry(cast(path));
		}
		
		return false;
	}
	
	/**
	 * Evaluates this predicate on the given argument.
	 *
	 * @param path the input argument
	 * @return {@code true} if the input argument matches the predicate,
	 * 	otherwise {@code false}
	 */
	default boolean testPath(@NotNull Path path) {
		Path realPath = isFullPathInspect() ? path : path.getFileName();
		Matcher matcher = getPattern().matcher(realPath.toString());
		
		return matcher.find();
	}
	
	/**
	 * Evaluates this predicate on the given argument.
	 *
	 * @param file the input argument
	 * @return {@code true} if the input argument matches the predicate,
	 * 	otherwise {@code false}
	 */
	default boolean testFile(@NotNull File file) {
		String path = isFullPathInspect() ? file.getAbsolutePath() : file.getName();
		Matcher matcher = getPattern().matcher(path);
		
		return matcher.find();
	}
	
	/**
	 * Evaluates this predicate on the given argument.
	 *
	 * @param entry the input argument
	 * @return {@code true} if the input argument matches the predicate,
	 * 	otherwise {@code false}
	 */
	default boolean testZipEntry(@NotNull ZipEntry entry) {
		String path = isFullPathInspect() ? entry.getName() :
					  IO.getFilename(entry);
		Matcher matcher = getPattern().matcher(path);
		
		return matcher.find();
	}
	
}
