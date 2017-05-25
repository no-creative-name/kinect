
package kinectapp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import org.jfree.data.category.DefaultCategoryDataset;


public class Game implements ActionListener {
    
    private Factory factory;
    
    private static final Object[][] SONGS = { 
        {"No Roots", 116},
        {"Mas Que Nada", 100},
        {"Call On Me", 126},
        {"E.T.", 75},
        {"Happy", 160},
        {"Applause", 140},
        {"Warwick Avenue", 84} 
    };
    
    // BPM to match and number of claps to match it
    
    // User's results
    
private boolean onPlayAgain = false;
    
    private Results resultsPanel;
    
    private String[] songNames;
    private JComboBox songList;
    private JButton playButton;
    private String soundSource = "src/songs/"+SONGS[0][0].toString().concat(".wav");;
    private boolean songIsPlaying = false;
    private AudioStream myStream;
     
    Game(Factory factory, JFrame f) {
        
        this.factory = factory;
        
        showSetup(f);
    }
    
    
    
    private void showSetup(JFrame frame) {
        
        factory.setMasterBPM((Integer)SONGS[0][1]);
        factory.setMasterClaps(3);
        
        songNames = new String[SONGS.length];
        
        for (int i = 0; i < SONGS.length; i++) {
            songNames[i] = SONGS[i][0].toString();
        }
        
        JPanel p = new JPanel(new BorderLayout(30,30));
        
        JPanel labels = new JPanel(new GridLayout(0,1,2,2));
        labels.add(new JLabel("Select song", SwingConstants.RIGHT));
        labels.add(new JLabel("", SwingConstants.RIGHT));
        labels.add(new JLabel("Number of claps", SwingConstants.RIGHT));
        labels.add(new JLabel("Easy mode", SwingConstants.RIGHT));
        p.add(labels, BorderLayout.WEST);
        
        JPanel controls = new JPanel(new GridLayout(0,1,2,2));
        songList = new JComboBox(songNames);
        songList.setSelectedIndex(0);
        songList.addActionListener(this);
        controls.add(songList);
        playButton = new JButton("▶");
        playButton.addActionListener(this);
        controls.add(playButton);
       //JTextField masterBPMField = new JTextField("" + this.masterBPM);
        //controls.add(masterBPMField);
        JTextField maxClapsField = new JTextField("" + factory.getMasterClaps());
        controls.add(maxClapsField);
        JCheckBox easyModeBox = new JCheckBox();
        controls.add(easyModeBox);
        p.add(controls, BorderLayout.EAST);

        JOptionPane.showMessageDialog(
            frame, p, "Setup", JOptionPane.QUESTION_MESSAGE
        );
        
        /*try {
            masterBPM = Integer.parseInt(masterBPMField.getText());   
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "BPM invalid. Please try again!");
            this.askUserForSetup(frame);
        }*/
        
        try {
            factory.setMasterClaps(Integer.parseInt(maxClapsField.getText()));   
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Number of claps invalid. Please try again!");
            this.showSetup(frame);
        }
        
        factory.setIsOnEasyMode(easyModeBox.isSelected());
        
        }
    
    public void showResults () {
        
        factory.setBPMDeviation(Math.round(Math.abs((factory.getMasterBPM() - factory.getUserBPMResult()) / factory.getMasterBPM() * 100)*100.0)/100.0);
        
        resultsPanel = new Results(this.factory);
        this.setupChart(resultsPanel);
        
        JLabel resultText = new JLabel(
                "You've reached a BPM of " + Math.round(factory.getUserBPMResult()*100.0)/100.0 + ", that's a deviation of " + Math.round(factory.getBPMDeviation()*100.0)/100.0 + "%! Want to play again?"
        );
        resultText.setFont(new Font("Roboto", Font.BOLD, 30));
        
        onPlayAgain = true;
        
        /*int reset = JOptionPane.showConfirmDialog(null, resultText, "Results", JOptionPane.YES_NO_OPTION);
        
        if(reset == JOptionPane.YES_OPTION) {
            onPlayAgain = true;
        }
        else {
            onPlayAgain = false;
        }*/
                
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == songList) {
            soundSource = "src/songs/"+songList.getSelectedItem().toString().concat(".wav");
            System.out.println(songList.getSelectedIndex());
            factory.setMasterBPM((Integer)SONGS[songList.getSelectedIndex()][1]);
        }
        if (e.getSource() == playButton) {
            if (songIsPlaying) {
                stopSong();
            }
            myStream = playSong();
            songIsPlaying = true;
        }
            
    }
        
    
    
    private AudioStream playSong () {
        try {
            // open the sound file as a Java input stream
            InputStream in = new FileInputStream(soundSource);

            // create an audiostream from the inputstream
            AudioStream audioStream = new AudioStream(in);

            // play the audio clip with the audioplayer class
            AudioPlayer.player.start(audioStream);
            
            return audioStream;
        }
        catch (Exception e) {
            System.out.println("no sound");
        }
        
        return null;
    }
    
    public void stopSong () {
        try {
            AudioPlayer.player.stop(myStream);
        }
        catch (Exception e) {
            System.out.println("no sound");
        }
    }
    
    public void setupChart (JPanel panel) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < factory.getUserTimesBetweenClaps().size(); i++) {
            dataset.setValue((long)factory.getUserTimesBetweenClaps().get(i)-(60000/factory.getMasterBPM()),"", "Clap"+i+"");
        }
        JFreeChart chart = ChartFactory.createBarChart("","","", dataset, PlotOrientation.VERTICAL, false, false, false);
        CategoryPlot catPlot = chart.getCategoryPlot();
        catPlot.setRangeGridlinePaint(Color.BLACK);
        
        ChartPanel chartPanel = new ChartPanel(chart);
        
        panel.add(chartPanel, BorderLayout.CENTER);
        panel.validate();
        
    }
}
