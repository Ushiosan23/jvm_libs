package ushiosan.jvm_utilities.lang.reflection.options;

import org.junit.Test;
import ushiosan.jvm_utilities.lang.reflection.MethodUtils;

import java.lang.reflect.Method;

public class ReflectionOptsTest {
	
	@Test
	public void runTest() {
		ReflectionOpts<Method> opts = ReflectionOpts.<Method>getDefault()
			.addPredicate(MethodUtils.validGetterMethod());
		
		System.err.println(opts);
	}
	
	
}