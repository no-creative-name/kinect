
package kinectapp;

import java.awt.Color;
import java.awt.Paint;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;


public class ResultsBarRenderer extends BarRenderer {
   
    private Paint[] colors;
    
    public ResultsBarRenderer() 
     { 
        this.colors = new Paint[] {Color.red, Color.blue, Color.green, 
          Color.yellow, Color.orange, Color.cyan, 
          Color.magenta, Color.blue}; 
     }
     public Paint getItemPaint(final int row, final int column) 
     { 
        CategoryDataset dataSet = getPlot().getDataset();
        String rowKey = (String)dataSet.getRowKey(row);
        String colKey = (String)dataSet.getColumnKey(column);
        double value = dataSet.getValue(rowKey, colKey).doubleValue();
        if (Math.abs(value) < 50) {
            return Color.green;
        }
        else if (Math.abs(value) < 200) {
            return Color.yellow;
        }
        else {
            return Color.red;
        }
     } 

}
