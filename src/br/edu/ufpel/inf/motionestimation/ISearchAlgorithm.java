package br.edu.ufpel.inf.motionestimation;

import br.edu.ufpel.inf.utils.CodingBlock;
import br.edu.ufpel.inf.utils.Frame;
import br.edu.ufpel.inf.utils.MotionEstimationVector;


public interface ISearchAlgorithm {
	
	public MotionEstimationVector run();
	
	public void setEvaluationCriteria(IEvaluationCriteria criteria);
	
	public void setActualFrame(Frame frame);
	public void setReferenceFrame(Frame frame);
	
	public void setCodingBlock(CodingBlock block);
	
	public void setSearchWidth(int width);
	public void setSearchHeight(int height);

}
