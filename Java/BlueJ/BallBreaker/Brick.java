import java.awt.*;
/**
 * Write a description of class Brick here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Brick extends Unit
{
    // instance variables 
    private int hardnes;

    /**
     * Constructor for objects of class Brick
     */
    public Brick(int x, int y, int width, int height, int hardnes)
    {
        super(x,y,width,height, Color.black);
        setHardnes(hardnes);
    }
    
    public void setHardnes(int init)
    {
        this.hardnes = init;
        switch(hardnes)
        {
            case 0: this.visible = false;
            case 1: this.setColor(Color.yellow); break;
            case 2: this.setColor(Color.orange); break;
            case 3: this.setColor(Color.red); break;
            case 4: this.setColor(Color.green); break;
            case 5: this.setColor(Color.blue); break;
            default: this.setColor(Color.black); break;
        }
    }
    public void hited()
    {
        setHardnes(hardnes -1);
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
