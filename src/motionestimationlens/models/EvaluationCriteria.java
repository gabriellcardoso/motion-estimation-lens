package motionestimationlens.models;


public abstract class EvaluationCriteria {
	
	protected Frame actualFrame;
	protected Frame referenceFrame;
	
	protected CodingBlock codingBlock;
	
	public void setActualFrame(Frame frame) {
		actualFrame = frame;
	}
	
	public void setReferenceFrame(Frame frame) {
		referenceFrame = frame;
	}
	
	public void setCodingBlock(CodingBlock block) {
		codingBlock = block;
	}
	
	public double[][] createHeatMap(int width, int height) {
		
		double[][] heatMap;
		
		int frameWidth = actualFrame.getWidth();
		int frameHeight = actualFrame.getHeight();
		
		int blockWidth = codingBlock.getWidth();
		int blockHeight = codingBlock.getHeight();
		
		int rangeX = width - blockWidth;
		int rangeY = height - blockHeight;
		
		int initialX = codingBlock.getPosition().getX() - rangeX / 2;
		int initialY = codingBlock.getPosition().getY() - rangeY / 2;
		
		int finalX = codingBlock.getPosition().getX() + rangeX / 2 + 1;
		int finalY = codingBlock.getPosition().getY() + rangeY / 2 + 1;
		
		int x, y;
		
		if (initialX < 0) {
			initialX = 0;
		}
		
		if (initialY < 0) {
			initialY = 0;
		}
		
		if (finalX >= frameWidth - blockWidth) {
			finalX = frameWidth - blockWidth - 1;
		}
		
		if (finalY >= frameHeight - blockHeight) {
			finalY = frameHeight - blockHeight - 1;
		}
		
		heatMap = new double[finalY - initialY][finalX - initialX];
		
		for (y = initialY; y < finalY; y++) {
			for (x = initialX; x < finalX; x++) {
				heatMap[y - initialY][x - initialX] = calculate(x, y);
			}
		}
		
		return heatMap;
	}
	
	public abstract int calculate(int x, int y);
}
