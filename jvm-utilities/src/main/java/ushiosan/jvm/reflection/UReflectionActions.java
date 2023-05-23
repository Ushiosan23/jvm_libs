package ushiosan.jvm.reflection;

import org.jetbrains.annotations.NotNull;
import ushiosan.jvm.internal.reflection.UReflectionImpl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public final class UReflectionActions extends UReflectionImpl {
	
	/**
	 * This class cannot be instantiated
	 */
	private UReflectionActions() {}
	
	/* -----------------------------------------------------
	 * Method reflection methods
	 * ----------------------------------------------------- */
	
	/**
	 * Performs a search for a class member depending on the options passed as a parameter.
	 *
	 * @param cls     the class you want to search for
	 * @param name    the name the member to search for (name only)
	 * @param options search options
	 * @param args    arguments that the method requires to function correctly.
	 * @return the method with the search properties
	 * @throws NoSuchMethodException error if the method does not exist or there is some
	 *                               incorrect configuration so that said member is not found
	 */
	public static @NotNull Method findMethod(@NotNull Class<?> cls, @NotNull String name,
		@NotNull UReflectionOptions<Method> options, Class<?> @NotNull ... args) throws NoSuchMethodException {
		// Temporal variables
		Method[] clsMethods = filterMethods(cls, options);
		return Arrays.stream(clsMethods)
			.filter(named(name))
			.filter(methodTypeParams(args))
			.findFirst()
			.orElseThrow(() -> new NoSuchMethodException(name));
	}
	
	/**
	 * Performs a search for a class member depending on the options passed as a parameter.
	 *
	 * @param cls  the class you want to search for
	 * @param name the name the member to search for (name only)
	 * @param args arguments that the method requires to function correctly.
	 * @return the method with the search properties
	 * @throws NoSuchMethodException error if the method does not exist or there is some
	 *                               incorrect configuration so that said member is not found
	 */
	public static @NotNull Method findMethod(@NotNull Class<?> cls, @NotNull String name, Class<?> @NotNull ... args) throws
		NoSuchMethodException {
		return findMethod(cls, name, UReflectionOptions.generateForMethods(), args);
	}
	
	/**
	 * Performs a search for a class member depending on the options passed as a parameter.
	 *
	 * @param cls     the class you want to search for
	 * @param name    the name the member to search for (name only)
	 * @param type    the data type of the member to search for
	 * @param options search options
	 * @return the field with the search properties
	 * @throws NoSuchFieldException error if the field does not exist or there is some
	 *                              incorrect configuration so that said member is not found
	 */
	public static @NotNull Field findField(@NotNull Class<?> cls, @NotNull String name,
		@NotNull Class<?> type, @NotNull UReflectionOptions<Field> options) throws NoSuchFieldException {
		// Temporal variables
		Field[] clsFields = filterFields(cls, options);
		return Arrays.stream(clsFields)
			.filter(named(name))
			.filter(fieldType(type))
			.findFirst()
			.orElseThrow(() -> new NoSuchFieldException(name));
	}
	
	/**
	 * Performs a search for a class member depending on the options passed as a parameter.
	 *
	 * @param cls  the class you want to search for
	 * @param name the name the member to search for (name only)
	 * @param type the data type of the member to search for
	 * @return the field with the search properties
	 * @throws NoSuchFieldException error if the field does not exist or there is some
	 *                              incorrect configuration so that said member is not found
	 */
	public static @NotNull Field findField(@NotNull Class<?> cls, @NotNull String name, @NotNull Class<?> type) throws
		NoSuchFieldException {
		return findField(cls, name, type, UReflectionOptions.generateForFields());
	}
	
}
