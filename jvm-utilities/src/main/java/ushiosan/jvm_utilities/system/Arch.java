package ushiosan.jvm_utilities.system;

import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ushiosan.jvm_utilities.lang.Obj;
import ushiosan.jvm_utilities.lang.print.annotations.PrintOpts;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@PrintOpts(getterAccess = true, getterPrefix = "^(is|name)")
public enum Arch {
	
	/**
	 * ARM architecture (The new Apple devices runs in this architecture)
	 */
	ARM("(arm)"),
	
	/**
	 * 64-bit architecture
	 */
	X64("(x64|amd64|sparc|ppc|aarch64)"),
	
	/**
	 * 32-bit architecture
	 */
	X86("(x86|i386)"),
	
	/**
	 * Unknown architecture. Used only to represent an error
	 */
	UNKNOWN(null);
	
	/* -----------------------------------------------------
	 * Properties
	 * ----------------------------------------------------- */
	
	/**
	 * Regular expression used to detect
	 * the device architecture
	 */
	private final @Nullable Pattern pattern;
	
	/* -----------------------------------------------------
	 * Constructor
	 * ----------------------------------------------------- */
	
	/**
	 * Constructor for specific architecture
	 *
	 * @param regex regular expression detector
	 */
	Arch(@Nullable @RegExp String regex) {
		pattern = regex != null ? Pattern.compile(regex) : null;
	}
	
	/**
	 * Gets the current platform architecture
	 *
	 * @return the current platform architecture
	 */
	public static String getRawArch() {
		return System.getProperty("os.arch");
	}
	
	/* -----------------------------------------------------
	 * Static methods
	 * ----------------------------------------------------- */
	
	/**
	 * Returns the current architecture where the JVM is running
	 *
	 * @return Returns the architecture where the JVM runs or UNKNOWN if the
	 * 	architecture is not listed
	 */
	@SuppressWarnings("ConstantConditions")
	public static Arch getRunningArch() {
		// Temporal variables
		String nativeArch = getRawArch();
		Arch[] allValidArch = Arrays.stream(values())
			.filter(Obj::isNotNull)
			.toArray(Arch[]::new);
		
		// Iterate all architectures
		for (Arch arch : allValidArch) {
			Matcher matcher = arch.pattern.matcher(nativeArch);
			if (matcher.find()) {
				return arch;
			}
		}
		
		return UNKNOWN;
	}
	
	/**
	 * Object string representation
	 *
	 * @return object string representation
	 */
	@Override
	public @NotNull String toString() {
		return Obj.toInstanceString(this);
	}
	
}
