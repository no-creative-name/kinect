
package kinectapp;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class GraphicSkeleton extends JPanel {
    
    private List<GraphicJoint> joints;
    private Skeleton skeleton;
    private int skeletonId;
    private double relaxFactor;
    private int iterationCount;
    private int clapCount;
    private int BPM;
    
    public GraphicSkeleton (Skeleton skeleton) {
        
        joints = new ArrayList<GraphicJoint>(25);
        this.skeleton = skeleton;
        this.skeletonId = this.skeleton.getId();
        this.relaxFactor = skeleton.getRelaxFactor();
        this.initJoints();
  
    }
  
    public List<GraphicJoint> getJoints () {
        
        return this.joints;
        
    }
    
    public int getId () {
        
        return this.skeletonId;
        
    }
    
    public int getClapCount () {
        
        return this.clapCount;        
        
    }
    
    public int getBPM () {
        
        return this.BPM;        
        
    }
    
    public double getRelaxFactor () {
        
        return this.relaxFactor;        
        
    }
    
    
    
    private void initJoints () {
        
        this.iterationCount = 0;
        
        for (Joint joint: this.skeleton.getJoints()) {
            this.joints.add(iterationCount, GraphicJoint.fromJoint(joint));
            iterationCount++;
        }
        
    }
    
    private void updateJoints () {
        
        this.iterationCount = 0;
        
        for (Joint joint: this.skeleton.getJoints()) {
            this.joints.set(iterationCount, GraphicJoint.fromJoint(joint));
            iterationCount++;
        }
        
        this.relaxFactor = skeleton.getRelaxFactor();
        this.clapCount = skeleton.getClapCount();
        this.BPM = skeleton.getBPM();
        
    }

    private void render () {
        
        updateJoints();
        
    }
    
    
    
    public void update (Skeleton skeleton) {
        
        this.skeleton = skeleton;
        this.render();
        
    }
   
}
