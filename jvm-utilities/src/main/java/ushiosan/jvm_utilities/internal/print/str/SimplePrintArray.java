package ushiosan.jvm_utilities.internal.print.str;

import org.jetbrains.annotations.NotNull;

public final class SimplePrintArray extends BasePrintArray {
	
	/**
	 * Current class instance object
	 */
	private static BasePrintArray INSTANCE;
	
	/**
	 * Get current class instance
	 *
	 * @return the instance of current class
	 */
	static BasePrintArray getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SimplePrintArray();
		}
		return INSTANCE;
	}
	
	/**
	 * Converts an array into a plain text representation of all its component elements.
	 *
	 * @param array the array to convert
	 * @return array string representation
	 */
	@Override
	protected @NotNull String arrayString(Object @NotNull [] array) {
		// Check if array is empty
		if (array.length == 0) return "[]";
		StringBuilder builder = new StringBuilder("[");
		int total = array.length;
		int index = 0;
		
		for (Object it : array) {
			builder.append(BasePrintObject.getInstance(false).toString(it));
			if (++index != total) builder.append(", ");
		}
		
		return builder.append("]").toString();
	}
	
}
