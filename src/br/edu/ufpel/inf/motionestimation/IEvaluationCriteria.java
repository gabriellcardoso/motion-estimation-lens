package br.edu.ufpel.inf.motionestimation;

import br.edu.ufpel.inf.utils.Position;

public interface IEvaluationCriteria {
	
	// Setters
	public void setActualFrame(byte[][] frame);
	public void setReferenceFrame(byte [][] frame);
	
	public void setActualBlockPosition(Position position);
	
	public void setBlockWidth(int width);
	public void setBlockHeight(int height);
	
	// Other methods
	public double calculate(int x, int y);

}
