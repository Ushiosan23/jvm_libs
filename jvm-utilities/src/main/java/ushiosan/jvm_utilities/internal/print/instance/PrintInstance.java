package ushiosan.jvm_utilities.internal.print.instance;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ushiosan.jvm_utilities.lang.Obj;
import ushiosan.jvm_utilities.lang.collection.Arrs;
import ushiosan.jvm_utilities.lang.collection.elements.Pair;
import ushiosan.jvm_utilities.lang.print.annotations.PrintExclude;
import ushiosan.jvm_utilities.lang.print.annotations.PrintOpts;
import ushiosan.jvm_utilities.lang.reflection.FieldUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PrintInstance {
	
	/* -----------------------------------------------------
	 * Properties
	 * ----------------------------------------------------- */
	
	/**
	 * All invalid method names
	 */
	private static final String[] INVALID_METHODS = new String[]{
		"toString", "getClass", "hashCode", "getDeclaringClass", "ordinal"
	};
	/**
	 * Empty method array
	 */
	private static final Method[] EMPTY_METHODS = new Method[0];
	/**
	 * Empty fields array
	 */
	private static final Field[] EMPTY_FIELDS = new Field[0];
	/**
	 * Default options instance
	 */
	private static final PrintOpts DEFAULT_OPTS = new PrintOpts() {
		
		@Override
		public boolean shortName() {
			return true;
		}
		
		@Override
		public boolean privateFieldsAccess() {
			return false;
		}
		
		@Override
		public boolean getterAccess() {
			return false;
		}
		
		@Contract(pure = true)
		@Override
		public @NotNull String getterPrefix() {
			return "^(get|is)";
		}
		
		@Contract(pure = true)
		@Override
		public @NotNull String getterSuffix() {
			return "";
		}
		
		@Override
		public Class<? extends Annotation> annotationType() {
			return PrintOpts.class;
		}
		
	};
	/**
	 * Current class instance
	 */
	private static PrintInstance INSTANCE;
	
	/* -----------------------------------------------------
	 * Constructors
	 * ----------------------------------------------------- */
	
	/**
	 * This class cannot be instantiated.
	 * <p>
	 * Singleton or utility class mode.
	 */
	private PrintInstance() {
	}
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Get current class instance
	 *
	 * @return an instance of current class
	 */
	public static PrintInstance getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PrintInstance();
		}
		return INSTANCE;
	}
	
	/* -----------------------------------------------------
	 * Internal methods
	 * ----------------------------------------------------- */
	
	/**
	 * Loop through the entire object and create a representation of the
	 * object in a text string. This behavior can be configured using class annotations
	 * such as {@link PrintOpts} and {@link PrintExclude}.
	 *
	 * @param obj the object to transform
	 * @return object string representation
	 */
	public @NotNull String toString(@NotNull Object obj) {
		// Temporal variables
		PrintOpts opts = DEFAULT_OPTS;
		Class<?> definedClass = obj.getClass();
		
		while (definedClass != Object.class) {
			// Check if annotation is present in the selected class
			if (definedClass.isAnnotationPresent(PrintOpts.class)) {
				opts = definedClass.getAnnotation(PrintOpts.class);
				break;
			}
			definedClass = definedClass.getSuperclass();
		}
		
		return toStringImpl(obj, opts);
	}
	
	/**
	 * Loop through the entire object and create a representation of the
	 * object in a text string. This behavior can be configured using class annotations
	 * such as {@link PrintOpts} and {@link PrintExclude}.
	 *
	 * @param obj  the object to transform
	 * @param opts print options
	 * @return object string representation
	 */
	private @NotNull String toStringImpl(@NotNull Object obj, @NotNull PrintOpts opts) {
		Class<?> clazz = obj.getClass();
		Method[] methods = getAllValidMethods(clazz, opts);
		Field[] fields = getAllValidFields(clazz, opts);
		StringBuilder builder = new StringBuilder(opts.shortName() ? clazz.getSimpleName() : Obj.toString(clazz));
		builder.append("{");
		
		try {
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				if (i != 0) builder.append(", ");
				builder.append(fields[i].getName()).append("=");
				builder.append(Obj.toString(fields[i].get(obj)));
			}
		} catch (Exception ignored) {
		}
		
		try {
			if (!builder.toString().endsWith("{") && methods.length != 0) builder.append(", ");
			for (int i = 0; i < methods.length; i++) {
				methods[i].setAccessible(true);
				if (i != 0) builder.append(", ");
				builder.append(methods[i].getName()).append("()=");
				builder.append(Obj.toString(methods[i].invoke(obj)));
			}
		} catch (Exception ignored) {
		}
		
		return builder.append("}").toString();
	}
	
	/**
	 * Returns all valid fields that will be part of the representation in the string
	 *
	 * @param cls  the instance class
	 * @param opts the print options
	 * @return all valid fields or {@link #EMPTY_FIELDS}
	 */
	private Field @NotNull [] getAllValidFields(@NotNull Class<?> cls, @NotNull PrintOpts opts) {
		Field[] allFields = FieldUtils
			.getAllRecursiveFields(cls);
		
		if (allFields.length == 0) return EMPTY_FIELDS;
		return Arrays.stream(allFields)
			.map(it -> Pair.of(it, opts))
			.filter(this::isValidField)
			.map(it -> it.first)
			.toArray(Field[]::new);
	}
	
	/**
	 * Returns all valid methods that will be part of the representation in the string
	 *
	 * @param cls  the instance class
	 * @param opts the print options
	 * @return all valid methods or {@link #EMPTY_METHODS}
	 */
	private Method @NotNull [] getAllValidMethods(@NotNull Class<?> cls, @NotNull PrintOpts opts) {
		Pattern prefix = Pattern.compile(opts.getterPrefix());
		Pattern suffix = Pattern.compile(opts.getterSuffix());
		// Check if options are null
		if (!opts.getterAccess()) return EMPTY_METHODS;
		return Arrays.stream(cls.getMethods())
			.map(it -> Pair.of(it, Pair.of(prefix, suffix)))
			.filter(this::isValidMethod)
			.map(it -> it.first)
			.toArray(Method[]::new);
	}
	
	/**
	 * Validate each field individually
	 *
	 * @param pair the pair information
	 * @return {@code true} if field is valid or {@code false} otherwise
	 */
	private boolean isValidField(@NotNull Pair<Field, PrintOpts> pair) {
		int mods = pair.first.getModifiers();
		boolean modsState = !Modifier.isAbstract(mods) && !Modifier.isStatic(mods);
		
		// Finalize modifiers check
		if (Modifier.isProtected(mods) || Modifier.isPrivate(mods)) {
			modsState = modsState && pair.second.privateFieldsAccess();
		}
		
		return modsState && !pair.first.isAnnotationPresent(PrintExclude.class);
	}
	
	/* -----------------------------------------------------
	 * Static methods
	 * ----------------------------------------------------- */
	
	/**
	 * Validate each method individually
	 *
	 * @param pair the pair information
	 * @return {@code true} if method is valid or {@code false} otherwise
	 */
	private boolean isValidMethod(@NotNull Pair<Method, Pair<Pattern, Pattern>> pair) {
		// Temporal variables
		Method method = pair.first;
		// Check if method return something
		if (method.getParameterCount() != 0 || method.getReturnType() == void.class) {
			return false;
		}
		
		// Match name
		Matcher pMatch = pair.second.first.matcher(method.getName());
		Matcher sMatch = pair.second.second.matcher(method.getName());
		if (!pMatch.find() || !sMatch.find() ||
			Arrs.contains(INVALID_METHODS, method.getName())) {
			return false;
		}
		
		// Check methods modifiers
		int mods = method.getModifiers();
		boolean modsState = !Modifier.isAbstract(mods) && !Modifier.isPrivate(mods) &&
							!Modifier.isProtected(mods) && !Modifier.isStatic(mods);
		return modsState && !method.isAnnotationPresent(PrintExclude.class);
	}
	
}
