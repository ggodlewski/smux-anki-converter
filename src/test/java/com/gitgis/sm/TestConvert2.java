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
public class TestConvert2 {

	
	private SmParser parser;

	@BeforeClass
	public void init() throws SmParException {
		SLF4JBridgeHandler.install();

		parser = new SmParser(
				"/var/www/testanki/Niemiecki Kein Problem 1/course");
	}
	
	@Test
	public void testRadio() {
		try {
			Item exercise = new Item(9);
			ExerciseConverter converter = new ExerciseConverter(exercise, parser.getInputStream(exercise.getEntryName()));
			exercise = converter.getExercise();
			
			System.out.println(exercise.question);
			System.out.println(exercise.answer);
			
			
			Assert.assertTrue(exercise.question.contains("die Frau<span style=\"color: red\">(e, s, en)</span>"));
			Assert.assertTrue(exercise.answer.contains("die Frau<span style=\"color: green\">en</span>"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testDropList() {
		try {
			Item exercise = new Item(117);
			ExerciseConverter converter = new ExerciseConverter(exercise, parser.getInputStream(exercise.getEntryName()));
			exercise = converter.getExercise();
			
			Assert.assertTrue(exercise.question.contains("<span style=\"color: red\">(Studieren, Studierst, Studiert, Studiere)</span> du Geschichte?"));
			Assert.assertTrue(exercise.answer.contains("<span style=\"color: green\">Studierst</span> du Geschichte?"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Test
	public void testOrderingList() {
		try {
			Item exercise = new Item(11);
			ExerciseConverter converter = new ExerciseConverter(exercise, parser.getInputStream(exercise.getEntryName()));
			exercise = converter.getExercise();
			
			System.out.println(exercise.question);
			System.out.println(exercise.answer);
			
//			Assert.assertTrue(exercise.question.contains("<span style=\"color: red\">&lt;geht, ? , es, Wie&gt;</span>"));
			Assert.assertTrue(exercise.answer.contains("<span style=\"color: green\">&lt;Wie geht es ? &gt;</span>"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSelectPhrases() {
		try {
			Item exercise = new Item(135);
			ExerciseConverter converter = new ExerciseConverter(exercise, parser.getInputStream(exercise.getEntryName()));
			exercise = converter.getExercise();
			
			Assert.assertTrue(exercise.question.contains("<span style=\"color: red\">[			Herr Stadler,  			arbeitete,  			früher,  			in Düsseldorf. ,  			Jedoch,  			musste,  			er,  			seinen,  			Arbeitsplatz,  			wechseln. ,  					]</span>"));
			Assert.assertTrue(exercise.answer.contains("<span style=\"color: green\">[				arbeitete, musste, 			]</span>"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	


	@Test
	public void testSelectPhrasesStrike() {
		try {
			Item exercise = new Item(1611);
			ExerciseConverter converter = new ExerciseConverter(exercise, parser.getInputStream(exercise.getEntryName()));
			exercise = converter.getExercise();

			System.out.println(exercise.question);
			System.out.println(exercise.answer);
			
			Assert.assertTrue(exercise.question.contains("<span style=\"color: red\">[			Vater,  			Mutter,  			Techniker,  			Sohn,  					]</span>"));
			Assert.assertTrue(exercise.answer.contains("<span style=\"color: green\">[				Vater, Mutter, <strike>Techniker, </strike>Sohn, 			]</span>"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Test
	public void testSentence() {
		try {
			Item exercise = new Item(4);
			ExerciseConverter converter = new ExerciseConverter(exercise, parser.getInputStream(exercise.getEntryName()));
			exercise = converter.getExercise();

			System.out.println(exercise.question);
			System.out.println(exercise.answer);

			Assert.assertTrue(exercise.question.contains("<span style=\"color: green\"><em>Guten Tag, Frau Fischer!</em> - <strong>Dzień dobry, pani Fischer!</strong></span>"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	
	@Test
	public void testDragDrop() {
		try {
			Item exercise = new Item(124);
			ExerciseConverter converter = new ExerciseConverter(exercise, parser.getInputStream(exercise.getEntryName()));
			exercise = converter.getExercise();

			System.out.println(exercise.question);
			System.out.println(exercise.answer);

			Assert.assertTrue(exercise.question.contains("Grüß <span style=\"color: red\">(dir, Sie, Ihnen, dich)</span>, Paul!"));
			Assert.assertTrue(exercise.answer.contains("Grüß <span style=\"color: green\">dich</span>, Paul!"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
