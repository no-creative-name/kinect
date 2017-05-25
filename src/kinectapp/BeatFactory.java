
package kinectapp;

import java.util.ArrayList;
import java.util.List;


public class BeatFactory implements Factory {
    
    private final int WINDOW_WIDTH = 1920;
    private final int WINDOW_HEIGHT = 1080;
    
    private int masterClaps;
    
    private double userBPMResult;
    private double userClaps;
    private double BPMDeviation;
    
    public List userTimesBetweenClaps = new ArrayList<Double>();
    
    private boolean isOnEasyMode;
    private boolean isGameOver;
    
    private FrameRateManager frameRateManager;
    private LevelManager levelManager;
    private SongManager songManager;
    private GameStateManager gameStateManager;
    
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
    
    @Override
    public LevelManager getLevelManager() {
        if (this.levelManager == null) {
            this.levelManager = new BeatLevelManager(this.getSongManager());
        }
        return this.levelManager;
    }

    private SongManager getSongManager() {
        if (this.songManager == null) {
            this.songManager = new BeatSongManager();
        }
        return this.songManager;
    }

    @Override
    public GameStateManager getGameStateManager() {
        if (this.gameStateManager == null) {
            this.gameStateManager = new BeatGameStateManager();
        }
        return this.gameStateManager;
    }
    
}
