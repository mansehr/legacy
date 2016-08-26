import java.awt.*;
/**
 * Write a description of class Platform here.
 * 
 * @author Andreas Sehr
 * @version (a version number or a date)
 */
public class Platform extends Unit
{
    /**
     * Constructor for objects of class Platform
     */
    public Platform(int x, int y, int width, int height, Color color)
    {
        super(x,y,width,height, color);
    }
    
    public void moveLeft(int steps)
    {
        this.x -= steps;
    }
    
    public void moveRight(int steps)
    {
        this.x += steps;
    }
    public void draw(Graphics g)
    {
        if(isVisible())
        {
            g.setColor (this.color);
            g.fillRect (x,y,width,height);   
        }
    }
}
