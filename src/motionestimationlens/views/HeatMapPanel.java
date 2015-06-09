package motionestimationlens.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import motionestimationlens.models.MotionEstimationVector;

import org.mblecker.heatmap.Gradient;
import org.mblecker.heatmap.HeatMap;

public class HeatMapPanel extends HeatMap {
	
	private static final Color[] GRADIENT_COLORS = Gradient.createMultiGradient(new Color[]{ Color.BLACK, new Color(102, 0, 205), new Color(163, 16, 148), Color.RED, Color.ORANGE, Color.YELLOW}, 5000);
	private static final boolean USE_GRAPHICS_Y_AXIS = true;

	public HeatMapPanel(double[][] data) {
		super(data, USE_GRAPHICS_Y_AXIS, GRADIENT_COLORS);
		setDrawLegend(true);
	}
	
	public void updateData(double[][] data) {
		updateData(data, USE_GRAPHICS_Y_AXIS);
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
		bufferedGraphics = bufferedImage.createGraphics();
	    bufferedGraphics.setColor(color);
	    bufferedGraphics.fillRect(x, y, 1, 1);
	    
		repaint();
	}
	
}
