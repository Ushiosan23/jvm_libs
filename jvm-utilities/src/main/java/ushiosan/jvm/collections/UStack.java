package ushiosan.jvm.collections;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.LinkedBlockingDeque;

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
		return makeImpl(new ArrayDeque<>(elements.length), elements);
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
		return makeImpl(new LinkedBlockingDeque<>(elements.length), elements);
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
