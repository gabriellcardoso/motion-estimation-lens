package motionestimationlens.utils;

import java.io.FileOutputStream;
import java.io.IOException;

import motionestimationlens.models.Frame;

public class YUVWriter {

    private FileOutputStream outputStream;

    public YUVWriter(String fileName) {
        openOutputStream(fileName);
    }
    
    public void writeFrameOnFile(Frame frame) {
    	byte[][] image = frame.getImage();
        int index;

        for (index = 0; index < frame.getHeight(); index++) {
            writeLine(image[index]);
        }
    }
    
    private void writeLine(byte[] bytes) {
    	try {
    		outputStream.write(bytes);
    	}
    	catch (IOException error) {
    		System.err.println(error.getMessage());
    	}
    }

    private void openOutputStream(String fileName) {
    	try {
            outputStream = new FileOutputStream(fileName);
        }
        catch (IOException error) {
            System.err.println(error.getMessage() + " : " + fileName);
        }
    }

    private void closeOutputStream() {
        try {
            outputStream.flush();
            outputStream.close();
        } catch (IOException error) {
            System.err.println("Error closing " + outputStream + " : " + error.getMessage());
        }
    }
    
    protected void finalize() {
    	closeOutputStream();
    }

}