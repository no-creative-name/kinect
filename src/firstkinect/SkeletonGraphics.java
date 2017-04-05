
package firstkinect;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
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
    long timeBefore;
    long currentTime;
    static List tapDifferences = new ArrayList<Double>();
    
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
        
    }

    private void countClaps (GraphicSkeleton skeleton) {
        
        if (!skeleton.isCurrentlyClapping) 
            {
                this.alreadyClapped = false;
            }
            
        if (!this.alreadyClapped && skeleton.isCurrentlyClapping) 
            {
                
                if(this.timeBefore != 0) {
                    this.currentTime = System.currentTimeMillis();
                    tapDifferences.add(
                            (this.currentTime-this.timeBefore)
                    );

                    if(tapDifferences.size() > 2 && (long)tapDifferences.get(clapCounter-1) - (long)tapDifferences.get(clapCounter-2) > 300) {
                        BPM = 0;
                        tapDifferences.clear();
                        clapCounter = 0;
                        this.timeBefore = this.currentTime;
                    }
                    else {
                        this.timeBefore = this.currentTime;
                        clapCounter++;
                        this.calculateBPM();
                    }
                }
                else {
                    this.timeBefore = System.currentTimeMillis();
                    clapCounter++;
                }
                this.alreadyClapped = true;
                
            }
    }
    
    private void calculateBPM () {
        
        long sum = 0;
        
        for (int i = 0; i < tapDifferences.size(); i++) {
            sum += (long)tapDifferences.get(i);
        }
        
        BPM =  (int)(
                1/(double)(sum / tapDifferences.size()) 
                * 
                60000
                );
    }
    
    public void renderJoints (Graphics g) {
        
        for (GraphicSkeleton skeleton : graphicSkeletons) {
            for (GraphicJoint joint : skeleton.joints) {
                joint.setGraphics(g);
                joint.render(Color.red);
            }
            
            this.countClaps(skeleton);
            this.infoText.setText("You are " + (int)(Math.round(skeleton.relaxFactor * 100d) / 100d) + "% relaxed! Claps: " + clapCounter + " BPM: " + BPM);
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
    
}   
