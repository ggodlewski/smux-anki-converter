package com.gitgis.sm.smpak;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ExerciseHandler extends DefaultHandler {

	private Exercise exercise;
	private int level = 0;
	private Map<Integer, String> rawXml = new HashMap<Integer, String>();
	private Map<Integer, String> rawInner = new HashMap<Integer, String>();
	private Map<Integer, String> question = new HashMap<Integer, String>();
	private Map<Integer, String> answer = new HashMap<Integer, String>();

	public ExerciseHandler(Exercise exercise) {
		this.exercise = exercise;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		{
			String buf = rawInner.get(level - 1);
			buf += new String(ch, start, length);
			rawInner.put(level - 1, buf);
		}

		{
			String buf = rawXml.get(level - 1);
			buf += new String(ch, start, length);
			rawXml.put(level - 1, buf);
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		String rawTag = "<" + qName;
		for (int i = 0, len = attributes.getLength(); i < len; i++) {
			rawTag += " " + attributes.getQName(i) + "=\""
					+ attributes.getValue(i) + "\"";
		}
		rawTag += ">";

		rawXml.put(level, rawTag);
		rawInner.put(level, "");
		question.put(level, "");
		answer.put(level, "");
		
		if (qName.equals("spellpad")) {
			question.put(level, "___");
			answer.put(level, "<strong>"+attributes.getValue("correct")+"</strong>");
		}
		
		level++;
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		level--;
		String txt = rawInner.get(level);
		String xml = rawXml.get(level);
		if (txt.isEmpty()) {
			xml = xml.substring(0, xml.length() - 1) + "/>";
		} else {
			xml += "</" + qName + ">";
		}

		if (qName.equals("question-title")) {
		} else if (qName.equals("question")) {
			String questionTxt = question.get(level);
			exercise.setQuestion(questionTxt);
//			exercise.setQuestion(ExerciseUtils.convertRawtoQuestion(txt));
			if (exercise.getAnswer().isEmpty()) {
				String answerTxt = answer.get(level);
				exercise.setAnswer(answerTxt);
			}
			System.out.println("TXT " + txt);
			System.out.println("XML " + xml);
		} else if (qName.equals("question-audio")) {
//			exercise.setQuestionAudio(true);
		} else if (qName.equals("answer")) {
		} else if (qName.equals("answer-audio")) {
//			exercise.setAnswerAudio(true);
		} else {

		}

		if (level >= 1) {
			if (qName.equals("spellpad")) {
				xml = answer.get(level);
			}
			
			String inner = rawInner.get(level - 1);
			inner += xml;
			rawInner.put(level - 1, inner);

			String outer = rawXml.get(level - 1);
			outer += xml;
			rawXml.put(level - 1, outer);
		}

	}

}
