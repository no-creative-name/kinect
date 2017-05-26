
package kinectapp.interfaces;

import java.util.List;


public interface ResultManager {
    
    public double getUserBPM ();
    public void setUserBPM (double BPM);
    
    public double getUserClaps ();
    public void setUserClaps (int claps);
    
    public List getUserTimesBetweenClaps ();
    public void setUserTimesBetweenClaps (List userTimesBetweenClaps);
    
    public double getBPMDeviation ();
    public void setBPMDeviation (double dev);
    
}
