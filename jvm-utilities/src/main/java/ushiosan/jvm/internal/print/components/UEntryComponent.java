package ushiosan.jvm.internal.print.components;

import org.jetbrains.annotations.NotNull;
import ushiosan.jvm.UObject;
import ushiosan.jvm.collections.UArrays;
import ushiosan.jvm.print.UToStringComponent;

import java.util.Map;

import static ushiosan.jvm.UObject.cast;

public final class UEntryComponent implements UToStringComponent {
	
	/**
	 * Singleton instance
	 */
	private static UToStringComponent INSTANCE;
	
	/* -----------------------------------------------------
	 * Properties
	 * ----------------------------------------------------- */
	/**
	 * All elements supported by the instance
	 */
	private final Class<?>[] SUPPORTED_CLASSES = UArrays.of(
		Map.Entry.class);
	
	/**
	 * This class cannot be instantiated directly
	 */
	private UEntryComponent() {}
	
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
			INSTANCE = new UEntryComponent();
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
		return false;
	}
	
	/**
	 * Gets the classes supported by that instance
	 *
	 * @return the supported classes
	 */
	@Override
	public Class<?>[] supportedElements() {
		return SUPPORTED_CLASSES;
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
		// Cast element
		Map.Entry<?, ?> entry = cast(object);
		return String.format("%s = %s",
							 manager().toString(entry.getKey(), verbose),
							 manager().toString(entry.getValue(), verbose));
	}
	
}
