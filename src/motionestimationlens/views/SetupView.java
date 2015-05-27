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
	private JComboBox<String> comboBoxResolution;
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
							"Configura��es de v�deo",
							new EmptyBorder(25, 25, 25, 25)
						);
		
		videoConfigPanel = new JPanel();
		videoConfigPanel.setLayout(new GridBagLayout());
		videoConfigPanel.setBorder(border);
		
		JLabel resolutionLabel = new JLabel("Resolu��o:");
		JLabel samplingLabel = new JLabel("Amostragem:");
		JLabel framesLabel = new JLabel("Total de quadros:");
		
		btnOpenVideo = new JButton("Abrir arquivo YUV");
		comboBoxResolution = new JComboBox<String>(ME.RESOLUTION_ITEMS);
		comboBoxSampling = new JComboBox<String>(ME.SAMPLING_ITEMS);
		inputFrames = new JTextField("1");
		
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
		videoConfigPanel.add(resolutionLabel, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 2;
		videoConfigPanel.add(comboBoxResolution, constraints);

		constraints.gridx = 1;
		constraints.gridy = 1;
		videoConfigPanel.add(samplingLabel, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 2;
		videoConfigPanel.add(comboBoxSampling, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 3;
		videoConfigPanel.add(framesLabel, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 4;
		videoConfigPanel.add(inputFrames, constraints);
	}
	
	private void createMotionEstimationConfigPanel() {
		Border border = BorderFactory.createTitledBorder(
							"Configura��es da estima��o de movimento",
							new EmptyBorder(25, 25, 25, 25)
						);
		
		motionEstimationConfigPanel = new JPanel();
		motionEstimationConfigPanel.setLayout(new GridBagLayout());
		motionEstimationConfigPanel.setBorder(border);

		JLabel algorithmLabel = new JLabel("Algoritmo de busca:");
		JLabel searchAreaLabel = new JLabel("�rea de busca:");
		JLabel blockLabel = new JLabel("Tamanho do bloco:");
		
		comboBoxAlgorithm = new JComboBox<String>(ME.ALGORITHM_ITEMS);
		comboBoxSearchArea = new JComboBox<String>(ME.SEARCH_AREA_ITEMS);
		comboBoxBlockSize = new JComboBox<String>(ME.BLOCK_SIZE_ITEMS);
		checkBoxKeepReferenceFrame = new JCheckBox("Fixar quadro de refer�ncia");
		
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
		
		btnStart = new JButton("Come�ar an�lise");
		
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
		String selected = (String) comboBoxResolution.getSelectedItem();
		return ME.getWidth(selected);
	}
	
	public int getFrameHeight() {
		String selected = (String) comboBoxResolution.getSelectedItem();
		return ME.getHeight(selected);
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
