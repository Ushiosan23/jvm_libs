package ushiosan.jvm.collections;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import static ushiosan.jvm.UObject.requireNotNull;

public final class UVector extends UCollection {
	
	/**
	 * This class cannot be instantiated directly
	 */
	private UVector() {}
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Create a vector with all given elements.
	 *
	 * @param elements the elements to insert
	 * @param <T>      generic type list
	 * @return a linked list with all elements
	 */
	@SafeVarargs
	public static <T> @NotNull Vector<T> make(T @NotNull ... elements) {
		return makeImpl(elements);
	}
	
	/**
	 * Create a vector with all given elements.
	 *
	 * @param base the base collection content
	 * @param <T>  generic type list
	 * @return a linked list with all elements
	 */
	public static <T> @NotNull Vector<T> make(@NotNull Collection<T> base) {
		requireNotNull(base, "base");
		return new Vector<>(base);
	}
	
	/**
	 * Create a vector with all given elements.
	 *
	 * @param iterator the base collection content
	 * @param <T>      generic type list
	 * @return a linked list with all elements
	 */
	public static <T> @NotNull Vector<T> make(@NotNull Iterator<T> iterator) {
		requireNotNull(iterator, "iterator");
		return makeImpl(new Vector<>(), iterator);
	}
	
	/* -----------------------------------------------------
	 * Internal methods
	 * ----------------------------------------------------- */
	
	/**
	 * Create a vector with all given elements.
	 *
	 * @param elements the elements to insert
	 * @param <T>      generic type list
	 * @return a mutable list with all elements
	 */
	@SafeVarargs
	@SuppressWarnings({"UseBulkOperation", "ManualArrayToCollectionCopy"})
	private static <T> @NotNull Vector<T> makeImpl(T @NotNull ... elements) {
		Vector<T> result = new Vector<>(elements.length);
		for (T it : elements) {
			result.add(it);
		}
		return result;
	}
	
	/**
	 * Create a vector with all given elements.
	 *
	 * @param elements the elements to insert
	 * @param <T>      generic type list
	 * @return a mutable list with all elements
	 */
	private static <T> @NotNull Vector<T> makeImpl(@NotNull Vector<T> vector, @NotNull Iterator<T> elements) {
		elements.forEachRemaining(vector::add);
		return vector;
	}
	
}
