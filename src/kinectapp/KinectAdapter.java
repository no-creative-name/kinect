
package kinectapp;


import edu.ufl.digitalworlds.j4k.J4KSDK; 
import edu.ufl.digitalworlds.j4k.Skeleton;

public class KinectAdapter extends J4KSDK{
   
    public static boolean skeletonLost = true;
    public static boolean skeletonDataAvailable = false;
    
    private double[][][] coordinates;
    private double[][][] emptyCoordinates;
    private boolean[] whichSkeletonsTracked;
    private static long skeletonDataCounter = 0;
    private static long oldSkeletonDataCounter = 0;
    private int skeletonCount;
    
    public KinectAdapter () {
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
        this.startKinect();
    }
    
    public void startKinect () {
        
        this.start(J4KSDK.COLOR|J4KSDK.DEPTH|J4KSDK.SKELETON);
        
    }
    
    public void stopKinect () {
        
        this.stop();
        
    }
    
    @Override
    public void onSkeletonFrameEvent(boolean[] skeleton_tracked, float[] positions, float[] orientations, byte[] joint_status) {

        skeletonDataAvailable = true;
        skeletonDataCounter++;
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
        skeletonNumber = 0;
    }
    
    @Override
    public void onColorFrameEvent(byte[] color_frame) {
        
        
        if (skeletonDataCounter > oldSkeletonDataCounter) {
            skeletonLost = false;
        }
        
        else {
            skeletonLost = true;
        }
        
        oldSkeletonDataCounter = skeletonDataCounter;
        
    }

    @Override
    public void onDepthFrameEvent(short[] depth_frame, byte[] body_index, float[] xyz, float[] uv) {

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
    
    public double[][][] getCurrentSkeletonCoordinates() {
        
        return coordinates;
        
    }
}
