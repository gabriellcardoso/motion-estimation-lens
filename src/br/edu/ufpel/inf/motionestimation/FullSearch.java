package br.edu.ufpel.inf.motionestimation;


public class FullSearch extends SearchAlgorithm implements ISearchAlgorithm {
	
	public FullSearch(IEvaluationCriteria criteria, int blockWidth, int blockHeight, int searchWidth, int searchHeight) {
		super(criteria, blockWidth, blockHeight, searchWidth, searchHeight);
	}
	
	public MotionEstimationVector run() {
		
		MotionEstimationVector vector = new MotionEstimationVector();
		
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
		
		int criteria = Integer.MAX_VALUE;
		int temporaryCriteria;
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
		
		for (y = initialY; y < finalY; y++) {
			for (x = initialX; x < finalX; x++) {
				temporaryCriteria = evaluationCriteria.calculate(x, y);
				if (temporaryCriteria < criteria) {
					criteria = temporaryCriteria;
				}
			}
		}
		
		return vector;
	}
	
}
