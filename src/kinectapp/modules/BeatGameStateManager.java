
package kinectapp.modules;

import kinectapp.interfaces.GameStateManager;
import kinectapp.Difficulty;


public class BeatGameStateManager implements GameStateManager {

    private Difficulty currentDifficulty = Difficulty.NORMAL;
    private int masterClaps = 0;
    private boolean running;
    private volatile boolean toRestartGame;
    private volatile boolean restartButtonClicked;
    
    @Override
    public void setDifficulty(Difficulty difficulty) {
        this.currentDifficulty = difficulty;
    }

    @Override
    public Difficulty getDifficulty() {
        return this.currentDifficulty;
    }

    @Override
    public void setMasterClaps(int claps) {
        this.masterClaps = claps;
    }

    @Override
    public int getMasterClaps() {
        return this.masterClaps;
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

    @Override
    public boolean isToRestartGame() {
        return this.toRestartGame;
    }

    @Override
    public void restartGame() {
        this.toRestartGame = true;
        this.restartButtonClicked = true;
    }

    @Override
    public void closeGame() {
        this.toRestartGame = false;
        this.restartButtonClicked = true;
    }

    
    @Override
    public void resetGameState() {
        this.toRestartGame = false;
        this.restartButtonClicked = false;
    }

    
    @Override
    public boolean isRestartButtonClicked() {
        return this.restartButtonClicked;
    }
    
   
    
    
}
