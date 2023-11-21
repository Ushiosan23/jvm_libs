package ushiosan.jvm.collections;

import org.jetbrains.annotations.NotNull;
import ushiosan.jvm.UObject;

import java.util.Collection;
import java.util.List;
import java.util.Map;

abstract class UCollection {
	
	/***
	 * The extra amount of space you have to reserve to avoid resizing the internal array each time
	 */
	private static final int CAPACITY_GROW = 10;
	/**
	 * List of classes considered the bases for immutable objects (inside the standard Java library)
	 */
	private static Class<?> @NotNull [] unmodifiableCollectionClassRefs;
	
	// Initialization static block
	static {
		try {
			// These classes are defined within the language itself and are in charge
			// of eliminating the mutation functionality in the collections.
			// Whether any collection that inherits from this class is considered
			// immutable and cannot change its contents unless it is changed to a
			// mutable state (creating a different object that does support that functionality).
			unmodifiableCollectionClassRefs = UArray.make(
				Class.forName("java.util.ImmutableCollections$AbstractImmutableCollection"),
				Class.forName("java.util.Collections$UnmodifiableCollection"),
				Class.forName("java.util.Collections$UnmodifiableMap"));
		} catch (Exception ignore) {
			// An empty array is created if any of the specified classes is not
			// available (only for really isolated cases).
			unmodifiableCollectionClassRefs = UObject.cast(UArray.OBJ_EMPTY);
		}
	}
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Check if the inspected collection supports the modification of its data
	 *
	 * @param collection the collection of data you want to inspect
	 * @return {@code true} if the collection is immutable or {@code false} otherwise
	 */
	public static boolean isUnmodifiable(@NotNull Collection<?> collection) {
		return isUnmodifiableImpl(collection);
	}
	
	/**
	 * Check if the inspected collection supports the modification of its data
	 *
	 * @param map the map of data you want to inspect
	 * @return {@code true} if the collection is immutable or {@code false} otherwise
	 */
	public static boolean isUnmodifiable(@NotNull Map<?, ?> map) {
		return isUnmodifiableImpl(map);
	}
	
	/* -----------------------------------------------------
	 * Join methods
	 * ----------------------------------------------------- */
	
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
		Collection<T> tmpResult = excludeDuplicates ? USet.makeMutable() :
								  UList.makeMutable();
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
	
	/* -----------------------------------------------------
	 * Internal methods
	 * ----------------------------------------------------- */
	
	/**
	 * Calculate the number of items within all data collections
	 *
	 * @param collections the collections you want to compute
	 * @return the number of items obtained within all collections
	 */
	protected static int calculateSize(Collection<?> @NotNull ... collections) {
		int result = 0;
		for (var collection : collections) {
			result += collection.size();
		}
		return result;
	}
	
	/**
	 * Gets the number of items to reserve within a collection
	 *
	 * @param size the base quantity of elements
	 * @return the number of target elements
	 */
	protected static int measureSize(int size) {
		return size + CAPACITY_GROW;
	}
	
	/**
	 * Check if the inspected collection supports the modification of its data
	 *
	 * @param collection the collection of data you want to inspect
	 * @return {@code true} if the collection is immutable or {@code false} otherwise
	 */
	private static boolean isUnmodifiableImpl(@NotNull Object collection) {
		UObject.requireNotNull(collection, "collection");
		// Check the unmodifiable type
		for (Class<?> reference : unmodifiableCollectionClassRefs) {
			if (UObject.canCastNotNull(collection, reference)) return true;
		}
		
		return false;
	}
	
}
