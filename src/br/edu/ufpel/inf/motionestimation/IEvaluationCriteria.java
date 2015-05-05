package br.edu.ufpel.inf.motionestimation;

public interface IEvaluationCriteria { 
	
	public int getBlockWidth();
	public int getBlockHeight();
	
	public int getSearchWidth();
	public int getSearchHeight();
	
	public int[][] getSearchArea();
	public int[][] getActualBlock();
	
	public void setBlockWidth(int width);
	public void setBlockHeight(int height);
	
	public void setSearchWidth(int width);
	public void setSearchHeight(int height);
	
	public void setSearchArea(int[][] searchArea);
	public void setActualBlock(int [][] actualBlock);
	
	public int calculate(int x, int y);

}
