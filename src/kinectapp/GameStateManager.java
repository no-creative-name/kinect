
package kinectapp;


public interface GameStateManager {

    public void setDifficulty (DIFFICULTY difficulty);
    public DIFFICULTY getDifficulty ();
    
    public void startGame ();
    public void stopGame ();
    
    public boolean isRunning();
}
