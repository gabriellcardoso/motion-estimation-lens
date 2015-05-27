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
		
		int initialX = codingBlock.getPosition().getX() - width / 2;
		int initialY = codingBlock.getPosition().getY() - height / 2;
		
		int x, y;
		
		heatMap = new double[width][height];
		
		for (y = 0; y < height; y++) {
			for (x = 0; x < width; x++) {
				heatMap[x][y] = calculate(initialX + x, initialY + y);
			}
		}
		
		return heatMap;
	}
	
	public abstract int calculate(int x, int y);
}
