package com.github.ushiosan23.jvm.collections;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.*;

public final class Containers {

	/* ------------------------------------------------------------------
	 * Constructors
	 * ------------------------------------------------------------------ */

	/**
	 * This class cannot be instantiated
	 */
	private Containers() {
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
	public static <T> @NotNull @Unmodifiable List<T> listOf(T... items) {
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
	public static <T> @NotNull List<T> mutableListOf(T... items) {
		return new ArrayList<>(Arrays.asList(items));
	}

	/**
	 * Create an immutable set with all the given elements.
	 *
	 * @param items Elements to insert
	 * @param <T>   Set generic type
	 * @return Return an immutable set with all elements
	 */
	@SafeVarargs
	public static <T> @Unmodifiable @NotNull Set<T> setOf(T... items) {
		return Set.of(items);
	}

	/**
	 * Create a mutable set with all the given elements.
	 *
	 * @param items Elements to insert
	 * @param <T>   Set generic type
	 * @return Return a mutable set with all elements
	 */
	@SafeVarargs
	@Contract("_ -> new")
	public static <T> @NotNull Set<T> mutableSetOf(T... items) {
		return new HashSet<>(Arrays.asList(items));
	}

	/**
	 * Generate an immutable map with all elements inside.
	 *
	 * @param elements Target elements to insert
	 * @param <K>      Generic key type
	 * @param <V>      Generic value type
	 * @return Returns an immutable map with all elements
	 */
	@SafeVarargs
	@UnmodifiableView
	public static <K, V> @NotNull Map<K, V> mapOf(Map.Entry<K, V>... elements) {
		return Collections.unmodifiableMap(mutableMapOf(elements));
	}

	/**
	 * Generate a mutable map with all elements inside.
	 *
	 * @param elements Target elements to insert
	 * @param <K>      Generic key type
	 * @param <V>      Generic value type
	 * @return Returns a mutable map with all elements
	 */
	@Contract(pure = true)
	@SafeVarargs
	public static <K, V> @NotNull Map<K, V> mutableMapOf(Map.Entry<K, V> @NotNull ... elements) {
		// Generate result
		Map<K, V> result = new HashMap<>(elements.length);

		// Insert values
		for (Map.Entry<K, V> entry : elements) {
			if (entry != null)
				result.put(entry.getKey(), entry.getValue());
		}

		return result;
	}

	/**
	 * Generate an immutable map entry with the selected information.
	 *
	 * @param key   The key to the map entry
	 * @param value The value to the map entry
	 * @param <K>   Generic key type
	 * @param <V>   Generic value type
	 * @return Returns a valid map entry with the selected information
	 */
	@Contract(value = "_, _ -> new", pure = true)
	public static <K, V> Map.@UnmodifiableView @NotNull Entry<K, V> entry(K key, V value) {
		return new AbstractMap.SimpleImmutableEntry<>(key, value);
	}

	/**
	 * Generate a mutable map entry with the selected information.
	 *
	 * @param key   The key to the map entry
	 * @param value The value to the map entry
	 * @param <K>   Generic key type
	 * @param <V>   Generic value type
	 * @return Returns a valid map entry with the selected information
	 */
	@Contract(value = "_, _ -> new", pure = true)
	public static <K, V> Map.@NotNull Entry<K, V> mutableEntry(K key, V value) {
		return new AbstractMap.SimpleEntry<>(key, value);
	}

}
