/**
 * 
 */
package com.gitgis.sm;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.gitgis.sm.smpak.Exercise;

/**
 * @author gg
 * 
 */
public class ExerciseConverter {

	private final InputStream inputStream;
	private final Exercise exercise;

	private final StreamSource questionSource = new StreamSource(ExerciseConverter.class.getResourceAsStream("question.xslt"));
	private final StreamSource answerSource = new StreamSource(ExerciseConverter.class.getResourceAsStream("answer.xslt"));

	private final Map<String, Object> parameters = new HashMap<String, Object>();

	/**
	 * @param itemId 
	 * @param inputStream
	 */
	public ExerciseConverter(String itemId, InputStream inputStream) {
		this.inputStream = inputStream;
		this.exercise = new Exercise(itemId);
		parameters.put("exercise", exercise);
		parameters.put("itemId", exercise.getItemId());
	}

	/**
	 * @return
	 * @throws IOException
	 */
	public Exercise getExercise() throws IOException {
		parse();
		return exercise;
	}

	/**
	 * @throws IOException
	 * 
	 */
	private void parse() throws IOException {
		ByteArrayOutputStream outputPipe1 = new ByteArrayOutputStream();
		ByteArrayOutputStream outputPipe2 = new ByteArrayOutputStream();
		
		try {
			byte[] buf = new byte[0x1000];
			int len;
			while ((len = inputStream.read(buf))>0) {
				outputPipe1.write(buf, 0, len);
				outputPipe2.write(buf, 0, len);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				outputPipe1.close();
				outputPipe2.close();
			} catch (IOException e) {
				e.printStackTrace();
//						logger.error(e.getMessage(), e);
			}
		}

		ByteArrayInputStream inputPipe1 = new ByteArrayInputStream(outputPipe1.toByteArray());
		ByteArrayInputStream inputPipe2 = new ByteArrayInputStream(outputPipe2.toByteArray());

		parseQuestion(inputPipe1);
		parseAnswer(inputPipe2);
	}

	private void parseQuestion(InputStream inputStream) throws IOException {
		InputStream is2 = XmlUtils.xslt(inputStream, questionSource, parameters);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is2));

		String question = "";
		String line;
		while ((line = reader.readLine()) != null) {
			question+=line;
		}
		exercise.setQuestion(question);
	}

	private void parseAnswer(InputStream inputStream) throws IOException {
		InputStream is2 = XmlUtils.xslt(inputStream, answerSource, parameters);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is2));

		String answer = "";
		String line;
		while ((line = reader.readLine()) != null) {
			answer+=line;
		}
		exercise.setAnswer(answer);
	}
}
