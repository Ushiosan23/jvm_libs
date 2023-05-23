package ushiosan.jvm.collections;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

abstract class UCollection {
	
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
		Collection<T> tmpResult = excludeDuplicates ? USet.mutableSetOf() :
								  UList.mutableListOf();
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
	
}
