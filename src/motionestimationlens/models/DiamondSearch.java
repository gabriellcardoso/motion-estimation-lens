package motionestimationlens.models;

public class DiamondSearch extends SearchAlgorithm implements ISearchAlgorithm {

	@Override
	public MotionEstimationVector run() {
		MotionEstimationVector vector = new MotionEstimationVector(codingBlock);
		
		int initialX = codingBlock.getPosition().getX() - searchWidth / 2;
		int initialY = codingBlock.getPosition().getY() - searchHeight / 2;
		
		int criteriaResult = Integer.MAX_VALUE;
		int resultX = searchWidth / 2;
		int resultY = searchHeight / 2;
		
		int temporaryResult = 0;
		int blocksVisited = 0;
		
		int x,y;
		
		// Large Diamond Search Pattern
		do {
			// Set the central block
			x = resultX;
			y = resultY;
			
			// Apply the evaluation criteria for the central block
			temporaryResult = evaluationCriteria.calculate(initialX + x, initialY + y);
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x; 
				resultY = y;
			}
			
			// Apply the evaluation criteria for the north block
			temporaryResult = evaluationCriteria.calculate(initialX + x, initialY + y - 2);
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x; 
				resultY = y - 2;
			}
			
			// Apply the evaluation criteria for the northeast block
			temporaryResult = evaluationCriteria.calculate(initialX + x + 1, initialY + y - 1);
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x + 1; 
				resultY = y - 1;
			}
			
			// Apply the evaluation criteria for the east block
			temporaryResult = evaluationCriteria.calculate(initialX + x + 2, initialY + y);
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x + 2; 
				resultY = y;
			}
			
			// Apply the evaluation criteria for the southeast block
			temporaryResult = evaluationCriteria.calculate(initialX + x + 1, initialY + y + 1);
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x + 1; 
				resultY = y + 1;
			}
			
			// Apply the evaluation criteria for the south block
			temporaryResult = evaluationCriteria.calculate(initialX + x, initialY + y + 2);
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x; 
				resultY = y + 2;
			}
			
			// Apply the evaluation criteria for the southwest block
			temporaryResult = evaluationCriteria.calculate(initialX + x - 1, initialY + y + 1);
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x - 1; 
				resultY = y + 1;
			}
			
			// Apply the evaluation criteria for the west block
			temporaryResult = evaluationCriteria.calculate(initialX + x - 2, initialY + y);
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x - 2; 
				resultY = y;
			}
			
			// Apply the evaluation criteria for the northwest block
			temporaryResult = evaluationCriteria.calculate(initialX + x - 1, initialY + y -1);
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
			temporaryResult = evaluationCriteria.calculate(initialX + x, initialY + y);
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x; 
				resultY = y;
			}
			
			// Apply the evaluation criteria for the north block
			temporaryResult = evaluationCriteria.calculate(initialX + x, initialY + y - 1);
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x; 
				resultY = y - 1;
			}
			
			// Apply the evaluation criteria for the east block
			temporaryResult = evaluationCriteria.calculate(initialX + x + 1, initialY + y);
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x + 1; 
				resultY = y;
			}
			
			// Apply the evaluation criteria for the south block
			temporaryResult = evaluationCriteria.calculate(initialX + x, initialY + y + 1);
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x; 
				resultY = y + 1;
			}

			// Apply the evaluation criteria for the west block
			temporaryResult = evaluationCriteria.calculate(initialX + x - 1, initialY + y);
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
