package ushiosan.jvm_utilities.internal.print.str;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ushiosan.jvm_utilities.function.Apply;
import ushiosan.jvm_utilities.lang.Cls;
import ushiosan.jvm_utilities.lang.Obj;
import ushiosan.jvm_utilities.lang.collection.Collections;
import ushiosan.jvm_utilities.lang.collection.elements.Pair;
import ushiosan.jvm_utilities.lang.reflection.MethodUtils;
import ushiosan.jvm_utilities.lang.reflection.options.ReflectionOpts;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static ushiosan.jvm_utilities.lang.Obj.canCast;
import static ushiosan.jvm_utilities.lang.Obj.canCastNotNull;
import static ushiosan.jvm_utilities.lang.Obj.cast;

/**
 * Class containing all the base functionality for printing objects.
 * <p>
 * This object can add extra functionality thanks to a plugin system without having to edit the class
 */
public abstract class BasePrintObject {
	
	/* -----------------------------------------------------
	 * Properties
	 * ----------------------------------------------------- */
	
	/**
	 * Print map
	 */
	protected List<Pair<Apply.Result<Object, String>, Class<?>[]>> printMap = Collections.mutableListOf();
	
	/* -----------------------------------------------------
	 * Constructors
	 * ----------------------------------------------------- */
	
	/**
	 * This class cannot be instantiated.
	 * <p>
	 * Singleton or utility class mode.
	 */
	protected BasePrintObject() {
		attach(this::toClassString, Class.class);
		attach(this::toCollectionString, Collection.class);
		attach(this::toEntryString, Map.Entry.class);
		attach(this::toMapString, Map.class);
		attach(this::toStringString, String.class, CharSequence.class);
		attach(this::toPathString, Path.class);
		// Should always go last to prevent unexpected behavior
		attach(this::toObjectString, Object.class);
	}
	
	/* -----------------------------------------------------
	 * Global methods
	 * ----------------------------------------------------- */
	
	/**
	 * Get valid print object instance
	 *
	 * @param verbose verbose information state
	 * @return a valid print object instance
	 */
	public static @NotNull BasePrintObject getInstance(boolean verbose) {
		return verbose ? VerbosePrintObject.getInstance() :
			   SimplePrintObject.getInstance();
	}
	
	/**
	 * Null reference string representation
	 *
	 * @return null string representation
	 */
	public @NotNull String nullString() {
		return "<null>";
	}
	
	/* -----------------------------------------------------
	 * String methods
	 * ----------------------------------------------------- */
	
	/**
	 * Insert new printable extension to the instance
	 *
	 * @param extension the extension to insert
	 */
	public void attachExtension(@NotNull Pair<Apply.Result<Object, String>, Class<?>[]> extension) {
		// Store the last element
		int lastIndex = printMap.size() - 1;
		Pair<Apply.Result<Object, String>, Class<?>[]> lastElement = printMap.get(lastIndex);
		List<Pair<Apply.Result<Object, String>, Class<?>[]>> tmpList = Collections
			.mutableListOf(printMap.subList(0, lastIndex));
		// Insert elements
		tmpList.add(extension);
		tmpList.add(lastElement);
		// Replace the list
		printMap = tmpList;
	}
	
	/**
	 * Insert new printable extension to the instance
	 *
	 * @param action    the extension action
	 * @param supported supported elements
	 */
	public void attachExtension(@NotNull Apply.Result<Object, String> action, Class<?> @NotNull ... supported) {
		attachExtension(Pair.of(action, supported));
	}
	
	/**
	 * Object string representation
	 *
	 * @param obj the object to convert
	 * @return an object string representation
	 */
	public @NotNull String toString(@Nullable Object obj) {
		if (obj == null) return nullString();
		// Check if object is a primitive type
		if (Cls.isPrimitive(obj)) {
			return canCast(obj, Character.class) ? String.format("'%s'", obj) :
				   obj.toString();
		}
		// Check if object is an array
		if (obj.getClass().isArray()) {
			return BasePrintArray.getInstance(this)
				.toString(obj);
		}
		// Other cases
		String result = nullString();
		for (Pair<Apply.Result<Object, String>, Class<?>[]> entry : printMap) {
			Class<?>[] entryClassArr = entry.second;
			for (Class<?> clazz : entryClassArr) {
				if (canCastNotNull(obj, clazz)) {
					return entry.first.apply(obj);
				}
			}
		}
		// Returns the default result if action is not registered
		return result;
	}
	
	/* ---------------------------------------------------------
	 * Internal methods
	 * --------------------------------------------------------- */
	
	/**
	 * String representation
	 *
	 * @param obj the object to convert
	 * @return an object string representation
	 */
	protected String toStringString(@NotNull Object obj) {
		return String.format("\"%s\"", obj);
	}
	
	/**
	 * Entry string representation
	 *
	 * @param obj the object to convert
	 * @return an object string representation
	 */
	protected @NotNull String toEntryString(@NotNull Object obj) {
		Map.Entry<?, ?> entry = cast(obj, Map.Entry.class);
		return String.format("%s=%s", toString(entry.getKey()), toString(entry.getValue()));
	}
	
	/**
	 * Generic object string representation
	 *
	 * @param obj the object to convert
	 * @return an object string representation
	 */
	protected @NotNull String toObjectString(@NotNull Object obj) {
		// Temporal variables
		Class<?> clazz = obj.getClass();
		ReflectionOpts<Method> opts = ReflectionOpts.<Method>getDefault()
			.setDeclaredOnly(true)
			.setSkipAbstracts(true)
			.setOnlyPublic(true);
		// Verify that the object has the method "toString" defined to call it instead.
		try {
			Method toStringMethod = MethodUtils.findMethodObj(obj, "toString", opts);
			if (toStringMethod.getDeclaringClass() == clazz) {
				throw new IllegalAccessException("Recursive call");
			}
			
			toStringMethod.setAccessible(true);
			return cast(toStringMethod.invoke(obj), String.class);
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			return String.format("(@%X) %s", obj.hashCode(), getInstance(isVerbose()).toClassString(clazz));
		}
	}
	
	/**
	 * Class string representation
	 *
	 * @param obj the object to convert
	 * @return an object string representation
	 */
	protected @NotNull String toClassString(@NotNull Object obj) {
		Class<?> clazz = cast(obj, Class.class);
		String name = "";
		// Check if class is a primitive type
		if (clazz.isPrimitive() || clazz.isArray()) {
			name = clazz.getCanonicalName();
		}
		if (name.isBlank()) {
			name = isVerbose() ? clazz.getName() : clazz.getSimpleName();
		}
		return name;
	}
	
	/**
	 * Path string representation
	 *
	 * @param obj the object to convert
	 * @return an object string representation
	 */
	protected @NotNull String toPathString(@NotNull Object obj) {
		return Obj.cast(obj, Path.class).toAbsolutePath().toString();
	}
	
	/**
	 * Collection string representation
	 *
	 * @param obj the object to convert
	 * @return an object string representation
	 */
	protected abstract @NotNull String toCollectionString(@NotNull Object obj);
	
	/**
	 * Map string representation
	 *
	 * @param obj the object to convert
	 * @return an object string representation
	 */
	protected abstract @NotNull String toMapString(@NotNull Object obj);
	
	/**
	 * check if an object is verbose or not
	 *
	 * @return verbose status
	 */
	protected abstract boolean isVerbose();
	
	/* -----------------------------------------------------
	 * Static methods
	 * ----------------------------------------------------- */
	
	/**
	 * Insert new printable extension to the instance
	 *
	 * @param action    the extension action
	 * @param supported supported elements
	 */
	private void attach(@NotNull Apply.Result<Object, String> action, Class<?> @NotNull ... supported) {
		printMap.add(Pair.of(action, supported));
	}
	
}
