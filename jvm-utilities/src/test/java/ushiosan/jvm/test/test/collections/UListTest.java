package ushiosan.jvm.test.test.collections;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ushiosan.jvm.collections.UList;
import ushiosan.jvm.test.UTestUnit;
import ushiosan.jvm.test.test.Constants;

class UListTest extends UTestUnit {
	
	/**
	 * The name of the module where the tests are being done
	 *
	 * @return the module name
	 */
	@Override
	public @NotNull String module() {
		return Constants.LIB_MODULE;
	}
	
	/* -----------------------------------------------------
	 * Test methods
	 * ----------------------------------------------------- */
	
	@Test
	public void transformTest() {
		makeSection(() -> {
			// Originals
			var original = UList.make("Hello", ", ", "World", "!");
			var originalMut = UList.makeMutable("Hello", ", ", "World", "!");
			// Transformations
			var transform = UList.transform(original, String::getBytes);
			var transformMut = UList.transform(originalMut, String::getBytes);
			
			// Assertions
			Assertions.assertTrue(UList.isUnmodifiable(transform),
								  "The collection cannot be mutable");
			Assertions.assertFalse(UList.isUnmodifiable(transformMut),
								   "The collection cannot be unmodifiable");
			
			println("Transform: (%s) %s", transform.getClass().getCanonicalName(), transform);
			println("TransformMut: (%s) %s", transformMut.getClass().getCanonicalName(), transformMut);
		});
	}
	
}