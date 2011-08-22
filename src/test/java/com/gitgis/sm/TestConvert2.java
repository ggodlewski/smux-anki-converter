/**
 * 
 */
package com.gitgis.sm;

import java.io.File;

import org.slf4j.bridge.SLF4JBridgeHandler;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.gitgis.sm.course.Course;
import com.gitgis.sm.course.Item;
import com.gitgis.sm.smpak.SmPakException;
import com.gitgis.sm.smpak.SmParser;

/**
 * @author gg
 *
 */
public class TestConvert2 {

	
	private SmParser parser;
	private Course course;

	@BeforeClass
	public void init() throws SmPakException {
		SLF4JBridgeHandler.install();
		
		parser = new SmParser( new File("/var/www/testanki/Niemiecki Kein Problem 1"), "course" );
		course = Course.getEmptyInstance();
	}
	
	@Test
	public void testRadio() {
		try {
			Item exercise = new Item(9);
			ItemConverter converter = new ItemConverter(course, exercise, parser.getInputStream(exercise.getEntryName()));
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
			ItemConverter converter = new ItemConverter(course, exercise, parser.getInputStream(exercise.getEntryName()));
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
			ItemConverter converter = new ItemConverter(course, exercise, parser.getInputStream(exercise.getEntryName()));
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
			ItemConverter converter = new ItemConverter(course, exercise, parser.getInputStream(exercise.getEntryName()));
			exercise = converter.getExercise();

			System.out.println(exercise.question);
			System.out.println(exercise.answer);

			Assert.assertTrue(exercise.question.contains("<span style=\"color: red\">[ Herr Stadler, arbeitete, früher, in Düsseldorf. , Jedoch, musste, er, seinen, Arbeitsplatz, wechseln. , ]</span>"));
			Assert.assertTrue(exercise.answer.contains("<span style=\"color: green\">[ arbeitete, musste, ]</span>"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	


	@Test
	public void testSelectPhrasesStrike() {
		try {
			Item exercise = new Item(1611);
			ItemConverter converter = new ItemConverter(course, exercise, parser.getInputStream(exercise.getEntryName()));
			exercise = converter.getExercise();

			System.out.println(exercise.question);
			System.out.println(exercise.answer);
			
			Assert.assertTrue(exercise.question.contains("<span style=\"color: red\">[ Vater, Mutter, Techniker, Sohn, ]</span>"));
			Assert.assertTrue(exercise.answer.contains("<span style=\"color: green\">[ Vater, Mutter, <strike>Techniker, </strike>Sohn, ]</span>"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Test
	public void testSentence() {
		try {
			Item exercise = new Item(4);
			ItemConverter converter = new ItemConverter(course, exercise, parser.getInputStream(exercise.getEntryName()));
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
			ItemConverter converter = new ItemConverter(course, exercise, parser.getInputStream(exercise.getEntryName()));
			exercise = converter.getExercise();

			System.out.println(exercise.question);
			System.out.println(exercise.answer);
			
//			<h1>Wskaż odpowiedni zaimek osobowy. </h1><br/>Grüß <span style="color: red">(Sie, dich, Ihnen, dir)</span>, Paul!
//			<br/>Grüß dich, Paul!<span style="color: green">Grüß dich, Paul!</span>Grüß dich, Paul![sound:00124a.mp3]

			Assert.assertTrue(exercise.answer.contains("<span style=\"color: green\">Grüß dich, Paul!</span>"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDragDrop2() {
		try {
			Item exercise = new Item(141);
			ItemConverter converter = new ItemConverter(course, exercise, parser.getInputStream(exercise.getEntryName()));
			exercise = converter.getExercise();

			System.out.println(exercise.question);
			System.out.println(exercise.answer);

//			<h1>Jakiego odpowiednika podanych formalnych zwrotów użyjesz, zwracając się do swojego przyjaciela? </h1>[0] - Was machen Sie hier? [1] - Guten Tag! [2] - Auf Wiedersehen! [3] - Entschuldigen Sie! <span style="color: red">(Tschüss! , Was machst du (denn) hier? , Hallo! / Grüß dich! , Wie heißt du? , Entschuldigung! )</span> - Wie ist Ihr Name? 
//			[0] - Was machen Sie hier? [1] - Guten Tag! [2] - Auf Wiedersehen! [3] - Entschuldigen Sie! [4] - Wie ist Ihr Name? <span style="color: green">[0] - Was machen Sie hier? [1] - Guten Tag! [2] - Auf Wiedersehen! [3] - Entschuldigen Sie! [4] - Wie ist Ihr Name? </span>[0] - Was machen Sie hier? [1] - Guten Tag! [2] - Auf Wiedersehen! [3] - Entschuldigen Sie! [4] - Wie ist Ihr Name? [sound:00141a.mp3]

			
			Assert.assertTrue(exercise.answer.contains("Was machst du (denn) hier? - Was machen Sie hier?"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
