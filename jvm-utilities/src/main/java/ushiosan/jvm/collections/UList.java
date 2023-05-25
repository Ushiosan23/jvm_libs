package ushiosan.jvm.collections;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;

import static ushiosan.jvm.UObject.requireNotNull;

public final class UList extends UCollection {
	
	/**
	 * This class cannot be instantiated
	 */
	private UList() {}
	
	/* -----------------------------------------------------
	 * Make methods
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
	public static @Unmodifiable <T> List<T> make(T @NotNull ... elements) {
		return List.of(elements);
	}
	
	/**
	 * Create an immutable list with all given elements.
	 *
	 * @param base the base collection content
	 * @param <T>  generic type list
	 * @return an immutable list with all elements
	 */
	public static @Unmodifiable <T> List<T> make(@NotNull Collection<T> base) {
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
	public static @Unmodifiable <T> @NotNull List<T> make(@NotNull Iterator<T> iterator) {
		return Collections.unmodifiableList(makeMutable(iterator));
	}
	
	/* -----------------------------------------------------
	 * Make mutable methods
	 * ----------------------------------------------------- */
	
	/**
	 * Create a mutable list with all given elements.
	 *
	 * @param elements the elements to insert
	 * @param <T>      generic type list
	 * @return a mutable list with all elements
	 */
	@SafeVarargs
	@Contract("_ -> new")
	public static <T> @NotNull List<T> makeMutable(T @NotNull ... elements) {
		return makeImpl(new ArrayList<>(elements.length), elements);
	}
	
	/**
	 * Create a mutable list with all given elements.
	 *
	 * @param base the base collection content
	 * @param <T>  generic type list
	 * @return a mutable list with all elements
	 */
	public static <T> @NotNull List<T> makeMutable(@NotNull Collection<T> base) {
		requireNotNull(base, "base");
		return makeImpl(new ArrayList<>(base.size()), base.iterator());
	}
	
	/**
	 * Create a mutable list with all given elements.
	 *
	 * @param iterator the base collection content
	 * @param <T>      generic type list
	 * @return a mutable list with all elements
	 */
	public static <T> @NotNull List<T> makeMutable(@NotNull Iterator<T> iterator) {
		requireNotNull(iterator, "iterator");
		return makeImpl(new ArrayList<>(), iterator);
	}
	
	/* -----------------------------------------------------
	 * Make linked methods
	 * ----------------------------------------------------- */
	
	/**
	 * Create a linked list with all given elements.
	 *
	 * @param elements the elements to insert
	 * @param <T>      generic type list
	 * @return a linked list with all elements
	 */
	@SafeVarargs
	public static <T> @NotNull List<T> makeLinked(T @NotNull ... elements) {
		return makeImpl(new LinkedList<>(), elements);
	}
	
	/**
	 * Create a linked list with all given elements.
	 *
	 * @param base the base collection content
	 * @param <T>  generic type list
	 * @return a linked list with all elements
	 */
	public static <T> @NotNull List<T> makeLinked(@NotNull Collection<T> base) {
		requireNotNull(base, "base");
		return new LinkedList<>(base);
	}
	
	/**
	 * Create a linked list with all given elements.
	 *
	 * @param iterator the base collection content
	 * @param <T>      generic type list
	 * @return a linked list with all elements
	 */
	public static <T> @NotNull List<T> makeLinked(@NotNull Iterator<T> iterator) {
		requireNotNull(iterator, "iterator");
		return makeImpl(new LinkedList<>(), iterator);
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
	public static <T> @NotNull List<Integer> searchIndexes(@NotNull List<T> base, @Nullable T search) {
		requireNotNull(base, "base");
		List<Integer> indexList = makeMutable();
		
		// Iterate all elements
		for (int i = 0; i < base.size(); i++) {
			T item = base.get(i);
			// Check element
			if (Objects.equals(search, item)) indexList.add(i);
		}
		
		return indexList;
	}
	
	/* -----------------------------------------------------
	 * Internal methods
	 * ----------------------------------------------------- */
	
	/**
	 * Create a mutable list with all given elements.
	 *
	 * @param mutableList base mutable list
	 * @param elements    the elements to insert
	 * @param <T>         generic type list
	 * @return a mutable list with all elements
	 */
	@SafeVarargs
	@SuppressWarnings({"UseBulkOperation", "ManualArrayToCollectionCopy"})
	private static <T> @NotNull List<T> makeImpl(@NotNull List<T> mutableList, T @NotNull ... elements) {
		for (T it : elements) {
			mutableList.add(it);
		}
		return mutableList;
	}
	
	/**
	 * Create a mutable list with all given elements.
	 *
	 * @param mutableList base mutable list
	 * @param elements    the elements to insert
	 * @param <T>         generic type list
	 * @return a mutable list with all elements
	 */
	private static <T> @NotNull List<T> makeImpl(@NotNull List<T> mutableList, @NotNull Iterator<T> elements) {
		elements.forEachRemaining(mutableList::add);
		return mutableList;
	}
	
}
