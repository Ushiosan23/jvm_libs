package ushiosan.jvm_utilities.internal.print.instance;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ushiosan.jvm_utilities.lang.Obj;
import ushiosan.jvm_utilities.lang.print.PrintObj;
import ushiosan.jvm_utilities.lang.print.annotations.PrintExclude;
import ushiosan.jvm_utilities.lang.print.annotations.PrintOpts;
import ushiosan.jvm_utilities.lang.reflection.FieldUtils;
import ushiosan.jvm_utilities.lang.reflection.MethodUtils;
import ushiosan.jvm_utilities.lang.reflection.options.ReflectionOpts;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

import static ushiosan.jvm_utilities.lang.Obj.cast;

/**
 * Class with utilities used to print instances dynamically
 */
public final class PrintInstance {
	
	/* -----------------------------------------------------
	 * Properties
	 * ----------------------------------------------------- */
	
	/**
	 * All invalid method names.
	 * They are really valid, but they are all inside the {@link Object} super class, and
	 * they are methods that can be omitted and can even become redundant.
	 */
	private static final String[] INVALID_METHODS = new String[]{
		"toString", "getClass", "hashCode", "getDeclaringClass", "ordinal", "notify", "notifyAll", "wait"
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
		public boolean recursive() {
			return false;
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
		
		while (true) {
			if (definedClass == Object.class || definedClass.isEnum()) break;
			
			if (definedClass.isAnnotationPresent(PrintOpts.class)) {
				opts = definedClass.getAnnotation(PrintOpts.class);
				break;
			}
			
			definedClass = definedClass.getSuperclass();
		}
		
		return definedClass.isEnum() ? toEnumStringImpl(obj) :
			   toStringImpl(obj, opts);
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
		Class<?> cls = obj.getClass();
		Method[] methods = getAllValidMethods(cls, opts);
		Field[] fields = getAllValidFields(cls, opts);
		StringBuilder builder = new StringBuilder(opts.shortName() ? cls.getSimpleName() : Obj.toString(cls));
		builder.append("{");
		
		try {
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				if (i != 0) builder.append(", ");
				builder.append(fields[i].getName()).append("=");
				builder.append(PrintObj.toString(fields[i].get(obj), !opts.shortName()));
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
	 * Returns the representation of an enumerated type in text.
	 * This behavior can be configured using class annotations such
	 * as {@link PrintOpts} and {@link PrintExclude}.
	 *
	 * @param enumObj the object to transform
	 * @return object string representation
	 */
	private @NotNull String toEnumStringImpl(@NotNull Object enumObj) {
		return cast(enumObj, Enum.class).name();
	}
	
	/**
	 * Returns all valid fields that will be part of the representation in the string
	 *
	 * @param cls  the instance class
	 * @param opts the print options
	 * @return all valid fields or {@link #EMPTY_FIELDS}
	 */
	private Field @NotNull [] getAllValidFields(@NotNull Class<?> cls, @NotNull PrintOpts opts) {
		ReflectionOpts<Field> rOpts = ReflectionOpts.<Field>getDefault()
			.setOnlyPublic(!opts.privateFieldsAccess())
			.setRecursive(opts.recursive())
			.setDeclaredOnly(false)
			.addPredicate(FieldUtils.requireAnnotations(true, PrintExclude.class));
		
		return FieldUtils.getAllClassFields(cls, rOpts);
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
		ReflectionOpts<Method> rOpts = ReflectionOpts.<Method>getDefault()
			.setOnlyPublic(true)
			.addPredicate(MethodUtils.validGetterMethod())
			.setRecursive(opts.recursive())
			.addPredicate(MethodUtils.excludeAll(INVALID_METHODS))
			.addPredicate(MethodUtils.regexMultipleOf(prefix, suffix))
			.addPredicate(MethodUtils.requireAnnotations(true, PrintExclude.class));
		
		return MethodUtils.getAllClassMethods(cls, rOpts);
	}
	
}
