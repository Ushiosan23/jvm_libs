package ushiosan.jvm.function;

/**
 * Representation of a method in Java. Although called a function, it works very similar to a
 * method, and it is possible to reference one via referencing or lambda expressions.
 *
 * @param <R> Value returned by the function
 */
@FunctionalInterface
public interface UFun<R> {
	
	/**
	 * Method that invokes the behavior of the function. This method takes the
	 * necessary parameters and returns the defined result.
	 *
	 * @return function call result
	 */
	R invoke();
	
	/**
	 * Representation of a method in Java. Although called a function, it works very similar to a
	 * method, and it is possible to reference one via referencing or lambda expressions.
	 *
	 * @param <R>  Value returned by the function
	 * @param <T1> function argument
	 */
	@FunctionalInterface
	interface UFun1<R, T1> {
		
		/**
		 * Method that invokes the behavior of the function. This method takes the
		 * necessary parameters and returns the defined result.
		 *
		 * @param a1 function argument
		 * @return function call result
		 */
		R invoke(T1 a1);
		
	}
	
	/**
	 * Representation of a method in Java. Although called a function, it works very similar to a
	 * method, and it is possible to reference one via referencing or lambda expressions.
	 *
	 * @param <R>  Value returned by the function
	 * @param <T1> first function argument
	 * @param <T2> second function argument
	 */
	@FunctionalInterface
	interface UFun2<R, T1, T2> {
		
		/**
		 * Method that invokes the behavior of the function. This method takes the
		 * necessary parameters and returns the defined result.
		 *
		 * @param a1 first function argument
		 * @param a2 second function argument
		 * @return function call result
		 */
		R invoke(T1 a1, T2 a2);
		
	}
	
	/**
	 * Representation of a method in Java. Although called a function, it works very similar to a
	 * method, and it is possible to reference one via referencing or lambda expressions.
	 *
	 * @param <R>  Value returned by the function
	 * @param <T1> first function argument
	 * @param <T2> second function argument
	 * @param <T3> third function argument
	 */
	@FunctionalInterface
	interface UFun3<R, T1, T2, T3> {
		
		/**
		 * Method that invokes the behavior of the function. This method takes the
		 * necessary parameters and returns the defined result.
		 *
		 * @param a1 first function argument
		 * @param a2 second function argument
		 * @param a3 third function argument
		 * @return function call result
		 */
		R invoke(T1 a1, T2 a2, T3 a3);
		
	}
	
	/**
	 * Representation of a method in Java. Although called a function, it works very similar to a
	 * method, and it is possible to reference one via referencing or lambda expressions.
	 *
	 * @param <R>  Value returned by the function
	 * @param <T1> first function argument
	 * @param <T2> second function argument
	 * @param <T3> third function argument
	 * @param <T4> fourth function argument
	 */
	@FunctionalInterface
	interface UFun4<R, T1, T2, T3, T4> {
		
		/**
		 * Method that invokes the behavior of the function. This method takes the
		 * necessary parameters and returns the defined result.
		 *
		 * @param a1 first function argument
		 * @param a2 second function argument
		 * @param a3 third function argument
		 * @param a4 fourth function argument
		 * @return function call result
		 */
		R invoke(T1 a1, T2 a2, T3 a3, T4 a4);
		
	}
	
	/**
	 * Representation of a method in Java. Although called a function, it works very similar to a
	 * method, and it is possible to reference one via referencing or lambda expressions.
	 *
	 * @param <R>  Value returned by the function
	 * @param <T1> first function argument
	 * @param <T2> second function argument
	 * @param <T3> third function argument
	 * @param <T4> fourth function argument
	 * @param <T5> fifth function argument
	 */
	@FunctionalInterface
	interface UFun5<R, T1, T2, T3, T4, T5> {
		
		/**
		 * Method that invokes the behavior of the function. This method takes the
		 * necessary parameters and returns the defined result.
		 *
		 * @param a1 first function argument
		 * @param a2 second function argument
		 * @param a3 third function argument
		 * @param a4 fourth function argument
		 * @param a5 fifth function argument
		 * @return function call result
		 */
		R invoke(T1 a1, T2 a2, T3 a3, T4 a4, T5 a5);
		
	}
	
	/**
	 * Representation of a method in Java. Although called a function, it works very similar to a
	 * method, and it is possible to reference one via referencing or lambda expressions.
	 *
	 * @param <R>  Value returned by the function
	 * @param <T1> first function argument
	 * @param <T2> second function argument
	 * @param <T3> third function argument
	 * @param <T4> fourth function argument
	 * @param <T5> fifth function argument
	 * @param <T6> sixth function argument
	 */
	@FunctionalInterface
	interface UFun6<R, T1, T2, T3, T4, T5, T6> {
		
		/**
		 * Method that invokes the behavior of the function. This method takes the
		 * necessary parameters and returns the defined result.
		 *
		 * @param a1 first function argument
		 * @param a2 second function argument
		 * @param a3 third function argument
		 * @param a4 fourth function argument
		 * @param a5 fifth function argument
		 * @param a6 sixth function argument
		 * @return function call result
		 */
		R invoke(T1 a1, T2 a2, T3 a3, T4 a4, T5 a5, T6 a6);
		
	}
	
}
