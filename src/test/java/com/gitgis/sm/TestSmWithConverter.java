/**
 * 
 */
package com.gitgis.sm;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.gitgis.sm.course.Course;
import com.gitgis.sm.smdb.SmDb;
import com.gitgis.sm.smdb.SmException;
import com.gitgis.sm.smpak.SmPakException;
import com.gitgis.sm.smpak.SmParser;

/**
 * @author gg
 *
 */
public class TestSmWithConverter {
	private SmDb smDb;
	private SmParser parser;
	
	@BeforeClass
	public void init() throws SmPakException {
		try {
			parser = new SmParser( new File("/var/www/testanki/Niemiecki Kein Problem 1"), "/course");

			smDb = SmDb.getInstance(new File("/var/www/testanki/Repetitions.dat"));
		} catch (SmException e1) {
			e1.printStackTrace();
		}
	}
	
	@Test
	public void testItems() {
		try {
			Assert.assertTrue(true);

			Course course = parser.getCourse();

			if (smDb!=null) {
				smDb.getItems(course);
			}

//			System.out.println(course.getExercises());
//			for (Entry<Integer, Item> entry: exercises.entrySet()) {
//				Item exercise = entry.getValue();
//				String lineStr = exercise.id+"\t"+exercise.name+"\t"+exercise.lastRepetition;
//				lineStr+="";
//				System.out.println(lineStr);
//			}
//			ExerciseConverter converter = new ExerciseConverter(id, parser.getInputStream(entryName));
//			Item anki = converter.getExercise();
			
//			Item exercise = course.getExercises().get(202);
//			String lineStr = exercise.id+"\t"+exercise.name+"\t"+exercise.lastRepetition+"\t"+exercise.nextRepetition+"\t"+exercise.learned;
//			lineStr+="";
//			System.out.println(lineStr);

			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			conn.close();
		}
	}
}
