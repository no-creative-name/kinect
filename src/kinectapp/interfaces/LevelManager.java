
package kinectapp.interfaces;

import java.util.List;
import kinectapp.Level;


public interface LevelManager {

    public void generateLevelsFromMP3s ();
    public List<Level> getAllLevels ();
    public Level getCurrentLevel ();
    public void setCurrentLevel (int id);
    public void previewCurrentLevel ();
    public void startCurrentLevel ();
    public void changeLevel ();
    
}
