package ushiosan.jvm;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ushiosan.jvm.function.UEmptyFun;
import ushiosan.jvm.function.UEmptyFunErr;
import ushiosan.jvm.function.UFun;
import ushiosan.jvm.function.UFunErr;

import java.util.Optional;
import java.util.logging.Logger;

public final class UAction {
	
	/**
	 * Logger instance
	 */
	private static final Logger LOG = Logger.getLogger(ULogger.loggerName(UAction.class));
	
	/* -----------------------------------------------------
	 * Properties
	 * ----------------------------------------------------- */
	
	/**
	 * This class cannot be instantiated
	 */
	private UAction() {}
	
	/* -----------------------------------------------------
	 * Also methods
	 * ----------------------------------------------------- */
	
	/**
	 * Applies configuration to an object based on a local context. Returns the
	 * same object but with the configuration already applied.
	 *
	 * @param obj    the base object to configure
	 * @param action the action to execute
	 * @param <T>    object type
	 * @return the same object but with the configuration already applied
	 * @throws IllegalArgumentException error if {@code action} is {@code null}
	 */
	@Contract("_, _ -> param1")
	public static <T> @Nullable T also(@Nullable T obj, UEmptyFun.@NotNull UEmptyFun1<T> action) {
		UObject.requireNotNull(action, "action");
		// Execute action
		action.invoke(obj);
		return obj;
	}
	
	/**
	 * Applies configuration to an object based on a local context. Returns the
	 * same object but with the configuration already applied.
	 *
	 * @param obj    the base object to configure
	 * @param action the action to execute
	 * @param <T>    object type
	 * @return the same object but with the configuration already applied
	 * @throws IllegalArgumentException error if {@code obj} or {@code action} is {@code null}
	 */
	public static <T> @NotNull T alsoNotNull(@NotNull T obj, UEmptyFun.@NotNull UEmptyFun1<T> action) {
		UObject.requireNotNull(obj, "obj");
		// Execute method
		return also(obj, action);
	}
	
	/**
	 * Applies configuration to an object based on a local context. Returns the
	 * same object but with the configuration already applied.
	 *
	 * @param obj    the base object to configure
	 * @param action the action to execute
	 * @param <T>    object type
	 * @param <E>    error type
	 * @return the same object but with the configuration already applied
	 * @throws E                        error if something goes wrong
	 * @throws IllegalArgumentException error if {@code action} is {@code null}
	 */
	public static <T, E extends Throwable> @Nullable T alsoErr(@Nullable T obj,
		UEmptyFunErr.@NotNull UEmptyFunErr1<T, E> action) throws E {
		UObject.requireNotNull(action, "action");
		// Execute the action
		action.invoke(obj);
		return obj;
	}
	
	/**
	 * Applies configuration to an object based on a local context. Returns the
	 * same object but with the configuration already applied.
	 *
	 * @param obj    the base object to configure
	 * @param action the action to execute
	 * @param <T>    object type
	 * @param <E>    error type
	 * @return the same object but with the configuration already applied
	 * @throws E                        error if something goes wrong
	 * @throws IllegalArgumentException error if {@code obj} or {@code action} is {@code null}
	 */
	public static <T, E extends Throwable> @NotNull T alsoErrNotNull(@NotNull T obj,
		UEmptyFunErr.@NotNull UEmptyFunErr1<T, E> action) throws E {
		UObject.requireNotNull(obj, "obj");
		// Execute the method
		return alsoErr(obj, action);
	}
	
	/**
	 * Applies configuration to an object based on a local context. Returns the
	 * same object but with the configuration already applied.
	 *
	 * @param obj    the base object to configure
	 * @param action the action to execute
	 * @param <T>    object type
	 * @param <E>    error type
	 * @return the same object but with the configuration already applied
	 * @throws IllegalArgumentException error if {@code action} is {@code null}
	 */
	public static <T, E extends Throwable> @Nullable T alsoErrSafe(@Nullable T obj,
		UEmptyFunErr.@NotNull UEmptyFunErr1<T, E> action) {
		UObject.requireNotNull(action, "action");
		// Try to execute the action
		try {
			action.invoke(obj);
		} catch (Throwable e) {
			LOG.log(ULogger.logWarning(e));
		}
		return obj;
	}
	
	/**
	 * Applies configuration to an object based on a local context. Returns the
	 * same object but with the configuration already applied.
	 *
	 * @param obj    the base object to configure
	 * @param action the action to execute
	 * @param <T>    object type
	 * @param <E>    error type
	 * @return the same object but with the configuration already applied
	 * @throws IllegalArgumentException error if {@code obj} or {@code action} is {@code null}
	 */
	public static <T, E extends Throwable> @NotNull T alsoErrSafeNotNull(@NotNull T obj,
		UEmptyFunErr.@NotNull UEmptyFunErr1<T, E> action) {
		UObject.requireNotNull(obj, "obj");
		// Execute the method
		return alsoErrSafe(obj, action);
	}
	
	/* -----------------------------------------------------
	 * Apply methods
	 * ----------------------------------------------------- */
	
	/**
	 * Applies configuration to an object based on a local context. Returns a
	 * different object depending on the applied configuration.
	 *
	 * @param obj    the base object to configure
	 * @param action the action to execute
	 * @param <T>    object type
	 * @param <R>    result type
	 * @return a different object depending on the applied configuration.
	 * @throws IllegalArgumentException error if {@code action} is {@code null}
	 */
	public static <R, T> R apply(@Nullable T obj, UFun.@NotNull UFun1<R, T> action) {
		UObject.requireNotNull(action, "action");
		// Execute the action
		return action.invoke(obj);
	}
	
	/**
	 * Applies configuration to an object based on a local context. Returns a
	 * different object depending on the applied configuration.
	 *
	 * @param obj    the base object to configure
	 * @param action the action to execute
	 * @param <T>    object type
	 * @param <R>    result type
	 * @return a different object depending on the applied configuration.
	 * @throws IllegalArgumentException error if {@code obj} or {@code action} is {@code null}
	 */
	public static <R, T> R applyNotNull(@NotNull T obj, UFun.@NotNull UFun1<R, T> action) {
		UObject.requireNotNull(obj, "obj");
		// Execute method
		return apply(obj, action);
	}
	
	/**
	 * Applies configuration to an object based on a local context. Returns a
	 * different object depending on the applied configuration.
	 *
	 * @param obj    the base object to configure
	 * @param action the action to execute
	 * @param <T>    object type
	 * @param <R>    result type
	 * @param <E>    error type
	 * @return a different object depending on the applied configuration.
	 * @throws E                        error if something goes wrong
	 * @throws IllegalArgumentException error if {@code action} is {@code null}
	 */
	public static <R, T, E extends Throwable> R applyErr(@Nullable T obj, UFunErr.@NotNull UFunErr1<R, T, E> action) throws E {
		UObject.requireNotNull(action, "action");
		// Execute the action
		return action.invoke(obj);
	}
	
	/**
	 * Applies configuration to an object based on a local context. Returns a
	 * different object depending on the applied configuration.
	 *
	 * @param obj    the base object to configure
	 * @param action the action to execute
	 * @param <T>    object type
	 * @param <R>    result type
	 * @param <E>    error type
	 * @return a different object depending on the applied configuration.
	 * @throws IllegalArgumentException error if {@code obj} or {@code action} is {@code null}
	 * @throws E                        error if {@code action} goes wrong
	 */
	public static <R, T, E extends Throwable> R applyErrNotNull(@NotNull T obj,
		UFunErr.@NotNull UFunErr1<R, T, E> action) throws E {
		UObject.requireNotNull(obj, "obj");
		// Execute the action
		return applyErr(obj, action);
	}
	
	/**
	 * Applies configuration to an object based on a local context. Returns a
	 * different object depending on the applied configuration.
	 *
	 * @param obj    the base object to configure
	 * @param action the action to execute
	 * @param <T>    object type
	 * @param <R>    result type
	 * @param <E>    error type
	 * @return a different object depending on the applied configuration.
	 */
	public static <R, T, E extends Throwable> @NotNull Optional<R> applyErrSafe(@Nullable T obj,
		UFunErr.@NotNull UFunErr1<R, T, E> action) {
		try {
			return Optional.ofNullable(applyErr(obj, action));
		} catch (Throwable e) {
			LOG.log(ULogger.logWarning(e));
		}
		// Return an empty object
		return Optional.empty();
	}
	
	/**
	 * Applies configuration to an object based on a local context. Returns a
	 * different object depending on the applied configuration.
	 *
	 * @param obj    the base object to configure
	 * @param action the action to execute
	 * @param <T>    object type
	 * @param <R>    result type
	 * @param <E>    error type
	 * @return a different object depending on the applied configuration.
	 */
	public static <R, T, E extends Throwable> @NotNull Optional<R> applyErrSafeNotNull(@NotNull T obj,
		UFunErr.@NotNull UFunErr1<R, T, E> action) {
		try {
			return Optional.ofNullable(applyErrNotNull(obj, action));
		} catch (Throwable e) {
			LOG.log(ULogger.logWarning(e));
		}
		// Return an empty object
		return Optional.empty();
	}
	
}
