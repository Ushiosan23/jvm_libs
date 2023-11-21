package ushiosan.jvm.print;

import org.jetbrains.annotations.NotNull;
import ushiosan.jvm.internal.print.UToStringManagerImpl;

public interface UToStringComponent {
	
	/**
	 * The parent string manager
	 *
	 * @return the {@link UToStringManagerImpl} instance
	 */
	default @NotNull UToStringManager manager() {
		return UToStringManager.getInstance();
	}
	
	/**
	 * The method used to identify if the current instance
	 * only prints arrays of data.
	 *
	 * @return {@code true} if the instance only prints arrays of data or
	 *    {@code false} otherwise
	 */
	boolean arraysOnly();
	
	/**
	 * Gets the classes supported by that instance
	 *
	 * @return the supported classes
	 */
	Class<?>[] supportedElements();
	
	/**
	 * Generates a text with the representation of the object.
	 * Very similar to what the {@link Object#toString()} method does, but it ensures
	 * that all objects have an easily identifiable representation.
	 *
	 * @param object  the object that you want to get the text representation
	 * @param verbose option used to determine if the output will be long or simple
	 * @return object string representation
	 */
	@NotNull String toString(@NotNull Object object, boolean verbose);
	
}
