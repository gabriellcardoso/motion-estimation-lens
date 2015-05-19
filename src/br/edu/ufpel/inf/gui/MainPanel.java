package br.edu.ufpel.inf.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.mblecker.heatmap.Gradient;
import org.mblecker.heatmap.HeatMap;

public class MainPanel extends JPanel {
	
	private static final boolean USE_GRAPHICS_Y_AXIS = true;
	private static final Color[] GRADIENT_TYPE = Gradient.GRADIENT_BLUE_TO_RED;
	
	JPanel controlPanel;
	
	JButton previousFrameButton;
	JButton nextFrameButton;
	JButton previousBlockButton;
	JButton nextBlockButton;
	
	HeatMap heatMapPanel;
	
	JPanel resultsPanel;
	
	JLabel framesLabel;
	JLabel blocksLabel;
	JLabel numberOfBlocksLabel;
	JLabel bestVectorLabel;
	JLabel bestSadLabel;
	JLabel vectorLabel;
	JLabel sadLabel;
	
	JPanel footerPanel;
	
	JButton backButton;
	
	public MainPanel() {
		super();
		
		setLayout(new GridBagLayout());
		
		createControlPanel();
		createHeatMapPanel();
		createResultsPanel();
		createFooterPanel();

		addControlPanel();
		addHeatMapPanel();
		addResultsPanel();
		addFooterPanel();
    }
	
	private void createControlPanel() {
		controlPanel = new JPanel(); 
		controlPanel.setLayout(new GridBagLayout());
		
		previousFrameButton = new JButton("Quadro anterior");
		previousBlockButton = new JButton("Bloco anterior");
		nextBlockButton = new JButton("Próximo bloco");
		nextFrameButton = new JButton("Próximo quadro");
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
    	constraints.insets = new Insets(5, 5, 5, 5);
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		controlPanel.add(previousFrameButton, constraints);
		
		constraints.gridx = 1;
    	constraints.gridy = 0;
    	controlPanel.add(previousBlockButton, constraints);
    	
    	constraints.gridx = 8;
    	constraints.gridy = 0;
    	controlPanel.add(nextBlockButton, constraints);
		
		constraints.gridx = 9;
		constraints.gridy = 0;
		controlPanel.add(nextFrameButton, constraints);
	}
	
    private void createHeatMapPanel() {
        heatMapPanel = new HeatMap(null, USE_GRAPHICS_Y_AXIS, GRADIENT_TYPE);
        heatMapPanel.setPreferredSize(new Dimension(350, 350));
        
        heatMapPanel.setDrawLegend(false);

        heatMapPanel.setTitle("Mapa de calor da área de busca");
        heatMapPanel.setDrawTitle(true);

        heatMapPanel.setXAxisTitle("X Axis");
        heatMapPanel.setDrawXAxisTitle(false);

        heatMapPanel.setYAxisTitle("Y Axis");
        heatMapPanel.setDrawYAxisTitle(false);

        heatMapPanel.setCoordinateBounds(0, 0, 0, 0);
        heatMapPanel.setDrawXTicks(false);
        heatMapPanel.setDrawYTicks(false);

        heatMapPanel.setColorForeground(Color.black);
    }
    
    private void createResultsPanel() {
    	Border border = BorderFactory.createTitledBorder(
    						"Resultados da Estimação Movimento",
    						new EmptyBorder(10, 10, 10, 10)
    					);
    	
    	resultsPanel = new JPanel();
    	resultsPanel.setPreferredSize(new Dimension(350, 350));
    	resultsPanel.setLayout(new GridBagLayout());
    	resultsPanel.setBorder(border);
    	
    	framesLabel = new JLabel("Quadro: 0/0");
    	blocksLabel = new JLabel("Bloco: 0/0");
    	numberOfBlocksLabel = new JLabel("Número de blocos candidatos: 0");
    	bestVectorLabel = new JLabel("Melhor bloco candidato: x, y");
    	bestSadLabel = new JLabel("SAD do melhor bloco candidato: 0");
    	vectorLabel = new JLabel("Bloco escolhido pelo algoritmo: x, y");
    	sadLabel = new JLabel("SAD do bloco escolhido pelo algoritmo: 0");
    	
    	GridBagConstraints constraints = new GridBagConstraints();
    	constraints.anchor = GridBagConstraints.NORTH;
    	constraints.fill = GridBagConstraints.BOTH;
    	constraints.insets = new Insets(5, 5, 5, 5);
    	
    	constraints.gridx = 0;
    	constraints.gridy = 0;
    	resultsPanel.add(framesLabel, constraints);
    	
    	constraints.gridx = 0;
    	constraints.gridy = 1;
    	resultsPanel.add(blocksLabel, constraints);
    	
    	constraints.gridx = 0;
    	constraints.gridy = 2;
    	resultsPanel.add(numberOfBlocksLabel, constraints);
    	
    	constraints.gridx = 0;
    	constraints.gridy = 3;
    	resultsPanel.add(bestVectorLabel, constraints);
    	
    	constraints.gridx = 0;
    	constraints.gridy = 4;
    	resultsPanel.add(bestSadLabel, constraints);
    	
    	constraints.gridx = 0;
    	constraints.gridy = 5;
    	resultsPanel.add(vectorLabel, constraints);
    	
    	constraints.gridx = 0;
    	constraints.gridy = 6;
    	resultsPanel.add(sadLabel, constraints);
    }
    
    private void createFooterPanel() {
    	footerPanel = new JPanel();
    	footerPanel.setLayout(new GridBagLayout());
    	
    	backButton = new JButton("Voltar para configurações");
    	
    	GridBagConstraints constraints = new GridBagConstraints();
    	constraints.anchor = GridBagConstraints.PAGE_END;
    	constraints.fill = GridBagConstraints.BOTH;
    	constraints.insets = new Insets(5, 5, 5, 5);
    	constraints.gridx = 0;
    	constraints.gridy = 9;
    	
    	footerPanel.add(backButton, constraints);
    }
	
	private void addControlPanel() {
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.anchor = GridBagConstraints.PAGE_START;
		constraints.fill = GridBagConstraints.BOTH;
    	constraints.insets = new Insets(5, 5, 5, 5);
    	constraints.gridx = 0;
    	constraints.gridx = 0;
    	constraints.gridwidth = 2;
    	
		this.add(controlPanel, constraints);
	}
	
	private void addHeatMapPanel() {
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.fill = GridBagConstraints.BOTH;
    	constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridx = 0;
        constraints.gridy = 2;
        
        this.add(heatMapPanel, constraints);
	}
	
	private void addResultsPanel() {
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.fill = GridBagConstraints.BOTH;
    	constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridx = 1;
        constraints.gridy = 2;
        
        this.add(resultsPanel, constraints);
	}
	
	private void addFooterPanel() {
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.fill = GridBagConstraints.BOTH;
    	constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        
        this.add(footerPanel, constraints);
	}
	
	public void setPreviousFrameButtonListener(ActionListener listener) {
    	previousFrameButton.addActionListener(listener);
    }
    
    public void setPreviousFrameButtonState(boolean state) {
    	previousFrameButton.setEnabled(state);
    }
    
    public void setPreviousBlockButtonListener(ActionListener listener) {
    	previousBlockButton.addActionListener(listener);
    }
    
    public void setPreviousBlockButtonState(boolean state) {
    	previousBlockButton.setEnabled(state);
    }
	
    public void setNextBlockButtonListener(ActionListener listener) {
    	nextBlockButton.addActionListener(listener);
    }
    
    public void setNextBlockButtonState(boolean state) {
    	nextBlockButton.setEnabled(state);
    }
    
    public void setNextFrameButtonListener(ActionListener listener) {
    	nextFrameButton.addActionListener(listener);
    }
    
    public void setNextFrameButtonState(boolean state) {
    	nextFrameButton.setEnabled(state);
    }
    
    public void setBackButtonListener(ActionListener listener) {
    	backButton.addActionListener(listener);
    }
    
    public void updateHeatMap(double[][] data) {
    	heatMapPanel.updateData(data, USE_GRAPHICS_Y_AXIS);
    }
    
}
