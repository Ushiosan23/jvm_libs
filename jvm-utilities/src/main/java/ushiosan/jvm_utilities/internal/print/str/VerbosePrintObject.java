package ushiosan.jvm_utilities.internal.print.str;

import org.jetbrains.annotations.NotNull;
import ushiosan.jvm_utilities.lang.Obj;

import java.util.Collection;
import java.util.Map;

import static ushiosan.jvm_utilities.lang.Obj.cast;

/**
 * Class that contains functionality for printing objects in detail
 */
public final class VerbosePrintObject extends BasePrintObject {
	
	/**
	 * Print object instance
	 */
	private static BasePrintObject INSTANCE;
	
	/**
	 * This class cannot be instantiated.
	 * <p>
	 * Singleton or utility class mode.
	 */
	private VerbosePrintObject() {
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
			INSTANCE = new VerbosePrintObject();
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
		Collection<?> collection = cast(obj, Collection.class);
		String clazzStr = toClassString(obj.getClass());
		StringBuilder builder = Obj.also(new StringBuilder(clazzStr), it -> it.append(" ["));
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
		return true;
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
		String clazzStr = toClassString(obj.getClass());
		StringBuilder builder = Obj.also(new StringBuilder(clazzStr), it -> it.append(" {"));
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
