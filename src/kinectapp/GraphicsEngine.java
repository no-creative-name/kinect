
package kinectapp;

import kinectapp.interfaces.GameStateManager;
import kinectapp.interfaces.LevelManager;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import kinectapp.interfaces.LayoutManager;
import kinectapp.interfaces.ResultManager;


public class GraphicsEngine extends JPanel{
    
    private final Factory factory;
    private final LayoutManager layoutManager;
    private final LevelManager levelManager;
    private final GameStateManager gameStateManager;
    private final ResultManager resultManager;
    
    private SkeletonService skeletonService;
    private List<GraphicSkeleton> graphicSkeletons;
    private Font defaultFont;
    private String infoText;
    private JLabel infoLabel;
    private boolean tick = false;
    private Timer timer;
    
    public GraphicsEngine (JFrame container, Factory factory) {
        
        this.factory = factory;
        this.layoutManager = factory.getLayoutManager();
        this.levelManager = factory.getLevelManager();
        this.gameStateManager = factory.getGameStateManager();
        this.resultManager = factory.getResultManager();
        
        this.defaultFont = new Font(this.layoutManager.getDefaultFontFamily(), Font.PLAIN, 40);
        
        this.setLayout(new GridLayout(10,1));
        
        this.setUpInfoText(container);

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
        
        this.removeAll();
        
        this.setUpInfoText(container);
        
        for (GraphicSkeleton graphicSkeleton : this.graphicSkeletons) {
            graphicSkeleton.resetBPMCounter();
        }
        
    }       
            
    private void setUpInfoText (JFrame container) {
        this.infoText = "Master BPM: " + this.levelManager.getCurrentLevel().song.BPM + "      Max number of claps: " + this.gameStateManager.getMasterClaps();

        if(this.gameStateManager.getDifficulty() == Difficulty.EASY) {
            this.infoText.concat("Your BPM: " + 0 + "      ");
        }

        this.infoLabel = new JLabel(this.infoText); 
        this.infoLabel.setFont(defaultFont);
        this.infoLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(this.infoLabel);
        container.add(this);
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
                joint.render(this.layoutManager.getDefaultJointColor());
            }
            
        }
    }
    
    private void paintBackground (Graphics g) {
        
        if(this.gameStateManager.getDifficulty() == Difficulty.EASY) {
            if(tick) {
                g.setColor(this.layoutManager.getDefaultBackgroundColor());
            }
            else {
                g.setColor(this.layoutManager.getBlinkingBackgroundColor());
            }
        }
        else {
            g.setColor(this.layoutManager.getDefaultBackgroundColor());
        }


        g.fillRect(0, 0, this.layoutManager.getWindowWidth(), this.layoutManager.getWindowHeight());
        
    }
    
    @Override
    public void paintComponent (Graphics g) {
        
        if(this.graphicSkeletons.size() > 0 && this.factory.getKinectManager().isSkeletonDataAvailable()) {
            
            if ((this.graphicSkeletons.get(0).getClapCount() >= this.gameStateManager.getMasterClaps())) {
                this.gameStateManager.stopGame();
            }

            if (this.gameStateManager.isRunning()) 
            {
                this.paintBackground(g);
                this.renderJoints(g);
            }
            else
            {
                this.resultManager.setUserBPM(this.graphicSkeletons.get(0).getBPM());
                this.resultManager.setUserClaps(this.graphicSkeletons.get(0).getClapCount());

                List x = new ArrayList<Double>();
                
                for (int i = 0; i < this.graphicSkeletons.get(0).getTimesBetweenClaps().size(); i++) {  
                    x.add(this.graphicSkeletons.get(0).getTimesBetweenClaps().get(i));
                    this.resultManager.setUserTimesBetweenClaps(x);
                }

                this.graphicSkeletons.get(0).resetBPMCounter();
            }
        }
            else
            {
                this.paintBackground(g);
            }

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


