package com.gitgis.sm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

		      
		String strMainDir = ".";
		File unpackDir = null;
		if (args.length>0) {
			strMainDir = args[0];
		}
		if (args.length>1) {
			unpackDir = new File(args[1]);
			if (!unpackDir.exists()) {
				unpackDir.mkdirs();
			}
		}
		int courseCount = 0;
		File mainDir = new File(strMainDir);
		if (mainDir.exists() && mainDir.isDirectory()) {
			String[] list = mainDir.list();
			if (list!=null) {
				for (String subDirName: list) {
					
					File courseDir = new File( mainDir, subDirName );
					File courseFile = new File( courseDir, "course.smpak" );
					if (courseFile.exists()) {
						try {
							courseCount++;
							logger.info("Converting course "+courseCount+": "+subDirName);
												
							AnkiDb ankiDb = new AnkiDb( courseDir );
							SmParser parser = new SmParser( courseDir, "course" );
							
							SmDb smDb = SmDb.getInstance(new File(mainDir , "Repetitions.dat"));
							
							
							for (String entryName: parser.getFileEntryNames()) {
								logger.info("entryName: "+entryName);

								if (unpackDir != null) {
									File outputFileDir = new File(unpackDir.getAbsolutePath()+"/"+entryName.substring(0, entryName.lastIndexOf("/")));
									if (!outputFileDir.exists()) {
										outputFileDir.mkdirs();
									}
									FileOutputStream outputStream = new FileOutputStream(unpackDir.getAbsolutePath()+"/"+entryName);
									InputStream inputStream = parser.getInputStream(entryName);
									logger.info("unpack to: "+unpackDir.getCanonicalPath()+"/"+entryName);
									StreamsUtil.copyStream(inputStream, outputStream);
									inputStream.close();
									outputStream.close();
								}
								
								if (entryName.startsWith("/media")) {
									String fileName = entryName;
									if (fileName.endsWith(".media")) {
										fileName = fileName.substring(0, fileName.length()-".media".length())+".mp3";
									}
									if (fileName.endsWith(".mp3")) {
										logger.info("New media file: "+fileName);
										InputStream inputStream = parser.getInputStream(entryName);
										ankiDb.putMedia(fileName, inputStream);
										inputStream.close();
									}
								}
								
							}

							Course course = parser.getCourse();
							course.printDetailed();
							if (smDb!=null) {
								smDb.getItems(course);
							}
							
							// process every entry in the course...
							int count = 0;
							for (Entry<Integer, Item> entry: course.getExercises().entrySet()){
								Item item = entry.getValue();
								String entryName = item.getEntryName();
 								ItemConverter converter = new ItemConverter(course, item, parser.getInputStream(entryName));
								item = converter.getExercise();
								
								//
								// don't log every Q and A at info level...
								//
								logger.debug("New card: "+item.toString());
								ankiDb.putItemToCard(item);
								count++;
							}
							logger.info( count + " cards processed in course");
						} catch (AnkiException e) {
							
							e.printStackTrace();
						} catch (SmPakException e) {
							
							e.printStackTrace();
						} catch (SmException e) {
							
							e.printStackTrace();
						} catch (IOException e) {
						
							e.printStackTrace();
						} finally {
							
						}
					}
				}
			}
			logger.info(courseCount + " courses processed" );
		}

	}


}
