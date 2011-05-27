package com.gitgis.sm.smpak;

public class CourseExercise {

	private int id;
	String name;

	public CourseExercise(int id, String name) {
		this.setId(id);
		this.name = name;
	}

	public String toString() {
		String retVal = "";
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
