package ushiosan.jvm.content;

import org.jetbrains.annotations.NotNull;

import static ushiosan.jvm.UObject.requireNotNull;

public class UTrio<F, S, T> extends UPair<F, S> {
	
	/* ---------------------------------------------------------
	 * Properties
	 * --------------------------------------------------------- */
	
	/**
	 * Third element
	 */
	public final T third;
	
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
	public UTrio(F first, S second, T third) {
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
	 * @param <F>    generic first type
	 * @param <S>    generic second type
	 * @param <T>    generic third type
	 * @return instance of {@link UTrio} class
	 */
	public static <F, S, T> @NotNull UTrio<F, S, T> make(F first, S second, T third) {
		return new UTrio<>(first, second, third);
	}
	
	/**
	 * Copies the content of a {@link UPair} object and converts it to a {@link UTrio} object.
	 *
	 * @param uPair the pair object to copy
	 * @param <F>   generic first type
	 * @param <S>   generic second type
	 * @param <T>   generic third type
	 * @return instance of {@link UTrio} class
	 */
	public static <F, S, T> @NotNull UTrio<F, S, T> make(@NotNull UPair<F, S> uPair) {
		requireNotNull(uPair, "uPair");
		return make(uPair.first, uPair.second, null);
	}
	
}
