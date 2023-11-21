package ushiosan.jvm.print;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ushiosan.jvm.internal.print.UToStringManagerImpl;

public interface UToStringManager {
	
	/**
	 * Generates an instance of the specified class and saves it so that only one
	 * instance exists throughout the entire program. If the JVM deletes such an
	 * instance, it is recreated.
	 *
	 * @return the object instance
	 */
	static @NotNull UToStringManager getInstance() {
		return UToStringManagerImpl.getInstance();
	}
	
	/**
	 * Generates a text with the representation of the object.
	 * Very similar to what the {@link Object#toString()} method does, but it ensures
	 * that all objects have an easily identifiable representation.
	 *
	 * @param object  the object that you want to get the text representation
	 * @param verbose option used to determine if the output will be long or simple
	 * @return object string representation
	 */
	@NotNull String toString(@Nullable Object object, boolean verbose);
	
	/**
	 * Generates a text with the representation of the object.
	 * Very similar to what the {@link Object#toString()} method does, but it ensures
	 * that all objects have an easily identifiable representation.
	 *
	 * @param object the object that you want to get the text representation
	 * @return object string representation
	 */
	default @NotNull String toString(@Nullable Object object) {
		return toString(object, false);
	}
	
	/**
	 * registers a new component to the handler instance
	 *
	 * @param component the new component to register
	 */
	void registerComponent(@NotNull UToStringComponent component);
	
	/**
	 * register multiple components to the handler instance
	 *
	 * @param components the new components to register
	 * @see #registerComponent(UToStringComponent)
	 */
	default void registerComponents(UToStringComponent @NotNull ... components) {
		for (var component : components) {
			registerComponent(component);
		}
	}
	
	/* -----------------------------------------------------
	 * Static methods
	 * ----------------------------------------------------- */
	
	/**
	 * Removes a component from the component list
	 *
	 * @param cls The class of the component you want to remove
	 */
	void removeComponent(@NotNull Class<? extends UToStringComponent> cls);
	
}
