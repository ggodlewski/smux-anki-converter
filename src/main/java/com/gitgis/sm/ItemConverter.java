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
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.stream.StreamSource;

import com.gitgis.sm.course.Course;
import com.gitgis.sm.course.Item;

/**
 * @author gg
 * 
 */
public class ItemConverter {

	private final InputStream inputStream;
	private final Item exercise;

	private final StreamSource questionSource = new StreamSource(ItemConverter.class.getResourceAsStream("question.xslt"));
	private final StreamSource answerSource = new StreamSource(ItemConverter.class.getResourceAsStream("answer.xslt"));

	private final Map<String, Object> parameters = new HashMap<String, Object>();
	private final Course course;

	/**
	 * @param exercise 
	 * @param inputStream
	 */
	public ItemConverter(Course course, Item exercise, InputStream inputStream) {
		this.course = course;
		this.inputStream = inputStream;
		this.exercise = exercise;
		parameters.put("exercise", exercise);
		parameters.put("itemId", String.format("%05d", exercise.id));
	}

	/**
	 * @return
	 * @throws IOException
	 */
	public Item getExercise() throws IOException {
		parse();
		
		if (exercise.type == Item.PRESENTATION) {
			if (course.languageOfInstruction.equals("pl")) {
				exercise.answer = "To jest karta lekcji przeniesiona z kursu SuperMemo UX. Po nauczeniu usuń ją z powtórek (Edytuj -> Zawieś karte).";
			} else {
				exercise.answer = "This card is a lesson converted from SuperMemo UX course. After learning remove it from repetitions (Edit -> Suspend card).";
			}
		}
		if (exercise.type == Item.ONCE) {
			if (course.languageOfInstruction.equals("pl")) {
				exercise.answer += "<br/>To jest jednorazowe ćwiczenie z kursu SuperMemo UX. Usuń je z powtórek ręcznie (Edytuj -> Zawieś karte).";
			} else {
				exercise.answer += "<br/>This one time exercise converted from SuperMemo UX course. Remove it from repetitions manualy (Edit -> Suspend card).";
			}
		}
		if (exercise.question == null || exercise.question.isEmpty()) {
			exercise.disabled = true;
		} else {
			exercise.question = exercise.question.replaceAll("[ \t]+", " ");
		}
		if (exercise.answer != null) {
			exercise.answer = exercise.answer.replaceAll("[ \t]+", " ");
		}
				
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
		BufferedReader reader = new BufferedReader(new InputStreamReader(is2, "UTF-8"));

		String question = "";
		String line;
		while ((line = reader.readLine()) != null) {
			question+=line;
		}
		exercise.question = (question);
	}

	private void parseAnswer(InputStream inputStream) throws IOException {
		InputStream is2 = XmlUtils.xslt(inputStream, answerSource, parameters);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is2, "UTF-8"));

		String answer = "";
		String line;
		while ((line = reader.readLine()) != null) {
			answer+=line;
		}
		exercise.answer = (answer);
	}
}
