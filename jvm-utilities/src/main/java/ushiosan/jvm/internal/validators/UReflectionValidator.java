package ushiosan.jvm.internal.validators;

import org.jetbrains.annotations.NotNull;
import ushiosan.jvm.error.URecursiveCallException;

import java.util.Arrays;

public class UReflectionValidator extends UClassValidator {
	
	/**
	 * Checks if a recursive call is being made at a certain point in the program.
	 *
	 * @param cls        the class that you want to parse to check for recursion
	 * @param methodName the method that you want to search within the call stack
	 * @throws URecursiveCallException error if it is detected that the stack has
	 *                                 several calls of the same method recursively.
	 */
	public static void checkRecursiveCall(@NotNull Class<?> cls, @NotNull String methodName) throws URecursiveCallException {
		var traceElements = Thread.currentThread()
			.getStackTrace();
		var recursiveFilter = Arrays.stream(traceElements)
			.filter(it -> it.getClassName().contentEquals(cls.getName()) &&
						  it.getMethodName().contentEquals(methodName))
			.findAny();
		
		// Check recursive call
		if (recursiveFilter.isEmpty()) return;
		// Launch error
		var element = recursiveFilter.get();
		throw new URecursiveCallException(String.format(
			"A recursive call was detected when calling method \"%s\" of class \"%s\".",
			element.getMethodName(), element.getClassName()));
	}
	
}