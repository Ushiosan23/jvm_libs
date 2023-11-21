package ushiosan.jvm.platform;

import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ushiosan.jvm.UObject;
import ushiosan.jvm.UString;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Enumerated type for identification of the operating system on which the JVM is running.
 */
public enum UPlatform {
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
	UPlatform(@Nullable @RegExp String regex) {
		pattern = regex == null ? null :
				  Pattern.compile(regex);
	}
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Gets the current platform where the JVM is running
	 *
	 * @return the platform where the JVM runs or {@link #UNKNOWN} if the platform is not listed
	 */
	@SuppressWarnings("ConstantConditions")
	public static @NotNull UPlatform runningPlatform() {
		return fromOrUnknown(platformNameRaw(true));
	}
	
	/**
	 * Gets the platform depending on the input
	 *
	 * @param name the name of the platform you want to get
	 * @return the instance of the platform element or {@link Optional#empty()} if the platform not exists
	 */
	@SuppressWarnings("ConstantConditions")
	public static @NotNull Optional<UPlatform> from(@NotNull String name) {
		UObject.requireNotNull(name, "name");
		// Platform temporal variables
		String validName = name.trim().toLowerCase();
		UPlatform[] validPlatforms = Arrays.stream(values())
			.filter(it -> UObject.isNotNull(it.pattern))
			.toArray(UPlatform[]::new);
		
		// Check if any of the platform name is on the valid platform array
		for (var platform : validPlatforms) {
			Matcher matcher = platform.pattern.matcher(validName);
			if (matcher.find()) return Optional.of(platform);
		}
		
		return Optional.empty();
	}
	
	
	/**
	 * Gets the platform depending on the input
	 *
	 * @param name the name of the platform you want to get
	 * @return the instance of the platform element or {@link #UNKNOWN} if the platform is not listed
	 */
	public static @NotNull UPlatform fromOrUnknown(@NotNull String name) {
		return from(name).orElse(UNKNOWN);
	}
	
	/**
	 * Gets the current platform name
	 *
	 * @return current platform name
	 */
	private static @NotNull String platformNameRaw(boolean lowerCase) {
		String content = System.getProperty("os.name");
		return lowerCase ? content.toLowerCase() :
			   content;
	}
	
	/* -----------------------------------------------------
	 * Static methods
	 * ----------------------------------------------------- */
	
	/**
	 * Determine if current platform is a {@code UNIX} like operating system
	 * <p>
	 * Example:
	 * <pre>{@code
	 * UPlatform current = Platform.runningPlatform();
	 * // Check if is a unix-like platform
	 * boolean result = current.isUnix();
	 * }</pre>
	 *
	 * @return {@code true} if a platform is unix-like or {@code false} otherwise
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
	 * Gets the current platform name
	 *
	 * @return current platform name or {@code Unknown} if the platform is {@link #UNKNOWN}
	 */
	public @NotNull String platformName() {
		// Ignore unknown platforms
		if (this == UNKNOWN) return "Unknown";
		// Get platform name
		return UString.capitalizeWord(name()).toString();
	}
	
	/**
	 * Gets the current platform version
	 *
	 * @return current platform version or {@link Optional#empty()} if the platform is {@link #UNKNOWN}
	 */
	public Optional<String> platformVersion() {
		// Discard unknown platforms
		if (this == UNKNOWN) return Optional.empty();
		return Optional.of(System.getProperty("os.version"));
	}
	
}
