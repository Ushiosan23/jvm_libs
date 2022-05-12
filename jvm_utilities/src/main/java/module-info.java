/**
 * Utilities module for repetitive or complex tasks of the java virtual machine.
 * All these utilities are separated into specific and intuitive packages for easy use.
 */
@SuppressWarnings("JavaModuleNaming")
module com.github.ushiosan23.jvm {
	uses java.nio.file.spi.FileSystemProvider;

	requires java.xml;
	requires static org.jetbrains.annotations;

	/* ------------------------------------------------------------------
	 * Base
	 * ------------------------------------------------------------------ */
	exports com.github.ushiosan23.jvm.base;
	/* ------------------------------------------------------------------
	 * Collections
	 * ------------------------------------------------------------------ */
	exports com.github.ushiosan23.jvm.collections;
	/* ------------------------------------------------------------------
	 * Functions
	 * ------------------------------------------------------------------ */
	exports com.github.ushiosan23.jvm.functions.apply;
	/* ------------------------------------------------------------------
	 * IO
	 * ------------------------------------------------------------------ */
	exports com.github.ushiosan23.jvm.io;
	exports com.github.ushiosan23.jvm.io.predicates;
	/* ------------------------------------------------------------------
	 * OS
	 * ------------------------------------------------------------------ */
	exports com.github.ushiosan23.jvm.system;
	exports com.github.ushiosan23.jvm.system.os;
	/* ------------------------------------------------------------------
	 * XML
	 * ------------------------------------------------------------------ */
	exports com.github.ushiosan23.jvm.xml;
}
