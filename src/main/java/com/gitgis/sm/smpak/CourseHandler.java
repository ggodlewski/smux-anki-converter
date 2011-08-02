package com.gitgis.sm.smpak;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.gitgis.sm.course.Course;
import com.gitgis.sm.course.Item;

public class CourseHandler extends DefaultHandler {

	int elementLevel = 0;
	int disabledLevel = 10000;
	private String lastElement = "";
	private Course course;

	public CourseHandler(Course course) {
		this.course = course;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		String string = new String(ch, start, length);
		if (lastElement.equals("title")) {
			course.title = string;
		} else if (lastElement.equals("description")) {
			course.description = string;
		} else if (lastElement.equals("guid")) {
			course.guid = string;
		} else if (lastElement.equals("author")) {
		} else if (lastElement.equals("rights-owner")) {
		} else if (lastElement.equals("hash")) {
		} else if (lastElement.equals("language-of-instruction")) {
			course.languageOfInstruction = string.trim();
		} else if (lastElement.equals("language-taught")) {
		} else if (lastElement.equals("default-new-items")) {
		} else if (lastElement.equals("created")) {
		} else if (lastElement.equals("modified")) {
		} else if (lastElement.equals("version")) {
		}
	}

	@Override
	public void startElement(String uri, String tagName, String qName,
			Attributes attributes) throws SAXException {
		lastElement = qName;
		
		if (qName.equals("element")) {
			
			int itemId = Integer.valueOf(attributes.getValue("id"));
			Item exercise = new Item(itemId);
			exercise.name = attributes.getValue("name");
			String typeOfExcercise = attributes.getValue("type");
			if (attributes.getValue("disabled")!=null) {
				disabledLevel = elementLevel;
				exercise.disabled = true;
			}
			if ("pres".equals(typeOfExcercise)) {
				exercise.type = Item.PRESENTATION;
			}
			if ("once".equals(typeOfExcercise)) {
				exercise.type = Item.ONCE;
			}
			if (disabledLevel < elementLevel) {
				exercise.disabled = true;
			}
			course.addExercise(exercise);
			elementLevel++;
		}
	}

	@Override
	public void endElement(String uri, String tagName, String qName)
			throws SAXException {

		if (qName.equals("element")) {
			elementLevel--;
		}
		if (elementLevel == disabledLevel) {
			disabledLevel = 10000;
		}

		lastElement = "";
	}

}
