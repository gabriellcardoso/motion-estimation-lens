import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.smartcardio.Card;
import javax.swing.JFrame;

import br.edu.ufpel.inf.gui.MainPanel;
import br.edu.ufpel.inf.gui.SetupPanel;
import br.edu.ufpel.inf.motionestimation.IEvaluationCriteria;
import br.edu.ufpel.inf.motionestimation.SumOfAbsoluteDifferences;
import br.edu.ufpel.inf.utils.CodingBlock;
import br.edu.ufpel.inf.utils.Frame;
import br.edu.ufpel.inf.utils.YUVReader;

public class MotionEstimationLens extends JFrame {
	
	private static final int INITIAL_BLOCK_X = 0;
	private static final int INITIAL_BLOCK_Y = 0;
	private static final int BLOCK_SIZE = 16;
	private static final int HEATMAP_SIZE = 128;
	
	private static final String MAIN_PANEL = "Main Panel";
	private static final String SETUP_PANEL = "Setup Panel";
	
	// Video file related attributes
	private String fileName;
	private int frameWidth;
	private int frameHeight;
	private int samplingYCbCr;
	
	// Motion estimation related attributes
	private YUVReader videoReader;
	
	private Frame actualFrame;
	private Frame referenceFrame;
	
	private IEvaluationCriteria evaluationCriteria;
	private CodingBlock codingBlock;
	
	// GUI attributes
	private Container container;
	private CardLayout layout;
	
	private MainPanel mainPanel;
	private SetupPanel setupPanel;
	
	public MotionEstimationLens() {
		super("Motio Estimation Lens");
		
		container = getContentPane();
		layout = new CardLayout();
		
		setLayout(layout);
	}
	
	public MotionEstimationLens(String fileName, int frameWidth, int frameHeight, int samplingYCbCr) {
		super("Motion Estimation Lens");
		
		this.fileName = fileName;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		this.samplingYCbCr = samplingYCbCr;
		
		setUpModels();
		
		container = this.getContentPane();
		layout = new CardLayout();
		
		setLayout(layout);
		
		setupPanel = new SetupPanel();
		mainPanel = new MainPanel();
		
		setupPanel.setStartButtonListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				layout.next(container);
			}
			
		});
		
		mainPanel.updateHeatMap(evaluationCriteria.createHeatMap(HEATMAP_SIZE, HEATMAP_SIZE));
		mainPanel.setNextBlockButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean nextBlockButtonState = goToNextCodingBlock();
				mainPanel.setNextBlockButtonState(nextBlockButtonState);
				mainPanel.updateHeatMap(evaluationCriteria.createHeatMap(HEATMAP_SIZE, HEATMAP_SIZE));
			}
		});
		mainPanel.setBackButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				layout.previous(container);
			}
		});
		
		container.add(setupPanel, SETUP_PANEL);
		container.add(mainPanel, MAIN_PANEL);
	}
	
	private void setUpModels() {
		videoReader = new YUVReader(fileName, frameWidth, frameHeight, samplingYCbCr);
		
		actualFrame = new Frame(frameWidth, frameHeight);
		referenceFrame = new Frame(frameWidth, frameHeight);
		
		evaluationCriteria = new SumOfAbsoluteDifferences();
		codingBlock = new CodingBlock(BLOCK_SIZE, BLOCK_SIZE);
		
		videoReader.setFrameWithImage(actualFrame, 1);
		videoReader.setFrameWithImage(referenceFrame, 0);
		
		evaluationCriteria.setActualFrame(actualFrame);
		evaluationCriteria.setReferenceFrame(referenceFrame);

		codingBlock.getPosition().setX(INITIAL_BLOCK_X);
		codingBlock.getPosition().setY(INITIAL_BLOCK_Y);
		
		evaluationCriteria.setCodingBlock(codingBlock);
	}
	
	 private boolean goToNextCodingBlock() {
    	int blockWidth = codingBlock.getWidth();
    	int blockHeight = codingBlock.getHeight();
    	
    	int blockCoordinateX = codingBlock.getPosition().getX();
    	int blockCoordinateY = codingBlock.getPosition().getY();
    	
    	int nextCoordinateX = blockCoordinateX + blockWidth;
    	int nextCoordinateY = blockCoordinateY + blockHeight;
    	
    	if (nextCoordinateX + blockWidth > frameWidth) {
    		nextCoordinateX = 0;
    		nextCoordinateY += blockHeight;
    	}
    	
    	if (nextCoordinateY + blockHeight > frameHeight) {
    		return false;
    	}

    	codingBlock.getPosition().setX(nextCoordinateX);
    	codingBlock.getPosition().setY(nextCoordinateY);
    	
    	return true;
    }
	 
}
