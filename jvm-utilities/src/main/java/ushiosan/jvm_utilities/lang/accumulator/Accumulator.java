package ushiosan.jvm_utilities.lang.accumulator;

import org.jetbrains.annotations.NotNull;

public interface Accumulator<E, R> {
	
	/**
	 * Gets the result of accumulated content
	 *
	 * @return all accumulated content
	 */
	@NotNull R result();
	
	/**
	 * Push more content to the current accumulator
	 *
	 * @param item The item you want to accumulate
	 */
	void push(@NotNull E item);
	
	/**
	 * Adds the result of other accumulators of the same type
	 *
	 * @param other another accumulator
	 */
	void pushAll(@NotNull Accumulator<E, R> other);
	
}
