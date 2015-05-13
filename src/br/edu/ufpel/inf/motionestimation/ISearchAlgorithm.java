package br.edu.ufpel.inf.motionestimation;

import br.edu.ufpel.inf.utils.CodingBlock;
import br.edu.ufpel.inf.utils.Frame;


public interface ISearchAlgorithm {
	
	public CodingBlock getCodingBlock();
	
	public void setEvaluationCriteria(IEvaluationCriteria criteria);
	
	public void setActualFrame(Frame frame);
	public void setReferenceFrame(Frame frame);
	
	public void setSearchArea(int width, int height);
	public void setCodingBlock(int width, int height);
	
	public MotionEstimationVector run();

}
