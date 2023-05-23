package ushiosan.jvm.collections;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;

import static ushiosan.jvm.UObject.requireNotNull;

public final class USet extends UCollection {
	
	/**
	 * This class cannot be instantiated
	 */
	private USet() {}
	
	/* -----------------------------------------------------
	 * Generator methods
	 * ----------------------------------------------------- */
	
	/**
	 * Create a set with all given elements.
	 *
	 * @param elements the elements to insert
	 * @param <T>      generic type set
	 * @return a set with all elements
	 */
	@SafeVarargs
	public static @Unmodifiable <T> @NotNull Set<T> setOf(T @NotNull ... elements) {
		return Set.of(elements);
	}
	
	/**
	 * Create a set with all given elements.
	 *
	 * @param base the base collection content
	 * @param <T>  generic type set
	 * @return a set with all elements
	 */
	@SuppressWarnings("unchecked")
	public static @Unmodifiable <T> @NotNull Set<T> setOf(@NotNull Collection<T> base) {
		requireNotNull(base, "base");
		// The "Set.copyOf(Collection)" method is not used because it performs unnecessary procedures
		// (it generates a list object and converts it to an array again), and this library
		// wants to avoid that.
		return (Set<T>) Set.of(base.toArray());
	}
	
	/**
	 * Create a mutable set with all given elements.
	 *
	 * @param elements the elements to insert
	 * @param <T>      generic type set
	 * @return a set with all elements
	 */
	@SafeVarargs
	@SuppressWarnings({"ManualArrayToCollectionCopy", "UseBulkOperation"})
	public static <T> @NotNull Set<T> mutableSetOf(T @NotNull ... elements) {
		HashSet<T> result = new HashSet<>(elements.length);
		// prioritize speed instead of copying items to another list
		for (T it : elements) {
			result.add(it);
		}
		// Only return the `result` object
		return result;
	}
	
	/**
	 * Create a mutable set with all given elements.
	 *
	 * @param base the base collection content
	 * @param <T>  generic type set
	 * @return a set with all elements
	 */
	public static <T> @NotNull Set<T> mutableSetOf(@NotNull Collection<T> base) {
		requireNotNull(base, "base");
		HashSet<T> result = new HashSet<>(base.size());
		result.addAll(base);
		// Only return the `result` object
		return result;
	}
	
	/**
	 * Create a linked set with all given elements.
	 *
	 * @param elements the elements to insert
	 * @param <T>      generic type set
	 * @return a linked set with all elements
	 */
	@SuppressWarnings({"ManualArrayToCollectionCopy", "UseBulkOperation"})
	@SafeVarargs
	public static <T> @NotNull Set<T> linkedSetOf(T @NotNull ... elements) {
		Set<T> result = new LinkedHashSet<>(elements.length);
		// prioritize speed instead of copying items to another list
		for (T it : elements) {
			result.add(it);
		}
		// Only return the `result` object
		return result;
	}
	
	/**
	 * Create a linked set with all given elements.
	 *
	 * @param base the base collection content
	 * @param <T>  generic type set
	 * @return a linked set with all elements
	 */
	public static <T> @NotNull Set<T> linkedSetOf(@NotNull Collection<T> base) {
		requireNotNull(base, "base");
		Set<T> result = new LinkedHashSet<>(base.size());
		result.addAll(base);
		// Only return the `result` object
		return result;
	}
	
	/**
	 * Create a tree set with all given elements.
	 *
	 * @param comparator the set comparator (used to sort the elements)
	 * @param elements   the elements to insert
	 * @param <T>        generic type set
	 * @return a tree set with all elements
	 */
	@SafeVarargs
	@SuppressWarnings({"ManualArrayToCollectionCopy", "UseBulkOperation"})
	public static <T> @NotNull Set<T> treeSetOf(@Nullable Comparator<T> comparator, T @NotNull ... elements) {
		Set<T> result = new TreeSet<>(comparator);
		// prioritize speed instead of copying items to another list
		for (T it : elements) {
			result.add(it);
		}
		// Only return the `result` object
		return result;
	}
	
	/**
	 * Create a tree set with all given elements.
	 *
	 * @param elements the elements to insert
	 * @param <T>      generic type set
	 * @return a linked set with all elements
	 */
	@SafeVarargs
	public static <T> @NotNull Set<T> treeSetOf(T @NotNull ... elements) {
		return treeSetOf(null, elements);
	}
	
	/**
	 * Create a linked set with all given elements.
	 *
	 * @param comparator sort comparator instance
	 * @param base       the base collection content
	 * @param <T>        generic type set
	 * @return a linked set with all elements
	 */
	public static <T> @NotNull Set<T> treeSetOf(@Nullable Comparator<T> comparator, @NotNull Collection<T> base) {
		requireNotNull(base, "base");
		Set<T> result = new TreeSet<>(comparator);
		result.addAll(base);
		// Only return the `result` object
		return result;
	}
	
	/**
	 * Create an enum set with all enum elements.
	 *
	 * @param cls the enum class to check
	 * @param <T> generic enum type
	 * @return an enum set with all enum elements
	 */
	public static <T extends Enum<T>> @NotNull Set<T> enumSetOf(@NotNull Class<T> cls) {
		requireNotNull(cls, "cls");
		return EnumSet.allOf(cls);
	}
	
	/* -----------------------------------------------------
	 * Join Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Combine different sets into a single collection
	 *
	 * @param sets the sets you want to merge
	 * @param <T>  generic collection type
	 * @return returns a single collection with all the elements of the passed sets
	 */
	@SafeVarargs
	public static <T> @NotNull Set<T> combine(Set<T> @NotNull ... sets) {
		Set<T> tmpResult = USet.mutableSetOf();
		
		// Iterate all collections
		for (Set<T> lt : sets) {
			tmpResult.addAll(lt);
		}
		return tmpResult;
	}
	
}
