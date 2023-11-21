package ushiosan.jvm.collections;

import org.jetbrains.annotations.NotNull;
import ushiosan.jvm.UObject;

import java.util.function.Predicate;
import java.util.stream.Stream;

public final class UStream {
	
	/**
	 * This class cannot be instantiated directly
	 */
	private UStream() {}
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Apply multiple filters to a single data stream
	 *
	 * @param base       the stream to be filtered
	 * @param predicates the multiple data filters
	 * @param <T>        the type of data flow
	 * @return the result of the data filters
	 */
	@SafeVarargs
	public static <T> @NotNull Stream<T> multipleFilters(@NotNull Stream<T> base, Predicate<T> @NotNull ... predicates) {
		UObject.requireNotNull(base, "base");
		// Apply all filters
		for (var filter : predicates) {
			base = base.filter(filter);
		}
		return base;
	}
	
}
