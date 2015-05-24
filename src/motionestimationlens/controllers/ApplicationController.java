package motionestimationlens.controllers;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import motionestimationlens.models.CodingBlock;
import motionestimationlens.models.Frame;
import motionestimationlens.models.FullSearch;
import motionestimationlens.models.IEvaluationCriteria;
import motionestimationlens.models.ISearchAlgorithm;
import motionestimationlens.models.MotionEstimation;
import motionestimationlens.models.MotionEstimationData;
import motionestimationlens.models.MotionEstimationVector;
import motionestimationlens.models.SumOfAbsoluteDifferences;
import motionestimationlens.utils.ME;
import motionestimationlens.utils.YUVReader;
import motionestimationlens.views.MainPanel;
import motionestimationlens.views.SetupPanel;

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
	
	private int actualFrameIndex;
	private int referenceFrameIndex;
	private int codingBlockIndex;
	
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
	private MainPanel mainPanel;
	private SetupPanel setupPanel;
	private JFileChooser videoChooser;
	
	public ApplicationController() {
		super("Motion Estimation Lens");
		
		container = this.getContentPane();
		layout = new CardLayout();
		
		setLayout(layout);
		
		startSetupPanel();
		startMainPanel();
	}
	
	private void startSetupPanel() {
		setupPanel = new SetupPanel();
		videoChooser = new JFileChooser();
		
		setupPanel.setBtnStartEnabled(false);
		
		setupPanel.setBtnOpenVideoListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int result = videoChooser.showDialog(setupPanel.getComponent(2), "Open YUV file");
				
				if (result == JFileChooser.APPROVE_OPTION) {
					inputFile = videoChooser.getSelectedFile();
					setupPanel.setBtnStartEnabled(true);
				}
			}
		});
		
		setupPanel.setBtnStartListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getConfigs();
				printConfigs();
				setUpModels();
				showResults();
				goToMainPanel();
			}
		});
		
		container.add(setupPanel, ME.SETUP_PANEL);
	}
	
	private void startMainPanel() {
		mainPanel = new MainPanel();
		
		mainPanel.setBtnPreviousFrameEnabled(false);
		mainPanel.setBtnPreviousBlockEnabled(false);
		
		mainPanel.setHeatMap(HeatMap.generateRampTestData());
		
		mainPanel.setBtnPreviousFrameListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				goToPreviousFrame();
				showResults();
				setControlButtonsState();
			}
		});
		
		mainPanel.setBtnPreviousBlockListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				goToPreviousBlock();
				showResults();
				setControlButtonsState();
			}
		});
		
		mainPanel.setBtnNextBlockListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				goToNextBlock();
				showResults();
				setControlButtonsState();
			}
		});
		
		mainPanel.setBtnNextFrameListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				goToNextFrame();
				showResults();
				setControlButtonsState();
			}
		});
		
		mainPanel.setBtnBackToSetupListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				goToSetupPanel();
			}
		});

		container.add(mainPanel, ME.MAIN_PANEL);
	}
	
	private void setCodingBlockIndex(int index) {
		codingBlockIndex = index;
		goToBlock(index);
	}
	
	private void getConfigs() {
		frameWidth = setupPanel.getFrameWidth();
		frameHeight = setupPanel.getFrameHeight();
		samplingYCbCr = setupPanel.getSampling();
		framesTotal = setupPanel.getTotalFrames();
		selectedAlgorithm = setupPanel.getSearchAlgorithm();
		searchAreaWidth = setupPanel.getSearchAreaWidth(); 
		searchAreaHeight = setupPanel.getSearchAreaHeight();
		blockWidth = setupPanel.getBlockWidth();
		blockHeight = setupPanel.getBlockHeight();
		keepReferenceFrame = setupPanel.getKeepReferenceFrameState();
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
		actualFrameIndex = ME.SECOND_FRAME;
		referenceFrameIndex = ME.FIRST_FRAME;
		codingBlockIndex = ME.FIRST_BLOCK;
		
		videoReader = new YUVReader(inputFile, frameWidth, frameHeight, samplingYCbCr);
		
		actualFrame = new Frame(frameWidth, frameHeight);
		referenceFrame = new Frame(frameWidth, frameHeight);
		
		videoReader.setFrameWithImage(actualFrame, actualFrameIndex);
		videoReader.setFrameWithImage(referenceFrame, referenceFrameIndex);
		
		fullSearchSad = new SumOfAbsoluteDifferences();
		fullSearch = new FullSearch();
		fullSearchME = new MotionEstimation(fullSearch, fullSearchSad, blockWidth, blockHeight, searchAreaWidth, searchAreaHeight);
		
		fullSearchME.setActualFrame(actualFrame);
		fullSearchME.setReferenceFrame(referenceFrame);
		
		switch (selectedAlgorithm) {
			case ME.FS:
			case ME.DS:
			case ME.TSS:
			case ME.EPZS:
			default:
				searchAlgorithm = new FullSearch();
		}
		
		searchAlgorithmSad = new SumOfAbsoluteDifferences();
		searchAlgorithmME = new MotionEstimation(searchAlgorithm, searchAlgorithmSad, blockWidth, blockHeight, searchAreaWidth, searchAreaHeight);
		
		searchAlgorithmME.setActualFrame(actualFrame);
		searchAlgorithmME.setReferenceFrame(referenceFrame);
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
		
		mainPanel.setActualFrameIndex(actualFrameIndex, framesTotal);
		mainPanel.setReferenceFrameIndex(referenceFrameIndex, framesTotal);
		mainPanel.setCodingBlockPosition(codingBlock.getPosition().getX(), codingBlock.getPosition().getY());
		
		mainPanel.setBestVector(fullSearchVector);
		mainPanel.setResultVector(searchAlgorithmVector);
		
		mainPanel.setNumberOfBlocks(candidateBlocksTotal);
		mainPanel.setNumberOfBlocksVisited(blocksVisited);
		
		mainPanel.setHeatMap(heatMap);
	}
	
	private void goToMainPanel() {
		layout.show(container, ME.MAIN_PANEL);
	}
	
	private void goToSetupPanel() {
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
			
			setCodingBlockIndex(0);
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
			
			setCodingBlockIndex(0);
		}
	}
	
	private void goToPreviousBlock() {
		if (codingBlockIndex > 0) {
			setCodingBlockIndex(codingBlockIndex - 1);
		}
	}
	
	private void goToNextBlock() {
		int totalCodingBlocks = (frameWidth / blockWidth) * (frameHeight / blockHeight);

		if (codingBlockIndex < totalCodingBlocks) {
			setCodingBlockIndex(codingBlockIndex + 1);
		}
	}
	
	private void goToBlock(int index) {
		int blockShifting = blockWidth * index;
		int blockRow = blockShifting / frameWidth;
		int blockPositionX = blockShifting;
		int blockPositionY = blockRow * blockHeight;
		
		if (blockRow > 0) {
			blockPositionX = blockShifting % frameWidth;
		}
		
		this.fullSearchME.setCodingBlockPosition(blockPositionX, blockPositionY);
		this.searchAlgorithmME.setCodingBlockPosition(blockPositionX, blockPositionY);
	}
	
	private void setControlButtonsState() {
		int totalCodingBlocks = (frameWidth / blockWidth) * (frameHeight / blockHeight);
		
		// Set previous frame button state
		if (actualFrameIndex == 1) {
			mainPanel.setBtnPreviousFrameEnabled(false);
		}
		else {
			mainPanel.setBtnPreviousFrameEnabled(true);
		}
		
		// Set next frame button state
		if (actualFrameIndex == framesTotal - 1) {
			mainPanel.setBtnNextFrameEnabled(false);
		}
		else {
			mainPanel.setBtnNextFrameEnabled(true);
		}
		
		// Set previous block button state
		if (codingBlockIndex == 0) {
			mainPanel.setBtnPreviousBlockEnabled(false);
		}
		else {
			mainPanel.setBtnPreviousBlockEnabled(true);
		}
		
		// Set next block button state
		if (codingBlockIndex == totalCodingBlocks - 1) {
			mainPanel.setBtnNextBlockEnabled(false);
		}
		else {
			mainPanel.setBtnNextBlockEnabled(true);
		}
	}
	
}
