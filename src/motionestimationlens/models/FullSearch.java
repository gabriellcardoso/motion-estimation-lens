package motionestimationlens.models;

public class FullSearch extends SearchAlgorithm implements ISearchAlgorithm {
	
	public MotionEstimationVector run() {
		
		MotionEstimationVector vector = new MotionEstimationVector(codingBlock);
		
		int rangeX = (searchWidth - codingBlock.getWidth()) / 2;
		int rangeY = (searchHeight - codingBlock.getHeight()) / 2;
		
		int initialX = codingBlock.getPosition().getX() - rangeX;
		int initialY = codingBlock.getPosition().getY() - rangeY;

		int finalX = codingBlock.getPosition().getX() + rangeX;
		int finalY = codingBlock.getPosition().getY() + rangeY;
		
		int resultX = initialX;
		int resultY = initialY;
		
		int criteriaResult = Integer.MAX_VALUE;
		int temporaryResult = 0;
		int blocksVisited = 0;
		
		int normalizedX, normalizedY, normalizedResultX, normalizedResultY;
		int x, y;
		
		for (y = initialY; y < finalY; y++) {
			for (x = initialX; x < finalX; x++) {

				temporaryResult = evaluationCriteria.calculate(x, y);
				
				normalizedX = codingBlock.getPosition().getX() - x;
				normalizedY = codingBlock.getPosition().getY() - y;
				
				normalizedResultX = codingBlock.getPosition().getX() - resultX;
				normalizedResultY = codingBlock.getPosition().getY() - resultY;
				
				blocksVisited++;
				
				if (temporaryResult < criteriaResult 
					|| temporaryResult == criteriaResult
					&& Math.sqrt(normalizedX * normalizedX + normalizedY * normalizedY) <= Math.sqrt(normalizedResultX * normalizedResultX + normalizedResultY * normalizedResultY))
				{
					criteriaResult = temporaryResult;
					resultX = x;
					resultY = y;
				}
				
			}
		}
		
		vector.setPosition(resultX, resultY, initialX, initialY);
		vector.setCriteriaResult(criteriaResult);
		vector.setBlocksVisited(blocksVisited);
		
		return vector;
	}
	
}
