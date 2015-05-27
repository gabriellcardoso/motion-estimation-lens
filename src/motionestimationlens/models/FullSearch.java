package motionestimationlens.models;



public class FullSearch extends SearchAlgorithm implements ISearchAlgorithm {
	
	public MotionEstimationVector run() {
		
		MotionEstimationVector vector = new MotionEstimationVector(codingBlock);
		
		int frameWidth = actualFrame.getWidth();
		int frameHeight = actualFrame.getHeight();
		
		int blockWidth = codingBlock.getWidth();
		int blockHeight = codingBlock.getHeight();
		
		int rangeX = searchHeight - blockHeight;
		int rangeY = searchWidth - blockWidth;
		
		int initialX = codingBlock.getPosition().getX() - rangeX / 2;
		int initialY = codingBlock.getPosition().getY() - rangeY / 2;
		
		int finalX = codingBlock.getPosition().getX() + rangeX / 2 + 1;
		int finalY = codingBlock.getPosition().getY() + rangeY / 2 + 1;
		
		int result = Integer.MAX_VALUE;
		int temporaryResult, blocksVisited;
		int x, y;
		
		if (initialX < 0) {
			initialX = 0;
		}
		
		if (initialY < 0) {
			initialY = 0;
		}
		
		if (finalX >= frameHeight - blockHeight) {
			finalX = frameHeight - blockHeight - 1;
		}
		
		if (finalY >= frameWidth - blockWidth) {
			finalY = frameWidth - blockWidth - 1;
		}
		
		
		blocksVisited = 0;
		
		vector.setPosition(initialX, initialY, initialX, initialY);
		
		for (x = initialX; x < finalX; x++) {
			for (y = initialY; y < finalY; y++) {
				blocksVisited++;
				temporaryResult = evaluationCriteria.calculate(x, y);
				if (temporaryResult < result) {
					result = temporaryResult;
					vector.setPosition(x, y, initialX, initialY);
					vector.setCriteriaResult(result);
				}
			}
		}
		
		vector.setBlocksVisited(blocksVisited);
		
		return vector;
	}
	
}
