/**
 * 
 */
package com.gitgis.sm;

import org.slf4j.bridge.SLF4JBridgeHandler;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.gitgis.sm.smdb.Item;
import com.gitgis.sm.smpak.SmParException;
import com.gitgis.sm.smpak.SmParser;

/**
 * @author gg
 *
 */
public class TestConvert {

	
	private SmParser parser;

	@BeforeClass
	public void init() throws SmParException {
		SLF4JBridgeHandler.install();

		parser = new SmParser(
				"/var/www/testanki/Niemiecki Kein Problem 1/course");
	}
	
	@Test
	public void testSoundAnswer() {
		try {
			Item exercise = new Item(6);
			ExerciseConverter converter = new ExerciseConverter(exercise, parser.getInputStream(exercise.getEntryName()));
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
			ExerciseConverter converter = new ExerciseConverter(exercise, parser.getInputStream(exercise.getEntryName()));
			exercise = converter.getExercise();
			
			System.out.println(exercise);
			
			Assert.assertTrue(exercise.question.contains("[sound:00002c.mp3]"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	
}
