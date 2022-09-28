package ushiosan.jvm_utilities.internal.print.str;

import static ushiosan.jvm_utilities.lang.Obj.cast;

import org.jetbrains.annotations.NotNull;

import ushiosan.jvm_utilities.lang.Cls;
import ushiosan.jvm_utilities.lang.collection.Arrs;

public abstract class BasePrintArray {

	/**
	 * This class cannot be instantiated.
	 * <p>
	 * Singleton or utility class mode.
	 */
	protected BasePrintArray() {
	}

	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */

	/**
	 * Array string representation
	 *
	 * @param obj the array to convert
	 * @return array string representation
	 */
	public @NotNull String toString(@NotNull Object obj) {
		// One trick used to avoid having to check the data type
		// is to convert the primitive types to object arrays
		// and use them as a generic object array.
		Object[] arrayObj = Cls.isPrimitiveArray(obj) ?
			Arrs.toObjectArray(obj):cast(obj);
		return arrayString(cast(arrayObj));
	}

	/**
	 * Converts an array into a plain text representation of all its component elements.
	 *
	 * @param array the array to convert
	 * @return array string representation
	 */
	protected abstract @NotNull String arrayString(Object @NotNull [] array);

	/**
	 * Get current class instance
	 *
	 * @param parent parent printer instance
	 * @return the instance of current class
	 */
	static BasePrintArray getInstance(@NotNull BasePrintObject parent) {
		if (parent instanceof VerbosePrintObject)
			return VerbosePrintArray.getInstance();
		return SimplePrintArray.getInstance();
	}

}
