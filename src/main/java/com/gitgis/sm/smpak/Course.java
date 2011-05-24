package com.gitgis.sm.smpak;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class Course {

	private List<CourseExercise> exercises = new ArrayList<CourseExercise>();
	public String description;
	public String title;
	
	public Course(Parser smPakParser, InputStream inputStream) throws IOException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = factory.newSAXParser();
			DefaultHandler dh = new CourseHandler(this);
			saxParser.parse(inputStream, dh);
		} catch (ParserConfigurationException e) {
			Logger.getLogger(Course.class.getName()).severe(e.getMessage());
			e.printStackTrace();
		} catch (SAXException e) {
			Logger.getLogger(Course.class.getName()).severe(e.getMessage());
			e.printStackTrace();
		}
	}

	public void addExercise(CourseExercise excersise) {
		getExercises().add(excersise);
		
	}

	public List<CourseExercise> getExercises() {
		return exercises;
	}

	public String toString() {
		return title+"["+exercises.size()+"]";
	}


}
