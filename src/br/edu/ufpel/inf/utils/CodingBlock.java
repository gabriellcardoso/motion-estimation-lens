package br.edu.ufpel.inf.utils;


public class CodingBlock {
	
	private Position position;
	private int width;
	private int height;
	
	public CodingBlock(int width, int height) {
		this.width = width;
		this.height = height;
		createPosition(0, 0);
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
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}

}
