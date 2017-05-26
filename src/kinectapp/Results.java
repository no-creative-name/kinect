
package kinectapp;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import kinectapp.interfaces.ResultManager;


public class Results extends JPanel{
    
        Factory factory;
        ResultManager resultManager;
    
    Results (Factory factory) {
        
        this.factory = factory;
        this.resultManager = factory.getResultManager();
        
        JFrame resultFrame = new JFrame("Results");
        resultFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        resultFrame.setSize(factory.getLayoutManager().getWindowWidth(),factory.getLayoutManager().getWindowHeight());
        resultFrame.setVisible(true);
        
        this.setLayout(new BorderLayout());
        
        JLabel resultText = new JLabel(
                "You've reached a BPM of " + Math.round(this.resultManager.getUserBPM()*100.0)/100.0 + ", that's a deviation of " + Math.round(this.resultManager.getBPMDeviation()*100.0)/100.0 + "%! Want to play again?"
        );
        resultText.setFont(new Font("Roboto", Font.BOLD, 30));
        
        this.add(resultText);
        
        resultFrame.add(this);
    }
}
