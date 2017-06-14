
package kinectapp.modules;

import java.io.File;
import kinectapp.interfaces.SongManager;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import kinectapp.Song;


public class BeatSongManager implements SongManager {
    
    Song currentSong;
    String songDirectory = "src/songs/";
    MediaPlayer player;
    boolean mediaPlayerInitiated;
    JFXPanel fxPanel = new JFXPanel();
    
    public void playSong (Song song) {
        try {
           //this.stopCurrentSong();
           
           String songFile = this.songDirectory + song.fileName;
           Media currentSong = new Media(new File(songFile).toURI().toString());
           this.player = new MediaPlayer(currentSong);
           this.mediaPlayerInitiated = true;
           player.play();
       }
       catch (Exception e) {
           System.out.println("Couldn't start playing song: " + this.songDirectory + song.fileName);
       }
    }
    
    public void stopCurrentSong () {
        if(this.mediaPlayerInitiated) {
            try {
                this.player.stop();
            }
            catch (Exception e) {
                System.out.println("Couldn't stop playing song: " + this.songDirectory + this.currentSong.fileName);
            }
        }
    }
    
}
