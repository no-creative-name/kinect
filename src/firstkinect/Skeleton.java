
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
    
    
}