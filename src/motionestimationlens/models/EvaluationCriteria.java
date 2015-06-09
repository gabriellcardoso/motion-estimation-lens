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
		
		int rangeX = (width - codingBlock.getWidth()) / 2;
		int rangeY = (height - codingBlock.getHeight()) / 2;
		
		int initialX = codingBlock.getPosition().getX() - rangeX;
		int initialY = codingBlock.getPosition().getY() - rangeY;
		
		int finalX = codingBlock.getPosition().getX() + rangeX;
		int finalY = codingBlock.getPosition().getY() + rangeY;
		
		int x, y;
		
		heatMap = new double[finalX - initialX][finalY - initialY];
		
		for (y = initialY; y < finalY; y++) {
			for (x = initialX; x < finalX; x++) {
				heatMap[x - initialX][y - initialY] = calculate(x, y);
			}
		}
		
		return heatMap;
	}
	
	public abstract int calculate(int x, int y);
}
