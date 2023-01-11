package ushiosan.jvm_utilities.internal.print.str;

import org.jetbrains.annotations.NotNull;
import ushiosan.jvm_utilities.lang.Cls;
import ushiosan.jvm_utilities.lang.collection.Arrs;

import static ushiosan.jvm_utilities.lang.Obj.cast;

/**
 * Class that contains all the basic functionality for printing arrays
 */
public abstract class BasePrintArray {
	
	/**
	 * This class cannot be instantiated.
	 */
	protected BasePrintArray() {}
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Get current class instance
	 *
	 * @param parent parent printer instance
	 * @return the instance of current class
	 */
	static BasePrintArray getInstance(@NotNull BasePrintObject parent) {
		if (parent instanceof VerbosePrintObject) {
			return VerbosePrintArray.getInstance();
		}
		return SimplePrintArray.getInstance();
	}
	
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
							Arrs.toUnsafeObjectArray(obj) : cast(obj);
		return arrayString(cast(arrayObj));
	}
	
	/**
	 * Converts an array into a plain text representation of all its component elements.
	 *
	 * @param array the array to convert
	 * @return array string representation
	 */
	protected abstract @NotNull String arrayString(Object @NotNull [] array);
	
}
