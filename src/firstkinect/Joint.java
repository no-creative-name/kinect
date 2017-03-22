
package firstkinect;


public class Joint {
    
    public int id;
    
    private double x;
    private double y;
    private double z;
    public boolean hasMoved;
    
    double filteredX[];
    double filteredY[];
    double filteredZ[];
    
    double filteredOutputX;
    double filteredOutputY;
    double filteredOutputZ;
    
    final int FILTER_SIZE = 2000;
    int filterIdx = 0;
    int prevFilterIdx = 0;
    
    double getX()
    {
        return this.x;
    }
    double getY()
    {
        return this.y;
    }
    double getZ()
    {
        return this.z;
    }
    
    double getFilteredX()
    {
        return this.filteredOutputX;
    }
    double getFilteredY()
    {
        return this.filteredOutputY;
    }
    double getFilteredZ()
    {
        return this.filteredOutputZ;
    }

            
    public Joint (int id, double x, double y, double z) {
        
        this.id = id;
        
        this.x = x;
        this.y = -y;
        this.z = z;
        
        this.filteredX = new double[FILTER_SIZE];
        this.filteredY = new double[FILTER_SIZE];
        this.filteredZ = new double[FILTER_SIZE];
        
    }
    
    public void update (double x, double y, double z) {
        
        this.filterIdx = (this.filterIdx+1) % FILTER_SIZE;
        
        this.x = x;
        this.y = -y;
        this.z = z;
        
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
       
        this.prevFilterIdx = this.filterIdx;
        
    }
    
    
}
