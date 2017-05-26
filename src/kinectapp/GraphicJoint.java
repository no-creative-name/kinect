
package kinectapp;

import java.awt.Color;
import java.awt.Graphics;


public class GraphicJoint {
    
    private Factory factory;
    
    private Graphics visualization;
    
    private Joint joint;
    
    private int x;
    private int y;
    private double z;
    
    private int id;
    
    private double width;
    private double height;
    
    private double currentSpeed;
    
    
    public GraphicJoint (Factory factory, Joint joint) {
        
        this.factory = factory;
        
        double jointX = (joint.getFilteredX()+1)*factory.getLayoutManager().getWindowWidth()*0.83/2;
        double jointY = (joint.getFilteredY()+1)*factory.getLayoutManager().getWindowHeight()/2-200;
        double jointZ = 2-joint.getFilteredZ();
        
        this.x = (int)jointX;
        this.y = (int)jointY;
        this.z = jointZ;
        
        this.joint = joint;
        
        this.id = joint.getId();
        
        this.currentSpeed = joint.getCurrentSpeed();
        
        if(this.z>0) {
            this.width = this.height = 10+25*this.z;
        }
        else {
            this.width = this.height = 10;
        }
        
        if(this.id == 3) {
            this.width = this.width+30;
            this.height = this.height+30;
        }
    }
    
    
    
    public void setGraphics (Graphics g) {
        
        this.visualization = g;
        
    }
    
    public static GraphicJoint fromJoint (Factory factory, Joint joint) {
        
        return new GraphicJoint(factory, joint);
        
    } 
    
    public void render (Color color) {
        
        this.visualization.setColor(color);
        this.visualization.fillOval(this.x, this.y, (int)this.width, (int)this.height);
        
    }
}
