
package firstkinect;


public class Joint {
    
    public int id;
    
    public double x;
    public double y;
    public double z;
    
    public Joint (int id, double x, double y, double z) {
        
        this.id = id;
        
        this.x = x;
        this.y = -y;
        this.z = z;
        
        System.out.println(this.x);
       
        
    }
    
}
