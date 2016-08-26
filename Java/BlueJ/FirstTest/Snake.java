
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;
import java.io.*;

/********************************************************************************
 *
 * Worm.java
 *
 * Comment.
 *
 * Copyright (c) 2002 Andreas Sehr
 * All rights reserved.
 *
 * @version         1.00.01     Test version
 *
 *******************************************************************************/


public class Snake extends JFrame implements Runnable
{
	public static final long serialVersionUID = 732823764;

    String debugString;
    private final boolean DEBUG = true;

    Thread myThread;
    SnakeField panel;
    Random random;

    private final int RIGHT   = 1001;
    private final int UP      = 1002;
    private final int LEFT    = 1003;
    private final int DOWN    = 1004;

    private final int GRID_SIZE  = 20;
    private final int GRIDS_X    = 20;
    private final int GRIDS_Y    = 20;
    private final int STEP       = GRID_SIZE;
    private final int VIEWTOP    = GRID_SIZE/2;
    private final int VIEWLEFT   = GRID_SIZE/2;
    private final int VIEWRIGHT  = (GRID_SIZE*GRIDS_X);
    private final int VIEWBOTTOM = (GRID_SIZE*GRIDS_Y);

    private final int APPLE_IMGS = 9;
    private final int START_TAIL_SIZE = 3;
    private final int MAX_TAIL_SIZE = GRIDS_X * GRIDS_Y;
    private final int START_SPEED = 250;    // Delay in milliseconds each turn

    private static final String SAVE_FILE_NAME = "data.sna";

    private int speed;
    private int points;
    private int antTail;
    private boolean dead;
    private boolean started;
    private boolean paused;
    private boolean showScull;
    private int     flagTicks;  //Flag ticks left
    private int level;
    private int lastHighScore;

    private TheHead head;
    private GridObject[] tail;
    private GridObject apple;
    private GridObject scull;
    private GridObject flag;
    private Image[] appleImgs;
    private HighScoreObj[] highScores;

    public Snake()
    {
         panel = new SnakeField();

         head = new TheHead();
         tail = new GridObject[MAX_TAIL_SIZE];
         highScores = new HighScoreObj[10];



         random = new Random(System.currentTimeMillis());
         started = false;
         paused = true;

         debugString = "debugString";

         startThread();
    }

    private void start()
    {
        reset();
        started = true;
        dead = false;
    }

    void startThread() {
        myThread = new Thread(this);
        myThread.start();
    }

    public void run() {
        while (myThread != null) {
            try {
                Thread.sleep(speed);
                if(started && !paused)
                {
                    switch (head.getDirection())
                    {
                        default:
                        case RIGHT: moveHead(head.getItsX() + STEP, head.getItsY()); break;
                        case UP: moveHead(head.getItsX(), head.getItsY() - STEP); break;
                        case LEFT: moveHead(head.getItsX() - STEP, head.getItsY()); break;
                        case DOWN: moveHead(head.getItsX(), head.getItsY() + STEP); break;
                    }
                    testKrash();
                    if(flagTicks > 0)
                        flagTicks--;
                }
                repaint(VIEWTOP,VIEWLEFT,VIEWRIGHT+20,VIEWBOTTOM+20);
            }
            catch(Exception e) {
                if (myThread.isInterrupted())
                    myThread = null;
            }
        }
    }

    private void stop() {
        myThread.interrupt();
        myThread = null;
    }

    public void init() {
        setBackground(Color.white);

        loadHighScore();
        loadImages();
        reset();
        dead = false;

        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel.setPreferredSize(new Dimension(VIEWRIGHT+20,VIEWBOTTOM+20));
        this.getContentPane().add(panel, BorderLayout.CENTER);

        this.setSize(VIEWRIGHT+VIEWLEFT+20,VIEWBOTTOM+VIEWTOP+20);


        this.addKeyListener(new KeyBoard());

        this.invalidate();
        //Display the window.
        this.pack();
        this.setVisible(true);
        this.toFront();
    }

    private boolean loadImages()
    {
         appleImgs = new Image[APPLE_IMGS];
         for(int i = 0; i < APPLE_IMGS; i++)
         {
             appleImgs[i] = Toolkit.getDefaultToolkit().getImage ("apple" + (i+1) + ".gif");
         }
         apple = new GridObject(appleImgs[0]);

         scull = new GridObject(Toolkit.getDefaultToolkit ().getImage ("scull.gif"));
         flag = new GridObject(Toolkit.getDefaultToolkit ().getImage ("flag.gif"));
         Image img = Toolkit.getDefaultToolkit ().getImage ("tail.gif");
         for(int i = 0; i < MAX_TAIL_SIZE; ++i)
         {
             tail[i] = new GridObject(img);
         }

         return true;
    }

    private void reset()
    {
        head.setXY(GRID_SIZE*(GRIDS_X/2), GRID_SIZE*(GRIDS_Y/2));
        head.setDirection(RIGHT);

        apple.setXY(GRID_SIZE*(GRIDS_X/2), GRID_SIZE*(GRIDS_Y/3));
        newRandomPosition(scull);

        antTail = START_TAIL_SIZE;
        points = 0;
        level = 0;
        showScull = false;
        paused = false;
        flagTicks = 0;
        speed = START_SPEED;
        lastHighScore = -1;

        for(int i = 0; i < MAX_TAIL_SIZE; ++i)
        {
            if(i < antTail)
                tail[i].setXY(head.getItsX()-((i+1)*STEP), head.getItsY());
            else
                tail[i].setXY(0,0);
        }
    }

    class SnakeField extends JPanel
    {
		public static final long serialVersionUID = 732823763;

        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            Font normalFont = g2.getFont();

            Stroke stroke = new BasicStroke(GRID_SIZE);
            g2.setStroke(stroke);
            if(paused)
            {
                Font bigFont = new Font("Serif", Font.BOLD, 32);
                g2.setFont(bigFont);
                g2.setPaint(Color.black);
                g2.drawString("Game paused!!", VIEWRIGHT/4, VIEWBOTTOM/4);
                g2.drawString("Press \'p\' to continue!", VIEWRIGHT/5, VIEWBOTTOM/3);
            }
            else if(!started)
            {
                Font bigFont = new Font("Serif", Font.BOLD, 32);
                g2.setFont(bigFont);
                g2.setPaint(Color.red);
                g2.drawString("Press enter to start!", VIEWRIGHT/5, VIEWBOTTOM - VIEWBOTTOM/10);

                g2.setPaint(Color.black);
                Font highFont = new Font("Serif", Font.BOLD, 16);
                g2.setFont(highFont);
                g2.drawString("High scorelist:", VIEWRIGHT/4, VIEWBOTTOM/3);
                g2.setFont(normalFont);
                for(int i = 0; i < 10; i++)
                {
                    if(lastHighScore == i)
                        g2.setPaint(Color.red);

                    int top = VIEWBOTTOM/3+(i*16)+20;
                    g2.drawString("" + (i+1)+". " + highScores[i].name , VIEWRIGHT/4, top);
                    g2.drawString("" +  highScores[i].points , VIEWRIGHT - VIEWRIGHT/4, top);

                    if(lastHighScore == i)
                        g2.setPaint(Color.black);
                }
            }
            else
            {
                for (int i = 0; i < antTail; ++i)
                    tail[i].paint(g, this);

                head.paint(g, this);
                if(showScull)
                    scull.paint(g, this);
                else
                    apple.paint(g, this);

                if(flagTicks > 0)
                    flag.paint(g, this);
            }

            if (dead)
            {
                Font bigFont = new Font("Serif", Font.BOLD, 32);
                g2.setFont(bigFont);
                g2.setPaint(Color.black);
                String gameOver = "Game Over!";
                int gameOverLeft = VIEWRIGHT/3;
                if(lastHighScore >= 0)
                {
                    gameOver = "You have made the top ten!";
                    gameOverLeft = VIEWRIGHT/12;
                }
                g2.drawString(gameOver, gameOverLeft, VIEWBOTTOM/6);
                g2.drawString("Score: " + points, (VIEWRIGHT/3)+40, (VIEWBOTTOM/6)+35);
            }

            g2.setPaint(Color.black);
            g2.drawRect(VIEWLEFT, VIEWTOP, VIEWRIGHT, VIEWBOTTOM);

            g2.setFont(normalFont);
            g2.setPaint(Color.white);
            g2.drawString("Points: " + points, 22, 15);

            g2.drawString("Level: " + level, 150, 15);

            if(flagTicks > 0)
                g2.drawString("Flag: " + flagTicks, 300, 15);

            if(DEBUG)
            {
                g2.setPaint(Color.blue);
                g2.drawString(debugString, 22, VIEWBOTTOM+15);
            }
        }
    }


    private void testKrash()
    {
        if(head.getItsX() <= VIEWLEFT || head.getItsX() >= VIEWRIGHT ||
           head.getItsY() <= VIEWTOP || head.getItsY() >= VIEWBOTTOM)
        {
            quit("Dead HEad outside");
            return;
        }

        // Apple test
        if(showScull == false && head.getItsY() == apple.getItsY() && head.getItsX() == apple.getItsX())
        {
            antTail++;
            points += 10;
            newRandomPosition(apple);
            apple.setImage(appleImgs[random.nextInt(APPLE_IMGS)]);
            //if(DEBUG)
              //  debugString = "X: " + apple.getItsX() + ", Y: "+apple.getItsY();

            if(points%100 == 0)
            {
                showScull = true;
                newRandomPosition(scull);
            }

            if(points%300 == 0)
            {
                level++;
                flagTicks = 80;
                newRandomPosition(flag);
            }
        }

        // Scull test
        if(showScull && head.getItsY() == scull.getItsY() && head.getItsX() == scull.getItsX())
        {
            antTail = antTail*2;
            showScull = false;
        }

        // flag test
        if(flagTicks > 0 && head.getItsY() == flag.getItsY() && head.getItsX() == flag.getItsX())
        {
            antTail = START_TAIL_SIZE;
            speed -= 20;
            flagTicks = 0;
            for(int i = antTail; i < MAX_TAIL_SIZE; i++)
                tail[i].setXY(0,0);
        }

        for (int i = 0; i < antTail; ++i)
            if ( head.getItsY() == tail[i].getItsY() &&
                 head.getItsX() == tail[i].getItsX() )
            {
                quit("Dead Head = tailpos[" + i + "] + head.getItsY(): " + head.getItsY() + " == tail[i].getItsY():"+
                    tail[i].getItsY()+"& head.getItsX(): " +head.getItsX()+"== tail[i].getItsX():"+tail[i].getItsX()+".");

                return;
            }
    }

    void moveHead(int initX, int initY)
    {
        for(int i = antTail-1; i > 0; --i)
            tail[i].setXY(tail[i-1].getItsX(), tail[i-1].getItsY());

        tail[0].setXY(head.getItsX(), head.getItsY());

        head.setItsX(initX);
        head.setItsY(initY);
    }

    private void newRandomPosition(GridObject obj)
    {
        boolean colision;
        int loops = 0;
        do{
            colision = false;
            obj.setXY((random.nextInt(GRIDS_X-1)+1)*GRID_SIZE , (random.nextInt(GRIDS_Y-1)+1)*GRID_SIZE);
            for (int i = 0; i < antTail; ++i)
                if ( obj.getItsY() == tail[i].getItsY() &&
                     obj.getItsX() == tail[i].getItsX() )
                {
                    colision = true;
                }
        } while(colision == true && loops++ < 1000);
    }

    private class KeyBoard implements KeyListener
    {

        public void keyTyped(KeyEvent e) {}
        public void keyReleased(KeyEvent e) {}
        public void keyPressed(KeyEvent e)
        {
//            if(DEBUG)
//               debugString = "KeyCode: " + e.getKeyCode();

            if(!started)
            {
                if(e.getKeyCode() == 10)    // Enter key code = 10
                {
                    start();
                }
            }
            else
            {
                if(e.getKeyCode() == 80)    // Enter key code = 10
                {
                    paused = !paused;
                }
                switch(e.getKeyCode())
                {
                    case 37: head.setDirection(LEFT); return;  // Arrow Left
                    case 38: head.setDirection(UP); return;  // Arrow Up
                    case 39: head.setDirection(RIGHT); return;  // Arrow Right
                    case 40: head.setDirection(DOWN); return;  // Arrow Down
                }
            }
        }
    }

    public static void main(String[] argv)
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable()   {
            public void run() {
                Snake s = new Snake();
                s.init();
            }
        });
    }

    void quit(String arg)
    {
        debugString = arg;
        dead = true;
        started = false;
        speed = 0;
        lastHighScore = addHighScore();
        if(lastHighScore >= 0)
            saveHighScore();
        //stop();
    }

    private class HighScoreObj
    {
        public final int points;
        public final String name;

        public HighScoreObj(int points, String name)
        {
            this.points = points;
            this.name = name;
        }
    }

    private void loadHighScore()
    {
        BufferedReader in = null;
        int i = 0;
        try
        {
            String line;
            in = new BufferedReader(new FileReader(SAVE_FILE_NAME));

            while((line = in.readLine()) != null)
            {
                String[] splitted = line.split("#");
                int points = Integer.parseInt(splitted[0]);
                String name = splitted[1];
                highScores[i++] = new HighScoreObj(points, name);
            }
        }
        catch(Exception e)
        {
            debugString = "Load High Score Exception: " + e.getMessage();
            for(; i < 10; i++)
            {
                highScores[i] = new HighScoreObj(0, "");
            }
        }
        finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                // At least we tried.
            }
        }
    }

    private void saveHighScore()
    {
        BufferedWriter out = null;
        try
        {
            String line;
            out = new BufferedWriter(new FileWriter(SAVE_FILE_NAME, false));
            for(int i = 0; i < 10; i++)
            {
                line = "" + highScores[i].points + "#" + highScores[i].name;
                out.write(line);
                out.newLine();
                out.flush();
            }
        }
        catch(Exception e)
        {
            debugString = "Save High Score Exception: " + e.getMessage();
        }
        finally
        {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                // At least we tried.
            }
        }
    }

    private int addHighScore()
    {
        if(points > highScores[9].points)
        {
            int position = 9;
            //Skriv in namn
            String name = "Name...";
            for(int i = 0; i < 9; i++ )
            {
                if(points > highScores[i].points)
                {
                    position = i;
                    for( i = 9; i > position; i--)
                    {
                        highScores[i] = highScores[i-1];
                    }
                    i = 9;  // End loop
                }
            }
            highScores[position] = new HighScoreObj(points, name);
            return position;
        }
        return -1;
    }
}

class TheHead  {
    boolean painted = true;
    int its_x = 0, its_y = 0;

    // Direction Variables
    private final int RIGHT   = 1001;
    private final int UP      = 1002;
    private final int LEFT    = 1003;
    private final int DOWN    = 1004;

    int headDirection = RIGHT;

    Image headUp;
    Image headDown;
    Image headLeft;
    Image headRight;
    Image activeHead;

    public TheHead()
    {
        headUp      = Toolkit.getDefaultToolkit ().getImage ("headUp.gif");
        headDown    = Toolkit.getDefaultToolkit ().getImage ("headDown.gif");
        headLeft    = Toolkit.getDefaultToolkit ().getImage ("headLeft.gif");
        headRight   = Toolkit.getDefaultToolkit ().getImage ("headRight.gif");
        activeHead  = headUp;
    }

    void setXY(int x, int y)
    {
        its_x = x;
        its_y = y;
    }

    void setItsX(int initX) { its_x = initX; }
    void setItsY(int initY) { its_y = initY; }

    void setDirection(int initDirection)
    {
        if(painted)
        {
            if (initDirection == RIGHT)
                if (headDirection != LEFT)
                    headDirection = RIGHT;

            if (initDirection == UP)
                if (headDirection != DOWN)
                    headDirection = UP;

            if (initDirection == LEFT)
                if (headDirection != RIGHT)
                    headDirection = LEFT;

            if (initDirection == DOWN)
                if (headDirection != UP)
                    headDirection = DOWN;

            switch(headDirection)
            {
                default:
                case RIGHT: activeHead = headRight; break;      // Right
                case UP: activeHead = headUp; break;         // Up
                case LEFT: activeHead = headLeft; break;       // Left
                case DOWN: activeHead = headDown; break;       // Down
            }
            painted = false;
        }
    }

    public void init() {}

    int getItsX() { return its_x; }
    int getItsY() { return its_y; }

    int getDirection() {return headDirection;}

    public void paint(Graphics g, JPanel panel)
    {
        painted = true;
        g.drawImage(activeHead, its_x, its_y, panel);
        //g.drawString("Head", its_x, its_y);
    }
}


class GridObject
{
    int its_x = 0, its_y = 0;

    Image img;

    public GridObject(Image init)
    {
        img = init;
    }

    void setXY(int x, int y)
    {
        its_x = x;
        its_y = y;
    }

    void setImage(Image img)
    {
        this.img = img;
    }

    public void init() {}

    int getItsX() { return its_x; }
    int getItsY() { return its_y; }

    public void paint(Graphics g, JPanel panel)
    {
        g.drawImage(img, its_x, its_y, panel);
        //g.drawString("¤#¤", its_x, its_y);
    }
}


