package ushiosan.jvm.internal.filesystem;

import org.intellij.lang.annotations.MagicConstant;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;
import ushiosan.jvm.UNumber;
import ushiosan.jvm.UObject;
import ushiosan.jvm.collections.UArray;
import ushiosan.jvm.error.UCommonErrorMessages;
import ushiosan.jvm.filesystem.UResource;
import ushiosan.jvm.internal.validators.UResourceValidator;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;

import static ushiosan.jvm.UObject.isNotNull;

public abstract class UResourceImpl extends UResourceValidator {
	
	/* -----------------------------------------------------
	 * Path methods
	 * ----------------------------------------------------- */
	
	/**
	 * Generates a filter to capture the elements that meet said restriction
	 *
	 * @param pattern  regular expression to apply
	 * @param fullPath property used to parse the full path or just the file name for matches
	 * @param inverted option to perform the action inverted
	 * @param flags    regular expression configuration flags
	 * @param <T>      generic member type
	 * @return the filter instance with the desired behavior
	 * @see Pattern
	 */
	public static <T extends Path> @NotNull Predicate<T> regexPathOf(@NotNull @RegExp String pattern, boolean fullPath,
		boolean inverted, @MagicConstant(flagsFromClass = Pattern.class) int @NotNull ... flags) {
		return regexResourceOfImpl(pattern, fullPath, inverted, flags);
	}
	
	/**
	 * Generates a filter to capture the elements that meet said restriction
	 *
	 * @param pattern  regular expression to apply
	 * @param fullPath property used to parse the full path or just the file name for matches
	 * @param flags    regular expression configuration flags
	 * @param <T>      generic member type
	 * @return the filter instance with the desired behavior
	 * @see Pattern
	 */
	public static <T extends Path> @NotNull Predicate<T> regexPathOf(@NotNull @RegExp String pattern, boolean fullPath,
		@MagicConstant(flagsFromClass = Pattern.class) int @NotNull ... flags) {
		return regexPathOf(pattern, fullPath, false, flags);
	}
	
	/**
	 * Generates a filter to capture the elements that meet said restriction
	 *
	 * @param pattern  regular expression to apply
	 * @param fullPath property used to parse the full path or just the file name for matches
	 * @param inverted option to perform the action inverted
	 * @param <T>      generic member type
	 * @return the filter instance with the desired behavior
	 * @see Pattern
	 */
	public static <T extends Path> @NotNull Predicate<T> regexPathOf(@NotNull Pattern pattern, boolean fullPath,
		boolean inverted) {
		return regexResourceOfImpl(pattern, fullPath, inverted);
	}
	
	/**
	 * Generates a filter to capture the elements that meet said restriction
	 *
	 * @param names    the resource possible names
	 * @param inverted option to perform the action inverted
	 * @param <T>      generic member type
	 * @return the filter instance with the desired behavior
	 */
	public static <T extends Path> @NotNull Predicate<T> namedPathOf(boolean inverted, String @NotNull ... names) {
		return namedImpl(inverted, names);
	}
	
	/**
	 * Generates a filter to capture the elements that meet said restriction
	 *
	 * @param extensions the resource possible extensions
	 * @param inverted   option to perform the action inverted
	 * @param <T>        generic member type
	 * @return the filter instance with the desired behavior
	 */
	public static @NotNull <T extends Path> Predicate<T> extensionsPathOf(boolean inverted, String @NotNull ... extensions) {
		return extensionsImpl(inverted, extensions);
	}
	
	/* -----------------------------------------------------
	 * File methods
	 * ----------------------------------------------------- */
	
	/**
	 * Generates a filter to capture the elements that meet said restriction
	 *
	 * @param pattern  regular expression to apply
	 * @param fullPath property used to parse the full path or just the file name for matches
	 * @param inverted option to perform the action inverted
	 * @param flags    regular expression configuration flags
	 * @param <T>      generic member type
	 * @return the filter instance with the desired behavior
	 * @see Pattern
	 */
	public static <T extends File> @NotNull Predicate<T> regexFileOf(@NotNull @RegExp String pattern, boolean fullPath,
		boolean inverted, @MagicConstant(flagsFromClass = Pattern.class) int @NotNull ... flags) {
		return regexResourceOfImpl(pattern, fullPath, inverted, flags);
	}
	
	/**
	 * Generates a filter to capture the elements that meet said restriction
	 *
	 * @param pattern  regular expression to apply
	 * @param fullPath property used to parse the full path or just the file name for matches
	 * @param flags    regular expression configuration flags
	 * @param <T>      generic member type
	 * @return the filter instance with the desired behavior
	 * @see Pattern
	 */
	public static <T extends File> @NotNull Predicate<T> regexFileOf(@NotNull @RegExp String pattern, boolean fullPath,
		@MagicConstant(flagsFromClass = Pattern.class) int @NotNull ... flags) {
		return regexFileOf(pattern, fullPath, false, flags);
	}
	
	/**
	 * Generates a filter to capture the elements that meet said restriction
	 *
	 * @param pattern  regular expression to apply
	 * @param fullPath property used to parse the full path or just the file name for matches
	 * @param inverted option to perform the action inverted
	 * @param <T>      generic member type
	 * @return the filter instance with the desired behavior
	 * @see Pattern
	 */
	public static <T extends File> @NotNull Predicate<T> regexFileOf(@NotNull Pattern pattern, boolean fullPath,
		boolean inverted) {
		return regexResourceOfImpl(pattern, fullPath, inverted);
	}
	
	/**
	 * Generates a filter to capture the elements that meet said restriction
	 *
	 * @param names    the resource possible names
	 * @param inverted option to perform the action inverted
	 * @param <T>      generic member type
	 * @return the filter instance with the desired behavior
	 */
	public static <T extends File> @NotNull Predicate<T> namedFileOf(boolean inverted, String @NotNull ... names) {
		return namedImpl(inverted, names);
	}
	
	/**
	 * Generates a filter to capture the elements that meet said restriction
	 *
	 * @param extensions the resource possible extensions
	 * @param inverted   option to perform the action inverted
	 * @param <T>        generic member type
	 * @return the filter instance with the desired behavior
	 */
	public static @NotNull <T extends File> Predicate<T> extensionsFileOf(boolean inverted, String @NotNull ... extensions) {
		return namedImpl(inverted, extensions);
	}
	
	/* -----------------------------------------------------
	 * Zip entry methods
	 * ----------------------------------------------------- */
	
	/**
	 * Generates a filter to capture the elements that meet said restriction
	 *
	 * @param pattern  regular expression to apply
	 * @param fullPath property used to parse the full path or just the file name for matches
	 * @param inverted option to perform the action inverted
	 * @param flags    regular expression configuration flags
	 * @param <T>      generic member type
	 * @return the filter instance with the desired behavior
	 * @see Pattern
	 */
	public static <T extends ZipEntry> @NotNull Predicate<T> regexEntryOf(@NotNull @RegExp String pattern, boolean fullPath,
		boolean inverted, @MagicConstant(flagsFromClass = Pattern.class) int @NotNull ... flags) {
		return regexResourceOfImpl(pattern, fullPath, inverted, flags);
	}
	
	/**
	 * Generates a filter to capture the elements that meet said restriction
	 *
	 * @param pattern  regular expression to apply
	 * @param fullPath property used to parse the full path or just the file name for matches
	 * @param flags    regular expression configuration flags
	 * @param <T>      generic member type
	 * @return the filter instance with the desired behavior
	 * @see Pattern
	 */
	public static <T extends ZipEntry> @NotNull Predicate<T> regexEntryOf(@NotNull @RegExp String pattern, boolean fullPath,
		@MagicConstant(flagsFromClass = Pattern.class) int @NotNull ... flags) {
		return regexEntryOf(pattern, fullPath, false, flags);
	}
	
	/**
	 * Generates a filter to capture the elements that meet said restriction
	 *
	 * @param pattern  regular expression to apply
	 * @param fullPath property used to parse the full path or just the file name for matches
	 * @param inverted option to perform the action inverted
	 * @param <T>      generic member type
	 * @return the filter instance with the desired behavior
	 * @see Pattern
	 */
	public static <T extends ZipEntry> @NotNull Predicate<T> regexEntryOf(@NotNull Pattern pattern, boolean fullPath,
		boolean inverted) {
		return regexResourceOfImpl(pattern, fullPath, inverted);
	}
	
	/**
	 * Generates a filter to capture the elements that meet said restriction
	 *
	 * @param names    the resource possible names
	 * @param inverted option to perform the action inverted
	 * @param <T>      generic member type
	 * @return the filter instance with the desired behavior
	 */
	public static <T extends ZipEntry> @NotNull Predicate<T> namedEntryOf(boolean inverted, String @NotNull ... names) {
		return namedImpl(inverted, names);
	}
	
	/**
	 * Generates a filter to capture the elements that meet said restriction
	 *
	 * @param extensions the resource possible extensions
	 * @param inverted   option to perform the action inverted
	 * @param <T>        generic member type
	 * @return the filter instance with the desired behavior
	 */
	public static @NotNull <T extends ZipEntry> Predicate<T> extensionsEntryOf(boolean inverted,
		String @NotNull ... extensions) {
		return extensionsImpl(inverted, extensions);
	}
	
	/* -----------------------------------------------------
	 * Internal methods
	 * ----------------------------------------------------- */
	
	/**
	 * Generates a filter to capture the elements that meet said restriction
	 *
	 * @param pattern  regular expression to apply
	 * @param inverted option to perform the action inverted
	 * @param flags    regular expression configuration flags
	 * @param <T>      generic member type
	 * @return the filter instance with the desired behavior
	 * @see Pattern
	 */
	@SuppressWarnings("MagicConstant")
	private static <T> @NotNull Predicate<T> regexResourceOfImpl(@NotNull @RegExp String pattern, boolean fullPath,
		boolean inverted, @MagicConstant(flagsFromClass = Pattern.class) int @NotNull ... flags) {
		UObject.requireNotNull(pattern, "pattern");
		// Generate pattern instance
		Pattern patternInstance = Pattern.compile(pattern, UNumber.asFlags(flags));
		return regexResourceOfImpl(patternInstance, fullPath, inverted);
	}
	
	/**
	 * Generates a filter to capture the elements that meet said restriction
	 *
	 * @param pattern  regular expression to apply
	 * @param inverted option to perform the action inverted
	 * @param <T>      generic member type
	 * @return the filter instance with the desired behavior
	 * @see Pattern
	 */
	private static @NotNull <T> Predicate<T> regexResourceOfImpl(@NotNull Pattern pattern, boolean fullPath, boolean inverted) {
		UObject.requireNotNull(pattern, "pattern");
		return it -> {
			Matcher matcher = null;
			// Check the object class type
			if (UObject.canCastNotNull(it, Path.class)) {
				Path path = UObject.cast(it);
				matcher = pattern.matcher((fullPath ? path : path.getFileName()).toString());
			}
			if (UObject.canCastNotNull(it, File.class)) {
				File file = UObject.cast(it);
				matcher = pattern.matcher(fullPath ? file.getAbsolutePath() : file.getName());
			}
			if (UObject.canCastNotNull(it, ZipEntry.class)) {
				ZipEntry entry = UObject.cast(it);
				matcher = pattern.matcher(entry.getName());
			}
			
			return inverted != (isNotNull(matcher) && matcher.find());
		};
	}
	
	/**
	 * Generates a filter to capture the elements that meet said restriction
	 *
	 * @param names    the resource possible names
	 * @param inverted option to perform the action inverted
	 * @param <T>      generic member type
	 * @return the filter instance with the desired behavior
	 * @see Pattern
	 */
	private static @NotNull <T> Predicate<T> namedImpl(boolean inverted, String @NotNull ... names) {
		return it -> {
			String resourceName = null;
			// Check the object class type
			if (UObject.canCastNotNull(it, Path.class)) {
				Path path = UObject.cast(it);
				resourceName = UResource.resourceName(path);
			}
			if (UObject.canCastNotNull(it, File.class)) {
				File file = UObject.cast(it);
				resourceName = UResource.resourceName(file);
			}
			if (UObject.canCastNotNull(it, ZipEntry.class)) {
				ZipEntry entry = UObject.cast(it);
				resourceName = UResource.resourceName(entry);
			}
			
			return inverted != (isNotNull(resourceName) && UArray.contains(names, resourceName));
		};
	}
	
	/**
	 * Generates a filter to capture the elements that meet said restriction
	 *
	 * @param extensions the resource possible extensions
	 * @param inverted   option to perform the action inverted
	 * @param <T>        generic member type
	 * @return the filter instance with the desired behavior
	 * @see Pattern
	 */
	@SuppressWarnings("OptionalGetWithoutIsPresent")
	private static @NotNull <T> Predicate<T> extensionsImpl(boolean inverted, String @NotNull ... extensions) {
		return it -> {
			Optional<String> resourceExtension = Optional.empty();
			// Check the object class type
			if (UObject.canCastNotNull(it, Path.class)) {
				Path path = UObject.cast(it);
				resourceExtension = UResource.extension(path);
			}
			if (UObject.canCastNotNull(it, File.class)) {
				File file = UObject.cast(it);
				resourceExtension = UResource.extension(file);
			}
			if (UObject.canCastNotNull(it, ZipEntry.class)) {
				ZipEntry entry = UObject.cast(it);
				resourceExtension = UResource.extension(entry);
			}
			
			// The "isNotNull" method verifies that an "Optional" object is present,
			// but the IDE doesn't know about this, and for this reason you should put
			// the @SuppressWarnings("OptionalGetWithoutIsPresent") annotation.
			return inverted != (isNotNull(resourceExtension) && UArray.contains(extensions, resourceExtension.get()));
		};
	}
	
	/**
	 * Gets the file module within a {@link URI} object for a {@code jrt:/} filesystem
	 *
	 * @param uri the URI where the file is located
	 * @return the file module name or {@link Optional#empty()} if the module is not found.
	 */
	protected static @NotNull Optional<String> jrtModuleName(@NotNull URI uri) {
		String scheme = uri.getScheme();
		if (!scheme.equals("jrt")) {
			String messageError = UCommonErrorMessages.schemeNotSupportedError(scheme);
			throw new IllegalArgumentException(messageError);
		}
		
		// Explode uri
		String uriStr = uri.toString();
		String[] chunks = uriStr.split("/");
		
		// Extract the module name
		String moduleName = (chunks.length > 1 ? chunks[1] : "").trim();
		if (!moduleName.contains(".")) return Optional.empty();
		
		return moduleName.isBlank() ? Optional.empty() : Optional.of(moduleName);
	}
	
	/**
	 * Gets the file path within a {@link URI} object for a {@code jrt:/} filesystem
	 *
	 * @param uri the URI where the file is located
	 * @return the file path or empty text if the file is not found.
	 */
	protected static @NotNull String jrtFileAccess(@NotNull URI uri) {
		String scheme = uri.getScheme();
		if (!scheme.equals("jrt")) {
			String messageError = UCommonErrorMessages.schemeNotSupportedError(scheme);
			throw new IllegalArgumentException(messageError);
		}
		
		// Explode uri
		String uriStr = uri.toString();
		String[] chunks = uriStr.split("/");
		
		// Extract the module name
		String moduleName = chunks.length > 1 ? chunks[1] : "";
		int from = !moduleName.contains(".") ? 1 : 2;
		int to = chunks.length;
		
		String[] location = Arrays.copyOfRange(chunks, from, to);
		return String.join("/", location);
	}
	
}
