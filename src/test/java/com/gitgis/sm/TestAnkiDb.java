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
import java.io.File;
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
import com.gitgis.sm.anki.AnkiException;
import com.gitgis.sm.smpak.Course;
import com.gitgis.sm.smpak.CourseExercise;
import com.gitgis.sm.smpak.Exercise;
import com.gitgis.sm.smpak.SmParException;
import com.gitgis.sm.smpak.SmParser;

/**
 * @author gg
 *
 */
public class TestAnkiDb {

	AnkiDb db;
	
	@BeforeClass
	public void init() throws SmParException {
		try {
			db = new AnkiDb(new File("/home/gg/testanki/Niemiecki Kein Problem 1"));
		} catch (AnkiException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	@Test
	public void testCreate() {
		try {
			Assert.assertTrue(true);
			
			db.addMedia("/home/gg/1.png", new FileInputStream("/home/gg/1.png"));
			db.addCard();
			db.addCard();
			db.addCard();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			conn.close();
		}
	}
	

	
	
}
