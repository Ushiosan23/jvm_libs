package com.github.ushiosan23.jvm.xml;

import com.github.ushiosan23.jvm.TestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.net.URL;

public class XMLUtilsTest {

	@Test
	public void runTest() throws Exception {
		URL xml = TestUtils.getResource("example.xml");
		Assert.assertNotNull(xml);
		Document document = XMLUtils.load(xml, null);
		Element element = document.getDocumentElement();

		System.err.println(element);
	}

}
