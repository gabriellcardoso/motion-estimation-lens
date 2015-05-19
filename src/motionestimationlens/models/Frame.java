package motionestimationlens.models;

public class Frame {

	byte[][] frame;
	int width;
	int height;
	
	public Frame(int width, int height) {
		this.width = width;
		this.height = height;
		createFrame();
	}
	
	private void createFrame() {
		frame = new byte[height][width];
	}
	
	public byte[][] getImage() {
		return frame;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
}
