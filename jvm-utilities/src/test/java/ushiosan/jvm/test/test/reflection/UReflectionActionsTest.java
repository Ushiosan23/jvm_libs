package ushiosan.jvm.test.test.reflection;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import ushiosan.jvm.reflection.UReflectionActions;
import ushiosan.jvm.reflection.UReflectionOptions;
import ushiosan.jvm.test.UTestUnit;
import ushiosan.jvm.test.test.Constants;

import javax.swing.*;
import java.awt.*;

public class UReflectionActionsTest extends UTestUnit {
	
	/**
	 * The name of the module where the tests are being done
	 *
	 * @return the module name
	 */
	@Override
	public @NotNull String module() {
		return Constants.LIB_MODULE;
	}
	
	@Test
	public void findMethodTest() throws NoSuchMethodException {
		makeSectionError(() -> {
			// Temporal variables
			var options = UReflectionOptions.generateForMethods()
				.addPredicate(UReflectionActions.methodReturnType(Dimension.class));
			var method = UReflectionActions
				.findMethod(JWindow.class, "getSize", options);
			
			println("Search method: %s", "Dimension getSize()");
			println("Search class:  %s", JWindow.class);
			println("Found method:  %s", method);
		});
	}
	
}