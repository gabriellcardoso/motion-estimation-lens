import javax.swing.JFrame;
import javax.swing.SwingUtilities;



public class Application {
	
	private static String fileName;
	private static int width;
	private static int height;
	private static int sampling;

    private static void createAndShowGUI() throws Exception
    {
        MotionEstimationLens application = new MotionEstimationLens(fileName, width, height, sampling);
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.setSize(500,500);
        application.setVisible(true);
    }

    public static void main(String[] args)
    {
    	fileName = args[0];
		width = Integer.parseInt(args[1]);
		height = Integer.parseInt(args[2]);
		sampling = Integer.parseInt(args[3]);
 
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    createAndShowGUI();
                }
                catch (Exception e)
                {
                    System.err.println(e);
                    e.printStackTrace();
                }
            }
        });
    }

}
