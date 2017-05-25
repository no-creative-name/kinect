
package kinectapp;

import java.util.List;

public interface Factory {
    
    public int getWindowWidth();
    public int getWindowHeight();
    
    public boolean isGameRunning (GraphicSkeleton skeleton);
    public boolean isGameToBeOver (GraphicSkeleton skeleton);
    
    public void setIsOnEasyMode (boolean isOnEasyMode);
    public boolean isOnEasyMode ();
    
    public void setIsGameOver (boolean isGameOver);
    public boolean isGameOver ();
    
    public int getMasterBPM ();
    public void setMasterBPM (int BPM);
  
    public int getMasterClaps ();
    public void setMasterClaps (int claps);
    
    public double getUserBPMResult ();
    public void setUserBPMResult (double BPM);
    
    public double getUserClaps ();
    public void setUserClaps (int claps);
    
    public List getUserTimesBetweenClaps ();
    public void setUserTimesBetweenClaps (List userTimesBetweenClaps);
    public void addToUserTimesBetweenClaps (int time);
    
    public double getBPMDeviation ();
    public void setBPMDeviation (double dev);
    
    
    public FrameRateManager getFrameRateManager ();
    
}
