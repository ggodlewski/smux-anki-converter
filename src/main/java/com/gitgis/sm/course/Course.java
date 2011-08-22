package com.gitgis.sm.course;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.gitgis.sm.smpak.CourseHandler;
import com.gitgis.sm.smpak.Parser;


public class Course {

	private Map<Integer, Item> exercises = new LinkedHashMap<Integer, Item>();
	public String description;
	public String title;
	public String guid;
	public String languageOfInstruction = "en";

	/**
	 * Only for Unit Tests
	 */
	private Course() {  
	}
	
	public Course(Parser smPakParser, InputStream inputStream) throws IOException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			byte[] bom = new byte[3];
			inputStream.read(bom);

			SAXParser saxParser = factory.newSAXParser();
			DefaultHandler dh = new CourseHandler(this);
			saxParser.parse(new InputSource(new InputStreamReader(inputStream, "UTF-8")), dh);
		} catch (ParserConfigurationException e) {
			Logger.getLogger(Course.class.getName()).severe(e.getMessage());
			e.printStackTrace();
		} catch (SAXException e) {
			Logger.getLogger(Course.class.getName()).severe(e.getMessage());
			e.printStackTrace();
		}
	}

	public void addExercise(Item exercise) {
		exercises.put(exercise.id, exercise);
		
	}

	public Map<Integer, Item> getExercises() {
		return exercises;
	}

	public String toString() {
		return title+"["+exercises.size()+"]";
	}

	/**
	 * 
	 */
	public void printDetailed() {
		for (Entry<Integer, Item> entry: exercises.entrySet()) {
			Item exercise = entry.getValue();
			String lineStr = exercise.id+"\t"+exercise.name+"\t"+exercise.lastRepetition;
			lineStr+="";
			System.out.println(lineStr);
		}
	}

	/**
	 * Only for Unit Tests
	 * @return
	 */
	public static Course getEmptyInstance() {
		return new Course();
	}


}
