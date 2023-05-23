package ushiosan.jvm.internal.error;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ushiosan.jvm.content.UPair;

import static ushiosan.jvm.UObject.isNull;
import static ushiosan.jvm.UObject.requireNotNull;

public abstract class UCommonMessages {
	
	/**
	 * Pair of values used to generate error messages when a value is null
	 */
	private static final UPair<String, String> UO_REQUIRE_NOT_NULL =
		UPair.of("The parameter must not be a <null> value.",
				 "The parameter \"%s\" must not be a <null> value.");
	
	/* -----------------------------------------------------
	 * Properties
	 * ----------------------------------------------------- */
	/**
	 * Text used to define error messages for invalid resources
	 */
	private static final String FS_TYPE_ERROR =
		"The resource is not a valid \"%s\" type. \"%s\" given";
	/**
	 * Text used when some scheme is not valid or is not supported by the current element
	 */
	private static final String FS_SCHEME_NOT_SUPPORTED =
		"Scheme \"%s\" is not supported.";
	
	/**
	 * This class cannot be instantiated directly
	 */
	private UCommonMessages() {}
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Generates a formatted error message when a value is null
	 *
	 * @param parameter name of the parameter you want to check
	 * @return formatted error message
	 */
	public static String requireNotNullError(@Nullable String parameter) {
		// Generate message
		return isNull(parameter) ? UO_REQUIRE_NOT_NULL.first :
			   String.format(UO_REQUIRE_NOT_NULL.second, parameter);
	}
	
	/**
	 * Generates a formatted error message for resources with invalid types
	 *
	 * @param expected expected resource type
	 * @param actual   the type of data received
	 * @return formatted error message
	 */
	public static String resourceTypeError(@NotNull String expected, @NotNull String actual) {
		requireNotNull(expected, "expected");
		requireNotNull(actual, "actual");
		// Generate message content
		return String.format(FS_TYPE_ERROR, expected, actual);
	}
	
	/**
	 * Generates a formatted error message when a scheme is not valid or is not
	 * supported by the current element
	 *
	 * @param scheme the unsupported scheme
	 * @return formatted error message
	 */
	public static String schemeNotSupportedError(@NotNull String scheme) {
		requireNotNull(scheme, "scheme");
		// Generate message content
		return String.format(FS_SCHEME_NOT_SUPPORTED, scheme);
	}
	
}
