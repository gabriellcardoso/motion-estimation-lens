package motionestimationlens.models;



public class CodingBlock implements Cloneable {
	
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

	@Override
	public Object clone() throws CloneNotSupportedException {
		CodingBlock newBlock = new CodingBlock(width, height);
		int x = getPosition().getX();
		int y = getPosition().getY();
		
		newBlock.getPosition().setPosition(x, y);
		return newBlock;
	}
	
	

}
