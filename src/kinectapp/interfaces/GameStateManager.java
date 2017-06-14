
package kinectapp.interfaces;

import kinectapp.Difficulty;


public interface GameStateManager {

    public void setDifficulty (Difficulty difficulty);
    public Difficulty getDifficulty ();
    
    public void setMasterClaps (int claps);
    public int getMasterClaps ();
    
    public boolean isRunning();
    
    public void startGame ();
    public void stopGame ();
    
    public boolean isToRestartGame ();
    
    public void restartGame ();
    public void closeGame ();
    
    public void resetGameState ();
    
    public boolean isRestartButtonClicked ();
}
