
package kinectapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Skeleton {
    
    private Factory factory;
    
    private List<Joint> joints;
    private int id;
    
    private boolean hasClapped;
    private int clapCounter;
    private int mistakeCounter;
    
    private final double clapMistakeAllowanceRate = 0.2;
    private long prevClapTime;
    private long currentClapTime;
    private List timesBetweenClaps = new ArrayList<Double>();
    
    private double BPM;
    private double BPMFilter[] = new double[10];
    private final int FILTER_SIZE = 3;
    private double filteredBPM;
    private int filterIdx = 0;
    
    public Skeleton (Factory factory, double[][] currentSkeletonCoordinates, int id) {
        
        this.factory = factory;
        
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
    
    public double getBPM () {
        
        return this.filteredBPM;        
        
    }
    
    public List getTimesBetweenClaps () {
        return this.timesBetweenClaps;
    }

    
    
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
        
        if (this.getLengthOf(this.getVectorBetween(JointNames.HAND_LEFT, JointNames.HAND_RIGHT)) < 0.15
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
            Joint joint = new Joint(this.factory, id,currentSkeletonCoordinates[id][0],currentSkeletonCoordinates[id][1],currentSkeletonCoordinates[id][2]);
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
                    
                    if (this.currentClapTime-this.prevClapTime > 100) {
                        this.timesBetweenClaps.add((this.currentClapTime-this.prevClapTime));
                    
                        if(this.hasMadeTooManyMistakes()) {
                            this.resetBPMCounter();
                            this.prevClapTime = this.currentClapTime;
                        }
                        else {
                            this.prevClapTime = this.currentClapTime;
                            this.clapCounter++;
                            this.calculateBPM();
                        }
                    }
                    
                    
                }
                else {
                    this.prevClapTime = System.currentTimeMillis();
                    this.clapCounter++;
                }
                this.hasClapped = true;
                
            }
    }
    
    private boolean hasMadeTooManyMistakes () {
        
        /*if(this.tapDifferences.size() > 3 && 
           (long)this.tapDifferences.get(clapCounter-1) - (long)this.tapDifferences.get(clapCounter-2) < -(60000/this.BPM)*0.5)
        {
            System.out.println("ERROR");
        }*/
        if(this.timesBetweenClaps.size() > 3 && 
           Math.abs((long)this.timesBetweenClaps.get(clapCounter-1) - (long)this.timesBetweenClaps.get(clapCounter-2)) > (60000/this.BPM)*this.clapMistakeAllowanceRate)
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
    
    private boolean isUserWantingToResetClaps () {
        if(this.joints.get(JointNames.HAND_RIGHT).getFilteredY() < this.joints.get(JointNames.HEAD).getFilteredY()) {
            return true;
        }
        else {
            return false;
        }
    }
    
    private void calculateBPM () {
        
        long sum = 0;
        
        for (int i = 0; i < timesBetweenClaps.size(); i++) {
            sum += (long)timesBetweenClaps.get(i);
        }
        
        BPM =  (
                1/(double)(sum / timesBetweenClaps.size()) 
                * 
                60000
                );
        
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
    
    
    
    public void resetBPMCounter () {
        this.timesBetweenClaps.clear();
        this.BPM = 0;
        this.clapCounter = 0;
        this.prevClapTime = 0;
        Arrays.fill(BPMFilter, 0);
    }
    
    public void update (double[][] currentSkeletonCoordinates) {
        
        this.updateWholeBody(currentSkeletonCoordinates);
        
        if (this.isUserWantingToResetClaps()) {
            this.resetBPMCounter();
        }
        
        if(this.BPM != 0) {
            this.filterIdx = (this.filterIdx+1) % FILTER_SIZE;
            this.applyBPMFilter();
        }
        this.countClaps();
        
    }
       
}
