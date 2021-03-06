package motionestimationlens.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import motionestimationlens.models.Frame;

public class YUVReader {

    private FileInputStream inputStream;
    public FileChannel fileChannel;

    private int videoWidth;
    private int videoHeight;
    private int sampling;
    
    public YUVReader(File file, int videoWidth, int videoHeight, int sampling) {
    	this.videoWidth = videoWidth;
    	this.videoHeight = videoHeight;
    	this.sampling = sampling;
    	openInputStream(file);
    }
    
    public void setFrameWithImage(Frame frame, int index) {
    	try {
    		goToFrame(index);
    		writeImageOnFrame(frame);
    	}
    	catch (IOException error) {
    		System.err.println(error.getMessage());
    	}
    }
    
    
    public int getFramesTotal() { 
    	double luminancePixels = getLuminancePixelsPerFrame();
    	double chrominancePixels = getChrominancePixelsPerFrame(); 
    	long fileSize = 0;
    	
    	try {
    		fileSize = fileChannel.size();
    	} catch (IOException error) {
    		System.out.println(error.getMessage());
    	}
    	
    	return (int) (fileSize / (luminancePixels + chrominancePixels * 2));
    }
    
    private void writeImageOnFrame(Frame frame) throws IOException {
    	byte[][] image = frame.getImage();
    	int line = 0;
    	
    	for (line = 0; line < frame.getHeight(); line++) {
			readLine(image[line], frame.getWidth());
		}
    }
    
    private void readLine(byte[] line, int length) throws IOException {
    	ByteBuffer buffer = ByteBuffer.wrap(line);

        if (fileChannel.read(buffer) != length) {
            throw new IOException();
        }
    }
    
    private void goToFrame(int index) throws IOException {
    	double luminancePixels = getLuminancePixelsPerFrame();
    	double chrominancePixels = getChrominancePixelsPerFrame();
    	double position = (luminancePixels + chrominancePixels * 2) * index;
    	
    	fileChannel.position((long)position);
    }
    
    private double getLuminancePixelsPerFrame() {
    	return videoWidth * videoHeight;
    }
    
    private double getChrominancePixelsPerFrame() {
    	double scale = (double) sampling / 4;
    	return videoWidth * videoHeight * scale;
    }
    
    private void openInputStream(File file) {
    	try {
    		inputStream = new FileInputStream(file);
    		fileChannel = inputStream.getChannel();
    	}
    	catch (FileNotFoundException error) {
    		System.err.println(error.getMessage());
    	}
    }

    private void closeInputStream() {
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
    
    protected void finalize() {
    	closeInputStream();
    }

}
