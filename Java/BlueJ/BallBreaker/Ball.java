import java.awt.*;
/**
 * Write a description of class Ball here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ball extends Unit
{
    float dy, dx;
    float speed;

    /**
     * Constructor for objects of class Ball
     */
    public Ball(int x, int y, int width, int height, float dX, float dY, Color color)
    {
        super(x,y,width,height, color);
        
        this.speed = 2;
        this.dx = dX;
        this.dy = dY;
    }
    
    /**
     * Constructor for objects of class Ball
     * sets dy & dx to zero(0)
     */
    public Ball(int x, int y, int width, int height)
    {
        this(x,y,width,height,0,0, Color.black);
    }

    public void setDY(float init)
    {
        this.dy = init;
    }
    
    public void setDX(float init)
    {
        this.dx = init;
    }
    
    public void setSpeed(float newSpeed)
    {
        speed = newSpeed;
    }
    
    public float getDY()
    {
        return this.dy;
    }
    
    public float getDX()
    {
        return this.dx;
    }
    
    public float getSpeed()
    {
        return speed; 
    }
    
    public void moveBall()
    {
        x = x + (int)(dx*speed);
        y = y + (int)(dy*speed);
    }
    
    public void draw(Graphics g)
    {
        if(isVisible())
        {
            g.setColor (this.color);
            g.fillOval (x,y,width,height);
        }
    }
}
