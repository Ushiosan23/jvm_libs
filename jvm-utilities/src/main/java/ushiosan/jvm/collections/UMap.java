package ushiosan.jvm.collections;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;
import ushiosan.jvm.content.UPair;

import java.util.*;

import static ushiosan.jvm.UObject.cast;

public class UMap {
	
	/**
	 * This class cannot be instantiated directly
	 */
	private UMap() {}
	
	/* -----------------------------------------------------
	 * Make methods
	 * ----------------------------------------------------- */
	
	/**
	 * Create a read-only map with all the given elements.
	 *
	 * @param entries the elements to insert
	 * @param <K>     key entry type
	 * @param <V>     value entry type
	 * @return a read-only map with all content
	 */
	@Contract(pure = true)
	@SafeVarargs
	public static <K, V> @UnmodifiableView @NotNull Map<K, V> make(Map.Entry<K, V> @NotNull ... entries) {
		return Collections.unmodifiableMap(makeMutable(entries));
	}
	
	/**
	 * Create a read-only map with all the given elements.
	 *
	 * @param pairs the elements to insert
	 * @param <K>   key entry type
	 * @param <V>   value entry type
	 * @return a read-only map with all content
	 */
	@Contract(pure = true)
	@SafeVarargs
	public static <K, V> @UnmodifiableView @NotNull Map<K, V> make(UPair<K, V> @NotNull ... pairs) {
		return Collections.unmodifiableMap(makeMutable(pairs));
	}
	
	/* -----------------------------------------------------
	 * Make mutable methods
	 * ----------------------------------------------------- */
	
	/**
	 * Create a mutable map with all the given elements.
	 *
	 * @param entries the elements to insert
	 * @param <K>     key entry type
	 * @param <V>     value entry type
	 * @return a mutable map with all content
	 */
	@SafeVarargs
	public static <K, V> @NotNull Map<K, V> makeMutable(Map.Entry<K, V> @NotNull ... entries) {
		return makeImpl(new HashMap<>(entries.length), entries);
	}
	
	/**
	 * Create a mutable map with all the given elements.
	 *
	 * @param pairs the elements to insert
	 * @param <K>   key entry type
	 * @param <V>   value entry type
	 * @return a mutable map with all content
	 */
	@SafeVarargs
	public static <K, V> @NotNull Map<K, V> makeMutable(UPair<K, V> @NotNull ... pairs) {
		return makeImpl(new HashMap<>(pairs.length), pairs);
	}
	
	/* -----------------------------------------------------
	 * Make weak methods
	 * ----------------------------------------------------- */
	
	/**
	 * Create a mutable map with all the given elements.
	 *
	 * @param entries the elements to insert
	 * @param <K>     key entry type
	 * @param <V>     value entry type
	 * @return a mutable map with all content
	 */
	@SafeVarargs
	public static <K, V> @NotNull Map<K, V> makeWeak(Map.Entry<K, V> @NotNull ... entries) {
		return makeImpl(new WeakHashMap<>(entries.length), entries);
	}
	
	/**
	 * Create a mutable map with all the given elements.
	 *
	 * @param pairs the elements to insert
	 * @param <K>   key entry type
	 * @param <V>   value entry type
	 * @return a mutable map with all content
	 */
	@SafeVarargs
	public static <K, V> @NotNull Map<K, V> makeWeak(UPair<K, V> @NotNull ... pairs) {
		return makeImpl(new WeakHashMap<>(pairs.length), pairs);
	}
	
	/* -----------------------------------------------------
	 * Make weak methods
	 * ----------------------------------------------------- */
	
	/**
	 * Create a mutable map with all the given elements.
	 *
	 * @param entries the elements to insert
	 * @param <K>     key entry type
	 * @param <V>     value entry type
	 * @return a mutable map with all content
	 */
	@SafeVarargs
	public static <K, V> @NotNull Map<K, V> makeLinked(Map.Entry<K, V> @NotNull ... entries) {
		return makeImpl(new LinkedHashMap<>(entries.length), entries);
	}
	
	/**
	 * Create a mutable map with all the given elements.
	 *
	 * @param pairs the elements to insert
	 * @param <K>   key entry type
	 * @param <V>   value entry type
	 * @return a mutable map with all content
	 */
	@SafeVarargs
	public static <K, V> @NotNull Map<K, V> makeLinked(UPair<K, V> @NotNull ... pairs) {
		return makeImpl(new LinkedHashMap<>(pairs.length), pairs);
	}
	
	/* -----------------------------------------------------
	 * Internal methods
	 * ----------------------------------------------------- */
	
	/**
	 * Create a map with all the given elements.
	 *
	 * @param entries the elements to insert
	 * @param <K>     key entry type
	 * @param <V>     value entry type
	 * @return a mutable map with all content
	 */
	@SafeVarargs
	private static <K, V> @NotNull Map<K, V> makeImpl(@NotNull Map<K, V> mutableMap, Map.Entry<K, V> @NotNull ... entries) {
		UPair<K, V>[] transform = cast(UArray.transform(entries, UPair::copyOf, UPair[]::new));
		return makeImpl(mutableMap, transform);
	}
	
	/**
	 * Create a map with all the given elements.
	 *
	 * @param pairs the elements to insert
	 * @param <K>   key entry type
	 * @param <V>   value entry type
	 * @return a mutable map with all content
	 */
	@SafeVarargs
	private static <K, V> @NotNull Map<K, V> makeImpl(@NotNull Map<K, V> mutableMap, UPair<K, V> @NotNull ... pairs) {
		for (var entry : pairs) {
			mutableMap.put(entry.first, entry.second);
		}
		return mutableMap;
	}
	
}