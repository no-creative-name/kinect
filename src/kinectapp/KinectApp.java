
package kinectapp;

import javax.swing.JFrame;

public class KinectApp {

   
    public boolean initiated = false;
    public GraphicsEngine graphicsEngine;
    public static final int WINDOW_WIDTH = 1920;
    public static final int WINDOW_HEIGHT = 1080;
    
    

    public void init (JFrame mainFrame) {
        
        this.graphicsEngine = new GraphicsEngine(mainFrame);
        
    }
    
    public void reset (JFrame mainFrame) {
        
        this.graphicsEngine.reset(mainFrame);
        
    }
    
    
    private void startGame () {
        
        JFrame mainFrame = new JFrame("BrainBeat");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
            
        Game game = new Game(mainFrame);
        
        if(!this.initiated) {
            init(mainFrame);
        }
        else {
            reset(mainFrame);
        }
        
        mainFrame.setVisible(true);
        this.initiated = true;
        
        game.stopSong();
        
        while(!Game.onGameOver) {
            update();
        }
        
        mainFrame.setVisible(false);
        game.showResults();
        
        /*if(Game.onPlayAgain) {
            Game.onGameOver = false;
            mainFrame.setVisible(false);
            startGame();
        }
        else {
            System.exit(0);
        }*/
        
    }
    
    public void update () {
        
        graphicsEngine.update();
    }
    
    
    public static void main(String[] args) {

        KinectApp app = new KinectApp();
        app.startGame();
        
    }

}