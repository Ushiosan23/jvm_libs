package ushiosan.jvm_utilities.lang.reflection;

import org.jetbrains.annotations.NotNull;
import ushiosan.jvm_utilities.lang.collection.Collections;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

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
	 * @param cls   the class you want to inspect
	 * @param field the field you want to search for
	 * @return the class field you want to access
	 * @throws NoSuchFieldException Error if such field does not exist in the entire inheritance tree of the class
	 */
	public static Field findField(@NotNull Class<?> cls, @NotNull String field) throws NoSuchFieldException {
		Field[] fields = getAllRecursiveFields(cls);
		
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
	 * @param obj   the object you want to inspect
	 * @param field the field you want to search for
	 * @return the class field you want to access
	 * @throws NoSuchFieldException Error if such field does not exist in the entire inheritance tree of the class
	 */
	public static Field findFieldObj(@NotNull Object obj, @NotNull String field) throws NoSuchFieldException {
		return findField(obj.getClass(), field);
	}
	
	/**
	 * Returns all defined and undefined fields of a class (for this, this method
	 * accesses the inheritance tree to get all these fields)
	 *
	 * @param cls the class you want to inspect
	 * @return all valid class fields
	 */
	public static Field @NotNull [] getAllRecursiveFields(@NotNull Class<?> cls) {
		Stack<Class<?>> classStack = getClassStack(cls);
		List<Field> allFields = Collections.mutableListOf();
		
		for (Class<?> iCls : classStack) {
			var fields = Collections.listOf(iCls.getFields());
			var declaredFields = Collections.listOf(iCls.getDeclaredFields());
			// Inset all fields
			allFields.addAll(fields);
			allFields.addAll(declaredFields);
		}
		
		return allFields.toArray(Field[]::new);
	}
	
}
