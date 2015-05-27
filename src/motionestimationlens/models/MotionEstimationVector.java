package motionestimationlens.models;


public class MotionEstimationVector {

	private CodingBlock codingBlock;
	private Position position;
	private Position heatMapPosition;
	private int criteriaResult;
	private int blocksVisited;
	
	public MotionEstimationVector(CodingBlock block) {
		initializeAttributes();
		createCodingBlock(block);
	}
	
	private void initializeAttributes() {
		position = new Position();
		heatMapPosition = new Position();
		criteriaResult = Integer.MAX_VALUE;
	}
	
	private void createCodingBlock(CodingBlock block) {
		try {
			codingBlock = (CodingBlock) block.clone();
		}
		catch (CloneNotSupportedException exception) {
			System.err.println(exception.getMessage());
		}
	}
	
	public CodingBlock getCodingBlock() {
		return codingBlock;
	}
	
	public Position getPosition() {
		return position;
	}
	
	public Position getHeatMapPosition() {
		return heatMapPosition;
	}
	
	public int getCriteriaResult() {
		return criteriaResult;
	}
	
	public int getBlocksVisited() {
		return blocksVisited;
	}
	
	public void setPosition(int x, int y, int initialSearchX, int initialSearchY) {
		int normalizedX = initialSearchX + x  - codingBlock.getPosition().getX();
		int normalizedY = initialSearchY + y  - codingBlock.getPosition().getY();
		int heatMapX = x;
		int heatMapY = y;
		
		heatMapPosition.setPosition(heatMapX, heatMapY);
		position.setPosition(normalizedX, normalizedY);
	}
	
	public void setCriteriaResult(int result) {
		criteriaResult = result;
	}
	
	public void setBlocksVisited(int total) {
		blocksVisited = total;
	}
	
}
