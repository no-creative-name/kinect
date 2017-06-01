
package kinectapp.interfaces;

import kinectapp.DIFFICULTY;


public interface GameStateManager {

    public void setDifficulty (DIFFICULTY difficulty);
    public DIFFICULTY getDifficulty ();
    
    public void setMasterClaps (int claps);
    public int getMasterClaps ();
    
    public boolean isRunning();
    
    public void startGame ();
    public void stopGame ();
    
    public boolean isToRestartGame ();
    
    public void restartGame ();
    public void closeGame ();
    
    public boolean isRestartButtonClicked ();
}
