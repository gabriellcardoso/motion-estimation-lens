package motionestimationlens.models;

public class MotionEstimationData {

	private MotionEstimationVector resultVector;
	private double[][] heatMap;
	private int candidateBlocksTotal;
	
	public MotionEstimationVector getResultVector() {
		return resultVector;
	}
	
	public double[][] getHeatMap() {
		return heatMap;
	}
	
	public int getCandidateBlocksTotal() {
		return candidateBlocksTotal;
	}
	
	public void setResultVector(MotionEstimationVector vector) {
		resultVector = vector;
	}
	
	public void setHeatMap(double[][] data) {
		heatMap = data;
	}
	
	public void setCandidateBlocksTotal(int total) {
		candidateBlocksTotal = total;
	}
	
}
