package ushiosan.jvm_utilities.function;

/**
 * Interface with the same behavior as {@link Runnable} but with the ability to handle errors within it
 */
public interface RunnableError extends Runnable {
	
	/**
	 * The general contract of the method <code>run</code> is that it may
	 * take any action whatsoever.
	 *
	 * @throws Exception error if something goes wrong
	 */
	void runWithErr() throws Exception;
	
	/**
	 * When an object implementing interface <code>Runnable</code> is used
	 * to create a thread, starting the thread causes the object's
	 * <code>run</code> method to be called in that separately executing
	 * thread.
	 * <p>
	 * The general contract of the method <code>run</code> is that it may
	 * take any action whatsoever.
	 *
	 * @see Thread#run()
	 */
	@Override
	default void run() {
		try {
			runWithErr();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
