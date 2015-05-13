package br.edu.ufpel.inf.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class SetupPanel extends JPanel {
	
	private GridBagConstraints constraints;
	
	private JButton startButton;
	
	public SetupPanel() {
		super();
		
		setBorder(BorderFactory.createTitledBorder("Setup Motion Estimation Lens"));
		setLayout(new GridBagLayout());
		
		setConstraints();
		createStartButton();
	}
	
	private void setConstraints() {
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(2, 1, 0, 0);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.gridy = GridBagConstraints.RELATIVE;
	}
	
	private void createStartButton() {
		startButton = new JButton("Start");
		this.add(startButton, constraints);
	}
	
	public void setStartButtonListener(ActionListener listener) {
		startButton.addActionListener(listener);
	}
	
}
