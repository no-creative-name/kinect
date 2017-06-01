
package kinectapp;

import kinectapp.interfaces.GameStateManager;
import javax.swing.JFrame;

public class KinectApp {

    private Factory factory;
    private GameStateManager gameStateManager;
    public boolean initiated = false;
    public GraphicsEngine graphicsEngine;
    
    public KinectApp (Factory factory) {
        this.factory = factory;
        this.gameStateManager = factory.getGameStateManager();
    }

    public void init (JFrame mainFrame) {
        
        this.graphicsEngine = new GraphicsEngine(mainFrame, this.factory);
        
    }
    
    public void reset (JFrame mainFrame) {
        
        this.graphicsEngine.reset(mainFrame);
        
    }
    
    
    private void startGame () {
        
        JFrame mainFrame = new JFrame("BrainBeat");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(factory.getLayoutManager().getWindowWidth(),factory.getLayoutManager().getWindowHeight());
            
        Game game = new Game(factory, mainFrame);
        
        if(!this.initiated) {
            init(mainFrame);
        }
        else {
            reset(mainFrame);
        }
        
        mainFrame.setVisible(true);
        this.initiated = true;
        
        this.gameStateManager.startGame();
        factory.getLevelManager().startCurrentLevel();
        
        
        while(this.gameStateManager.isRunning()) {
            update();
        }
        
        mainFrame.setVisible(false);
        game.showResults();
        
        while(!this.gameStateManager.isRestartButtonClicked()) {
            if(this.gameStateManager.isToRestartGame()) {
                startGame();
            }
        }
    }
    
    public void update () {
        
        graphicsEngine.update();
    }
    
    
    public static void main(String[] args) {

        Factory factory = new BeatFactory();
        KinectApp app = new KinectApp(factory);
        app.startGame();
        
    }

}