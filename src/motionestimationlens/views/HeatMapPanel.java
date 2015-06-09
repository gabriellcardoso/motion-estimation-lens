package motionestimationlens.views;

import java.awt.Color;
import java.awt.image.BufferedImage;

import motionestimationlens.models.MotionEstimationVector;

import org.mblecker.heatmap.Gradient;
import org.mblecker.heatmap.HeatMap;

public class HeatMapPanel extends HeatMap {
	
	private static final Color[] GRADIENT_COLORS = Gradient.createMultiGradient(new Color[]{new Color(181, 32, 255), Color.blue, Color.green, Color.yellow, Color.orange, Color.red}, 5000);
	private static final boolean USE_GRAPHICS_Y_AXIS = true;

	public HeatMapPanel(double[][] data) {
		super(data, USE_GRAPHICS_Y_AXIS, GRADIENT_COLORS);
		setDrawLegend(true);
	}
	
	public void updateData(double[][] data) {
		updateData(data, USE_GRAPHICS_Y_AXIS);
	}
	
	@Override
	protected void updateDataColors()
    {
        //We need to find the range of the data values,
        // in order to assign proper colors.
        double largest = Double.MIN_VALUE;
        double smallest = Double.MAX_VALUE;
        for (int x = 0; x < data.length; x++)
        {
            for (int y = 0; y < data[0].length; y++)
            {
                largest = Math.max(data[x][y], largest);
                smallest = Math.min(data[x][y], smallest);
            }
        }
        double range = largest - smallest;

        // dataColorIndices is the same size as the data array
        // It stores an int index into the color array
        dataColorIndices = new int[data.length][data[0].length];    

        //assign a Color to each data point
        for (int x = 0; x < data.length; x++)
        {
            for (int y = 0; y < data[0].length; y++)
            {
                double norm = (data[x][y] - smallest) / range; // 0 < norm < 1
                int colorIndex = (int) Math.floor(norm * (colors.length - 1));
                dataColorIndices[x][y] = colorIndex;
            }
        }
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
