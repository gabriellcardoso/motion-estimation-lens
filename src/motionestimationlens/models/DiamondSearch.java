package motionestimationlens.models;

public class DiamondSearch extends SearchAlgorithm implements ISearchAlgorithm {

	@Override
	public MotionEstimationVector run() {
		MotionEstimationVector vector = new MotionEstimationVector(codingBlock);
		
		int rangeX = (searchWidth - codingBlock.getWidth()) / 2;
		int rangeY = (searchHeight - codingBlock.getHeight()) / 2;
		
		int initialX = codingBlock.getPosition().getX() - rangeX;
		int initialY = codingBlock.getPosition().getY() - rangeY;
		
		int finalX = codingBlock.getPosition().getX() + rangeX;
		int finalY = codingBlock.getPosition().getY() + rangeY;
		
		int criteriaResult = Integer.MAX_VALUE;
		
		int resultX = codingBlock.getPosition().getX();
		int resultY = codingBlock.getPosition().getY();
		
		int temporaryResult = 0;
		int blocksVisited = 0;
		
		int x,y;
		
		// Large Diamond Search Pattern
		do {
			// Set the central block
			x = resultX;
			y = resultY;
			
			// Apply the evaluation criteria for the central block
			temporaryResult = evaluationCriteria.calculate(x, y);
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x; 
				resultY = y;
			}
			
			// Apply the evaluation criteria for the north block
			temporaryResult = evaluationCriteria.calculate(x, y - 2);
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x; 
				resultY = y - 2;
			}
			
			// Apply the evaluation criteria for the northeast block
			temporaryResult = evaluationCriteria.calculate(x + 1, y - 1);
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x + 1; 
				resultY = y - 1;
			}
			
			// Apply the evaluation criteria for the east block
			temporaryResult = evaluationCriteria.calculate(x + 2, y);
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x + 2; 
				resultY = y;
			}
			
			// Apply the evaluation criteria for the southeast block
			temporaryResult = evaluationCriteria.calculate(x + 1, y + 1);
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x + 1; 
				resultY = y + 1;
			}
			
			// Apply the evaluation criteria for the south block
			temporaryResult = evaluationCriteria.calculate(x, y + 2);
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x; 
				resultY = y + 2;
			}
			
			// Apply the evaluation criteria for the southwest block
			temporaryResult = evaluationCriteria.calculate(x - 1, y + 1);
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x - 1; 
				resultY = y + 1;
			}
			
			// Apply the evaluation criteria for the west block
			temporaryResult = evaluationCriteria.calculate(x - 2, y);
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x - 2; 
				resultY = y;
			}
			
			// Apply the evaluation criteria for the northwest block
			temporaryResult = evaluationCriteria.calculate(x - 1, y -1);
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x - 1; 
				resultY = y - 1;
			}
			
			blocksVisited += 9;
			
		} while (x != resultX || y != resultY);
		
		// Small Diamond Search Pattern
		do {
			// Set the central block
			x = resultX;
			y = resultY;
			
			// Apply the evaluation criteria for the central block
			temporaryResult = evaluationCriteria.calculate(x, y);
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x; 
				resultY = y;
			}
			
			// Apply the evaluation criteria for the north block
			temporaryResult = evaluationCriteria.calculate(x, y - 1);
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x; 
				resultY = y - 1;
			}
			
			// Apply the evaluation criteria for the east block
			temporaryResult = evaluationCriteria.calculate(x + 1, y);
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x + 1; 
				resultY = y;
			}
			
			// Apply the evaluation criteria for the south block
			temporaryResult = evaluationCriteria.calculate(x, y + 1);
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x; 
				resultY = y + 1;
			}

			// Apply the evaluation criteria for the west block
			temporaryResult = evaluationCriteria.calculate(x - 1, y);
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x - 1; 
				resultY = y;
			}
			
			blocksVisited += 5;
			
		} while (x != resultX || y != resultY);
		
		vector.setPosition(resultX, resultY, initialX, initialY);
		vector.setCriteriaResult(criteriaResult);
		vector.setBlocksVisited(blocksVisited);
		
		return vector;
	}

}
