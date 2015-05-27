package motionestimationlens.models;



public class FullSearch extends SearchAlgorithm implements ISearchAlgorithm {
	
	public MotionEstimationVector run() {
		
		MotionEstimationVector vector = new MotionEstimationVector(codingBlock);
		
		int initialX = codingBlock.getPosition().getX() - searchWidth / 2;
		int initialY = codingBlock.getPosition().getY() - searchHeight / 2;
		
		int criteriaResult = Integer.MAX_VALUE;
		int resultX = 0;
		int resultY = 0;
		
		int temporaryResult = 0;
		int normalizedX;
		int normalizedY;
		
		int x, y;
		
		int blocksVisited = 0;
		
		for (y = 0; y < searchHeight; y++) {
			for (x = 0; x < searchWidth; x++) {

				temporaryResult = evaluationCriteria.calculate(initialX + x, initialY + y);
				normalizedX = initialX + x - codingBlock.getPosition().getX();
				normalizedY = initialY + y - codingBlock.getPosition().getY();
				blocksVisited++;
				
				if (temporaryResult < criteriaResult 
					|| temporaryResult == criteriaResult
					&& Math.pow(normalizedX * normalizedX + normalizedY * normalizedY, 2) 
						<= Math.pow(resultX * resultX + resultY * resultY, 2))
				{
					criteriaResult = temporaryResult;
					resultX = x;
					resultY = y;
				}
				
			}
		}
		
		System.out.println(temporaryResult);
		System.out.println();
		
		vector.setPosition(resultX, resultY, initialX, initialY);
		vector.setCriteriaResult(criteriaResult);
		vector.setBlocksVisited(blocksVisited);
		
		return vector;
	}
	
}
