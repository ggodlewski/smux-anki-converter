/**
 * 
 */
package com.gitgis.sm;

import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.gitgis.sm.anki.AnkiDb;
import com.gitgis.sm.anki.AnkiException;
import com.gitgis.sm.course.Course;
import com.gitgis.sm.course.Item;
import com.gitgis.sm.smdb.SmDb;
import com.gitgis.sm.smdb.SmException;
import com.gitgis.sm.smpak.SmPakException;
import com.gitgis.sm.smpak.SmParser;

/**
 * @author gg
 *
 */
public class TestAnkiDb {

	AnkiDb ankiDb;
	private SmDb smDb;
	private SmParser parser;
	
	@BeforeClass
	public void init() throws SmPakException {
		try {
			String mainDir = "/var/www/testanki";
			String courseDir = mainDir+"/Niemiecki Kein Problem 1";
			
			ankiDb = new AnkiDb(new File(courseDir));
			parser = new SmParser(new File(courseDir), "course");
			smDb = SmDb.getInstance(new File(mainDir+"/Repetitions.dat"));
		} catch (AnkiException e1) {

			e1.printStackTrace();
		} catch (SmException e1) {
			e1.printStackTrace();
		}
		
	}
	
	@Test
	public void testCreate() {
		try {
			Assert.assertTrue(true);
			
//			ankiDb.putMedia("/home/gg/1.png", new FileInputStream("/home/gg/1.png"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			conn.close();
		}
	}
	
	@Test(enabled=false)
	public void testPutCourse() {
		try {
			Course course = parser.getCourse();
			if (smDb!=null) {
				smDb.getItems(course);
			}
			
			for (String entryName: parser.getFileEntryNames()) {
				if (entryName.startsWith("/media")) {
					String fileName = entryName;
					if (fileName.endsWith(".media")) {
						fileName = fileName.substring(0, fileName.length()-".media".length())+".mp3";
					}
					if (fileName.endsWith(".mp3")) {
						System.out.println(fileName);
						ankiDb.putMedia(fileName, parser.getInputStream(entryName));
					}
				}
			}
			
			for (Entry<Integer, Item> entry: course.getExercises().entrySet()) {
				Item item = entry.getValue();
				ItemConverter converter = new ItemConverter(course, item, parser.getInputStream(item.getEntryName()));
				item = converter.getExercise();
				
				System.out.println(item);
				ankiDb.putItemToCard(item);
			}
			
		} catch (SmPakException e) {
			
			e.printStackTrace();
		} catch (SmException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (AnkiException e) {
			
			e.printStackTrace();
		}

	}
	
	
}
