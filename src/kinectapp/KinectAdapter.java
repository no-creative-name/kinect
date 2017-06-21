
package kinectapp;


import edu.ufl.digitalworlds.j4k.J4KSDK; 
import edu.ufl.digitalworlds.j4k.Skeleton;
import java.util.Arrays;
import kinectapp.interfaces.KinectManager;

public class KinectAdapter extends J4KSDK{
   
    private final Factory factory;
    private final KinectManager kinectManager;
    
    private double[][][] coordinates;
    private long skeletonDataCounter = 0;
    private long oldSkeletonDataCounter = 0;
    
    
    public KinectAdapter (Factory factory) {
        
        this.factory = factory;
        this.kinectManager = this.factory.getKinectManager();
        
        this.coordinates = new double[6][25][3];
        
        for(int i = 0; i < 6; i++) {
            for (int j = 0; j < 25; j++) {
                Arrays.fill(this.coordinates[i][j], 0.0);
            }
        }
        
        this.startKinect();
    }
    
    
    public final void startKinect () {
        
        this.start(J4KSDK.COLOR|J4KSDK.DEPTH|J4KSDK.SKELETON);
        
    }
    
    public void stopKinect () {
        
        this.stop();
        
    }
    
    @Override
    public void onSkeletonFrameEvent(boolean[] skeleton_tracked, float[] positions, float[] orientations, byte[] joint_status) {
        
        factory.getFrameRateManager().startFrameDurationMeasurement();

        this.skeletonDataCounter++;
        int skeletonNumber = 0;
        
        for (int skeleton = 0; skeleton < skeleton_tracked.length; skeleton++) {
            if(skeleton_tracked[skeleton]) {
                Skeleton currentSkeleton = Skeleton.getSkeleton(skeleton,skeleton_tracked,positions,orientations,joint_status,this);
                for(int joint = 0; joint < 25; joint++) {
                    for(int axis = 0; axis < 3; axis++) {
                        this.coordinates[skeletonNumber][joint][axis] = currentSkeleton.get3DJoint(joint)[axis];
                    }
                }
                this.kinectManager.setSkeletonData(coordinates);
                skeletonNumber++;
            }
        }
        skeletonNumber = 0;
        
        factory.getFrameRateManager().stopFrameDurationMeasurement();
    }
    
    @Override
    public void onColorFrameEvent(byte[] color_frame) {
        
        if (this.skeletonDataCounter > this.oldSkeletonDataCounter) {
            this.kinectManager.setIsSkeletonDataAvailable(true);
        }
        
        else {
            this.kinectManager.setIsSkeletonDataAvailable(false);
        }
        
        this.oldSkeletonDataCounter = this.skeletonDataCounter;
        
    }

    @Override
    public void onDepthFrameEvent(short[] depth_frame, byte[] body_index, float[] xyz, float[] uv) {

    }   
    
}
