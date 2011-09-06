/**
 * 
 */
package com.gitgis.sm;

import org.slf4j.bridge.SLF4JBridgeHandler;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.gitgis.sm.course.Course;
import com.gitgis.sm.course.Item;
import com.gitgis.sm.smpak.SmPakException;
import com.gitgis.sm.smpak.SmParser;

import java.io.File;


/**
 * @author gg
 *
 */
public class TestConvert {

	
	private SmParser parser;
	private Course course;

	@BeforeClass
	public void init() throws SmPakException {
		SLF4JBridgeHandler.install();

		
		parser = new SmParser( new File("/var/www/testanki/Niemiecki Kein Problem 1") , "course" );

		course = Course.getEmptyInstance();
	}
	
	@Test
	public void testSoundAnswer() {
		try {
			Item exercise = new Item(6);
			ItemConverter converter = new ItemConverter(course, exercise, parser.getInputStream(exercise.getEntryName()));
			exercise = converter.getExercise();
			
			System.out.println(exercise.question);
			System.out.println(exercise.answer);
			
			Assert.assertTrue(exercise.answer.contains("[sound:00006a.mp3]"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSfx() {
		try {
			Item exercise = new Item(2);
			ItemConverter converter = new ItemConverter(course, exercise, parser.getInputStream(exercise.getEntryName()));
			exercise = converter.getExercise();
			
			System.out.println(exercise.question);
			System.out.println(exercise.answer);
			
			Assert.assertTrue(exercise.question.contains("[sound:00002c.mp3]"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	@Test
	public void testPresentation() {
		try {
			Item exercise = new Item(3);
			exercise.type = Item.PRESENTATION;
			ItemConverter converter = new ItemConverter(course, exercise, parser.getInputStream(exercise.getEntryName()));
			exercise = converter.getExercise();
			
			System.out.println(exercise.question);
			System.out.println(exercise.answer);
			
			Assert.assertTrue(exercise.question.contains("Lektion 1"));
			Assert.assertTrue(exercise.answer.contains("This card is a lesson converted from SuperMemo UX course"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
