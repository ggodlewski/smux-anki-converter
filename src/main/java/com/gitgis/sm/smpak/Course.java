package com.gitgis.sm.smpak;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.gitgis.sm.smdb.SmDbItem;


public class Course {

	private Map<Integer, SmDbItem> exercises = new LinkedHashMap<Integer, SmDbItem>();
	public String description;
	public String title;
	public String guid;
	
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

	public void addExercise(SmDbItem exercise) {
		exercises.put(exercise.id, exercise);
		
	}

	public Map<Integer, SmDbItem> getExercises() {
		return exercises;
	}

	public String toString() {
		return title+"["+exercises.size()+"]";
	}


}
