package br.edu.ufpel.inf.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import br.edu.ufpel.inf.utils.ME;

public class SetupPanel extends JPanel {
	
	private final static Border EMPTY_BORDER = new EmptyBorder(0, 0, 0, 0);
	
	private JPanel videoConfigPanel;
	
	private JButton openVideoButton;
	private JComboBox<String> resolutionComboBox;
	private JComboBox<String> samplingComboBox;
	private JTextField framesInput;
	
	private JPanel motionEstimationConfigPanel;
	
	private JComboBox<String> algorithmComboBox;
	private JComboBox<String> searchAreaComboBox;
	private JComboBox<String> blockSizeComboBox;
	private JCheckBox sameReferenceFrameCheckBox;
	
	private JPanel footerPanel;
	
	private JButton startButton;
	
	public SetupPanel() {
		super();
		
		setLayout(new GridBagLayout());
		
		createVideoConfigPanel();
		createMotionEstimationConfigPanel();
		createFooterPanel();
		
		addVideoConfigPanel();
		addMotionEstimationConfigPanel();
		addFooterPanel();
	}
	
	private void createVideoConfigPanel() {
		Border border = BorderFactory.createTitledBorder(
							"Configurações de vídeo",
							new EmptyBorder(25, 25, 25, 25)
						);
		
		videoConfigPanel = new JPanel();
		videoConfigPanel.setLayout(new GridBagLayout());
		videoConfigPanel.setBorder(border);
		
		JLabel resolutionLabel = new JLabel("Resolução:");
		JLabel samplingLabel = new JLabel("Amostragem:");
		JLabel framesLabel = new JLabel("Total de quadros:");
		
		openVideoButton = new JButton("Abrir arquivo YUV");
		resolutionComboBox = new JComboBox<String>(ME.RESOLUTION_ITEMS);
		samplingComboBox = new JComboBox<String>(ME.SAMPLING_ITEMS);
		framesInput = new JTextField("1");
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;  
		constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(5, 5, 5, 5);
        
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        videoConfigPanel.add(openVideoButton, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		videoConfigPanel.add(resolutionLabel, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 2;
		videoConfigPanel.add(resolutionComboBox, constraints);

		constraints.gridx = 1;
		constraints.gridy = 1;
		videoConfigPanel.add(samplingLabel, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 2;
		videoConfigPanel.add(samplingComboBox, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 3;
		videoConfigPanel.add(framesLabel, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 4;
		videoConfigPanel.add(framesInput, constraints);
	}
	
	private void createMotionEstimationConfigPanel() {
		Border border = BorderFactory.createTitledBorder(
							"Configurações da estimação de movimento",
							new EmptyBorder(25, 25, 25, 25)
						);
		
		motionEstimationConfigPanel = new JPanel();
		motionEstimationConfigPanel.setLayout(new GridBagLayout());
		motionEstimationConfigPanel.setBorder(border);

		JLabel algorithmLabel = new JLabel("Algoritmo de busca:");
		JLabel searchAreaLabel = new JLabel("Área de busca:");
		JLabel blockLabel = new JLabel("Tamanho do bloco:");
		
		algorithmComboBox = new JComboBox<String>(ME.ALGORITHM_ITEMS);
		searchAreaComboBox = new JComboBox<String>(ME.SEARCH_AREA_ITEMS);
		blockSizeComboBox = new JComboBox<String>(ME.BLOCK_SIZE_ITEMS);
		sameReferenceFrameCheckBox = new JCheckBox("Fixar quadro de referência");
		
		GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 5, 5);
        
        constraints.gridx = 0;
		constraints.gridy =  0;
		motionEstimationConfigPanel.add(algorithmLabel, constraints);
		
		constraints.gridx = 0;
		constraints.gridy =  1;
		constraints.gridwidth = 2;
		motionEstimationConfigPanel.add(algorithmComboBox, constraints);
		
		constraints.gridx = 0;
		constraints.gridy =  2;
		constraints.gridwidth = 1;
		motionEstimationConfigPanel.add(searchAreaLabel, constraints);
		
        constraints.gridx = 0;
		constraints.gridy =  3;
		motionEstimationConfigPanel.add(searchAreaComboBox, constraints);
        
		constraints.gridx = 1;
		constraints.gridy =  2;
		motionEstimationConfigPanel.add(blockLabel, constraints);
        
        constraints.gridx = 1;
		constraints.gridy = 3;
		motionEstimationConfigPanel.add(blockSizeComboBox, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth = 2;
		motionEstimationConfigPanel.add(sameReferenceFrameCheckBox, constraints);
	}
	
	private void createFooterPanel() {
		Border border = BorderFactory.EMPTY_BORDER;
		
		footerPanel = new JPanel();
		footerPanel.setLayout(new GridBagLayout());
		footerPanel.setBorder(border);
		
		startButton = new JButton("Começar a análise");
		
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.fill = GridBagConstraints.HORIZONTAL;  
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridx = 0;
        constraints.gridy = 0;
        
		footerPanel.add(startButton, constraints);
	}
	
	private void addVideoConfigPanel() {
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 5, 5, 5);
		constraints.gridx = 0;
		constraints.gridy = 0;
		
		this.add(videoConfigPanel, constraints);
	}
	
	private void addMotionEstimationConfigPanel() {
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(20, 5, 5, 5);
		constraints.gridx = 0;
		constraints.gridy = 1;
		
		this.add(motionEstimationConfigPanel, constraints);
	}
	
	private void addFooterPanel() {
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(20, 5, 5, 5);
		constraints.gridx = 0;
		constraints.gridy = 2;
		
		this.add(footerPanel, constraints);
	}
	
	public void setOpenVideoButtonListener (ActionListener listener) {
		openVideoButton.addActionListener(listener);
	}
	
	public void setStartButtonListener(ActionListener listener) {
		startButton.addActionListener(listener);
	}
	
}
