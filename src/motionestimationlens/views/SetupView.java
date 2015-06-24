package motionestimationlens.views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
	
	private JPanel videoConfigPanel;
	
		private JButton btnOpenVideo;
		private JTextField resolutionWidth;
		private JTextField resolutionHeight;
		private JComboBox<String> comboBoxSampling;
	
	private JPanel motionEstimationConfigPanel;
	
		private ArrayList<JCheckBox> checkBoxAlgorithms;
		private JTextField searchAreaWidth;
		private JTextField searchAreaHeight;
		private JTextField blockWidth;
		private JTextField blockHeight;
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
		
		btnOpenVideo = new JButton("Abrir arquivo YUV");
		resolutionWidth = new JTextField();
		resolutionHeight = new JTextField();
		comboBoxSampling = new JComboBox<String>(ME.SAMPLING_ITEMS);
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;  
		constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(5, 5, 5, 5);
        
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        videoConfigPanel.add(btnOpenVideo, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		videoConfigPanel.add(resolutionWidthLabel, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 2;
		videoConfigPanel.add(resolutionWidth, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 1;
		videoConfigPanel.add(resolutionHeightLabel, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 2;
		videoConfigPanel.add(resolutionHeight, constraints);

		constraints.gridx = 0;
		constraints.gridy = 3;
		videoConfigPanel.add(samplingLabel, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 4;
		videoConfigPanel.add(comboBoxSampling, constraints);
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
		JCheckBox algorithmBox;
		
		JLabel searchAreaWidthLabel = new JLabel("Largura da área de busca:");
		JLabel searchAreaHeightLabel = new JLabel("Altura da área de busca:");
		
		JLabel blockWidthLabel = new JLabel("Largura do bloco:");
		JLabel blockHeightLabel = new JLabel("Altura do bloco:");
		
		JPanel algorithmsPanel = new JPanel(new GridLayout(0,2,5,5));
		
		checkBoxAlgorithms = new ArrayList<JCheckBox>();
		
		for (String algorithm : ME.ALGORITHM_ITEMS) {
			algorithmBox = new JCheckBox(algorithm);
			algorithmsPanel.add(algorithmBox);
			checkBoxAlgorithms.add(algorithmBox);
		}
		
		searchAreaWidth = new JTextField();
		searchAreaHeight = new JTextField();
		
		blockWidth = new JTextField();
		blockHeight = new JTextField();
		
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
		motionEstimationConfigPanel.add(algorithmsPanel, constraints);
		
		constraints.gridx = 0;
		constraints.gridy =  2;
		constraints.gridwidth = 1;
		motionEstimationConfigPanel.add(searchAreaWidthLabel, constraints);
		
        constraints.gridx = 0;
		constraints.gridy =  3;
		motionEstimationConfigPanel.add(searchAreaWidth, constraints);
		
		constraints.gridx = 1;
		constraints.gridy =  2;
		motionEstimationConfigPanel.add(searchAreaHeightLabel, constraints);
		
        constraints.gridx = 1;
		constraints.gridy =  3;
		motionEstimationConfigPanel.add(searchAreaHeight, constraints);
        
		constraints.gridx = 0;
		constraints.gridy =  4;
		motionEstimationConfigPanel.add(blockWidthLabel, constraints);
        
        constraints.gridx = 0;
		constraints.gridy = 5;
		motionEstimationConfigPanel.add(blockWidth, constraints);
		
		constraints.gridx = 1;
		constraints.gridy =  4;
		motionEstimationConfigPanel.add(blockHeightLabel, constraints);
        
        constraints.gridx = 1;
		constraints.gridy = 5;
		motionEstimationConfigPanel.add(blockHeight, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 6;
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
	
	public ArrayList<String> getSelectedAlgorithms() {
		ArrayList<String> selectedAlgorithms = new ArrayList<String>();
		
		for (JCheckBox checkbox : checkBoxAlgorithms) {
			if (checkbox.isSelected()) {
				selectedAlgorithms.add(checkbox.getText());
			}
		}
		return selectedAlgorithms;
	}
	
	public int getSearchAreaWidth() {
		String width = (String) searchAreaWidth.getText();
		return Integer.parseInt(width.trim());
	}
	
	public int getSearchAreaHeight() {
		String height = (String) searchAreaHeight.getText();
		return Integer.parseInt(height.trim());
	}
	
	public int getBlockWidth() {
		String width = (String) blockWidth.getText();
		return Integer.parseInt(width.trim());
	}
	
	public int getBlockHeight() {
		String height = (String) blockHeight.getText();
		return Integer.parseInt(height);
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
