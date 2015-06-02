package motionestimationlens.models;

public class ThreeStepSearch extends SearchAlgorithm implements
		ISearchAlgorithm {

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
		int distance = searchWidth / 2;
		
		for (int i = 0; i < 3; i++) {
			
			x = resultX;
			y = resultY;
			
			// Apply evaluation criteria to central block
			temporaryResult = evaluationCriteria.calculate(initialX + x, initialY + y); 
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x;
				resultY = y;
			}
			
			// Apply evaluation criteria to top block
			temporaryResult = evaluationCriteria.calculate(initialX + x, initialY + y - distance); 
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x;
				resultY = y - distance;
			}
			
			// Apply evaluation criteria to top-right block
			temporaryResult = evaluationCriteria.calculate(initialX + x + distance, initialY + y - distance); 
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x + distance;
				resultY = y - distance;
			}
			
			// Apply evaluation criteria to right block
			temporaryResult = evaluationCriteria.calculate(initialX + x + distance, initialY + y); 
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x + distance;
				resultY = y;
			}
			
			// Apply evaluation criteria to bottom-right block
			temporaryResult = evaluationCriteria.calculate(initialX + x + distance, initialY + y + distance); 
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x + distance;
				resultY = y + distance;
			}
			
			// Apply evaluation criteria to bottom block
			temporaryResult = evaluationCriteria.calculate(initialX + x, initialY + y + distance); 
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x;
				resultY = y + distance;
			}
			
			// Apply evaluation criteria to bottom-left block
			temporaryResult = evaluationCriteria.calculate(initialX + x - distance, initialY + y + distance); 
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x - distance;
				resultY = y + distance;
			}
			
			// Apply evaluation criteria to left block
			temporaryResult = evaluationCriteria.calculate(initialX + x - distance, initialY + y); 
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x - distance;
				resultY = y;
			}
			
			// Apply evaluation criteria to top-left block
			temporaryResult = evaluationCriteria.calculate(initialX + x - distance, initialY + y - distance); 
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x - distance;
				resultY = y - distance;
			}
			
			distance = distance / 2;
			
			blocksVisited += 9;
		}
		
		vector.setPosition(resultX, resultY, initialX, initialY);
		vector.setCriteriaResult(criteriaResult);
		vector.setBlocksVisited(blocksVisited);
		
		return vector;
	}

}
