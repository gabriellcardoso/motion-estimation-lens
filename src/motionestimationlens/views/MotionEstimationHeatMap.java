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
			vector.getPosition().getX(),
			vector.getPosition().getY()
		);
	}
	
	public void setResultBlock(MotionEstimationVector vector) {
		setBlock(
			Color.YELLOW,
			vector.getPosition().getX(),
			vector.getPosition().getY()
		);
	}
	
	private void setBlock(Color color, int x, int y) {
		int centerX, centerY;
		
		drawData();
		
		centerX = bufferedImage.getWidth() / 2;
		centerY = bufferedImage.getHeight() / 2;
		
		System.out.println(bufferedImage.getWidth());
		System.out.println(bufferedImage.getHeight());
		
		x = centerX + x;
		y = centerY + y;
		
		bufferedGraphics = bufferedImage.createGraphics();
	    bufferedGraphics.setColor(color);
	    bufferedGraphics.fillRect(x, y, 1, 1);
	
		repaint();
	}
	
}
