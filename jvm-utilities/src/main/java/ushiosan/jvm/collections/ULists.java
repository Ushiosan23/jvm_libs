package ushiosan.jvm.collections;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;

import static ushiosan.jvm.UObject.requireNotNull;

public final class ULists extends UCollections {
	
	/**
	 * This class cannot be instantiated
	 */
	private ULists() {}
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Create an immutable list with all given elements.
	 *
	 * @param elements the elements to insert
	 * @param <T>      generic type list
	 * @return an immutable list with all elements
	 */
	@SafeVarargs
	@Contract(pure = true)
	public static @Unmodifiable <T> List<T> listOf(T @NotNull ... elements) {
		return List.of(elements);
	}
	
	
	/**
	 * Create an immutable list with all given elements.
	 *
	 * @param base the base collection content
	 * @param <T>  generic type list
	 * @return an immutable list with all elements
	 */
	public static @Unmodifiable <T> List<T> listOf(@NotNull Collection<T> base) {
		requireNotNull(base, "base");
		return List.copyOf(base);
	}
	
	/**
	 * Create an immutable list with all given elements.
	 *
	 * @param iterator the base collection content
	 * @param <T>      generic type list
	 * @return an immutable list with all elements
	 */
	public static @Unmodifiable <T> @NotNull List<T> listOf(@NotNull Iterator<T> iterator) {
		return Collections.unmodifiableList(mutableListOf(iterator));
	}
	
	/**
	 * Create a mutable list with all given elements.
	 *
	 * @param elements the elements to insert
	 * @param <T>      generic type list
	 * @return a mutable list with all elements
	 */
	@SuppressWarnings({"ManualArrayToCollectionCopy", "UseBulkOperation"})
	@SafeVarargs
	@Contract("_ -> new")
	public static <T> @NotNull List<T> mutableListOf(T @NotNull ... elements) {
		List<T> result = new ArrayList<>(elements.length);
		for (T it : elements) {
			result.add(it);
		}
		return result;
	}
	
	/**
	 * Create a mutable list with all given elements.
	 *
	 * @param base the base collection content
	 * @param <T>  generic type list
	 * @return a mutable list with all elements
	 */
	public static <T> @NotNull List<T> mutableListOf(@NotNull Collection<T> base) {
		requireNotNull(base, "base");
		return new ArrayList<>(base);
	}
	
	/**
	 * Create a mutable list with all given elements.
	 *
	 * @param iterator the base collection content
	 * @param <T>      generic type list
	 * @return a mutable list with all elements
	 */
	public static <T> @NotNull List<T> mutableListOf(@NotNull Iterator<T> iterator) {
		requireNotNull(iterator, "iterator");
		// Fill a new mutable list
		List<T> base = new ArrayList<>();
		iterator.forEachRemaining(base::add);
		
		return base;
	}
	
	/**
	 * Create a linked list with all given elements.
	 *
	 * @param elements the elements to insert
	 * @param <T>      generic type list
	 * @return a linked list with all elements
	 */
	@SafeVarargs
	public static <T> @NotNull List<T> linkedListOf(T @NotNull ... elements) {
		return new LinkedList<>(listOf(elements));
	}
	
	/**
	 * Create a linked list with all given elements.
	 *
	 * @param base the base collection content
	 * @param <T>  generic type list
	 * @return a linked list with all elements
	 */
	public static <T> @NotNull List<T> linkedListOf(@NotNull Collection<T> base) {
		return new LinkedList<>(base);
	}
	
	/* -----------------------------------------------------
	 * Search methods
	 * ----------------------------------------------------- */
	
	/**
	 * Returns all indices where the searched element is found
	 *
	 * @param base   the base list
	 * @param search the object to search
	 * @param <T>    generic object type
	 * @return all indices where the searched object is found
	 */
	public static <T> @NotNull List<Integer> searchIndexes(@NotNull List<T> base, @NotNull T search) {
		List<Integer> indexList = mutableListOf();
		
		// Iterate all elements
		for (int i = 0; i < base.size(); i++) {
			T item = base.get(i);
			// Check element
			if (search.equals(item)) indexList.add(i);
		}
		
		return indexList;
	}
	
}
