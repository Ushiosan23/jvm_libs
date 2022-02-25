package com.github.ushiosan23.jvm.system.os;

import com.github.ushiosan23.jvm.base.StringUtils;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Platform {
	/**
	 * Linux platform
	 */
	LINUX("^(linux)"),
	/**
	 * Any macOS platform
	 */
	MACOS("^(mac os|mac)"),
	/**
	 * Solaris platform
	 */
	SOLARIS("^(sunos|sun)"),
	/**
	 * Windows platform
	 */
	WINDOWS("^(windows|win)"),
	/**
	 * Free BSD platform
	 */
	FREE_BSD("^(freebsd|free)"),
	/**
	 * Unknown platform. Used only to represent an error
	 */
	UNKNOWN;

	/* ------------------------------------------------------------------
	 * Properties
	 * ------------------------------------------------------------------ */

	/**
	 * Regular expression to verify platform
	 */
	@Nullable
	private final Pattern pattern;

	/* ------------------------------------------------------------------
	 * Constructors
	 * ------------------------------------------------------------------ */

	/**
	 * Default constructor
	 *
	 * @param regex Regular expression to verify platform
	 */
	Platform(@Nullable @RegExp String regex) {
		pattern = regex == null ? null : Pattern.compile(regex);
	}

	/**
	 * Null constructor
	 */
	Platform() {
		this(null);
	}

	/* ------------------------------------------------------------------
	 * Methods
	 * ------------------------------------------------------------------ */

	/**
	 * Determine if the platform is unix-like.
	 * <p>
	 * Example:
	 * <pre>{@code
	 * Platform current = Platform.getCurrent();
	 * // Check unix platform
	 * boolean isUnix = current.isUnix();
	 * }</pre>
	 *
	 * @return Returns {@code true} if platform is unix-like or {@code false} otherwise
	 */
	public boolean isUnix() {
		switch (this) {
			case LINUX:
			case MACOS:
			case SOLARIS:
			case FREE_BSD:
				return true;
			default:
				return false;
		}
	}

	/**
	 * Returns the current platform where the JVM is running
	 *
	 * @return Returns the platform where the JVM runs or {@link #UNKNOWN} if the platform is not listed
	 */
	public static Platform getCurrent() {
		// Get all platforms
		Platform[] platforms = values();
		String osName = System.getProperty("os.name").toLowerCase(Locale.ROOT);
		// Iterate all
		for (Platform platform : platforms) {
			// Ignore null patterns
			if (platform.pattern == null) continue;
			// Match result
			Matcher matcher = platform.pattern.matcher(osName);
			if (matcher.find()) return platform;
		}
		return UNKNOWN;
	}

	/**
	 * Object string representation
	 *
	 * @return Object string representation
	 */
	@Override
	public @NotNull String toString() {
		return StringUtils.capitalize(
			super.toString()
				.replace("_", " ")
				.toLowerCase(Locale.ROOT)
		);
	}

}
