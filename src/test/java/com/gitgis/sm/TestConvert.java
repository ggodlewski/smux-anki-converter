/**
 * 
 */
package com.gitgis.sm;

import org.slf4j.bridge.SLF4JBridgeHandler;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.Test;
import java.io.FileInputStream;
import java.io.IOException;

import org.testng.annotations.Test;

import com.gitgis.sm.smpak.Course;
import com.gitgis.sm.smpak.CourseExercise;
import com.gitgis.sm.smpak.Exercise;
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

			String itemId = String.format("%05d", 6);
			String entryName = "/item"+itemId+".xml";
			ExerciseConverter converter = new ExerciseConverter(itemId, parser.getInputStream(entryName));
			Exercise anki = converter.getExercise();
			System.out.println(anki);
			
			Assert.assertTrue(anki.getAnswer().contains("[sound:00006a.mp3]"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSfx() {
		try {
			String itemId = String.format("%05d", 2);
			String entryName = "/item"+itemId+".xml";
			ExerciseConverter converter = new ExerciseConverter(itemId, parser.getInputStream(entryName));
			Exercise anki = converter.getExercise();
			System.out.println(anki);
			
			Assert.assertTrue(anki.getQuestion().contains("[sound:00002c.mp3]"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	
}
