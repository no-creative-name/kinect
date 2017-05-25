
package kinectapp;

import javax.swing.JFrame;

public class KinectApp {

    private Factory factory;
    public boolean initiated = false;
    public GraphicsEngine graphicsEngine;
    
    public KinectApp (Factory factory) {
        this.factory = factory;
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
        mainFrame.setSize(factory.getWindowWidth(),factory.getWindowHeight());
            
        Game game = new Game(factory, mainFrame);
        
        if(!this.initiated) {
            init(mainFrame);
        }
        else {
            reset(mainFrame);
        }
        
        mainFrame.setVisible(true);
        this.initiated = true;
        
        game.stopSong();
        
        while(!factory.isGameOver()) {
            update();
        }
        
        mainFrame.setVisible(false);
        game.showResults();
        
        /*if(Game.onPlayAgain) {
            factory.setIsGameOver(false);
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

        Factory factory = new ClapGameFactory();
        KinectApp app = new KinectApp(factory);
        app.startGame();
        
    }

}