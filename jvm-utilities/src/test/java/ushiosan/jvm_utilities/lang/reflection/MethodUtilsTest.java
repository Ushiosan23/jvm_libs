package ushiosan.jvm_utilities.lang.reflection;

import org.junit.Assert;
import org.junit.Test;
import ushiosan.jvm_utilities.TestUtils;
import ushiosan.jvm_utilities.lang.reflection.options.ReflectionOpts;

import java.lang.reflect.Method;

public class MethodUtilsTest {
	
	@Test
	public void getAllClassMethodsTest() {
		TestUtils.printSection();
		
		E3 instance = new E3();
		ReflectionOpts<Method> opts = ReflectionOpts.<Method>getDefault()
			.setSkipAbstracts(true)
			.setOnlyPublic(true)
			.addPredicate(it -> it.getDeclaringClass() != Object.class)
			.addPredicate(it -> it.getName().equals("toString"));
		
		// All methods
		Method[] methods = MethodUtils.getAllClassMethods(instance.getClass(), opts);
		Assert.assertTrue("Method not found", methods.length > 0);
		
		for (Method method : methods) {
			System.err.println(method);
		}
		System.out.println();
	}
	
	static class E1 {
		
		@Override
		public String toString() {
			return "E1{}";
		}
		
	}
	
	static class E2 extends E1 {}
	
	static class E3 extends E2 {}
	
}