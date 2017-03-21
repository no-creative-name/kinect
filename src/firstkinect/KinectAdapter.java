
package firstkinect;


import edu.ufl.digitalworlds.j4k.J4KSDK; 
import edu.ufl.digitalworlds.j4k.Skeleton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KinectAdapter extends J4KSDK{
   
    //Skeleton currentSkeleton;
    double[][][] coordinates;
    double[][][] emptyCoordinates;
    public boolean[] whichSkeletonsTracked;
    public int skeletonCount;
    public volatile boolean skeletonDataAvailable;
    //List<Skeleton> existingSkeletons;
    
    public KinectAdapter () {
        //this.currentSkeleton = new Skeleton();
        this.coordinates = new double[6][25][3];
        this.emptyCoordinates = new double[6][25][3];
        
        //fill emptyCoordinates with zeros
        for (int skeleton = 0; skeleton < 6; skeleton++) {
                for(int joint = 0; joint < 25; joint++) {
                    for(int axis = 0; axis < 3; axis++) {
                        this.coordinates[skeleton][joint][axis] = 0;
                    }
                }
            }
        
        this.whichSkeletonsTracked = new boolean[6];
        this.skeletonCount = 0;
        this.skeletonDataAvailable = false;
        //this.existingSkeletons = new ArrayList<Skeleton>(6);
        this.startKinect();
    }
    
    public void startKinect () {
        
        this.start(J4KSDK.COLOR|J4KSDK.DEPTH|J4KSDK.SKELETON);
        
    }
    
    public void stopKinect () {
        
        this.stop();
        
    }
  
    public void getSkeletonData () {
        
        this.skeletonDataAvailable = true;
        int skeletonNumber = 0;
        
        ;
        
        for (int skeleton = 0; skeleton < this.getSkeletons().length; skeleton++) {
            if(this.getSkeletons()[skeleton] != null) {
                Skeleton currentSkeleton = this.getSkeletons()[skeleton];
                for(int joint = 0; joint < 25; joint++) {
                    for(int axis = 0; axis < 3; axis++) {
                        this.coordinates[skeletonNumber][joint][axis] = currentSkeleton.get3DJoint(joint)[axis];
                        //System.out.println(Arrays.deepToString(coordinates));
                    }
                }
                    
                skeletonNumber++;
            }
            else {
                for(int joint = 0; joint < 25; joint++) {
                    for(int axis = 0; axis < 3; axis++) {
                        this.coordinates[skeletonNumber][joint][axis] = this.emptyCoordinates[skeletonNumber][joint][axis];
                        //System.out.println(Arrays.deepToString(coordinates));
                    }
                }
            }
        }
        
        
        skeletonNumber = 0;
    }
    
    @Override
    public void onSkeletonFrameEvent(boolean[] skeleton_tracked, float[] positions, float[] orientations, byte[] joint_status) {
        
        /*this.skeletonDataAvailable = true;
        this.whichSkeletonsTracked = skeleton_tracked;
        int skeletonNumber = 0;
        
        for (int skeleton = 0; skeleton < skeleton_tracked.length; skeleton++) {
            if(skeleton_tracked[skeleton]) {
                Skeleton currentSkeleton = Skeleton.getSkeleton(skeleton,skeleton_tracked,positions,orientations,joint_status,this);
                for(int joint = 0; joint < 25; joint++) {
                    for(int axis = 0; axis < 3; axis++) {
                        this.coordinates[skeletonNumber][joint][axis] = currentSkeleton.get3DJoint(joint)[axis];
                    }
                }
                    
                skeletonNumber++;
            }
        }
        
        
        skeletonNumber = 0;*/
        
        
    }
   
    public int getCountOfExistingSkeletons () {
        
        int internalSkeletonCount = 0;
        
        for (boolean isTracked : this.whichSkeletonsTracked) {
            if (isTracked) {
                internalSkeletonCount++;
            }
        }
        
        this.skeletonCount = internalSkeletonCount;
        return skeletonCount;
    }
        
    /*public boolean skeletonDataAvailable () {
        
        
        for(boolean b : this.whichSkeletonsTracked) {
            if(b) {
                return true;
            }
        }
        return false;
    
    }*/
   

    @Override
    public void onColorFrameEvent(byte[] color_frame) {
            //System.out.println("A new color frame was received.");
    }

    @Override
    public void onDepthFrameEvent(short[] depth_frame, byte[] body_index, float[] xyz, float[] uv) {

    }   
    
    public void setSkeletonDataAvailable (boolean x) {
        this.skeletonDataAvailable = false;
    }
    
    public double[][][] getCurrentSkeletonCoordinates() {
        
        return coordinates;
        
    }
}
