package ushiosan.jvm.collections;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
	public static @Unmodifiable <T> @NotNull Set<T> make(T @NotNull ... elements) {
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
	public static @Unmodifiable <T> @NotNull Set<T> make(@NotNull Collection<T> base) {
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
	public static <T> @NotNull Set<T> makeMutable(T @NotNull ... elements) {
		HashSet<T> result = new HashSet<>(measureSize(elements.length));
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
	public static <T> @NotNull Set<T> makeMutable(@NotNull Collection<T> base) {
		requireNotNull(base, "base");
		HashSet<T> result = new HashSet<>(measureSize(base.size()));
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
	public static <T> @NotNull Set<T> makeLinked(T @NotNull ... elements) {
		Set<T> result = new LinkedHashSet<>(measureSize(elements.length));
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
	public static <T> @NotNull Set<T> makeLinked(@NotNull Collection<T> base) {
		requireNotNull(base, "base");
		Set<T> result = new LinkedHashSet<>(measureSize(base.size()));
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
	public static <T> @NotNull Set<T> makeTree(@Nullable Comparator<T> comparator, T @NotNull ... elements) {
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
	public static <T> @NotNull Set<T> makeTree(T @NotNull ... elements) {
		return makeTree(null, elements);
	}
	
	/**
	 * Create a linked set with all given elements.
	 *
	 * @param comparator sort comparator instance
	 * @param base       the base collection content
	 * @param <T>        generic type set
	 * @return a linked set with all elements
	 */
	public static <T> @NotNull Set<T> makeTree(@Nullable Comparator<T> comparator, @NotNull Collection<T> base) {
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
	public static <T extends Enum<T>> @NotNull Set<T> makeEnum(@NotNull Class<T> cls) {
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
		Set<T> tmpResult = new HashSet<>(measureSize(calculateSize(sets)));
		
		// Iterate all collections
		for (Set<T> lt : sets) {
			tmpResult.addAll(lt);
		}
		return tmpResult;
	}
	
	/* -----------------------------------------------------
	 * Transform methods
	 * ----------------------------------------------------- */
	
	/**
	 * Converts one set to another but with a different data type.
	 *
	 * @param original the original set that you want to convert
	 * @param mapper   function in charge of transforming each element of the set
	 * @param <T>      the original data type
	 * @param <R>      the target data type
	 * @return the new set with the converted data
	 */
	public static <T, R> @NotNull Set<R> transform(@NotNull Set<T> original, @NotNull Function<T, R> mapper) {
		requireNotNull(original, "original");
		requireNotNull(mapper, "mapper");
		// Generate set stream
		var setStream = original.stream()
			.map(mapper);
		
		if (isUnmodifiable(original)) {
			return setStream.collect(Collectors.toUnmodifiableSet());
		} else {
			return setStream.collect(Collectors.toSet());
		}
	}
	
}
