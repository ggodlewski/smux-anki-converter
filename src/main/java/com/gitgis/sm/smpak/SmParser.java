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
import java.io.FileInputStream;

import com.gitgis.sm.course.Course;

/**
 * @author gg
 *
 */
public class SmParser implements Parser {

	private SmPakParser diffParser;
	private SmPakParser baseParser;
	private File courseDir;
	
	public SmParser(File dir, String file ) throws SmPakException
	{
		courseDir = dir;
		baseParser = SmPakParser.getInstance(new File(courseDir, file + ".smpak"));
		diffParser = SmPakParser.getInstance(new File(courseDir, file + ".smdif"));
	}


	public Course getCourse() throws SmPakException {
		Course course = null;
		try {
			course = new Course(this, getInputStream("/course.xml"));
		} catch (IOException e) {

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
		File overrideDir = new File(courseDir, "override");
		if( overrideDir.exists() && overrideDir.isDirectory() )	{
			//
			// get the stream from the override dir rather than from within the smpak file
			//
			File courseXml = new File(overrideDir, entryName );
			if( courseXml.isFile() ){
				FileInputStream courseStream = new FileInputStream( courseXml );
				inputStream = courseStream;
			}
		}
		if( inputStream == null ){
			//
			// failed to read from override dir - fallback to reading from SMPAK file
			//
			if (diffParser != null){
				inputStream = diffParser.getInputStream(entryName);
			}
			if (inputStream == null){
				inputStream = baseParser.getInputStream(entryName);
			}
		}
		return inputStream;
	}
}
