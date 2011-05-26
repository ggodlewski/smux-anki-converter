/**
 * 
 */
package com.gitgis.sm.anki;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author gg
 * 
 */
public class AnkiDb {

	private Connection conn;
	private final File smDir;
	private final File mediaDir;

	Long seq = 1L;
	Long modelId = -4490606492491703409L;

	/**
	 * @param string
	 * @throws AnkiException
	 */
	public AnkiDb(File smDir) throws AnkiException {
		this.smDir = smDir;
		try {
			System.setProperty("org.sqlite.JDBC", "true");
			Class.forName("org.sqlite.JDBC");

			File file2 = new File(smDir.getAbsoluteFile() + ".anki");
			mediaDir = new File(smDir.getAbsoluteFile() + ".media");

			if (file2.exists()) {
				file2.delete();
			}
			if (!mediaDir.exists()) {
				mediaDir.mkdirs();
			}

			conn = DriverManager.getConnection("jdbc:sqlite:"
					+ file2.getAbsolutePath());

			create();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new AnkiException();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AnkiException();
		} finally {

		}
	}

	private void create() throws AnkiException {
		try {
			Statement statement = conn.createStatement();
			// ResultSet result =
			// statement.executeQuery("SELECT * FROM items WHERE PageNum = 6");
			// ResultSetMetaData metaData = result.getMetaData();
			// while (result.next()) {
			// for (int colNo=0; colNo < metaData.getColumnCount(); colNo++) {
			// String columnName = metaData.getColumnName(colNo+1);
			// System.out.println(columnName+" = "+result.getString(columnName));
			// }
			// break;
			// }

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
	public Long addMedia(String origName, InputStream inputStream)
			throws AnkiException {
		String name = origName;
		if (name.contains("/")) {
			name = name.substring(name.lastIndexOf("/") + 1);
		}
		String ext = "";
		if (name.contains(".")) {
			ext = name.substring(name.lastIndexOf("."));
		}
		File outputFile = new File(mediaDir + "/" + name);
		if (outputFile.exists()) {
			outputFile.delete();
		}
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(outputFile);

			byte[] buf = new byte[0x1000];
			int len;
			while (0 < (len = inputStream.read(buf))) {
				outputStream.write(buf, 0, len);
			}

			// Statement stmt = conn.createStatement();
			PreparedStatement stmt = conn
					.prepareStatement("INSERT INTO media "
							+ "(id, filename, size, created, originalPath, description) VALUES "
							+ "(?, ?, ?, ?, ?, ?)");

			Long id = getNextId();

			stmt.setLong(1, id);
			stmt.setString(2, name);
			stmt.setLong(3, outputFile.getTotalSpace());
			stmt.setLong(4, outputFile.lastModified() / 1000);
			stmt.setString(5, origName);
			stmt.setString(6, name);
			stmt.executeUpdate();

			return id;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AnkiException();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AnkiException();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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

	public Long addFact() throws AnkiException {
		try {

			PreparedStatement stmt = conn
					.prepareStatement("INSERT INTO facts "
							+ "(id, modelId, created, modified, tags, spaceUntil, lastCardId) VALUES "
							+ "(?, ?, ?, ?, ?, ?, ?, ?)");

			Long id = getNextId();
			Long time = getTime();

			stmt.setLong(1, id);
			stmt.setLong(2, modelId);
			stmt.setLong(3, time);
			stmt.setLong(4, time);
			stmt.setString(5, "");
			stmt.setLong(6, 0L);
			stmt.setNull(7, java.sql.Types.INTEGER);
			stmt.executeUpdate();

			return id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AnkiException();
		} finally {

		}
	}

	public Long addCard() throws AnkiException {
		try {

			PreparedStatement stmt = conn
					.prepareStatement("INSERT INTO cards "
							+ "(id, factId, cardModelId, created, modified, tags, ordinal, "
							+ "question, answer, priority, interval, lastInterval, "
							+ "due, lastDue, factor, lastFactor, firstAnswered, reps, successive, averageTime, reviewTime, "
							+ "youngEase0, youngEase1, youngEase2, youngEase3, youngEase4, "
							+ "matureEase0, matureEase1, matureEase2, matureEase3, matureEase4, "
							+ "yesCount, noCount, spaceUntil, relativeDelay, isDue, type, combinedDue"
							+ ") VALUES (" + "?, ?, ?, ?, ?, ?, ?, "
							+ "?, ?, ?, ?, ?, " + "?, ?, ?, ?, ?, ?, ?, ?, ?, "
							+ "? ,? ,? ,?, ?," + "? ,? ,? ,?, ?,"
							+ "?, ?, ?, ?, ?, ?, ?)");

			Long id = getNextId();
			Long time = getTime();
			Long factId = addFact();
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
			int reps = yesCount + noCount;
			float spaceUntil = due;
			float relativeDelay = due;
			boolean isDue = true;
			int type = 2;
			float combinedDue = due;

			stmt.setLong(1, id);
			stmt.setLong(2, factId);
			stmt.setLong(3, modelId);
			stmt.setLong(4, time);
			stmt.setLong(5, time);
			stmt.setString(6, ""); // tags
			stmt.setInt(7, ordinal);
			stmt.setString(8, "question");
			stmt.setString(9, "answer");
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

			stmt.setInt(22, 0);
			stmt.setInt(23, 0);
			stmt.setInt(24, 0);
			stmt.setInt(25, 0);
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

			return id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AnkiException();
		} finally {

		}
	}

	/**
	 * @return
	 */
	private long getTime() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * @return
	 */
	private long getNextId() {
		return seq++;
	}

}
