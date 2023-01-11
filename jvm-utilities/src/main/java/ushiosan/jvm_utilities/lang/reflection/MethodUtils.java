package ushiosan.jvm_utilities.lang.reflection;

import org.jetbrains.annotations.NotNull;
import ushiosan.jvm_utilities.lang.collection.Collections;
import ushiosan.jvm_utilities.lang.reflection.options.ReflectionOpts;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ushiosan.jvm_utilities.lang.Obj.cast;
import static ushiosan.jvm_utilities.lang.reflection.ClassUtils.getClassStack;

/**
 * Class containing utilities for handling methods obtained by reflection. In addition
 * to filters for handling within the same functionality of the class to have a more powerful reflection.
 */
public final class MethodUtils extends ReflectionUtils {
	
	/**
	 * Private constructor
	 */
	private MethodUtils() {}
	
	/* ---------------------------------------------------------
	 * Methods
	 * --------------------------------------------------------- */
	
	/**
	 * It searches inside a class (also inside the classes it inherits) and identifies the one that is mentioned
	 * inside the method parameters. Java handles methods independently for each class, and you
	 * cannot access a field that is inside another class, but inherits from it.
	 * That is why a trick is performed to perform such a task.
	 *
	 * @param cls       the class you want to inspect
	 * @param name      the method you want to search for
	 * @param opts      reflection options
	 * @param argsTypes the data types contained in the method parameters
	 * @return the method that contains all the constraints mentioned
	 * @throws NoSuchMethodException error if such method does not exist in the entire inheritance tree of the class
	 * @see ReflectionOpts
	 */
	public static Method findMethod(@NotNull Class<?> cls, @NotNull String name, @NotNull ReflectionOpts<Method> opts,
		Class<?>... argsTypes) throws NoSuchMethodException {
		Method[] methods = getAllClassMethods(cls, opts);
		
		return Arrays.stream(methods)
			.filter(it -> it.getName().equals(name))
			.filter(it -> checkParametersEquals(it.getParameterTypes(), argsTypes))
			.findFirst()
			.orElseThrow(() -> new NoSuchMethodException(name));
	}
	
	/**
	 * It searches inside a class (also inside the classes it inherits) and identifies the one that is mentioned
	 * inside the method parameters. Java handles methods independently for each class, and you
	 * cannot access a field that is inside another class, but inherits from it.
	 * That is why a trick is performed to perform such a task.
	 *
	 * @param cls       the class you want to inspect
	 * @param name      the method you want to search for
	 * @param argsTypes the data types contained in the method parameters
	 * @return the method that contains all the constraints mentioned
	 * @throws NoSuchMethodException error if such method does not exist in the entire inheritance tree of the class
	 * @see ReflectionOpts
	 */
	public static Method findMethod(@NotNull Class<?> cls, @NotNull String name, Class<?>... argsTypes) throws
		NoSuchMethodException {
		return findMethod(cls, name, ReflectionOpts.getDefault(), argsTypes);
	}
	
	/**
	 * It searches inside a class (also inside the classes it inherits) and identifies the one that is mentioned
	 * inside the method parameters. Java handles methods independently for each class, and you
	 * cannot access a field that is inside another class, but inherits from it.
	 * That is why a trick is performed to perform such a task.
	 *
	 * @param obj       the object you want to inspect
	 * @param name      the method you want to search for
	 * @param opts      reflection options
	 * @param argsTypes the data types contained in the method parameters
	 * @return the method that contains all the constraints mentioned
	 * @throws NoSuchMethodException error if such method does not exist in the entire inheritance tree of the class
	 * @see ReflectionOpts
	 */
	public static Method findMethodObj(@NotNull Object obj, @NotNull String name, @NotNull ReflectionOpts<Method> opts,
		Class<?>... argsTypes) throws NoSuchMethodException {
		return findMethod(obj.getClass(), name, opts, argsTypes);
	}
	
	/**
	 * It searches inside a class (also inside the classes it inherits) and identifies the one that is mentioned
	 * inside the method parameters. Java handles methods independently for each class, and you
	 * cannot access a field that is inside another class, but inherits from it.
	 * That is why a trick is performed to perform such a task.
	 *
	 * @param obj       the object you want to inspect
	 * @param name      the method you want to search for
	 * @param argsTypes the data types contained in the method parameters
	 * @return the method that contains all the constraints mentioned
	 * @throws NoSuchMethodException error if such method does not exist in the entire inheritance tree of the class
	 * @see ReflectionOpts
	 */
	public static Method findMethodObj(@NotNull Object obj, @NotNull String name, Class<?>... argsTypes) throws
		NoSuchMethodException {
		return findMethodObj(obj, name, ReflectionOpts.getDefault(), argsTypes);
	}
	
	/**
	 * Returns all defined and undefined methods of a class (for this, this method
	 * accesses the inheritance tree to get all these fields)
	 *
	 * @param cls  the class you want to inspect
	 * @param opts reflection options
	 * @return all valid class methods
	 * @see ReflectionOpts
	 */
	public static Method @NotNull [] getAllClassMethods(@NotNull Class<?> cls, @NotNull ReflectionOpts<Method> opts) {
		Stack<Class<?>> classStack = getClassStack(cls, opts.recursive());
		Set<Method> allMethods = Collections.mutableSetOf();
		
		for (Class<?> clazz : classStack) {
			List<Method> methods = opts.declaredOnly() ? Collections.listOf() :
								   validateMethods(clazz.getMethods(), opts);
			List<Method> declaredMethods = validateMethods(clazz.getDeclaredMethods(), opts);
			// Combine all valid methods
			allMethods = cast(Collections.combine(methods, declaredMethods));
		}
		
		return allMethods.toArray(Method[]::new);
	}
	
	/**
	 * Returns all defined and undefined methods of a class (for this, this method
	 * accesses the inheritance tree to get all these fields)
	 *
	 * @param cls the class you want to inspect
	 * @return all valid class methods
	 * @see ReflectionOpts
	 */
	public static Method @NotNull [] getAllClassMethods(@NotNull Class<?> cls) {
		return getAllClassMethods(cls, ReflectionOpts.getDefault());
	}
	
	/* ---------------------------------------------------------
	 * Internal methods
	 * --------------------------------------------------------- */
	
	/**
	 * Validates the methods that you want to obtain
	 *
	 * @param methods all methods of the class
	 * @param opts    reflection options for filters
	 * @return the methods with the constraints applied
	 */
	private static @NotNull List<Method> validateMethods(Method @NotNull [] methods, @NotNull ReflectionOpts<Method> opts) {
		Stream<Method> stream = Arrays.stream(methods);
		
		// Filter all public methods
		if (opts.onlyPublic()) {
			stream = stream.filter(it -> {
				int modifiers = it.getModifiers();
				return Modifier.isPublic(modifiers);
			});
		}
		// Skip all abstract methods
		if (opts.skipAbstracts()) {
			stream = stream.filter(it -> {
				int modifiers = it.getModifiers();
				return !Modifier.isAbstract(modifiers);
			});
		}
		// Skip all static methods
		if (opts.skipStatic()) {
			stream = stream.filter(it -> {
				int modifiers = it.getModifiers();
				return !Modifier.isStatic(modifiers);
			});
		}
		// Apply filters
		if (!opts.predicates().isEmpty()) {
			for (Predicate<Method> predicate : opts.predicates()) {
				stream = stream.filter(predicate);
			}
		}
		
		return stream.collect(Collectors.toUnmodifiableList());
	}
	
	/**
	 * Check if the types of arguments are the same as those required by the search.
	 *
	 * @param current  the types of method arguments
	 * @param expected the types of arguments expected
	 * @return {@code true} if type parameters are equals or {@code false} otherwise
	 */
	private static boolean checkParametersEquals(Class<?> @NotNull [] current, Class<?> @NotNull [] expected) {
		boolean result = current.length == expected.length;
		if (result) {
			for (int i = 0; i < current.length; i++) {
				if (!current[i].equals(expected[i])) {
					result = false;
					break;
				}
			}
		}
		
		return result;
	}
	
}
