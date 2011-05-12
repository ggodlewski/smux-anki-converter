/**
 * 
 */
package com.gitgis.sm.smpak;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author gg
 * 
 */
public class ItemParser {

	private final SmPakParser smPakParser;

	public ItemParser(SmPakParser smPakParser) {
		this.smPakParser = smPakParser;
	}

	public Item parseItem(InputStream inputStream) throws IOException {
		Item item = new Item();

		byte[] buf = new byte[100];
		int len;
		while ((len = inputStream.read(buf, 0, buf.length)) > 0) {
			System.out.print(new String(buf));
//			fo.write(buf, 0, len);
		}
//		fo.close();

		// SAXParserFactory factory = SAXParserFactory.newInstance();
		// try {
		// SAXParser saxParser = factory.newSAXParser();
		// DefaultHandler dh = new ItemHandler(item);
		// saxParser.parse(inputStream, dh);
		// } catch (ParserConfigurationException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (SAXException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		return item;
	}
}
