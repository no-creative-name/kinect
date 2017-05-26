
package kinectapp.modules;

import java.util.ArrayList;
import java.util.List;
import kinectapp.interfaces.ResultManager;


public class BeatResultManager implements ResultManager{
    
    private double userBPMResult;
    private double userClaps;
    private double BPMDeviation;
    public List userTimesBetweenClaps = new ArrayList<Double>();
    
    @Override
    public double getUserBPM () {
        return this.userBPMResult;
    }
    @Override
    public void setUserBPM (double BPM) {
        this.userBPMResult = BPM;
    }
    
    @Override
    public double getUserClaps () {
        return this.userClaps;
    }
    @Override
    public void setUserClaps (int claps) {
        this.userClaps = claps;
    }
    
    @Override
    public List getUserTimesBetweenClaps () {
        return this.userTimesBetweenClaps;
    }
    @Override
    public void setUserTimesBetweenClaps (List userTimesBetweenClaps) {
        this.userTimesBetweenClaps = userTimesBetweenClaps;
    }
    
    @Override
    public double getBPMDeviation () {
        return this.BPMDeviation;
    }
    @Override
    public void setBPMDeviation (double dev) {
        this.BPMDeviation = dev;
    }
    
}
