/**
 * 
 */
package com.gitgis.sm;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.gitgis.sm.anki.AnkiDb;
import com.gitgis.sm.anki.AnkiException;
import com.gitgis.sm.smdb.SmDb;
import com.gitgis.sm.smdb.SmDbItem;
import com.gitgis.sm.smdb.SmException;
import com.gitgis.sm.smpak.SmParException;

/**
 * @author gg
 *
 */
public class TestSmDb {
	SmDb db;
	
	@BeforeClass
	public void init() throws SmParException {
		try {
			db = new SmDb(new File("/home/gg/testanki/Repetitions.dat"));
		} catch (SmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	@Test
	public void testItems() {
		try {
			Assert.assertTrue(true);

			Map<Integer, SmDbItem> items = new HashMap<Integer, SmDbItem>();
			
			db.getItems("dd8a1e73-279c-4059-8bcc-c2f3b906d203", items);
			
			System.out.println(items);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			conn.close();
		}
	}
}
