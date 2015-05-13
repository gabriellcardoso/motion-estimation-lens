package br.edu.ufpel.inf.motionestimation;

import br.edu.ufpel.inf.utils.CodingBlock;
import br.edu.ufpel.inf.utils.Frame;

public class MotionEstimation {

	private ISearchAlgorithm searchAlgorithm;
	
	private Frame actualFrame;
	private Frame referenceFrame;
	
	private MotionEstimationResult results;
	
	public MotionEstimation(ISearchAlgorithm algorithm) {
		searchAlgorithm = algorithm;
		results = new MotionEstimationResult();
	}
	
	public void setActualFrame(Frame frame) {
		actualFrame = frame;
		searchAlgorithm.setActualFrame(actualFrame);
	}
	
	public void setReferenceFrame(Frame frame) {
		referenceFrame = frame;
		searchAlgorithm.setReferenceFrame(referenceFrame);
	}
	
	public void setSearchAlgorithm(ISearchAlgorithm algorithm) {
		searchAlgorithm = algorithm;
	}
	
	public void setEvaluationCriteria(IEvaluationCriteria criteria) {
		searchAlgorithm.setEvaluationCriteria(criteria);
	}
	
	public void setCodingBlock(int width, int height) {
		searchAlgorithm.setCodingBlock(width, height);
	}
	
public MotionEstimationResult run() {
		
		int frameHeight = actualFrame.getHeight();
		int frameWidth = actualFrame.getWidth();
		
		CodingBlock codingBlock = searchAlgorithm.getCodingBlock();
		
		int blockHeight = codingBlock.getHeight();
		int blockWidth = codingBlock.getWidth(); 
		
		int i, j;
		
		for (i = 0; i < frameHeight - blockHeight; i += blockHeight) {
			codingBlock.getPosition().setY(i);
			for (j = 0; j < frameWidth - blockWidth; j += blockWidth) {
				codingBlock.getPosition().setX(j);
				searchAlgorithm.run();
			}
		}
		
		return results;
	}
	
}
