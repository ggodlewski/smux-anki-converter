/**
 * 
 */
package com.gitgis.sm.anki;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import com.gitgis.sm.StreamsUtil;
import com.gitgis.sm.course.Item;

/**
 * @author gg
 * 
 */
public class AnkiDb {

	private Connection conn;
	private final File mediaDir;

	Long modelId = 5178503160903817499L;
	Long cardModelId = -450455413588436709L;
	Long fieldModelIdQuestion = 7752868899852966171L;
	Long fieldModelIdAnswer = -549677541902198501L;

	/**
	 * @param string
	 * @throws AnkiException
	 */
	public AnkiDb(File smDir) throws AnkiException {
		try {
			System.setProperty("org.sqlite.JDBC", "true");
			Class.forName("org.sqlite.JDBC");

			File file2 = new File(smDir.getAbsoluteFile() + ".anki");
			mediaDir = new File(smDir.getAbsoluteFile() + ".media");

			if (!mediaDir.exists()) {
				mediaDir.mkdirs();
			}

			conn = DriverManager.getConnection("jdbc:sqlite:"
					+ file2.getAbsolutePath());

			if (isEmpty()) {
				create();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new AnkiException();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AnkiException();
		} finally {

		}
	}

	/**
	 * @return
	 */
	private boolean isEmpty() {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.execute("SELECT COUNT(id) FROM models");
			ResultSet resSet = stmt.getResultSet();
			if (resSet.next()) {
				return (resSet.getInt(1) == 0);
			}
		} catch (SQLException e) {
			return true;
		} finally {
			if (stmt!=null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	private void create() throws AnkiException {
		try {
			InputStream inputStream = AnkiDb.class
					.getResourceAsStream("empty.sql");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream));
			String line;
			String command = "";
			while (null != (line = reader.readLine())) {

				line = line.trim();

				command += " " + line;

				if (line.endsWith(";")) {
					// System.out.println(command);
					Statement stmt = conn.createStatement();
					stmt.execute(command);
					command = "";
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new AnkiException();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AnkiException();
		} finally {

		}

	}

	/**
	 * @param name
	 * @param inputStream
	 * @return
	 * @throws AnkiException
	 */
	public Long putMedia(String origName, InputStream inputStream)
			throws AnkiException {
		String name = origName;
		if (name.contains("/")) {
			name = name.substring(name.lastIndexOf("/") + 1);
		}
		// String ext = "";
		// if (name.contains(".")) {
		// ext = name.substring(name.lastIndexOf("."));
		// }
		File outputFile = new File(mediaDir + "/" + name);
		if (outputFile.exists()) {
			outputFile.delete();
		}
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(outputFile);

			StreamsUtil.copyStream(inputStream, outputStream);

			PreparedStatement stmt;
			Long id = null;

			stmt = conn
					.prepareStatement("SELECT id FROM media WHERE filename=?");
			stmt.setString(1, name);
			ResultSet resultSet = stmt.executeQuery();
			if (resultSet.next()) {
				id = resultSet.getLong(1);
			}
			stmt.close();

			if (id == null || id == 0) {
				stmt = conn
						.prepareStatement("INSERT INTO media "
								+ "(id, filename, size, created, originalPath, description) VALUES "
								+ "(?, ?, ?, ?, ?, ?)");

				id = getNextId("media");

				stmt.setLong(1, id);
				stmt.setString(2, name);
				stmt.setLong(3, outputFile.getTotalSpace());
				stmt.setLong(4, outputFile.lastModified() / 1000);
				stmt.setString(5, origName);
				stmt.setString(6, name);
				stmt.executeUpdate();
				stmt.close();
			}

			return id;
		} catch (FileNotFoundException e) {
	
			e.printStackTrace();
			throw new AnkiException();
		} catch (IOException e) {
		
			e.printStackTrace();
			throw new AnkiException();
		} catch (SQLException e) {
		
			e.printStackTrace();
			throw new AnkiException();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException ignore) {
				}
			}
		}
	}

	private Long addFact() throws AnkiException {
		try {

			PreparedStatement stmt = conn
					.prepareStatement("INSERT INTO facts "
							+ "(id, modelId, created, modified, tags, spaceUntil, lastCardId) VALUES "
							+ "(?, ?, ?, ?, ?, ?, ?)");

			Long id = getNextId("facts");
			Long time = getTime();

			stmt.setLong(1, id);
			stmt.setLong(2, modelId);
			stmt.setLong(3, time);
			stmt.setLong(4, time);
			stmt.setString(5, "");
			stmt.setLong(6, 0L);
			stmt.setNull(7, java.sql.Types.INTEGER);
			stmt.executeUpdate();
			stmt.close();

			return id;
		} catch (SQLException e) {
		
			e.printStackTrace();
			throw new AnkiException();
		} finally {

		}
	}

	public Long putItemToCard(Item item) throws AnkiException {
		if (item.question==null || item.question.isEmpty()) {
			return null;
		}
		
		try {
			Long factId = null;
			if (null != (factId = itemCardExists(item))) {
				PreparedStatement stmt = conn
						.prepareStatement("UPDATE cards SET "
								+ "question=?, answer=? " + "WHERE id=? ");
				stmt.setString(1, item.question);
				stmt.setString(2, item.answer);
				stmt.setLong(3, item.id);

				stmt.executeUpdate();
			} else {
				PreparedStatement stmt = conn
						.prepareStatement("INSERT INTO cards "
								+ "(id, factId, cardModelId, created, modified, tags, ordinal, "
								+ "question, answer, priority, interval, lastInterval, "
								+ "due, lastDue, factor, lastFactor, firstAnswered, reps, successive, averageTime, reviewTime, "
								+ "youngEase0, youngEase1, youngEase2, youngEase3, youngEase4, "
								+ "matureEase0, matureEase1, matureEase2, matureEase3, matureEase4, "
								+ "yesCount, noCount, spaceUntil, relativeDelay, isDue, type, combinedDue"
								+ ") VALUES (" + "?, ?, ?, ?, ?, ?, ?, "
								+ "?, ?, ?, ?, ?, "
								+ "?, ?, ?, ?, ?, ?, ?, ?, ?, "
								+ "? ,? ,? ,?, ?," + "? ,? ,? ,?, ?,"
								+ "?, ?, ?, ?, ?, ?, ?)");

				Long time = getTime();
				factId = addFact();
				int ordinal = 0;
				int priority = 2;
				float interval = 0;
				float lastInterval = 0;
				float due = 0;
				float lastDue = 0;
				float factor = 2.5f;
				float lastFactor = 2.5f;
				float firstAnswered = 0;
				int successive = 0;
				float averageTime = 0;
				float reviewTime = 0;
				int yesCount = 0;
				int noCount = 0;
				
				if (item.lastRepetition != null) {
					successive = item.repetitions;
					due = item.nextRepetition.getTime()/1000;
					interval = (item.nextRepetition.getTime()-item.lastRepetition.getTime())/1000/24/3600;
					factor = item.aFactor;
					lastFactor = item.aFactor;
					yesCount = item.repetitions;
					noCount = item.lapses;
				}
				if (item.learned && item.type==Item.PRESENTATION) {
					due = (new Date(2100-1900,1,1)).getTime()/1000;
					interval = 365*100;
					yesCount = 1;
					priority = -3; // suspend
				}
				if (item.learned && item.type==Item.ONCE) {
					due = (new Date(2100-1900,1,1)).getTime()/1000;
					interval = 365*100;
					yesCount = 1;
					priority = -3; // suspend
				}
//				if (item.disabled) {
//					priority = -3; // suspend
//				}

				int reps = yesCount + noCount;
				float spaceUntil = due;
				float relativeDelay = due;
				boolean isDue = true;
				int type = 2;
				float combinedDue = due;

				stmt.setLong(1, Long.valueOf(item.id));
				stmt.setLong(2, factId);
				stmt.setLong(3, cardModelId);
				stmt.setLong(4, time);
				stmt.setLong(5, time);
				stmt.setString(6, ""); // tags
				stmt.setInt(7, ordinal);
				stmt.setString(8, item.question);
				stmt.setString(9, item.answer);
				stmt.setInt(10, priority);
				stmt.setFloat(11, interval);
				stmt.setFloat(12, lastInterval);
				stmt.setFloat(13, due);
				stmt.setFloat(14, lastDue);
				stmt.setFloat(15, factor);
				stmt.setFloat(16, lastFactor);
				stmt.setFloat(17, firstAnswered);
				stmt.setInt(18, reps);
				stmt.setInt(19, successive);
				stmt.setFloat(20, averageTime);
				stmt.setFloat(21, reviewTime);

				stmt.setInt(22, noCount);
				stmt.setInt(23, 0);
				stmt.setInt(24, 0);
				stmt.setInt(25, yesCount+noCount);
				stmt.setInt(26, 0);

				stmt.setInt(27, 0);
				stmt.setInt(28, 0);
				stmt.setInt(29, 0);
				stmt.setInt(30, 0);
				stmt.setInt(31, 0);

				stmt.setInt(32, yesCount);
				stmt.setInt(33, noCount);
				stmt.setFloat(34, spaceUntil);
				stmt.setFloat(35, relativeDelay);
				stmt.setBoolean(36, isDue);
				stmt.setInt(37, type);
				stmt.setFloat(38, combinedDue);

				stmt.executeUpdate();
				stmt.close();
				
				stmt = conn.prepareStatement("DELETE FROM cardtags WHERE cardId=?");
				stmt.setLong(1, item.id);
				stmt.executeUpdate();
				stmt.close();

				stmt = conn.prepareStatement("INSERT INTO cardtags "
						+ " (id, cardId, tagId, src) "
						+ " VALUES (?, ?, ?, ?)");
				stmt.setLong(1, getNextId("cardtags"));
				stmt.setLong(2, item.id);
				stmt.setLong(3, 1);
				stmt.setLong(4, 1);
				stmt.executeUpdate();
				stmt.close();

				stmt = conn.prepareStatement("INSERT INTO cardtags "
						+ " (id, cardId, tagId, src) "
						+ " VALUES (?, ?, ?, ?)");
				stmt.setLong(1, getNextId("cardtags"));
				stmt.setLong(2, item.id);
				stmt.setLong(3, 3);
				stmt.setLong(4, 2);
				stmt.executeUpdate();
				stmt.close();
			}
			
			PreparedStatement stmt;
			
			stmt = conn.prepareStatement("DELETE FROM fields WHERE factId=?");
			stmt.setLong(1, factId);
			stmt.executeUpdate();
			
			stmt = conn.prepareStatement("INSERT INTO fields "
					+ " (id, factId, fieldModelId, ordinal, value) "
					+ " VALUES (?, ?, ?, ?, ?)");
			stmt.setLong(1, getNextId("fields"));
			stmt.setLong(2, factId);
			stmt.setLong(3, fieldModelIdQuestion);
			stmt.setInt(4, 0);
			stmt.setString(5, item.question);
			stmt.executeUpdate();
			stmt.close();

			stmt = conn.prepareStatement("INSERT INTO fields "
					+ " (id, factId, fieldModelId, ordinal, value) "
					+ " VALUES (?, ?, ?, ?, ?)");
			stmt.setLong(1, getNextId("fields"));
			stmt.setLong(2, factId);
			stmt.setLong(3, fieldModelIdAnswer);
			stmt.setInt(4, 1);
			stmt.setString(5, item.answer);
			stmt.executeUpdate();
			stmt.close();

			int cardTotal = 0;
			int cardNew = 0;
			{
				stmt = conn.prepareStatement("SELECT COUNT(id) FROM cards");
				ResultSet resultSet = stmt.executeQuery();
				if (resultSet.next()) {
					cardTotal = resultSet.getInt(1);
				}
			}
			{
				stmt = conn.prepareStatement("SELECT COUNT(id) FROM cards WHERE due = 0");
				ResultSet resultSet = stmt.executeQuery();
				if (resultSet.next()) {
					cardNew = resultSet.getInt(1);
				}
			}

			stmt = conn.prepareStatement("UPDATE decks SET "
					+ " cardCount=?, factCount=?, newCount=? ");
			stmt.setInt(1, cardTotal);
			stmt.setInt(2, cardTotal);
			stmt.setInt(3, cardNew);
			stmt.executeUpdate();
			stmt.close();
			
			return Long.valueOf(item.id);
		} catch (SQLException e) {
		
			e.printStackTrace();
			throw new AnkiException();
		} finally {

		}
	}

	/**
	 * @param item
	 * @return
	 */
	private Long itemCardExists(Item item) {
		try {
			PreparedStatement stmt = conn
					.prepareStatement("SELECT factId FROM cards WHERE id=?");
			stmt.setLong(1, item.id);
			stmt.execute();
			ResultSet resSet = stmt.getResultSet();
			if (resSet.next()) {
				return resSet.getLong(1);
			}
		} catch (SQLException e) {
			return null;
		}
		return null;
	}

	/**
	 * @return
	 */
	private long getTime() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * @param table
	 * @return
	 * @throws AnkiException
	 */
	private long getNextId(String table) throws AnkiException {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.execute("SELECT max(id) FROM " + table);
			ResultSet resSet = stmt.getResultSet();
			Long id = resSet.getLong(1);
			if (id == null)
				id = 0L;
			return id + 1;
		} catch (SQLException e) {
		
			e.printStackTrace();
			throw new AnkiException();
		} finally {
			if (stmt!=null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
