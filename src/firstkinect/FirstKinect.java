
package firstkinect;

public class FirstKinect {

   
    public static boolean isRunning = false;
    public static SkeletonGraphics skeletonGraphics;
    
    public static void update () {
        
        System.out.println("update");
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