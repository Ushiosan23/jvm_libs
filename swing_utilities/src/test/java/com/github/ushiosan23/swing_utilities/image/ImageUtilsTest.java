package com.github.ushiosan23.swing_utilities.image;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class ImageUtilsTest {

	private static final ClassLoader loader = ClassLoader.getSystemClassLoader();

	@Test
	public void runTest() {
		URL imageUrl = loader.getResource("res_pic.png");
		Assert.assertNotNull("Image not found", imageUrl);
		Image image = Toolkit
			.getDefaultToolkit()
			.getImage(imageUrl);
		BufferedImage converted = ImageUtils
			.imageToBufferedImage(image);

		Graphics2D graphics = converted.createGraphics();
		System.err.println(graphics);
		graphics.dispose();
	}


}
