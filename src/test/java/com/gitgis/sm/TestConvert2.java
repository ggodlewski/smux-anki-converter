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
			int id = 9;
			String itemId = String.format("%05d", id);
			String entryName = "/item"+itemId+".xml";
			ExerciseConverter converter = new ExerciseConverter(id, parser.getInputStream(entryName));
			SmDbItem anki = converter.getExercise();
			
			Assert.assertTrue(anki.question.contains("die Frau<span style=\"color: red\">(e, s, en)</span>"));
			Assert.assertTrue(anki.answer.contains("die Frau<span style=\"color: red\">en</span>"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testDropList() {
		try {
			int id = 117;
			String itemId = String.format("%05d", id);
			String entryName = "/item"+itemId+".xml";
			ExerciseConverter converter = new ExerciseConverter(id, parser.getInputStream(entryName));
			SmDbItem anki = converter.getExercise();
			
			Assert.assertTrue(anki.question.contains("<span style=\"color: red\">(Studieren, Studierst, Studiert, Studiere)</span> du Geschichte?"));
			Assert.assertTrue(anki.answer.contains("<span style=\"color: red\">Studierst</span> du Geschichte?"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Test
	public void testOrderingList() {
		try {
			int id = 11;
			String itemId = String.format("%05d", id);
			String entryName = "/item"+itemId+".xml";
			ExerciseConverter converter = new ExerciseConverter(id, parser.getInputStream(entryName));
			SmDbItem anki = converter.getExercise();
//			System.out.println(anki.question);
//			System.out.println(anki.answer);
			
//			Assert.assertTrue(anki.question.contains("<span style=\"color: red\">&lt;geht, ? , es, Wie&gt;</span>"));
			Assert.assertTrue(anki.answer.contains("<span style=\"color: red\">&lt;Wie geht es ? &gt;</span>"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSelectPhrases() {
		try {
			int id = 135;
			String itemId = String.format("%05d", id);
			String entryName = "/item"+itemId+".xml";
			ExerciseConverter converter = new ExerciseConverter(id, parser.getInputStream(entryName));
			SmDbItem anki = converter.getExercise();
			
			Assert.assertTrue(anki.question.contains("<span style=\"color: red\">[			Herr Stadler,  			arbeitete,  			früher,  			in Düsseldorf. ,  			Jedoch,  			musste,  			er,  			seinen,  			Arbeitsplatz,  			wechseln. ,  					]</span>"));
			Assert.assertTrue(anki.answer.contains("<span style=\"color: red\">[				arbeitete, musste, 			]</span>"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	


	@Test
	public void testSelectPhrasesStrike() {
		try {
			int id = 1611;
			String itemId = String.format("%05d", id);
			String entryName = "/item"+itemId+".xml";
			ExerciseConverter converter = new ExerciseConverter(id, parser.getInputStream(entryName));
			SmDbItem anki = converter.getExercise();
			
			Assert.assertTrue(anki.question.contains("<span style=\"color: red\">[			Vater,  			Mutter,  			Techniker,  			Sohn,  					]</span>"));
			Assert.assertTrue(anki.answer.contains("<span style=\"color: red\">[				Vater, Mutter, <strike>Techniker, </strike>Sohn, 			]</span>"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Test
	public void testSentence() {
		try {
			int id = 4;
			String itemId = String.format("%05d", id);
			String entryName = "/item"+itemId+".xml";
			ExerciseConverter converter = new ExerciseConverter(id, parser.getInputStream(entryName));
			SmDbItem anki = converter.getExercise();
//			System.out.println(anki.question);
//			System.out.println(anki.answer);
			
			Assert.assertTrue(anki.question.contains("<span style=\"color: red\"><em>Guten Tag, Frau Fischer!</em> - <strong>Dzień dobry, pani Fischer!</strong></span>"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	
	@Test
	public void testDragDrop() {
		try {
			int id = 124;
			String itemId = String.format("%05d", id);
			String entryName = "/item"+itemId+".xml";
			ExerciseConverter converter = new ExerciseConverter(id, parser.getInputStream(entryName));
			SmDbItem anki = converter.getExercise();
			
			Assert.assertTrue(anki.question.contains("Grüß <span style=\"color: red\">(dir, Sie, Ihnen, dich)</span>, Paul!"));
			Assert.assertTrue(anki.answer.contains("Grüß <span style=\"color: red\">dich</span>, Paul!"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
