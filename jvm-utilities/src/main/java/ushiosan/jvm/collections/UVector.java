package ushiosan.jvm.collections;

import org.jetbrains.annotations.NotNull;
import ushiosan.jvm.UObject;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import java.util.function.Function;

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
		UObject.requireNotNull(base, "base");
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
		UObject.requireNotNull(iterator, "iterator");
		return makeImpl(new Vector<>(), iterator);
	}
	
	/* -----------------------------------------------------
	 * Transform methods
	 * ----------------------------------------------------- */
	
	/**
	 * Converts one vector to another but with a different data type.
	 *
	 * @param original the original vector that you want to convert
	 * @param mapper   function in charge of transforming each element of the vector
	 * @param <T>      the original data type
	 * @param <R>      the target data type
	 * @return the new vector with the converted data
	 */
	public static <T, R> @NotNull Vector<R> transform(@NotNull Vector<T> original, @NotNull Function<T, R> mapper) {
		UObject.requireNotNull(original, "original");
		UObject.requireNotNull(mapper, "mapper");
		// Generate vector result
		return original.stream()
			.map(mapper)
			.collect(Vector::new, Vector::add, Vector::addAll);
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
	private static <T> @NotNull Vector<T> makeImpl(T @NotNull ... elements) {
		Vector<T> result = new Vector<>(measureSize(elements.length));
		for (T it : elements) {
			result.addElement(it);
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
		elements.forEachRemaining(vector::addElement);
		return vector;
	}
	
}
