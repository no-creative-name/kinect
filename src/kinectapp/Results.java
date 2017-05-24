
package kinectapp;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static kinectapp.Game.BPMDeviation;
import static kinectapp.Game.userBPM;
import static kinectapp.KinectApp.WINDOW_HEIGHT;
import static kinectapp.KinectApp.WINDOW_WIDTH;


public class Results extends JPanel{
    
    Results () {
        
        JFrame resultFrame = new JFrame("Results");
        resultFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        resultFrame.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        resultFrame.setVisible(true);
        
        this.setLayout(new BorderLayout());
        
        JLabel resultText = new JLabel(
                "You've reached a BPM of " + Math.round(userBPM*100.0)/100.0 + ", that's a deviation of " + Math.round(BPMDeviation*100.0)/100.0 + "%! Want to play again?"
        );
        resultText.setFont(new Font("Roboto", Font.BOLD, 30));
        
        this.add(resultText);
        
        resultFrame.add(this);
    }
}
