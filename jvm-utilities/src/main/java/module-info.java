/**
 *
 */
module ushiosan.jvm.utilities {
	// Dependencies
	requires java.xml;
	requires java.logging;
	requires static org.jetbrains.annotations;
	
	// Exports
	exports ushiosan.jvm;
	exports ushiosan.jvm.accumulator;
	exports ushiosan.jvm.collections;
	exports ushiosan.jvm.content;
	exports ushiosan.jvm.error;
	exports ushiosan.jvm.filesystem;
	exports ushiosan.jvm.function;
	exports ushiosan.jvm.platform;
	exports ushiosan.jvm.print;
	exports ushiosan.jvm.reflection;
}