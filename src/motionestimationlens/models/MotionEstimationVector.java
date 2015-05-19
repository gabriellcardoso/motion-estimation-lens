package motionestimationlens.models;


public class MotionEstimationVector {

	private CodingBlock codingBlock;
	private Position position;
	private int criteriaResult;
	
	public MotionEstimationVector(CodingBlock block) {
		position = new Position();
		criteriaResult = Integer.MAX_VALUE;
		
		createCodingBlock(block);
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
	
	public void setPosition(int x, int y) {
		position.setPosition(x, y);
	}
	
	public void setCriteriaResult(int result) {
		criteriaResult = result;
	}
	
	
	
	
	
}
