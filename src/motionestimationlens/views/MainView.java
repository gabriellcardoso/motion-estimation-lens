package motionestimationlens.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import motionestimationlens.models.CodingBlock;
import motionestimationlens.models.Frame;
import motionestimationlens.models.MotionEstimationVector;
import motionestimationlens.models.Position;
import motionestimationlens.utils.BorderFactory;
import motionestimationlens.utils.ME;

public class MainView extends JPanel {
	
	private JPanel controlPanel;
		
		private JButton btnPreviousFrame;
		private JButton btnNextFrame;
		private JButton btnPreviousBlock;
		private JButton btnNextBlock;
	
	private FramePanel actualFramePanel;
	
	private FramePanel referenceFramePanel;
	
	private HeatMapPanel heatMapPanel;
	
	private JPanel resultsPanel;
	
		private JLabel lblActualFrame;
		private JLabel lblReferenceFrame;
		private JLabel lblCodingBlock;
		private JLabel lblNumberOfBlocks;
		private JLabel lblBestVector;
		private JLabel lblBestSad;
		private JLabel lblNumberOfBlocksVisited;
		private JLabel lblVector;
		private JLabel lblSad;
	
	private JPanel footerPanel;
	
		private JButton btnSetActualFrame;
		private JButton btnSetReferenceFrame;
		private JButton btnSetCodingBlock;
		private JButton btnBackToSetup;
	
	public MainView() {
		super();
		
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new BorderLayout(10, 10));
		
		createControlPanel();
		createReferenceFramePanel();
		createActualFramePanel();
		createHeatMapPanel();
		createResultsPanel();
		createFooterPanel();

		addControlPanel();
		addMotionEstimationPanels();
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
		controlPanel.add(btnPreviousFrame, constraints);
		
		constraints.gridx = 1;
    	constraints.gridy = 0;
    	controlPanel.add(btnPreviousBlock, constraints);
    	
    	constraints.gridx = 8;
    	constraints.gridy = 0;
    	controlPanel.add(btnNextBlock, constraints);
		
		constraints.gridx = 9;
		constraints.gridy = 0;
		controlPanel.add(btnNextFrame, constraints);
	}
	
	private void createActualFramePanel() {
		actualFramePanel = new FramePanel(null);
		actualFramePanel.setTitle("Quadro atual");
		actualFramePanel.setDrawTitle(true);
	}
	
	private void createReferenceFramePanel() {
		referenceFramePanel = new FramePanel(null);
		referenceFramePanel.setTitle("Quadro de referência");
		referenceFramePanel.setDrawTitle(true);
	}
	
    private void createHeatMapPanel() {
        heatMapPanel = new HeatMapPanel(null);
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
    						"Resultados da Estima��o Movimento",
    						new EmptyBorder(10, 10, 10, 10)
    					);
    	
    	resultsPanel = new JPanel();
    	resultsPanel.setLayout(new GridLayout(0, 4, 5, 5));
    	resultsPanel.setBorder(border);
    	resultsPanel.setForeground(Color.WHITE);
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
    	footerPanel.add(btnSetReferenceFrame, constraints);

    	constraints.gridx = 1;
    	constraints.gridy = 0;
    	footerPanel.add(btnSetActualFrame, constraints);
    	
    	constraints.gridx = 2;
    	constraints.gridy = 0;
    	footerPanel.add(btnSetCodingBlock, constraints);
    	
    	constraints.gridx = 3;
    	constraints.gridy = 0;
    	footerPanel.add(btnBackToSetup, constraints);
    }
	
	private void addControlPanel() {
		this.add(controlPanel, BorderLayout.PAGE_START);
	}
	
	private void addMotionEstimationPanels() {
		JPanel motionEstimationPanels = new JPanel(new GridLayout(0, 2, 5, 5));
		
		motionEstimationPanels.add(this.referenceFramePanel);
		motionEstimationPanels.add(this.actualFramePanel);
		motionEstimationPanels.add(this.heatMapPanel);
		motionEstimationPanels.add(this.resultsPanel);
		
		this.add(motionEstimationPanels, BorderLayout.CENTER);
	}
	
	private void addFooterPanel() {
		this.add(footerPanel, BorderLayout.PAGE_END);
	}
    
    public void setResults(int framesTotal, 
    						Frame referenceFrame, int referenceFrameIndex,
    						Frame actualFrame, int actualFrameIndex, 
    						int searchWidth, int searchHeight,
    						CodingBlock codingBlock,
    						int candidateBlocksTotal,
    						double[][] heatMap,
    						ArrayList<String> selectedAlgorithms, ArrayList<MotionEstimationVector> results) {
    	
    	int componentIndex = 0;
    	
    	Position blockPosition = codingBlock.getPosition();
    	int blockX = blockPosition.getX() / codingBlock.getWidth();
    	int blockY = blockPosition.getY() / codingBlock.getHeight();
    	
    	int resultsSize = selectedAlgorithms.size();
    	
    	int index;
    	String algorithm;
    	MotionEstimationVector result;
    	Color algorithmColor;
    	
    	JLabel algorithmLabel;
    	JLabel criteriaResultLabel;
    	JLabel blockVisitedLabel;
    	JLabel vectorLabel;
    	
    	referenceFramePanel.setFrame(actualFrame);
    	referenceFramePanel.setSearchArea(codingBlock, searchWidth, searchHeight);
    	
    	actualFramePanel.setFrame(referenceFrame);
    	actualFramePanel.setCodingBlock(codingBlock);
    	
    	heatMapPanel.updateData(heatMap);
    	
    	resultsPanel.removeAll();
    	
    	// Video estimation header info
    	resultsPanel.add(new JLabel("Quadro de refer�ncia", JLabel.CENTER), componentIndex++);
    	resultsPanel.add(new JLabel("Quadro atual", JLabel.CENTER), componentIndex++);
    	resultsPanel.add(new JLabel("Area de busca", JLabel.CENTER), componentIndex++);
    	resultsPanel.add(new JLabel("Bloco", JLabel.CENTER), componentIndex++);
    	
    	// Video estimation info
    	resultsPanel.add(new JLabel("" + referenceFrameIndex + "/" + framesTotal, JLabel.CENTER), componentIndex++);
    	resultsPanel.add(new JLabel("" + actualFrameIndex + "/" + framesTotal, JLabel.CENTER), componentIndex++);
    	resultsPanel.add(new JLabel("" + searchWidth + "x" + searchHeight, JLabel.CENTER), componentIndex++);
    	resultsPanel.add(new JLabel("[" + blockX + "," + blockY + "]", JLabel.CENTER), componentIndex++);
    	
    	resultsPanel.add(new JLabel("Blocos candidatos: " + candidateBlocksTotal, JLabel.CENTER), componentIndex++);
    	resultsPanel.add(new JLabel("", JLabel.CENTER), componentIndex++);
    	resultsPanel.add(new JLabel("", JLabel.CENTER), componentIndex++);
    	resultsPanel.add(new JLabel("", JLabel.CENTER), componentIndex++);
    	
    	// Algorithm header
    	resultsPanel.add(new JLabel("", JLabel.CENTER), componentIndex++);
    	resultsPanel.add(new JLabel("SAD", JLabel.CENTER), componentIndex++);
    	resultsPanel.add(new JLabel("Blocos comparados", JLabel.CENTER), componentIndex++);
    	resultsPanel.add(new JLabel("Vetor", JLabel.CENTER), componentIndex++);
    	
    	// Algorithm info
    	for (index = 0; index < resultsSize; index++) {
    		algorithm = selectedAlgorithms.get(index);
    		result = results.get(index);
    		
    		switch(algorithm) {
    		
    		case ME.FS:
    			algorithmColor = Color.GREEN;
    			break;
    			
    		case ME.DS:
    			algorithmColor = Color.YELLOW;
    			break;
    			
    		case ME.TSS:
    			algorithmColor = Color.CYAN;
    			break;
    			
    		default:
    			algorithmColor = Color.WHITE;
    		}
    		
    		heatMapPanel.setBlock(result, algorithmColor);
    		
    		algorithmLabel = new JLabel(algorithm, JLabel.CENTER);
    		algorithmLabel.setForeground(algorithmColor);
    		
    		criteriaResultLabel = new JLabel("" + result.getCriteriaResult(), JLabel.CENTER);
    		criteriaResultLabel.setForeground(algorithmColor);
    		
    		blockVisitedLabel = new JLabel("" + result.getBlocksVisited(), JLabel.CENTER);
    		blockVisitedLabel.setForeground(algorithmColor);
    		
    		vectorLabel = new JLabel("[" + result.getPosition().getX() + "," + result.getPosition().getY() + "]", JLabel.CENTER);
    		vectorLabel.setForeground(algorithmColor);
    		
    		resultsPanel.add(algorithmLabel, componentIndex++);
    		resultsPanel.add(criteriaResultLabel, componentIndex++);
    		resultsPanel.add(blockVisitedLabel, componentIndex++);
    		resultsPanel.add(vectorLabel, componentIndex++);
    	}
    
    	resultsPanel.revalidate();
    }
    
	public void setBtnPreviousFrameListener(ActionListener listener) {
    	btnPreviousFrame.addActionListener(listener);
    }
    
    public void setBtnPreviousFrameEnabled(boolean state) {
    	btnPreviousFrame.setEnabled(state);
    }
    
    public void setBtnPreviousBlockListener(ActionListener listener) {
    	btnPreviousBlock.addActionListener(listener);
    }
    
    public void setBtnPreviousBlockEnabled(boolean state) {
    	btnPreviousBlock.setEnabled(state);
    }
	
    public void setBtnNextBlockListener(ActionListener listener) {
    	btnNextBlock.addActionListener(listener);
    }

    public void setBtnNextBlockEnabled(boolean state) {
    	btnNextBlock.setEnabled(state);
    }
    
    public void setBtnNextFrameListener(ActionListener listener) {
    	btnNextFrame.addActionListener(listener);
    }
    
    public void setBtnNextFrameEnabled(boolean state) {
    	btnNextFrame.setEnabled(state);
    }
    
    public void setBtnSetActualFrameListener(ActionListener listener) {
    	btnSetActualFrame.addActionListener(listener);
    }
    
    public void setBtnSetReferenceFrameListener(ActionListener listener) {
    	btnSetReferenceFrame.addActionListener(listener);
    }
    
    public void setBtnSetCodingBlockListener(ActionListener listener) {
    	btnSetCodingBlock.addActionListener(listener);
    }
    
    public void setBtnBackToSetupListener(ActionListener listener) {
    	btnBackToSetup.addActionListener(listener);
    }
    
}
