package br.edu.ufpel.inf.motionestimation;

import br.edu.ufpel.inf.utils.CodingBlock;
import br.edu.ufpel.inf.utils.Frame;

public abstract class SearchAlgorithm {
	
	protected Frame actualFrame;
	protected Frame referenceFrame;
	
	protected IEvaluationCriteria evaluationCriteria;
	
	protected int searchWidth;
	protected int searchHeight;
	
	protected CodingBlock codingBlock;
	
	public SearchAlgorithm(IEvaluationCriteria criteria, int blockWidth, int blockHeight, int searchWidth, int searchHeight) {
		setEvaluationCriteria(criteria);
		setSearchArea(searchWidth, searchHeight);
		createCodingBlock(blockWidth, blockHeight);
	}
	
	private void createCodingBlock(int width, int height) {
		codingBlock = new CodingBlock(width, height);
	}
	
	public CodingBlock getCodingBlock() {
		return codingBlock;
	}
	
	public void setActualFrame(Frame frame) {
		actualFrame = frame;
		evaluationCriteria.setActualFrame(actualFrame);
	}
	
	public void setReferenceFrame(Frame frame) {
		referenceFrame = frame;
		evaluationCriteria.setReferenceFrame(referenceFrame);
	}
	
	public void setEvaluationCriteria(IEvaluationCriteria criteria) {
		evaluationCriteria = criteria;
	}

	public void setSearchArea(int width, int height) {
		searchWidth = width;
		searchHeight = height;
	}
	
	public void setCodingBlock(int width, int height) {
		codingBlock.setWidth(width);
		codingBlock.setHeight(height);
	}
	
}
