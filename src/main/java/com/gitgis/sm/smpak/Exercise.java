package com.gitgis.sm.smpak;

/**
 * @author gg
 * 
 */
public class Exercise {

	private String question = "";
	private String answer = "";
//	private boolean answerAudio;
//	private boolean questionAudio;
	private String itemId;

	public Exercise(String itemId) {
		this.itemId = itemId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String toString() {
		String retVal = "";

		retVal+=question;
		retVal+="\t";
		retVal+=answer;
		
		return retVal;
	}

	public String getItemId() {
		return itemId;
	}

}
