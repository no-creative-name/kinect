
package kinectapp;

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


public class GraphicsEngine extends JPanel{
    
    private Factory factory;
    
    private SkeletonService skeletonService;
    private List<GraphicSkeleton> graphicSkeletons;
    private Font defaultFont = new Font("Roboto", Font.PLAIN, 40);
    private String infoText;
    private JLabel infoLabel;
    private boolean tick = false;
    private Timer timer;
    
    public GraphicsEngine (JFrame container, Factory factory) {
        
        this.factory = factory;
        
        this.setLayout(new BorderLayout());
        
        this.infoText = "Master BPM: " + factory.getMasterBPM() + "      Max number of claps: " + factory.getMasterClaps();
        

        if(factory.isOnEasyMode()) {
            this.infoText = this.infoText.concat("      Your BPM: " + 0);
        }

        this.infoLabel = new JLabel(this.infoText); 
        this.infoLabel.setFont(defaultFont);
        this.add(this.infoLabel, BorderLayout.PAGE_START);
        container.add(this);


        this.skeletonService = new SkeletonService(this.factory);
        this.graphicSkeletons = new ArrayList<GraphicSkeleton>();
        
        timer = new Timer(60000/(int)factory.getMasterBPM()/2, new ActionListener() {
                                             public void actionPerformed( ActionEvent e ) {
                                                 if(!tick) {
                                                     tick = true;
                                                             }
                                                 else {
                                                     tick = false;
                                                 }
                                             }
                                           });
        timer.setInitialDelay(60000/(int)factory.getMasterBPM()/2);
        timer.start();
        
       
    }
    
    public void reset (JFrame container) {
        
        this.infoText = "Master BPM: " + factory.getMasterBPM() + "      Max number of claps: " + factory.getMasterClaps();
        

        if(factory.isOnEasyMode()) {
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
        
        if (!skeletonService.isSkeletonLost() && factory.isGameRunning(this.graphicSkeletons.get(0))) 
        {
            super.paintComponent(g);
            if(factory.isOnEasyMode()) {
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
            
            
            g.fillRect(0, 0, factory.getWindowWidth(), factory.getWindowHeight());
            
            this.renderJoints(g);
        }
        else if (!skeletonService.isSkeletonLost() && factory.isGameToBeOver(this.graphicSkeletons.get(0))) 
        {
            
            factory.setUserBPMResult(this.graphicSkeletons.get(0).getBPM());
            factory.setUserClaps(this.graphicSkeletons.get(0).getClapCount());
            
            for (int i = 0; i < this.graphicSkeletons.get(0).getTimesBetweenClaps().size(); i++) {  
                factory.addToUserTimesBetweenClaps((int)this.graphicSkeletons.get(0).getTimesBetweenClaps().get(i));
            }

            factory.setIsGameOver(true);
            this.graphicSkeletons.get(0).resetBPMCounter();
        }
        else
        {
            
            if(factory.isOnEasyMode()) {
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
            g.fillRect(0, 0, factory.getWindowWidth(), factory.getWindowHeight());
           
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


