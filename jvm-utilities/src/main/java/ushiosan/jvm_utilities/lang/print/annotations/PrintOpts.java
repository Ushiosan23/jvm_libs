package ushiosan.jvm_utilities.lang.print.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.intellij.lang.annotations.RegExp;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PrintOpts {

	/**
	 * Print instance short name
	 *
	 * @return short name status
	 */
	boolean shortName() default true;

	/**
	 * Option to give access to the internal elements of an instance.
	 *
	 * @return {@code true} if inner elements can be accessed or {@code false} otherwise
	 */
	boolean privateFieldsAccess() default false;

	/**
	 * Option to give access to the getter elements of an instance.
	 *
	 * @return {@code true} if getter elements can be accessed or {@code false} otherwise
	 */
	boolean getterAccess() default false;

	/**
	 * Prefix regular expression indicating which methods will be accessible by name
	 *
	 * @return the regular expression to identify all valid methods
	 */
	@RegExp String getterPrefix() default "^(get|is)";

	/**
	 * Suffix regular expression indicating which methods will be accessible by name
	 *
	 * @return the regular expression to identify all valid methods
	 */
	@RegExp String getterSuffix() default "";

}
