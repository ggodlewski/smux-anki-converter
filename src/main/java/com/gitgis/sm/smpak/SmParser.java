/**
 * 
 */
package com.gitgis.sm.smpak;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.gitgis.sm.course.Course;

/**
 * @author gg
 *
 */
public class SmParser implements Parser {

	private SmPakParser diffParser;
	private SmPakParser baseParser;

	/**
	 * @param string
	 * @throws SmPakException 
	 */
	public SmParser(String fileName) throws SmPakException {
		baseParser = SmPakParser.getInstance(new File(fileName+".smpak"));
		diffParser = SmPakParser.getInstance(new File(fileName+".smdif"));
	}


	public Course getCourse() throws SmPakException {
		Course course = null;
		try {
			course = new Course(this, getInputStream("/course.xml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return course;
	}

	/**
	 * @return
	 * @throws SmPakException 
	 * @throws IOException 
	 */
	public Collection<String> getFileEntryNames() throws IOException, SmPakException {
		Set<String> retVal = new HashSet<String>();
		
		retVal.addAll(baseParser.getFileEntryNames());
		if (diffParser != null) {
			retVal.addAll(diffParser.getFileEntryNames());
		}
		
		return retVal;
	}


	/* (non-Javadoc)
	 * @see com.gitgis.sm.smpak.Parser#getInputStream(java.lang.String)
	 */
	@Override
	public InputStream getInputStream(String entryName) throws IOException,
			SmPakException {
		InputStream inputStream = null;
		if (diffParser != null) {
			inputStream = diffParser.getInputStream(entryName);
		}
		if (inputStream == null) {
			inputStream = baseParser.getInputStream(entryName);
		}
		return inputStream;
	}
	

}
