
package kinectapp.modules;

import kinectapp.interfaces.FrameRateManager;


public class BeatFrameRateManager implements FrameRateManager{
    
    private long time = 0;
    private long prevTime = 0;
    private long lastFrameDuration = 0;
    
    public long getLastFrameDuration () {
        return lastFrameDuration;
    }
    
    public void startFrameDurationMeasurement () {
        this.time = System.currentTimeMillis();
        this.lastFrameDuration = this.time - this.prevTime;
    }
    
    public void stopFrameDurationMeasurement () {
        this.prevTime = this.time;
    }
}
