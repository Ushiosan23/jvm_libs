@SuppressWarnings("JavaModuleNaming")
module com.github.ushiosan23.jvm {
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
}
