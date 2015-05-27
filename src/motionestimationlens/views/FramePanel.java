package motionestimationlens.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import motionestimationlens.models.Frame;

public class FramePanel extends JPanel {
	 	
		private String title;
		private Frame frame;

	    private boolean drawTitle = false;

	    private Color bg = Color.white;
	    private Color fg = Color.black;

	    protected BufferedImage bufferedImage;
	    protected Graphics2D bufferedGraphics;
	    
	    public FramePanel(Frame frame)
	    {
	        super();

	        if (frame != null) {
	        	setFrame(frame);
	        	this.setPreferredSize(new Dimension(60 + frame.getWidth(), 60 + frame.getHeight()));
	        }

	        this.setDoubleBuffered(true);

	        this.bg = Color.white;
	        this.fg = Color.black;
	        
	        // this is the expensive function that draws the data plot into a 
	        // BufferedImage. The data plot is then cheaply drawn to the screen when
	        // needed, saving us a lot of time in the end.
	        if (frame != null) {
	        	drawFrame();
	        }
	    }

	    public void setTitle(String title)
	    {
	        this.title = title;
	        
	        repaint();
	    }

	    public void setDrawTitle(boolean drawTitle)
	    {
	        this.drawTitle = drawTitle;
	        
	        repaint();
	    }

	    public void setColorForeground(Color fg)
	    {
	        this.fg = fg;

	        repaint();
	    }

	    public void setColorBackground(Color bg)
	    {
	        this.bg = bg;

	        repaint();
	    }

	    public void setFrame(Frame frame)
	    {
	        this.frame = frame;

	        updateFrame();
	    }
	    
	    public void setCodingBlock(int width, int height, int x, int y) {
	    	
	    }
	    
	    public void setSearchArea(int width, int height, int x, int y) {
	    	
	    }
	    
	    public void updateFrame() {
	    	drawFrame();

	        repaint();
	    }
	    
	    protected void drawFrame()
	    {
	        bufferedImage = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_ARGB);
	        bufferedGraphics = bufferedImage.createGraphics();
	        
	        byte[][] image = frame.getImage();
	        int r, g, b;
	        
	        for (int y = 0; y < frame.getHeight(); y++)
	        {
	            for (int x = 0; x < frame.getWidth(); x++)
	            {
	            	r = Math.abs(image[y][x]);
	            	g = Math.abs(image[y][x]);
	            	b = Math.abs(image[y][x]);
	            	
	                bufferedGraphics.setColor(new Color((int) r, (int) g, (int) b));
	                bufferedGraphics.fillRect(x, y, 1, 1);
	            }
	        }
	    }

	    public void paintComponent(Graphics g)
	    {
	        super.paintComponent(g);
	        Graphics2D g2d = (Graphics2D) g;
	        
	        int width = this.getWidth();
	        int height = this.getHeight();
	        
	        this.setOpaque(true);

	        // clear the panel
	        g2d.setColor(bg);
	        g2d.fillRect(0, 0, width, height);

	        // draw the heat map
	        if (bufferedImage == null)
	        {
	            // Ideally, we only to call drawData in the constructor, or if we
	            // change the data or gradients. We include this just to be safe.
	            drawFrame();
	        }
	        
	        // The data plot itself is drawn with 1 pixel per data point, and the
	        // drawImage method scales that up to fit our current window size. This
	        // is very fast, and is much faster than the previous version, which 
	        // redrew the data plot each time we had to repaint the screen.
	        g2d.drawImage(bufferedImage,
	                      31, 31,
	                      width - 30,
	                      height - 30,
	                      0, 0,
	                      bufferedImage.getWidth(), bufferedImage.getHeight(),
	                      null);

	        // border
	        g2d.setColor(fg);
	        g2d.drawRect(30, 30, width - 60, height - 60);
	        
	        // title
	        if (drawTitle && title != null)
	        {
	            g2d.drawString(title, (width / 2) - 4 * title.length(), 20);
	        }
	    }
}
