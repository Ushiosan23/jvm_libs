package ushiosan.jvm.reflection;

import org.junit.jupiter.api.Test;
import ushiosan.UTestUnit;

import javax.swing.*;
import java.awt.*;

import static ushiosan.jvm.reflection.UReflectionActions.methodReturnType;

public class UReflectionActionsTest extends UTestUnit {
	
	@Test
	public void findMethodTest() throws NoSuchMethodException {
		sectionErrOf(() -> {
			// Temporal variables
			var options = UReflectionOptions.generateForMethods()
				.addPredicate(methodReturnType(Dimension.class));
			var method = UReflectionActions
				.findMethod(JWindow.class, "getSize", options);
			
			System.out.printf("Search method: %s%n", "Dimension getSize()");
			System.out.printf("Search class:  %s%n", JWindow.class);
			System.out.printf("Found method:  %s%n", method);
		});
	}
	
}