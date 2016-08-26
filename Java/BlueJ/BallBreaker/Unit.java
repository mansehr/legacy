import java.awt.*;
import java.util.*;

/**
 * Abstract class Unit - write a description of the class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Unit
{
    // instance variables - replace the example below with your own
    protected int x;
    protected int y;
    
    protected int width;
    protected int height;
    
    protected boolean visible;
    
    protected Color color;
    
    public Unit()
    {
        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
        
        this.visible = true;
        
        this.color = Color.black;
    }
    
    public Unit(int x, int y, int width, int height, Color color)
    {
        // initialise instance variables
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.visible = true;
        
    }
    
    public void draw(Graphics g)
    {
    }
    
    
    public void setX(int init)
    {
        x = init;
    }
    
    public void setColor(Color init)
    {
        this.color = init;
    }
    
    public void getY(int init)
    {
        y = init;
    }
    
    public void getWidth(int init)
    {
        width = init;
    }
    
    public void getHeight(int init)
    {
        height = init;
    }
    
    public void setVisible(boolean init)
    {
        visible = init;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public int getHeight()
    {
        return height;
    }
    
    public boolean isVisible()
    {
        return visible;
    }
    
    public int getLeft()
    {
        return x;
    }

    public int getRight()
    {
        return x + width;
    }
    
    public int getTop()
    {
        return y;
    }
    
    public int getBottom()
    {
        return y + height;
    }
    
    public Color getColor()
    {
        return color;
    }
    
    public boolean testCollision(Unit init)
    {
       Rectangle thisObj = new Rectangle(x, y, width, height);
       Rectangle testObj = new Rectangle(init.getX(), init.getY(), init.getWidth(), init.getHeight()); 
        //Testar top bottom
      if(thisObj.intersects(testObj))
      {
            return true;
      }
      return false;
    }
}
