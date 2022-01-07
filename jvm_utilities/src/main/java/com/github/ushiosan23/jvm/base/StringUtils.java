package com.github.ushiosan23.jvm.base;

import org.jetbrains.annotations.NotNull;

public final class StringUtils {

	/* ------------------------------------------------------------------
	 * Constructor
	 * ------------------------------------------------------------------ */

	/**
	 * This class cannot be instantiated
	 */
	private StringUtils() {
	}

	/* ------------------------------------------------------------------
	 * Methods
	 * ------------------------------------------------------------------ */

	/**
	 * Generates a text with a capital letter at the beginning.
	 *
	 * @param sequence      Text to transform
	 * @param capitalizeAll It does the same but at the beginning of each word
	 * @return Returns a text with a capital letter
	 */
	public static @NotNull String capitalize(@NotNull CharSequence sequence, boolean capitalizeAll) {
		// Check sequence size
		if (sequence.length() == 0) return "";
		// Split string in chunks
		StringBuilder builder = new StringBuilder();
		CharSequence[] chunks = sequence.toString()
			.trim()
			.split("\\s");
		// Generate result
		CharSequence firstElement = chunks[0];
		char firstLetter = Character.toUpperCase(firstElement.charAt(0));
		// Append only first element
		chunks[0] = Character.toString(firstLetter) + firstElement.subSequence(1, firstElement.length());
		// Capitalize all elements
		if (capitalizeAll && chunks.length > 1) {
			for (int i = 1; i < chunks.length; i++) {
				CharSequence chunkSequence = chunks[i];
				char chunkFirsLetter = Character.toUpperCase(chunkSequence.charAt(0));
				chunks[i] = Character.toString(chunkFirsLetter) + chunkSequence.subSequence(1, chunkSequence.length());
			}
		}

		builder.append(String.join(" ", chunks));
		return builder.toString();
	}

	/**
	 * Generates a text with a capital letter at the beginning.
	 *
	 * @param sequence Text to transform
	 * @return Returns a text with a capital letter
	 */
	public static @NotNull String capitalize(@NotNull CharSequence sequence) {
		return capitalize(sequence, false);
	}

}
