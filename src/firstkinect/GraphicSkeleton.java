
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
    private int handOverHeadCounter = 0;
    
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
       
        if (!KinectAdapter.skeletonLost) {
            super.paint(g);
            
            g.setColor(Color.white);
            g.fillRect(0, 0, 1000, 1000);
            
            for (GraphicJoint joint : joints) {
                if(joint.id == 3 || joint.id == 7) {
                    joint.setGraphics(g);
                    joint.render(Color.blue);
                }
                
            }
           
            
            if (Rules.isRightHandAboveHead(this.joints.get(7).y, this.joints.get(3).y)) {
                this.handOverHeadCounter++;
            }
            
            System.out.println(handOverHeadCounter);
            
        }
        
        else
        {
            g.setColor(Color.white);
            g.fillRect(0, 0, 1000, 1000);
        }
        
    }
    
    public void update (Skeleton skeleton) {
        
        this.skeleton = skeleton;
        this.render();
        
    }
   
}
