package com.gitgis.sm.smpak;


/**
 * @author gg
 *
 */
public class Item {

	private String fileName;

	public Item() {
	}

	/**
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return fileName;
	}


}
