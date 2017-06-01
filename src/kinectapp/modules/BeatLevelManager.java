
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
        this.allLevels.add(new Level("No Roots", "No Roots.wav", 116));
        this.allLevels.add(new Level("Applause", "Applause.wav", 140));
        this.allLevels.add(new Level("Mas Que Nada", "Mas Que Nada.wav", 100));
        
    }
    
    @Override
    public List<Level> getAllLevels () {
        return this.allLevels;
    }
    @Override
    public Level getCurrentLevel () {
        return this.allLevels.get(currentLevelId);
    }
    @Override
    public void setCurrentLevel (int id) {
        this.currentLevelId = id;
    }
    @Override
    public void previewCurrentLevel () {
        this.songManager.playSong(this.getCurrentLevel().song);
    }
    @Override
    public void startCurrentLevel () {
        this.songManager.stopCurrentSong();
    }
    
}
