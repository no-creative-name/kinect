
package kinectapp.modules;

import kinectapp.interfaces.LevelManager;
import kinectapp.interfaces.SongManager;
import java.util.ArrayList;
import java.util.List;
import kinectapp.Level;


public class BeatLevelManager implements LevelManager{
    
    private List<Level> allLevels;
    private int currentLevelId;
    private SongManager songManager;
    
    public BeatLevelManager (SongManager songManager) {
        
        this.songManager = songManager;
        
        this.allLevels = new ArrayList<Level>();
        Level example = new Level("No Roots", "No Roots.wav", 116);
        this.allLevels.add(example);
        
    }
    
    public List<Level> getAllLevels () {
        return this.allLevels;
    }
    public Level getCurrentLevel () {
        return this.allLevels.get(currentLevelId);
    }
    public void setCurrentLevel (int id) {
        this.currentLevelId = id;
    }
    public void previewCurrentLevel () {
        this.songManager.playSong(this.getCurrentLevel().song);
    }
    public void startCurrentLevel () {
        this.songManager.stopCurrentSong();
    }
    
}
