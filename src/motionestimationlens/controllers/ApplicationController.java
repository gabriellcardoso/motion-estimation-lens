package motionestimationlens.controllers;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import motionestimationlens.models.CodingBlock;
import motionestimationlens.models.DiamondSearch;
import motionestimationlens.models.Frame;
import motionestimationlens.models.FullSearch;
import motionestimationlens.models.IEvaluationCriteria;
import motionestimationlens.models.ISearchAlgorithm;
import motionestimationlens.models.MotionEstimation;
import motionestimationlens.models.MotionEstimationData;
import motionestimationlens.models.MotionEstimationVector;
import motionestimationlens.models.Position;
import motionestimationlens.models.SumOfAbsoluteDifferences;
import motionestimationlens.models.ThreeStepSearch;
import motionestimationlens.utils.ME;
import motionestimationlens.utils.YUVReader;
import motionestimationlens.views.MainView;
import motionestimationlens.views.SetupView;

import org.mblecker.heatmap.HeatMap;


public class ApplicationController extends JFrame {
	
	// Application configs
	private File inputFile;
	private int frameWidth;
	private int frameHeight;
	private int samplingYCbCr;
	private int framesTotal;
	private ArrayList<String> selectedAlgorithms;
	private int searchAreaWidth;
	private int searchAreaHeight;
	private int blockWidth;
	private int blockHeight;
	private boolean keepReferenceFrame;
	
	// Data generation related
	private YUVReader videoReader;
	
	private int referenceFrameIndex = ME.FIRST_FRAME;
	private Frame referenceFrame;
	
	private int actualFrameIndex = ME.SECOND_FRAME;
	private Frame actualFrame;
	
	private Position codingBlockPosition = new Position(ME.FIRST_BLOCK_X, ME.FIRST_BLOCK_Y);
	
	ArrayList<MotionEstimation> motionEstimationList = new ArrayList();

	// User interface related
	private Container container;
	private CardLayout layout;
	private MainView mainView;
	private SetupView setupView;
	private JFileChooser videoChooser;
	
	public ApplicationController() {
		super("Motion Estimation Lens");
		
		container = this.getContentPane();
		layout = new CardLayout();
		
		setLayout(layout);
		
		startSetupView();
		startMainView();
	}
	
	private void startSetupView() {
		setupView = new SetupView();
		videoChooser = new JFileChooser();
		
		setupView.setBtnStartEnabled(false);
		
		setupView.setBtnOpenVideoListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int result = videoChooser.showDialog(setupView.getComponent(2), "Open YUV file");
				
				if (result == JFileChooser.APPROVE_OPTION) {
					inputFile = videoChooser.getSelectedFile();
					setupView.setBtnStartEnabled(true);
				}
			}
		});
		
		setupView.setBtnStartListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				getConfigs();
				
				if (frameWidth <= 0 && frameHeight <= 0) {
					showMessage("Informe uma resolu��o v�lida");
				}
				else if (selectedAlgorithms.size() == 0) {
					showMessage("Selecione ao menos um algoritmo");
				}
				else if (searchAreaWidth <= 0 && searchAreaHeight <= 0) {
					showMessage("Informe um tamanho de �rea de busca v�lido");
				}
				else if (blockWidth <= 0 && blockHeight <= 0) {
					showMessage("Informe o tamanho de bloco v�lido");
				}
				else {
					setUpModels();
					showResults();
					goToMainView();
				}
			}
		});
		
		container.add(setupView, ME.SETUP_PANEL);
	}
	
	private void startMainView() {
		mainView = new MainView();
		
		mainView.setBtnPreviousFrameEnabled(false);
		mainView.setBtnPreviousBlockEnabled(false);
		
		mainView.setBtnPreviousFrameListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				goToPreviousFrame();
				showResults();
				setControlButtonsState();
			}
		});
		
		mainView.setBtnPreviousBlockListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				goToPreviousBlock();
				showResults();
				setControlButtonsState();
			}
		});
		
		mainView.setBtnNextBlockListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				goToNextBlock();
				showResults();
				setControlButtonsState();
			}
		});
		
		mainView.setBtnNextFrameListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				goToNextFrame();
				showResults();
				setControlButtonsState();
			}
		});
		
		mainView.setBtnSetActualFrameListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String response = JOptionPane.showInputDialog(null, "Escolha o quadro atual: ");
				
				if (response != "") {
					actualFrameIndex = Integer.parseInt(response) - 1;
					videoReader.setFrameWithImage(actualFrame, actualFrameIndex);
					showResults();
					setControlButtonsState();
				}
				
			}
		});
		
		mainView.setBtnSetReferenceFrameListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String response = JOptionPane.showInputDialog(null, "Escolha o quadro de referência: ");

				if (response != "") {
					referenceFrameIndex = Integer.parseInt(response) - 1;
					videoReader.setFrameWithImage(referenceFrame, referenceFrameIndex);
					showResults();
					setControlButtonsState();
				}
			}
		});
		
		mainView.setBtnSetCodingBlockListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String response = JOptionPane.showInputDialog(null, "Escolha as coordenadas do bloco a ser codificado (x,y): ");
				
				if (response != "") {
					int x = ME.getX(response);
					int y = ME.getY(response);
					setCodingBlockPosition(x, y);
					showResults();
					setControlButtonsState();
				}
			}
		});
		
		mainView.setBtnBackToSetupListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				goToSetupView();
			}
		});

		container.add(mainView, ME.MAIN_PANEL);
	}
	
	private void setCodingBlockPosition(int x, int y) {
		codingBlockPosition.setPosition(x, y);
		goToBlock(x, y);
	}
	
	private void getConfigs() {
		frameWidth = setupView.getFrameWidth();
		frameHeight = setupView.getFrameHeight();
		samplingYCbCr = setupView.getSampling();
		selectedAlgorithms = setupView.getSelectedAlgorithms();
		searchAreaWidth = setupView.getSearchAreaWidth(); 
		searchAreaHeight = setupView.getSearchAreaHeight();
		blockWidth = setupView.getBlockWidth();
		blockHeight = setupView.getBlockHeight();
		keepReferenceFrame = setupView.getKeepReferenceFrameState();
	}
	
	private void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}
	
	private void setUpModels() {
		MotionEstimation searchAlgorithmME;
		ISearchAlgorithm searchAlgorithm;
		IEvaluationCriteria searchAlgorithmSad;
		
		videoReader = new YUVReader(inputFile, frameWidth, frameHeight, samplingYCbCr);
		framesTotal = videoReader.getFramesTotal();
		
		actualFrame = new Frame(frameWidth, frameHeight);
		referenceFrame = new Frame(frameWidth, frameHeight);
		
		videoReader.setFrameWithImage(actualFrame, actualFrameIndex);
		videoReader.setFrameWithImage(referenceFrame, referenceFrameIndex);
		
		motionEstimationList.clear();
		
		for (String selectedAlgorithm: selectedAlgorithms) {
			
			switch (selectedAlgorithm) {
			
			case ME.FS:
				searchAlgorithm = new FullSearch();
				break;
			
			case ME.DS:
				searchAlgorithm = new DiamondSearch();
				break;
			
			case ME.TSS:
				searchAlgorithm = new ThreeStepSearch();
				break;

			default:
				searchAlgorithm = new FullSearch();
			
			}
			
			searchAlgorithmSad = new SumOfAbsoluteDifferences();
			searchAlgorithmME = new MotionEstimation(searchAlgorithm, searchAlgorithmSad, blockWidth, blockHeight, searchAreaWidth, searchAreaHeight);
			searchAlgorithmME.setActualFrame(actualFrame);
			searchAlgorithmME.setReferenceFrame(referenceFrame);
			
			motionEstimationList.add(searchAlgorithmME);
		}
		
		setCodingBlockPosition(codingBlockPosition.getX(), codingBlockPosition.getY());
	}
	
	private void showResults() {
		ArrayList<MotionEstimationVector> results = new ArrayList<MotionEstimationVector>();
		
		double[][] heatMap;
		CodingBlock codingBlock;
		int candidateBlocksTotal;
		
		MotionEstimationData algorithmResult = null;
		MotionEstimationVector algorithmVector = null;
		
		for (MotionEstimation motionEstimation : motionEstimationList) {
			algorithmResult = motionEstimation.run();
			algorithmVector = algorithmResult.getResultVector();
			results.add(algorithmVector);
		}
		
		heatMap = algorithmResult.getHeatMap();
		codingBlock = algorithmResult.getResultVector().getCodingBlock();
		candidateBlocksTotal = algorithmResult.getCandidateBlocksTotal();
		
		mainView.setResults(framesTotal, referenceFrame, referenceFrameIndex + 1, actualFrame, actualFrameIndex + 1, searchAreaWidth, searchAreaHeight, codingBlock, candidateBlocksTotal, heatMap, selectedAlgorithms, results);
	}
	
	private void goToMainView() {
		layout.show(container, ME.MAIN_PANEL);
	}
	
	private void goToSetupView() {
		layout.show(container, ME.SETUP_PANEL);
	}
	
	private void goToPreviousFrame() {
		if (actualFrameIndex > 1) {

			actualFrameIndex--;
			videoReader.setFrameWithImage(actualFrame, actualFrameIndex);
			
			if (!keepReferenceFrame && referenceFrameIndex > 1) {
				referenceFrameIndex--;
				videoReader.setFrameWithImage(referenceFrame, referenceFrameIndex);
			}
		}
	}
	
	private void goToNextFrame() {
		if (actualFrameIndex < framesTotal) {
			
			actualFrameIndex++;
			videoReader.setFrameWithImage(actualFrame, actualFrameIndex);
			
			if (!keepReferenceFrame && referenceFrameIndex < framesTotal) {
				referenceFrameIndex++;
				videoReader.setFrameWithImage(referenceFrame, referenceFrameIndex);
			}
		}
	}
	
	private void goToPreviousBlock() {
		int nextBlockX = codingBlockPosition.getX() - 1;
		int nextBlockY = codingBlockPosition.getY();
		
		if (nextBlockX < 0) {
			nextBlockY--;
		}
		
		setCodingBlockPosition(nextBlockX, nextBlockY);
	}
	
	private void goToNextBlock() {
		int widthMaxBlocks = frameWidth / blockWidth;
		int heightMaxBlocks = frameHeight / blockHeight;
		
		int nextBlockX = codingBlockPosition.getX() + 1;
		int nextBlockY = codingBlockPosition.getY();
		
		if (nextBlockX >= widthMaxBlocks) {
			nextBlockX = 0;
			nextBlockY++;
		}
		
		setCodingBlockPosition(nextBlockX, nextBlockY);
	}
	
	private void goToBlock(int x, int y) {
		for (MotionEstimation motionEstimation : motionEstimationList) {
			motionEstimation.setCodingBlockPosition(x * blockWidth, y * blockHeight);
		}
	}
	
	private void setControlButtonsState() {
		// Set previous frame button state
		if (actualFrameIndex == 1) {
			mainView.setBtnPreviousFrameEnabled(false);
		}
		else {
			mainView.setBtnPreviousFrameEnabled(true);
		}
		
		// Set next frame button state
		if (actualFrameIndex == framesTotal - 1) {
			mainView.setBtnNextFrameEnabled(false);
		}
		else {
			mainView.setBtnNextFrameEnabled(true);
		}
		
		// Set previous block button state
		if (codingBlockPosition.getX() <= 0 && codingBlockPosition.getY() <= 0) {
			mainView.setBtnPreviousBlockEnabled(false);
		}
		else {
			mainView.setBtnPreviousBlockEnabled(true);
		}
		
		// Set next block button state
		if (codingBlockPosition.getX() >= frameWidth / blockWidth && codingBlockPosition.getY() >= frameHeight / blockHeight ) {
			mainView.setBtnNextBlockEnabled(false);
		}
		else {
			mainView.setBtnNextBlockEnabled(true);
		}
	}
	
}
