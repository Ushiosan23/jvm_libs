package ushiosan.jvm_utilities.lang.collection.elements;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ushiosan.jvm_utilities.lang.Obj;
import ushiosan.jvm_utilities.lang.print.annotations.PrintOpts;

import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import static ushiosan.jvm_utilities.lang.Obj.toInstanceString;

/**
 * Immutable object used to represent elements with 2 values (element pair).
 * It is an alternative to "input" type objects because its value is read-only.
 *
 * @param <T> first element type
 * @param <K> second element type
 */
@PrintOpts
public class Pair<T, K> {
	
	/**
	 * First pair element
	 */
	public final T first;
	
	/**
	 * Second pair element
	 */
	public final K second;
	
	/* -----------------------------------------------------
	 * Constructors
	 * ----------------------------------------------------- */
	
	/**
	 * Default constructor
	 *
	 * @param first  first element
	 * @param second second element
	 */
	public Pair(T first, K second) {
		this.first = first;
		this.second = second;
	}
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Shortcut pair instance
	 *
	 * @param first  first value
	 * @param second second value
	 * @param <T>    generic first type
	 * @param <K>    generic second type
	 * @return instance of {@link Pair} class
	 */
	@Contract(value = "_, _ -> new", pure = true)
	public static <T, K> @NotNull Pair<T, K> of(T first, K second) {
		return new Pair<>(first, second);
	}
	
	/**
	 * Copies the content of a map entry and converts it to a {@link Pair} object.
	 *
	 * @param entry the entry to copy
	 * @param <T>   first element type
	 * @param <K>   second element type
	 * @return an instance of {@link Pair} with entry content
	 */
	@Contract("_ -> new")
	public static <T, K> @NotNull Pair<T, K> copyOf(Map.@NotNull Entry<T, K> entry) {
		return of(entry.getKey(), entry.getValue());
	}
	
	/**
	 * Extracts all map entries and converts them to {@link Pair} objects.
	 *
	 * @param map the map to inspect
	 * @param <T> first element type
	 * @param <K> second element type
	 * @return an array with all map pairs
	 */
	@SuppressWarnings("unchecked")
	public static <T, K> Pair<T, K> @NotNull [] extractPairs(@NotNull Map<T, K> map) {
		// Temporal variables
		int counter = 0;
		Pair<T, K>[] result = (Pair<T, K>[]) new Pair[map.size()];
		
		// Iterate all map
		for (Map.Entry<T, K> current : map.entrySet()) {
			result[counter++] = copyOf(current);
		}
		return result;
	}
	
	/**
	 * Extracts all properties entries and converts them to {@link Pair} objects.
	 *
	 * @param props the properties to inspect
	 * @return an array with all property pairs
	 */
	public static Pair<String, String> @NotNull [] extractPairs(@NotNull Properties props) {
		// Temporal variables
		int counter = 0;
		Pair<String, String>[] result = Obj.cast(new Pair[props.size()]);
		
		// Iterate all map
		for (var current : props.entrySet()) {
			result[counter++] = Obj.cast(Pair.copyOf(current));
		}
		return result;
	}
	
	/**
	 * Indicates whether some other object is "equal to" this one.
	 * <p>
	 *
	 * @param obj the reference object with which to compare.
	 * @return {@code true} if this object is the same as the obj
	 * 	argument; {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		
		Pair<?, ?> pair = (Pair<?, ?>) obj;
		
		if (!Objects.equals(first, pair.first)) return false;
		return Objects.equals(second, pair.second);
	}
	
	/**
	 * Returns a hash code value for the object. This method is supported for the
	 * benefit of hash tables such as those provided by {@link java.util.HashMap}.
	 *
	 * @return a hash code value for this object.
	 */
	@Override
	public int hashCode() {
		int result = first != null ? first.hashCode() : 0;
		result = 31 * result + (second != null ? second.hashCode() : 0);
		return result;
	}
	
	/**
	 * Object string representation
	 *
	 * @return object string representation
	 */
	@Override
	public String toString() {
		return toInstanceString(this);
	}
	
}
