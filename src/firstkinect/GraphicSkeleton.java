
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
       
        if (KinectAdapter.skeletonDataAvailable) {
            super.paint(g);

            for (GraphicJoint joint : joints) {
                if (joint.hasMoved) {
                    joint.setGraphics(g);
                    joint.render(Color.blue);
                }
                else {
                    
                }
            }
        }
        
        KinectAdapter.skeletonDataAvailable = false;
    }
    
    public void update (Skeleton skeleton) {
        
        this.skeleton = skeleton;
        this.render();
        
    }
   
}
