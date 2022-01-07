package com.github.ushiosan23.jvm.functions.apply;

public interface IApply {

	interface EmptyResult<T> extends IApply {

		/**
		 * Apply configuration to the object and return it as a result
		 *
		 * @param element Object to which the action applies
		 */
		void run(T element);

	}

	interface WithResult<T, V> extends IApply {

		/**
		 * Apply configuration to the object and return it as a result
		 *
		 * @param element Object to which the action applies
		 * @return Returns the same object is returned but with the changes made
		 */
		V run(T element);

	}

}
