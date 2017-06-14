
package kinectapp;

import kinectapp.interfaces.GameStateManager;
import kinectapp.interfaces.LevelManager;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import kinectapp.interfaces.ResultManager;


public class Game implements ActionListener {
    
    private Factory factory;
    private LevelManager levelManager;
    private GameStateManager gameStateManager;
    private ResultManager resultManager;

    private Results resultsPanel;
    
    private JComboBox songList;
    private JButton playButton;
   
     
    Game(Factory factory, JFrame f) {
        
        this.factory = factory;
        this.levelManager = factory.getLevelManager();
        this.gameStateManager = factory.getGameStateManager();
        this.resultManager = factory.getResultManager();
        
        showSetup(f);
    }
    
    
    
    private void showSetup(JFrame frame) {
        
        this.levelManager.setCurrentLevel(0);
        this.gameStateManager.setMasterClaps(8);
        
        JPanel p = new JPanel(new GridBagLayout());
        
        JLabel songListLabel = new JLabel("Select song");
        GridBagConstraints songListLabelC = new GridBagConstraints();
        songListLabelC.fill = GridBagConstraints.HORIZONTAL;
        songListLabelC.gridx = 0;
        songListLabelC.gridy = 0;
        songListLabelC.insets = new Insets(20,20,20,50);
        
        p.add(songListLabel, songListLabelC);
        
        JLabel maxClapsFieldLabel = new JLabel("Number of claps");
        GridBagConstraints maxClapsFieldLabelC = new GridBagConstraints();
        maxClapsFieldLabelC.fill = GridBagConstraints.HORIZONTAL;
        maxClapsFieldLabelC.gridx = 0;
        maxClapsFieldLabelC.gridy = 2;
        maxClapsFieldLabelC.insets = new Insets(0,20,20,50);
        
        p.add(maxClapsFieldLabel, maxClapsFieldLabelC);
        
        JLabel easyModeBoxLabel = new JLabel("Easy mode");
        GridBagConstraints easyModeBoxLabelC = new GridBagConstraints();
        easyModeBoxLabelC.fill = GridBagConstraints.HORIZONTAL;
        easyModeBoxLabelC.gridx = 0;
        easyModeBoxLabelC.gridy = 3;
        easyModeBoxLabelC.insets = new Insets(0,20,20,50);
        
        p.add(easyModeBoxLabel, easyModeBoxLabelC);
        
        
        
        songList = new JComboBox(this.levelManager.getAllLevels().toArray());
        songList.setSelectedIndex(0);
        songList.addActionListener(this);
        GridBagConstraints songListC = new GridBagConstraints();
        songListC.fill = GridBagConstraints.HORIZONTAL;
        songListC.gridx = 1;
        songListC.gridy = 0;
        songListC.insets = new Insets(20,0,20,20);
        
        p.add(songList, songListC);
        
        playButton = new JButton("▶");
        playButton.addActionListener(this);
        GridBagConstraints playButtonC = new GridBagConstraints();
        playButtonC.fill = GridBagConstraints.HORIZONTAL;
        playButtonC.gridx = 2;
        playButtonC.gridy = 0;
        playButtonC.insets = new Insets(20,0,20,20);
        
        p.add(playButton, playButtonC);
        
        JTextField maxClapsField = new JTextField("" + this.gameStateManager.getMasterClaps());
        GridBagConstraints maxClapsFieldC = new GridBagConstraints();
        maxClapsFieldC.fill = GridBagConstraints.HORIZONTAL;
        maxClapsFieldC.gridx = 1;
        maxClapsFieldC.gridy = 2;
        maxClapsFieldC.insets = new Insets(0,0,20,20);
        
        p.add(maxClapsField, maxClapsFieldC);
        
        JCheckBox easyModeBox = new JCheckBox();
        GridBagConstraints easyModeBoxC = new GridBagConstraints();
        easyModeBoxC.fill = GridBagConstraints.HORIZONTAL;
        easyModeBoxC.gridx = 1;
        easyModeBoxC.gridy = 3;
        easyModeBoxC.insets = new Insets(0,0,20,20);
        
        p.add(easyModeBox, easyModeBoxC);
        
        JOptionPane.showMessageDialog(
            frame, p, "Setup", JOptionPane.PLAIN_MESSAGE
        );
        
        /*try {
            masterBPM = Integer.parseInt(masterBPMField.getText());   
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "BPM invalid. Please try again!");
            this.askUserForSetup(frame);
        }*/
        
        try {
            this.gameStateManager.setMasterClaps(Integer.parseInt(maxClapsField.getText()));
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Number of claps invalid. Please try again!");
            this.showSetup(frame);
        }
        
        if(easyModeBox.isSelected()) {
            this.gameStateManager.setDifficulty(Difficulty.EASY);
        }
        else {
            this.gameStateManager.setDifficulty(Difficulty.NORMAL);
        }
        
        }
    
    public void showResults () {
        
        this.resultManager.setBPMDeviation(Math.round(Math.abs((this.levelManager.getCurrentLevel().song.BPM - this.resultManager.getUserBPM()) / this.levelManager.getCurrentLevel().song.BPM * 100)*100.0)/100.0);
        
        this.resultsPanel = new Results(this.factory);
                
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == songList) {
            //Level selectedLevel = (Level)songList.getSelectedItem();
            this.levelManager.setCurrentLevel(this.songList.getSelectedIndex());
            this.factory.getLevelManager().changeLevel();
        }
        if (e.getSource() == playButton) {
            this.levelManager.previewCurrentLevel();
        }
            
    }
    
}
