
package kinectapp;

import java.util.ArrayList;
import java.util.List;
import kinectapp.interfaces.KinectManager;

public class SkeletonService {
   
    private Factory factory;
    private KinectManager kinectManager;
    
    private List <Skeleton> skeletons;
    private KinectAdapter kinectAdapter;
    
    public SkeletonService (Factory factory) {
        
        this.factory = factory;
        this.kinectManager = this.factory.getKinectManager();
        
        this.kinectAdapter = new KinectAdapter(this.factory);
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
        
        if(this.kinectManager.isSkeletonDataAvailable()) {
        for (int skeletonId = 0; skeletonId < 1; skeletonId++) {
            if (this.getSkeletonBySkeletonId(skeletonId) == null) {
                Skeleton skeleton = new Skeleton(this.factory, this.kinectManager.getSkeletonDataForSkeletonNumber(skeletonId),skeletonId);
                this.skeletons.add(skeleton);
            }
            else {
                this.getSkeletonBySkeletonId(skeletonId).update(this.kinectManager.getSkeletonDataForSkeletonNumber(skeletonId));
            }
        }
        }
    }
            
        
}
    
        

    
