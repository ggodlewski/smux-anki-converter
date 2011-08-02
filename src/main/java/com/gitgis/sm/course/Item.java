/**
 * 
 */
package com.gitgis.sm.course;

import java.util.Date;

/**
 * @author gg
 *
 */
public class Item {

	public static final int PRESENTATION = 5;
	public static final int EXERCISE = 0;
	public static final int ONCE = 1;
	public int id;
	public String name;
	public int type = EXERCISE;
	public Date lastRepetition;
	public Date nextRepetition;
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
	public boolean learned;
	public int lapses;

	/**
	 * @param itemId
	 */
	public Item(int itemId) {
		this.id = itemId;
	}

	public String toString() {
		return "[id="+id+", name="+name+"]\t"+question+"\t"+answer;
	}

	/**
	 * @return
	 */
	public String getEntryName() {
		return "/item"+String.format("%05d", id)+".xml";
	}
}
