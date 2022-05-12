package com.github.ushiosan23.jvm.xml;

import com.github.ushiosan23.jvm.base.Obj;
import com.github.ushiosan23.jvm.functions.apply.IApply;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
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
	public static @NotNull Document load(
		@NotNull InputStream stream,
		@Nullable IApply.EmptyResult<DocumentBuilderFactory> apply
	) throws IOException, ParserConfigurationException, SAXException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		if (apply != null) apply.invoke(factory);
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(stream);
	}

	/* ------------------------------------------------------------------
	 * Validators
	 * ------------------------------------------------------------------ */

	/**
	 * Validates an XML file using a schema file.
	 *
	 * @param xml             The XML file you want to validate
	 * @param schemaLanguage  The validator you want to use. See {@link XMLConstants#W3C_XML_SCHEMA_NS_URI} and
	 *                        {@link XMLConstants#W3C_XML_SCHEMA_INSTANCE_NS_URI}
	 * @param schemaValidator The file containing the validation schema
	 * @return Returns {@code true} if the xml file is valid or {@code false} if it is invalid or an error has occurred.
	 */
	public static boolean validate(
		@NotNull InputStream xml,
		@Nullable String schemaLanguage,
		@Nullable InputStream schemaValidator
	) {
		try (xml; schemaValidator) {
			Validator realValidator = generateValidator(schemaValidator, schemaLanguage);
			realValidator.validate(new StreamSource(xml));
			return true;
		} catch (Exception ignore) {
		}
		return false;
	}

	/**
	 * Generates an XML file validator.
	 *
	 * @param schemaValidator XML schema file
	 * @param schemaLanguage  The validator you want to use. See {@link XMLConstants#W3C_XML_SCHEMA_NS_URI} and
	 *                        {@link XMLConstants#W3C_XML_SCHEMA_INSTANCE_NS_URI}
	 * @return Returns an instance of {@link Validator} configured for use.
	 * @throws IOException  IO error
	 * @throws SAXException If an error occurred during parsing
	 */
	public static @NotNull Validator generateValidator(
		@Nullable InputStream schemaValidator,
		@Nullable String schemaLanguage
	) throws IOException, SAXException {
		// Not null schema language
		schemaLanguage = Obj.notNull(schemaLanguage, XMLConstants.W3C_XML_SCHEMA_NS_URI);
		// Auto close stream
		try (schemaValidator) {
			// Temporal variables
			SchemaFactory factory = SchemaFactory.newInstance(schemaLanguage);
			Schema schema = schemaValidator == null ? factory.newSchema() :
				factory.newSchema(new StreamSource(schemaValidator));
			// Generate validator
			return schema.newValidator();
		}
	}

}
