package motionestimationlens.models;

import java.util.ArrayList;

public class MotionEstimation {

	private ISearchAlgorithm searchAlgorithm;
	
	private Frame actualFrame;
	private Frame referenceFrame;
	
	private ArrayList<MotionEstimationVector> results;
	
	public MotionEstimation(ISearchAlgorithm algorithm) {
		searchAlgorithm = algorithm;
		results = new ArrayList<MotionEstimationVector>();
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
	
	public ArrayList<MotionEstimationVector> run() {
		int frameHeight = actualFrame.getHeight();
		int frameWidth = actualFrame.getWidth();
		
		CodingBlock codingBlock = searchAlgorithm.getCodingBlock();
		
		int blockHeight = codingBlock.getHeight();
		int blockWidth = codingBlock.getWidth(); 
		
		int i, j;
		
		results.clear();
		
		for (i = 0; i < frameHeight - blockHeight; i += blockHeight) {
			codingBlock.getPosition().setY(i);
			for (j = 0; j < frameWidth - blockWidth; j += blockWidth) {
				codingBlock.getPosition().setX(j);
				results.add(searchAlgorithm.run());
			}
		}
		
		return results;
	}
	
}
