package com.gitgis.sm.smpak;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;


public class EntryInputStream extends InputStream {

	private int fileSize;
	int pos = 0;
	private InputStream inputStream;

	public EntryInputStream(InputStream inputStream, int fileSize) {
		this.fileSize = fileSize;
		this.inputStream = inputStream;
	}

	@Override
	public int read() throws IOException {
		if (pos+1>fileSize) return -1;
		int retVal = inputStream.read();
		pos+=retVal;
		return retVal;
	}
	
	public int read(byte[] buf) throws IOException {
		return read(buf, 0, buf.length);
	}
	
	public int read(byte[] buf, int off, int len) throws IOException {
		if (pos+off>fileSize) return -1;
		if (pos+off+len>fileSize) len = fileSize-pos-off;
		int retVal = inputStream.read(buf, off, len);
		pos+=retVal;
		return retVal;
	}
	
	public long skip(long len) throws IOException {
		if (len+pos>fileSize) throw new EOFException();
		long retVal = inputStream.skip(len);
		pos+=retVal;
		return retVal;
	}
	
	public int available() throws IOException  {
		int retVal = inputStream.available();
		return retVal;
	}
	
	public void close() throws IOException {
		inputStream.close();
	}
	
	public void mark(int readlimit) {
		inputStream.mark(readlimit);
	}

	public void reset() throws IOException {
		inputStream.reset();
	}

}
