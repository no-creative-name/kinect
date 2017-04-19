
package kinectapp;


public class Joint {
    
    public int id;
    
    double filteredOutputX;
    double filteredOutputY;
    double filteredOutputZ;
    
    private double x;
    private double y;
    private double z;
    
    private double filteredX[];
    private double filteredY[];
    private double filteredZ[];
    
    private double prevFilteredOutputX;
    private double prevFilteredOutputY;
    private double prevFilteredOutputZ;
    
    private final int FILTER_SIZE = 1000;
    private int filterIdx = 0;
    private double currentSpeed = 0;
    
    public Joint (int id, double x, double y, double z) {
        
        this.id = id;
        
        this.x = x;
        this.y = -y;
        this.z = z;
        
        this.filteredX = new double[FILTER_SIZE];
        this.filteredY = new double[FILTER_SIZE];
        this.filteredZ = new double[FILTER_SIZE];
        
    }
        
    
    
    public double getX()
    {
        return this.x;
    }
    public double getY()
    {
        return this.y;
    }
    public double getZ()
    {
        return this.z;
    }
    
    public double getFilteredX()
    {
        return this.filteredOutputX;
    }
    public double getFilteredY()
    {
        return this.filteredOutputY;
    }
    public double getFilteredZ()
    {
        return this.filteredOutputZ;
    }
    public double getCurrentSpeed() {
        return this.currentSpeed;
    }
    

    
    private void applyFilter () {
        
        this.filteredX[filterIdx]=this.x;
        this.filteredY[filterIdx]=this.y;
        this.filteredZ[filterIdx]=this.z;
        
        this.filteredOutputX=0;
        this.filteredOutputY=0;
        this.filteredOutputZ=0;
        
        for (int i=0; i<FILTER_SIZE; i++)
        {
            this.filteredOutputX+=this.filteredX[i];
            this.filteredOutputY+=this.filteredY[i];
            this.filteredOutputZ+=this.filteredZ[i];
            
        }
        
        this.filteredOutputX/=this.FILTER_SIZE;
        this.filteredOutputY/=this.FILTER_SIZE;
        this.filteredOutputZ/=this.FILTER_SIZE;
      
    }
    
    private void calculateCurrentJointSpeed () {
        
        double xSpeed = (this.filteredOutputX-this.prevFilteredOutputX);
        double ySpeed = (this.filteredOutputY-this.prevFilteredOutputY);
        double zSpeed = (this.filteredOutputZ-this.prevFilteredOutputZ);
        
        double totalSpeed = Math.sqrt(Math.pow(Math.sqrt(Math.pow(xSpeed, 2)+Math.pow(ySpeed, 2)),2)+Math.pow(zSpeed, 2));
        
        this.currentSpeed = totalSpeed*1000;
        
    }
    
    
    
    public void update (double x, double y, double z) {
        
        this.filterIdx = (this.filterIdx+1) % FILTER_SIZE;
        
        this.x = x;
        this.y = -y;
        this.z = z;
        
        applyFilter();
        calculateCurrentJointSpeed();
        
        this.prevFilteredOutputX = this.filteredOutputX;
        this.prevFilteredOutputY = this.filteredOutputY;
        this.prevFilteredOutputZ = this.filteredOutputZ;
        
    }
    
}
