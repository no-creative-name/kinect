
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
    private int iterationCount;
    
    public GraphicSkeleton (JFrame container, Skeleton skeleton) {
        
        container.add(this);
        joints = new ArrayList<GraphicJoint>(25);
        this.skeleton = skeleton;
        this.skeletonId = this.skeleton.id;
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
        
    }
    
    public void render () {
        
        updateJoints();
        this.repaint();
        
    }
    
    @Override
    public void paint (Graphics g) {
       
        super.paint(g);
       
        for (GraphicJoint joint : joints) {
            joint.setGraphics(g);
            //if(Rules.isRightHandAboveHead(joints.get(11).y, joints.get(3).y)) {
                joint.render(Color.blue);
            //}
            //else {
            //    joint.render(Color.red);
            //}
        }
        
       
        
    }
    
    public void update (Skeleton skeleton) {
        
        this.skeleton = skeleton;
        this.render();
        
    }
   
}
