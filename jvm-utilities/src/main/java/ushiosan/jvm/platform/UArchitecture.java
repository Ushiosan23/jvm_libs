package ushiosan.jvm.platform;

import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ushiosan.jvm.UObject;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Enumerated type for listing the processor architectures of the
 * platform on which the JVM is running
 */
public enum UArchitecture {
	/**
	 * ARM architecture (The new Apple devices run in this architecture)
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
	@Nullable
	private final Pattern pattern;
	
	/* -----------------------------------------------------
	 * Constructor
	 * ----------------------------------------------------- */
	
	/**
	 * Constructor for specific architecture
	 *
	 * @param regex regular expression detector
	 */
	UArchitecture(@Nullable @RegExp String regex) {
		pattern = regex == null ? null :
				  Pattern.compile(regex);
	}
	
	/* -----------------------------------------------------
	 * Static methods
	 * ----------------------------------------------------- */
	
	/**
	 * Gets the current platform architecture
	 *
	 * @return the current platform architecture
	 */
	public static @NotNull String platformRawArch() {
		return System.getProperty("os.arch");
	}
	
	/**
	 * Gets the current architecture where the JVM is running
	 *
	 * @return the architecture where the JVM runs or {@link #UNKNOWN} if the
	 * 	architecture is not listed
	 */
	@SuppressWarnings("DataFlowIssue")
	public static @NotNull UArchitecture platformRunningArch() {
		// Temporal variables
		String nativeArch = platformRawArch();
		UArchitecture[] allValidArch = Arrays.stream(values())
			.filter(it -> UObject.isNotNull(it.pattern))
			.toArray(UArchitecture[]::new);
		
		// Iterate all architectures
		for (var arch : allValidArch) {
			// The editor detects this line as a possible candidate for
			// launch a "NullPointerException" error when it was previously verified
			// that this is not possible.
			// For that reason, an annotation "SuppressWarnings("DataFlowIssue")" is used
			// which removes such a warning.
			Matcher matcher = arch.pattern.matcher(nativeArch);
			if (matcher.find()) {
				return arch;
			}
		}
		
		return UNKNOWN;
	}
	
}
