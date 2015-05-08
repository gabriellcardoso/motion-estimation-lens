package br.edu.ufpel.inf.motionestimation;

import br.edu.ufpel.inf.utils.CodingBlock;
import br.edu.ufpel.inf.utils.Frame;


public class SumOfAbsoluteDifferences implements IEvaluationCriteria {
	
	private Frame actualFrame;
	private Frame referenceFrame;
	private CodingBlock codingBlock;
	
	public SumOfAbsoluteDifferences() {
		actualFrame = null;
		referenceFrame = null;
		codingBlock = null;
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
	
	public void setActualFrame(Frame frame) {
		actualFrame = frame;
	}
	
	public void setReferenceFrame(Frame frame) {
		referenceFrame = frame;
	}
	
	public void setCodingBlock(CodingBlock block) {
		codingBlock = block;
	}
	
}
