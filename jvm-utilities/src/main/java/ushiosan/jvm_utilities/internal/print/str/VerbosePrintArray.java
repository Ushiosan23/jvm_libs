package ushiosan.jvm_utilities.internal.print.str;

import org.jetbrains.annotations.NotNull;

public final class VerbosePrintArray extends BasePrintArray {

	/**
	 * Current class instance object
	 */
	private static BasePrintArray INSTANCE;

	/**
	 * Converts an array into a plain text representation of all its component elements.
	 *
	 * @param array the array to convert
	 * @return array string representation
	 */
	@Override
	protected @NotNull String arrayString(Object @NotNull [] array) {
		String clazzStr = BasePrintObject.getInstance(true)
			.toString(array.getClass());
		// Check if array is empty
		if (array.length == 0) return clazzStr + " []";
		int total = array.length;
		int index = 0;
		StringBuilder builder = new StringBuilder(clazzStr + " [");

		for (Object it : array) {
			builder.append(BasePrintObject.getInstance(true).toString(it));
			if (++index != total) builder.append(", ");
		}

		return builder.append("]").toString();
	}

	/**
	 * Get current class instance
	 *
	 * @return the instance of current class
	 */
	static BasePrintArray getInstance() {
		if (INSTANCE == null)
			INSTANCE = new VerbosePrintArray();
		return INSTANCE;
	}

}
