
package kinectapp;

import java.io.FileInputStream;
import java.io.InputStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;


public class BeatSongManager implements SongManager {
    
    Song currentSong;
    String songDirectory = "src/songs/";
    AudioStream currentStream;
    
    public void playSong (Song song) {
        try {
           this.stopCurrentSong();
           
           InputStream in = new FileInputStream(this.songDirectory + song.fileName);

           this.currentStream = new AudioStream(in);

           AudioPlayer.player.start(currentStream);
       }
       catch (Exception e) {
           System.out.println("Couldn't start playing song: " + this.songDirectory + song.fileName);
       }
    }
    
    public void stopCurrentSong () {
        try {
            AudioPlayer.player.stop(this.currentStream);
        }
        catch (Exception e) {
            System.out.println("Couldn't stop playing song: " + this.songDirectory + this.currentSong.fileName);
        }
    }
    
}
