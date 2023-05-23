package ushiosan.jvm.reflection;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import ushiosan.jvm.UClass;
import ushiosan.jvm.collections.UList;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static ushiosan.jvm.UObject.requireNotNull;

public class UReflectionOptions<T extends Member> {
	
	/* -----------------------------------------------------
	 * Properties
	 * ----------------------------------------------------- */
	
	/**
	 * List of all registered filters
	 */
	private final List<Predicate<T>> predicates;
	
	/**
	 * Property used to check only public members
	 */
	private boolean publicAccess;
	
	/**
	 * Property used to identify members recursively
	 */
	private boolean recursive;
	
	/**
	 * Property used to exclude all abstract elements of
	 * an interface or abstract class
	 */
	private boolean skipAbstract;
	
	/**
	 * Maximum iterations in recursive searches
	 */
	private int maxDeep;
	
	/* -----------------------------------------------------
	 * Constructors
	 * ----------------------------------------------------- */
	
	/**
	 * This class cannot be instantiated directly
	 */
	private UReflectionOptions() {
		publicAccess = true;
		recursive = true;
		skipAbstract = true;
		maxDeep = UClass.FULL_CLASS_STACK;
		predicates = UList.mutableListOf();
	}
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Generates a generic instance of options for use with reflection
	 *
	 * @param <T> genetic member type
	 * @return a generic instance of options
	 */
	@Contract(" -> new")
	public static <T extends Member> @NotNull UReflectionOptions<T> generate() {
		return new UReflectionOptions<>();
	}
	
	/**
	 * Generates a method instance of options for use with reflection
	 *
	 * @return a method instance of options
	 */
	@Contract(" -> new")
	public static @NotNull UReflectionOptions<Method> generateForMethods() {
		return generate();
	}
	
	/**
	 * Generates a field instance of options for use with reflection
	 *
	 * @return a field instance of options
	 */
	@Contract(" -> new")
	public static @NotNull UReflectionOptions<Field> generateForFields() {
		return generate();
	}
	
	/**
	 * Property used to check only public members
	 *
	 * @return {@code true} if you want to access only public elements or {@code false} otherwise
	 */
	public boolean publicAccess() {
		return publicAccess;
	}
	
	/**
	 * Changes the state of public access on the current instance.
	 *
	 * @param publicAccess {@code true} if you want to access only public elements or {@code false} otherwise
	 * @return the same current instance
	 */
	public @NotNull UReflectionOptions<T> setPublicAccessOnly(boolean publicAccess) {
		this.publicAccess = publicAccess;
		return this;
	}
	
	/**
	 * User-defined maximum iterations.
	 * <p>
	 * This method does not take into account if the instance has the
	 * {@link #recursive()} property set to {@code true} and will only return the raw value.
	 *
	 * @return user-defined maximum iterations
	 */
	public int maxDeep() {
		return maxDeep;
	}
	
	/**
	 * User-defined maximum iterations.
	 * <p>
	 * This method takes into account if the instance has the
	 * {@link #recursive()} property marked as true and will return
	 * a different value, depending on the value of said property.
	 *
	 * @return user-defined maximum iterations
	 */
	public int maxDeepRecursive() {
		return recursive ? maxDeep : UClass.ALONE_CLASS_STACK;
	}
	
	/**
	 * Changes the state of deep recursive iterations on the current instance.
	 *
	 * @param maxDeep maximum iterations in recursive searches.
	 *                If the value is {@code 0} then it is considered undefined recursive.
	 * @return the same current instance
	 */
	public @NotNull UReflectionOptions<T> setMaxDeep(int maxDeep) {
		this.maxDeep = Math.max(maxDeep, UClass.FULL_CLASS_STACK);
		return this;
	}
	
	/**
	 * Property used to identify members recursively
	 *
	 * @return user-defined recursive property
	 */
	public boolean recursive() {
		return recursive;
	}
	
	/**
	 * Changes the state of {@code recursive} property on the current instance.
	 *
	 * @param recursive property used to handle recursive iterations in calls with reflection.
	 * @return the same current instance
	 */
	public @NotNull UReflectionOptions<T> setRecursive(boolean recursive) {
		this.recursive = recursive;
		return this;
	}
	
	/**
	 * Property used to exclude all abstract elements of an interface
	 * or abstract class
	 *
	 * @return user-defined {@code skipAbstract} property
	 */
	public boolean skipAbstract() {
		return skipAbstract;
	}
	
	/**
	 * Changes the state of {@code skipAbstract} property on the current instance.
	 *
	 * @param skipAbstract property used to exclude all abstract elements of an interface
	 *                     or abstract class
	 * @return the same current instance
	 */
	public @NotNull UReflectionOptions<T> setSkipAbstract(boolean skipAbstract) {
		this.skipAbstract = skipAbstract;
		return this;
	}
	
	/* -----------------------------------------------------
	 * Static methods
	 * ----------------------------------------------------- */
	
	/**
	 * List with all filters used to perform reflection operations
	 *
	 * @return all predicate elements
	 */
	public @NotNull @Unmodifiable List<Predicate<T>> predicates() {
		return UList.listOf(predicates);
	}
	
	/**
	 * Adds a new filter to the list of already registered filters.
	 *
	 * @param predicate the new filter you want to add
	 * @return the same current instance
	 */
	public @NotNull UReflectionOptions<T> addPredicate(@NotNull Predicate<T> predicate) {
		requireNotNull(predicate, "predicate");
		predicates.add(predicate);
		return this;
	}
	
	/**
	 * Rewrites all the filters that were already registered. Previous filters
	 * are lost once this method is called.
	 *
	 * @param predicates the new filters you want to add
	 * @return the same current instance
	 */
	@SafeVarargs
	public final @NotNull UReflectionOptions<T> setPredicates(Predicate<T> @NotNull ... predicates) {
		// Remove all previous predicates
		this.predicates.clear();
		// Attach new predicates
		Arrays.stream(predicates).forEach(this::addPredicate);
		return this;
	}
	
}
