package ushiosan.jvm_utilities.function;

/**
 * Interface used for the execution of actions very similar to what is done in the {@link Runnable} interface
 */
public interface Run {
	
	/**
	 * Action to execute
	 *
	 * @throws Exception error if something goes wrong
	 */
	void invoke() throws Exception;
	
	/**
	 * Interface used for the execution of actions very similar to what is done in the {@link Runnable} interface.
	 * The difference between the previous interface is that this one prevents the throwing of errors.
	 */
	interface Safe extends Run {
		
		/**
		 * The same behavior of {@link #invoke()} but without launch any error
		 */
		default void safeInvoke() {
			try {
				invoke();
			} catch (Exception ignored) {
			}
		}
		
	}
	
}
