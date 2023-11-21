package ushiosan.jvm.function;

import ushiosan.jvm.ULogger;

import java.util.logging.Logger;

/**
 * Representation of a method in Java. Although called a function, it works very similar to a
 * method, and it is possible to reference one via referencing or lambda expressions.
 *
 * @param <E> execution error
 */
@FunctionalInterface
public interface UEmptyFunErrSafe<E extends Throwable> extends UEmptyFunErr<E> {
	
	/**
	 * Method that invokes the behavior of the function. This method takes the
	 * necessary parameters.
	 */
	default void invokeSafe() {
		try {
			invoke();
		} catch (Throwable e) {
			Logger logger = Logger.getLogger(ULogger.loggerName(getClass()));
			logger.log(ULogger.logWarning(e));
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
	interface UEmptyFunErrSafe1<T1, E extends Throwable> extends UEmptyFunErr1<T1, E> {
		
		/**
		 * Method that invokes the behavior of the function. This method takes the
		 * necessary parameters.
		 *
		 * @param a1 function argument
		 */
		default void invokeSafe(T1 a1) {
			try {
				invoke(a1);
			} catch (Throwable e) {
				Logger logger = Logger.getLogger(ULogger.loggerName(getClass()));
				logger.log(ULogger.logWarning(e));
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
	interface UEmptyFunErrSafe2<T1, T2, E extends Throwable> extends UEmptyFunErr2<T1, T2, E> {
		
		/**
		 * Method that invokes the behavior of the function. This method takes the
		 * necessary parameters.
		 *
		 * @param a1 first function argument
		 * @param a2 second function argument
		 */
		default void invokeSafe(T1 a1, T2 a2) {
			try {
				invoke(a1, a2);
			} catch (Throwable e) {
				Logger logger = Logger.getLogger(ULogger.loggerName(getClass()));
				logger.log(ULogger.logWarning(e));
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
	interface UEmptyFunErrSafe3<T1, T2, T3, E extends Throwable> extends UEmptyFunErr3<T1, T2, T3, E> {
		
		/**
		 * Method that invokes the behavior of the function. This method takes the
		 * necessary parameters.
		 *
		 * @param a1 first function argument
		 * @param a2 second function argument
		 * @param a3 third function argument
		 */
		default void invokeSafe(T1 a1, T2 a2, T3 a3) {
			try {
				invoke(a1, a2, a3);
			} catch (Throwable e) {
				Logger logger = Logger.getLogger(ULogger.loggerName(getClass()));
				logger.log(ULogger.logWarning(e));
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
	interface UEmptyFunErrSafe4<T1, T2, T3, T4, E extends Throwable> extends UEmptyFunErr4<T1, T2, T3, T4, E> {
		
		/**
		 * Method that invokes the behavior of the function. This method takes the
		 * necessary parameters.
		 *
		 * @param a1 first function argument
		 * @param a2 second function argument
		 * @param a3 third function argument
		 * @param a4 fourth function argument
		 */
		default void invokeSafe(T1 a1, T2 a2, T3 a3, T4 a4) {
			try {
				invoke(a1, a2, a3, a4);
			} catch (Throwable e) {
				Logger logger = Logger.getLogger(ULogger.loggerName(getClass()));
				logger.log(ULogger.logWarning(e));
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
	interface UEmptyFunErrSafe5<T1, T2, T3, T4, T5, E extends Throwable> extends UEmptyFunErr5<T1, T2, T3, T4, T5, E> {
		
		/**
		 * Method that invokes the behavior of the function. This method takes the
		 * necessary parameters.
		 *
		 * @param a1 first function argument
		 * @param a2 second function argument
		 * @param a3 third function argument
		 * @param a4 fourth function argument
		 * @param a5 fifth function argument
		 */
		default void invokeSafe(T1 a1, T2 a2, T3 a3, T4 a4, T5 a5) {
			try {
				invoke(a1, a2, a3, a4, a5);
			} catch (Throwable e) {
				Logger logger = Logger.getLogger(ULogger.loggerName(getClass()));
				logger.log(ULogger.logWarning(e));
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
	interface UEmptyFunErrSafe6<T1, T2, T3, T4, T5, T6, E extends Throwable> extends UEmptyFunErr6<T1, T2, T3, T4, T5, T6, E> {
		
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
		 */
		default void invokeSafe(T1 a1, T2 a2, T3 a3, T4 a4, T5 a5, T6 a6) {
			try {
				invoke(a1, a2, a3, a4, a5, a6);
			} catch (Throwable e) {
				Logger logger = Logger.getLogger(ULogger.loggerName(getClass()));
				logger.log(ULogger.logWarning(e));
			}
		}
		
	}
	
}