
package firstkinect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SkeletonService {
   
    List <Skeleton> skeletons;
    KinectAdapter kinectAdapter;
    public boolean skeletonsAvailable;
    
    public SkeletonService () {
        
        this.kinectAdapter = new KinectAdapter();
        this.skeletonsAvailable = false;
        this.skeletons = new ArrayList<Skeleton>();
        
    }
    
    public List<Skeleton> getAllSkeletons () {
        
        return this.skeletons;
        
    }
    
    public void removeAllSkeletons () {
        
        this.skeletons.removeAll(skeletons);
       
    }
    
    public Skeleton getSkeletonBySkeletonId (int id) {
        
        Skeleton foundSkeleton = null;
        
        for (Skeleton skeleton : this.skeletons) {
            
            if(skeleton.id == id) {
                foundSkeleton = skeleton;
            }
            
        }
        
        return foundSkeleton; 
        
    }
    
       
    public void update () {
        
        //if (kinectAdapter.skeletonDataAvailable) {
            kinectAdapter.getSkeletonData();
            this.skeletonsAvailable = true;
            
            for (int skeletonId = 0; skeletonId < kinectAdapter.getCountOfExistingSkeletons(); skeletonId++) {
                if (this.skeletons.isEmpty()) {
                    System.out.println("");
                    Skeleton skeleton = new Skeleton(kinectAdapter.getCurrentSkeletonCoordinates()[skeletonId],skeletonId);
                    this.skeletons.add(skeleton);
                }
                else {
                    this.getSkeletonBySkeletonId(skeletonId).update(kinectAdapter.getCurrentSkeletonCoordinates()[skeletonId]);
                }
            }
            
            //kinectAdapter.skeletonDataAvailable = false;
        //}
        
        //else {    
        
            //this.removeAllSkeletons();
            //this.skeletonsAvailable = false;
        //}
        
       
    }
    
        
}
    
