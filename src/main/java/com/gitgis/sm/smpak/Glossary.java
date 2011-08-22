package com.gitgis.sm.smpak;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Glossary extends HashMap<String, String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8008525076662634084L;

	public Glossary(InputStream inputStream) throws IOException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = factory.newSAXParser();
			DefaultHandler dh = new GlossaryHandler(this);
			saxParser.parse(inputStream, dh);
		} catch (ParserConfigurationException e) {
			
			e.printStackTrace();
		} catch (SAXException e) {
		
			e.printStackTrace();
		}
	}

}
