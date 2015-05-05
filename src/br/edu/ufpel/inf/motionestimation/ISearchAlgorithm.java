package br.edu.ufpel.inf.motionestimation;

import br.edu.ufpel.inf.utils.Position;

public interface ISearchAlgorithm {
	
	// Getters
	public Position getActualBlockPosition();
	
	public int getBlockWidth();
	public int getBlockHeight();
	
	public Position getSearchAreaPosition();
	
	public int getSearchWidth();
	public int getSearchHeight();
	
	public MotionEstimationResult getResult();
	
	// Setters
	public void setActualFrame(int[][] frame);
	public void setReferenceFrame(int[][] frame);
	
	public void setActualBlockPosition(int x, int y);
	
	public void setBlockWidth(int width);
	public void setBlockHeight(int height);
	
	public void setSearchAreaPosition(int x, int y);
	
	public void setSearchWidth(int width);
	public void setSearchHeight(int height);
	
	// Other methods
	public MotionEstimationResult run();

}
