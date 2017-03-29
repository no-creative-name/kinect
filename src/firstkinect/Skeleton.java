
package firstkinect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Skeleton {
    
    public List<Joint> joints;
    public int id;
    
    public Skeleton (double[][] currentSkeletonCoordinates, int id) {
        
        this.joints = new ArrayList<Joint>();
        this.joints = createJoints(currentSkeletonCoordinates);
        this.id = id;
        
    }
    
    public List<Joint> createJoints (double[][] currentSkeletonCoordinates) {
        
        addWholeBody(currentSkeletonCoordinates);
        
        return joints;
        
    }
    
    public void update (double[][] currentSkeletonCoordinates) {
        
        updateWholeBody(currentSkeletonCoordinates);
        if(getVectorBetween(5,7)[0] != 0) {
            System.out.println(getDegreeBetween(this.getVectorBetween(5, 7), this.getVectorBetween(5, 4)));
        }
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
    
    // von a nach b
    private double[] getVectorBetween (int a, int b) {
        
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
    
    
}