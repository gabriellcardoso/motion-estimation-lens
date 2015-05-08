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
		
		for (int i = 0; i < 300; i++) {
			reader.setFrameWithImage(actualFrame, i);
			writer.writeFrameOnFile(actualFrame);
		}
	}
}
