
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
        
        if (headCoordinate > rightHandCoordinate) {
            return true;
        }
        
        else {
            return false;
        }
 
    }
   
    
}
