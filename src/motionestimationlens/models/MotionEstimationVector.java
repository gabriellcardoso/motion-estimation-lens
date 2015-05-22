package motionestimationlens.models;


public class MotionEstimationVector {

	private CodingBlock codingBlock;
	private Position position;
	private int criteriaResult;
	private int blocksVisited;
	
	public MotionEstimationVector(CodingBlock block) {
		initializeAttributes();
		createCodingBlock(block);
	}
	
	private void initializeAttributes() {
		position = new Position();
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
	
	public int getCriteriaResult() {
		return criteriaResult;
	}
	
	public int getBlocksVisited() {
		return blocksVisited;
	}
	
	public void setPosition(int x, int y) {
		position.setPosition(x, y);
	}
	
	public void setCriteriaResult(int result) {
		criteriaResult = result;
	}
	
	public void setBlocksVisited(int total) {
		blocksVisited = total;
	}
	
}
