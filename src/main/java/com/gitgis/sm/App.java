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

import com.gitgis.sm.smdb.Item;
import com.gitgis.sm.smpak.Course;
import com.gitgis.sm.smpak.SmParException;
import com.gitgis.sm.smpak.SmParser;

public class App {

	// http://xerial.org/trac/Xerial/wiki/SQLiteJDBC
	
	public static void main(String args[]) {

		try {
			SmParser parser = new SmParser(
					"/var/www/testanki/Niemiecki Kein Problem 3/course");

			unpakAll(parser);
//			printCourse(parser);
//			readSqlLite();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 */
	private static void readSqlLite() {
		// TODO Auto-generated method stub
		try {
			System.setProperty("org.sqlite.JDBC", "true");
			Class.forName("org.sqlite.JDBC");
			
			Connection conn = DriverManager.getConnection("jdbc:sqlite:/home/gg/var/supermemo/Repetitions.dat");
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM items WHERE PageNum = 6");
			ResultSetMetaData metaData = result.getMetaData();
			while (result.next()) {
				for (int colNo=0; colNo < metaData.getColumnCount(); colNo++) {
					String columnName = metaData.getColumnName(colNo+1);
					System.out.println(columnName+" = "+result.getString(columnName));
				}
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void printCourse(SmParser parser) throws SmParException {
		Course course = parser.getCourse();
		System.out.println(course);
		for (Entry<Integer, Item> entry : course.getExercises().entrySet()) {
			Item exercise = entry.getValue();
			try {
				ExerciseConverter converter = new ExerciseConverter(exercise, parser.getInputStream(exercise.getEntryName()));
				exercise = converter.getExercise();
				
//				ExerciseConverter converter = new ExerciseConverter(itemId, new FileInputStream("/tmp/unpak2/"+entryName));
				System.out.println("======");
				System.out.println(exercise.getEntryName());
				System.out.println(converter.getExercise());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
//				System.out.println(parser.getItem(e));
			break;
		}
	}

	private static void unpakAll(SmParser parser) throws IOException, SmParException {
		int cnt = 0;
		
		Course course = parser.getCourse();
		System.out.println(course);
		
		String outputDir = "/tmp/unpak3";
		
		for (String fileName: parser.getFileEntryNames()) {
			try {
				InputStream is = parser.getInputStream(fileName);

				File file = new File(outputDir+"/" + fileName);
				file.getParentFile().mkdirs();
				FileOutputStream fo = new FileOutputStream(file);
				byte[] buf = new byte[100];
				int len;
				while ((len = is.read(buf, 0, buf.length)) > 0) {
					fo.write(buf, 0, len);
				}
				fo.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

//			if (cnt > 10)
//				break;
			cnt++;
		}

	}

}
