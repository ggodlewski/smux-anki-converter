package com.gitgis.sm.smpak;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GlossaryHandler extends DefaultHandler {

	private Glossary glossary;
	private String lastKey = "";

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		String string = new String(ch, start, length);
		if (!lastKey.equals("")) {
			glossary.put(lastKey, string);
		}
	}

	public GlossaryHandler(Glossary glossary) {
		this.glossary = glossary;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		if (qName.equals("phrase")) {
			lastKey = attributes.getValue("key");
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		lastKey = "";
	}
}
