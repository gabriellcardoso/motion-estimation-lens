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
	private MainView mainView;
	private SetupView setupView;
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
				printConfigs();
				setUpModels();
				showResults();
				goToMainView();
			}
		});
		
		container.add(setupView, ME.SETUP_PANEL);
	}
	
	private void startMainPanel() {
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
		
		mainView.setBtnBackToSetupListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				goToSetupView();
			}
		});

		container.add(mainView, ME.MAIN_PANEL);
	}
	
	private void setCodingBlockIndex(int index) {
		codingBlockIndex = index;
		goToBlock(index);
	}
	
	private void getConfigs() {
		frameWidth = setupView.getFrameWidth();
		frameHeight = setupView.getFrameHeight();
		samplingYCbCr = setupView.getSampling();
		framesTotal = setupView.getTotalFrames();
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
		actualFrameIndex = ME.SECOND_FRAME;
		referenceFrameIndex = ME.FIRST_FRAME;
		codingBlockIndex = ME.FIRST_BLOCK;
		
		videoReader = new YUVReader(inputFile, frameWidth, frameHeight, samplingYCbCr);
		
		actualFrame = new Frame(frameWidth, frameHeight);
		referenceFrame = new Frame(frameWidth, frameHeight);
		
		videoReader.setFrameWithImage(actualFrame, actualFrameIndex);
		videoReader.setFrameWithImage(referenceFrame, referenceFrameIndex);
		
		mainView.setActualFrame(actualFrame);
		mainView.setReferenceFrame(actualFrame);
		
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
		
		mainView.setHeatMap(heatMap);
		
		mainView.setActualFrameIndex(actualFrameIndex, framesTotal);
		mainView.setReferenceFrameIndex(referenceFrameIndex, framesTotal);
		
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
		int totalCodingBlocks = (frameWidth / blockWidth + 1) * (frameHeight / blockHeight + 1);

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
			blockPositionX = ((blockShifting % frameWidth) / blockWidth) * blockWidth;
		}
		
		this.fullSearchME.setCodingBlockPosition(blockPositionX, blockPositionY);
		this.searchAlgorithmME.setCodingBlockPosition(blockPositionX, blockPositionY);
	}
	
	private void setControlButtonsState() {
		int totalCodingBlocks = (frameWidth / blockWidth + 1) * (frameHeight / blockHeight + 1);
		
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
		if (codingBlockIndex == 0) {
			mainView.setBtnPreviousBlockEnabled(false);
		}
		else {
			mainView.setBtnPreviousBlockEnabled(true);
		}
		
		// Set next block button state
		if (codingBlockIndex == totalCodingBlocks - 1) {
			mainView.setBtnNextBlockEnabled(false);
		}
		else {
			mainView.setBtnNextBlockEnabled(true);
		}
	}
	
}
