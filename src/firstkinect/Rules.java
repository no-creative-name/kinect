
package firstkinect;


public class Rules {
    
    
    public static boolean isLeftHandAboveHead (int leftHandCoordinate, int headCoordinate) {
        
        if (headCoordinate < leftHandCoordinate) {
            return true;
        }
        
        else {
            return false;
        }
        
 
    }
  
    public static boolean isRightHandAboveHead (int rightHandCoordinate, int headCoordinate) {
        
        if (headCoordinate < rightHandCoordinate) {
            return true;
        }
        
        else {
            return false;
        }
 
    }
    
    public static boolean isDouble1HigherThanDouble2 (double x, double y) {
        
        if (x > y) {
            return true;
        }
        else {
            return false;
        }
        
        
    }
   
    
}
