package ushiosan.jvm_utilities.function;

import java.util.Optional;

public interface Apply {

	/* -----------------------------------------------------
	 * Empty interfaces
	 * ----------------------------------------------------- */

	interface Empty<T> extends Apply {

		/**
		 * Apply configuration to the object and return it as a result
		 *
		 * @param item target element to which the changes will be applied
		 */
		void apply(T item);

	}

	interface EmptyError<T> extends Apply {

		/**
		 * Apply configuration to the object and return it as a result
		 *
		 * @param item target element to which the changes will be applied
		 * @throws Exception Error if something goes wrong
		 */
		void apply(T item) throws Exception;

	}

	interface EmptyErrorSafe<T> extends EmptyError<T> {

		/**
		 * The same behavior of {@link #apply(Object)} but without launch any error
		 *
		 * @param item target element to which the changes will be applied
		 */
		default void safeApply(T item) {
			try {
				apply(item);
			} catch (Exception ignored) {
			}
		}

	}

	/* -----------------------------------------------------
	 * Result interfaces
	 * ----------------------------------------------------- */

	interface Result<T, V> extends Apply {

		/**
		 * Apply configuration to the object and return it as a result
		 *
		 * @param item target element to which the changes will be applied
		 * @return returns the resulting object after applying the configuration
		 */
		V apply(T item);

	}

	interface ResultError<T, V> extends Apply {

		/**
		 * Apply configuration to the object and return it as a result
		 *
		 * @param item target element to which the changes will be applied
		 * @return returns the resulting object after applying the configuration
		 * @throws Exception error if something goes wrong
		 */
		V apply(T item) throws Exception;

	}

	interface ResultErrorSafe<T, V> extends ResultError<T, V> {

		/**
		 * The same behavior of {@link #apply(Object)} but without launch any error
		 *
		 * @param item target element to which the changes will be applied
		 * @return returns the resulting object after applying the configuration
		 * or {@link Optional#empty()} if action fail
		 */
		default Optional<V> safeApply(T item) {
			try {
				return Optional.ofNullable(apply(item));
			} catch (Exception ignored) {
			}
			return Optional.empty();
		}

	}

}
