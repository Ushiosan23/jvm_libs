package ushiosan;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Assertions;
import ushiosan.jvm.UAction;
import ushiosan.jvm.function.UEmptyFun;
import ushiosan.jvm.function.UEmptyFunErr;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.nio.file.Path;

public abstract class UTestUnit {
	
	private static final String FILE_NAME = "UTestUnit.java";
	private static final String MODULE_NAME = "ushiosan.jvm.utilities";
	
	private static final JFileChooser fileChooser =
		new JFileChooser(Path.of(System.getProperty("user.home")).toFile());
	
	/* -----------------------------------------------------
	 * Print methods
	 * ----------------------------------------------------- */
	
	protected static void printSection(@Nullable String title) {
		printSectionImpl(title, 4);
	}
	
	protected static void printSection() {
		printSectionImpl(null, 4);
	}
	
	/* -----------------------------------------------------
	 * Section generators
	 * ----------------------------------------------------- */
	
	private static void sectionOf(int index, boolean blankAtEnd, @Nullable String title, @NotNull UEmptyFun action) {
		printSectionImpl(title, index);
		System.out.println();
		
		// Measure call time
		long star = System.nanoTime();
		// Execute action
		action.invoke();
		// Show measure information
		long end = (System.nanoTime() - star) / 1_000_000L;
		System.err.printf("%nThe test took %dms to execute%n", end);
		
		if (blankAtEnd) System.out.println();
	}
	
	protected static void sectionOf(boolean blankAtEnd, @Nullable String title, @NotNull UEmptyFun action) {
		int idx = getParentCallStack();
		sectionOf(idx, blankAtEnd, title, action);
	}
	
	protected static void sectionOf(boolean blankAtEnd, @NotNull UEmptyFun action) {
		int idx = getParentCallStack();
		sectionOf(idx, blankAtEnd, null, action);
	}
	
	protected static void sectionOf(@NotNull UEmptyFun action) {
		int idx = getParentCallStack();
		sectionOf(idx, true, null, action);
	}
	
	/* -----------------------------------------------------
	 * Section error generators
	 * ----------------------------------------------------- */
	
	private static <E extends Throwable> void sectionErrOf(int index, boolean blankAtEnd, @Nullable String title,
		@NotNull UEmptyFunErr<E> action) throws E {
		printSectionImpl(title, index);
		System.out.println();
		
		// Measure call time
		long star = System.nanoTime();
		// Execute action
		action.invoke();
		// Show measure information
		long end = (System.nanoTime() - star) / 1_000_000L;
		System.err.printf("%nThe test took %dms to execute%n", end);
		
		if (blankAtEnd) System.out.println();
	}
	
	protected static <E extends Throwable> void sectionErrOf(boolean blankAtEnd, @Nullable String title,
		@NotNull UEmptyFunErr<E> action) throws E {
		int idx = getParentCallStack();
		sectionErrOf(idx, blankAtEnd, title, action);
	}
	
	protected static <E extends Throwable> void sectionErrOf(boolean blankAtEnd, @NotNull UEmptyFunErr<E> action) throws E {
		int idx = getParentCallStack();
		sectionErrOf(idx, blankAtEnd, null, action);
	}
	
	protected static <E extends Throwable> void sectionErrOf(@NotNull UEmptyFunErr<E> action) throws E {
		int idx = getParentCallStack();
		sectionErrOf(idx, true, null, action);
	}
	
	/* -----------------------------------------------------
	 * File chooser methods
	 * ----------------------------------------------------- */
	
	protected static @NotNull File requireFile(String @NotNull ... extensions) {
		return requireFileImpl(JFileChooser.FILES_ONLY, extensions);
	}
	
	protected static File @NotNull [] requireFiles(String @NotNull ... extensions) {
		return requireFilesImpl(JFileChooser.FILES_ONLY, extensions);
	}
	
	protected static @NotNull File requireDirectory() {
		return requireFileImpl(JFileChooser.DIRECTORIES_ONLY);
	}
	
	protected static File @NotNull [] requiresDirectory() {
		return requireFilesImpl(JFileChooser.DIRECTORIES_ONLY);
	}
	
	/* -----------------------------------------------------
	 * Internal methods
	 * ----------------------------------------------------- */
	
	private static int getParentCallStack() {
		StackTraceElement[] elements = Thread.currentThread()
			.getStackTrace();
		
		for (int i = 0; i < elements.length; i++) {
			var element = elements[i];
			var filename = element.getFileName();
			var method = element.getMethodName();
			
			// Validate name
			if (FILE_NAME.equals(filename) &&
				"getParentCallStack".equals(method)) {
				return i + 4;
			}
		}
		
		return 0;
	}
	
	private static void printSectionImpl(String title, final int index) {
		System.err.println("#######################################");
		if (title == null) {
			printAutoSectionImpl(index);
		} else {
			// Display information
			System.err.printf("# %s%n", title);
		}
		System.err.println("#######################################");
	}
	
	private static void printAutoSectionImpl(final int index) {
		StackTraceElement[] traceElements = Thread.currentThread()
			.getStackTrace();
		StackTraceElement lastElement = traceElements[index];
		
		// Display information
		System.err.printf("# %s(%s:%d): %s()%n",
						  lastElement.getModuleName() == null ? MODULE_NAME : lastElement.getModuleName(),
						  lastElement.getFileName(),
						  lastElement.getLineNumber(),
						  lastElement.getMethodName());
	}
	
	private static @NotNull File requireFileImpl(int flags, String @NotNull ... extensions) {
		int option = UAction.alsoNotNull(fileChooser, it -> {
			it.setFileSelectionMode(flags);
			it.setMultiSelectionEnabled(false);
			
			if (extensions.length == 0) {
				it.setFileFilter(null);
				return;
			}
			
			FileFilter filter = new FileNameExtensionFilter("Select file/s", extensions);
			it.setFileFilter(filter);
		}).showOpenDialog(null);
		
		Assertions.assertEquals(JFileChooser.APPROVE_OPTION, option,
								"Selection is not valid");
		Assertions.assertNotNull(fileChooser.getSelectedFile(),
								 "No file selected");
		
		return fileChooser.getSelectedFile();
	}
	
	private static File @NotNull [] requireFilesImpl(int flags, String @NotNull ... extensions) {
		int option = UAction.alsoNotNull(fileChooser, it -> {
			it.setFileSelectionMode(flags);
			it.setMultiSelectionEnabled(true);
			
			if (extensions.length == 0) {
				it.setFileFilter(null);
				return;
			}
			
			FileFilter filter = new FileNameExtensionFilter("Select file/s", extensions);
			it.setFileFilter(filter);
		}).showOpenDialog(null);
		
		Assertions.assertEquals(JFileChooser.APPROVE_OPTION, option,
								"Selection is not valid");
		Assertions.assertNotNull(fileChooser.getSelectedFile(),
								 "No file selected");
		
		return fileChooser.getSelectedFiles();
	}
	
}