/**
 * 
 */
package com.gitgis.sm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.testng.annotations.Test;

import com.gitgis.sm.smpak.SmParser;

/**
 * @author gg
 * 
 */
public class TestUnpakAll {
	@Test(enabled = false)
	public void testUnpak() {
		try {
			SmParser parser = new SmParser( new File("/var/www/testankirus/Rosyjski NP1 demo" ), "course");
	
//			SmParser parser = new SmParser(
//			"/var/www/testanki/Niemiecki Kein Problem 1/course");
//			SmParser parser = new SmParser("/var/www/testankirus/EEAdvanced/course");

			int cnt = 0;
			
//			Course course = parser.getCourse();
//			System.out.println(course);
			
			String outputDir = "/var/www/testankirus/unpakaa";
			
			for (String fileName: parser.getFileEntryNames()) {
				System.out.println(fileName);
				
				try {
					InputStream is = parser.getInputStream(fileName);

					File file = new File(outputDir+"/" + fileName);
					file.getParentFile().mkdirs();
					FileOutputStream fo = new FileOutputStream(file);
					byte[] buf = new byte[100];
					int len;
					while ((len = is.read(buf, 0, buf.length)) > 0) {
						fo.write(buf, 0, len);
					}
					fo.close();

				} catch (IllegalArgumentException ignore) {
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

//				if (cnt > 10)
//					break;
				cnt++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
