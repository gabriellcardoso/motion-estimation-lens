package br.edu.ufpel.inf.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class YUVReader {

    private FileInputStream inputStream;

    private int width;
    private int height;

    public YUVReader(File file, int width, int height) throws IOException {
        this.width = width;
        this.height = height;

        try {
            inputStream = new FileInputStream(file);
        }
        catch (IOException error) {
            throw new IOException(error.getMessage() + " : " + file.getName());
        }
    }

    public void close() {
        try {
            inputStream.close();
        } catch (IOException error) {
            System.err.println("Error closing " + inputStream + " : " + error);
        }
    }

    public byte[][] getNextFrame() throws IOException {
        byte[][] frame = new byte[height][width];
        int index;

        for (index = 0; index < height; index++) {
            frame[index] = readLine(width);

            if (frame[index] == null) {
                break;
            }
        }

        skipCbCr();

        return frame;
    }

    private byte[] readLine(int size) throws IOException {
        byte[] bytes = new byte[size];

        int offset = 0;
        int read = 0;

        while (offset < bytes.length && read >= 0) {
            read = inputStream.read(bytes, offset, bytes.length - offset);
            offset += read;
        }

        if (read != size) {
            return null;
        }

        return bytes;
    }

    private void skipCbCr() throws IOException {
        inputStream.skip(width * height / 4);
    }

}
