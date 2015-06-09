package motionestimationlens.controllers;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
	private String selectedAlgorithm;
	private int searchAreaWidth;
	private int searchAreaHeight;
	private int blockWidth;
	private int blockHeight;
	private boolean keepReferenceFrame;
	
	private int actualFrameIndex = ME.SECOND_FRAME;
	private int referenceFrameIndex = ME.FIRST_FRAME;
	private Position codingBlockPosition = new Position(ME.FIRST_BLOCK_X, ME.FIRST_BLOCK_Y);
	
	// Data generation related
	private YUVReader videoReader;
	private Frame actualFrame;
	private Frame referenceFrame;
	private MotionEstimation fullSearchME;
	private ISearchAlgorithm fullSearch;
	private IEvaluationCriteria fullSearchSad;
	private MotionEstimation searchAlgorithmME;
	private ISearchAlgorithm searchAlgorithm;
	private IEvaluationCriteria searchAlgorithmSad;

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
				setUpModels();
				showResults();
				goToMainView();
			}
		});
		
		container.add(setupView, ME.SETUP_PANEL);
	}
	
	private void startMainView() {
		mainView = new MainView();
		
		mainView.setBtnPreviousFrameEnabled(false);
		mainView.setBtnPreviousBlockEnabled(false);
		
		mainView.setHeatMap(HeatMap.generateRampTestData());
		
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
		selectedAlgorithm = setupView.getSearchAlgorithm();
		searchAreaWidth = setupView.getSearchAreaWidth(); 
		searchAreaHeight = setupView.getSearchAreaHeight();
		blockWidth = setupView.getBlockWidth();
		blockHeight = setupView.getBlockHeight();
		keepReferenceFrame = setupView.getKeepReferenceFrameState();
	}
	
	private void printConfigs() {
		System.out.println("Input file: " + inputFile.getPath());
		System.out.println("Frame width: " + frameWidth);
		System.out.println("Frame height: " + frameHeight);
		System.out.println("Video sampling: " + samplingYCbCr);
		System.out.println("Total frames: " + framesTotal);
		System.out.println("Selected algorithm: " + selectedAlgorithm);
		System.out.println("Search area width: " + searchAreaWidth);
		System.out.println("Search area height: " + searchAreaHeight);
		System.out.println("Block width: " + blockWidth);
		System.out.println("Block height: " + blockHeight);
		System.out.println("Keep reference frame: " + keepReferenceFrame);
	}
	
	private void setUpModels() {
		videoReader = new YUVReader(inputFile, frameWidth, frameHeight, samplingYCbCr);
		framesTotal = videoReader.getFramesTotal();
		
		actualFrame = new Frame(frameWidth, frameHeight);
		referenceFrame = new Frame(frameWidth, frameHeight);
		
		videoReader.setFrameWithImage(actualFrame, actualFrameIndex);
		videoReader.setFrameWithImage(referenceFrame, referenceFrameIndex);
		
		mainView.setActualFrame(actualFrame);
		mainView.setReferenceFrame(referenceFrame);
		
		fullSearchSad = new SumOfAbsoluteDifferences();
		fullSearch = new FullSearch();
		fullSearchME = new MotionEstimation(fullSearch, fullSearchSad, blockWidth, blockHeight, searchAreaWidth, searchAreaHeight);
		
		fullSearchME.setActualFrame(actualFrame);
		fullSearchME.setReferenceFrame(referenceFrame);
		
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
		
		setCodingBlockPosition(codingBlockPosition.getX(), codingBlockPosition.getY());
	}
	
	private void showResults() {
		MotionEstimationData fullSearchResult = fullSearchME.run();
		MotionEstimationData searchAlgorithmResult = searchAlgorithmME.run();
		
		CodingBlock codingBlock = searchAlgorithmResult.getResultVector().getCodingBlock();
		
		MotionEstimationVector fullSearchVector = fullSearchResult.getResultVector();
		MotionEstimationVector searchAlgorithmVector = searchAlgorithmResult.getResultVector();
		
		double[][] heatMap = searchAlgorithmResult.getHeatMap();
		
		int candidateBlocksTotal = searchAlgorithmResult.getCandidateBlocksTotal();
		int blocksVisited = searchAlgorithmResult.getResultVector().getBlocksVisited();
		
		mainView.setHeatMap(heatMap);
		
		mainView.setActualFrameIndex(actualFrameIndex + 1, framesTotal);
		mainView.setReferenceFrameIndex(referenceFrameIndex + 1, framesTotal);
		
		mainView.setSearchArea(codingBlock, searchAreaWidth, searchAreaHeight);
		mainView.setCodingBlock(codingBlock);
		
		mainView.setBestVector(fullSearchVector);
		mainView.setResultVector(searchAlgorithmVector);
		
		mainView.setNumberOfBlocks(candidateBlocksTotal);
		mainView.setNumberOfBlocksVisited(blocksVisited);
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
			
			if (!keepReferenceFrame) {
				referenceFrameIndex--;
				videoReader.setFrameWithImage(referenceFrame, referenceFrameIndex);
			}
		}
	}
	
	private void goToNextFrame() {
		if (actualFrameIndex < framesTotal) {
			
			actualFrameIndex++;
			videoReader.setFrameWithImage(actualFrame, actualFrameIndex);
			
			if (!keepReferenceFrame) {
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
		this.fullSearchME.setCodingBlockPosition(x * blockWidth, y * blockHeight);
		this.searchAlgorithmME.setCodingBlockPosition(x * blockWidth, y * blockHeight);
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
