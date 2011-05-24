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
public class TestConvert2 {

	
	private SmParser parser;

	@BeforeClass
	public void init() throws SmParException {
		SLF4JBridgeHandler.install();

		parser = new SmParser(
				"/home/gg/var/supermemo/Niemiecki Kein Problem 1/course");
	}
	
	@Test
	public void testRadio() {
		try {
			String itemId = String.format("%05d", 9);
			String entryName = "/item"+itemId+".xml";
			ExerciseConverter converter = new ExerciseConverter(itemId, parser.getInputStream(entryName));
			Exercise anki = converter.getExercise();
			
			Assert.assertTrue(anki.getQuestion().contains("die Frau<span style=\"color: red\">(e, s, en)</span>"));
			Assert.assertTrue(anki.getAnswer().contains("die Frau<span style=\"color: red\">en</span>"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testDropList() {
		try {
			String itemId = String.format("%05d", 117);
			String entryName = "/item"+itemId+".xml";
			ExerciseConverter converter = new ExerciseConverter(itemId, parser.getInputStream(entryName));
			Exercise anki = converter.getExercise();
			
			Assert.assertTrue(anki.getQuestion().contains("<span style=\"color: red\">(Studieren, Studierst, Studiert, Studiere)</span> du Geschichte?"));
			Assert.assertTrue(anki.getAnswer().contains("<span style=\"color: red\">Studierst</span> du Geschichte?"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Test
	public void testOrderingList() {
		try {
			String itemId = String.format("%05d", 11);
			String entryName = "/item"+itemId+".xml";
			ExerciseConverter converter = new ExerciseConverter(itemId, parser.getInputStream(entryName));
			Exercise anki = converter.getExercise();
//			System.out.println(anki.getQuestion());
//			System.out.println(anki.getAnswer());
			
//			Assert.assertTrue(anki.getQuestion().contains("<span style=\"color: red\">&lt;geht, ? , es, Wie&gt;</span>"));
			Assert.assertTrue(anki.getAnswer().contains("<span style=\"color: red\">&lt;Wie geht es ? &gt;</span>"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSelectPhrases() {
		try {
			String itemId = String.format("%05d", 135);
			String entryName = "/item"+itemId+".xml";
			ExerciseConverter converter = new ExerciseConverter(itemId, parser.getInputStream(entryName));
			Exercise anki = converter.getExercise();
			
			Assert.assertTrue(anki.getQuestion().contains("<span style=\"color: red\">[			Herr Stadler,  			arbeitete,  			früher,  			in Düsseldorf. ,  			Jedoch,  			musste,  			er,  			seinen,  			Arbeitsplatz,  			wechseln. ,  					]</span>"));
			Assert.assertTrue(anki.getAnswer().contains("<span style=\"color: red\">[				arbeitete, musste, 			]</span>"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	


	@Test
	public void testSelectPhrasesStrike() {
		try {
			String itemId = String.format("%05d", 1611);
			String entryName = "/item"+itemId+".xml";
			ExerciseConverter converter = new ExerciseConverter(itemId, parser.getInputStream(entryName));
			Exercise anki = converter.getExercise();
			
			Assert.assertTrue(anki.getQuestion().contains("<span style=\"color: red\">[			Vater,  			Mutter,  			Techniker,  			Sohn,  					]</span>"));
			Assert.assertTrue(anki.getAnswer().contains("<span style=\"color: red\">[				Vater, Mutter, <strike>Techniker, </strike>Sohn, 			]</span>"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Test
	public void testSentence() {
		try {
			String itemId = String.format("%05d", 4);
			String entryName = "/item"+itemId+".xml";
			ExerciseConverter converter = new ExerciseConverter(itemId, parser.getInputStream(entryName));
			Exercise anki = converter.getExercise();
//			System.out.println(anki.getQuestion());
//			System.out.println(anki.getAnswer());
			
			Assert.assertTrue(anki.getQuestion().contains("<span style=\"color: red\"><em>Guten Tag, Frau Fischer!</em> - <strong>Dzień dobry, pani Fischer!</strong></span>"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	
	@Test
	public void testDragDrop() {
		try {
			String itemId = String.format("%05d", 124);
			String entryName = "/item"+itemId+".xml";
			ExerciseConverter converter = new ExerciseConverter(itemId, parser.getInputStream(entryName));
			Exercise anki = converter.getExercise();
			
			Assert.assertTrue(anki.getQuestion().contains("Grüß <span style=\"color: red\">(dir, Sie, Ihnen, dich)</span>, Paul!"));
			Assert.assertTrue(anki.getAnswer().contains("Grüß <span style=\"color: red\">dich</span>, Paul!"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
