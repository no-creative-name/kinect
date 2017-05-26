
package kinectapp;

import kinectapp.interfaces.GameStateManager;
import kinectapp.interfaces.LevelManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
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
import javax.swing.SwingConstants;
import kinectapp.interfaces.ResultManager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class Game implements ActionListener {
    
    private Factory factory;
    private LevelManager levelManager;
    private GameStateManager gameStateManager;
    private ResultManager resultManager;

    private boolean onPlayAgain = false;
    
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
        factory.setMasterClaps(3);
        
        
        JPanel p = new JPanel(new BorderLayout(30,30));
        
        JPanel labels = new JPanel(new GridLayout(0,1,2,2));
        labels.add(new JLabel("Select song", SwingConstants.RIGHT));
        labels.add(new JLabel("", SwingConstants.RIGHT));
        labels.add(new JLabel("Number of claps", SwingConstants.RIGHT));
        labels.add(new JLabel("Easy mode", SwingConstants.RIGHT));
        p.add(labels, BorderLayout.WEST);
        
        JPanel controls = new JPanel(new GridLayout(0,1,2,2));
        songList = new JComboBox(this.levelManager.getAllLevels().toArray());
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
        
        if(easyModeBox.isSelected()) {
            this.gameStateManager.setDifficulty(DIFFICULTY.EASY);
        }
        else {
            this.gameStateManager.setDifficulty(DIFFICULTY.NORMAL);
        }
        
        }
    
    public void showResults () {
        
        this.resultManager.setBPMDeviation(Math.round(Math.abs((this.levelManager.getCurrentLevel().song.BPM - this.resultManager.getUserBPM()) / this.levelManager.getCurrentLevel().song.BPM * 100)*100.0)/100.0);
        
        resultsPanel = new Results(this.factory);
        this.setupChart(resultsPanel);
        
        JLabel resultText = new JLabel(
                "You've reached a BPM of " + Math.round(this.resultManager.getUserBPM()*100.0)/100.0 + ", that's a deviation of " + Math.round(this.resultManager.getBPMDeviation()*100.0)/100.0 + "%! Want to play again?"
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
            Level selectedLevel = (Level)songList.getSelectedItem();
            this.levelManager.setCurrentLevel(selectedLevel.id);
        }
        if (e.getSource() == playButton) {
            this.playButtonPressed();
        }
            
    }
    
    public void playButtonPressed() {
        this.levelManager.previewCurrentLevel();
    }
        
    
    
    public void setupChart (JPanel panel) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < this.resultManager.getUserTimesBetweenClaps().size(); i++) {
            dataset.setValue((long)this.resultManager.getUserTimesBetweenClaps().get(i)-(60000/this.levelManager.getCurrentLevel().song.BPM),"", "Clap "+(i+1)+"");
        }
        JFreeChart chart = ChartFactory.createBarChart("","","", dataset, PlotOrientation.VERTICAL, false, false, false);
        CategoryPlot catPlot = chart.getCategoryPlot();
        catPlot.setRangeGridlinePaint(Color.BLACK);
        
        ChartPanel chartPanel = new ChartPanel(chart);
        
        panel.add(chartPanel, BorderLayout.CENTER);
        panel.validate();
        
    }
}
