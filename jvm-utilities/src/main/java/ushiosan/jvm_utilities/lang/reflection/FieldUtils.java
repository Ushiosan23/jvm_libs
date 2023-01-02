package ushiosan.jvm_utilities.lang.reflection;

import org.jetbrains.annotations.NotNull;
import ushiosan.jvm_utilities.lang.collection.Collections;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ushiosan.jvm_utilities.lang.reflection.ClassUtils.getClassStack;

public final class FieldUtils {
	
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
	 * @param cls           the class you want to inspect
	 * @param field         the field you want to search for
	 * @param recursive     returns inputs recursively
	 * @param onlyPublic    returns only public entries
	 * @param skipAbstracts omit all abstract entries
	 * @return the class field you want to access
	 * @throws NoSuchFieldException Error if such field does not exist in the entire inheritance tree of the class
	 */
	public static Field findField(@NotNull Class<?> cls, @NotNull String field, boolean recursive, boolean onlyPublic,
		boolean skipAbstracts) throws NoSuchFieldException {
		Field[] fields = getAllClassFields(cls, recursive, onlyPublic, skipAbstracts);
		
		return Arrays.stream(fields)
			.filter(it -> it.getName().equals(field))
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
		return findField(cls, field, true, false, false);
	}
	
	/**
	 * It searches inside a class (also inside the classes it inherits) and identifies the one that is mentioned
	 * inside the parameters of the method. Java handles fields independently for each class, and you
	 * cannot access a field that is inside another class, but inherits from it.
	 * That is why a trick is performed to perform such a task.
	 *
	 * @param obj           the object you want to inspect
	 * @param field         the field you want to search for
	 * @param recursive     returns inputs recursively
	 * @param onlyPublic    returns only public entries
	 * @param skipAbstracts omit all abstract entries
	 * @return the class field you want to access
	 * @throws NoSuchFieldException Error if such field does not exist in the entire inheritance tree of the class
	 */
	public static Field findFieldObj(@NotNull Object obj, @NotNull String field, boolean recursive, boolean onlyPublic,
		boolean skipAbstracts) throws NoSuchFieldException {
		return findField(obj.getClass(), field, recursive, onlyPublic, skipAbstracts);
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
		return findFieldObj(obj, field, true, false, false);
	}
	
	/**
	 * Returns all defined and undefined fields of a class (for this, this method
	 * accesses the inheritance tree to get all these fields)
	 *
	 * @param cls           the class you want to inspect
	 * @param recursive     returns inputs recursively
	 * @param onlyPublic    returns only public entries
	 * @param skipAbstracts omit all abstract entries
	 * @return all valid class fields
	 */
	public static Field @NotNull [] getAllClassFields(@NotNull Class<?> cls, boolean recursive, boolean onlyPublic,
		boolean skipAbstracts) {
		Stack<Class<?>> classStack = getClassStack(cls, recursive);
		List<Field> allFields = Collections.mutableListOf();
		
		for (Class<?> clazz : classStack) {
			List<Field> fields = getAllValidFields(clazz.getFields(), onlyPublic, skipAbstracts);
			List<Field> declaredFields = getAllValidFields(clazz.getDeclaredFields(), onlyPublic, skipAbstracts);
			// Combine fields
			allFields.addAll(fields);
			allFields.addAll(declaredFields);
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
		return getAllClassFields(cls, true, false, false);
	}
	
	/* ---------------------------------------------------------
	 * Internal methods
	 * --------------------------------------------------------- */
	
	/**
	 * Filter all class entries
	 *
	 * @param fields        the entries you want to filter
	 * @param onlyPublic    returns only public entries
	 * @param skipAbstracts omit all abstract entries
	 * @return the filtered list of selected entries
	 */
	private static @NotNull List<Field> getAllValidFields(Field @NotNull [] fields, boolean onlyPublic, boolean skipAbstracts) {
		Stream<Field> stream = Arrays.stream(fields);
		
		// Filter all public fields
		if (onlyPublic) {
			stream = stream.filter(it -> {
				int mods = it.getModifiers();
				return Modifier.isPublic(mods);
			});
		}
		// Skip all abstract fields
		if (skipAbstracts) {
			stream = stream.filter(it -> {
				int mods = it.getModifiers();
				return !Modifier.isAbstract(mods);
			});
		}
		
		return stream.collect(Collectors.toUnmodifiableList());
	}
	
}
