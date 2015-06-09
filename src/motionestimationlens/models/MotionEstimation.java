package motionestimationlens.models;

import java.util.ArrayList;

public class MotionEstimation {

	private Frame actualFrame;
	private Frame referenceFrame;
	
	private ISearchAlgorithm searchAlgorithm;
	private IEvaluationCriteria evaluationCriteria;
	private CodingBlock codingBlock;
	
	private int searchAreaWidth;
	private int searchAreaHeight;
	
	private MotionEstimationData result;
	
	public MotionEstimation(ISearchAlgorithm algorithm, IEvaluationCriteria criteria, int blockWidth, int blockHeight, int searchAreaWidth, int searchAreaHeight) {
		searchAlgorithm = algorithm;
		evaluationCriteria = criteria;
		codingBlock = new CodingBlock(blockWidth, blockHeight);
		result = new MotionEstimationData();
		
		this.searchAreaWidth = searchAreaWidth;
		this.searchAreaHeight = searchAreaHeight;
		
		searchAlgorithm.setEvaluationCriteria(evaluationCriteria);
		searchAlgorithm.setSearchArea(searchAreaWidth, searchAreaHeight);
		searchAlgorithm.setCodingBlock(codingBlock);
	}
	
	public void setActualFrame(Frame frame) {
		actualFrame = frame;
		
		searchAlgorithm.setActualFrame(actualFrame);
	}
	
	public void setReferenceFrame(Frame frame) {
		referenceFrame = frame;
		
		searchAlgorithm.setReferenceFrame(referenceFrame);
	}
	
	public void setSearchArea(int width, int height) {
		searchAreaWidth = width;
		searchAreaHeight = height;
		
		searchAlgorithm.setSearchArea(searchAreaWidth, searchAreaHeight);
	}
	
	public void setCodingBlockPosition(int x, int y) {
		codingBlock.getPosition().setPosition(x, y);
	}
	
	public void setCodingBlock(int width, int height) {
		codingBlock.setWidth(width);
		codingBlock.setHeight(height);
	}
	
	public void setSearchAlgorithm(ISearchAlgorithm algorithm) {
		searchAlgorithm = algorithm;
		
		searchAlgorithm.setEvaluationCriteria(evaluationCriteria);
		searchAlgorithm.setSearchArea(searchAreaWidth, searchAreaHeight);
		searchAlgorithm.setCodingBlock(codingBlock);
	}
	
	public void setEvaluationCriteria(IEvaluationCriteria criteria) {
		evaluationCriteria = criteria;
		searchAlgorithm.setEvaluationCriteria(evaluationCriteria);
	}
	
	private int getCandidateBlocksTotal() {
		return  (searchAreaWidth - codingBlock.getWidth()) * (searchAreaHeight - codingBlock.getHeight());
	}
	
	public MotionEstimationData run() {
		MotionEstimationVector algorithmResult = searchAlgorithm.run();
		double[][] heatMap = evaluationCriteria.createHeatMap(searchAreaWidth, searchAreaHeight);
		int candidateBlocksTotal = getCandidateBlocksTotal();
		
		result.setCandidateBlocksTotal(candidateBlocksTotal);
		result.setResultVector(algorithmResult);
		result.setHeatMap(heatMap);
		
		return result;
	}
	
}
