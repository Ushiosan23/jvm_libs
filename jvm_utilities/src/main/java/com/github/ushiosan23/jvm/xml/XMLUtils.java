package com.github.ushiosan23.jvm.xml;

import com.github.ushiosan23.jvm.functions.apply.IApply;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public final class XMLUtils {

	/**
	 * This class cannot be instantiated
	 */
	private XMLUtils() {
	}

	/* ------------------------------------------------------------------
	 * Methods
	 * ------------------------------------------------------------------ */

	/**
	 * Load xml file
	 *
	 * @param location The xml location
	 * @param apply    Action to execute to configure factory instance
	 * @return Returns a document instance
	 * @throws IOException                  Error to open file
	 * @throws ParserConfigurationException Error factory configuration
	 * @throws SAXException                 Error to parse xml file
	 */
	public static Document load(
		@NotNull File location,
		@Nullable IApply.EmptyResult<DocumentBuilderFactory> apply
	) throws IOException, ParserConfigurationException, SAXException {
		try (InputStream stream = new FileInputStream(location)) {
			return load(stream, apply);
		}
	}

	/**
	 * Load xml file path
	 *
	 * @param location File location
	 * @param apply    Action to execute to configure factory instance
	 * @return Returns a document instance
	 * @throws IOException                  Error to open file
	 * @throws ParserConfigurationException Error factory configuration
	 * @throws SAXException                 Error to parse xml file
	 */
	public static Document load(
		@NotNull Path location,
		@Nullable IApply.EmptyResult<DocumentBuilderFactory> apply
	) throws IOException, ParserConfigurationException, SAXException {
		try (InputStream stream = Files.newInputStream(location)) {
			return load(stream, apply);
		}
	}

	/**
	 * Load xml url
	 *
	 * @param location URL location
	 * @param apply    Action to execute to configure factory instance
	 * @return Returns a document instance
	 * @throws IOException                  Error to open file
	 * @throws ParserConfigurationException Error factory configuration
	 * @throws SAXException                 Error to parse xml file
	 */
	public static Document load(
		@NotNull URL location,
		@Nullable IApply.EmptyResult<DocumentBuilderFactory> apply
	) throws IOException, ParserConfigurationException, SAXException {
		try (InputStream stream = location.openStream()) {
			return load(stream, apply);
		}
	}

	/**
	 * Load xml stream
	 *
	 * @param stream XML Stream
	 * @param apply  Action to execute to configure factory instance
	 * @return Returns a document instance
	 * @throws IOException                  Error to process the stream
	 * @throws ParserConfigurationException Error factory configuration
	 * @throws SAXException                 Error to parse xml file
	 */
	public static Document load(
		@NotNull InputStream stream,
		@Nullable IApply.EmptyResult<DocumentBuilderFactory> apply
	) throws IOException, ParserConfigurationException, SAXException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		if (apply != null) apply.run(factory);
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(stream);
	}

}
