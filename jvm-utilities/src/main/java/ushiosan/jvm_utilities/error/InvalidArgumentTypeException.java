package ushiosan.jvm_utilities.error;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.security.PrivilegedActionException;

public class InvalidArgumentTypeException extends Exception {
	
	/* -----------------------------------------------------
	 * Properties
	 * ----------------------------------------------------- */
	
	/**
	 * Exception message
	 */
	private static final String MESSAGE = "The argument is not valid \"%s\" type. A \"%s\" given";
	
	/* -----------------------------------------------------
	 * Constructors
	 * ----------------------------------------------------- */
	
	/**
	 * Constructs a new exception with {@code null} as its detail message.
	 * The cause is not initialized, and may subsequently be initialized by a
	 * call to {@link #initCause}.
	 */
	public InvalidArgumentTypeException() {
	}
	
	/**
	 * Constructs a new exception with the specified detail message.  The
	 * cause is not initialized, and may subsequently be initialized by
	 * a call to {@link #initCause}.
	 *
	 * @param message the detail message. The detail message is saved for
	 *                later retrieval by the {@link #getMessage()} method.
	 */
	public InvalidArgumentTypeException(String message) {
		super(message);
	}
	
	/**
	 * Constructs a new exception with the specified detail message and
	 * cause.  <p>Note that the detail message associated with
	 * {@code cause} is <i>not</i> automatically incorporated in
	 * this exception's detail message.
	 *
	 * @param message the detail message (which is saved for later retrieval
	 *                by the {@link #getMessage()} method).
	 * @param cause   the cause (which is saved for later retrieval by the
	 *                {@link #getCause()} method).  (A {@code null} value is
	 *                permitted, and indicates that the cause is nonexistent or
	 *                unknown.)
	 * @since 1.4
	 */
	public InvalidArgumentTypeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Constructs a new exception with the specified cause and a detail
	 * message of {@code (cause==null ? null : cause.toString())} (which
	 * typically contains the class and detail message of {@code cause}).
	 * This constructor is useful for exceptions that are little more than
	 * wrappers for other throwables (for example, {@link
	 * PrivilegedActionException}).
	 *
	 * @param cause the cause (which is saved for later retrieval by the
	 *              {@link #getCause()} method).  (A {@code null} value is
	 *              permitted, and indicates that the cause is nonexistent or
	 *              unknown.)
	 * @since 1.4
	 */
	public InvalidArgumentTypeException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructs a new exception with the specified detail message,
	 * cause, suppression enabled or disabled, and writable stack
	 * trace enabled or disabled.
	 *
	 * @param message            the detail message.
	 * @param cause              the cause.  (A {@code null} value is permitted,
	 *                           and indicates that the cause is nonexistent or unknown.)
	 * @param enableSuppression  whether suppression is enabled
	 *                           or disabled
	 * @param writableStackTrace whether the stack trace should
	 *                           be writable
	 * @since 1.7
	 */
	public InvalidArgumentTypeException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Generate a configured exception
	 *
	 * @param expected expected argument type
	 * @param given    error argument type
	 * @return an instance with configured message
	 */
	@Contract("_, _ -> new")
	public static @NotNull InvalidArgumentTypeException makeOf(@NotNull String expected, @NotNull String given) {
		return new InvalidArgumentTypeException(String.format(MESSAGE, expected, given));
	}
	
	/**
	 * Generate a configured exception
	 *
	 * @param expected expected argument type
	 * @param given    error argument type
	 * @return an instance with configured message
	 */
	@Contract("_, _ -> new")
	public static @NotNull InvalidArgumentTypeException makeOf(@NotNull Class<?> expected, @NotNull Class<?> given) {
		return makeOf(expected.toString(), given.toString());
	}
	
}
