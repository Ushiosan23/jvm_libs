package com.github.ushiosan23.jvm.system.os;

import com.github.ushiosan23.jvm.base.StringUtils;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Arch {
	X64("(x64|amd64|sparc|ppc|aarch64)"),
	X86("(x86|i386)"),
	ARM("(arm)"),
	UNKNOWN;

	/* ------------------------------------------------------------------
	 * Properties
	 * ------------------------------------------------------------------ */

	/**
	 * Regular expression to verify arch
	 */
	@Nullable
	private final Pattern pattern;

	/* ------------------------------------------------------------------
	 * Constructors
	 * ------------------------------------------------------------------ */

	/**
	 * Default constructor
	 *
	 * @param regex Regular expression to verify arch
	 */
	Arch(@Nullable @RegExp String regex) {
		pattern = regex == null ? null : Pattern.compile(regex);
	}

	/**
	 * Null constructor
	 */
	Arch() {
		this(null);
	}

	/* ------------------------------------------------------------------
	 * Methods
	 * ------------------------------------------------------------------ */

	/**
	 * Returns the current architecture of the processor.
	 * This object does not return any specifications, it only defines what type of architecture it is (x86, x64, ARM).
	 *
	 * @return Returns the current architecture of the device processor
	 * @see Arch#X64
	 * @see Arch#X86
	 * @see Arch#ARM
	 * @see Arch#UNKNOWN
	 */
	public static Arch getCurrent() {
		// Get all arch values
		Arch[] arches = values();
		String archValue = System
			.getProperty("os.arch")
			.toLowerCase(Locale.ROOT);
		// Iterate all
		for (Arch arch : arches) {
			// Ignore all null patterns
			if (arch.pattern == null) continue;
			// Match result
			Matcher matcher = arch.pattern.matcher(archValue);
			if (matcher.find()) return arch;
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
