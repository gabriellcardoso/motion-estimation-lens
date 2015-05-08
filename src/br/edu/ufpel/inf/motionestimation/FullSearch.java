package br.edu.ufpel.inf.motionestimation;

import br.edu.ufpel.inf.utils.CodingBlock;
import br.edu.ufpel.inf.utils.Frame;
import br.edu.ufpel.inf.utils.MotionEstimationVector;

public class FullSearch implements ISearchAlgorithm {
	
	private IEvaluationCriteria evaluationCriteria;
	
	private Frame actualFrame;
	private Frame referenceFrame;
	
	private CodingBlock codingBlock;
	
	private int searchWidth;
	private int searchHeight;
	
	public FullSearch(int width, int height) {
		searchWidth = width;
		searchHeight = height;
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
	
	public void setEvaluationCriteria(IEvaluationCriteria criteria) {
		evaluationCriteria = criteria;
	}
	
	public void setActualFrame(Frame frame) {
		actualFrame = frame;
		evaluationCriteria.setActualFrame(frame);
	}
	
	public void setReferenceFrame(Frame frame) {
		referenceFrame = frame;
		evaluationCriteria.setReferenceFrame(frame);
	}
	
	public void setCodingBlock(CodingBlock block) {
		codingBlock = block;
		evaluationCriteria.setCodingBlock(block);
	}
	
	public void setSearchWidth(int width) {
		searchWidth = width;
	}
	
	public void setSearchHeight(int height) {
		searchHeight = height;
	}
	
}
