package com.gitgis.sm.smpak;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ItemHandler extends DefaultHandler {

	private Item item;
	private String lastElement = "";
	private String rawBuf = "";
	private int level = 0;

	public ItemHandler(Item item) {
		this.item = item;
	}

	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		String string = new String(ch, start, length);
		if (level>=2) {
			rawBuf+=string;
		}
		
		if (lastElement.equals("title")) {
		} else
		if (lastElement.equals("modified")) {
		} else
		if (lastElement.equals("version")) {
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		lastElement = qName;

		String rawTag = null;
		
		if (qName.equals("item")) {
		} else
		if (qName.equals("modified")) {
		} else
		if (qName.equals("chapter-title")) {
		} else
		if (qName.equals("lesson-title")) {
		} else
		if (qName.equals("answer-audio")) {
		} else
		if (qName.equals("answer")) {
		} else
		if (qName.equals("question")) {
		} else
		if (qName.equals("question-title")) {
		} else
		if (qName.equals("template-id")) {
		} else
		if (qName.equals("translation")) {
		} else
		if (qName.equals("sentence")) {
		} else
		if (qName.equals("text")) {
		} else
		if (qName.startsWith("gfx-")) {
		} else {
			rawTag = "<"+qName;
			
			
			for (int i = 0, len = attributes.getLength(); i<len; i++) {
				rawTag+=" "+attributes.getQName(i)+"=\""+attributes.getValue(i)+"\"";
			}
			rawTag+=">";

			//System.out.println(rawTag);
		}

		if (level<2) {
			rawBuf="";
		} else {
			rawBuf+=rawTag;
		}
		
//		if (qName.equals("element")) {
//			course.addElement(new Element(Integer.valueOf(attributes.getValue("id")), attributes.getValue("name"), elementLevel));
//			
//		}
		level ++;
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {


		if (qName.equals("question")) {
			System.out.println(rawBuf);
			rawBuf = "";
		} else {
			rawBuf+="</"+qName+">";
		}
		
		//System.out.println("/"+qName);
//		if (qName.equals("element")) {
//		}
		
		lastElement  = "";
		level--;
	}

}
