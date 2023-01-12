package ushiosan.jvm_utilities.lang.reflection;

import org.jetbrains.annotations.NotNull;
import ushiosan.jvm_utilities.lang.collection.Collections;
import ushiosan.jvm_utilities.lang.reflection.options.ReflectionOpts;

import java.lang.reflect.Field;
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
 * Class with functionalities for handling class fields with reflection
 */
public final class FieldUtils extends ReflectionUtils {
	
	/**
	 * Private constructor
	 */
	private FieldUtils() {}
	
	/* ---------------------------------------------------------
	 * Methods
	 * --------------------------------------------------------- */
	
	/**
	 * It searches inside a class (also inside the classes it inherits) and identifies the one that is mentioned
	 * inside the parameters of the method. Java handles fields independently for each class, and you
	 * cannot access a field that is inside another class, but inherits from it.
	 * That is why a trick is performed to perform such a task.
	 *
	 * @param cls   the class you want to inspect
	 * @param field the field you want to search for
	 * @param opts  reflection options
	 * @return the class field you want to access
	 * @throws NoSuchFieldException error if such field does not exist in the entire inheritance tree of the class
	 * @see ReflectionOpts
	 */
	public static Field findField(@NotNull Class<?> cls, @NotNull String field, @NotNull ReflectionOpts<Field> opts) throws
		NoSuchFieldException {
		Field[] fields = getAllClassFields(cls, opts);
		
		return Arrays.stream(fields)
			.filter(it -> it.getName().contentEquals(field))
			.findFirst()
			.orElseThrow(() -> new NoSuchFieldException(field));
	}
	
	/**
	 * It searches inside a class (also inside the classes it inherits) and identifies the one that is mentioned
	 * inside the parameters of the method. Java handles fields independently for each class, and you
	 * cannot access a field that is inside another class, but inherits from it.
	 * That is why a trick is performed to perform such a task.
	 *
	 * @param cls   the class you want to inspect
	 * @param field the field you want to search for
	 * @return the class field you want to access
	 * @throws NoSuchFieldException Error if such field does not exist in the entire inheritance tree of the class
	 */
	public static Field findField(@NotNull Class<?> cls, @NotNull String field) throws NoSuchFieldException {
		return findField(cls, field, ReflectionOpts.getDefault());
	}
	
	/**
	 * It searches inside a class (also inside the classes it inherits) and identifies the one that is mentioned
	 * inside the parameters of the method. Java handles fields independently for each class, and you
	 * cannot access a field that is inside another class, but inherits from it.
	 * That is why a trick is performed to perform such a task.
	 *
	 * @param obj   the object you want to inspect
	 * @param field the field you want to search for
	 * @param opts  reflection options
	 * @return the class field you want to access
	 * @throws NoSuchFieldException Error if such field does not exist in the entire inheritance tree of the class
	 * @see ReflectionOpts
	 */
	public static Field findFieldObj(@NotNull Object obj, @NotNull String field, @NotNull ReflectionOpts<Field> opts) throws
		NoSuchFieldException {
		return findField(obj.getClass(), field, opts);
	}
	
	/**
	 * It searches inside a class (also inside the classes it inherits) and identifies the one that is mentioned
	 * inside the parameters of the method. Java handles fields independently for each class, and you
	 * cannot access a field that is inside another class, but inherits from it.
	 * That is why a trick is performed to perform such a task.
	 *
	 * @param obj   the object you want to inspect
	 * @param field the field you want to search for
	 * @return the class field you want to access
	 * @throws NoSuchFieldException Error if such field does not exist in the entire inheritance tree of the class
	 */
	public static Field findFieldObj(@NotNull Object obj, @NotNull String field) throws NoSuchFieldException {
		return findFieldObj(obj, field, ReflectionOpts.getDefault());
	}
	
	/**
	 * Returns all defined and undefined fields of a class (for this, this method
	 * accesses the inheritance tree to get all these fields)
	 *
	 * @param cls  the class you want to inspect
	 * @param opts reflection options
	 * @return all valid class fields
	 * @see ReflectionOpts
	 */
	public static Field @NotNull [] getAllClassFields(@NotNull Class<?> cls, @NotNull ReflectionOpts<Field> opts) {
		Stack<Class<?>> classStack = getClassStack(cls, opts.recursive());
		Set<Field> allFields = Collections.mutableSetOf();
		
		for (Class<?> clazz : classStack) {
			List<Field> fields = opts.declaredOnly() ? Collections.listOf() :
								 getAllValidFields(clazz.getFields(), opts);
			List<Field> declaredFields = getAllValidFields(clazz.getDeclaredFields(), opts);
			// Combine fields
			Set<Field> tmpAllFields = cast(Collections.combine(fields, declaredFields));
			allFields = cast(Collections.combine(allFields, tmpAllFields));
		}
		
		return allFields.toArray(Field[]::new);
	}
	
	/**
	 * Returns all defined and undefined fields of a class (for this, this method
	 * accesses the inheritance tree to get all these fields)
	 *
	 * @param cls the class you want to inspect
	 * @return all valid class fields
	 */
	public static Field @NotNull [] getAllClassFields(@NotNull Class<?> cls) {
		return getAllClassFields(cls, ReflectionOpts.getDefault());
	}
	
	/* ---------------------------------------------------------
	 * Internal methods
	 * --------------------------------------------------------- */
	
	/**
	 * Filter all class entries
	 *
	 * @param fields the entries you want to filter
	 * @param opts   reflection options
	 * @return the filtered list of selected entries
	 * @see ReflectionOpts
	 */
	private static @NotNull List<Field> getAllValidFields(Field @NotNull [] fields, @NotNull ReflectionOpts<Field> opts) {
		Stream<Field> stream = Arrays.stream(fields);
		
		// Filter all public fields
		if (opts.onlyPublic()) {
			stream = stream.filter(it -> {
				int mods = it.getModifiers();
				return Modifier.isPublic(mods);
			});
		}
		// Skip all abstract fields
		if (opts.skipAbstracts()) {
			stream = stream.filter(it -> {
				int mods = it.getModifiers();
				return !Modifier.isAbstract(mods);
			});
		}
		// Apply filters
		if (!opts.predicates().isEmpty()) {
			for (Predicate<Field> fieldPredicate : opts.predicates()) {
				stream = stream.filter(fieldPredicate);
			}
		}
		
		return stream.collect(Collectors.toUnmodifiableList());
	}
	
}
