package motionestimationlens.views;

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

import motionestimationlens.utils.BorderFactory;

import org.mblecker.heatmap.Gradient;
import org.mblecker.heatmap.HeatMap;

import com.sun.corba.se.spi.orbutil.fsm.Action;

public class MainPanel extends JPanel {
	
	private static final boolean USE_GRAPHICS_Y_AXIS = true;
	private static final Color[] GRADIENT_TYPE = Gradient.GRADIENT_BLUE_TO_RED;
	
	private JPanel controlPanel;
	
	private JButton btnPreviousFrame;
	private JButton btnNextFrame;
	private JButton btnPreviousBlock;
	private JButton btnNextBlock;
	
	private HeatMap heatMapPanel;
	
	private JPanel resultsPanel;
	
	private JLabel lblActualFrame;
	private JLabel lblReferenceFrame;
	private JLabel lblCodingBlock;
	private JLabel lblNumberOfBlocks;
	private JLabel lblBestVector;
	private JLabel lblBestSad;
	private JLabel lblVector;
	private JLabel lblSad;
	
	private JPanel footerPanel;
	
	private JButton btnSetActualFrame;
	private JButton btnSetReferenceFrame;
	private JButton btnSetCodingBlock;
	private JButton btnBackToSetup;
	
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
		
		btnPreviousFrame = new JButton("Quadro anterior");
		btnPreviousBlock = new JButton("Bloco anterior");
		btnNextBlock = new JButton("Próximo bloco");
		btnNextFrame = new JButton("Próximo quadro");
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
    	constraints.insets = new Insets(5, 5, 5, 5);
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		btnPreviousFrame.setEnabled(false);
		controlPanel.add(btnPreviousFrame, constraints);
		
		constraints.gridx = 1;
    	constraints.gridy = 0;
    	btnPreviousBlock.setEnabled(false);
    	controlPanel.add(btnPreviousBlock, constraints);
    	
    	constraints.gridx = 8;
    	constraints.gridy = 0;
    	controlPanel.add(btnNextBlock, constraints);
		
		constraints.gridx = 9;
		constraints.gridy = 0;
		btnNextFrame.setEnabled(false);
		controlPanel.add(btnNextFrame, constraints);
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
    	
    	lblReferenceFrame = new JLabel("Quadro de referência: 0/0");
    	lblActualFrame = new JLabel("Quadro atual: 0/0");
    	lblCodingBlock = new JLabel("Bloco: 0/0");
    	lblNumberOfBlocks = new JLabel("Número de blocos candidatos: 0");
    	lblBestVector = new JLabel("Melhor bloco candidato: x, y");
    	lblBestSad = new JLabel("SAD do melhor bloco candidato: 0");
    	lblVector = new JLabel("Bloco escolhido pelo algoritmo: x, y");
    	lblSad = new JLabel("SAD do bloco escolhido pelo algoritmo: 0");
    	
    	GridBagConstraints constraints = new GridBagConstraints();
    	constraints.anchor = GridBagConstraints.NORTH;
    	constraints.fill = GridBagConstraints.BOTH;
    	constraints.insets = new Insets(5, 5, 5, 5);
    	
    	constraints.gridx = 0;
    	constraints.gridy = 0;
    	resultsPanel.add(lblReferenceFrame, constraints);
    	
    	constraints.gridx = 0;
    	constraints.gridy = 1;
    	resultsPanel.add(lblActualFrame, constraints);
    	
    	constraints.gridx = 0;
    	constraints.gridy = 2;
    	resultsPanel.add(lblCodingBlock, constraints);
    	
    	constraints.gridx = 0;
    	constraints.gridy = 3;
    	resultsPanel.add(lblNumberOfBlocks, constraints);
    	
    	constraints.gridx = 0;
    	constraints.gridy = 4;
    	resultsPanel.add(lblBestVector, constraints);
    	
    	constraints.gridx = 0;
    	constraints.gridy = 5;
    	resultsPanel.add(lblBestSad, constraints);
    	
    	constraints.gridx = 0;
    	constraints.gridy = 6;
    	resultsPanel.add(lblVector, constraints);
    	
    	constraints.gridx = 0;
    	constraints.gridy = 7;
    	resultsPanel.add(lblSad, constraints);
    }
    
    private void createFooterPanel() {
    	footerPanel = new JPanel();
    	footerPanel.setLayout(new GridBagLayout());
    	
    	btnSetReferenceFrame = new JButton("Especificar quadro de referência");
    	btnSetActualFrame = new JButton("Especificar quadro atual");
    	btnSetCodingBlock = new JButton("Especificar bloco a ser codificado");
    	btnBackToSetup = new JButton("Voltar para configurações");
    	
    	GridBagConstraints constraints = new GridBagConstraints();
    	constraints.anchor = GridBagConstraints.PAGE_END;
    	constraints.fill = GridBagConstraints.BOTH;
    	constraints.insets = new Insets(5, 5, 5, 5);
    	
    	constraints.gridx = 0;
    	constraints.gridy = 0;
    	btnSetReferenceFrame.setEnabled(false);
    	footerPanel.add(btnSetReferenceFrame, constraints);

    	constraints.gridx = 1;
    	constraints.gridy = 0;
    	btnSetActualFrame.setEnabled(false);
    	footerPanel.add(btnSetActualFrame, constraints);
    	
    	constraints.gridx = 2;
    	constraints.gridy = 0;
    	btnSetCodingBlock.setEnabled(false);
    	footerPanel.add(btnSetCodingBlock, constraints);
    	
    	constraints.gridx = 3;
    	constraints.gridy = 0;
    	footerPanel.add(btnBackToSetup, constraints);
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
	
	public void setBtnPreviousFrameListener(ActionListener listener) {
    	btnPreviousFrame.addActionListener(listener);
    }
    
    public void setBtnPreviousFrameState(boolean state) {
    	btnPreviousFrame.setEnabled(state);
    }
    
    public void setBtnPreviousBlockListener(ActionListener listener) {
    	btnPreviousBlock.addActionListener(listener);
    }
    
    public void setBtnPreviousBlockState(boolean state) {
    	btnPreviousBlock.setEnabled(state);
    }
	
    public void setBtnNextBlockListener(ActionListener listener) {
    	btnNextBlock.addActionListener(listener);
    }
    
    public void setBtnNextBlockState(boolean state) {
    	btnNextBlock.setEnabled(state);
    }
    
    public void setBtnNextFrameListener(ActionListener listener) {
    	btnNextFrame.addActionListener(listener);
    }
    
    public void setBtnNextFrameState(boolean state) {
    	btnNextFrame.setEnabled(state);
    }
    
    public void setBtnSetActualFrame(ActionListener listener) {
    	btnSetActualFrame.addActionListener(listener);
    }
    
    public void setBtnSetReferenceFrame(ActionListener listener) {
    	btnSetReferenceFrame.addActionListener(listener);
    }
    
    public void setBtnSetCodingBlock(ActionListener listener) {
    	btnSetCodingBlock.addActionListener(listener);
    }
    
    public void setBtnBackToSetupListener(ActionListener listener) {
    	btnBackToSetup.addActionListener(listener);
    }
    
    public void updateHeatMap(double[][] data) {
    	heatMapPanel.updateData(data, USE_GRAPHICS_Y_AXIS);
    }
    
}
