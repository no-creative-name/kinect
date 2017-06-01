
package kinectapp;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;


public class GraphicSkeleton extends JPanel {
    
    private Factory factory;
    
    private List<GraphicJoint> joints;
    private Skeleton skeleton;
    private int skeletonId;
    private double relaxFactor;
    private int iterationCount;
    private int clapCount;
    private double BPM;
    private List timesBetweenClaps;
    
    public GraphicSkeleton (Factory factory, Skeleton skeleton) {
        
        this.factory = factory;
        
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
    
    public double getBPM () {
        
        return this.BPM;        
        
    }
    
    public double getRelaxFactor () {
        
        return this.relaxFactor;        
        
    }
    
    public List getTimesBetweenClaps () {
        return this.timesBetweenClaps;
    }
    
    public void resetBPMCounter () {
        
        this.skeleton.resetBPMCounter();
        
    }
    
    
    
    private void initJoints () {
        
        this.iterationCount = 0;
        
        for (Joint joint: this.skeleton.getJoints()) {
            this.joints.add(iterationCount, GraphicJoint.fromJoint(factory, joint));
            iterationCount++;
        }
        
    }
    
    private void updateJoints () {
        
        this.iterationCount = 0;
        
        for (Joint joint: this.skeleton.getJoints()) {
            this.joints.set(iterationCount, GraphicJoint.fromJoint(factory, joint));
            iterationCount++;
        }
        
        this.relaxFactor = skeleton.getRelaxFactor();
        this.clapCount = skeleton.getClapCount();
        this.BPM = skeleton.getBPM();
        this.timesBetweenClaps = skeleton.getTimesBetweenClaps();
        
    }

    private void render () {
        
        updateJoints();
        
    }
    
    
    
    public void update (Skeleton skeleton) {
        
        this.skeleton = skeleton;
        this.render();
        
    }
   
}
