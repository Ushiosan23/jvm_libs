package ushiosan.jvm.function;

/**
 * Representation of a method in Java. Although called a function, it works very similar to a
 * method, and it is possible to reference one via referencing or lambda expressions.
 *
 * @param <E> execution error
 */
@FunctionalInterface
public interface UEmptyFunErr<E extends Throwable> {
	
	/**
	 * Method that invokes the behavior of the function. This method takes the
	 * necessary parameters.
	 *
	 * @throws E error if something goes wrong
	 */
	void invoke() throws E;
	
	/**
	 * Representation of a method in Java. Although called a function, it works very similar to a
	 * method, and it is possible to reference one via referencing or lambda expressions.
	 *
	 * @param <T1> function argument
	 * @param <E>  execution error
	 */
	@FunctionalInterface
	interface UEmptyFunErr1<T1, E extends Throwable> {
		
		/**
		 * Method that invokes the behavior of the function. This method takes the
		 * necessary parameters.
		 *
		 * @param a1 function argument
		 * @throws E error if something goes wrong
		 */
		void invoke(T1 a1) throws E;
		
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
	interface UEmptyFunErr2<T1, T2, E extends Throwable> {
		
		/**
		 * Method that invokes the behavior of the function. This method takes the
		 * necessary parameters.
		 *
		 * @param a1 first function argument
		 * @param a2 second function argument
		 * @throws E error if something goes wrong
		 */
		void invoke(T1 a1, T2 a2) throws E;
		
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
	interface UEmptyFunErr3<T1, T2, T3, E extends Throwable> {
		
		/**
		 * Method that invokes the behavior of the function. This method takes the
		 * necessary parameters.
		 *
		 * @param a1 first function argument
		 * @param a2 second function argument
		 * @param a3 third function argument
		 * @throws E error if something goes wrong
		 */
		void invoke(T1 a1, T2 a2, T3 a3) throws E;
		
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
	interface UEmptyFunErr4<T1, T2, T3, T4, E extends Throwable> {
		
		/**
		 * Method that invokes the behavior of the function. This method takes the
		 * necessary parameters.
		 *
		 * @param a1 first function argument
		 * @param a2 second function argument
		 * @param a3 third function argument
		 * @param a4 fourth function argument
		 * @throws E error if something goes wrong
		 */
		void invoke(T1 a1, T2 a2, T3 a3, T4 a4) throws E;
		
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
	interface UEmptyFunErr5<T1, T2, T3, T4, T5, E extends Throwable> {
		
		/**
		 * Method that invokes the behavior of the function. This method takes the
		 * necessary parameters.
		 *
		 * @param a1 first function argument
		 * @param a2 second function argument
		 * @param a3 third function argument
		 * @param a4 fourth function argument
		 * @param a5 fifth function argument
		 * @throws E error if something goes wrong
		 */
		void invoke(T1 a1, T2 a2, T3 a3, T4 a4, T5 a5) throws E;
		
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
	interface UEmptyFunErr6<T1, T2, T3, T4, T5, T6, E extends Throwable> {
		
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
		 * @throws E error if something goes wrong
		 */
		void invoke(T1 a1, T2 a2, T3 a3, T4 a4, T5 a5, T6 a6) throws E;
		
	}
	
}