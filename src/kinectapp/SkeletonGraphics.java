
package kinectapp;

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


public class SkeletonGraphics extends JPanel{
    
    private SkeletonService skeletonService;
    private List<GraphicSkeleton> graphicSkeletons;
    private JLabel infoText;
    private boolean tick = false;
    private Timer timer;
    
    public SkeletonGraphics (JFrame container) {
        
       this.infoText = new JLabel(""); 
       this.infoText.setFont(new Font("Roboto", Font.PLAIN, 40));
       this.add(this.infoText);
       container.add(this);
       this.skeletonService = new SkeletonService();
       this.graphicSkeletons = new ArrayList<GraphicSkeleton>();
       
       timer = new Timer(60000/120, new ActionListener() {
                                            public void actionPerformed( ActionEvent e ) {
                                                if(!tick) {
                                                    tick = true;
                                                            }
                                                else {
                                                    tick = false;
                                                }
                                            }
                                          });
       timer.setInitialDelay(60000/120);
       timer.start();
       
       
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
    
    private void createGraphicSkeletonFromSkeleton(Skeleton skeleton) {
        
        GraphicSkeleton graphicSkeleton = new GraphicSkeleton(skeleton);
        this.graphicSkeletons.add(graphicSkeleton);
        
    }
    
    private void renderJoints (Graphics g) {
        
        for (GraphicSkeleton skeleton : graphicSkeletons) {
            
            for (GraphicJoint joint : skeleton.getJoints()) {
                joint.setGraphics(g);
                joint.render(Color.red);
            }
            
            if(skeleton.getBPM() == 0) {
                this.timer.setDelay(60000/120);
            }
            else {
                this.timer.setDelay(60000/skeleton.getBPM());
            }
            this.infoText.setText("You are " + (int)(Math.round(skeleton.getRelaxFactor() * 100d) / 100d) + "% relaxed! Claps: " + skeleton.getClapCount() + " BPM: " + skeleton.getBPM());
        }
    }

    @Override
    public void paintComponent (Graphics g) {
       
        if (!KinectAdapter.skeletonLost) 
        {
            super.paintComponent(g);
            
            if(tick) {
                g.setColor(Color.white);
            }
            else {
                g.setColor(Color.blue);
            }
            
            g.fillRect(0, 0, 1000, 1000);
            
            this.renderJoints(g);
        }
        
        else
        {
            if(tick) {
                g.setColor(Color.white);
            }
            else {
                g.setColor(Color.blue);
            }
            g.fillRect(0, 0, 1000, 1000);
           
        }
        
       repaint();
        
    }
    
    
    
    public void update () {
        
        skeletonService.update();
        
        for (Skeleton skeleton : this.skeletonService.getAllSkeletons()) {
            if(this.getGraphicSkeletonForSkeleton(skeleton) == null){
                this.createGraphicSkeletonFromSkeleton(skeleton);
            }
            else {
                this.updateGraphicSkeleton(skeleton.getId());
            }
        }
        
        this.repaint();
        
    }
    
}   


