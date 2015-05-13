package br.edu.ufpel.inf.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.mblecker.heatmap.Gradient;
import org.mblecker.heatmap.HeatMap;

public class MainPanel extends JPanel {
	
	private static final boolean USE_GRAPHICS_Y_AXIS = true;
	private static final Color[] GRADIENT_TYPE = Gradient.GRADIENT_BLUE_TO_RED;
	
	HeatMap heatMapPanel;
	JButton nextBlockButton;
	JButton backButton;
	
	public MainPanel() {
		super();
		
		setLayout(new BorderLayout());
		
        createHeatMapPanel();
        createNextBlockButton();
        createBackButton();
    }
    
    private void createHeatMapPanel() {
        heatMapPanel = new HeatMap(null, USE_GRAPHICS_Y_AXIS, GRADIENT_TYPE);
        
        heatMapPanel.setDrawLegend(false);

        heatMapPanel.setTitle("Search Area");
        heatMapPanel.setDrawTitle(true);

        heatMapPanel.setXAxisTitle("X Axis");
        heatMapPanel.setDrawXAxisTitle(false);

        heatMapPanel.setYAxisTitle("Y Axis");
        heatMapPanel.setDrawYAxisTitle(false);

        heatMapPanel.setCoordinateBounds(0, 0, 0, 0);
        heatMapPanel.setDrawXTicks(false);
        heatMapPanel.setDrawYTicks(false);

        heatMapPanel.setColorForeground(Color.black);

        this.add(heatMapPanel, BorderLayout.CENTER);
    }
    
    private void createNextBlockButton() {
    	nextBlockButton = new JButton("Next block");
    	this.add(nextBlockButton, BorderLayout.NORTH);
    }
    
    private void createBackButton() {
    	backButton = new JButton("Back to setup");
    	this.add(backButton, BorderLayout.SOUTH);
    }
    
    public void setNextBlockButtonListener(ActionListener listener) {
    	nextBlockButton.addActionListener(listener);
    }
    
    public void setNextBlockButtonState(boolean state) {
    	nextBlockButton.setEnabled(state);
    }
    
    public void setBackButtonListener(ActionListener listener) {
    	backButton.addActionListener(listener);
    }
    
    public void updateHeatMap(double[][] heatMap) {
    	heatMapPanel.updateData(heatMap, USE_GRAPHICS_Y_AXIS);
    }
    
}
