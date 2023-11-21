package ushiosan.jvm;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import ushiosan.jvm.collections.UArray;
import ushiosan.jvm.content.UPair;

import java.util.Arrays;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class UString {
	
	/**
	 * Pattern used to count start spaces
	 */
	private static final Pattern SPACE_INSPECTOR = Pattern.compile("^(\\s+)");
	
	/* -----------------------------------------------------
	 * Properties
	 * ----------------------------------------------------- */
	
	/**
	 * This class cannot be instantiated directly
	 */
	private UString() {}
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Transforms the text string and converts each word start to an uppercase letter
	 *
	 * @param content    the content to convert
	 * @param allContent option used to convert all words or only the first word of
	 *                   the entire content
	 * @return capitalize content string
	 */
	public static @NotNull @Unmodifiable CharSequence capitalize(@NotNull CharSequence content, boolean allContent) {
		UObject.requireNotNull(content, "content");
		// Temporal variables
		String contentString = content.toString();
		String[] contentChunks = allContent ? contentString.split("\\s") :
								 UArray.make(contentString);
		
		// Try to convert all elements
		String[] convertedChunks = Arrays.stream(contentChunks)
			.map(UString::capitalizeImpl)
			.toArray(String[]::new);
		return String.join(" ", convertedChunks);
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
	public static @NotNull @Unmodifiable CharSequence capitalizeWord(@NotNull CharSequence content) {
		return capitalize(content, false);
	}
	
	/**
	 * Generate a pseudo random text string, depending on the selected setting
	 *
	 * @param random        an instance of random type to generate the content
	 * @param size          size of characters that the string will have
	 * @param generatorType the type of configuration used
	 * @param ignore        all the characters you want to ignore within the result
	 * @return a pseudo random text string
	 * @see UGeneratorType
	 */
	public static @NotNull @Unmodifiable CharSequence randomSequence(@NotNull Random random, int size,
		@NotNull UGeneratorType generatorType, char... ignore) {
		UObject.requireNotNull(random, "random");
		UObject.requireNotNull(generatorType, "generatorType");
		// Generate result
		return random.ints(generatorType.range.first, generatorType.range.second)
			// Filter invalid ASCII characters
			.filter(c -> (c <= 57 || c >= 65) && (c <= 90 || c >= 97))
			// Filter selected characters to be ignored
			.filter(c -> !UArray.primitiveContains(ignore, (char) c))
			.limit(size)
			.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			.toString();
	}
	
	/**
	 * Generate a pseudo random text string, depending on the selected setting
	 *
	 * @param random an instance of random type to generate the content
	 * @param size   size of characters that the string will have
	 * @param ignore all the characters you want to ignore within the result
	 * @return a pseudo random text string
	 * @see #randomSequence(Random, int, UGeneratorType, char...)
	 * @see UGeneratorType
	 */
	public static @NotNull @Unmodifiable CharSequence randomSequence(@NotNull Random random, int size, char... ignore) {
		return randomSequence(random, size, UGeneratorType.ALL_SYMBOLS, ignore);
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
	 * @param content the word to convert
	 * @return capitalize word string
	 */
	private static @NotNull String capitalizeWordImpl(@NotNull String content) {
		// Check if content is empty
		if (content.isBlank()) return content;
		// Temporal variables
		StringBuilder builder = new StringBuilder();
		char[] contentCharArr = content.toCharArray();
		
		// Generate result
		return builder
			.append(Character.toUpperCase(contentCharArr[0]))
			.append(content.substring(1))
			.toString();
	}
	
	/**
	 * Transforms the text string and converts each word start to an uppercase letter
	 *
	 * @param content the content to convert
	 * @return capitalize content string
	 */
	private static @NotNull String capitalizeImpl(@NotNull String content) {
		// Check if content is empty
		if (content.isBlank()) return content;
		// Temporal variables
		int indexSpaces = 0;
		CharSequence spaces = "";
		StringBuilder builder = new StringBuilder();
		Matcher matcher = SPACE_INSPECTOR.matcher(content);
		
		// Check if content has any space
		if (matcher.find()) {
			spaces = matcher.group();
			indexSpaces = spaces.length();
		}
		
		// Generate result
		return builder.append(spaces)
			.append(capitalizeWordImpl(content.substring(indexSpaces)))
			.toString();
	}
	
	/* -----------------------------------------------------
	 * Internal types
	 * ----------------------------------------------------- */
	
	public enum UGeneratorType {
		/**
		 * Element that identifies only letter type characters and excludes numbers and special characters.
		 */
		LETTERS(65, 123),
		
		/**
		 * Element that identifies only characters of type number
		 */
		NUMERIC(48, 58),
		
		/**
		 * Element that identifies letter and number type characters
		 */
		ALPHANUMERIC(48, 123),
		
		/**
		 * Element that identifies any character within the ASCII standard
		 */
		ALL_SYMBOLS(33, 126);
		
		/**
		 * ASCII start and end range
		 */
		public final UPair<Integer, Integer> range;
		
		/**
		 * Text type constructor
		 *
		 * @param start ASCII start range
		 * @param end   ASCII end range
		 */
		UGeneratorType(int start, int end) {
			range = UPair.make(start, end);
		}
	}
	
}
