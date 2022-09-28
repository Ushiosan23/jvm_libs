package ushiosan.jvm_utilities.lang.random;

public enum TextType {
	LETTERS(65, 123),
	NUMERIC(48, 58),
	ALPHANUMERIC(48, 123),
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
