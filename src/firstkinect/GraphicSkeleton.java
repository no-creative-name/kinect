
package firstkinect;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class GraphicSkeleton extends JPanel {
    
    public List<GraphicJoint> joints;
    public Skeleton skeleton;
    public int skeletonId;
    public double relaxFactor;
    public boolean checkForClap;
    private int iterationCount;
    
    public GraphicSkeleton (Skeleton skeleton) {
        
        joints = new ArrayList<GraphicJoint>(25);
        this.skeleton = skeleton;
        this.skeletonId = this.skeleton.id;
        this.relaxFactor = skeleton.getRelaxFactor();
        this.checkForClap = skeleton.getIfClap();
        initJoints();
  
    }
    
    public void initJoints () {
        
        this.iterationCount = 0;
        
        for (Joint joint: this.skeleton.joints) {
            this.joints.add(iterationCount, GraphicJoint.fromJoint(joint));
            iterationCount++;
        }
        
    }
    
    public void updateJoints () {
        
        this.iterationCount = 0;
        
        for (Joint joint: this.skeleton.joints) {
            this.joints.set(iterationCount, GraphicJoint.fromJoint(joint));
            iterationCount++;
        }
        
        this.relaxFactor = skeleton.getRelaxFactor();
        this.checkForClap = skeleton.getIfClap();
        
        
    }

    
    public void render () {
        
        updateJoints();
        
    }
  
    
    public void update (Skeleton skeleton) {
        
        this.skeleton = skeleton;
        this.render();
        
    }
   
}
