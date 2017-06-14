
package kinectapp.modules;

import java.io.File;
import kinectapp.interfaces.LevelManager;
import kinectapp.interfaces.SongManager;
import java.util.ArrayList;
import java.util.List;
import kinectapp.Level;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

public class BeatLevelManager implements LevelManager{
    
    private List<Level> allLevels;
    private int currentLevelId;
    private SongManager songManager;
    
    public BeatLevelManager (SongManager songManager) {
        
        this.songManager = songManager;
        
        
        
        this.allLevels = new ArrayList<Level>();
        this.generateLevelsFromMP3s();
        
    }
    

    @Override
    public void generateLevelsFromMP3s() {
        File folder = new File("src/songs");
        File[] listOfFiles = folder.listFiles();
        
        for(int i = 0; i < listOfFiles.length; i++) {
            File f = listOfFiles[i];
            AudioFile af = new AudioFile();
            try {
                af = AudioFileIO.read(f);
            }
            catch (Exception e) {

            }
            Tag tag = af.getTag();
            String title = tag.getFirst(FieldKey.TITLE);
            
            String artist = tag.getFirst(FieldKey.ARTIST);
            String fileName = artist + " - " + title + ".mp3";
            double BPM = Double.parseDouble(tag.getFirst(FieldKey.BPM));
            
            this.allLevels.add(new Level(title, fileName, BPM));
        }
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
    @Override
    public void changeLevel() {
        this.songManager.stopCurrentSong();
    }
   
    
}
