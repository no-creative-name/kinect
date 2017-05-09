
package kinectapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Skeleton {
    
    private List<Joint> joints;
    private int id;
    
    private boolean hasClapped;
    private int clapCounter;
    private int mistakeCounter;
    private int BPM;
    
    private final int FILTER_SIZE = 3;
    private int BPMFilter[] = new int[10];
    private int filteredBPM;
    private int filterIdx = 0;
    
    private final double clapMistakeAllowanceRate = 0.2;
    private long prevClapTime;
    private long currentClapTime;
    private List tapDifferences = new ArrayList<Double>();
    
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
        
        return this.filteredBPM;        
        
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
                (Math.abs(this.getDegreeBetween(
                        this.getVectorBetween(JointNames.SHOULDER_LEFT, JointNames.SPINE_MID),
                        this.getVectorBetween(JointNames.SHOULDER_LEFT, JointNames.ELBOW_LEFT)
                ))-270)
                +
                (Math.abs(this.getDegreeBetween(
                        this.getVectorBetween(JointNames.HIP_LEFT, JointNames.SPINE_BASE),
                        this.getVectorBetween(JointNames.HIP_LEFT, JointNames.KNEE_LEFT)
                ))-270)
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
                (Math.abs(this.getDegreeBetween(
                        this.getVectorBetween(JointNames.SHOULDER_RIGHT, JointNames.SPINE_MID),
                        this.getVectorBetween(JointNames.SHOULDER_RIGHT, JointNames.ELBOW_RIGHT)
                ))-270)
                +
                (Math.abs(this.getDegreeBetween(
                        this.getVectorBetween(JointNames.HIP_RIGHT, JointNames.SPINE_BASE),
                        this.getVectorBetween(JointNames.HIP_RIGHT, JointNames.KNEE_RIGHT)
                ))-270)
                ) 
                / 14.4;
        
        return relaxFactor;
        
    }
        
    public boolean isCurrentlyClapping () {
        
        if (this.getLengthOf(this.getVectorBetween(JointNames.HAND_LEFT, JointNames.HAND_RIGHT)) < 0.2
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
                this.hasClapped = false;
            }
            
        if (!this.hasClapped && this.isCurrentlyClapping()) 
            {
                
                if(this.prevClapTime != 0) {
                    this.currentClapTime = System.currentTimeMillis();
                    this.tapDifferences.add((this.currentClapTime-this.prevClapTime));
                    
                    if(this.clapsAreToBeReset()) {
                        System.out.println("RESET");
                        this.BPM = 0;
                        this.tapDifferences.clear();
                        this.clapCounter = 0;
                        this.prevClapTime = this.currentClapTime;
                    }
                    else {
                        this.prevClapTime = this.currentClapTime;
                        this.clapCounter++;
                        this.calculateBPM();
                    }
                    
                }
                else {
                    this.prevClapTime = System.currentTimeMillis();
                    this.clapCounter++;
                }
                this.hasClapped = true;
                
            }
    }
    
    private boolean clapsAreToBeReset () {
        if (this.hasMadeTooManyMistakes()) {
            return true;
        }
        else {
            return false;
        }
    }
    
    private boolean hasMadeTooManyMistakes() {
        
        
        if(this.tapDifferences.size() > 3 && 
           Math.abs((long)this.tapDifferences.get(clapCounter-1) - (long)this.tapDifferences.get(clapCounter-2)) > (60000/this.BPM)*this.clapMistakeAllowanceRate)
        {
            this.mistakeCounter++;
        }
        else {
            this.mistakeCounter = 0;
        }
        
        //System.out.println(this.mistakeCounter);
        
        if(this.mistakeCounter > 2)
        {    
            return true;
        }
        else {
            return false;
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
        
        //System.out.println(tapDifferences);
    }
    
    private void applyBPMFilter () {
        
        this.BPMFilter[filterIdx]=this.BPM;
        
        this.filteredBPM=0;
        
        for (int i=0; i<FILTER_SIZE; i++)
        {
            this.filteredBPM+=this.BPMFilter[filterIdx];
        }
        
        this.filteredBPM/=this.FILTER_SIZE;
      
    }
    
    private void resetBPMCounter () {
        this.tapDifferences.clear();
        this.BPM = 0;
        Arrays.fill(BPMFilter, 0);
    }
    
    public void update (double[][] currentSkeletonCoordinates) {
        
        this.updateWholeBody(currentSkeletonCoordinates);
        
        if(this.joints.get(JointNames.HAND_RIGHT).getFilteredY() < this.joints.get(JointNames.HEAD).getFilteredY()) {
            this.resetBPMCounter();
        }
        
        if(this.BPM != 0) {
            this.filterIdx = (this.filterIdx+1) % FILTER_SIZE;
            this.applyBPMFilter();
        }
        this.countClaps();
        
    }
       
}
