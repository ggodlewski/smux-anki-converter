/**
 * 
 */
package com.gitgis.sm;

import java.io.File;
import java.io.FileInputStream;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.gitgis.sm.anki.AnkiDb;
import com.gitgis.sm.anki.AnkiException;
import com.gitgis.sm.smpak.SmParException;

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