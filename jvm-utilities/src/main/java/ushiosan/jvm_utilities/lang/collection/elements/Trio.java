package ushiosan.jvm_utilities.lang.collection.elements;

import org.jetbrains.annotations.NotNull;

/**
 * Data structure that represents 3 values within the same object.
 * This class is very similar to what we can find with class {@link Pair}
 *
 * @param <T> first value type
 * @param <K> second value type
 * @param <V> third value type
 * @see Pair
 */
public final class Trio<T, K, V> extends Pair<T, K> {
	
	/* ---------------------------------------------------------
	 * Properties
	 * --------------------------------------------------------- */
	
	/**
	 * Third element
	 */
	public final V third;
	
	/* ---------------------------------------------------------
	 * Constructors
	 * --------------------------------------------------------- */
	
	/**
	 * Default constructor
	 *
	 * @param first  first element
	 * @param second second element
	 * @param third  third element
	 */
	public Trio(T first, K second, V third) {
		super(first, second);
		this.third = third;
	}
	
	/* ---------------------------------------------------------
	 * Static methods
	 * --------------------------------------------------------- */
	
	/**
	 * Shortcut trio instance
	 *
	 * @param first  first value
	 * @param second second value
	 * @param third  third value
	 * @param <T>    generic first type
	 * @param <K>    generic second type
	 * @param <V>    generic third type
	 * @return instance of {@link Trio} class
	 */
	public static <T, K, V> @NotNull Trio<T, K, V> of(T first, K second, V third) {
		return new Trio<>(first, second, third);
	}
	
	/**
	 * Copies the content of a {@link Pair} object and converts it to a {@link Trio} object.
	 *
	 * @param pair the pair object to copy
	 * @param <T>  generic first type
	 * @param <K>  generic second type
	 * @param <V>  generic third type
	 * @return instance of {@link Trio} class
	 */
	public static <T, K, V> @NotNull Trio<T, K, V> of(@NotNull Pair<T, K> pair) {
		return of(pair.first, pair.second, null);
	}
	
}
