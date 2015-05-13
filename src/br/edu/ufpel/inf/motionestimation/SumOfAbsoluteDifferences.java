package br.edu.ufpel.inf.motionestimation;

public class SumOfAbsoluteDifferences extends EvaluationCriteria implements IEvaluationCriteria {
	
	public SumOfAbsoluteDifferences() {
		setActualFrame(null);
		setReferenceFrame(null);
		setCodingBlock(null);
	}
	
	public int calculate(int positionX, int positionY) {
		
		byte[][] actualImage = actualFrame.getImage();
		byte[][] referenceImage = referenceFrame.getImage();
		
		int blockPositionX = codingBlock.getPosition().getX();
		int blockPositionY = codingBlock.getPosition().getY();

		int blockWidth = codingBlock.getWidth();
		int blockHeight = codingBlock.getHeight();
		
		int criteria = 0;
		int i, j;
		
		for (i = 0; i < blockHeight; i++) {
			for (j = 0; j < blockWidth; j++) {
				criteria += Math.abs(
					actualImage[blockPositionY + i][blockPositionX + j]
					- referenceImage[positionY + i][positionX + j]
				);
			}
		}
		
		return criteria;
	}

	
}
