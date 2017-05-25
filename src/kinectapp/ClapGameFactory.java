
package kinectapp;

import java.util.ArrayList;
import java.util.List;


public class ClapGameFactory implements Factory {
    
    private final int WINDOW_WIDTH = 1920;
    private final int WINDOW_HEIGHT = 1080;
    
    private FrameRateManager frameRateManager;
    private int masterBPM;
    private int masterClaps;
    
    private double userBPMResult;
    private double userClaps;
    private double BPMDeviation;
    
    public List userTimesBetweenClaps = new ArrayList<Double>();
    
    private boolean isOnEasyMode;
    private boolean isGameOver;
    
    @Override
    public int getWindowWidth () {
        return this.WINDOW_WIDTH;
    }
    @Override
    public int getWindowHeight () {
        return this.WINDOW_HEIGHT;
    }
    
    @Override
    public boolean isGameRunning (GraphicSkeleton skeleton) {
        if(skeleton.getClapCount() < this.getMasterClaps() && !this.isGameOver)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    @Override
    public boolean isGameToBeOver (GraphicSkeleton skeleton) {
        if(skeleton.getClapCount() == this.getMasterClaps() && !this.isGameOver)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    
    @Override
    public void setIsOnEasyMode (boolean isOnEasyMode) {
        this.isOnEasyMode = isOnEasyMode;
    }
    @Override
    public boolean isOnEasyMode () {
        return this.isOnEasyMode;
    }
    
    @Override
    public void setIsGameOver (boolean isGameOver) {
        this.isGameOver = isGameOver;
    }
    @Override
    public boolean isGameOver () {
        return this.isGameOver;
    }
    
    
    @Override
    public int getMasterBPM () {
        return this.masterBPM;
    }
    @Override
    public void setMasterBPM (int BPM) {
        this.masterBPM = BPM;
    }
    
    @Override
    public int getMasterClaps () {
        return this.masterClaps;
    }
    @Override
    public void setMasterClaps (int claps) {
        this.masterClaps = claps;
    }
    
    @Override
    public double getUserBPMResult () {
        return this.userBPMResult;
    }
    @Override
    public void setUserBPMResult (double BPM) {
        this.userBPMResult = BPM;
    }
    
    @Override
    public double getUserClaps () {
        return this.userClaps;
    }
    @Override
    public void setUserClaps (int claps) {
        this.userClaps = claps;
    }
    
    @Override
    public List getUserTimesBetweenClaps () {
        return this.userTimesBetweenClaps;
    }
    @Override
    public void setUserTimesBetweenClaps (List userTimesBetweenClaps) {
        this.userTimesBetweenClaps = userTimesBetweenClaps;
    }
    @Override
    public void addToUserTimesBetweenClaps (int time) {
        this.userTimesBetweenClaps.add(time);
    }
    
    @Override
    public double getBPMDeviation () {
        return this.BPMDeviation;
    }
    @Override
    public void setBPMDeviation (double dev) {
        this.BPMDeviation = dev;
    }
    
    
    @Override
    public FrameRateManager getFrameRateManager() {
        if (this.frameRateManager == null) {
            this.frameRateManager = new BeatFrameRateManager();
        }
        return this.frameRateManager;
    }

    
}
