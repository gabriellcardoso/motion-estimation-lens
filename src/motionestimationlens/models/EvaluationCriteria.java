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
		
		if (finalX >= frameHeight - blockHeight) {
			finalX = frameHeight - blockHeight - 1;
		}
		
		if (finalY >= frameWidth - blockWidth) {
			finalY = frameWidth - blockWidth - 1;
		}
		
		heatMap = new double[finalX - initialX][finalY - initialY];
		
		for (x = initialX; x < finalX; x++) {
			for (y = initialY; y < finalY; y++) {
				heatMap[x - initialX][y - initialY] = calculate(x, y);
			}
		}
		
		return heatMap;
	}
	
	public abstract int calculate(int x, int y);
}
