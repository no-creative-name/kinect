
package kinectapp.modules;

import kinectapp.interfaces.KinectManager;


public class BeatKinectManager implements KinectManager {
    
    private boolean skeletonDataAvailable = false;
    private double[][][] skeletonData;
    
    @Override
    public void setIsSkeletonDataAvailable(boolean skeletonDataAvailable) {
        this.skeletonDataAvailable = skeletonDataAvailable;
    }

    @Override
    public boolean isSkeletonDataAvailable() {
        return this.skeletonDataAvailable;
    }

    @Override
    public void setSkeletonData(double[][][] coordinates) {
        this.skeletonData = coordinates;
    }

    @Override
    public double[][][] getSkeletonData() {
        return this.skeletonData;
    }

    @Override
    public double[][] getSkeletonDataForSkeletonNumber(int skeletonNumber) {
        return this.skeletonData[skeletonNumber];
    }
    
}
