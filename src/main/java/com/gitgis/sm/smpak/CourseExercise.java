package com.gitgis.sm.smpak;

public class CourseExercise {

	private int id;
	String name;
	private int elementLevel;

	public CourseExercise(int id, String name, int elementLevel) {
		this.setId(id);
		this.name = name;
		this.elementLevel = elementLevel;
	}

	public String toString() {
		String retVal = "";
		for (int i=0; i<elementLevel; i++) retVal+=" ";
		retVal+=name;
		
		return retVal;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	
}
