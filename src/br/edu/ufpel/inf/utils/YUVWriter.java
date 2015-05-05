package br.edu.ufpel.inf.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class YUVWriter {

    private FileOutputStream outputStream;

	private int width;
    private int height;

    public YUVWriter(File file, int width, int height) throws IOException {
        this.width = width;
        this.height = height;

        try {
            outputStream = new FileOutputStream(file);
        }
        catch (IOException error) {
            throw new IOException(error.getMessage() + " : " + file.getName());
        }
    }

    public void close() {
        try {
            outputStream.flush();
            outputStream.close();
        } catch (IOException error) {
            System.err.println("Error closing " + outputStream + " : " + error);
        }
    }

    public void writeFrame(byte[][] frame) throws IOException {
        int index;

        for (index = 0; index < height; index++) {
            writeLine(frame[index]);
        }
    }

    private void writeLine(byte[] bytes) throws IOException {
    	if (bytes.length == width) {
    		outputStream.write(bytes);
    	}
    }

}
