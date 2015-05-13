package br.edu.ufpel.inf.motionestimation;

import br.edu.ufpel.inf.utils.CodingBlock;
import br.edu.ufpel.inf.utils.Frame;

public interface IEvaluationCriteria {
	
	public void setActualFrame(Frame frame);
	public void setReferenceFrame(Frame frame);
	public void setCodingBlock(CodingBlock block);
	
	public int calculate(int positionX, int positionY);
	public double[][] createHeatMap(int width, int height);

}
