package ushiosan.jvm_utilities.internal.print.str;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;

import static ushiosan.jvm_utilities.lang.Obj.cast;

/**
 * Class with functionalities for printing objects dynamically
 */
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
	 * Get current class instance
	 *
	 * @return the instance of current class
	 */
	static BasePrintObject getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SimplePrintObject();
		}
		return INSTANCE;
	}
	
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
	
	/* -----------------------------------------------------
	 * Static methods
	 * ----------------------------------------------------- */
	
	/**
	 * check if an object is verbose or not
	 *
	 * @return verbose status
	 */
	@Override
	protected boolean isVerbose() {
		return false;
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
	
}
