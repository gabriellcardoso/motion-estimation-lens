package br.edu.ufpel.inf.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class YUVReader {

    private FileInputStream inputStream;
    public FileChannel fileChannel;

    private int videoWidth;
    private int videoHeight;
    private int sampling;
    
    public YUVReader(int videoWidth, int videoHeight, int sampling) { 
    	this.videoWidth = videoWidth;
    	this.videoHeight = videoHeight;
    	this.sampling = sampling;
    }
    
    public YUVReader(String fileName, int videoWidth, int videoHeight, int sampling) {
    	this(videoWidth, videoHeight, sampling);
    	openInputStream(fileName);
    }
    
    public void openInputStream(String fileName) {
    	try {
    		inputStream = new FileInputStream(fileName);
    		fileChannel = inputStream.getChannel();
    	}
    	catch (FileNotFoundException error) {
    		System.err.println(error.getMessage());
    	}
    }

    public void closeInputStream() {
    	if (inputStream == null)
    		return;
    	
    	try {
    		inputStream.close();
    		fileChannel.close();
    	}
    	catch (IOException error) {
    		System.err.println(error.getMessage());
    	}
    }

    public void getFrame(byte[][] destination, int index) {
    	int line = 0;
    	
    	try {
    		goToFrame(index);
    		
    		for (line = 0; line < videoHeight; line++) {
    			readLine(destination[line], videoWidth);
    		}
    	}
    	catch (IOException error) {
    		System.err.println(error.getMessage());
    	}
    }
    
    private void goToFrame(int index) throws IOException {
    	double luminancePixels = getLuminancePixelsPerFrame();
    	double chrominancePixels = getChrominancePixelsPerFrame();
    	double position = (luminancePixels + chrominancePixels * 2) * index;
    	
    	fileChannel.position((long)position);
    }

    private void readLine(byte[] line, int length) throws IOException {
    	ByteBuffer buffer = ByteBuffer.wrap(line);

        if (fileChannel.read(buffer) != length) {
            throw new IOException();
        }
    }
    
    public double getLuminancePixelsPerFrame() {
    	return videoWidth * videoHeight;
    }
    
    public double getChrominancePixelsPerFrame() {
    	double scale = (double) sampling / 4;
    	return videoWidth * videoHeight * scale;
    }

}
