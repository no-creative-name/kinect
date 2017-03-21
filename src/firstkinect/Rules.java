
package firstkinect;


public class Rules {
    
    
    public static boolean isLeftHandAboveHead (double[][] coordinates) {
        
        if (coordinates[3][1] < coordinates[7][1]) {
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
    
    public static boolean isRightHandAboveHead (double rightHandCoordinate, double headCoordinate) {
        
        if (headCoordinate < rightHandCoordinate) {
            return true;
        }
        
        else {
            return false;
        }
 
    }
    
}
