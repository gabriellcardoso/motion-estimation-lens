package br.edu.ufpel.inf.utils;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class YUVReaderTest {
	
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	

	@Before
	public void setUpStreams() {
	    System.setErr(new PrintStream(errContent));
	}

	@After
	public void cleanUpStreams() {
	    System.setErr(null);
	}

	@Test
	public void testOpenInputStreamWithNonExistentFile() {
		YUVReader reader = new YUVReader(1920, 1080, 1);
		reader.openInputStream("video.yuv");
		assertEquals("video.yuv (No such file or directory)\n", errContent.toString());
	}

	@Test
	public void testOpenInputStreamWithExistentFile() throws IOException {
		File video = folder.newFile("video.yuv");
		YUVReader reader = new YUVReader(1920, 1080, 1);
		reader.openInputStream(video.getCanonicalPath());
	}
	
	@Test
	public void testCloseInputStreamWithNoInputStreamOpened() {
		YUVReader reader = new YUVReader(1920, 1080, 1);
		reader.closeInputStream();
	}
	
	@Test
	public void testCloseInputStreamWithInputStreamOpened() throws IOException {
		File video = folder.newFile("video.yuv");
		YUVReader reader = new YUVReader(1920, 1080, 1);
		reader.openInputStream(video.getCanonicalPath());
		reader.closeInputStream();
	}
	
	@Test
	public void testGetLuminancePixelsPerFrame() {
		YUVReader reader = new YUVReader(1920, 1080, 1);
		assertEquals((double) 1920 * 1080, reader.getLuminancePixelsPerFrame(), 0);
	}
	
	@Test
	public void testGetChrominancePixelsPerFrame() {
		YUVReader reader = new YUVReader(1920, 1080, 1);
		assertEquals((double) 1920 * 1080 * (1/4), reader.getChrominancePixelsPerFrame(), 0);
	}
	
}
