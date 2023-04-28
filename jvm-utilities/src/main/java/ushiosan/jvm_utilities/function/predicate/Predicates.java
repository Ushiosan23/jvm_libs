package ushiosan.jvm_utilities.function.predicate;

import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;
import java.util.stream.Stream;

public final class Predicates {
	
	/**
	 * This class cannot be instantiated
	 */
	private Predicates() {}
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Applies all predicates to the data stream, returning the same
	 * stream but with those filters applied.
	 *
	 * @param stream     the stream to modify
	 * @param predicates all filter predicates
	 * @param <T>        generic stream type
	 * @return the stream with filters applied
	 */
	@SafeVarargs
	public static <T> @NotNull Stream<T> filterAll(@NotNull Stream<T> stream, Predicate<T> @NotNull ... predicates) {
		// Iterate all filters
		for (var predicate : predicates) {
			stream = stream.filter(predicate);
		}
		return stream;
	}
	
	
}
