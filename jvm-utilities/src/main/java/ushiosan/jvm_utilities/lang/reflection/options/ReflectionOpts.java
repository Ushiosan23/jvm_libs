package ushiosan.jvm_utilities.lang.reflection.options;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ushiosan.jvm_utilities.lang.Obj;
import ushiosan.jvm_utilities.lang.collection.Collections;
import ushiosan.jvm_utilities.lang.print.annotations.PrintOpts;

import java.lang.reflect.Member;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Data class for data reflection options
 *
 * @param <T> reflection data type
 */
@PrintOpts(privateFieldsAccess = true)
public final class ReflectionOpts<T extends Member> {
	
	/* ---------------------------------------------------------
	 * Properties
	 * --------------------------------------------------------- */
	
	/**
	 * Data option to get only elements declared by the class
	 */
	private boolean declaredOnly;
	
	/**
	 * Data option to get only public elements
	 */
	private boolean onlyPublic;
	
	/**
	 * Data option to perform the action recursively
	 */
	private boolean recursive;
	
	/**
	 * Data option to exclude abstracts elements
	 */
	private boolean skipAbstracts;
	
	/**
	 * List of predicates for the data filter
	 */
	private List<Predicate<T>> predicateList;
	
	/* ---------------------------------------------------------
	 * Constructors
	 * --------------------------------------------------------- */
	
	/**
	 * Default constructor
	 */
	public ReflectionOpts() {
		declaredOnly = false;
		onlyPublic = true;
		recursive = true;
		skipAbstracts = true;
		predicateList = Collections.mutableListOf();
	}
	
	/* ---------------------------------------------------------
	 * Methods
	 * --------------------------------------------------------- */
	
	/**
	 * Returns the default instance.
	 *
	 * @param <T> reflection data type
	 * @return default instance
	 */
	@Contract(" -> new")
	public static <T extends Member> @NotNull ReflectionOpts<T> getDefault() {
		return new ReflectionOpts<>();
	}
	
	/**
	 * Data option to get only elements declared by the class
	 *
	 * @return {@code declaredOnly} option status
	 */
	public boolean declaredOnly() {
		return declaredOnly;
	}
	
	/**
	 * Change the state of the class to handle information not defined by the class
	 *
	 * @param declaredOnly new status
	 * @return the same current object
	 */
	public @NotNull ReflectionOpts<T> setDeclaredOnly(boolean declaredOnly) {
		this.declaredOnly = declaredOnly;
		return this;
	}
	
	/**
	 * Data option to get only public elements
	 *
	 * @return {@code onlyPublic} option status
	 */
	public boolean onlyPublic() {
		return onlyPublic;
	}
	
	/**
	 * Change the state of the class to handle private data
	 *
	 * @param onlyPublic new status
	 * @return the same current object
	 */
	public @NotNull ReflectionOpts<T> setOnlyPublic(boolean onlyPublic) {
		this.onlyPublic = onlyPublic;
		return this;
	}
	
	/**
	 * Data option to perform the action recursively
	 *
	 * @return {@code recursive} option status
	 */
	public boolean recursive() {
		return recursive;
	}
	
	/**
	 * Change the state of the class to handle recursively data
	 *
	 * @param recursive new status
	 * @return the same current object
	 */
	public @NotNull ReflectionOpts<T> setRecursive(boolean recursive) {
		this.recursive = recursive;
		return this;
	}
	
	/**
	 * Data option to exclude abstracts elements
	 *
	 * @return {@code skipAbstracts} option status
	 */
	public boolean skipAbstracts() {
		return skipAbstracts;
	}
	
	/**
	 * Change the state of the class to handle abstract elements
	 *
	 * @param skipAbstracts new status
	 * @return the same current object
	 */
	public @NotNull ReflectionOpts<T> setSkipAbstracts(boolean skipAbstracts) {
		this.skipAbstracts = skipAbstracts;
		return this;
	}
	
	/**
	 * List of predicates for the data filter
	 *
	 * @return {@code predicateList} option status
	 */
	public @NotNull List<Predicate<T>> predicates() {
		return predicateList;
	}
	
	/**
	 * Add a new filter to the list of options
	 *
	 * @param predicates all the filters you want to add
	 * @return the same current object
	 */
	@SafeVarargs
	public final @NotNull ReflectionOpts<T> addPredicate(Predicate<T> @NotNull ... predicates) {
		predicateList.addAll(Arrays.asList(predicates));
		return this;
	}
	
	/**
	 * Add a new filter to the list of options.
	 * <p>
	 * The main difference between this method and the {@link #addPredicate(Predicate[])} method is
	 * that this method rewrites the filters already applied, while the {@link #addPredicate(Predicate[])} method
	 * adds more filters to the existing ones.
	 *
	 * @param predicates all the filters you want to add
	 * @return the same current object
	 */
	@SafeVarargs
	public final @NotNull ReflectionOpts<T> setPredicates(Predicate<T> @NotNull ... predicates) {
		predicateList = Collections.mutableListOf(predicates);
		return this;
	}
	
	/**
	 * Object string representation
	 *
	 * @return object string representation
	 */
	@Contract(pure = true)
	@Override
	public @NotNull String toString() {
		return Obj.toInstanceString(this);
	}
	
}
