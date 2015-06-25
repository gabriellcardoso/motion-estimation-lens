package motionestimationlens.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import motionestimationlens.models.MotionEstimationVector;

import org.mblecker.heatmap.Gradient;
import org.mblecker.heatmap.HeatMap;

public class HeatMapPanel extends HeatMap {
	
	private static final Color[] GRADIENT_COLORS = Gradient.createMultiGradient(new Color[]{ Color.BLACK, new Color(102, 0, 205), new Color(163, 16, 148), Color.RED, Color.ORANGE, Color.YELLOW}, 5000);
	private static final boolean USE_GRAPHICS_Y_AXIS = true;

	public HeatMapPanel(double[][] data) {
		super(data, USE_GRAPHICS_Y_AXIS, GRADIENT_COLORS);
	}
	
	public void updateData(double[][] data) {
		updateData(data, USE_GRAPHICS_Y_AXIS);
	}
	
	public void setBlock(MotionEstimationVector vector, Color color) {
		int imageHeight = bufferedImage.getHeight();
		int imageWidth = bufferedImage.getWidth();
		
		int x = vector.getHeatMapPosition().getX();
		int y = vector.getHeatMapPosition().getY();
		
		int crossSize, crossStrokeWidth, startingShift;

		bufferedGraphics = bufferedImage.createGraphics();
	    bufferedGraphics.setColor(color);
	    
	    crossSize = 3;
	    crossStrokeWidth = 1;
	    
	    if (imageWidth >= 200 && imageHeight >= 200) {
	    	crossStrokeWidth = 3;
	    	crossSize = 9;
	    }
	    else if (imageWidth >= 50 && imageHeight >= 50) {
	    	crossSize = 5;
	    }
	    
	    if (imageWidth >= 200 && imageHeight >= 200) {
	    	startingShift = (int) Math.floor(crossSize / 2) + (int) Math.floor(crossStrokeWidth / 2) - 2;
	    }
	    else {
	    	startingShift = (int) Math.floor(crossSize / 2) + (int) Math.floor(crossStrokeWidth / 2);
	    }
	    
	    bufferedGraphics.fillRect(x - startingShift, y, crossSize, crossStrokeWidth);
	    bufferedGraphics.fillRect(x, y - startingShift, crossStrokeWidth, crossSize);
	    
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Graphics2D g2d = (Graphics2D) g;
	    
	    int width = this.getWidth();
	    int height = this.getHeight();
	    
	    int imageWidth, imageHeight;
        int borderWidth, borderHeight;
        double proportion;
	    
	    this.setOpaque(true);
	
	    // clear the panel
	    g2d.setColor(bg);
	    g2d.fillRect(0, 0, width, height);
	
	    // draw the heat map
	    if (bufferedImage == null)
	    {
	        // Ideally, we only to call drawData in the constructor, or if we
	        // change the data or gradients. We include this just to be safe.
	        drawData();
	    }

	    imageWidth = bufferedImage.getWidth();
        imageHeight = bufferedImage.getHeight();
        
        if (width < height) {
        	borderWidth = 30;
        	proportion = (double) imageWidth / (double) (width - borderWidth * 2);
	    	borderHeight = (int) (height - imageHeight / proportion) / 2;
	    } else {
	    	borderHeight = 30;
	    	proportion = (double) imageHeight / (double) (height - borderHeight * 2);
	    	borderWidth = (int) (width - imageWidth / proportion) / 2;
	    }
	    
	    // The data plot itself is drawn with 1 pixel per data point, and the
	    // drawImage method scales that up to fit our current window size. This
	    // is very fast, and is much faster than the previous version, which 
	    // redrew the data plot each time we had to repaint the screen.
	    g2d.drawImage(bufferedImage,
	                  borderWidth - 1, borderHeight -1,
	                  width - borderWidth,
	                  height - borderHeight,
	                  0, 0,
	                  bufferedImage.getWidth(), bufferedImage.getHeight(),
	                  null);
	
	    // border
	    g2d.setColor(fg);
	    g2d.drawRect(borderWidth, borderHeight, width - borderWidth * 2, height - borderHeight * 2);
	    
	    // title
	    if (drawTitle && title != null)
	    {
	        g2d.drawString(title, (width / 2) - 4 * title.length(), 20);
	    }
	}
	
}
