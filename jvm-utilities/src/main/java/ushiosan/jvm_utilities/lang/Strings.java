package ushiosan.jvm_utilities.lang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ushiosan.jvm_utilities.function.Apply;
import ushiosan.jvm_utilities.lang.collection.Arrs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ushiosan.jvm_utilities.lang.Obj.applyNotNull;
import static ushiosan.jvm_utilities.lang.Obj.isNull;

/**
 * Class with utilities for handling text strings
 */
public final class Strings {
	
	/**
	 * Pattern used to count start spaces
	 */
	private static final Pattern SPACES_COUNTER = Pattern.compile("^(\\s+)");
	
	/**
	 * This class cannot be instantiated.
	 * <p>
	 * Singleton or utility class mode.
	 */
	private Strings() {
	}
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Check if the string is valid. This method verifies that the text is not null or that the
	 * content is an empty string.
	 *
	 * @param content the content you want to verify
	 * @param action  the action that is executed if the content is valid
	 */
	public static void validate(@Nullable String content, Apply.@NotNull Empty<String> action) {
		if (isNull(content) || content.isBlank()) return;
		action.apply(content.trim());
	}
	
	/**
	 * Check if the string is valid. This method verifies that the text is not null or that the
	 * content is an empty string.
	 *
	 * @param content the content you want to verify
	 * @param action  the action that is executed if the content is valid
	 */
	public static void validateC(@Nullable CharSequence content, Apply.@NotNull Empty<CharSequence> action) {
		validate(applyNotNull(content, CharSequence::toString).orElse(null), action::apply);
	}
	
	/**
	 * Transforms the text string and converts each word start to an uppercase letter
	 *
	 * @param content the content to convert
	 * @param all     option used to convert all words or only the first word of
	 *                the entire content
	 * @return the converted content
	 */
	public static @NotNull String capitalize(@NotNull CharSequence content, boolean all) {
		String realContent = content.toString();
		String[] chunks = all ? realContent.split("\\s") : Arrs.of(realContent);
		// Convert all elements
		String[] result = java.util.Arrays.stream(chunks)
			.map(Strings::capitalizeImpl)
			.toArray(String[]::new);
		return String.join(" ", result);
	}
	
	/**
	 * Transforms the text string and converts each word start to an uppercase letter
	 *
	 * @param content the content to convert
	 * @return the converted content
	 */
	public static @NotNull String capitalize(@NotNull CharSequence content) {
		return capitalize(content, true);
	}
	
	/**
	 * Change the first letter of the word to a capital letter.
	 * <p>
	 * This method cleans the content of spaces and only converts to a word and
	 * not a complete content.
	 *
	 * @param content the content to convert
	 * @return the converted content
	 */
	public static @NotNull String capitalizeWord(@NotNull CharSequence content) {
		return simpleCapitalize(content);
	}
	
	/* -----------------------------------------------------
	 * Internal methods
	 * ----------------------------------------------------- */
	
	/**
	 * Change the first letter of the word to a capital letter.
	 * <p>
	 * This method cleans the content of spaces and only converts to a word and
	 * not a complete content.
	 *
	 * @param sequence the content to convert
	 * @return the converted content
	 */
	private static @NotNull String simpleCapitalize(@NotNull CharSequence sequence) {
		String contentStr = sequence.toString().trim();
		if (contentStr.isEmpty()) return "";
		// Temporal variables
		StringBuilder builder = new StringBuilder();
		char[] wordArr = contentStr.toCharArray();
		
		// Insert elements
		builder.append(Character.toUpperCase(wordArr[0]));
		builder.append(contentStr.substring(1));
		
		return builder.toString();
	}
	
	/**
	 * Transforms the text string and converts each word start to an uppercase letter
	 *
	 * @param sequence the content to convert
	 * @return the converted content
	 */
	private static @NotNull String capitalizeImpl(@NotNull CharSequence sequence) {
		// Check if sequence is empty
		if (sequence.toString().isEmpty()) return "";
		// Temporal variables
		StringBuilder builder = new StringBuilder();
		String contentStr = sequence.toString();
		Matcher matcher = SPACES_COUNTER.matcher(contentStr);
		int spaces = 0;
		
		// Count all spaces
		if (matcher.find()) {
			spaces = matcher.group().length();
		}
		
		// Insert the content
		builder.append(" ".repeat(spaces));
		builder.append(simpleCapitalize(contentStr.substring(spaces)));
		return builder.toString();
	}
	
}
