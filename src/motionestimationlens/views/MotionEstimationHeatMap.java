package motionestimationlens.views;

import java.awt.Color;
import java.awt.image.BufferedImage;

import motionestimationlens.models.MotionEstimationVector;

import org.mblecker.heatmap.Gradient;
import org.mblecker.heatmap.HeatMap;

public class MotionEstimationHeatMap extends HeatMap {
	
	private static final Color[] GRADIENT_COLORS = Gradient.createGradient(Color.RED, Color.BLUE, 255);

	public MotionEstimationHeatMap(double[][] data, boolean useGraphicsYAxis) {
		super(data, useGraphicsYAxis, GRADIENT_COLORS);
	}
	
	public void setBestBlock(MotionEstimationVector vector) {
		setBlock(
			Color.GREEN,
			vector.getHeatMapPosition().getX(),
			vector.getHeatMapPosition().getY()
		);
	}
	
	public void setResultBlock(MotionEstimationVector vector) {
		setBlock(
			Color.YELLOW,
			vector.getHeatMapPosition().getX(),
			vector.getHeatMapPosition().getY()
		);
	}
	
	private void setBlock(Color color, int x, int y) {
		drawData();
		
		bufferedGraphics = bufferedImage.createGraphics();
	    bufferedGraphics.setColor(color);
	    bufferedGraphics.fillRect(x, y, 1, 1);
	    
		repaint();
	}
	
}
