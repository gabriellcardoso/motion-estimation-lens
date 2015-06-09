package motionestimationlens.views;

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

import motionestimationlens.utils.BorderFactory;
import motionestimationlens.utils.ME;


public class SetupView extends JPanel {
	
	private final static Border EMPTY_BORDER = new EmptyBorder(0, 0, 0, 0);
	
	private JPanel videoConfigPanel;
	
	private JButton btnOpenVideo;
	private JTextField resolutionWidth;
	private JTextField resolutionHeight;
	private JComboBox<String> comboBoxSampling;
	private JTextField inputFrames;
	
	private JPanel motionEstimationConfigPanel;
	
	private JComboBox<String> comboBoxAlgorithm;
	private JComboBox<String> comboBoxSearchArea;
	private JComboBox<String> comboBoxBlockSize;
	private JCheckBox checkBoxKeepReferenceFrame;
	
	private JPanel footerPanel;
	
	private JButton btnStart;
	
	public SetupView() {
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
		
		JLabel resolutionWidthLabel = new JLabel("Largura da resolução:");
		JLabel resolutionHeightLabel = new JLabel("Altura da resolução:");
		JLabel samplingLabel = new JLabel("Amostragem:");
		JLabel framesLabel = new JLabel("Total de quadros:");
		
		btnOpenVideo = new JButton("Abrir arquivo YUV");
		resolutionWidth = new JTextField();
		resolutionHeight = new JTextField();
		comboBoxSampling = new JComboBox<String>(ME.SAMPLING_ITEMS);
		inputFrames = new JTextField("1");
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;  
		constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(5, 5, 5, 5);
        
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        videoConfigPanel.add(btnOpenVideo, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		videoConfigPanel.add(resolutionWidthLabel, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 2;
		videoConfigPanel.add(resolutionWidth, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 3;
		videoConfigPanel.add(resolutionHeightLabel, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 4;
		videoConfigPanel.add(resolutionHeight, constraints);

		constraints.gridx = 0;
		constraints.gridy = 5;
		videoConfigPanel.add(samplingLabel, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 6;
		videoConfigPanel.add(comboBoxSampling, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 7;
		videoConfigPanel.add(framesLabel, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 8;
		videoConfigPanel.add(inputFrames, constraints);
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
		
		comboBoxAlgorithm = new JComboBox<String>(ME.ALGORITHM_ITEMS);
		comboBoxSearchArea = new JComboBox<String>(ME.SEARCH_AREA_ITEMS);
		comboBoxBlockSize = new JComboBox<String>(ME.BLOCK_SIZE_ITEMS);
		checkBoxKeepReferenceFrame = new JCheckBox("Fixar quadro de referência");
		
		GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 5, 5);
        
        constraints.gridx = 0;
		constraints.gridy =  0;
		motionEstimationConfigPanel.add(algorithmLabel, constraints);
		
		constraints.gridx = 0;
		constraints.gridy =  1;
		constraints.gridwidth = 2;
		motionEstimationConfigPanel.add(comboBoxAlgorithm, constraints);
		
		constraints.gridx = 0;
		constraints.gridy =  2;
		constraints.gridwidth = 1;
		motionEstimationConfigPanel.add(searchAreaLabel, constraints);
		
        constraints.gridx = 0;
		constraints.gridy =  3;
		motionEstimationConfigPanel.add(comboBoxSearchArea, constraints);
        
		constraints.gridx = 1;
		constraints.gridy =  2;
		motionEstimationConfigPanel.add(blockLabel, constraints);
        
        constraints.gridx = 1;
		constraints.gridy = 3;
		motionEstimationConfigPanel.add(comboBoxBlockSize, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth = 2;
		motionEstimationConfigPanel.add(checkBoxKeepReferenceFrame, constraints);
	}
	
	private void createFooterPanel() {
		Border border = BorderFactory.EMPTY_BORDER;
		
		footerPanel = new JPanel();
		footerPanel.setLayout(new GridBagLayout());
		footerPanel.setBorder(border);
		
		btnStart = new JButton("Começar análise");
		
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.fill = GridBagConstraints.HORIZONTAL;  
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridx = 0;
        constraints.gridy = 0;
        
		footerPanel.add(btnStart, constraints);
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
	
	public int getTotalFrames() {
		String totalFrames = inputFrames.getText();
		return Integer.parseInt(totalFrames);
	}
	
	public int getFrameWidth() {
		String width = (String) resolutionWidth.getText();
		return Integer.parseInt(width.trim());
	}
	
	public int getFrameHeight() {
		String height = (String) resolutionHeight.getText();
		return Integer.parseInt(height.trim());
	}
	
	public int getSampling() {
		String selected = (String) comboBoxSampling.getSelectedItem();
		
		switch (selected) {
			case ME.S_444: return 4;
			case ME.S_422: return 2;
			case ME.S_420: return 1;
			case ME.S_411: return 1;
			case ME.S_400: return 0; 
		}
		
		return 0;
	}
	
	public String getSearchAlgorithm() {
		return (String) comboBoxAlgorithm.getSelectedItem();
	}
	
	public int getSearchAreaWidth() {
		String selected = (String) comboBoxSearchArea.getSelectedItem();
		return ME.getWidth(selected);
	}
	
	public int getSearchAreaHeight() {
		String selected = (String) comboBoxSearchArea.getSelectedItem();
		return ME.getHeight(selected);
	}
	
	public int getBlockWidth() {
		String selected = (String) comboBoxBlockSize.getSelectedItem();
		return ME.getWidth(selected);
	}
	
	public int getBlockHeight() {
		String selected = (String) comboBoxBlockSize.getSelectedItem();
		return ME.getHeight(selected);
	}
	
	public boolean getKeepReferenceFrameState() {
		return checkBoxKeepReferenceFrame.isSelected();
	}
	
	public void setBtnOpenVideoListener (ActionListener listener) {
		btnOpenVideo.addActionListener(listener);
	}
	
	public void setBtnStartListener(ActionListener listener) {
		btnStart.addActionListener(listener);
	}
	
	public void setBtnStartEnabled(boolean state) {
		btnStart.setEnabled(state);
	}
	
}
