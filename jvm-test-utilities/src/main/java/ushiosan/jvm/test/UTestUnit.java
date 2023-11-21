package ushiosan.jvm.test;

import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.junit.jupiter.api.Assertions;
import ushiosan.jvm.UObject;
import ushiosan.jvm.function.UEmptyFun;
import ushiosan.jvm.function.UEmptyFunErr;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.nio.file.Path;

public abstract class UTestUnit implements UTestInfo, UDesktopTest {
	
	/* -----------------------------------------------------
	 * Properties
	 * ----------------------------------------------------- */
	
	/**
	 * The name of the current file. This is used internally to identify the stack position.
	 */
	private static final String FILENAME = "UTestUnit.java";
	
	/**
	 * The dialog for the selection of local files
	 */
	private static JFileChooser chooser;
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	protected static @NotNull UTestUnit instanceAccess(@NotNull String module) {
		return new UTestUnit() {
			@Override
			public @NotNull String module() {
				return module;
			}
		};
	}
	
	/**
	 * Object used to request local files
	 *
	 * @return the file chooser instance
	 */
	@Override
	public final @NotNull JFileChooser fileChooser() {
		if (chooser == null) {
			Path location = Path.of(System.getProperty("user.home"));
			chooser = new JFileChooser(location.toFile());
		}
		return chooser;
	}
	
	/**
	 * The name of the file where the tests are being processed
	 *
	 * @return the testing file name
	 */
	@Override
	public final @NotNull String filename() {
		return FILENAME;
	}
	
	/* -----------------------------------------------------
	 * Section methods
	 * ----------------------------------------------------- */
	
	/**
	 * Generates an execution section for unit tests
	 *
	 * @param stackIndex option used to get the stack index and create the title automatically
	 * @param blankEnd   at the end of execution inserts a blank space to separate sections
	 * @param name       the name of the section
	 * @param action     the unit test you want to run
	 */
	public void makeSection(int stackIndex, boolean blankEnd, @Nullable String name, @NotNull UEmptyFun action) {
		// Display base information
		printSectionImpl(name, stackIndex);
		println();
		
		// Temporal variables
		long measureStart = System.nanoTime(),
			measureEnd;
		
		// Action execution
		action.invoke();
		
		// Show end information
		measureEnd = (System.nanoTime() - measureStart) / 1_000_000L;
		
		printlnOpt(true, "%nThe test took %dms to execute.", measureEnd);
		if (blankEnd) printlnOpt();
	}
	
	/**
	 * Generates an execution section for unit tests
	 *
	 * @param blankEnd at the end of execution inserts a blank space to separate sections
	 * @param name     the name of the section
	 * @param action   the unit test you want to run
	 */
	public void makeSection(boolean blankEnd, @Nullable String name, @NotNull UEmptyFun action) {
		int stackIndex = getParentStackCallIndex();
		makeSection(stackIndex, blankEnd, name, action);
	}
	
	/**
	 * Generates an execution section for unit tests
	 *
	 * @param blankEnd at the end of execution inserts a blank space to separate sections
	 * @param action   the unit test you want to run
	 */
	public void makeSection(boolean blankEnd, @NotNull UEmptyFun action) {
		int stackIndex = getParentStackCallIndex();
		makeSection(stackIndex, blankEnd, null, action);
	}
	
	/**
	 * Generates an execution section for unit tests
	 *
	 * @param action the unit test you want to run
	 */
	public void makeSection(@NotNull UEmptyFun action) {
		int stackIndex = getParentStackCallIndex();
		makeSection(stackIndex, true, null, action);
	}
	
	/* -----------------------------------------------------
	 * Section error methods
	 * ----------------------------------------------------- */
	
	/**
	 * Generates an execution section for unit tests
	 *
	 * @param stackIndex option used to get the stack index and create the title automatically
	 * @param blankEnd   at the end of execution inserts a blank space to separate sections
	 * @param name       the name of the section
	 * @param action     the unit test you want to run
	 * @param <E>        error that must be caught by the method
	 * @throws E error if something goes wrong
	 */
	public <E extends Throwable> void makeSectionError(int stackIndex, boolean blankEnd, @Nullable String name,
		@NotNull UEmptyFunErr<E> action) throws E {
		// Display base information
		printSectionImpl(name, stackIndex);
		println();
		
		// Temporal variables
		long measureStart = System.nanoTime(),
			measureEnd;
		
		// Action execution
		action.invoke();
		
		// Show end information
		measureEnd = (System.nanoTime() - measureStart) / 1_000_000L;
		
		printlnOpt(true, "%nThe test took %dms to execute.", measureEnd);
		if (blankEnd) println();
	}
	
	/**
	 * Generates an execution section for unit tests
	 *
	 * @param blankEnd at the end of execution inserts a blank space to separate sections
	 * @param name     the name of the section
	 * @param action   the unit test you want to run
	 * @param <E>      error that must be caught by the method
	 * @throws E error if something goes wrong
	 */
	public <E extends Throwable> void makeSectionError(boolean blankEnd, @Nullable String name,
		@NotNull UEmptyFunErr<E> action) throws E {
		int stackIndex = getParentStackCallIndex();
		makeSectionError(stackIndex, blankEnd, name, action);
	}
	
	/**
	 * Generates an execution section for unit tests
	 *
	 * @param blankEnd at the end of execution inserts a blank space to separate sections
	 * @param action   the unit test you want to run
	 * @param <E>      error that must be caught by the method
	 * @throws E error if something goes wrong
	 */
	public <E extends Throwable> void makeSectionError(boolean blankEnd, @NotNull UEmptyFunErr<E> action) throws E {
		int stackIndex = getParentStackCallIndex();
		makeSectionError(stackIndex, blankEnd, null, action);
	}
	
	/**
	 * Generates an execution section for unit tests
	 *
	 * @param action the unit test you want to run
	 * @param <E>    error that must be caught by the method
	 * @throws E error if something goes wrong
	 */
	public <E extends Throwable> void makeSectionError(@NotNull UEmptyFunErr<E> action) throws E {
		int stackIndex = getParentStackCallIndex();
		makeSectionError(stackIndex, true, null, action);
	}
	
	/* -----------------------------------------------------
	 * Expected error methods
	 * ----------------------------------------------------- */
	
	/**
	 * Generates an execution section for unit tests
	 *
	 * @param stackIndex option used to get the stack index and create the title automatically
	 * @param blankEnd   at the end of execution inserts a blank space to separate sections
	 * @param name       the name of the section
	 * @param action     the unit test you want to run
	 * @param expected   class with the expected error to catch
	 * @param <E>        error that must be caught by the method
	 */
	public <E extends Throwable> void makeSectionExpected(int stackIndex, boolean blankEnd, @Nullable String name,
		@NotNull Class<E> expected, @NotNull UEmptyFunErr<E> action) {
		// Display base information
		printSectionImpl(name, stackIndex);
		println();
		
		// Temporal variables
		long measureStart = System.nanoTime(),
			measureEnd;
		
		// Action execution
		Assertions.assertThrows(expected, action::invoke);
		
		// Show end information
		measureEnd = (System.nanoTime() - measureStart) / 1_000_000L;
		
		printlnOpt(true, "%nThe test took %dms to execute.", measureEnd);
		if (blankEnd) println();
	}
	
	/**
	 * Generates an execution section for unit tests
	 *
	 * @param blankEnd at the end of execution inserts a blank space to separate sections
	 * @param name     the name of the section
	 * @param action   the unit test you want to run
	 * @param expected class with the expected error to catch
	 * @param <E>      error that must be caught by the method
	 */
	public <E extends Throwable> void makeSectionExpected(boolean blankEnd, @Nullable String name,
		@NotNull Class<E> expected, @NotNull UEmptyFunErr<E> action) {
		int stackIndex = getParentStackCallIndex();
		makeSectionExpected(stackIndex, blankEnd, name, expected, action);
	}
	
	/**
	 * Generates an execution section for unit tests
	 *
	 * @param blankEnd at the end of execution inserts a blank space to separate sections
	 * @param action   the unit test you want to run
	 * @param expected class with the expected error to catch
	 * @param <E>      error that must be caught by the method
	 */
	public <E extends Throwable> void makeSectionExpected(boolean blankEnd, @NotNull Class<E> expected,
		@NotNull UEmptyFunErr<E> action) {
		int stackIndex = getParentStackCallIndex();
		makeSectionExpected(stackIndex, blankEnd, null, expected, action);
	}
	
	/**
	 * Generates an execution section for unit tests
	 *
	 * @param action   the unit test you want to run
	 * @param expected class with the expected error to catch
	 * @param <E>      error that must be caught by the method
	 */
	public <E extends Throwable> void makeSectionExpected(@NotNull Class<E> expected, @NotNull UEmptyFunErr<E> action) {
		int stackIndex = getParentStackCallIndex();
		makeSectionExpected(stackIndex, true, null, expected, action);
	}
	
	/* -----------------------------------------------------
	 * Chooser methods
	 * ----------------------------------------------------- */
	
	/**
	 * Display a file selection dialog for specific file
	 *
	 * @param extensions supported file extensions
	 * @return the file that has been selected when closing the selection dialog
	 */
	public @NotNull File requireFile(String @NotNull ... extensions) {
		return requireFileImpl(JFileChooser.FILES_ONLY, extensions);
	}
	
	/**
	 * Display a file selection dialog for one or more specific files
	 *
	 * @param extensions supported file extensions
	 * @return the file that has been selected when closing the selection dialog
	 */
	public File @NotNull @Unmodifiable [] requireFiles(String @NotNull ... extensions) {
		return requireFilesImpl(JFileChooser.FILES_ONLY, extensions);
	}
	
	/**
	 * Display a file selection dialog for specific directory
	 *
	 * @return the file that has been selected when closing the selection dialog
	 */
	public @NotNull File requireDirectory() {
		return requireFileImpl(JFileChooser.DIRECTORIES_ONLY);
	}
	
	/**
	 * Display a file selection dialog for one or more specific directories
	 *
	 * @return the file that has been selected when closing the selection dialog
	 */
	public File @NotNull @Unmodifiable [] requireDirectories() {
		return requireFilesImpl(JFileChooser.DIRECTORIES_ONLY);
	}
	
	/* -----------------------------------------------------
	 * Internal methods
	 * ----------------------------------------------------- */
	
	/**
	 * Gets the index of the parent method where the unit test is executed
	 *
	 * @return the stack index of the executing unit test
	 */
	private int getParentStackCallIndex() {
		StackTraceElement[] elements = Thread.currentThread()
			.getStackTrace();
		
		for (int i = 0; i < elements.length; i++) {
			var element = elements[i];
			var filename = element.getFileName();
			var method = element.getMethodName();
			
			// Validate name
			if (filename().equals(filename) &&
				"getParentStackCallIndex".equals(method)) {
				return i + 4;
			}
		}
		
		return 0;
	}
	
	/**
	 * Display a file selection dialog for specific file
	 *
	 * @param flag       the type of files you want to get {@link JFileChooser#FILES_ONLY} or
	 *                   {@link JFileChooser#DIRECTORIES_ONLY}
	 * @param extensions supported file extensions
	 * @return the file that has been selected when closing the selection dialog
	 */
	private File requireFileImpl(@MagicConstant(valuesFromClass = JFileChooser.class) int flag,
		String @NotNull ... extensions) {
		// Configure chooser
		fileChooser().setFileSelectionMode(flag);
		fileChooser().setMultiSelectionEnabled(false);
		fileChooser().setFileFilter(null);
		
		// Set chooser extension filter
		if (extensions.length != 0) {
			var filter = new FileNameExtensionFilter("Select Files...", extensions);
			fileChooser().setFileFilter(filter);
		}
		
		// Display chooser dialog
		var option = fileChooser().showOpenDialog(null);
		Assertions.assertEquals(JFileChooser.APPROVE_OPTION, option,
								"Selection is not valid");
		Assertions.assertNotNull(fileChooser().getSelectedFile(),
								 "No file selected");
		return fileChooser().getSelectedFile();
	}
	
	/**
	 * Display a file selection dialog for one or more specific files
	 *
	 * @param flag       the type of files you want to get {@link JFileChooser#FILES_ONLY} or
	 *                   {@link JFileChooser#DIRECTORIES_ONLY}
	 * @param extensions supported file extensions
	 * @return the file that has been selected when closing the selection dialog
	 */
	private File @NotNull @Unmodifiable [] requireFilesImpl(@MagicConstant(valuesFromClass = JFileChooser.class) int flag,
		String @NotNull ... extensions) {
		// Configure chooser
		fileChooser().setFileSelectionMode(flag);
		fileChooser().setMultiSelectionEnabled(true);
		fileChooser().setFileFilter(null);
		
		// Set chooser extension filter
		if (extensions.length != 0) {
			var filter = new FileNameExtensionFilter("Select Files...", extensions);
			fileChooser().setFileFilter(filter);
		}
		
		// Display chooser dialog
		var option = fileChooser().showOpenDialog(null);
		Assertions.assertEquals(JFileChooser.APPROVE_OPTION, option,
								"Selection is not valid");
		Assertions.assertNotNull(fileChooser().getSelectedFile(),
								 "No file selected");
		return fileChooser().getSelectedFiles();
	}
	
	/**
	 * Prints the name of the section that is currently being called
	 *
	 * @param name       the name of the section
	 * @param stackIndex option used to get the stack index and create the title automatically
	 */
	private void printSectionImpl(@Nullable String name, final int stackIndex) {
		// Base information
		printlnOpt(true, "#######################################");
		// Content information
		if (name == null || name.isBlank()) {
			printAutoTitle(stackIndex);
		} else {
			printlnOpt(true, "# %s", name);
		}
		// Base information
		printlnOpt(true, "#######################################");
	}
	
	/**
	 * Prints the name of the section that is currently being called
	 *
	 * @param stackIndex option used to get the stack index and create the title automatically
	 */
	private void printAutoTitle(final int stackIndex) {
		StackTraceElement[] traceElements = Thread.currentThread()
			.getStackTrace();
		StackTraceElement stackElement = traceElements[stackIndex];
		
		// Get stack information
		printOpt(true, "# %s(%s:%d): %s()%n",
				 UObject.notNull(stackElement.getModuleName(), module()),
				 stackElement.getFileName(),
				 stackElement.getLineNumber(),
				 stackElement.getMethodName());
	}
	
}
