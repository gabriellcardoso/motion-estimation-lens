package motionestimationlens.views;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.mblecker.heatmap.HeatMap;

public class EditableHeatMap extends HeatMap {

	public EditableHeatMap(double[][] data, boolean useGraphicsYAxis, Color[] colors) {
		super(data, useGraphicsYAxis, colors);
	}
	
	public void setPixel(Color color, int x, int y) {
		drawData();
		
	    bufferedGraphics.setColor(color);
	    bufferedGraphics.fillRect(x, y, 1, 1);
	
		repaint();
	}

}
