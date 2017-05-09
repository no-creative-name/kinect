
package kinectapp;

import java.util.ArrayList;
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
    
    public Skeleton getSkeletonBySkeletonId (int id) {
        
        Skeleton foundSkeleton = null;
        
        for (Skeleton skeleton : this.skeletons) {
            
            if(skeleton.getId() == id) {
                foundSkeleton = skeleton;
            }
            
        }
        
        return foundSkeleton; 
        
    }
    
    
    
    public void update () {
        
        this.skeletonsAvailable = true;
        
        if(KinectAdapter.skeletonDataAvailable) {
        for (int skeletonId = 0; skeletonId < 1; skeletonId++) {
            if (this.getSkeletonBySkeletonId(skeletonId) == null) {
                Skeleton skeleton = new Skeleton(kinectAdapter.getCurrentSkeletonCoordinates()[skeletonId],skeletonId);
                this.skeletons.add(skeleton);
            }
            else {
                this.getSkeletonBySkeletonId(skeletonId).update(kinectAdapter.getCurrentSkeletonCoordinates()[skeletonId]);
            }
        }
        }
    }
            
        
}
    
        

    
