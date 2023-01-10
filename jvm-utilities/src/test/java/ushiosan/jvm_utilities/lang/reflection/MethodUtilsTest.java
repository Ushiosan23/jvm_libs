package ushiosan.jvm_utilities.lang.reflection;

import org.junit.Test;
import ushiosan.jvm_utilities.lang.reflection.options.ReflectionOpts;

import java.lang.reflect.Method;

public class MethodUtilsTest {
	
	@Test
	public void runTest() {
		E3 instance = new E3();
		ReflectionOpts<Method> opts = ReflectionOpts.<Method>getDefault()
			.setSkipAbstracts(true)
			.setOnlyPublic(true)
			.addPredicate(it -> it.getDeclaringClass() != Object.class)
			.addPredicate(it -> it.getName().equals("toString"));
		
		// All methods
		Method[] methods = MethodUtils.getAllClassMethods(instance.getClass(), opts);
		
		for (Method method : methods) {
			System.err.println(method);
		}
	}
	
	static class E1 {}
	
	static class E2 extends E1 {}
	
	static class E3 extends E2 {}
	
}