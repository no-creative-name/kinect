
package kinectapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Skeleton {
    
    private List<Joint> joints;
    private int id;
    
    private boolean alreadyClapped;
    private int clapCounter;
    private int BPM;
    private long timeBefore;
    private long currentTime;
    private static List tapDifferences = new ArrayList<Double>();
    
    public Skeleton (double[][] currentSkeletonCoordinates, int id) {
        
        this.joints = new ArrayList<Joint>();
        this.joints = createJoints(currentSkeletonCoordinates);
        this.id = id;
        
    }
    
    
    
    public List<Joint> getJoints () {
        
        return this.joints;
        
    }
    
    public int getId () {
        
        return this.id;
        
    }
    
    public int getClapCount () {
        
        return this.clapCounter;        
        
    }
    
    public int getBPM () {
        
        return this.BPM;        
        
    }
    
        // a to b
    public double[] getVectorBetween (int a, int b) {
        
        double x = (this.joints.get(b).filteredOutputX-this.joints.get(a).filteredOutputX);
        double y = (this.joints.get(b).filteredOutputY-this.joints.get(a).filteredOutputY);
        double z = (this.joints.get(b).filteredOutputZ-this.joints.get(a).filteredOutputZ);
        
        double[] vector = {x,y,z};
        
        return vector;
    }
   
    public double getLengthOf (double[] vector) {
        
        double distance = Math.abs(Math.sqrt(
                            Math.pow(vector[0],2)+
                            Math.pow(vector[1],2)+
                            Math.pow(vector[2],2)
                        ));
        
        return distance;
        
    }
    
    public double getDegreeBetween (double[] vectorA, double[] vectorB) {
        
        double degree = Math.acos(
                (vectorA[0]*vectorB[0]+vectorA[1]*vectorB[1]+vectorA[2]*vectorB[2])/
                (this.getLengthOf(vectorA)*this.getLengthOf(vectorB))
        );
        
        return degree * 180.0 / Math.PI;
    }

    public double getRelaxFactor () {
        
        double relaxFactor = 
                (this.getDegreeBetween(
                        this.getVectorBetween(JointNames.ELBOW_LEFT, JointNames.WRIST_LEFT),
                        this.getVectorBetween(JointNames.ELBOW_LEFT, JointNames.SHOULDER_LEFT)
                ))
                +
                (this.getDegreeBetween(
                        this.getVectorBetween(JointNames.KNEE_LEFT, JointNames.ANKLE_LEFT),
                        this.getVectorBetween(JointNames.KNEE_LEFT, JointNames.HIP_LEFT)
                ))
                +
                (Math.abs(180-this.getDegreeBetween(
                        this.getVectorBetween(JointNames.SHOULDER_LEFT, JointNames.SPINE_MID),
                        this.getVectorBetween(JointNames.SHOULDER_LEFT, JointNames.ELBOW_LEFT)
                )))
                +
                (Math.abs(180-this.getDegreeBetween(
                        this.getVectorBetween(JointNames.HIP_LEFT, JointNames.SPINE_BASE),
                        this.getVectorBetween(JointNames.HIP_LEFT, JointNames.KNEE_LEFT)
                )))
                +
                (this.getDegreeBetween(
                        this.getVectorBetween(JointNames.ELBOW_RIGHT, JointNames.WRIST_RIGHT),
                        this.getVectorBetween(JointNames.ELBOW_RIGHT, JointNames.SHOULDER_RIGHT)
                ))
                +
                (this.getDegreeBetween(
                        this.getVectorBetween(JointNames.KNEE_RIGHT, JointNames.ANKLE_RIGHT),
                        this.getVectorBetween(JointNames.KNEE_RIGHT, JointNames.HIP_RIGHT)
                )
                +
                (Math.abs(180-this.getDegreeBetween(
                        this.getVectorBetween(JointNames.SHOULDER_RIGHT, JointNames.SPINE_MID),
                        this.getVectorBetween(JointNames.SHOULDER_RIGHT, JointNames.ELBOW_RIGHT)
                )))
                +
                (Math.abs(180-this.getDegreeBetween(
                        this.getVectorBetween(JointNames.HIP_RIGHT, JointNames.SPINE_BASE),
                        this.getVectorBetween(JointNames.HIP_RIGHT, JointNames.KNEE_RIGHT)
                )))
                );
        
        return relaxFactor / 11.5;
        
    }
        
    public boolean isCurrentlyClapping () {
        
        if (this.getLengthOf(this.getVectorBetween(JointNames.HAND_LEFT, JointNames.HAND_RIGHT)) < 0.1
         && this.getLengthOf(this.getVectorBetween(JointNames.HAND_LEFT, JointNames.HAND_RIGHT)) != 0 ) 
        {
            return true;
        }
        else {
            return false;
        }
        
    }

    
    
    public List<Joint> createJoints (double[][] currentSkeletonCoordinates) {
        
        addWholeBody(currentSkeletonCoordinates);
        
        return joints;
        
    }
    
    public void addWholeBody (double[][] currentSkeletonCoordinates) {
       
        for (int id = 0; id < 25; id++) {
            Joint joint = new Joint(id,currentSkeletonCoordinates[id][0],currentSkeletonCoordinates[id][1],currentSkeletonCoordinates[id][2]);
            this.joints.add(joint);
        }
    } 
    
    public void updateWholeBody (double[][] currentSkeletonCoordinates) {
       
        for (int id = 0; id < 25; id++) {
            this.joints.get(id).update(currentSkeletonCoordinates[id][0],currentSkeletonCoordinates[id][1],currentSkeletonCoordinates[id][2]);
        }
    } 
    
    

    private void countClaps () {
        
        if (!this.isCurrentlyClapping()) 
            {
                this.alreadyClapped = false;
            }
            
        if (!this.alreadyClapped && this.isCurrentlyClapping()) 
            {
                
                if(this.timeBefore != 0) {
                    this.currentTime = System.currentTimeMillis();
                    this.tapDifferences.add(
                            (this.currentTime-this.timeBefore)
                    );

                    if(this.tapDifferences.size() > 2 && Math.abs((long)this.tapDifferences.get(clapCounter-1) - (long)this.tapDifferences.get(clapCounter-2)) > 300) {
                        this.BPM = 0;
                        this.tapDifferences.clear();
                        this.clapCounter = 0;
                        this.timeBefore = this.currentTime;
                    }
                    else {
                        this.timeBefore = this.currentTime;
                        this.clapCounter++;
                        this.calculateBPM();
                    }
                }
                else {
                    this.timeBefore = System.currentTimeMillis();
                    this.clapCounter++;
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
   
    
    
    public void update (double[][] currentSkeletonCoordinates) {
        
        this.updateWholeBody(currentSkeletonCoordinates);
        this.countClaps();
        
    }
       
}