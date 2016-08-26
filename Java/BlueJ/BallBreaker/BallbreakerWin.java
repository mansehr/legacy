
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.applet.*;


/**
 * Class Ballbreaker - write a description of the class here
 *
 * @author Andreas Sehr
 * @version (a version number)
 */
public class BallbreakerWin extends JFrame implements Runnable, KeyListener
{

	public static final long serialVersionUID = 732823766;

    // Definitions readonlyvariables
    private final int BRICK_ROWS = 8;
    private final int BRICK_COLUMNS = 12;
    private final int TOTAL_BRICKS = BRICK_ROWS * BRICK_COLUMNS;
    private final int BRICK_MARGIN = 1;
    private final int BRICK_WIDTH = 30;
    private final int BRICK_HEIGHT = 20;
    private final int PLATFORM_WIDTH = 100;
    private final int PLATFORM_HEIGHT = 20;
    private final int BALL_SIZE = 20;
    private final int VIEWTOP    = 0;
    private final int VIEWLEFT   = 0;
    private final int VIEWRIGHT  = 500;
    private final int VIEWBOTTOM = 500;

    // instance variables
    private long points;
    private int bricksLeft;

    private String temp = "Hej";

    private Platform platform;
    private Ball ball;
    private Brick[] bricks = new Brick[TOTAL_BRICKS];
    private Brick[] walls = new Brick[4];

     /**
     * Called by the browser or applet viewer to inform this JApplet that it
     * has been loaded into the system. It is always called before the first
     * time that the start method is called.
     */
    public BallbreakerWin()
    {
	setTitle("Andreas Fönster");
    setSize(VIEWRIGHT, VIEWBOTTOM);
    setDefaultCloseOperation(
                           JFrame.EXIT_ON_CLOSE);

        setBackground(Color.white);

        addKeyListener(this);

        points = 0;

        bricksLeft = 0;
        for(int j = 0; j < BRICK_ROWS; j++)
        {
            for(int i = 0; i < BRICK_COLUMNS; i++)
            {
                int x = ((i+1)*(BRICK_WIDTH+BRICK_MARGIN))+30;
                int y = ((j+1)*(BRICK_HEIGHT+BRICK_MARGIN))+30;

                bricks[i+(j*BRICK_COLUMNS)] = new Brick(x,y,BRICK_WIDTH,BRICK_HEIGHT, 2);

                bricksLeft++;
            }
        }

        ball = new Ball(100,300,BALL_SIZE,BALL_SIZE, 1, 1, Color.red);
        platform = new Platform(200,450,PLATFORM_WIDTH,PLATFORM_HEIGHT, Color.orange);

        walls[0] = new Brick(0,0,20,500, -1);       // Left
        walls[1] = new Brick(0,0,500,20, -1);       // Top
        walls[2] = new Brick(480,0,500,500, -1);    // Right
        walls[3] = new Brick(0,480,500,500, -1);    // Bottom
        walls[3].setVisible(false);

         this.invalidate();

        new Thread(this).start();    // Här startas en ny process..
    }

    /**
     * Called by the browser or applet viewer to inform this JApplet that it
     * should start its execution. It is called after the init method and
     * each time the JApplet is revisited in a Web page.
     */
    public void start()
    {

    }

    /**
     * Called by the browser or applet viewer to inform this JApplet that
     * it should stop its execution. It is called when the Web page that
     * contains this JApplet has been replaced by another page, and also
     * just before the JApplet is to be destroyed.
     */
    public void stop()
    {
        // provide any code that needs to be run when page
        // is replaced by another page or before JApplet is destroyed
    }

    /**
     * Paint method for applet.
     *
     * @param  g   the Graphics object for this applet
     */
    public void paint (Graphics g)
    {
       g.setColor (Color.white);
        g.fillRect (20,20,460,500);

        // Draws the fore and background
       for(int i = 0; i < 4; i++) {
           walls[i].draw(g);
        }

        // Draw a red, filled circle:
        ball.draw(g);

        for(int i = 0; i < TOTAL_BRICKS; i++)
        {
           bricks[i].draw(g);
        }

        platform.draw(g);

         g.drawString("ball.getDX = " + ball.getDX(), 10, 10);
         g.drawString("Poäng: " + points, 300, 10);

         if(bricksLeft <= 0)
         {
             g.setColor (Color.red);
             g.drawString("Grattis du klarade banan!", 200, 250);
             g.setColor (Color.black);
             g.drawString("Laddar ny nivå...!", 220, 270);
         }
         if(ball.getY() > 500)
         {
             g.setColor (Color.red);
             g.drawString("YOU SUCK!!!!!", 200, 250);
         }
  //      g.drawString("Temp = " + temp, 10, 40);
    }

    public void run() {            // ..som använder denna run-metod..
    while (true) {               // ..som anropar paint varje sekund
      try {
          Thread.currentThread().sleep(20);
          ball.moveBall();
          testCollision();
          if(bricksLeft <= 0)
          {
              //NewLevel();
              Thread.currentThread().sleep(20000);
          }
          if(ball.getY() > 500)
          {
              //NewLevel();
              Thread.currentThread().sleep(20000);
          }
          repaint(VIEWTOP,VIEWLEFT,VIEWRIGHT+20,VIEWBOTTOM+20);
      }catch (InterruptedException e){}
    }
  }

    public void testCollision()
    {
        boolean changeDX = false;
        boolean changeDY = false;
      if(platform.testCollision(ball))
      {
        float newDX = 1;
        float ballWidth = ball.getWidth();
        float ballLeft = ball.getLeft();
        float platformWidth = platform.getWidth();
        float platformLeft = platform.getLeft();
        newDX = (ballLeft + (ballWidth/2) - (platformLeft + (platformWidth/2))) /( platformWidth/4);

        ball.setDY(-ball.getDY());
       // newDX = (ball.getLeft() + (ball.getWidth()/2) - (platform.getLeft() + (platform.getWidth()/2))) /( platform.getWidth()/2);

       // Om newDX är inom intervallet -0.2 < newDX < 0.2 så ändras inte x dvs den studdsar rakt upp och ner
       // Annars om newDX är inom intervallet -0.5 < newDX < -0.2 eller 0.2 < newDX < 0.5
       // Sätts newDX till -0.5 resp 0.5
        //if(newDX < 0.5 && newDX > - 0.5 && newDX > 0.2 && newDX < -0.2)
        if(newDX > - 0.9 && newDX < 0.9)
        {
           /* if(newDX < 0)
                newDX = -0.5f;
            else
                newDX = 0.5f;*/


        } else {

            ball.setDX(newDX);
        }
        //g.DrawString("newDX = " + newDX);
      }
      if(walls[0].testCollision(ball))      // Left wall
      {
          ball.setDX(-ball.getDX());
      }
      if(walls[1].testCollision(ball))      // Top wall
      {
          ball.setDY(-ball.getDY());
      }
      if(ball.testCollision(walls[2]))      // Right wall
      {
          ball.setDX(-ball.getDX());
      }
      if(walls[3].isVisible() && ball.testCollision(walls[3]))      // Bottom wall
      {
          ball.setDY(-ball.getDY());
      }
      for(int i = 0; i < TOTAL_BRICKS; i++)
      {
          if(bricks[i].isVisible() && ball.testCollision(bricks[i]))
          {
              // Sees how much the side of the ball is inside the brick
              int topInside = 0;
              int bottomInside = 0;
              int leftInside = 0;
              int rightInside = 0;
              // Right side of ball hits Left side of the brick
              if(ball.getRight() >= bricks[i].getLeft() && ball.getRight() <= bricks[i].getRight())
              {
                  rightInside = ball.getRight() - bricks[i].getLeft();
              }
              // Left side of ball hits Right side of the brick
              if(ball.getLeft() >= bricks[i].getLeft() && ball.getLeft() <= bricks[i].getRight())
              {
                  leftInside = bricks[i].getRight() - ball.getLeft();
              }
              // Top side of ball hits Bottom side of the brick
              if(ball.getTop() <= bricks[i].getBottom() && ball.getTop() >= bricks[i].getTop())
              {
                  topInside = bricks[i].getBottom() - ball.getTop();
              }
              // Bottom side of ball hits Top side of the brick
              if(ball.getBottom() <= bricks[i].getBottom() && ball.getBottom() >= bricks[i].getTop())
              {
                  bottomInside = ball.getBottom() - bricks[i].getTop();
              }

              if(topInside+bottomInside > leftInside+rightInside)
              {
                changeDX = true;
              }
              else
              {
                changeDY = true;
              }

              //bricks[i].setColor(ball.getColor());
              bricks[i].hited();
              points += 10;
              if(bricks[i].isVisible() == false)
              {
                  bricksLeft--;
              }
          }
        }
        if(changeDX == true)
        {
            ball.setDX(-ball.getDX());
        }
        if(changeDY == true)
        {
            ball.setDY(-ball.getDY());
        }

      }
      public void keyTyped(KeyEvent evt) {
          // The user has typed a character, while the
          // apple thas the input focus.  If it is one
          // of the keys that represents a color, change
          // the color of the square and redraw the applet.

      char ch = evt.getKeyChar();  // The character typed.

//      temp += ch;
    /*  if (ch == 'B' || ch == 'b') {
         squareColor = Color.blue;
         repaint();
      }
      else if (ch == 'G' || ch == 'g') {
         squareColor = Color.green;
         repaint();
      }
      else if (ch == 'R' || ch == 'r') {
         squareColor = Color.red;
         repaint();
      }
      else if (ch == 'K' || ch == 'k') {
         squareColor = Color.black;
         repaint();
      }
*/
   }  // end keyTyped()


      public void keyPressed(KeyEvent evt) {
          // Called when the user has pressed a key, which can be
          // a special key such as an arrow key.  If the key pressed
          // was one of the arrow keys, move the platform

      int key = evt.getKeyCode();  // keyboard code for the key that was pressed

      if (key == KeyEvent.VK_LEFT) {
         platform.moveLeft(5);
         if(platform.getLeft() < 20)
            platform.setX(20);
      }
      else if (key == KeyEvent.VK_RIGHT) {
         platform.moveRight(5);
         if(platform.getRight() > 480)
            platform.setX(480 - platform.getWidth());
      }
      /*else if (key == KeyEvent.VK_UP) {
         squareTop -= 8;
         if (squareTop < 3)
            squareTop = 3;
         repaint();
      }
      else if (key == KeyEvent.VK_DOWN) {
         squareTop += 8;
         if (squareTop > getSize().height - 3 - SQUARE_SIZE)
            squareTop = getSize().height - 3 - SQUARE_SIZE;
         repaint();
      }*/

   }  // end keyPressed()

   public void keyReleased(KeyEvent evt) {
      // empty method, required by the KeyListener Interface
   }

	public static void main(String[] args){
    //Instantiate an object of this class, which
    // will, in turn, instantiate an object of
    // the class named GraphicsGUI.
    BallbreakerWin winA = new BallbreakerWin();
    winA.setVisible(true);
  }//
}
