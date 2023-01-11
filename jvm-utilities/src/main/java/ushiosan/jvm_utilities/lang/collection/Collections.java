package ushiosan.jvm_utilities.lang.collection;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import ushiosan.jvm_utilities.lang.collection.elements.Pair;

import java.util.*;

/**
 * Class that has helper methods for data containers like {@link Map}, {@link List},
 * {@link Set}, {@link Stack} and {@link Vector}
 */
public final class Collections {
	
	/**
	 * Empty data pair
	 */
	private static final Pair<?, ?>[] EMPTY_PAIR = new Pair[0];
	
	/**
	 * This class cannot be instantiated.
	 */
	private Collections() {
	}
	
	/* -----------------------------------------------------
	 * List Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Create an immutable list with all given elements.
	 *
	 * @param elements the elements to insert
	 * @param <T>      list generic type
	 * @return an immutable list with all elements
	 */
	@SafeVarargs
	@Contract(pure = true)
	public static @Unmodifiable <T> List<T> listOf(T @NotNull ... elements) {
		return List.of(elements);
	}
	
	/**
	 * Create an immutable list with all given elements.
	 *
	 * @param base the base collection content
	 * @param <T>  list generic type
	 * @return an immutable list with all elements
	 */
	public static @Unmodifiable <T> List<T> listOf(@NotNull Collection<T> base) {
		return List.copyOf(base);
	}
	
	/**
	 * Create an immutable list with all given elements.
	 *
	 * @param iterator the base collection content
	 * @param <T>      list generic type
	 * @return an immutable list with all elements
	 */
	public static @Unmodifiable <T> @NotNull List<T> listOf(@NotNull Iterator<T> iterator) {
		return java.util.Collections.unmodifiableList(mutableListOf(iterator));
	}
	
	/**
	 * Create a mutable list with all given elements.
	 *
	 * @param elements the elements to insert
	 * @param <T>      list generic type
	 * @return a mutable list with all elements
	 */
	@SuppressWarnings({"ManualArrayToCollectionCopy", "UseBulkOperation"})
	@SafeVarargs
	@Contract("_ -> new")
	public static <T> @NotNull List<T> mutableListOf(T @NotNull ... elements) {
		List<T> result = new ArrayList<>(elements.length);
		for (T it : elements) {
			result.add(it);
		}
		return result;
	}
	
	/**
	 * Create a mutable list with all given elements.
	 *
	 * @param base the base collection content
	 * @param <T>  list generic type
	 * @return a mutable list with all elements
	 */
	public static <T> @NotNull List<T> mutableListOf(@NotNull Collection<T> base) {
		return new ArrayList<>(base);
	}
	
	/**
	 * Create a mutable list with all given elements.
	 *
	 * @param iterator the base collection content
	 * @param <T>      list generic type
	 * @return a mutable list with all elements
	 */
	public static <T> @NotNull List<T> mutableListOf(@NotNull Iterator<T> iterator) {
		List<T> base = new ArrayList<>();
		iterator.forEachRemaining(base::add);
		
		return base;
	}
	
	/**
	 * Create a linked list with all given elements.
	 *
	 * @param elements the elements to insert
	 * @param <T>      generic type list
	 * @return a linked list with all elements
	 */
	@SafeVarargs
	public static <T> @NotNull List<T> linkedListOf(T @NotNull ... elements) {
		return new LinkedList<>(listOf(elements));
	}
	
	/**
	 * Create a linked list with all given elements.
	 *
	 * @param base the base collection content
	 * @param <T>  list generic type
	 * @return a linked list with all elements
	 */
	public static <T> @NotNull List<T> linkedListOf(@NotNull Collection<T> base) {
		return new LinkedList<>(base);
	}
	
	/**
	 * Create a stack with all given elements.
	 *
	 * @param elements the elements to insert
	 * @param <T>      generic type list
	 * @return a stack with all elements
	 */
	@SuppressWarnings({"ManualArrayToCollectionCopy", "UseBulkOperation"})
	@SafeVarargs
	public static <T> @NotNull Stack<T> stackOf(T @NotNull ... elements) {
		Stack<T> result = new Stack<>();
		for (T it : elements) {
			result.add(it);
		}
		// Only return the `result` object
		return result;
	}
	
	/**
	 * Create a stack with all given elements.
	 *
	 * @param base the base collection content
	 * @param <T>  list generic type
	 * @return a stack with all elements
	 */
	public static <T> @NotNull Stack<T> stackOf(@NotNull Collection<T> base) {
		Stack<T> result = new Stack<>();
		result.addAll(base);
		// Only return the `result` object
		return result;
	}
	
	/**
	 * Create a vector with all given elements.
	 *
	 * @param elements the elements to insert
	 * @param <T>      generic type list
	 * @return a vector with all elements
	 */
	@SuppressWarnings({"ManualArrayToCollectionCopy", "UseBulkOperation"})
	@SafeVarargs
	public static <T> @NotNull Vector<T> vectorOf(T @NotNull ... elements) {
		Vector<T> result = new Vector<>(elements.length);
		for (T it : elements) {
			result.add(it);
		}
		// Only return the `result` object
		return result;
	}
	
	/**
	 * Create a vector with all given elements.
	 *
	 * @param base the base collection content
	 * @param <T>  list generic type
	 * @return a vector with all elements
	 */
	public static <T> @NotNull Vector<T> vectorOf(@NotNull Collection<T> base) {
		Vector<T> result = new Vector<>(base.size());
		result.addAll(base);
		// Only return the `result` object
		return result;
	}
	
	/* -----------------------------------------------------
	 * Set methods
	 * ----------------------------------------------------- */
	
	/**
	 * Create a set with all given elements.
	 *
	 * @param elements the elements to insert
	 * @param <T>      generic type set
	 * @return a set with all elements
	 */
	@SafeVarargs
	public static @Unmodifiable <T> @NotNull Set<T> setOf(T @NotNull ... elements) {
		return Set.of(elements);
	}
	
	/**
	 * Create a set with all given elements.
	 *
	 * @param base the base collection content
	 * @param <T>  generic type set
	 * @return a set with all elements
	 */
	@SuppressWarnings("unchecked")
	public static @Unmodifiable <T> @NotNull Set<T> setOf(@NotNull Collection<T> base) {
		// The "Set.copyOf(Collection)" method is not used because it performs unnecessary procedures
		// (it generates a list object and converts it to an array again) and this library
		// wants to avoid that.
		return (Set<T>) Set.of(base.toArray());
	}
	
	/**
	 * Create a mutable set with all given elements.
	 *
	 * @param elements the elements to insert
	 * @param <T>      generic type set
	 * @return a set with all elements
	 */
	@SafeVarargs
	@SuppressWarnings({"ManualArrayToCollectionCopy", "UseBulkOperation"})
	public static <T> @NotNull Set<T> mutableSetOf(T @NotNull ... elements) {
		HashSet<T> result = new HashSet<>(elements.length);
		// prioritize speed instead of copying items to another list
		for (T it : elements) {
			result.add(it);
		}
		// Only return the `result` object
		return result;
	}
	
	/**
	 * Create a mutable set with all given elements.
	 *
	 * @param base the base collection content
	 * @param <T>  generic type set
	 * @return a set with all elements
	 */
	public static <T> @NotNull Set<T> mutableSetOf(@NotNull Collection<T> base) {
		HashSet<T> result = new HashSet<>(base.size());
		result.addAll(base);
		// Only return the `result` object
		return result;
	}
	
	/**
	 * Create a linked set with all given elements.
	 *
	 * @param elements the elements to insert
	 * @param <T>      generic type set
	 * @return a linked set with all elements
	 */
	@SuppressWarnings({"ManualArrayToCollectionCopy", "UseBulkOperation"})
	@SafeVarargs
	public static <T> @NotNull Set<T> linkedSetOf(T @NotNull ... elements) {
		Set<T> result = new LinkedHashSet<>(elements.length);
		// prioritize speed instead of copying items to another list
		for (T it : elements) {
			result.add(it);
		}
		// Only return the `result` object
		return result;
	}
	
	/**
	 * Create a linked set with all given elements.
	 *
	 * @param base the base collection content
	 * @param <T>  generic type set
	 * @return a linked set with all elements
	 */
	public static <T> @NotNull Set<T> linkedSetOf(@NotNull Collection<T> base) {
		Set<T> result = new LinkedHashSet<>(base.size());
		result.addAll(base);
		// Only return the `result` object
		return result;
	}
	
	/**
	 * Create a tree set with all given elements.
	 *
	 * @param comparator the set comparator (used to sort the elements)
	 * @param elements   the elements to insert
	 * @param <T>        generic type set
	 * @return a tree set with all elements
	 */
	@SafeVarargs
	@SuppressWarnings({"ManualArrayToCollectionCopy", "UseBulkOperation"})
	public static <T> @NotNull Set<T> treeSetOf(@Nullable Comparator<T> comparator, T @NotNull ... elements) {
		Set<T> result = new TreeSet<>(comparator);
		// prioritize speed instead of copying items to another list
		for (T it : elements) {
			result.add(it);
		}
		// Only return the `result` object
		return result;
	}
	
	/**
	 * Create a tree set with all given elements.
	 *
	 * @param elements the elements to insert
	 * @param <T>      generic type set
	 * @return a linked set with all elements
	 */
	@SafeVarargs
	public static <T> @NotNull Set<T> treeSetOf(T @NotNull ... elements) {
		return treeSetOf(null, elements);
	}
	
	/**
	 * Create a linked set with all given elements.
	 *
	 * @param comparator sort comparator instance
	 * @param base       the base collection content
	 * @param <T>        generic type set
	 * @return a linked set with all elements
	 */
	public static <T> @NotNull Set<T> treeSetOf(@Nullable Comparator<T> comparator, @NotNull Collection<T> base) {
		Set<T> result = new TreeSet<>(comparator);
		result.addAll(base);
		// Only return the `result` object
		return result;
	}
	
	/**
	 * Create an enum set with all enum elements.
	 *
	 * @param cls the enum class to check
	 * @param <T> generic enum type
	 * @return an enum set with all enum elements
	 */
	public static <T extends Enum<T>> @NotNull Set<T> enumSetOf(@NotNull Class<T> cls) {
		return EnumSet.allOf(cls);
	}
	
	/* -----------------------------------------------------
	 * Map methods
	 * ----------------------------------------------------- */
	
	/**
	 * Create a read-only empty map.
	 *
	 * @param <K> key entry type
	 * @param <V> value entry type
	 * @return a read-only map with all content
	 */
	@SuppressWarnings("unchecked")
	public static @Unmodifiable <K, V> @NotNull Map<K, V> mapOf() {
		return mapOf((Pair<K, V>[]) EMPTY_PAIR);
	}
	
	/**
	 * Create a read-only map with all the given elements.
	 *
	 * @param entries the elements to insert
	 * @param <K>     key entry type
	 * @param <V>     value entry type
	 * @return a read-only map with all content
	 */
	@SafeVarargs
	public static @Unmodifiable <K, V> @NotNull Map<K, V> mapOf(Map.Entry<K, V> @NotNull ... entries) {
		// Convert a mutable map to an immutable one
		return java.util.Collections.unmodifiableMap(mutableMapOf(entries));
	}
	
	/**
	 * Create a read-only map with all the given elements.
	 *
	 * @param pairs the elements to insert
	 * @param <K>   key entry type
	 * @param <V>   value entry type
	 * @return a read-only map with all content
	 */
	@SafeVarargs
	public static @Unmodifiable <K, V> @NotNull Map<K, V> mapOf(Pair<K, V> @NotNull ... pairs) {
		// Convert a mutable map to an immutable one
		return java.util.Collections.unmodifiableMap(mutableMapOf(pairs));
	}
	
	/**
	 * Create a mutable map with all the given elements.
	 *
	 * @param <K> key entry type
	 * @param <V> value entry type
	 * @return a mutable map with all content
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> @NotNull Map<K, V> mutableMapOf() {
		return mutableMapOf((Pair<K, V>[]) EMPTY_PAIR);
	}
	
	/**
	 * Create a mutable map with all the given elements.
	 *
	 * @param entries the elements to insert
	 * @param <K>     key entry type
	 * @param <V>     value entry type
	 * @return a mutable map with all content
	 */
	@SafeVarargs
	public static <K, V> @NotNull Map<K, V> mutableMapOf(Map.Entry<K, V> @NotNull ... entries) {
		Map<K, V> result = new HashMap<>(entries.length);
		for (Map.Entry<K, V> it : entries) {
			result.put(it.getKey(), it.getValue());
		}
		return result;
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
	public static <K, V> @NotNull Map<K, V> mutableMapOf(Pair<K, V> @NotNull ... pairs) {
		Map<K, V> result = new HashMap<>(pairs.length);
		for (Pair<K, V> it : pairs) {
			result.put(it.first, it.second);
		}
		return result;
	}
	
	/**
	 * Create a weak-reference map with all the given elements.
	 *
	 * @param entries the elements to insert
	 * @param <K>     key entry type
	 * @param <V>     value entry type
	 * @return a weak map with all content
	 */
	@SafeVarargs
	public static <K, V> @NotNull Map<K, V> weakMapOf(Map.Entry<K, V> @NotNull ... entries) {
		Map<K, V> result = new WeakHashMap<>(entries.length);
		for (Map.Entry<K, V> it : entries) {
			result.put(it.getKey(), it.getValue());
		}
		return result;
	}
	
	/**
	 * Create a weak-reference map with all the given elements.
	 *
	 * @param pairs the elements to insert
	 * @param <K>   key entry type
	 * @param <V>   value entry type
	 * @return a weak map with all content
	 */
	@SafeVarargs
	public static <K, V> @NotNull Map<K, V> weakMapOf(Pair<K, V> @NotNull ... pairs) {
		Map<K, V> result = new WeakHashMap<>(pairs.length);
		for (Pair<K, V> it : pairs) {
			result.put(it.first, it.second);
		}
		return result;
	}
	
	/**
	 * Create a linked map with all the given elements.
	 *
	 * @param entries the elements to insert
	 * @param <K>     key entry type
	 * @param <V>     value entry type
	 * @return a linked map with all content
	 */
	@SafeVarargs
	public static <K, V> @NotNull Map<K, V> linkedMapOf(Map.Entry<K, V> @NotNull ... entries) {
		Map<K, V> result = new LinkedHashMap<>(entries.length);
		for (Map.Entry<K, V> it : entries) {
			result.put(it.getKey(), it.getValue());
		}
		return result;
	}
	
	/**
	 * Create a linked map with all the given elements.
	 *
	 * @param pairs the elements to insert
	 * @param <K>   key entry type
	 * @param <V>   value entry type
	 * @return a linked map with all content
	 */
	@SafeVarargs
	public static <K, V> @NotNull Map<K, V> linkedMapOf(Pair<K, V> @NotNull ... pairs) {
		Map<K, V> result = new LinkedHashMap<>(pairs.length);
		for (Pair<K, V> it : pairs) {
			result.put(it.first, it.second);
		}
		return result;
	}
	
	/* -----------------------------------------------------
	 * Entry methods
	 * ----------------------------------------------------- */
	
	/**
	 * Instantiate {@link Map.Entry} to more easily generate data maps.
	 *
	 * @param key   entry key
	 * @param value entry value
	 * @param <K>   entry key type
	 * @param <V>   entry value type
	 * @return a read-only configured entry
	 */
	public static <K, V> Map.@Unmodifiable @NotNull Entry<K, V> entryOf(K key, V value) {
		return new AbstractMap.SimpleImmutableEntry<>(key, value);
	}
	
	/**
	 * Instantiate {@link Map.Entry} to more easily generate data maps.
	 *
	 * @param key   entry key
	 * @param value entry value
	 * @param <K>   entry key type
	 * @param <V>   entry value type
	 * @return a mutable configured entry
	 */
	public static <K, V> Map.@NotNull Entry<K, V> mutableEntryOf(K key, V value) {
		return new AbstractMap.SimpleEntry<>(key, value);
	}
	
	/* ---------------------------------------------------------
	 * Join methods
	 * --------------------------------------------------------- */
	
	/**
	 * Combine different lists into a single collection
	 *
	 * @param excludeDuplicates remove all duplicate items
	 * @param lts               the lists you want to merge
	 * @param <T>               generic collection type
	 * @return returns a single collection with all the elements of the passed lists
	 */
	@SafeVarargs
	public static <T> @NotNull Collection<T> combine(boolean excludeDuplicates, List<T> @NotNull ... lts) {
		Collection<T> tmpResult = excludeDuplicates ? Collections.mutableSetOf() :
								  Collections.mutableListOf();
		// Iterate all collections
		for (List<T> lt : lts) {
			tmpResult.addAll(lt);
		}
		return tmpResult;
	}
	
	/**
	 * Combine different lists into a single collection
	 *
	 * @param lts the lists you want to merge
	 * @param <T> generic collection type
	 * @return returns a single collection with all the elements of the passed lists
	 */
	@SafeVarargs
	public static <T> @NotNull Collection<T> combine(List<T> @NotNull ... lts) {
		return combine(true, lts);
	}
	
	/**
	 * Combine different sets into a single collection
	 *
	 * @param sets the sets you want to merge
	 * @param <T>  generic collection type
	 * @return returns a single collection with all the elements of the passed sets
	 */
	@SafeVarargs
	public static <T> @NotNull Set<T> combine(Set<T> @NotNull ... sets) {
		Set<T> tmpResult = Collections.mutableSetOf();
		
		// Iterate all collections
		for (Set<T> lt : sets) {
			tmpResult.addAll(lt);
		}
		return tmpResult;
	}
	
}
