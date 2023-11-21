package ushiosan.jvm;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Logger;

public final class UR {
	
	/**
	 * Logger instance
	 */
	private static final Logger LOG =
		Logger.getLogger(ULogger.loggerName(UR.class));
	
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
	private static volatile UR instance;
	
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
		ClassLoader classLoader = UR.class.getClassLoader();
		initialize(classLoader);
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
		if (instance == null) {
			synchronized (UR.class) {
				if (instance == null) {
					instance = new UR();
				}
			}
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
		Throwable error;
		
		// Try to load resource
		try (InputStream stream = loader.getResourceAsStream(RESOURCE_FILE)) {
			// Check if resource is null
			if (stream == null) {
				String errorMsg = String.format("The resource \"%s\" not found", RESOURCE_FILE);
				throw new IllegalAccessException(errorMsg);
			}
			properties.load(stream);
			error = null;
		} catch (Exception e) {
			error = e;
		}
		
		if (error != null) {
			LOG.log(ULogger.logError(error));
		}
	}
	
}
