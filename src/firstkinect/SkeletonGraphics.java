
package firstkinect;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class SkeletonGraphics extends JPanel{
    
    SkeletonService skeletonService;
    List<GraphicSkeleton> graphicSkeletons;
    
    public SkeletonGraphics (JFrame container) {
        
       container.add(this);
       this.skeletonService = new SkeletonService();
       this.graphicSkeletons = new ArrayList<GraphicSkeleton>();
       
    }
    
    private void removeAllGraphicSkeletons () {
        
        //System.out.println("removing all graphic skeletons");
        this.graphicSkeletons.removeAll(graphicSkeletons);
        
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
        }
    }
    
    @Override
    public void paintComponent (Graphics g) {
       
        if (!KinectAdapter.skeletonLost) {
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
}   
 