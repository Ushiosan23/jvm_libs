package ushiosan.jvm_utilities.function;

public interface Run {
	
	/**
	 * Action to execute
	 *
	 * @throws Exception error if something goes wrong
	 */
	void invoke() throws Exception;
	
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
