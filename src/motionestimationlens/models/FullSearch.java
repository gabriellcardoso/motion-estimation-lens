package motionestimationlens.models;



public class FullSearch extends SearchAlgorithm implements ISearchAlgorithm {
	
	public MotionEstimationVector run() {
		
		MotionEstimationVector vector = new MotionEstimationVector(codingBlock);
		
		int frameWidth = actualFrame.getWidth();
		int frameHeight = actualFrame.getHeight();
		
		int blockWidth = codingBlock.getWidth();
		int blockHeight = codingBlock.getHeight();
		
		int rangeX = searchWidth - blockWidth;
		int rangeY = searchHeight - blockHeight;
		
		int initialX = codingBlock.getPosition().getX() - rangeX / 2;
		int initialY = codingBlock.getPosition().getY() - rangeY / 2;
		
		int finalX = codingBlock.getPosition().getX() + rangeX / 2;
		int finalY = codingBlock.getPosition().getY() + rangeY / 2;
		
		int result = Integer.MAX_VALUE;
		int temporaryResult, blocksVisited;
		int x, y;
		
		if (initialX < 0) {
			initialX = 0;
		}
		
		if (initialY < 0) {
			initialY = 0;
		}
		
		if (finalX >= frameWidth - blockWidth) {
			finalX = frameWidth - blockWidth - 1;
		}
		
		if (finalY >= frameHeight - blockHeight) {
			finalY = frameHeight - blockHeight - 1;
		}
		
		blocksVisited = 0;
		
		for (y = initialY; y < finalY; y++) {
			for (x = initialX; x < finalX; x++) {
				blocksVisited++;
				temporaryResult = evaluationCriteria.calculate(x, y);
				if (temporaryResult < result) {
					result = temporaryResult;
					vector.setPosition(x, y);
					vector.setCriteriaResult(result);
				}
			}
		}
		
		vector.setBlocksVisited(blocksVisited);
		
		return vector;
	}
	
}
