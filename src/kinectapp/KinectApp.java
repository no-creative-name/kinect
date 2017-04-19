
package kinectapp;

import javax.swing.JFrame;

public class KinectApp {

   
    public static boolean isRunning = false;
    public static SkeletonGraphics skeletonGraphics;
    
    

    public static void checkForExit () {
        
        isRunning = false;
        
    }

    public static void init (JFrame mainFrame) {
        
        skeletonGraphics = new SkeletonGraphics(mainFrame);
        
    }
    
    
    
    public static void update () {
        
        skeletonGraphics.update();
    }
    
    
    public static void main(String[] args) {

        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1000,1000);
        
        init(mainFrame);
        
        mainFrame.setVisible(true);
        
        isRunning = true;
        
        while(isRunning) {
            update();
            //checkForExit();
        }
        
        
    }

}