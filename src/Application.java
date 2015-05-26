import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import motionestimationlens.controllers.ApplicationController;



public class Application {
	
    private static void createAndShowGUI() throws Exception
    {
        ApplicationController application = new ApplicationController();
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.setSize(1280, 720);
        application.setResizable(true);
        application.setVisible(true);
    }

    public static void main(String[] args)
    {
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
