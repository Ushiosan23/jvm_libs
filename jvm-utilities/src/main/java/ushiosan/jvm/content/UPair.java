package ushiosan.jvm.content;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import static ushiosan.jvm.UObject.cast;
import static ushiosan.jvm.UObject.requireNotNull;

/**
 * Immutable object used to represent elements with 2 values (element pair).
 * It is an alternative to "input" type objects because its value is read-only.
 *
 * @param <F> first element type
 * @param <S> second element type
 */
public class UPair<F, S> {
	
	/* -----------------------------------------------------
	 * Properties
	 * ----------------------------------------------------- */
	
	/**
	 * First pair element
	 */
	public final F first;
	
	/**
	 * Second pair element
	 */
	public final S second;
	
	/* -----------------------------------------------------
	 * Constructors
	 * ----------------------------------------------------- */
	
	/**
	 * Default constructor
	 *
	 * @param first  first element
	 * @param second second element
	 */
	public UPair(F first, S second) {
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
	 * @return instance of {@link UPair} class
	 */
	@Contract(value = "_, _ -> new", pure = true)
	public static <T, K> @NotNull UPair<T, K> of(T first, K second) {
		return new UPair<>(first, second);
	}
	
	/**
	 * Copies the content of a map entry and converts it to a {@link UPair} object.
	 *
	 * @param entry the entry to copy
	 * @param <T>   first element type
	 * @param <K>   second element type
	 * @return an instance of {@link UPair} with entry content
	 */
	@Contract("_ -> new")
	public static <T, K> @NotNull UPair<T, K> copyOf(Map.@NotNull Entry<T, K> entry) {
		requireNotNull(entry, "entry");
		return of(entry.getKey(), entry.getValue());
	}
	
	/* -----------------------------------------------------
	 * Static methods
	 * ----------------------------------------------------- */
	
	/**
	 * Extracts all map entries and converts them to {@link UPair} objects.
	 *
	 * @param map the map to inspect
	 * @param <T> first element type
	 * @param <K> second element type
	 * @return an array with all map pairs
	 */
	public static <T, K> UPair<T, K> @NotNull [] extractPairs(@NotNull Map<T, K> map) {
		requireNotNull(map, "map");
		// Temporal variables
		int counter = 0;
		UPair<T, K>[] result = cast(new UPair[map.size()]);
		
		// Iterate all map
		for (Map.Entry<T, K> current : map.entrySet()) {
			result[counter++] = copyOf(current);
		}
		return result;
	}
	
	/**
	 * Extracts all-properties entries and converts them to {@link UPair} objects.
	 *
	 * @param properties the properties to inspect
	 * @return an array with all property pairs
	 */
	public static UPair<String, String> @NotNull [] extractPairs(@NotNull Properties properties) {
		requireNotNull(properties, "properties");
		// Temporal variables
		UPair<String, String>[] result = cast(new UPair[properties.size()]);
		int counter = 0;
		
		// Iterate all map
		for (var current : properties.entrySet()) {
			result[counter++] = cast(UPair.copyOf(current));
		}
		return result;
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
		
		var pair = (UPair<?, ?>) obj;
		return Objects.equals(first, pair.first) &&
			   Objects.equals(second, pair.second);
	}
	
}
