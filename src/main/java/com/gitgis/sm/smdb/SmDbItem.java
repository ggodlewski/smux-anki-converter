/**
 * 
 */
package com.gitgis.sm.smdb;

/**
 * @author gg
 *
 */
public class SmDbItem {

	public static final int LESSON = 5;
	public static final int EXERCISE = 0;
	public int id;
	public String name;
	public int type = EXERCISE;
	public int lastRepetition;
	public int lextRepetition;
	public float aFactor;
	public float estimatedFI;
	public float expectedFI;
	public int firstGrade;
	public int newInterval;
	public float normalizedGrade;
	public int repetitions;
	public int repetitionsCategory;
	public float uFactor;
	public int usedInterval;
	public int origNewInterval;
	public String question;
	public String answer;
	public boolean disabled = false;
	public boolean dbdata;

	/**
	 * @param itemId
	 */
	public SmDbItem(int itemId) {
		this.id = itemId;
	}

	public String toString() {
		return "[id="+id+", name="+name+"]";
	}
}
