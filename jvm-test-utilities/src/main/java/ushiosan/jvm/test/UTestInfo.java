package ushiosan.jvm.test;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ushiosan.jvm.UClass;
import ushiosan.jvm.UObject;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.function.Function;

public interface UTestInfo {
	
	/**
	 * Filter used to convert objects to plain text
	 */
	Function<Object, CharSequence> ARRAY_MAPPER = it -> {
		checkType:
		{
			if (UObject.isNull(it)) break checkType;
			if (UClass.isPrimitive(it.getClass()) || UObject.canCast(it, CharSequence.class)) {
				return it.toString();
			}
		}
		
		return UObject.toString(it);
	};
	
	/**
	 * The name of the module where the tests are being done
	 *
	 * @return the module name
	 */
	@NotNull String module();
	
	/**
	 * The name of the file where the tests are being processed
	 *
	 * @return the testing file name
	 */
	@NotNull String filename();
	
	/**
	 * Gets the object where the information output will be presented
	 *
	 * @param contrast option to present information contrast
	 * @return output object instance
	 */
	default @NotNull PrintStream out(boolean contrast) {
		return contrast ? System.err : System.out;
	}
	
	/**
	 * Gets the object where the information output will be presented
	 *
	 * @return output object instance
	 */
	default @NotNull PrintStream out() {
		return out(false);
	}
	
	/**
	 * Prints the format of the object in the selected output
	 *
	 * @param contrast option to present information contrast
	 * @param format   the format of the output following the same options as
	 *                 the {@link PrintStream#printf(String, Object...)} objects
	 * @param args     text format arguments
	 * @return the current object instance
	 */
	@SuppressWarnings("resource")
	default @NotNull UTestInfo printOpt(boolean contrast, @Nullable Object format, Object... args) {
		String formatStr = UObject.toString(format);
		
		// Display information
		out(contrast).printf(formatStr, args);
		return this;
	}
	
	/**
	 * Prints the format of the object in the selected output
	 *
	 * @param format the format of the output following the same options as
	 *               the {@link PrintStream#printf(String, Object...)} objects
	 * @param args   text format arguments
	 * @return the current object instance
	 */
	default @NotNull UTestInfo print(@Nullable Object format, Object... args) {
		return printOpt(false, format, args);
	}
	
	/**
	 * Prints the format of the object in the selected output
	 *
	 * @param contrast option to present information contrast
	 * @param format   the format of the output following the same options as
	 *                 the {@link PrintStream#printf(String, Object...)} objects
	 * @param args     text format arguments
	 * @return the current object instance
	 */
	@SuppressWarnings("resource")
	default @NotNull UTestInfo printlnOpt(boolean contrast, @Nullable Object format, Object... args) {
		String formatStr = UObject.toString(format);
		
		// Generate output information array
		Object[] outArgs = Arrays.stream(args)
			.map(ARRAY_MAPPER)
			.toArray();
		
		// Send the information to the target output stream
		out(contrast).printf(formatStr + "%n", outArgs);
		return this;
	}
	
	/**
	 * Prints the format of the object in the selected output
	 *
	 * @param contrast option to present information contrast
	 * @return the current object instance
	 */
	@SuppressWarnings("resource")
	default @NotNull UTestInfo printlnOpt(boolean contrast) {
		out(contrast).println();
		return this;
	}
	
	/**
	 * Prints the format of the object in the selected output
	 *
	 * @return the current object instance
	 */
	default @NotNull UTestInfo printlnOpt() {
		return printlnOpt(false);
	}
	
	/**
	 * Prints the format of the object in the selected output
	 *
	 * @param format the format of the output following the same options as
	 *               the {@link PrintStream#printf(String, Object...)} objects
	 * @param args   text format arguments
	 * @return the current object instance
	 */
	default @NotNull UTestInfo println(@Nullable Object format, Object... args) {
		return printlnOpt(false, format, args);
	}
	
	/**
	 * Prints the format of the object in the selected output
	 *
	 * @return the current object instance
	 */
	default @NotNull UTestInfo println() {
		return printlnOpt();
	}
	
}
