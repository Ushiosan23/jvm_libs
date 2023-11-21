package ushiosan.jvm.collections;

import org.jetbrains.annotations.NotNull;
import ushiosan.jvm.UObject;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Function;
import java.util.stream.Stream;

public final class UStack extends UCollection {
	
	/**
	 * This class cannot be instantiated directly
	 */
	private UStack() {}
	
	/* -----------------------------------------------------
	 * Stack methods
	 * ----------------------------------------------------- */
	
	/**
	 * Create a stack with all given elements.
	 *
	 * @param elements the elements to insert
	 * @param <T>      generic type list
	 * @return a linked list with all elements
	 */
	@SafeVarargs
	public static <T> @NotNull Stack<T> make(T @NotNull ... elements) {
		return makeImpl(new Stack<>(), elements);
	}
	
	/* -----------------------------------------------------
	 * Deque methods
	 * ----------------------------------------------------- */
	
	/**
	 * Create a deque with all given elements.
	 *
	 * @param elements the elements to insert
	 * @param <T>      generic type list
	 * @return a linked list with all elements
	 */
	@SafeVarargs
	public static <T> @NotNull Deque<T> makeDeque(T @NotNull ... elements) {
		return makeImpl(new ArrayDeque<>(), elements);
	}
	
	/**
	 * Create a deque with all given elements.
	 *
	 * @param elements the elements to insert
	 * @param <T>      generic type list
	 * @return a linked list with all elements
	 */
	@SafeVarargs
	public static <T> @NotNull Deque<T> makeLinkedDeque(T @NotNull ... elements) {
		return makeImpl(new LinkedBlockingDeque<>(), elements);
	}
	
	/**
	 * Create a deque with all given elements.
	 *
	 * @param elements the elements to insert
	 * @param <T>      generic type list
	 * @return a linked list with all elements
	 */
	@SafeVarargs
	public static <T> @NotNull Deque<T> makeConcurrentDeque(T @NotNull ... elements) {
		return makeImpl(new ConcurrentLinkedDeque<>(), elements);
	}
	
	/* -----------------------------------------------------
	 * Transform methods
	 * ----------------------------------------------------- */
	
	/**
	 * Converts a stack to another but with a different data type.
	 *
	 * @param original the original stack that you want to convert
	 * @param mapper   function in charge of transforming each element of the stack
	 * @param <T>      the original data type
	 * @param <R>      the target data type
	 * @return the new stack with the converted data
	 */
	public static <T, R> @NotNull Stack<R> transform(@NotNull Stack<T> original, @NotNull Function<T, R> mapper) {
		UObject.requireNotNull(original, "original");
		UObject.requireNotNull(mapper, "mapper");
		// Generate stack stream
		return original.stream()
			.map(mapper)
			.collect(Stack::new, Stack::push, Stack::addAll);
	}
	
	/**
	 * Converts one deque to another but with a different data type.
	 *
	 * @param original the original deque that you want to convert
	 * @param mapper   function in charge of transforming each element of the deque
	 * @param <T>      the original data type
	 * @param <R>      the target data type
	 * @return the new deque with the converted data
	 */
	public static <T, R> @NotNull Deque<R> transform(@NotNull Deque<T> original, @NotNull Function<T, R> mapper) {
		UObject.requireNotNull(original, "original");
		UObject.requireNotNull(mapper, "mapper");
		// Generate stack stream
		Stream<R> dequeStream = original.stream()
			.map(mapper);
		
		if (UObject.canCastNotNull(original, ConcurrentLinkedDeque.class)) {
			return dequeStream.collect(ConcurrentLinkedDeque::new, Deque::push, Deque::addAll);
		} else if (UObject.canCastNotNull(original, LinkedBlockingDeque.class)) {
			return dequeStream.collect(LinkedBlockingDeque::new, Deque::push, Deque::addAll);
		} else {
			return dequeStream.collect(ArrayDeque::new, Deque::push, Deque::addAll);
		}
	}
	
	/* -----------------------------------------------------
	 * Internal methods
	 * ----------------------------------------------------- */
	
	/**
	 * Create a stack with all given elements.
	 *
	 * @param stack    base stack
	 * @param elements the elements to insert
	 * @param <C>      generic stack type
	 * @param <T>      generic type list
	 * @return a linked list with all elements
	 */
	@SafeVarargs
	private static <T, C extends Vector<T>> @NotNull C makeImpl(@NotNull C stack, T @NotNull ... elements) {
		for (T item : elements) {
			stack.addElement(item);
		}
		return stack;
	}
	
	/**
	 * Create a deque with all given elements.
	 *
	 * @param deque    base deque
	 * @param elements the elements to insert
	 * @param <C>      generic deque type
	 * @param <T>      generic type list
	 * @return a linked list with all elements
	 */
	@SafeVarargs
	private static <T, C extends Deque<T>> @NotNull C makeImpl(@NotNull C deque, T @NotNull ... elements) {
		for (T item : elements) {
			deque.push(item);
		}
		return deque;
	}
	
}
