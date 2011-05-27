/**
 * 
 */
package com.gitgis.sm;

import org.slf4j.bridge.SLF4JBridgeHandler;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.gitgis.sm.smdb.SmDbItem;
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
				"/home/gg/var/supermemo/Niemiecki Kein Problem 1/course");
	}
	
	@Test
	public void testSoundAnswer() {
		try {
			int id = 6;
			String itemId = String.format("%05d", id);
			String entryName = "/item"+itemId+".xml";
			ExerciseConverter converter = new ExerciseConverter(id, parser.getInputStream(entryName));
			SmDbItem exercise = converter.getExercise();
			System.out.println(exercise);
			
			Assert.assertTrue(exercise.answer.contains("[sound:00006a.mp3]"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSfx() {
		try {
			int id = 2;
			String itemId = String.format("%05d", id);
			String entryName = "/item"+itemId+".xml";
			ExerciseConverter converter = new ExerciseConverter(id, parser.getInputStream(entryName));
			SmDbItem anki = converter.getExercise();
			System.out.println(anki);
			
			Assert.assertTrue(anki.question.contains("[sound:00002c.mp3]"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	
}
