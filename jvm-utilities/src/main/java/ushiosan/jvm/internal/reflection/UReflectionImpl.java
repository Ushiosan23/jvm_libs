package ushiosan.jvm.internal.reflection;

import org.intellij.lang.annotations.MagicConstant;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;
import ushiosan.jvm.UClass;
import ushiosan.jvm.UNumber;
import ushiosan.jvm.collections.UArray;
import ushiosan.jvm.collections.UList;
import ushiosan.jvm.collections.USet;
import ushiosan.jvm.content.UPair;
import ushiosan.jvm.function.UFun;
import ushiosan.jvm.internal.validators.UReflectionValidator;
import ushiosan.jvm.reflection.UReflectionOptions;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ushiosan.jvm.UObject.canCast;
import static ushiosan.jvm.UObject.cast;
import static ushiosan.jvm.UObject.requireNotNull;

public abstract class UReflectionImpl extends UReflectionValidator {
	
	/* -----------------------------------------------------
	 * Properties
	 * ----------------------------------------------------- */
	
	/**
	 * Pair of elements that are used to filter elements depending
	 * on the selected configuration.
	 */
	protected static final UPair<UFun.UFun1<Boolean, UReflectionOptions<? extends Member>>, Predicate<? extends Member>>[]
		MEMBER_FILTER_ARRAY = UArray.of(
		UPair.of(UReflectionOptions::publicAccess, modifiers(Modifier.PUBLIC)),
		UPair.of(UReflectionOptions::skipAbstract, modifiers(true, Modifier.ABSTRACT)));
	
	/* -----------------------------------------------------
	 * Filter members
	 * ----------------------------------------------------- */
	
	/**
	 * Filters the methods of a class depending on the filters passed within the configuration
	 * passed as a parameter.
	 *
	 * @param cls     the class where you want to search for class members
	 * @param options search options
	 * @return the methods found based on the filters passed.
	 */
	public static Method @NotNull [] filterMethods(@NotNull Class<?> cls, @NotNull UReflectionOptions<Method> options) {
		requireNotNull(cls, "cls");
		requireNotNull(options, "options");
		// Temporal variables
		Stack<Class<?>> classStack = UClass.classStack(cls, options.maxDeepRecursive());
		Set<Method> methodContainer = USet.mutableSetOf();
		
		// Iterate all classes
		for (Class<?> clsItem : classStack) {
			var clsMethods = options.recursive() ? UList.<Method>listOf() :
							 filterMembers(clsItem.getMethods(), options);
			var clsDeclaredMethods = filterMembers(clsItem.getDeclaredMethods(),
												   options);
			
			// Combine all methods (ignore equal methods)
			Set<Method> uniqueMethods = cast(USet.combine(clsMethods, clsDeclaredMethods));
			methodContainer = cast(USet.combine(methodContainer, uniqueMethods));
		}
		
		// Generate array
		return methodContainer
			.toArray(Method[]::new);
	}
	
	/**
	 * Filters the fields of a class depending on the filters passed within the configuration
	 * passed as a parameter.
	 *
	 * @param cls     the class where you want to search for class members
	 * @param options search options
	 * @return the fields found based on the filters passed.
	 */
	public static Field @NotNull [] filterFields(@NotNull Class<?> cls, @NotNull UReflectionOptions<Field> options) {
		requireNotNull(cls, "cls");
		requireNotNull(options, "options");
		// Temporal variables
		Stack<Class<?>> classStack = UClass.classStack(cls, options.maxDeepRecursive());
		Set<Field> fieldContainer = USet.mutableSetOf();
		
		// Iterate all classes
		for (Class<?> clsItem : classStack) {
			var clsFields = options.recursive() ? UList.<Field>listOf() :
							filterMembers(clsItem.getFields(), options);
			var clsDeclaredFields = filterMembers(clsItem.getDeclaredFields(),
												  options);
			
			// Combine all fields (ignore equal fields)
			Set<Field> uniqueFields = cast(USet.combine(clsFields, clsDeclaredFields));
			fieldContainer = cast(USet.combine(fieldContainer, uniqueFields));
		}
		
		// Generate array
		return fieldContainer
			.toArray(Field[]::new);
	}
	
	/* -----------------------------------------------------
	 * Regular expression filter members
	 * ----------------------------------------------------- */
	
	/**
	 * Generates a filter to capture the elements that meet said restriction
	 *
	 * @param pattern  regular expression to apply
	 * @param inverted option to perform the action inverted
	 * @param flags    regular expression configuration flags
	 * @param <T>      generic member type
	 * @return the filter instance with the desired behavior
	 * @see Pattern
	 */
	@SuppressWarnings("MagicConstant")
	public static <T extends Member> @NotNull Predicate<T> regexMemberOf(@NotNull @RegExp String pattern, boolean inverted,
		@MagicConstant(flagsFromClass = Pattern.class) int @NotNull ... flags) {
		requireNotNull(pattern, "pattern");
		// Generate pattern instance
		Pattern patternInstance = Pattern.compile(pattern, UNumber.asFlags(flags));
		return regexMemberOf(patternInstance, inverted);
	}
	
	/**
	 * Generates a filter to capture the elements that meet said restriction
	 *
	 * @param pattern regular expression to apply
	 * @param flags   regular expression configuration flags
	 * @param <T>     generic member type
	 * @return the filter instance with the desired behavior
	 * @see Pattern
	 */
	public static <T extends Member> @NotNull Predicate<T> regexMemberOf(@NotNull @RegExp String pattern,
		@MagicConstant(flagsFromClass = Pattern.class) int @NotNull ... flags) {
		return regexMemberOf(pattern, false, flags);
	}
	
	/**
	 * Generates a filter to capture the elements that meet said restriction
	 *
	 * @param pattern  regular expression to apply
	 * @param inverted option to perform the action inverted
	 * @param <T>      generic member type
	 * @return the filter instance with the desired behavior
	 * @see Pattern
	 */
	public static <T extends Member> @NotNull Predicate<T> regexMemberOf(@NotNull Pattern pattern, boolean inverted) {
		requireNotNull(pattern, "pattern");
		return (it) -> {
			Matcher matcher = pattern.matcher(it.getName());
			return inverted != matcher.find();
		};
	}
	
	/**
	 * Generates a filter to capture the elements that meet said restriction
	 *
	 * @param pattern regular expression to apply
	 * @param <T>     generic member type
	 * @return the filter instance with the desired behavior
	 * @see Pattern
	 */
	public static <T extends Member> @NotNull Predicate<T> regexMemberOf(@NotNull Pattern pattern) {
		return regexMemberOf(pattern, false);
	}
	
	/* -----------------------------------------------------
	 * Exclude filter methods
	 * ----------------------------------------------------- */
	
	/**
	 * Only valid to members with the name passed as a parameter
	 *
	 * @param name the name of the member you want to search for
	 * @param <T>  generic member type
	 * @return the filter instance with the desired behavior
	 */
	public static <T extends Member> @NotNull Predicate<T> named(@NotNull String name) {
		requireNotNull(name, "name");
		return it -> name.trim().contentEquals(it.getName());
	}
	
	/**
	 * Excludes all members matching any of the defined names
	 *
	 * @param names the names you want to exclude
	 * @param <T>   generic member type
	 * @return the filter instance with the desired behavior
	 */
	public static <T extends Member> @NotNull Predicate<T> excludeMembers(String @NotNull ... names) {
		return it -> !UArray.contains(names, it.getName());
	}
	
	/**
	 * Validates that a member is valid to get its value. In the case of class fields, it is
	 * verified that their type is not "void" and in the case of methods, it only applies to getter
	 * methods and those that do not return "void" values.
	 *
	 * @param <T> generic member type
	 * @return the filter instance with the desired behavior
	 */
	public static <T extends Member> @NotNull Predicate<T> validObtainMember() {
		return it -> {
			// Verify if a member is a field or method
			if (canCast(it, Field.class)) {
				Field field = cast(it);
				return !UArray.contains(INVALID_GET_TYPES, field.getType());
			}
			if (canCast(it, Method.class)) {
				Method method = cast(it);
				return method.getParameterCount() == 0 &&
					   !UArray.contains(INVALID_GET_TYPES, method.getReturnType());
			}
			return false;
		};
	}
	
	/**
	 * Exclusive method for {@link Field} type members. Used to verify that an element
	 * is of the set type.
	 *
	 * @param type the type you want to parse
	 * @return the filter instance with the desired behavior
	 */
	public static @NotNull Predicate<Field> fieldType(@NotNull Class<?> type) {
		requireNotNull(type, "type");
		return it -> type.equals(it.getType());
	}
	
	/**
	 * Exclusive method for members of type {@link Method}. It is used to verify that an
	 * element requires the passed parameters.
	 *
	 * @param args array of arguments required by the searched method
	 * @return the filter instance with the desired behavior
	 */
	public static @NotNull Predicate<Method> methodTypeParams(Class<?> @NotNull ... args) {
		return it -> UArray.contentEquals(it.getParameterTypes(), args);
	}
	
	/**
	 * Exclusive method for members of type {@link Method}.
	 * It is used to verify that the method returns the desired result.
	 *
	 * @param type data type required for method return
	 * @return the filter instance with the desired behavior
	 */
	public static @NotNull Predicate<Method> methodReturnType(@NotNull Class<?> type) {
		requireNotNull(type, "type");
		return it -> type.equals(it.getReturnType());
	}
	
	/* -----------------------------------------------------
	 * Require filter methods
	 * ----------------------------------------------------- */
	
	/**
	 * Generates a filter to check if the object contains one or more annotations.
	 *
	 * @param inverted    option to perform the action inverted
	 * @param annotations the annotations that are required
	 * @param <T>         generic accessible type
	 * @return the filter instance with the desired behavior
	 */
	@SafeVarargs
	public static <T extends AccessibleObject> @NotNull Predicate<T> annotatedWith(boolean inverted,
		Class<? extends Annotation> @NotNull ... annotations) {
		return it -> {
			// Temporal variables
			boolean result = inverted;
			// Check if an element contains the annotation
			for (var annotation : annotations) {
				if (!it.isAnnotationPresent(annotation)) {
					// All conditions need to be true otherwise the object
					// does not have the necessary requirements
					result = !inverted;
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
	public static <T extends AccessibleObject> @NotNull Predicate<T> annotatedWith(
		Class<? extends Annotation> @NotNull ... annotations) {
		return annotatedWith(false, annotations);
	}
	
	/**
	 * Generates a filter to check if the object contains one or more modifiers.
	 * Modifiers can be individual or plural, where they all have to be true for the
	 * filter to be valid.
	 *
	 * @param inverted  option for the condition to be inverse to the one established
	 * @param modifiers array with all access modifiers
	 * @param <T>       generic accessible type
	 * @return the filter instance with the desired behavior
	 * @see Modifier#PUBLIC
	 * @see Modifier#PRIVATE
	 * @see Modifier#PROTECTED
	 * @see Modifier#STATIC
	 * @see Modifier#FINAL
	 * @see Modifier#SYNCHRONIZED
	 * @see Modifier#VOLATILE
	 * @see Modifier#TRANSIENT
	 * @see Modifier#NATIVE
	 * @see Modifier#INTERFACE
	 * @see Modifier#ABSTRACT
	 * @see Modifier#STRICT
	 */
	public static <T extends Member> @NotNull Predicate<T> modifiers(boolean inverted,
		@MagicConstant(valuesFromClass = Modifier.class) int... modifiers) {
		return (it) -> {
			// Temporal variables
			boolean result = true;
			int memberMods = it.getModifiers();
			
			// Iterate all modifiers
			for (int modifier : modifiers) {
				int operation = memberMods & modifier;
				boolean resultOperation = inverted == (operation != 0);
				
				// Check operation
				if (resultOperation) {
					result = false;
					break;
				}
			}
			
			return result;
		};
	}
	
	/**
	 * Generates a filter to check if the object contains one or more modifiers.
	 * Modifiers can be individual or plural, where they all have to be true for the
	 * filter to be valid.
	 *
	 * @param modifiers array with all access modifiers
	 * @param <T>       generic accessible type
	 * @return the filter instance with the desired behavior
	 * @see Modifier#PUBLIC
	 * @see Modifier#PRIVATE
	 * @see Modifier#PROTECTED
	 * @see Modifier#STATIC
	 * @see Modifier#FINAL
	 * @see Modifier#SYNCHRONIZED
	 * @see Modifier#VOLATILE
	 * @see Modifier#TRANSIENT
	 * @see Modifier#NATIVE
	 * @see Modifier#INTERFACE
	 * @see Modifier#ABSTRACT
	 * @see Modifier#STRICT
	 */
	public static <T extends Member> @NotNull Predicate<T> modifiers(
		@MagicConstant(valuesFromClass = Modifier.class) int... modifiers) {
		return modifiers(false, modifiers);
	}
	
	/* -----------------------------------------------------
	 * Internal methods
	 * ----------------------------------------------------- */
	
	/**
	 * Filters the members of a class depending on the filters passed within the configuration
	 * passed as a parameter.
	 *
	 * @param members all class members you want to filter
	 * @param options search options
	 * @return the methods found based on the filters passed.
	 */
	private static @NotNull <T extends Member> List<T> filterMembers(T @NotNull [] members,
		@NotNull UReflectionOptions<T> options) {
		// Temporal variables
		Stream<T> memberStream = Arrays.stream(members);
		
		// Apply all filters
		filter:
		{
			// Required filters
			for (var filter : MEMBER_FILTER_ARRAY) {
				if (filter.first.invoke(options)) {
					memberStream = memberStream.filter(cast(filter.second));
				}
			}
			if (options.predicates().isEmpty()) break filter;
			
			// Iterate all filters
			for (var filter : options.predicates()) {
				memberStream = memberStream.filter(filter);
			}
		}
		return memberStream
			.collect(Collectors.toUnmodifiableList());
	}
	
}
