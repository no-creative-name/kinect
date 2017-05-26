
package kinectapp;


public class Level {
    
    public int id;
    public Song song;
    
    public Level (String displayName, String songFileName, double BPM) {
        this.song = new Song(displayName, songFileName, BPM);
    }
    
    @Override
    public String toString () {
        return this.song.displayName;
    }
    
}

