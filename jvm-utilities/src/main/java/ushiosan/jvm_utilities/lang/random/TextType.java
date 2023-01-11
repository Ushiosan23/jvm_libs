package ushiosan.jvm_utilities.lang.random;

import ushiosan.jvm_utilities.lang.collection.elements.Pair;

/**
 * Enumerated type that identifies what type of text is the one that will be generated randomly
 */
public enum TextType {
	
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
	public final Pair<Integer, Integer> range;
	
	/**
	 * Text type constructor
	 *
	 * @param start ASCII start range
	 * @param end   ASCII end range
	 */
	TextType(int start, int end) {
		range = Pair.of(start, end);
	}
	
}
