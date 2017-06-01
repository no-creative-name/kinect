
package kinectapp.interfaces;


public interface KinectManager {
    
    public void setIsSkeletonDataAvailable (boolean skeletonDataAvailable);
    public boolean isSkeletonDataAvailable ();
    
    public void setSkeletonData (double [][][] coordinates);
    public double [][][] getSkeletonData ();
    public double [][] getSkeletonDataForSkeletonNumber (int skeletonNumber);
    
}
