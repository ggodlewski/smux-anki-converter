package com.gitgis.sm.smpak;

public class FileEntry {
	public String name;
	public int filePos;
	public int fileSize;
	public short compression = 0;
	
	final static public short INFLATE = 1;

	public String toString() {
		return name+"["+fileSize+"]";
	}

}
