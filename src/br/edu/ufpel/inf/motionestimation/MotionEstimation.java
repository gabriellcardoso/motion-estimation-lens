package br.edu.ufpel.inf.motionestimation;

import br.edu.ufpel.inf.utils.CodingBlock;
import br.edu.ufpel.inf.utils.Frame;
import br.edu.ufpel.inf.utils.Position;

@SuppressWarnings("unused")
public class MotionEstimation {

	private ISearchAlgorithm searchAlgorithm;
	private IEvaluationCriteria evaluationCriteria;
	
	private Frame actualFrame;
	private Frame referenceFrame;
	
	private CodingBlock codingBlock;
	
	private MotionEstimationResult results;
	
	public MotionEstimation(ISearchAlgorithm algorithm, IEvaluationCriteria criteria, int blockWidth, int blockHeight) {
		codingBlock = new CodingBlock(blockWidth, blockHeight);
		results = new MotionEstimationResult();
		
		searchAlgorithm = algorithm;
		searchAlgorithm.setEvaluationCriteria(criteria);
		searchAlgorithm.setCodingBlock(codingBlock);
	}
	
	public MotionEstimationResult run() {
		
		int frameHeight = actualFrame.getHeight();
		int frameWidth = actualFrame.getWidth();
		
		Position blockPosition = codingBlock.getPosition();
		int blockHeight = codingBlock.getHeight();
		int blockWidth = codingBlock.getWidth(); 
		
		int i, j;
		
		for (i = 0; i < frameHeight - blockHeight; i += blockHeight) {
			blockPosition.setY(i);
			for (j = 0; j < frameWidth - blockWidth; j += blockWidth) {
				blockPosition.setX(j);
				searchAlgorithm.run();
			}
		}
		
		return results;
	}
	
	public void setActualFrame(Frame frame) {
		actualFrame = frame;
		searchAlgorithm.setActualFrame(frame);
	}
	
	public void setReferenceFrame(Frame frame) {
		referenceFrame = frame;
		searchAlgorithm.setReferenceFrame(frame);
	}
	
}
