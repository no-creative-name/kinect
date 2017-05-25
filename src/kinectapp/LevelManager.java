
package kinectapp;

import java.util.List;


public interface LevelManager {

    public List<Level> getAllLevels ();
    public Level getCurrentLevel ();
    public void setCurrentLevel (int id);
    public void previewCurrentLevel ();
    public void startCurrentLevel ();
    
}
