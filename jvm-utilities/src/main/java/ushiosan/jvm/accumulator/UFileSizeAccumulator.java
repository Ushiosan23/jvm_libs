package ushiosan.jvm.accumulator;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;

public class UFileSizeAccumulator implements UAccumulator<Long, Long> {
	
	/* -----------------------------------------------------
	 * Properties
	 * ----------------------------------------------------- */
	
	/**
	 * accumulator result
	 */
	private volatile long resultImpl = 0L;
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Gets the result of accumulated content
	 *
	 * @return all accumulated content
	 */
	@Override
	public synchronized @NotNull Long result() {
		return resultImpl;
	}
	
	/**
	 * Push more content to the current accumulator
	 *
	 * @param item The item you want to accumulate
	 */
	@Override
	public synchronized void push(@NotNull Long item) {
		resultImpl += item;
	}
	
	/**
	 * Adds the result of other accumulators with the same type
	 *
	 * @param other another accumulator
	 */
	@Override
	public void pushAll(@NotNull UAccumulator<Long, Long> other) {
		push(other.result());
	}
	
	/**
	 * Push more content to the current accumulator
	 *
	 * @param file The item you want to accumulate
	 */
	public void push(@NotNull File file) {
		if (file.exists()) push(file.length());
	}
	
	/**
	 * Push more content to the current accumulator
	 *
	 * @param path The item you want to accumulate
	 */
	public void push(@NotNull Path path) {
		try {
			if (Files.exists(path) || !Files.isDirectory(path)) {
				push(Files.size(path));
			}
		} catch (IOException ignore) {
		}
	}
	
	/**
	 * Push more content to the current accumulator
	 *
	 * @param entry The item you want to accumulate
	 */
	public void push(@NotNull ZipEntry entry) {
		long fSize = entry.getSize();
		if (fSize != -1L) push(fSize);
	}
	
}