package ushiosan.jvm.test.test;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ushiosan.jvm.UAction;
import ushiosan.jvm.content.UPair;
import ushiosan.jvm.test.UTestUnit;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;

class UActionTest extends UTestUnit {
	
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
	public void alsoTest() {
		makeSection(() -> {
			// Temporal variables
			var dimension = UAction.also(new Dimension(), it -> {
				it.width = 1000;
				it.height = 600;
			});
			
			// Assertions
			Assertions.assertEquals(dimension.width, 1000,
									"Invalid also() invocation");
			Assertions.assertEquals(dimension.height, 600,
									"Invalid also() invocation");
			
			// Display information
			System.out.printf("Dimension info: %s%n", dimension);
		});
	}
	
	@Test
	public void applyTest() {
		makeSection(() -> {
			// Temporal variables
			var dimensionToPair = UAction.apply(new Dimension(1000, 600),
												it -> UPair.make(it.width, it.height));
			
			// Assertions
			Assertions.assertEquals(UPair.class, dimensionToPair.getClass(),
									"Invalid apply() result");
			Assertions.assertTrue(dimensionToPair.first == 1000 && dimensionToPair.second == 600,
								  "Invalid apply() result");
			
			// Display information
			System.out.printf("Pair result: %s%n", dimensionToPair);
		});
	}
	
	@Test
	public void applyErrTest() {
		makeSectionExpected(URISyntaxException.class, () -> {
			// Force error
			var malformedURL = "C:\\Users\\current\\example";
			var uriObject = UAction.applyErr(malformedURL, URI::new);
			
			System.out.println(malformedURL);
			System.out.println(uriObject);
		});
	}
	
}