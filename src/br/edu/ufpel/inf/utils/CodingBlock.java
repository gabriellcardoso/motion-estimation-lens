package br.edu.ufpel.inf.utils;


public class CodingBlock {
	
	private Position position;
	private int width;
	private int height;
	
	public CodingBlock(int positionX, int positionY, int width, int height) {
		this.width = width;
		this.height = height;
		createPosition(positionX, positionY);
	};
	
	private void createPosition(int positionX, int positionY) {
		position = new Position(positionX, positionY);
	}
	
	public Position getPosition() {
		return position;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

}
