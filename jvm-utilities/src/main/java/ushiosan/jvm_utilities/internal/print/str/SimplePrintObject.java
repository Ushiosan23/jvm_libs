package ushiosan.jvm_utilities.internal.print.str;

import static ushiosan.jvm_utilities.lang.Obj.cast;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;

public final class SimplePrintObject extends BasePrintObject {

	/**
	 * Print object instance
	 */
	private static BasePrintObject INSTANCE;

	/**
	 * This class cannot be instantiated.
	 * <p>
	 * Singleton or utility class mode.
	 */
	private SimplePrintObject() {
		super();
	}

	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */

	/**
	 * Collection string representation
	 *
	 * @param obj the object to convert
	 * @return an object string representation
	 */
	@Override
	protected @NotNull String toCollectionString(@NotNull Object obj) {
		// Temporal variables
		Collection<?> collection = cast(obj);
		StringBuilder builder = new StringBuilder("[");
		// Iteration variables
		int index = 0;
		int total = collection.size();

		for (Object it : collection) {
			builder.append(toString(it));
			if (++index != total) builder.append(", ");
		}
		return builder.append("]").toString();
	}

	/**
	 * Map string representation
	 *
	 * @param obj the object to convert
	 * @return an object string representation
	 */
	@Override
	protected @NotNull String toMapString(@NotNull Object obj) {
		// Temporal variables
		Map<?, ?> mapObj = cast(obj, Map.class);
		StringBuilder builder = new StringBuilder("{");
		// Iteration variables
		int index = 0;
		int total = mapObj.size();

		for (Map.Entry<?, ?> entry : mapObj.entrySet()) {
			builder.append(toString(entry));
			if (++index != total) builder.append(", ");
		}
		return builder.append("}").toString();
	}

	/* -----------------------------------------------------
	 * Static methods
	 * ----------------------------------------------------- */

	/**
	 * Get current class instance
	 *
	 * @return the instance of current class
	 */
	static BasePrintObject getInstance() {
		if (INSTANCE == null)
			INSTANCE = new SimplePrintObject();
		return INSTANCE;
	}

}
