package com.gitgis.sm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.gitgis.sm.smpak.Course;
import com.gitgis.sm.smpak.Element;
import com.gitgis.sm.smpak.FileEntry;
import com.gitgis.sm.smpak.ItemParser;
import com.gitgis.sm.smpak.SmPakParser;
import com.gitgis.sm.smpak.SmParException;

public class App {

	public static void main(String args[]) {

		try {
			SmPakParser parser = new SmPakParser(
					"/home/gg/var/supermemo/Niemiecki Kein Problem 1/course.smdif");

//			printCourse(parser);
			// course.getItem(Elements)
			// parser.get();

			// HashMap<String, String> glossary = parser.getGlossary();
			// for (String key: glossary.keySet()) {
			// System.out.println(key+" = "+glossary.get(key));
			// }

			// XmlParser parser = new XmlParser();

			// System.exit(1);
			unpakAll(parser);
		} catch (SmParException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void printCourse(SmPakParser parser) throws SmParException {
		Course course = parser.getCourse();
		System.out.println(course);
		for (Element e : course.getElements()) {
			System.out.println("======" + e.getId());
//				System.out.println(parser.getItem(e));
			break;
		}
	}

	private static void unpakAll(SmPakParser parser) throws IOException,
			SmParException {
		int cnt = 0;
		
		Course course = parser.getCourse();
		System.out.println(course);
		
		String outputDir = "/tmp/unpak2";
		
//		for (Element element: course.getElements()) {
		for (String fileName: parser.getFileEntryNames()) {
//		for (FileEntry fileEntry: parser.getFileEntryList()) {

			try {
				System.out.println(fileName);
//				String fileName = String.format("/item%05d.xml", element.getId());
				
				FileEntry fileEntry = parser.getFileEntry(fileName);
				InputStream is = parser.getInputStream(fileEntry);

				File file = new File(outputDir+"/" + fileEntry.name);
				file.getParentFile().mkdirs();
				FileOutputStream fo = new FileOutputStream(file);
				byte[] buf = new byte[100];
				int len;
				while ((len = is.read(buf, 0, buf.length)) > 0) {
					fo.write(buf, 0, len);
				}
				fo.close();

				// byte[] b = new byte[100];
				// is.read(b,0,100);
				// System.out.println(new String(b));

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

//			if (cnt > 10)
//				break;
			cnt++;
		}

	}

}
