package com.gitgis.sm.smpak;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class SmPakParser implements Parser {

	protected File fileName;
	protected RandomAccessFile randomAccessFile;

	private int entrPos;
	private int namePos;

	private HashMap<String, FileEntry> cachedEntries = new HashMap<String, FileEntry>();

	private SmPakParser(File file) throws SmPakException {
		this.fileName = file;
		open();
	}
	
	public static SmPakParser getInstance(File file) throws SmPakException {
		if (file.exists()) {
			return new SmPakParser(file);
		}
		return null;
	}

	public FileEntry getFileEntry(String fileName) throws IOException,
			SmPakException {
		FileEntry retVal = cachedEntries.get(fileName);
		if (retVal == null) {
			cachedEntries.put(fileName, null);
			scanEntrChnk();
			retVal = cachedEntries.get(fileName);
		}
		return retVal;
	}

	private void open() throws SmPakException {

		try {
			randomAccessFile = new RandomAccessFile(fileName, "r");
			// dataInputStream = new DataInputStream(fileInputStream);

			// int i1;
			// fileInputStream.skip(30);
			// i1 = find("DataChnk".getBytes());
			// System.out.format("%08X %d", i1 ,i1);
			// System.out.println();

			readHeader();

			// 427+28=455 // 01C7
			// fileInputStream.skip(8);
			// i1 = find("DataChnk".getBytes());
			// System.out.format("%08X %d", i1 ,i1);
			// System.out.println();

			// DataChnk
			// byte[] buf = readBuf(8);
			// System.out.println(new String(buf));

			// 4877

			cachedEntries.put("/course.xml", null);
			// cachedEntries.put("/glossary.xml", null);

			scanEntrChnk();

		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	private void readHeader() throws IOException, SmPakException {
		// -SMArch-
		String headerTag = new String(readBuf(8), "ISO-8859-1");

		if (!headerTag.equals("-SMArch-")) {
			Logger.getLogger(SmPakParser.class.getName()).info(
					"TESTx2 " + headerTag);
			throw new SmPakException("Invalid SMArch");
		}

		// System.out.println(new String(header));
		// TODO StreamCorruptedException

		int i1 = readInt(); // TODO
		entrPos = readInt();
		namePos = readInt();

		// fileInputStream.seek(namePos-20-4);
		randomAccessFile.seek(namePos - 8);
		String nameHeaderTag = new String(readBuf(8), "ISO-8859-1");

		if (!nameHeaderTag.equals("NameChnk")) {
			throw new SmPakException("Invalid NameChnk");
		}

		int skipped = 4;
		readBuf(skipped);
		
		while (true) { // HACK
			byte[] buf = readBuf(1);
			if (buf[0]>='a' && buf[0]<='z') {
				break;
			}
			skipped++;
		}

		namePos = namePos + skipped;
	}

	private void scanEntrChnk() throws IOException, SmPakException {
		// long curPos = entrPos-8;

		randomAccessFile.seek(entrPos - 8);

		String headerTag = new String(readBuf(8), "ISO-8859-1");

		if (!headerTag.equals("EntrChnk")) {
//			Logger.getLogger(SmPakParser.class.getName()).info(
//					"TEST2 " + headerTag);
			throw new SmPakException("Invalid EntrChnk");
		}

		int filesCnt = readInt();

//		System.out.println(filesCnt);

//		int pos = 28;
		for (int i = 0; i < filesCnt; i++) {
			FileEntry fileEntry = new FileEntry();

			int currentNamePos = this.namePos + readInt(); // 4
			int nameSize = readShort(); // 2
			fileEntry.compression = (short) readShort(); // 2
			fileEntry.filePos = readInt(); // 4
			fileEntry.fileSize = readInt(); // 4

			// System.out.println(fileEntry.compression);
			// curPos+=16;

			// fileInputStream.reset();
			// fileInputStream.skip(-curPos);
			long curPos = randomAccessFile.getFilePointer();
			randomAccessFile.seek(currentNamePos);
			fileEntry.name = "/" + new String(readBuf(nameSize), "ISO-8859-1");
			// fileInputStream.skip(-currentNamePos-nameSize);
			// fileInputStream.reset();
			randomAccessFile.seek(curPos);

			cachedEntries.put(fileEntry.name, fileEntry);
		}

		// fileInputStream.reset();
		// fileInputStream.skip(-4-filesCnt*16-entrPos);
	}

	// private List<FileEntry> readNameChnk(List<FileEntry> list) throws
	// IOException {
	// // 4E 61 6D 65 43 68 6E 6B 58 04 00 00 D8 08
	// // 4E 61 6D 65 43 68 6E 6B CD 02 00 00 CD 05
	// // 4E 61 6D 65 43 68 6E 6B FC FA 01 00 FC F5 07
	// // N a m e c h n k chunksize TODO
	//
	// fileInputStream.skip(namePos-8);
	// int chunkSize = readInt();
	// int skipped = 0;
	//
	// while(((readBuf(1)[0])&0xFF)>=0xF) skipped++; // HACK
	// skipped++;
	//
	// int cnt=0;
	// for (FileEntry fileEntry: list) {
	// fileEntry.name = "/"+new String(readBuf(fileEntry.nameSize));
	// cnt+=fileEntry.nameSize;
	// }
	//
	// fileInputStream.skip(8-namePos-cnt-4-skipped);
	// return list;
	// }

	protected byte[] readBuf(int length) throws IOException {
		byte[] buf = new byte[length];
		randomAccessFile.read(buf, 0, buf.length);
		return buf;

	}

	protected int readInt() throws IOException {
		byte[] buf = new byte[4];
		randomAccessFile.read(buf, 0, buf.length);
		return ((buf[3] & 0xff) << 24) + ((buf[2] & 0xff) << 16)
				+ ((buf[1] & 0xff) << 8) + ((buf[0] & 0xff) << 0);
	}

	protected int readInt2() throws IOException {
		byte[] buf = new byte[4];
		randomAccessFile.read(buf, 0, buf.length);
		return ((buf[0] & 0xff) << 24) + ((buf[1] & 0xff) << 16)
				+ ((buf[2] & 0xff) << 8) + ((buf[3] & 0xff) << 0);
	}

	protected int readShort2() throws IOException {
		byte[] buf = new byte[2];
		randomAccessFile.read(buf, 0, buf.length);
		return ((buf[1] & 0xff) << 0) + ((buf[0] & 0xff) << 8);
	}

	protected int readShort() throws IOException {
		byte[] buf = new byte[2];
		randomAccessFile.read(buf, 0, buf.length);
		return ((buf[0] & 0xff) << 0) + ((buf[1] & 0xff) << 8);
	}

	public InputStream getInputStream(String entryName) throws IOException, SmPakException {
		FileEntry fileEntry = getFileEntry(entryName);
		if (fileEntry == null) {
			return null;
		}
		return getInputStream(fileEntry);
	}

	private InputStream getInputStream(FileEntry fileEntry) throws IOException {
		InputStream retVal = new FileInputStream(fileName);
		retVal.skip(fileEntry.filePos);

		retVal = new EntryInputStream(retVal, fileEntry.fileSize);

		if (fileEntry.compression == FileEntry.INFLATE) {
			Inflater decompresser = new Inflater(true);
			retVal = new InflaterInputStream(retVal, decompresser,
					fileEntry.fileSize);
		}
		return retVal;

	}

	public HashMap<String, String> getGlossary() throws SmPakException {
		HashMap<String, String> glossary = null;
		try {
			glossary = new Glossary(
					getInputStream(getFileEntry("/glossary/glossary.xml")));
		} catch (IOException e) {

			e.printStackTrace();
		}
		return glossary;
	}

	/**
	 * @return
	 * @throws SmPakException
	 * @throws IOException
	 */
	public Collection<String> getFileEntryNames() throws IOException,
			SmPakException {
		scanEntrChnk();
		return cachedEntries.keySet();
	}

}
