package ushiosan.jvm.error;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ushiosan.jvm.UObject;
import ushiosan.jvm.content.UPair;

public abstract class UCommonErrorMessages {
	
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
	 * Pair of values used to generate error messages when a value is null
	 */
	private static final UPair<String, String> UO_REQUIRE_NOT_NULL =
		UPair.make("The parameter must not be a <null> value.",
				   "The parameter \"%s\" must not be a <null> value.");
	
	/**
	 * Pair of values used to generate error messages when a property was not found
	 */
	private static final UPair<String, String> UC_PROPERTY_NOT_FOUND =
		UPair.make("The specified property was not found.",
				   "The property \"%s\" was not found");
	
	/**
	 * This class cannot be instantiated directly
	 */
	private UCommonErrorMessages() {}
	
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
		return (parameter == null) ? UO_REQUIRE_NOT_NULL.first :
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
		UObject.requireNotNull(expected, "expected");
		UObject.requireNotNull(actual, "actual");
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
		UObject.requireNotNull(scheme, "scheme");
		// Generate message content
		return String.format(FS_SCHEME_NOT_SUPPORTED, scheme);
	}
	
	/**
	 * Generates a formatted error message when the property was not found
	 *
	 * @param property the name of the property that was not found
	 * @return formatted error message
	 */
	public static String propertyNotFoundError(@Nullable String property) {
		// Generate message content
		return (property == null) ? UC_PROPERTY_NOT_FOUND.first :
			   String.format(UC_PROPERTY_NOT_FOUND.second, property);
	}
	
}
