package com.gitgis.sm;

import java.io.*;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.transform.*;
import javax.xml.transform.stream.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

public class XmlUtils {

	private final static Logger logger = LoggerFactory.getLogger(XmlUtils.class.getCanonicalName());

	static public InputStream xslt(final InputStream inputStream, Source source, Map<String, Object> parameters) {

		try {
			byte[] bom = new byte[3];
			inputStream.read(bom);
			
			TransformerFactory tFactory = TransformerFactory.newInstance();
			final Transformer transformer = tFactory.newTransformer(source);

			for (Entry<String, Object> entry: parameters.entrySet()) {
				transformer.setParameter(entry.getKey(), entry.getValue());
			}
			
			final PipedOutputStream outputPipe = new PipedOutputStream();
			final PipedInputStream inputPipe = new PipedInputStream(outputPipe);

			Thread reader = new Thread() {
				public void run() {
					try {
						try {
							transformer.transform(new StreamSource(new InputStreamReader(inputStream, "UTF-8")),
									new StreamResult(new OutputStreamWriter(outputPipe, "UTF-8")));
						} catch (UnsupportedEncodingException impossible) {
						}
					} catch (TransformerException e) {
						logger.error(e.getMessage(), e);
					} finally {
						try {
							outputPipe.close();
						} catch (IOException e) {
							logger.error(e.getMessage(), e);
						}
					}
				}
			};
			reader.start();

			return inputPipe;
		} catch (TransformerConfigurationException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {

		}
		return null;
	}

}
