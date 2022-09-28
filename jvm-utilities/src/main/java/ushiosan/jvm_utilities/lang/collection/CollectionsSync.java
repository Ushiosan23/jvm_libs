package ushiosan.jvm_utilities.lang.collection;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ushiosan.jvm_utilities.lang.collection.elements.Pair;

public final class CollectionsSync {

	/**
	 * This class cannot be instantiated.
	 * <p>
	 * Singleton or utility class mode.
	 */
	private CollectionsSync() {
	}

	/* -----------------------------------------------------
	 * List methods
	 * ----------------------------------------------------- */

	/**
	 * Create a mutable synchronized collection with all given elements.
	 *
	 * @param base the elements to insert
	 * @param <T>  collection generic type
	 * @return a mutable synchronized collection with all elements
	 */
	public static <T> @NotNull Collection<T> collectionOf(@NotNull Collection<T> base) {
		return Collections.synchronizedCollection(base);
	}

	/**
	 * Create a mutable synchronized list with all given elements.
	 *
	 * @param elements the elements to insert
	 * @param <T>      list generic type
	 * @return a mutable synchronized list with all elements
	 */
	@SafeVarargs
	public static <T> @NotNull List<T> listOf(T @NotNull ... elements) {
		return Collections.synchronizedList(List.of(elements));
	}

	/* -----------------------------------------------------
	 * Set methods
	 * ----------------------------------------------------- */

	/**
	 * Create a mutable synchronized set with all given elements.
	 *
	 * @param elements the elements to insert
	 * @param <T>      list generic type
	 * @return a mutable synchronized set with all elements
	 */
	@SafeVarargs
	public static <T> @NotNull Set<T> setOf(T @NotNull ... elements) {
		return Collections.synchronizedSet(Set.of(elements));
	}

	/* -----------------------------------------------------
	 * Map methods
	 * ----------------------------------------------------- */

	/**
	 * Create a mutable synchronized map with all the given elements.
	 *
	 * @param entries the elements to insert
	 * @param <K>     key entry type
	 * @param <V>     value entry type
	 * @return a mutable synchronized map with all content
	 */
	@SafeVarargs
	public static <K, V> @NotNull Map<K, V> mapOf(Map.Entry<K, V> @NotNull ... entries) {
		final Map<K, V> base = ushiosan.jvm_utilities.lang.collection.Collections
			.mapOf(entries);
		return Collections.synchronizedMap(base);
	}

	/**
	 * Create a mutable synchronized map with all the given elements.
	 *
	 * @param pairs the elements to insert
	 * @param <K>   key entry type
	 * @param <V>   value entry type
	 * @return a mutable synchronized map with all content
	 */
	@SafeVarargs
	public static <K, V> @NotNull Map<K, V> mapOf(Pair<K, V> @NotNull ... pairs) {
		final Map<K, V> base = ushiosan.jvm_utilities.lang.collection.Collections
			.mapOf(pairs);
		return Collections.synchronizedMap(base);
	}

}
