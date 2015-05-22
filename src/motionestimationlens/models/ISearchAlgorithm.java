package motionestimationlens.models;




public interface ISearchAlgorithm {
	
	public void setActualFrame(Frame frame);
	public void setReferenceFrame(Frame frame);
	
	public void setEvaluationCriteria(IEvaluationCriteria criteria);
	
	public void setSearchArea(int width, int height);
	public void setCodingBlock(CodingBlock block);
	
	public MotionEstimationVector run();

}
