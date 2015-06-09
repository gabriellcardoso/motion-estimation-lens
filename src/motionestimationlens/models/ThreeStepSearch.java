package motionestimationlens.models;

public class ThreeStepSearch extends SearchAlgorithm implements
		ISearchAlgorithm {

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
		int distance = rangeX / 2;
		
		for (int i = 0; i < 3; i++) {
			
			x = resultX;
			y = resultY;
			
			// Apply evaluation criteria to central block
			temporaryResult = evaluationCriteria.calculate(x, y); 
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x;
				resultY = y;
			}
			
			// Apply evaluation criteria to top block
			temporaryResult = evaluationCriteria.calculate(x, y - distance); 
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x;
				resultY = y - distance;
			}
			
			// Apply evaluation criteria to top-right block
			temporaryResult = evaluationCriteria.calculate(x + distance, y - distance); 
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x + distance;
				resultY = y - distance;
			}
			
			// Apply evaluation criteria to right block
			temporaryResult = evaluationCriteria.calculate(x + distance, y); 
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x + distance;
				resultY = y;
			}
			
			// Apply evaluation criteria to bottom-right block
			temporaryResult = evaluationCriteria.calculate(x + distance, y + distance); 
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x + distance;
				resultY = y + distance;
			}
			
			// Apply evaluation criteria to bottom block
			temporaryResult = evaluationCriteria.calculate(x, y + distance); 
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x;
				resultY = y + distance;
			}
			
			// Apply evaluation criteria to bottom-left block
			temporaryResult = evaluationCriteria.calculate(x - distance, y + distance); 
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x - distance;
				resultY = y + distance;
			}
			
			// Apply evaluation criteria to left block
			temporaryResult = evaluationCriteria.calculate(x - distance, y); 
			if (temporaryResult < criteriaResult) {
				criteriaResult = temporaryResult;
				resultX = x - distance;
				resultY = y;
			}
			
			// Apply evaluation criteria to top-left block
			temporaryResult = evaluationCriteria.calculate(x - distance, y - distance); 
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
