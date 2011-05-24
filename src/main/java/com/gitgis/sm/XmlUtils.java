package com.gitgis.sm;

import java.io.*;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.transform.*;
import javax.xml.transform.stream.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlUtils {

	private final static Logger logger = LoggerFactory.getLogger(XmlUtils.class.getCanonicalName());

	static public InputStream xslt(final InputStream inputStream, Source source, Map<String, Object> parameters) {

		try {
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
						transformer.transform(new StreamSource(inputStream),
								new StreamResult(outputPipe));
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
