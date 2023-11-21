package ushiosan.jvm.test.test;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ushiosan.jvm.UR;
import ushiosan.jvm.test.UTestUnit;

class URTest extends UTestUnit {
	
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
	public void getPropertyTest() {
		makeSection(() -> {
			// Get property value
			var libraryVersion = UR.getInstance()
				.getProperty("jvm.utilities.version");
			
			// Assertions
			Assertions.assertTrue(libraryVersion.isPresent(),
								  "Property cannot be <empty>");
			
			// Display information
			var version = libraryVersion.get();
			println("Library version: %s", version);
		});
	}
	
}