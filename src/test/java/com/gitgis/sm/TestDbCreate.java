/**
 * 
 */
package com.gitgis.sm;

import org.slf4j.bridge.SLF4JBridgeHandler;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.Test;

import com.gitgis.sm.anki.AnkiDb;
import com.gitgis.sm.smpak.Course;
import com.gitgis.sm.smpak.CourseExercise;
import com.gitgis.sm.smpak.Exercise;
import com.gitgis.sm.smpak.SmParException;
import com.gitgis.sm.smpak.SmParser;

/**
 * @author gg
 *
 */
public class TestDbCreate {

	Connection conn;
	
	@BeforeClass
	public void init() throws SmParException {
		try {
			System.setProperty("org.sqlite.JDBC", "true");
			Class.forName("org.sqlite.JDBC");
			
			conn = DriverManager.getConnection("jdbc:sqlite:/tmp/empty.anki");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
	}
	
	@Test
	public void testCreate() {
		try {
			Statement statement = conn.createStatement();
//			ResultSet result = statement.executeQuery("SELECT * FROM items WHERE PageNum = 6");
//			ResultSetMetaData metaData = result.getMetaData();
//			while (result.next()) {
//				for (int colNo=0; colNo < metaData.getColumnCount(); colNo++) {
//					String columnName = metaData.getColumnName(colNo+1);
//					System.out.println(columnName+" = "+result.getString(columnName));
//				}
//				break;
//			}


			InputStream inputStream = AnkiDb.class.getResourceAsStream("empty.sql");
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			String command = "";
			while (null != (line = reader.readLine())) {
				
				line = line.trim();
				
				command+=" "+line;
				
				if (line.endsWith(";")) {
					System.out.println(command);
					Statement stmt = conn.createStatement();
					stmt.execute(command);
					command = "";
				}
			}

			Assert.assertTrue(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			conn.close();
		}
	}
	

	
	
}
