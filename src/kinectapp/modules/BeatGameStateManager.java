
package kinectapp.modules;

import kinectapp.interfaces.GameStateManager;
import kinectapp.DIFFICULTY;


public class BeatGameStateManager implements GameStateManager {

    private DIFFICULTY currentDifficulty = DIFFICULTY.NORMAL;
    private boolean running;
    
    @Override
    public void setDifficulty(DIFFICULTY difficulty) {
        this.currentDifficulty = difficulty;
    }

    @Override
    public DIFFICULTY getDifficulty() {
        return this.currentDifficulty;
    }

    @Override
    public boolean isRunning() {
        return this.running;
    }

    @Override
    public void startGame() {
        this.running = true;
    }

    @Override
    public void stopGame() {
        this.running = false;
    }
   
    
    
}
