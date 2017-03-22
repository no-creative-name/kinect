
package firstkinect;

public class FirstKinect {

   
    public static boolean isRunning = false;
    public static int counter = 0;
    public static SkeletonGraphics skeletonGraphics;
    static long oldFrameNumber;
    static int oldCounter;
    public static void update () {
        
       
        skeletonGraphics.update();
    }
    
    public static void checkForExit () {
        
        isRunning = false;
        
    }

    public static void init () {
        
        skeletonGraphics = new SkeletonGraphics();
        
    }
    
    
    public static void main(String[] args) {

        init();
        
        isRunning = true;
        
        while(isRunning) {
            update();
            //checkForExit();
        }
        
        
    }

}