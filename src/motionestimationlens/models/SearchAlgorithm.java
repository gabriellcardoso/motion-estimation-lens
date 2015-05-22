package motionestimationlens.models;



public abstract class SearchAlgorithm {
	
	protected Frame actualFrame;
	protected Frame referenceFrame;
	
	protected IEvaluationCriteria evaluationCriteria;
	
	protected int searchWidth;
	protected int searchHeight;
	
	protected CodingBlock codingBlock;
	
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
		
		evaluationCriteria.setActualFrame(actualFrame);
		evaluationCriteria.setReferenceFrame(referenceFrame);
		evaluationCriteria.setCodingBlock(codingBlock);
	}

	public void setSearchArea(int width, int height) {
		searchWidth = width;
		searchHeight = height;
	}
	
	public void setCodingBlock(CodingBlock block) {
		codingBlock = block;
		evaluationCriteria.setCodingBlock(codingBlock);
	}
	
}
