package ushiosan.jvm.function;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import static ushiosan.jvm.ULogger.getLogger;
import static ushiosan.jvm.ULogger.logWarning;

/**
 * Representation of a method in Java. Although called a function, it works very similar to a
 * method, and it is possible to reference one via referencing or lambda expressions.
 *
 * @param <E> execution error
 */
@FunctionalInterface
public interface UFunErrSafe<R, E extends Throwable> extends UFunErr<R, E> {
	
	/**
	 * Method that invokes the behavior of the function. This method takes the
	 * necessary parameters.
	 *
	 * @return function call result
	 */
	default @NotNull Optional<R> invokeSafe() {
		try {
			return Optional.ofNullable(invoke());
		} catch (Throwable e) {
			logWarning(getLogger(getClass()), e);
			return Optional.empty();
		}
	}
	
	/**
	 * Representation of a method in Java. Although called a function, it works very similar to a
	 * method, and it is possible to reference one via referencing or lambda expressions.
	 *
	 * @param <T1> function argument
	 * @param <E>  execution error
	 */
	@FunctionalInterface
	interface UFunErrSafe1<R, T1, E extends Throwable> extends UFunErr.UFunErr1<R, T1, E> {
		
		/**
		 * Method that invokes the behavior of the function. This method takes the
		 * necessary parameters.
		 *
		 * @param a1 function argument
		 * @return function call result
		 */
		default @NotNull Optional<R> invokeSafe(T1 a1) {
			try {
				return Optional.ofNullable(invoke(a1));
			} catch (Throwable e) {
				logWarning(getLogger(getClass()), e);
				return Optional.empty();
			}
		}
		
	}
	
	/**
	 * Representation of a method in Java. Although called a function, it works very similar to a
	 * method, and it is possible to reference one via referencing or lambda expressions.
	 *
	 * @param <T1> first function argument
	 * @param <T2> second function argument
	 * @param <E>  execution error
	 */
	@FunctionalInterface
	interface UFunErrSafe2<R, T1, T2, E extends Throwable> extends UFunErr.UFunErr2<R, T1, T2, E> {
		
		/**
		 * Method that invokes the behavior of the function. This method takes the
		 * necessary parameters.
		 *
		 * @param a1 first function argument
		 * @param a2 second function argument
		 * @return function call result
		 */
		default @NotNull Optional<R> invokeSafe(T1 a1, T2 a2) {
			try {
				return Optional.ofNullable(invoke(a1, a2));
			} catch (Throwable e) {
				logWarning(getLogger(getClass()), e);
				return Optional.empty();
			}
		}
		
	}
	
	/**
	 * Representation of a method in Java. Although called a function, it works very similar to a
	 * method, and it is possible to reference one via referencing or lambda expressions.
	 *
	 * @param <T1> first function argument
	 * @param <T2> second function argument
	 * @param <T3> third function argument
	 * @param <E>  execution error
	 */
	@FunctionalInterface
	interface UFunErrSafe3<R, T1, T2, T3, E extends Throwable> extends UFunErr.UFunErr3<R, T1, T2, T3, E> {
		
		/**
		 * Method that invokes the behavior of the function. This method takes the
		 * necessary parameters.
		 *
		 * @param a1 first function argument
		 * @param a2 second function argument
		 * @param a3 third function argument
		 * @return function call result
		 */
		default @NotNull Optional<R> invokeSafe(T1 a1, T2 a2, T3 a3) {
			try {
				return Optional.ofNullable(invoke(a1, a2, a3));
			} catch (Throwable e) {
				logWarning(getLogger(getClass()), e);
				return Optional.empty();
			}
		}
		
	}
	
	/**
	 * Representation of a method in Java. Although called a function, it works very similar to a
	 * method, and it is possible to reference one via referencing or lambda expressions.
	 *
	 * @param <T1> first function argument
	 * @param <T2> second function argument
	 * @param <T3> third function argument
	 * @param <T4> fourth function argument
	 * @param <E>  execution error
	 */
	@FunctionalInterface
	interface UFunErrSafe4<R, T1, T2, T3, T4, E extends Throwable> extends UFunErr.UFunErr4<R, T1, T2, T3, T4, E> {
		
		/**
		 * Method that invokes the behavior of the function. This method takes the
		 * necessary parameters.
		 *
		 * @param a1 first function argument
		 * @param a2 second function argument
		 * @param a3 third function argument
		 * @param a4 fourth function argument
		 * @return function call result
		 */
		default @NotNull Optional<R> invokeSafe(T1 a1, T2 a2, T3 a3, T4 a4) {
			try {
				return Optional.ofNullable(invoke(a1, a2, a3, a4));
			} catch (Throwable e) {
				logWarning(getLogger(getClass()), e);
				return Optional.empty();
			}
		}
		
	}
	
	/**
	 * Representation of a method in Java. Although called a function, it works very similar to a
	 * method, and it is possible to reference one via referencing or lambda expressions.
	 *
	 * @param <T1> first function argument
	 * @param <T2> second function argument
	 * @param <T3> third function argument
	 * @param <T4> fourth function argument
	 * @param <T5> fifth function argument
	 * @param <E>  execution error
	 */
	@FunctionalInterface
	interface UFunErrSafe5<R, T1, T2, T3, T4, T5, E extends Throwable> extends UFunErr.UFunErr5<R, T1, T2, T3, T4, T5, E> {
		
		/**
		 * Method that invokes the behavior of the function. This method takes the
		 * necessary parameters.
		 *
		 * @param a1 first function argument
		 * @param a2 second function argument
		 * @param a3 third function argument
		 * @param a4 fourth function argument
		 * @param a5 fifth function argument
		 * @return function call result
		 */
		default @NotNull Optional<R> invokeSafe(T1 a1, T2 a2, T3 a3, T4 a4, T5 a5) {
			try {
				return Optional.ofNullable(invoke(a1, a2, a3, a4, a5));
			} catch (Throwable e) {
				logWarning(getLogger(getClass()), e);
				return Optional.empty();
			}
		}
		
	}
	
	/**
	 * Representation of a method in Java. Although called a function, it works very similar to a
	 * method, and it is possible to reference one via referencing or lambda expressions.
	 *
	 * @param <T1> first function argument
	 * @param <T2> second function argument
	 * @param <T3> third function argument
	 * @param <T4> fourth function argument
	 * @param <T5> fifth function argument
	 * @param <T6> sixth function argument
	 * @param <E>  execution error
	 */
	@FunctionalInterface
	interface UFunErrSafe6<R, T1, T2, T3, T4, T5, T6, E extends Throwable> extends
		UFunErr.UFunErr6<R, T1, T2, T3, T4, T5, T6, E> {
		
		/**
		 * Method that invokes the behavior of the function. This method takes the
		 * necessary parameters.
		 *
		 * @param a1 first function argument
		 * @param a2 second function argument
		 * @param a3 third function argument
		 * @param a4 fourth function argument
		 * @param a5 fifth function argument
		 * @param a6 sixth function argument
		 * @return function call result
		 */
		default @NotNull Optional<R> invokeSafe(T1 a1, T2 a2, T3 a3, T4 a4, T5 a5, T6 a6) {
			try {
				return Optional.ofNullable(invoke(a1, a2, a3, a4, a5, a6));
			} catch (Throwable e) {
				logWarning(getLogger(getClass()), e);
				return Optional.empty();
			}
		}
		
	}
	
}