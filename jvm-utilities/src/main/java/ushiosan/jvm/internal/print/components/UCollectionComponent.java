package ushiosan.jvm.internal.print.components;

import org.jetbrains.annotations.NotNull;
import ushiosan.jvm.UObject;
import ushiosan.jvm.collections.UArray;
import ushiosan.jvm.function.UFun;
import ushiosan.jvm.print.UToStringComponent;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import static ushiosan.jvm.UObject.cast;

public final class UCollectionComponent implements UToStringComponent {
	
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
	private final Class<?>[] SUPPORTED_CLASSES = UArray.of(
		Map.class,
		Collection.class);
	/**
	 * Reference of the methods that are called in each case.
	 * The order of the methods must be the same as the order of the supported classes.
	 *
	 * @see #SUPPORTED_CLASSES
	 */
	private final UFun.UFun2<String, Object, Boolean>[] CONVERSION_REFERENCE = UArray.of(
		this::toMapString,
		this::toCollectionString);
	
	/**
	 * This class cannot be instantiated directly
	 */
	private UCollectionComponent() {}
	
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
			INSTANCE = new UCollectionComponent();
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
	 * Internal methods
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
		// Iterate all elements
		for (int i = 0; i < SUPPORTED_CLASSES.length; i++) {
			if (UObject.canCast(object, SUPPORTED_CLASSES[i])) {
				return CONVERSION_REFERENCE[i].invoke(object, verbose);
			}
		}
		// Only for compile purposes
		return manager().toString(object, verbose);
	}
	
	/**
	 * Gets the text representation of {@link Map} types
	 *
	 * @param object  the object that you want to get the text representation
	 * @param verbose option used to determine if the output will be long or simple
	 * @return object string representation
	 */
	private @NotNull String toMapString(@NotNull Object object, boolean verbose) {
		// Generate string builder and temporal variables
		StringBuilder builder = new StringBuilder();
		Map<?, ?> objectMap = cast(object);
		Class<?> cls = objectMap.getClass();
		int counter = 0;
		
		// Base information
		if (verbose) builder.append(manager().toString(cls, true));
		builder.append('{');
		
		// Generate content string
		for (var entry : objectMap.entrySet()) {
			builder.append(manager().toString(entry, verbose));
			
			// Separate elements
			if (++counter < objectMap.size()) {
				builder.append(", ");
			}
		}
		
		return builder.append('}')
			.toString();
	}
	
	/* -----------------------------------------------------
	 * Static methods
	 * ----------------------------------------------------- */
	
	/**
	 * Gets the text representation of {@link Collection} types
	 *
	 * @param object  the object that you want to get the text representation
	 * @param verbose option used to determine if the output will be long or simple
	 * @return object string representation
	 */
	private @NotNull String toCollectionString(@NotNull Object object, boolean verbose) {
		// Generate string builder and temporal variables
		StringBuilder builder = new StringBuilder();
		Collection<?> objectCollection = cast(object);
		Class<?> cls = objectCollection.getClass();
		Iterator<?> iterator = objectCollection.iterator();
		
		// Base information
		if (verbose) builder.append(manager().toString(cls, true));
		builder.append('[');
		
		// Generate content string
		while (iterator.hasNext()) {
			var element = iterator.next();
			builder.append(manager().toString(element, verbose));
			
			// Separate elements
			if (iterator.hasNext()) builder.append(", ");
		}
		
		return builder.append(']')
			.toString();
	}
	
}
