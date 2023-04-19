package ushiosan.jvm_utilities.lang.reflection;

import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ushiosan.jvm_utilities.lang.collection.Arrs;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class ReflectionUtils {
	
	/**
	 * Java classes that represent no value, but are a valid data type
	 */
	static final Class<?>[] INVALID_RETURN_TYPES = new Class[]{void.class, Void.class};
	
	/* ---------------------------------------------------------
	 * Constants
	 * --------------------------------------------------------- */
	
	/**
	 * Private constructor
	 */
	ReflectionUtils() {}
	
	/* ---------------------------------------------------------
	 * Filter methods
	 * --------------------------------------------------------- */
	
	/**
	 * Generates a filter to capture the elements that meet said restriction
	 *
	 * @param regex    regular expression to apply
	 * @param inverted option to perform the action inverted
	 * @param <T>      generic member type
	 * @return the filter instance with the desired behavior
	 */
	public static @NotNull <T extends Member> Predicate<T> regexOf(@NotNull @RegExp String regex, boolean inverted) {
		return regexOf(Pattern.compile(regex), inverted);
	}
	
	/**
	 * Generates a filter to capture the elements that meet said restriction
	 *
	 * @param regex regular expression to apply
	 * @param <T>   generic member type
	 * @return the filter instance with the desired behavior
	 */
	public static @NotNull <T extends Member> Predicate<T> regexOf(@NotNull @RegExp String regex) {
		return regexOf(Pattern.compile(regex));
	}
	
	/**
	 * Generates a filter to capture the elements that meet said restriction
	 *
	 * @param pattern  regular expression object to apply
	 * @param inverted option to perform the action inverted
	 * @param <T>      generic member type
	 * @return the filter instance with the desired behavior
	 */
	@Contract(pure = true)
	public static @NotNull <T extends Member> Predicate<T> regexOf(@NotNull Pattern pattern, boolean inverted) {
		return (it) -> {
			Matcher matcher = pattern.matcher(it.getName());
			return inverted != matcher.find();
		};
	}
	
	/**
	 * Generates a filter to capture the elements that meet said restriction
	 *
	 * @param pattern regular expression object to apply
	 * @param <T>     generic member type
	 * @return the filter instance with the desired behavior
	 */
	public static @NotNull <T extends Member> Predicate<T> regexOf(@NotNull Pattern pattern) {
		return regexOf(pattern, false);
	}
	
	/**
	 * Generates a filter to capture the items that meet that constraint.
	 * The only difference with the {@link #regexOf(String)} method is that it contains multiple constraints.
	 *
	 * @param inverted option to perform the action inverted
	 * @param regex    regular expression to apply
	 * @param <T>      generic member type
	 * @return the filter instance with the desired behavior
	 */
	public static @NotNull <T extends Member> Predicate<T> regexMultipleOf(boolean inverted,
		@RegExp String @NotNull ... regex) {
		Pattern[] patterns = Arrs.transform(regex, Pattern::compile, Pattern[]::new);
		return regexMultipleOf(inverted, patterns);
	}
	
	/**
	 * Generates a filter to capture the items that meet that constraint.
	 * The only difference with the {@link #regexOf(String)} method is that it contains multiple constraints.
	 *
	 * @param regex regular expression to apply
	 * @param <T>   generic member type
	 * @return the filter instance with the desired behavior
	 */
	public static @NotNull <T extends Member> Predicate<T> regexMultipleOf(@RegExp String @NotNull ... regex) {
		Pattern[] patterns = Arrs.transform(regex, Pattern::compile, Pattern[]::new);
		return regexMultipleOf(patterns);
	}
	
	/**
	 * Generates a filter to capture the items that meet that constraint.
	 * The only difference with the {@link #regexOf(String)} method is that it contains multiple constraints.
	 *
	 * @param inverted option to perform the action inverted
	 * @param patterns regular expression object to apply
	 * @param <T>      generic member type
	 * @return the filter instance with the desired behavior
	 */
	public static @NotNull <T extends Member> Predicate<T> regexMultipleOf(boolean inverted, Pattern @NotNull ... patterns) {
		return (it) -> {
			boolean result = true;
			for (Pattern pattern : patterns) {
				Matcher matcher = pattern.matcher(it.getName());
				if (inverted == matcher.find()) {
					result = false;
					break;
				}
			}
			
			return result;
		};
	}
	
	/**
	 * Generates a filter to capture the items that meet that constraint.
	 * The only difference with the {@link #regexOf(String)} method is that it contains multiple constraints.
	 *
	 * @param patterns regular expression object to apply
	 * @param <T>      generic member type
	 * @return the filter instance with the desired behavior
	 */
	public static @NotNull <T extends Member> Predicate<T> regexMultipleOf(Pattern @NotNull ... patterns) {
		return regexMultipleOf(false, patterns);
	}
	
	/* ---------------------------------------------------------
	 * Exclusion methods
	 * --------------------------------------------------------- */
	
	/**
	 * Generates a filter to capture elements that are different from the names passed as parameters.
	 *
	 * @param methods all the names you want to exclude
	 * @param <T>     generic member type
	 * @return the filter instance with the desired behavior
	 */
	public static <T extends Member> @NotNull Predicate<T> excludeAll(String @NotNull ... methods) {
		return (it) -> !Arrs.contains(methods, it.getName());
	}
	
	/**
	 * Generates a filter to check if a field is valid within a class
	 *
	 * @return the filter instance with the desired behavior
	 */
	public static @NotNull Predicate<Field> validField() {
		return (it) -> !Arrs.contains(INVALID_RETURN_TYPES, it.getType());
	}
	
	/**
	 * Generates a filter to check if a method is valid inside a class (also checks if it is a getter)
	 *
	 * @return the filter instance with the desired behavior
	 */
	public static @NotNull Predicate<Method> validGetterMethod() {
		return (it) -> it.getParameterCount() == 0 &&
					   !Arrs.contains(INVALID_RETURN_TYPES, it.getReturnType());
	}
	
	/* ---------------------------------------------------------
	 * Require methods
	 * --------------------------------------------------------- */
	
	/**
	 * Generates a filter to check if the object contains one or more annotations.
	 *
	 * @param inverted    option to perform the action inverted
	 * @param annotations the annotations that are required
	 * @param <T>         generic accessible type
	 * @return the filter instance with the desired behavior
	 */
	@SafeVarargs
	public static <T extends AccessibleObject> @NotNull Predicate<T> requireAnnotations(boolean inverted,
		Class<? extends Annotation> @NotNull ... annotations) {
		return (it) -> {
			boolean result = true;
			for (Class<? extends Annotation> clsAnnotation : annotations) {
				if (!inverted == it.isAnnotationPresent(clsAnnotation)) {
					result = false;
					break;
				}
			}
			
			return result;
		};
	}
	
	/**
	 * Generates a filter to check if the object contains one or more annotations.
	 *
	 * @param annotations the annotations that are required
	 * @param <T>         generic accessible type
	 * @return the filter instance with the desired behavior
	 */
	@SafeVarargs
	public static <T extends AccessibleObject> @NotNull Predicate<T> requireAnnotations(
		Class<? extends Annotation> @NotNull ... annotations) {
		return requireAnnotations(false, annotations);
	}
	
}
