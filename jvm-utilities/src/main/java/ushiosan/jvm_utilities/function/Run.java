package ushiosan.jvm_utilities.function;

import ushiosan.jvm_utilities.lang.Obj;

import java.util.logging.Level;

/**
 * Interface used for the execution of actions very similar to what is done in the {@link Runnable} interface
 *
 * @param <E> type of error that this interface can throw
 */
public interface Run<E extends Throwable> {
	
	/**
	 * Action to execute
	 *
	 * @throws E error if something goes wrong
	 */
	void invoke() throws E;
	
	/**
	 * Interface used for the execution of actions very similar to what is done in the {@link Runnable} interface.
	 * The difference between the previous interface is that this one prevents the throwing of errors.
	 */
	interface Safe<E extends Throwable> extends Run<E> {
		
		/**
		 * The same behavior of {@link #invoke()} but without launch any error
		 */
		default void safeInvoke() {
			try {
				invoke();
			} catch (Throwable e) {
				Obj.logger().log(Level.SEVERE, e.getMessage(), e);
			}
		}
		
	}
	
}
