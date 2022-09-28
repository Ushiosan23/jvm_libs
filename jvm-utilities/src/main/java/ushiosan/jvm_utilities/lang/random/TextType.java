package ushiosan.jvm_utilities.lang.random;

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
	 * ASCII range start
	 */
	public final int start;

	/**
	 * ASCII range end
	 */
	public final int end;

	/**
	 * Text type constructor
	 *
	 * @param start ASCII start
	 * @param end   ASCII end
	 */
	TextType(int start, int end) {
		this.start = start;
		this.end = end;
	}

}
