package ushiosan.jvm_utilities;

public final class TestUtils {
	
	public static void printSection(String title) {
		System.out.println("------------------------------------------");
		System.out.printf("- %s%n", title);
		System.out.println("------------------------------------------");
	}
	
	public static void printSection() {
		var stack = Thread.currentThread().getStackTrace();
		var current = stack[2];
		var format = String.format("%s() - (%s:%d)", current.getMethodName(), current.getFileName(), current.getLineNumber());
		
		printSection(format);
	}
	
}
