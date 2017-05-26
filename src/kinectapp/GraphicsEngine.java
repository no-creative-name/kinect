
package kinectapp;

import kinectapp.interfaces.GameStateManager;
import kinectapp.interfaces.LevelManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import kinectapp.interfaces.ResultManager;


public class GraphicsEngine extends JPanel{
    
    private Factory factory;
    private LevelManager levelManager;
    private GameStateManager gameStateManager;
    private ResultManager resultManager;
    
    private SkeletonService skeletonService;
    private List<GraphicSkeleton> graphicSkeletons;
    private Font defaultFont = new Font("Roboto", Font.PLAIN, 40);
    private String infoText;
    private JLabel infoLabel;
    private boolean tick = false;
    private Timer timer;
    
    public GraphicsEngine (JFrame container, Factory factory) {
        
        this.factory = factory;
        this.levelManager = factory.getLevelManager();
        this.gameStateManager = factory.getGameStateManager();
        this.resultManager = factory.getResultManager();
        
        this.setLayout(new BorderLayout());
        
        this.infoText = "Master BPM: " + this.levelManager.getCurrentLevel().song.BPM + "      Max number of claps: " + factory.getMasterClaps();
        

        if(this.gameStateManager.getDifficulty() == DIFFICULTY.EASY) {
            this.infoText = this.infoText.concat("      Your BPM: " + 0);
        }

        this.infoLabel = new JLabel(this.infoText); 
        this.infoLabel.setFont(defaultFont);
        this.add(this.infoLabel, BorderLayout.PAGE_START);
        container.add(this);


        this.skeletonService = new SkeletonService(this.factory);
        this.graphicSkeletons = new ArrayList<GraphicSkeleton>();
        
        timer = new Timer(60000/(int)this.levelManager.getCurrentLevel().song.BPM/2, new ActionListener() {
                                             public void actionPerformed( ActionEvent e ) {
                                                 if(!tick) {
                                                     tick = true;
                                                             }
                                                 else {
                                                     tick = false;
                                                 }
                                             }
                                           });
        timer.setInitialDelay(60000/(int)this.levelManager.getCurrentLevel().song.BPM/2);
        timer.start();
        
       
    }
    
    public void reset (JFrame container) {
        
        this.infoText = "Master BPM: " + this.levelManager.getCurrentLevel().song.BPM + "      Max number of claps: " + factory.getMasterClaps();
        

        if(this.gameStateManager.getDifficulty() == DIFFICULTY.EASY) {
            this.infoText.concat("Your BPM: " + 0 + "      ");
        }

        this.infoLabel = new JLabel(this.infoText); 
        this.infoLabel.setFont(defaultFont);
        this.add(this.infoLabel, BorderLayout.PAGE_START);
        container.add(this);
        
        for (GraphicSkeleton graphicSkeleton : this.graphicSkeletons) {
            graphicSkeleton.resetBPMCounter();
        }
        
    }        
    
    public List<GraphicSkeleton> getGraphicSkeletons() {
        return this.graphicSkeletons;
    }
            
    
    private GraphicSkeleton getGraphicSkeletonForSkeleton (Skeleton skeleton) {
        
        return getGraphicSkeletonBySkeletonId(skeleton.getId());
        
    } 
    
    private void updateGraphicSkeleton (int skeletonId) {
        
        this.getGraphicSkeletonBySkeletonId(skeletonId).update(skeletonService.getSkeletonBySkeletonId(skeletonId));
        
    }
    
    private GraphicSkeleton getGraphicSkeletonBySkeletonId (int skeletonId) {
        
        GraphicSkeleton foundSkeleton = null;
        
        for (GraphicSkeleton graphicSkeleton : this.graphicSkeletons) {
            
            if(graphicSkeleton.getId() == skeletonId) {
                foundSkeleton = graphicSkeleton;
                break;
            }
            
        }
        
        return foundSkeleton; 
        
    }
    
    private void createGraphicSkeletonFromSkeleton (Factory factory, Skeleton skeleton) {
        
        GraphicSkeleton graphicSkeleton = new GraphicSkeleton(factory, skeleton);
        this.graphicSkeletons.add(graphicSkeleton);
        
    }
    
    
    
    private void renderJoints (Graphics g) {
        
        for (GraphicSkeleton skeleton : graphicSkeletons) {
            
            for (GraphicJoint joint : skeleton.getJoints()) {
                joint.setGraphics(g);
                joint.render(Color.red);
            }
            
            /*if(factory.isOnEasyMode()) {
                this.userResultText.setText("Your BPM: " + Math.round(skeleton.getBPM()*100.0)/100.0 + "      ");
            }*/
            
        }
    }
    
    @Override
    public void paintComponent (Graphics g) {
        
        if ((!skeletonService.isSkeletonLost() && this.graphicSkeletons.get(0).getClapCount() >= this.factory.getMasterClaps())) {
            this.gameStateManager.stopGame();
        }
        
        if (!skeletonService.isSkeletonLost() && this.gameStateManager.isRunning()) 
        {
            super.paintComponent(g);
            if(this.gameStateManager.getDifficulty() == DIFFICULTY.EASY) {
                if(tick) {
                    g.setColor(Color.white);
                }
                else {
                    g.setColor(Color.blue);
                }
            }
            else {
                g.setColor(Color.white);
            }
            
            
            g.fillRect(0, 0, factory.getLayoutManager().getWindowWidth(), factory.getLayoutManager().getWindowHeight());
            
            this.renderJoints(g);
        }
        else if (!skeletonService.isSkeletonLost()) 
        {
            
            this.resultManager.setUserBPM(this.graphicSkeletons.get(0).getBPM());
            this.resultManager.setUserClaps(this.graphicSkeletons.get(0).getClapCount());
            
            for (int i = 0; i < this.graphicSkeletons.get(0).getTimesBetweenClaps().size(); i++) {  
                List x = this.resultManager.getUserTimesBetweenClaps();
                x.add(this.graphicSkeletons.get(0).getTimesBetweenClaps().get(i));
                this.resultManager.setUserTimesBetweenClaps(x);
            }

            this.gameStateManager.stopGame();
            this.graphicSkeletons.get(0).resetBPMCounter();
        }
        else
        {
            
            if(this.gameStateManager.getDifficulty() == DIFFICULTY.EASY) {
                if(tick) {
                    g.setColor(Color.white);
                }
                else {
                    g.setColor(Color.blue);
                }
            }
            else {
                g.setColor(Color.white);
            }
            g.fillRect(0, 0, factory.getLayoutManager().getWindowWidth(), factory.getLayoutManager().getWindowHeight());
           
        }
        
       repaint();
        
    }
    
    public void update () {
        
        skeletonService.update();
        
        for (Skeleton skeleton : this.skeletonService.getAllSkeletons()) {
            if(this.getGraphicSkeletonForSkeleton(skeleton) == null){
                this.createGraphicSkeletonFromSkeleton(factory, skeleton);
            }
            else {
                this.updateGraphicSkeleton(skeleton.getId());
            }
        }
        
        this.repaint();
        
    }
    
}   


