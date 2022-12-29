package ushiosan.jvm_utilities.lang.io;

import org.jetbrains.annotations.NotNull;

import java.util.zip.ZipEntry;

public final class ZipUtils {
	
	/**
	 * Private constructor
	 */
	private ZipUtils() {
	}
	
	/* ---------------------------------------------------------
	 * Methods
	 * --------------------------------------------------------- */
	
	/**
	 * Check if the input is a regular file
	 *
	 * @param entry the entry to analyze
	 * @return {@code true} if entry is a regular file or {@code false} otherwise
	 */
	public static boolean isRegularFile(@NotNull ZipEntry entry) {
		return !entry.isDirectory();
	}
	
}
