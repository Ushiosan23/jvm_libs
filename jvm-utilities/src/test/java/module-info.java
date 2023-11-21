module ushiosan.jvm.utilities.test {
	requires java.net.http;
	requires java.logging;
	requires java.desktop;
	
	requires ushiosan.jvm.utilities;
	requires ushiosan.jvm.test;
	requires org.junit.jupiter.engine;
	requires org.junit.jupiter.api;
	requires org.junit.jupiter;
	
	requires static org.jetbrains.annotations;
	
	opens ushiosan.jvm.test.test to org.junit.platform.commons;
	opens ushiosan.jvm.test.test.collections to org.junit.platform.commons;
	opens ushiosan.jvm.test.test.http to org.junit.platform.commons;
	opens ushiosan.jvm.test.test.print to org.junit.platform.commons, ushiosan.jvm.utilities;
	opens ushiosan.jvm.test.test.reflection to org.junit.platform.commons;
}