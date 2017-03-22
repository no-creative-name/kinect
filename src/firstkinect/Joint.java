
package firstkinect;


public class Joint {
    
    public int id;
    
    public double x;
    public double y;
    public double z;
    public boolean hasMoved;
    
    public Joint (int id, double x, double y, double z) {
        
        this.id = id;
        
        this.x = x;
        this.y = -y;
        this.z = z;
       
        
    }
    
    public void update (double x, double y, double z) {
        
        if (x==this.x &&
            y==this.y &&
            z==this.z 
            )
        {
            this.hasMoved = false;
            System.out.println("Has not moved");
        }
        else
        {
            this.hasMoved = true;
        }
        
        
        
        this.x = x;
        this.y = -y;
        this.z = z;
       
       
    }
    
    
}
