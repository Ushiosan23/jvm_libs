package ushiosan.jvm.test;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public interface UDesktopTest {
	
	/**
	 * Object used to request local files
	 *
	 * @return the file chooser instance
	 */
	@NotNull JFileChooser fileChooser();
	
	/**
	 * Gets the platform's default toolkit
	 *
	 * @return the platform's toolkit
	 */
	default @NotNull Toolkit toolkit() {
		return Toolkit.getDefaultToolkit();
	}
	
}
