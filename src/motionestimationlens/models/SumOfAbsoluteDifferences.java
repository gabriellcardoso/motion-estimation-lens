package motionestimationlens.models;


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
		int x, y;
		
		int actualImagePixel, referenceImagePixel;
		
		for (y = 0; y < blockHeight; y++) {
			for (x = 0; x < blockWidth; x++) {
				
				if (blockPositionY + y < actualFrame.getHeight()
					&& blockPositionX + x < actualFrame.getWidth())
				{
					actualImagePixel = actualImage[blockPositionY + y][blockPositionX + x];
				}
				else {
					actualImagePixel = 0;
				}
				
				if (positionY + y >= 0 
					&& positionX + x >= 0
					&& positionY + y < actualFrame.getHeight() 
					&& positionX + x < actualFrame.getWidth())
				{
					referenceImagePixel = referenceImage[positionY + y][positionX + x];
				}
				else {
					referenceImagePixel = 0;
				}
				
				criteria += Math.abs(
					actualImagePixel
					- referenceImagePixel
				);
			}
		}
		
		return criteria;
	}

	
}
