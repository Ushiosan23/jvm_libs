package ushiosan.jvm_utilities.function;

import ushiosan.jvm_utilities.lang.Obj;

import java.util.Optional;
import java.util.logging.Level;

/**
 * Interface used as a model for lambda expressions.
 */
public interface Apply {
	
	/* -----------------------------------------------------
	 * Empty interfaces
	 * ----------------------------------------------------- */
	
	/**
	 * Very similar to the {@link Apply} interface but with the difference that it does not return a value at the
	 * time of its call. That's why it's called {@code Empty}
	 *
	 * @param <T> the type of data you enter as a parameter
	 */
	interface Empty<T> extends Apply {
		
		/**
		 * Apply configuration to the object and return it as a result
		 *
		 * @param item target element to which the changes will be applied
		 */
		void apply(T item);
		
	}
	
	/**
	 * Very similar to the {@link Apply} interface but with the difference that it can generate
	 * an error at the time of its call
	 *
	 * @param <T> the type of data you enter as a parameter
	 * @param <E> the type of error when the interface is called
	 */
	interface EmptyError<T, E extends Throwable> extends Apply {
		
		/**
		 * Apply configuration to the object and return it as a result
		 *
		 * @param item target element to which the changes will be applied
		 * @throws E Error if something goes wrong
		 */
		void apply(T item) throws E;
		
	}
	
	/**
	 * Very similar to the {@link Apply} interface but with the difference that it can generate
	 * an error at the time of its call.
	 * <p>
	 * Unlike {@link EmptyError} this can be called without generating any errors, but its use
	 * is not recommended unless you know what you are doing.
	 *
	 * @param <T> the type of data you enter as a parameter
	 * @param <E> the type of error when the interface is called
	 */
	interface EmptyErrorSafe<T, E extends Throwable> extends EmptyError<T, E> {
		
		/**
		 * The same behavior of {@link #apply(Object)} but without launch any error
		 *
		 * @param item target element to which the changes will be applied
		 */
		default void safeApply(T item) {
			try {
				apply(item);
			} catch (Throwable e) {
				Obj.logger().log(Level.SEVERE, e.getMessage(), e);
			}
		}
		
		/**
		 * The same behavior of {@link #apply(Object)} but without writing anything to the logs
		 *
		 * @param item target element to which the changes will be applied
		 */
		default void silentApply(T item) {
			try {
				apply(item);
			} catch (Throwable ignore) {
			}
		}
		
	}
	
	/* -----------------------------------------------------
	 * Result interfaces
	 * ----------------------------------------------------- */
	
	/**
	 * Very similar to the {@link Apply} interface but with the difference that it does return a value at the
	 * time of its call. That's why it's called {@code Result}
	 *
	 * @param <T> the type of data you enter as a parameter
	 * @param <V> the result generated when called
	 */
	interface Result<T, V> extends Apply {
		
		/**
		 * Apply configuration to the object and return it as a result
		 *
		 * @param item target element to which the changes will be applied
		 * @return returns the resulting object after applying the configuration
		 */
		V apply(T item);
		
	}
	
	/**
	 * Very similar to the {@link Result} interface but with the difference that it can generate
	 * an error at the time of its call
	 *
	 * @param <T> the type of data you enter as a parameter
	 * @param <V> the result generated when called
	 * @param <E> the type of error when the interface is called
	 */
	interface ResultError<T, V, E extends Throwable> extends Apply {
		
		/**
		 * Apply configuration to the object and return it as a result
		 *
		 * @param item target element to which the changes will be applied
		 * @return returns the resulting object after applying the configuration
		 * @throws E error if something goes wrong
		 */
		V apply(T item) throws E;
		
	}
	
	/**
	 * Very similar to the {@link ResultError} interface but with the difference that it can generate
	 * an error at the time of its call.
	 * <p>
	 * Unlike {@link ResultError} this can be called without generating any errors, but its use
	 * is not recommended unless you know what you are doing.
	 *
	 * @param <T> the type of data you enter as a parameter
	 * @param <V> the result generated when called
	 * @param <E> the type of error when the interface is called
	 */
	interface ResultErrorSafe<T, V, E extends Throwable> extends ResultError<T, V, E> {
		
		/**
		 * The same behavior of {@link #apply(Object)} but without launch any error
		 *
		 * @param item target element to which the changes will be applied
		 * @return returns the resulting object after applying the configuration
		 * 	or {@link Optional#empty()} if action fail
		 */
		default Optional<V> safeApply(T item) {
			try {
				return Optional.ofNullable(apply(item));
			} catch (Throwable e) {
				Obj.logger().log(Level.SEVERE, e.getMessage(), e);
			}
			return Optional.empty();
		}
		
		/**
		 * The same behavior of {@link #apply(Object)} but without writing anything to the logs
		 *
		 * @param item target element to which the changes will be applied
		 * @return returns the resulting object after applying the configuration
		 * 	or {@link Optional#empty()} if action fail
		 */
		default Optional<V> silentSafeApply(T item) {
			try {
				return Optional.ofNullable(apply(item));
			} catch (Throwable ignore) {
			}
			return Optional.empty();
		}
		
	}
	
}
