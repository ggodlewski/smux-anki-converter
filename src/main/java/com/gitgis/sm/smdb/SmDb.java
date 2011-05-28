/**
 * 
 */
package com.gitgis.sm.smdb;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gitgis.sm.anki.AnkiException;
import com.gitgis.sm.smpak.Course;

/**
 * @author gg
 *
 */
public class SmDb {
	
	private Connection conn;

	private SmDb(File smFile) throws SmException {
		try {
			System.setProperty("org.sqlite.JDBC", "true");
			Class.forName("org.sqlite.JDBC");

			conn = DriverManager.getConnection("jdbc:sqlite:"
					+ smFile.getAbsolutePath());

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new SmException();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SmException();
		} finally {

		}
	}
	
	public static SmDb getInstance(File file) throws SmException {
		if (file.exists()) {
			return new SmDb(file);
		}
		return null;
	}

	/**
	 * @param string
	 * @throws SmException 
	 */
	public void getItems(Course course) throws SmException {
		Map<Integer, Item> retVal = course.getExercises();
		
		// TODO Auto-generated method stub
		try {
			
			/*
			CREATE TABLE Items ( 
			PageNum				integer NOT NULL ON CONFLICT ROLLBACK, 
			CourseId				integer NOT NULL ON CONFLICT ROLLBACK, 
			Type					smallint NOT NULL ON CONFLICT REPLACE DEFAULT 0, 
			Disabled				boolean NOT NULL ON CONFLICT REPLACE DEFAULT 0, 
			Subtype				smallint NOT NULL ON CONFLICT REPLACE DEFAULT 0, 
			ParentId				integer NOT NULL ON CONFLICT REPLACE DEFAULT 0, 
			Frequency				integer NOT NULL ON CONFLICT REPLACE DEFAULT 0, 
			Name					text NOT NULL ON CONFLICT REPLACE DEFAULT '', 
			Keywords				text NOT NULL ON CONFLICT REPLACE DEFAULT '', 
			PartOfSpeech			text NOT NULL ON CONFLICT REPLACE DEFAULT '', 
			QueueOrder				integer NOT NULL ON CONFLICT REPLACE DEFAULT 1, 
			PendingDate			date, 
			Status					smallint NOT NULL ON CONFLICT REPLACE DEFAULT 0, 
			LastRepetition			integer NOT NULL ON CONFLICT REPLACE DEFAULT 0, 
			NextRepetition			integer NOT NULL ON CONFLICT REPLACE DEFAULT 0, 
			AFactor				float(10,2) NOT NULL ON CONFLICT REPLACE DEFAULT 3, 
			EstimatedFI			float(10) NOT NULL ON CONFLICT REPLACE DEFAULT 0, 
			ExpectedFI				float(10) NOT NULL ON CONFLICT REPLACE DEFAULT 0, 
			FirstGrade				smallint NOT NULL ON CONFLICT REPLACE DEFAULT 6, 
			Flags					smallint NOT NULL ON CONFLICT REPLACE DEFAULT 0, 
			Grades					int NOT NULL ON CONFLICT REPLACE DEFAULT 0, 
			Lapses					smallint NOT NULL ON CONFLICT REPLACE DEFAULT 0, 
			NewInterval			integer NOT NULL ON CONFLICT REPLACE DEFAULT 0, 
			NormalizedGrade		float NOT NULL ON CONFLICT REPLACE DEFAULT 0, 
			Repetitions			smallint NOT NULL ON CONFLICT REPLACE DEFAULT 0, 
			RepetitionsCategory	smallint NOT NULL ON CONFLICT REPLACE DEFAULT 0, 
			UFactor				float(10,2) NOT NULL ON CONFLICT REPLACE DEFAULT 0, 
			UsedInterval			integer NOT NULL ON CONFLICT REPLACE DEFAULT 0, 
			OrigNewInterval integer NOT NULL ON CONFLICT REPLACE DEFAULT 0, 
			PRIMARY KEY(CourseId, PageNum))
			*/

			
			PreparedStatement stmt = conn.prepareStatement("SELECT id FROM courses WHERE guid=?");
			stmt.setString(1, course.guid);
			ResultSet resultSet = stmt.executeQuery();
			int courseId = resultSet.getInt(1);
			
			stmt = conn.prepareStatement("SELECT PageNum, Name, Type, LastRepetition, NextRepetition, AFactor, " +
					" EstimatedFI, ExpectedFI, FirstGrade, NewInterval, NormalizedGrade, " +
					" Repetitions, RepetitionsCategory, UFactor, UsedInterval, OrigNewInterval, Disabled, Status " +
					" FROM items WHERE CourseId=? ORDER BY QueueOrder");
			stmt.setInt(1, courseId);
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				Item item = retVal.get(resultSet.getInt(1));
				if (item!=null) {
					item.dbdata = true;
					item.name = resultSet.getString(2);
					item.type = resultSet.getInt(3)==5 ? Item.LESSON : Item.EXERCISE; 
					item.lastRepetition = new Date((Long.valueOf(resultSet.getLong(4)))*1000*3600*24);
					item.nextRepetition = new Date((Long.valueOf(resultSet.getLong(5)))*1000*3600*24);
					item.aFactor = resultSet.getFloat(6);
					item.estimatedFI = resultSet.getFloat(7);
					item.expectedFI = resultSet.getFloat(8);
					item.firstGrade = resultSet.getInt(9);
					item.newInterval = resultSet.getInt(10);
					item.normalizedGrade = resultSet.getFloat(11);
					item.repetitions = resultSet.getInt(12); 
					item.repetitionsCategory = resultSet.getInt(13); 
					item.uFactor = resultSet.getFloat(14);
					item.usedInterval = resultSet.getInt(15);
					item.origNewInterval = resultSet.getInt(16);
					if (resultSet.getBoolean(17)) {
						item.disabled = true;
					}
					item.learned = resultSet.getInt(18)==1;
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SmException();
		}
		
	}
}
