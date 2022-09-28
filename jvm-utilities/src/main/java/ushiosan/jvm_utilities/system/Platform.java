package ushiosan.jvm_utilities.system;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ushiosan.jvm_utilities.lang.Obj;
import ushiosan.jvm_utilities.lang.print.annotations.PrintOpts;

@PrintOpts(getterAccess = true, getterPrefix = "^(is|name)")
public enum Platform {
	/**
	 * Free BSD operating system family
	 */
	FREE_BSD("^(freebsd|free)"),

	/**
	 * Linux operating system family (distros)
	 */
	LINUX("^(linux)"),

	/**
	 * Mac operating system family (IOS not supported)
	 */
	MACOS("^(mac)"),

	/**
	 * Solaris operating system family
	 */
	SOLARIS("^(sunos|sun)"),

	/**
	 * Windows operating system family
	 */
	WINDOWS("^(windows|win)"),

	/**
	 * Unknown platform. Used only to represent an error
	 */
	UNKNOWN(null);

	/* -----------------------------------------------------
	 * Properties
	 * ----------------------------------------------------- */

	/**
	 * Platform pattern
	 */
	private final @Nullable Pattern pattern;

	/* -----------------------------------------------------
	 * Constructors
	 * ----------------------------------------------------- */

	/**
	 * Constructor with regular expression
	 *
	 * @param regex target regular expression to detect the platform
	 */
	Platform(@Nullable @RegExp String regex) {
		pattern = regex != null ? Pattern.compile(regex) : null;
	}

	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */

	/**
	 * Returns the current platform where the JVM is running
	 *
	 * @return Returns the platform where the JVM runs or {@link #UNKNOWN} if the platform is not listed
	 */
	@SuppressWarnings("ConstantConditions")
	public static Platform getRunningPlatform() {
		// Temporal variables
		String nativeOs = System.getProperty("os.name")
			.toLowerCase();
		Platform[] allValidPlatforms = Arrays.stream(values())
			.filter(it -> it.pattern != null)
			.toArray(Platform[]::new);

		// Iterate all platforms
		for (Platform platform : allValidPlatforms) {
			Matcher matcher = platform.pattern.matcher(nativeOs);
			if (matcher.find())
				return platform;
		}

		return UNKNOWN;
	}

	/**
	 * Determine if current platform is a {@code UNIX} like operating system
	 * <p>
	 * Example:
	 * <pre>{@code
	 * Platform current = Platform.getRunningPlatform();
	 * // Check if is a unix-like platform
	 * boolean result = current.isUnix();
	 * }</pre>
	 *
	 * @return {@code true} if platform is unix-like or {@code false} otherwise
	 */
	public boolean isUnix() {
		switch (this) {
			case FREE_BSD:
			case LINUX:
			case MACOS:
			case SOLARIS:
				return true;
			default:
				return false;
		}
	}

	/**
	 * Get current platform name
	 *
	 * @return current platform name or {@code Unknown} if the platform is {@link #UNKNOWN}
	 */
	@Contract(pure = true)
	public @NotNull String getPlatformName() {
		// Ignore unknown platforms
		if (this == UNKNOWN) return "Unknown";
		// Get platform name
		return System.getProperty("os.name");
	}

	/**
	 * Get current platform version
	 *
	 * @return current platform version or {@link Optional#empty()} if the platform is {@link #UNKNOWN}
	 */
	public Optional<String> getVersion() {
		// Discard unknown platforms
		if (this == UNKNOWN) return Optional.empty();
		return Optional.of(System.getProperty("os.version"));
	}

	/* -----------------------------------------------------
	 * Static methods
	 * ----------------------------------------------------- */

	/**
	 * Object string representation
	 *
	 * @return object string representation
	 */
	public @NotNull String toString() {
		return Obj.toInstanceString(this);
	}

}
