package ushiosan.jvm.internal.print.components;

import org.jetbrains.annotations.NotNull;
import ushiosan.jvm.UObject;
import ushiosan.jvm.internal.collections.arrays.primitives.UArrayPrimitive;
import ushiosan.jvm.print.UToStringComponent;

public class UArrayComponent extends UArrayPrimitive implements UToStringComponent {
	
	/**
	 * Singleton instance
	 */
	private static UToStringComponent INSTANCE;
	
	/* -----------------------------------------------------
	 * Properties
	 * ----------------------------------------------------- */
	
	/**
	 * This class cannot be instantiated directly
	 */
	private UArrayComponent() {}
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Generates an instance of the specified class and saves it so that only one
	 * instance exists throughout the entire program.
	 * If the JVM deletes such an instance, it is recreated.
	 *
	 * @return the object instance
	 */
	public static @NotNull UToStringComponent getInstance() {
		if (UObject.isNull(INSTANCE)) {
			INSTANCE = new UArrayComponent();
		}
		return INSTANCE;
	}
	
	/**
	 * The method used to identify if the current instance
	 * only prints arrays of data.
	 *
	 * @return {@code true} if the instance only prints arrays of data or
	 *    {@code false} otherwise
	 */
	@Override
	public boolean arraysOnly() {
		return true;
	}
	
	/**
	 * Gets the classes supported by that instance
	 *
	 * @return the supported classes
	 */
	@Override
	public Class<?>[] supportedElements() {
		return null;
	}
	
	/* -----------------------------------------------------
	 * Static methods
	 * ----------------------------------------------------- */
	
	/**
	 * Generates a text with the representation of the object.
	 * Very similar to what the {@link Object#toString()} method does, but it ensures
	 * that all objects have an easily identifiable representation.
	 *
	 * @param object  the object that you want to get the text representation
	 * @param verbose option used to determine if the output will be long or simple
	 * @return object string representation
	 */
	@Override
	public @NotNull String toString(@NotNull Object object, boolean verbose) {
		// Temporal variables
		StringBuilder builder = new StringBuilder();
		Class<?> cls = object.getClass();
		Object[] array = toObjectArrayImpl(object);
		
		// Base information
		if (verbose) {
			String clsInfo = manager().toString(cls, true);
			final int index = clsInfo.indexOf('[') + 1;
			
			builder.append(clsInfo, 0, index)
				.append(array.length)
				.append(clsInfo.substring(index));
		}
		builder.append('[');
		
		// Builder content
		for (int i = 0; i < array.length; i++) {
			Object item = array[i];
			builder.append(manager().toString(item, verbose));
			
			// We add the separator for each element
			if (i < (array.length - 1)) builder.append(", ");
		}
		return builder.append(']').toString();
	}
	
}
