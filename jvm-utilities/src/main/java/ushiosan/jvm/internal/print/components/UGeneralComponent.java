package ushiosan.jvm.internal.print.components;

import org.jetbrains.annotations.NotNull;
import ushiosan.jvm.UObject;
import ushiosan.jvm.collections.UArray;
import ushiosan.jvm.error.URecursiveCallException;
import ushiosan.jvm.function.UFun;
import ushiosan.jvm.print.UToStringComponent;
import ushiosan.jvm.reflection.UReflectionActions;
import ushiosan.jvm.reflection.UReflectionOptions;

import java.lang.reflect.Method;

public final class UGeneralComponent implements UToStringComponent {
	
	/**
	 * Name of the method to call the objects
	 */
	private static final String TO_STRING_METHOD = "toString";
	
	/* -----------------------------------------------------
	 * Properties
	 * ----------------------------------------------------- */
	
	/**
	 * Singleton instance
	 */
	private static UToStringComponent INSTANCE;
	
	/**
	 * All elements supported by the instance
	 */
	private final Class<?>[] SUPPORTED_CLASSES = UArray.make(
		CharSequence.class,
		Class.class,
		Object.class);
	
	/**
	 * Reference of the methods that are called in each case.
	 * The order of the methods must be the same as the order of the supported classes.
	 *
	 * @see #SUPPORTED_CLASSES
	 */
	private final UFun.UFun2<String, Object, Boolean>[] CONVERSION_REFERENCE = UArray.make(
		this::toStringCharSequence,
		this::toStringClass,
		this::toStringObject);
	
	/**
	 * This class cannot be instantiated directly
	 */
	private UGeneralComponent() {}
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Generates an instance of the specified class and saves it so that only one
	 * instance exists throughout the entire program. If the JVM deletes such an
	 * instance, it is recreated.
	 *
	 * @return the object instance
	 */
	public static @NotNull UToStringComponent getInstance() {
		if (UObject.isNull(INSTANCE)) {
			INSTANCE = new UGeneralComponent();
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
		if (object.getClass() == Object.class) return toStringObject(object, verbose);
		// Iterate all elements
		for (int i = 0; i < SUPPORTED_CLASSES.length; i++) {
			if (UObject.canCast(object, SUPPORTED_CLASSES[i])) {
				return CONVERSION_REFERENCE[i].invoke(object, verbose);
			}
		}
		// Only for compile purposes
		return toStringObject(object, verbose);
	}
	
	/* -----------------------------------------------------
	 * Internal methods
	 * ----------------------------------------------------- */
	
	/**
	 * Gets the text representation of a text string with its respective double quotes.
	 *
	 * @param object  the object that you want to get the text representation
	 * @param verbose option used to determine if the output will be long or simple
	 * @return object string representation
	 */
	private @NotNull String toStringCharSequence(@NotNull Object object, boolean verbose) {
		return UObject.cast(object, CharSequence.class).toString();
	}
	
	/**
	 * Gets the text representation of {@link Class} types
	 *
	 * @param object  the object that you want to get the text representation
	 * @param verbose option used to determine if the output will be long or simple
	 * @return object string representation
	 */
	private @NotNull String toStringClass(@NotNull Object object, boolean verbose) {
		// Temporal variables
		Class<?> cls = UObject.cast(object);
		StringBuilder clsResult = new StringBuilder();
		
		if (cls.isPrimitive() || cls.isArray()) {
			String clsName = cls.getCanonicalName();
			if (verbose) {
				clsResult.append(clsName);
			} else {
				int index = clsName.lastIndexOf('.');
				clsResult.append(clsName.substring(index + 1));
			}
		}
		
		// Check if clsName is empty
		if (clsResult.length() == 0) {
			clsResult.append(verbose ? cls.getName() :
							 cls.getSimpleName());
		}
		// Check if class is anonymous
		if (clsResult.length() == 0) {
			int index = cls.getName()
				.lastIndexOf('.');
			String anonymous = verbose ? cls.getName() :
							   cls.getName().substring(index + 1);
			clsResult.append(anonymous);
		}
		
		return clsResult.toString();
	}
	
	/**
	 * Gets the generic text representation of all objects and types not
	 * registered within the current instance.
	 *
	 * @param object  the object that you want to get the text representation
	 * @param verbose option used to determine if the output will be long or simple
	 * @return object string representation
	 */
	@SuppressWarnings("CatchMayIgnoreException")
	private @NotNull String toStringObject(@NotNull Object object, boolean verbose) {
		// Temporal variables
		Class<?> cls = object.getClass();
		UReflectionOptions<Method> options = UReflectionOptions.generateForMethods()
			.addPredicate(UReflectionActions.methodReturnType(String.class));
		
		// Try to generate object information
		try {
			Method foundMethod = UReflectionActions
				.findMethod(cls, TO_STRING_METHOD, options);
			
			// Check recursive call
			if (foundMethod.getDeclaringClass() == cls) {
				UReflectionActions
					.checkRecursiveCall(cls, TO_STRING_METHOD);
			}
			
			// Call toString method
			foundMethod.setAccessible(true);
			return UObject.cast(foundMethod.invoke(object), String.class);
		} catch (Exception e) {
			if (e instanceof URecursiveCallException) {
				return String.format("E(@%X) %s", object.hashCode(),
									 e.getMessage());
			}
		}
		
		return String.format("(@%X) %s", object.hashCode(),
							 manager().toString(cls, verbose));
	}
	
}
