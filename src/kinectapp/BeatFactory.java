
package kinectapp;

import kinectapp.modules.BeatGameStateManager;
import kinectapp.interfaces.GameStateManager;
import kinectapp.modules.BeatFrameRateManager;
import kinectapp.interfaces.FrameRateManager;
import kinectapp.interfaces.LevelManager;
import kinectapp.modules.BeatLevelManager;
import kinectapp.modules.BeatSongManager;
import kinectapp.interfaces.SongManager;
import kinectapp.interfaces.LayoutManager;
import kinectapp.interfaces.ResultManager;
import kinectapp.modules.BeatLayoutManager;
import kinectapp.modules.BeatResultManager;


public class BeatFactory implements Factory {
    
    
    private int masterClaps;
    
    private FrameRateManager frameRateManager;
    private LevelManager levelManager;
    private SongManager songManager;
    private GameStateManager gameStateManager;
    private ResultManager resultManager;
    private LayoutManager layoutManager;

    @Override
    public int getMasterClaps () {
        return this.masterClaps;
    }
    @Override
    public void setMasterClaps (int claps) {
        this.masterClaps = claps;
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
    
    @Override
    public ResultManager getResultManager () {
        if (this.resultManager == null) {
            this.resultManager = new BeatResultManager();
        }
        return this.resultManager;    
    }
    
    @Override
    public LayoutManager getLayoutManager () {
        if (this.layoutManager == null) {
            this.layoutManager = new BeatLayoutManager();
        }
        return this.layoutManager;    
    }
    
}
