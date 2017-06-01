
package kinectapp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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


public class Results extends JPanel implements ActionListener{
    
        Factory factory;
        ResultManager resultManager;
        GameStateManager gameStateManager;
        
        JFrame resultFrame;
        JButton yesBtn;
        JButton noBtn;
    
    Results (Factory factory) {
        
        this.factory = factory;
        this.resultManager = factory.getResultManager();
        this.gameStateManager = factory.getGameStateManager();
        
        this.resultFrame = new JFrame("Results");
        this.resultFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.resultFrame.setSize(factory.getLayoutManager().getWindowWidth(),factory.getLayoutManager().getWindowHeight());
        this.resultFrame.setLayout(new FlowLayout());
        
        this.yesBtn = new JButton("Yes");
        this.noBtn = new JButton ("No");
        
        this.yesBtn.addActionListener(this);
        this.noBtn.addActionListener(this);
        
        JLabel resultText = new JLabel(
                "You've reached a BPM of " + Math.round(this.resultManager.getUserBPM()*100.0)/100.0 + ", that's a deviation of " + Math.round(this.resultManager.getBPMDeviation()*100.0)/100.0 + "%! Want to play again?"
        );
        resultText.setFont(new Font(this.factory.getLayoutManager().getDefaultFontFamily(), Font.PLAIN, 30));
        
        this.add(resultText);
        
        this.resultFrame.add(this);
        this.resultFrame.add(setupChart());
        this.resultFrame.add(this.yesBtn);
        this.resultFrame.add(this.noBtn);
        
        this.resultFrame.setVisible(true);
    }
        
    public JPanel setupChart () {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < this.resultManager.getUserTimesBetweenClaps().size(); i++) {
            dataset.setValue((long)this.resultManager.getUserTimesBetweenClaps().get(i)-(60000/this.factory.getLevelManager().getCurrentLevel().song.BPM),"", "Clap "+(i+1)+"");
        }
        JFreeChart chart = ChartFactory.createBarChart("","","", dataset, PlotOrientation.VERTICAL, false, false, false);
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
        if(ae.getSource() == this.yesBtn) {
            this.resultFrame.setVisible(false);
            this.gameStateManager.restartGame();
        }
        if(ae.getSource() == this.noBtn) {
            System.exit(0);
        }
    }
    
    
    
}
