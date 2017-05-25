
package kinectapp;


public interface FrameRateManager {
    
    public long getLastFrameDuration ();
    
    public void startFrameDurationMeasurement ();
    
    public void stopFrameDurationMeasurement ();
}
