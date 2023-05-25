package ushiosan.jvm.accumulator;

import org.jetbrains.annotations.NotNull;

public interface UAccumulator<R, E> {
	
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
	 * Adds the result of other accumulators with the same type
	 *
	 * @param other another accumulator
	 */
	void pushAll(@NotNull UAccumulator<E, R> other);
	
}
