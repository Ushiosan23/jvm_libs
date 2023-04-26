/**
 * Module containing classes with utilities for handling actions that are
 * repetitive or too cumbersome to perform continuously. All of these classes are highly
 * configurable, and you can implement your own functionality if you wish.
 */
module com.github.ushiosan.jvm_utilities {
	requires java.logging;
	requires static org.jetbrains.annotations;
	
	exports ushiosan.jvm_utilities.error;
	exports ushiosan.jvm_utilities.function;
	exports ushiosan.jvm_utilities.function.predicate;
	exports ushiosan.jvm_utilities.lang;
	exports ushiosan.jvm_utilities.lang.accumulator;
	exports ushiosan.jvm_utilities.lang.collection;
	exports ushiosan.jvm_utilities.lang.collection.elements;
	exports ushiosan.jvm_utilities.lang.io;
	exports ushiosan.jvm_utilities.lang.print;
	exports ushiosan.jvm_utilities.lang.print.annotations;
	exports ushiosan.jvm_utilities.lang.random;
	exports ushiosan.jvm_utilities.lang.reflection;
	exports ushiosan.jvm_utilities.lang.reflection.options;
	exports ushiosan.jvm_utilities.system;
}