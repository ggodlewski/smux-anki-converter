/**
 * Copyright (c) GitGis http://gitgis.com
 * All rights reserved
 */
package com.gitgis.sm;

import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author gg@gitgis.com
 *
 */
public class StreamsUtil {

	public static void copyStream(InputStream inputStream,
			OutputStream outputStream) throws IOException {
		byte[] buf = new byte[0x1000];
		int len;
		while (0 < (len = inputStream.read(buf))) {
			outputStream.write(buf, 0, len);
		}
	}

}
