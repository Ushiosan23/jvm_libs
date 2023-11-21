package ushiosan.jvm.internal.validators;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ushiosan.jvm.UObject;
import ushiosan.jvm.collections.UArray;
import ushiosan.jvm.content.UPair;
import ushiosan.jvm.function.UFun;

import java.lang.ref.WeakReference;
import java.util.Optional;
import java.util.function.Predicate;

public abstract class UObjectValidators {
	
	/**
	 * Object pairs used to check that objects are null
	 */
	public static final UPair<Class<?>, UFun.UFun1<Boolean, Object>>[] NULLABLE_VALIDATORS = UArray.make(
		UPair.make(WeakReference.class, obj -> ((WeakReference<?>) obj).get() != null),
		UPair.make(Optional.class, obj -> ((Optional<?>) obj).isEmpty()));
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Method to validate an object multiple times and that all the conditions are correct or
	 * incorrect depending on the "inverted" parameter.
	 *
	 * @param object     the object to be analyzed
	 * @param inverted   option used to check the reverse process of the filters
	 * @param predicates all the filters that you want to apply to the object
	 * @param <T>        the type of object you want to scan
	 * @return {@code true} if all conditions were valid or {@code false} otherwise.
	 */
	@SafeVarargs
	public static <T> boolean validate(@Nullable T object, boolean inverted, Predicate<T> @NotNull ... predicates) {
		// Temporal variables
		boolean result = !inverted;
		
		for (var predicate : predicates) {
			if (!predicate.test(object)) {
				result = inverted;
				break;
			}
		}
		return result;
	}
	
	/**
	 * Method to validate an object multiple times and that all the conditions are correct or
	 * incorrect depending on the "inverted" parameter.
	 *
	 * @param object     the object to be analyzed
	 * @param inverted   option used to check the reverse process of the filters
	 * @param predicates all the filters that you want to apply to the object
	 * @param <T>        the type of object you want to scan
	 * @return {@code true} if all conditions were valid or {@code false} otherwise.
	 */
	@SafeVarargs
	public static <T> boolean validateNotNull(@NotNull T object, boolean inverted, Predicate<T> @NotNull ... predicates) {
		UObject.requireNotNull(object, "object");
		return validate(object, inverted, predicates);
	}
	
}
