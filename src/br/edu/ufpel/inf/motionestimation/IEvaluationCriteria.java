package br.edu.ufpel.inf.motionestimation;

import br.edu.ufpel.inf.utils.CodingBlock;
import br.edu.ufpel.inf.utils.Frame;

public interface IEvaluationCriteria {
	
	public int calculate(int positionX, int positionY);
	
	public void setActualFrame(Frame frame);
	public void setReferenceFrame(Frame frame);
	
	public void setCodingBlock(CodingBlock block);

}
