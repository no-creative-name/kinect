
package firstkinect;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class SkeletonGraphics extends JPanel{
    
    SkeletonService skeletonService;
    List<GraphicSkeleton> graphicSkeletons;
    JLabel infoText;
    boolean alreadyClapped;
    static int clapCounter;
    static int BPM;
    Timer timer = new Timer();
    
    public SkeletonGraphics (JFrame container) {
        
       this.infoText = new JLabel(""); 
       this.infoText.setFont(new Font("Roboto", Font.PLAIN, 40));
       this.add(this.infoText);
       container.add(this);
       this.skeletonService = new SkeletonService();
       this.graphicSkeletons = new ArrayList<GraphicSkeleton>();
       
    }
    
    private GraphicSkeleton getGraphicSkeletonForSkeleton (Skeleton skeleton) {
        
        return getGraphicSkeletonBySkeletonId(skeleton.id);
        
    } 
    
    private void updateGraphicSkeleton (int skeletonId) {
        
        this.getGraphicSkeletonBySkeletonId(skeletonId).update(skeletonService.getSkeletonBySkeletonId(skeletonId));
        
    }
    
    private GraphicSkeleton getGraphicSkeletonBySkeletonId (int skeletonId) {
        
        GraphicSkeleton foundSkeleton = null;
        
        for (GraphicSkeleton graphicSkeleton : this.graphicSkeletons) {
            
            if(graphicSkeleton.skeletonId == skeletonId) {
                foundSkeleton = graphicSkeleton;
                break;
            }
            
        }
        
        return foundSkeleton; 
        
    }
    
    private void createGraphicSkeletonFromSkeleton(Skeleton skeleton) {
        
        GraphicSkeleton graphicSkeleton = new GraphicSkeleton(skeleton);
        this.graphicSkeletons.add(graphicSkeleton);
        this.timer.schedule(new Task(), 20000);
        
    }
    
    public void update () {
        
        skeletonService.update();
        
        for (Skeleton skeleton : this.skeletonService.getAllSkeletons()) {
            if(this.getGraphicSkeletonForSkeleton(skeleton) == null){
                this.createGraphicSkeletonFromSkeleton(skeleton);
            }
            else {
                this.updateGraphicSkeleton(skeleton.id);
            }
        }
        
        this.repaint();
        
    }
    
    public void renderJoints (Graphics g) {
        
        for (GraphicSkeleton skeleton : graphicSkeletons) {
            for (GraphicJoint joint : skeleton.joints) {
                joint.setGraphics(g);
                joint.render(Color.red);
            }
            
            this.countClaps(skeleton);
            this.infoText.setText("You are " + (double)Math.round(skeleton.relaxFactor * 100d) / 100d + "% relaxed! Claps: " + clapCounter + " BPM: " + BPM);
        }
    }

    @Override
    public void paintComponent (Graphics g) {
       
        if (!KinectAdapter.skeletonLost) 
        {
            super.paintComponent(g);
            
            g.setColor(Color.white); 
            g.fillRect(0, 0, 1000, 1000);
            
            this.renderJoints(g);
           
        }
        
        else
        {
            g.setColor(Color.white);
            g.fillRect(0, 0, 1000, 1000);
           
        }
        
       repaint();
        
    }
    
    private void countClaps (GraphicSkeleton skeleton) {
         if (!skeleton.checkForClap) {
                this.alreadyClapped = false;
            }
            
            if (!this.alreadyClapped) 
            {
                if (skeleton.checkForClap) {
                    clapCounter++;
                    this.alreadyClapped = true;
                }
            }
    }
    
}   
 
class Task extends TimerTask
{
    
    @Override public void run() {
        System.out.println(SkeletonGraphics.clapCounter);
        SkeletonGraphics.BPM = SkeletonGraphics.clapCounter*3;
    }
    
}