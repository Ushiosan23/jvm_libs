package ushiosan.jvm_utilities.lang.reflection.options;

import org.junit.Test;
import ushiosan.jvm_utilities.TestUtils;
import ushiosan.jvm_utilities.lang.reflection.MethodUtils;

import java.lang.reflect.Method;

public class ReflectionOptsTest {
	
	@Test
	public void reflectionOptsTest() {
		TestUtils.printSection();
		
		ReflectionOpts<Method> opts = ReflectionOpts.<Method>getDefault()
			.addPredicate(MethodUtils.validGetterMethod());
		
		System.out.println(opts);
		System.out.println();
	}
	
}