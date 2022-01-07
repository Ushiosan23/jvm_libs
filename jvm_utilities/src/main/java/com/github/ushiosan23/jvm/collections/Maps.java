package com.github.ushiosan23.jvm.collections;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Maps {

	/* ------------------------------------------------------------------
	 * Constructor
	 * ------------------------------------------------------------------ */

	/**
	 * This class cannot be instantiated
	 */
	private Maps() {
	}

	/* ------------------------------------------------------------------
	 * Methods
	 * ------------------------------------------------------------------ */

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
	public static <K, V> @NotNull Map<K, V> of(Map.Entry<K, V>... elements) {
		return Collections.unmodifiableMap(mutableOf(elements));
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
	public static <K, V> @NotNull Map<K, V> mutableOf(Map.Entry<K, V> @NotNull ... elements) {
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
