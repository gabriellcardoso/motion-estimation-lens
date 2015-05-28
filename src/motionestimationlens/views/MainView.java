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

import motionestimationlens.models.CodingBlock;
import motionestimationlens.models.Frame;
import motionestimationlens.models.MotionEstimationVector;
import motionestimationlens.models.Position;
import motionestimationlens.utils.BorderFactory;

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
		
		setLayout(new GridBagLayout());
		
		createControlPanel();
		createReferenceFramePanel();
		createActualFramePanel();
		createHeatMapPanel();
		createResultsPanel();
		createFooterPanel();

		addControlPanel();
		addReferenceFramePanel();
		addActualFramePanel();
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
		actualFramePanel.setPreferredSize(new Dimension(350, 350));
		
		actualFramePanel.setTitle("Quadro atual");
		actualFramePanel.setDrawTitle(true);
	}
	
	private void createReferenceFramePanel() {
		referenceFramePanel = new FramePanel(null);
		referenceFramePanel.setPreferredSize(new Dimension(350, 350));
		
		referenceFramePanel.setTitle("Quadro de referência");
		referenceFramePanel.setDrawTitle(true);
	}
	
    private void createHeatMapPanel() {
        heatMapPanel = new HeatMapPanel(null);
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
    	lblCodingBlock = new JLabel("Bloco: x, y");
    	lblBestVector = new JLabel("Melhor bloco candidato: x, y");
    	lblBestSad = new JLabel("SAD do melhor bloco candidato: 0");
    	lblVector = new JLabel("Bloco escolhido pelo algoritmo: x, y");
    	lblSad = new JLabel("SAD do bloco escolhido pelo algoritmo: 0");
    	lblNumberOfBlocks = new JLabel("Número de blocos candidatos: 0");
    	lblNumberOfBlocksVisited = new JLabel("Número de blocos candidatos visitados: 0");
    	
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
    	resultsPanel.add(lblBestVector, constraints);
    	
    	constraints.gridx = 0;
    	constraints.gridy = 4;
    	resultsPanel.add(lblBestSad, constraints);
    	
    	constraints.gridx = 0;
    	constraints.gridy = 5;
    	resultsPanel.add(lblVector, constraints);
    	
    	constraints.gridx = 0;
    	constraints.gridy = 6;
    	resultsPanel.add(lblSad, constraints);
    	
    	constraints.gridx = 0;
    	constraints.gridy = 7;
    	resultsPanel.add(lblNumberOfBlocks, constraints);
    	
    	constraints.gridx = 0;
    	constraints.gridy = 8;
    	resultsPanel.add(lblNumberOfBlocksVisited, constraints);
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
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.anchor = GridBagConstraints.PAGE_START;
		constraints.fill = GridBagConstraints.BOTH;
    	constraints.insets = new Insets(5, 5, 5, 5);
    	constraints.gridx = 0;
    	constraints.gridx = 0;
    	constraints.gridwidth = 2;
    	
		this.add(controlPanel, constraints);
	}
	
	
	private void addReferenceFramePanel() {
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.fill = GridBagConstraints.BOTH;
    	constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridx = 0;
        constraints.gridy = 1;
        
        this.add(referenceFramePanel, constraints);
	}
	
	private void addActualFramePanel() {
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.fill = GridBagConstraints.BOTH;
    	constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridx = 1;
        constraints.gridy = 1;
        
        this.add(actualFramePanel, constraints);
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
	
   public void setActualFrame(Frame frame) {
    	actualFramePanel.setFrame(frame);
    }
    
    public void setReferenceFrame(Frame frame) {
    	referenceFramePanel.setFrame(frame);
    }
    
    public void setHeatMap(double[][] data) {
    	heatMapPanel.updateData(data);
    }
    
    public void setActualFrameIndex(int actualFrameIndex, int framesTotal) {
    	lblActualFrame.setText("Quadro atual: " + actualFrameIndex + "/" + framesTotal);
    }
    
    public void setReferenceFrameIndex(int referenceFrameIndex, int framesTotal) {
    	lblReferenceFrame.setText("Quadro de referência: " + referenceFrameIndex + "/" + framesTotal);
    }
    
    public void setCodingBlock(CodingBlock codingBlock) {
    	Position blockPosition = codingBlock.getPosition();
    	
    	actualFramePanel.setCodingBlock(codingBlock);
    	
    	lblCodingBlock.setText("Bloco: " + blockPosition.getX() + "," + blockPosition.getY());
    }
    
    public void setSearchArea(CodingBlock codingBlock, int width, int height) {
    	referenceFramePanel.setSearchArea(codingBlock, width, height);
    }
    
    public void setBestVector(MotionEstimationVector bestVector) {
    	int bestBlockX = bestVector.getPosition().getX();
    	int bestBlockY = bestVector.getPosition().getY();
    	int bestSad = bestVector.getCriteriaResult();
    	
    	heatMapPanel.setBestBlock(bestVector);
    	
    	lblBestVector.setText("Vetor do melhor bloco candidato: " + bestBlockX + "," + bestBlockY);
    	lblBestSad.setText("SAD do melhor bloco candidato: " + bestSad);
    }
    
    public void setResultVector(MotionEstimationVector resultVector) {
    	int resultBlockX = resultVector.getPosition().getX();
    	int resultBlockY = resultVector.getPosition().getY();
    	int resultSad = resultVector.getCriteriaResult();
    	
    	heatMapPanel.setResultBlock(resultVector);
    	
    	lblVector.setText("Vetor do bloco candidato escolhido: " + resultBlockX + "," + resultBlockY);
    	lblSad.setText("SAD do bloco candidato escolhido: " + resultSad);
    }

    public void setNumberOfBlocks(int numberOfBlocks) {
    	lblNumberOfBlocks.setText("Número de blocos candidatos: " + numberOfBlocks);
    }
    
    public void setNumberOfBlocksVisited(int numberOfBlocksVisited) {
    	lblNumberOfBlocksVisited.setText("Número de blocos candidatos visitados: " + numberOfBlocksVisited);
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
