package com.gitgis.sm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gitgis.sm.anki.AnkiDb;
import com.gitgis.sm.anki.AnkiException;
import com.gitgis.sm.course.Course;
import com.gitgis.sm.course.Item;
import com.gitgis.sm.smdb.SmDb;
import com.gitgis.sm.smdb.SmException;
import com.gitgis.sm.smpak.SmPakException;
import com.gitgis.sm.smpak.SmParser;

public class App {

	// http://xerial.org/trac/Xerial/wiki/SQLiteJDBC
	private final static Logger logger = LoggerFactory.getLogger(App.class.getCanonicalName());
	
	public static void main(String args[]) {

		
		String mainDir = ".";
		if (args.length>0) {
			mainDir = args[0];
		}
		
		File dir = new File(mainDir);
		if (dir.exists() && dir.isDirectory()) {
			String[] list = dir.list();
			if (list!=null) {
				for (String subDirName: list) {
					File file = new File(mainDir+"/"+subDirName+"/course.smpak");
					if (file.exists()) {
						try {
							logger.info("Converting course: "+subDirName);
							
							String courseDir = mainDir+"/"+subDirName;
							
							AnkiDb ankiDb = new AnkiDb(new File(courseDir));
							SmParser parser = new SmParser(courseDir+"/course");
							SmDb smDb = SmDb.getInstance(new File(mainDir+"/Repetitions.dat"));
							
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
										logger.info("New media file: "+fileName);
										ankiDb.putMedia(fileName, parser.getInputStream(entryName));
									}
								}
							}
							
							for (Entry<Integer, Item> entry: course.getExercises().entrySet()) {
								Item item = entry.getValue();
								ItemConverter converter = new ItemConverter(course, item, parser.getInputStream(item.getEntryName()));
								item = converter.getExercise();
								
								logger.info("New card: "+item.toString());
								ankiDb.putItemToCard(item);
							}
						} catch (AnkiException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SmPakException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SmException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
							
						}
					}
				}
			}
		}
	}

}
