package ushiosan.jvm.function;

/**
 * Representation of a method in Java. Although called a function, it works very similar to a
 * method, and it is possible to reference one via referencing or lambda expressions.
 */
@FunctionalInterface
public interface UEmptyFun {
	
	/**
	 * Method that invokes the behavior of the function. This method takes the
	 * necessary parameters.
	 */
	void invoke();
	
	/**
	 * Representation of a method in Java. Although called a function, it works very similar to a
	 * method, and it is possible to reference one via referencing or lambda expressions.
	 *
	 * @param <T1> function argument
	 */
	
	@FunctionalInterface
	interface UEmptyFun1<T1> {
		
		/**
		 * Method that invokes the behavior of the function. This method takes the
		 * necessary parameters.
		 *
		 * @param a1 first function argument
		 */
		void invoke(T1 a1);
		
	}
	
	/**
	 * Representation of a method in Java. Although called a function, it works very similar to a
	 * method, and it is possible to reference one via referencing or lambda expressions.
	 *
	 * @param <T1> first function argument
	 * @param <T2> second function argument
	 */
	@FunctionalInterface
	interface UEmptyFun2<T1, T2> {
		
		/**
		 * Method that invokes the behavior of the function. This method takes the
		 * necessary parameters.
		 *
		 * @param a1 first function argument
		 * @param a2 second function argument
		 */
		void invoke(T1 a1, T2 a2);
		
	}
	
	/**
	 * Representation of a method in Java. Although called a function, it works very similar to a
	 * method, and it is possible to reference one via referencing or lambda expressions.
	 *
	 * @param <T1> first function argument
	 * @param <T2> second function argument
	 * @param <T3> third function argument
	 */
	@FunctionalInterface
	interface UEmptyFun3<T1, T2, T3> {
		
		/**
		 * Method that invokes the behavior of the function. This method takes the
		 * necessary parameters.
		 *
		 * @param a1 first function argument
		 * @param a2 second function argument
		 * @param a3 third function argument
		 */
		void invoke(T1 a1, T2 a2, T3 a3);
		
	}
	
	/**
	 * Representation of a method in Java. Although called a function, it works very similar to a
	 * method, and it is possible to reference one via referencing or lambda expressions.
	 *
	 * @param <T1> first function argument
	 * @param <T2> second function argument
	 * @param <T3> third function argument
	 * @param <T4> fourth function argument
	 */
	@FunctionalInterface
	interface UEmptyFun4<T1, T2, T3, T4> {
		
		/**
		 * Method that invokes the behavior of the function. This method takes the
		 * necessary parameters.
		 *
		 * @param a1 first function argument
		 * @param a2 second function argument
		 * @param a3 third function argument
		 * @param a4 fourth function argument
		 */
		void invoke(T1 a1, T2 a2, T3 a3, T4 a4);
		
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
	 */
	@FunctionalInterface
	interface UEmptyFun5<T1, T2, T3, T4, T5> {
		
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
		void invoke(T1 a1, T2 a2, T3 a3, T4 a4, T5 a5);
		
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
	 */
	@FunctionalInterface
	interface UEmptyFun6<T1, T2, T3, T4, T5, T6> {
		
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
		void invoke(T1 a1, T2 a2, T3 a3, T4 a4, T5 a5, T6 a6);
		
	}
	
}
