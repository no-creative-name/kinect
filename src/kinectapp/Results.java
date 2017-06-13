
package kinectapp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import kinectapp.interfaces.GameStateManager;
import kinectapp.interfaces.ResultManager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class Results implements ActionListener{
    
        Factory factory;
        ResultManager resultManager;
        GameStateManager gameStateManager;
        
        JFrame resultFrame;
        JButton playAgainBtn;
        JButton exitBtn;
    
    Results (Factory factory) {
        
        this.factory = factory;
        this.resultManager = factory.getResultManager();
        this.gameStateManager = factory.getGameStateManager();
        
        this.resultFrame = new JFrame("Results");
        this.resultFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.resultFrame.setSize(factory.getLayoutManager().getWindowWidth(),factory.getLayoutManager().getWindowHeight());
        this.resultFrame.setLayout(new GridBagLayout());
        
        this.setupResults();
        
    }
    
    private void setupResults () {
        JLabel resultText = new JLabel(
                "Your BPM: " + Math.round(this.resultManager.getUserBPM()*100.0)/100.0 + "     Average deviation: " + Math.round(this.resultManager.getBPMDeviation()*100.0)/100.0 + "%"
        );
        resultText.setFont(new Font(this.factory.getLayoutManager().getDefaultFontFamily(), Font.PLAIN, 30));
        GridBagConstraints resultTextC = new GridBagConstraints();
        resultTextC.gridx = 0;
        resultTextC.gridy = 0;
        resultTextC.gridwidth = 2;
        resultTextC.insets = new Insets(0,0,30,0);
        
        this.resultFrame.add(resultText, resultTextC);
        
        GridBagConstraints chartC = new GridBagConstraints();
        chartC.fill = GridBagConstraints.HORIZONTAL;
        chartC.gridx = 0;
        chartC.gridy = 1;
        chartC.gridwidth = 2;
        
        this.resultFrame.add(this.setupChart(), chartC);
        
        this.playAgainBtn = new JButton("Play again");
        GridBagConstraints yesBtnC = new GridBagConstraints();
        yesBtnC.gridx = 0;
        yesBtnC.gridy = 2;
        yesBtnC.gridwidth = 2;
        yesBtnC.insets = new Insets(20,20,20,50);
        this.playAgainBtn.addActionListener(this);
        
        this.resultFrame.add(this.playAgainBtn, yesBtnC);
        
        /*this.exitBtn = new JButton ("Exit");
        GridBagConstraints noBtnC = new GridBagConstraints();
        noBtnC.gridx = 1;
        noBtnC.gridy = 2;
        noBtnC.insets = new Insets(20,20,20,50);
        this.exitBtn.addActionListener(this);
        
        
        this.resultFrame.add(this.exitBtn, noBtnC);*/
        this.resultFrame.setVisible(true);
    }
        
    private JPanel setupChart () {
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < this.resultManager.getUserTimesBetweenClaps().size(); i++) {
            dataset.setValue((long)this.resultManager.getUserTimesBetweenClaps().get(i)-(60000/this.factory.getLevelManager().getCurrentLevel().song.BPM),"", "Clap "+(i+1)+"");
        }
        JFreeChart chart = ChartFactory.createBarChart("Deviation of single claps","Claps","Deviation in ms", dataset, PlotOrientation.VERTICAL, false, false, false);
        CategoryPlot catPlot = chart.getCategoryPlot();
        catPlot.setRangeGridlinePaint(Color.BLACK);
        
        ChartPanel JFreeChartPanel = new ChartPanel(chart);
        JFreeChartPanel.setMaximumDrawWidth(4000);  
        JFreeChartPanel.setPreferredSize(new Dimension(1200,800));
        
        JPanel chartPanel = new JPanel();
        
        chartPanel.add(JFreeChartPanel, BorderLayout.CENTER);
        chartPanel.validate();
        
        chartPanel.setSize(new Dimension(2000, 2000));
        
        return chartPanel;
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == this.playAgainBtn) {
            this.resultFrame.setVisible(false);
            this.gameStateManager.restartGame();
        }
        if(ae.getSource() == this.exitBtn) {
            System.exit(0);
        }
    }
    
    
    
}
