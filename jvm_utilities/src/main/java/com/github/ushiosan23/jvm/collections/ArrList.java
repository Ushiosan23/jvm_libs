package com.github.ushiosan23.jvm.collections;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ArrList {

	/* ------------------------------------------------------------------
	 * Constructors
	 * ------------------------------------------------------------------ */

	/**
	 * This class cannot be instantiated
	 */
	private ArrList() {
	}

	/* ------------------------------------------------------------------
	 * Methods
	 * ------------------------------------------------------------------ */

	/**
	 * Create an immutable list with all the given elements.
	 *
	 * @param items Elements to insert
	 * @param <T>   List generic type
	 * @return Return an immutable list with all elements
	 */
	@SafeVarargs
	@Contract(pure = true)
	public static <T> @NotNull @Unmodifiable List<T> of(T... items) {
		return List.of(items);
	}

	/**
	 * Create a mutable list with all the given elements.
	 *
	 * @param items Elements to insert
	 * @param <T>   List generic type
	 * @return Return a mutable list with all elements
	 */
	@SafeVarargs
	@Contract("_ -> new")
	public static <T> @NotNull List<T> mutableOf(T... items) {
		return new ArrayList<>(Arrays.asList(items));
	}

}
