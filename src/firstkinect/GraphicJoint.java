
package firstkinect;

import java.awt.Color;
import java.awt.Graphics;


public class GraphicJoint {
    
    public Graphics visualization;
    
    public Joint joint;
    
    public int x;
    public int y;
    public double z;
    
    public int id;
    
    public double width;
    public double height;
    
    /*public GraphicJoint (Graphics g, double x, double y, double z) {
        
        this.x = (int)x;
        this.y = (int)y;
        this.z = (int)z;
        
        this.width = this.height = 10;
        this.visualization = g;
        
    }
    
    public GraphicJoint (double x, double y, double z) {
        
        this.x = (int)x;
        this.y = (int)y;
        this.z = (int)z;
        
        this.width = this.height = 10;
        
    }*/
    
    public GraphicJoint (Joint joint) {
        
        double jointX = (joint.x+1)*500;
        double jointY = (joint.y+1)*500;
        double jointZ = 2-joint.z;
        
        this.x = (int)jointX;
        this.y = (int)jointY;
        this.z = jointZ;
        
        this.joint = joint;
        
        this.id = joint.id;
        
        if(this.z>0) {
            this.width = this.height = 25+25*this.z;
        }
        else {
            this.width = this.height = 25;
        }
        
        if(this.id == 3) {
            this.width = this.width+30;
            this.height = this.height+30;
        }
    }
    
    
    public void setGraphics (Graphics g) {
        
        this.visualization = g;
        
    }
    
    public static GraphicJoint fromJoint (Joint joint) {
        
        return new GraphicJoint(joint);
        
    } 
    
    public void render (Color color) {
        
        this.visualization.setColor(color);
        this.visualization.fillOval(this.x, this.y, (int)this.width, (int)this.height);
        
    }
}
