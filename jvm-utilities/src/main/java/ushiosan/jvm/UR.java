package ushiosan.jvm;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

import java.util.Optional;
import java.util.Properties;
import java.util.logging.Logger;

import static ushiosan.jvm.ULogger.logError;
import static ushiosan.jvm.UObject.isNull;
import static ushiosan.jvm.UObject.requireNotNull;

public final class UR {
	
	private static final Logger LOG = ULogger.getLogger(UR.class);
	
	/* -----------------------------------------------------
	 * Constants
	 * ----------------------------------------------------- */
	
	/**
	 * Path of the resource within the library
	 */
	private static final String RESOURCE_FILE = "ushiosan/jvm/jvm-utilities.properties";
	
	/**
	 * Resource identifier in {@link java.util.ResourceBundle} format
	 */
	private static final String BUNDLE = "ushiosan.jvm.jvm-utilities";
	
	/* -----------------------------------------------------
	 * Properties
	 * ----------------------------------------------------- */
	
	/**
	 * Current class instance
	 */
	private static UR instance;
	
	/**
	 * Instance properties
	 */
	private final Properties properties;
	
	/**
	 * This class cannot be instantiated directly
	 */
	private UR() {
		properties = new Properties();
		// Initialize properties
		ClassLoader loader = ClassLoader.getSystemClassLoader();
		initialize(loader);
	}
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Gets the instance of the current class
	 *
	 * @return current instance class
	 */
	public static @NotNull UR getInstance() {
		if (isNull(instance)) {
			instance = new UR();
		}
		return instance;
	}
	
	/**
	 * Looks up the property value inside the library properties file and returns its result on success
	 *
	 * @param key the property you want to search for
	 * @return the value of the property or {@link Optional#empty()} if the property does not exist
	 */
	public @NotNull Optional<String> getProperty(@NotNull @PropertyKey(resourceBundle = BUNDLE) String key) {
		return Optional.ofNullable(properties.getProperty(key));
	}
	
	/**
	 * Looks up the property value inside the library properties file and returns its result on success
	 *
	 * @param key          the property you want to search for
	 * @param defaultValue default value if property not found
	 * @return the value of the property or {@code defaultValue} if the property does not exist
	 */
	public @NotNull String getProperty(@NotNull @PropertyKey(resourceBundle = BUNDLE) String key,
		@NotNull String defaultValue) {
		return getProperty(key).orElse(defaultValue);
	}
	
	/* -----------------------------------------------------
	 * Internal methods
	 * ----------------------------------------------------- */
	
	/**
	 * Initialize object properties
	 *
	 * @param loader the classloader of the current context
	 */
	private void initialize(@NotNull ClassLoader loader) {
		try (var stream = loader.getResourceAsStream(RESOURCE_FILE)) {
			// Check null object
			requireNotNull(stream, "stream");
			// Load properties
			properties.load(stream);
		} catch (Exception e) {
			logError(LOG, e);
		}
	}
	
}
