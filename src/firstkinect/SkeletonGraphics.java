
package firstkinect;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;


public class SkeletonGraphics {
    
    JFrame mainFrame;
    SkeletonService skeletonService;
    List<GraphicSkeleton> graphicSkeletons;
    
    public SkeletonGraphics () {
        
       this.skeletonService = new SkeletonService();
       this.graphicSkeletons = new ArrayList<GraphicSkeleton>();
       
       this.mainFrame = new JFrame();
       this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       this.mainFrame.setSize(1000,1000);
       
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
        
        GraphicSkeleton graphicSkeleton = new GraphicSkeleton(this.mainFrame, skeleton);
        this.graphicSkeletons.add(graphicSkeleton);
        
        this.mainFrame.setVisible(true);
        
    }
    
    public void update () {
        
        skeletonService.update();
        
        if (this.skeletonService.skeletonsAvailable) {
            for (Skeleton skeleton : this.skeletonService.getAllSkeletons()) {
                if(this.getGraphicSkeletonForSkeleton(skeleton) == null){
                    this.createGraphicSkeletonFromSkeleton(skeleton);
                }
                else {
                    this.updateGraphicSkeleton(skeleton.id);
                }
            }
        }
        else {
            this.removeAllGraphicSkeletons();
        }
       
    }
    
}   
 