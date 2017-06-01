
package kinectapp;

import kinectapp.interfaces.GameStateManager;
import kinectapp.interfaces.FrameRateManager;
import kinectapp.interfaces.KinectManager;
import kinectapp.interfaces.LayoutManager;
import kinectapp.interfaces.LevelManager;
import kinectapp.interfaces.ResultManager;

public interface Factory {
    
    public KinectManager getKinectManager ();
    
    public FrameRateManager getFrameRateManager ();
    
    public LevelManager getLevelManager ();
    
    public GameStateManager getGameStateManager ();
    
    public ResultManager getResultManager ();
    
    public LayoutManager getLayoutManager ();
}
