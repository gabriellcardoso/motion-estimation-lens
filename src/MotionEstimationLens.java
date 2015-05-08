import java.util.Date;

import br.edu.ufpel.inf.motionestimation.FullSearch;
import br.edu.ufpel.inf.motionestimation.MotionEstimation;
import br.edu.ufpel.inf.motionestimation.SumOfAbsoluteDifferences;
import br.edu.ufpel.inf.utils.Frame;
import br.edu.ufpel.inf.utils.YUVReader;
import br.edu.ufpel.inf.utils.YUVWriter;


public class MotionEstimationLens {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String fileName = args[0];
		int width = Integer.parseInt(args[1]);
		int height = Integer.parseInt(args[2]);
		int sampling = Integer.parseInt(args[3]);
 
		YUVReader reader = new YUVReader(fileName, width, height, sampling);
		YUVWriter writer = new YUVWriter(fileName + ".out");
		
		Frame actualFrame = new Frame(width, height);
		Frame referenceFrame = new Frame(width, height);
		Frame auxiliar;
		
		FullSearch fs = new FullSearch(32, 32);
		SumOfAbsoluteDifferences sad = new SumOfAbsoluteDifferences();
		MotionEstimation me = new MotionEstimation(fs, sad, 8, 8);
		
		int i;
		
		for (i = 1; i < 300; i++) {
			auxiliar = referenceFrame;
			referenceFrame = actualFrame;
			actualFrame = auxiliar;
			
			me.setActualFrame(actualFrame);
			me.setReferenceFrame(referenceFrame);
			
			reader.setFrameWithImage(actualFrame, i);
			writer.writeFrameOnFile(actualFrame);
			
			me.run();
		}
	}
}
