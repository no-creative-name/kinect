
package kinectapp;

import kinectapp.interfaces.GameStateManager;
import kinectapp.interfaces.FrameRateManager;
import kinectapp.interfaces.LayoutManager;
import kinectapp.interfaces.LevelManager;
import kinectapp.interfaces.ResultManager;

public interface Factory {
    
    public int getMasterClaps ();
    public void setMasterClaps (int claps);
    
    
    public FrameRateManager getFrameRateManager ();
    
    public LevelManager getLevelManager ();
    
    public GameStateManager getGameStateManager ();
    
    public ResultManager getResultManager ();
    
    public LayoutManager getLayoutManager ();
}
