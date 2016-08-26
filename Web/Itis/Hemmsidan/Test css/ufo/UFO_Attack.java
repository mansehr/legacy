// Code by Sergio Fanchiotti (c) 1997
//
// Free to use and copy for any purpose.
//
// No responsability for any mis-use or problems 
// due to the usage of this code on any device.
// (i.e. crashing your browser and/or your OS)
//

import java.awt.*;
import java.applet.*;
import java.util.*;

public class UFO_Attack extends Applet implements Runnable {

	Image buffer   = null ;	// Temporary image buffer
	Image backdrop = null ; // Backdrop image 
	Image bgimg    = null ; // Original backdrop  image 
	Image ufostrip = null ;	// UFO Sequence
	Image missile  = null ; // Misile Sequence
	Image missile_explosion
	               = null ; // Misile Esplosion Sequence

	MediaTracker tracker = null ;

	Graphics  buf_g = null ; // Buffer graphics object
	Graphics  bkd_g = null ; // Backdrop graphics object
	Dimension window_size = null;
	Font font ;
	Font font_s ;
	
        AudioClip explosion       = null ;
        AudioClip newufo         = null ;
        AudioClip missile_launch = null ;

	
        Thread  game      = null ;
	boolean game_over = true ;
	int     mouse_x   = 100 ;
        Rectangle paint_area = new Rectangle() ;
        Rectangle new_area   = new Rectangle() ;


	Launcher L  = null ;
	Missile  M  = null ;
	Vector   UV = new Vector() ;
	Vector   EV = new Vector() ;
	
        int NU    = 1 ;
	int score = 0 ;
	
	// Colors
	Color gunColor;
	Color mColor;
	Color ufoColor;
	Color scoreColor;
	Color bgColor;
	
	public void init() {
	  System.out.println("UFO Attack: A game by Sergio Fanchiotti") ;
	  tracker = new MediaTracker(this) ;

          bgimg = getImage(this.getCodeBase(),"bgimg.gif") ;
	  tracker.addImage(bgimg,0) ;

          ufostrip = getImage(this.getCodeBase(),"ufostrip.gif") ;
	  tracker.addImage(ufostrip,0) ;

          missile  = getImage(this.getCodeBase(),"missile.gif") ;
	  tracker.addImage(missile,0) ;

          missile_explosion = getImage(this.getCodeBase(),"explosionstrip.gif") ;
	  tracker.addImage(missile_explosion,0) ;

	  font   = new Font("Helvetica", Font.BOLD, 24) ;
	  font_s = new Font("Helvetica", Font.BOLD, 14) ;

          // Define the colors to use
	  bgColor    = new Color(0,0,128);
	  gunColor   = new Color(0,88,0);
	  mColor     = new Color(255,255,255);
	  ufoColor   = new Color(255,0,0);
	  scoreColor = new Color(0,0,255);
	}


	public void start() {
	  // Change the cursor
	  getFrame(this).setCursor(Frame.CROSSHAIR_CURSOR) ;


	  window_size = size();

	  buffer = null;
 	  buffer = createImage(window_size.width, window_size.height);

	  backdrop = null;
 	  backdrop = createImage(window_size.width, window_size.height);

	  buf_g = buffer.getGraphics();
	  buf_g.setColor(bgColor);
	  buf_g.fillRect(0, 0, window_size.width, window_size.height);


          // Display initial message
          set_say_font(font) ;
          set_say_mode(CENTER) ;
          set_say_style(SHADOW) ;
	  say("UFO",10,80) ;
	  say("ATTACK") ;
          set_say_font(font_s) ;
          set_say_style(NORMAL) ;
	  say("") ;
	  say("Click to start") ;
	  say("a game") ;

	  //repaint() ;
	  Graphics g = getGraphics() ;
          g.drawImage(buffer,0,0,this) ;

          // Initialize the launcher
	  mouse_x = window_size.width/2 ; 
          L = new Launcher(this) ;
          L.set_color(gunColor) ;

          // Initialize the missile
          M = new Missile(this) ;
          M.set_color(mColor) ;
		

          // Load the sound files now
	  if (explosion == null) 
	    explosion = getAudioClip(getCodeBase(),"explosion.au") ;

	  if (newufo == null) 
	    newufo    = getAudioClip(getCodeBase(),"sonar.au") ;

	  if (missile_launch == null) 
	    missile_launch = getAudioClip(getCodeBase(),"rocket.au") ;

	  game_over = true ;

          newufo.play() ;         // Play sounds
	  missile_launch.play() ;
	  explosion.play() ;

	}


	public void stop() {
	  // Stop the game if running 
	  if (game != null) {
	    game.stop() ;
	    game = null ; // and eliminate the thread
	  }
	  // Reset the cursor
	  getFrame(this).setCursor(Frame.DEFAULT_CURSOR) ;
	}


        // The main game thread
	public void run() {
	  // Define some local variables
	  UFO  U ;
	  Explosion  E ;
	  long count = 0 ;
          long ti    = 0 ;
	   
	  // Wait for the images to load
	  Graphics g = getGraphics() ;
	  g.setColor(Color.red) ;
	  g.drawString("Starting Game...", 20,20) ;

          while (tracker.checkAll(true) == false) { 
	    if ((ti++ % 2) == 0) 
	      g.setColor(Color.red) ;
	    else 
	      g.setColor(Color.green) ;

	    g.drawString("*", 10,22) ;
            try {Thread.sleep(50);} catch (InterruptedException e) { } ;

	    if (ti > 1000) break ;
	  }
	  
          if (tracker.isErrorAny()) {
	    showStatus("Error getting images") ;
            return ;
	  }
	  showStatus("Loading completed") ;
	  g.dispose() ;


          // Paint the backdrop buffer
	  buf_g = backdrop.getGraphics();
          buf_g.drawImage(bgimg,0,0,window_size.width,window_size.height,this) ;

          // Paint the screen
	  buf_g = getGraphics();
          buf_g.drawImage(backdrop,0,0,this) ;

          // Paing the buffer
	  buf_g = buffer.getGraphics();
          buf_g.drawImage(bgimg,0,0,window_size.width,window_size.height,this) ;

          // Display the buffer
	  repaint() ;

          // Display the initial setup
	  display_score() ;
	  L.draw() ;
          showStatus("UFO ATTACK") ;

	  // Action loop
          for (;;) {
	    ti = System.currentTimeMillis() ;

            // If there is place for one more UFO, try to add one
	    // with some probability 
	    if ((UV.size() < NU) && 
	        (Math.random() > (UV.size() == 0 ? 0.90 : 0.98))) {
              newufo.play() ;  // Play a bell 
	      U = new UFO(this) ; 
              U.set_color(ufoColor) ;

              // Set the UFO to fast descent if:
	      if (score > 10 && Math.random() > 0.7) U.vy -= 1 ;

              // Add UFO to the list
	      UV.addElement(U) ;
	    }

            // Draw the explosion in the backdrop
	    // and eliminate it from the list if ended
	    for (int j=EV.size()-1; j>=0 ; --j) {
	      E = (Explosion) EV.elementAt(j) ;
	      if (E.active) {
	        // System.out.println("Drawing Explosion " + j) ;
	        E.draw() ;
              }
              else {
	        // System.out.println("Ending Explosion " + j) ;
	        E.erase() ;
		EV.removeElementAt(j) ;
	      }
	    }
	  
	    // Move the launcher and the missile
	    L.move() ;
	    if (M.active() == true) M.move() ;

            // Move each UFO
	    for (int i=0; i < UV.size(); ++i) {
	      U = (UFO) UV.elementAt(i) ;
	      U.move() ;
	    }


            // Check for collision between UFO and missile
	    for (int i=(UV.size()-1); i >=0 ; --i) {
	      U = (UFO) UV.elementAt(i) ;
	      if (U.active() && M.active() && U.collision(M)) {
		++score ;
	        explosion.stop() ; 
		display_score() ;
	        explosion.play() ; 

                // Increase the max # of UFOs every 10 destroyed
		// till we reach a max of 5 attacking
	        if ((NU < 5) && (score % 10) == 1)  ++NU ;

                // Erase the missile and deactivate it
	        M.active(false) ;
		M.erase() ;

                // Erase and deactivate the UFO hit
		U.active(false) ;
		U.erase() ;

		// Display an explosion
		E = new Explosion(this,U.px,U.py) ;
	        // System.out.println("Adding Explosion") ;
		EV.addElement(E) ;
	      }

              // Draw the UFO or eliminate it from the list
	      if (U.active()) 
	        U.draw() ;
              else 
	        UV.removeElementAt(i) ;

              // If a UFO reaches the floor then you've lost
              if ((U.py - U.h/2) <=0) {
	        game_over = true ;
		display_game_over() ;
		return ;
	      }
	    }

            // If the launcher moves... redraw
	    if (L.has_moved() || ((M.py-M.h) < (L.py+L.h)) || (! M.active()) )
	      L.draw() ;

            // If active redraw the missile
	    if (M.active() == true) M.draw() ;


            // Make the loop last 20ms in case the CPU is too fast...
            ti = System.currentTimeMillis() - ti ;
	    ti = 20 - ti ; 
	    ti = ti > 0 ?  10 + ti : 10 ; // At least sleep por 10ms
            Thread.yield() ; // Let the GC work?
            try {Thread.sleep(ti);} catch (InterruptedException e) { } ;

            // Repaint every 100 cycles... just in case
	    if ((count = ++count % 500) == 0) {
	      repaint() ;
              // g.drawImage(buffer,0,0,this) ;
	    }
	  }
	}


	public void display_score() {
          Graphics bkd_g = backdrop.getGraphics();
          bkd_g.clipRect(window_size.width/2, 0, window_size.width/2, 40);
          bkd_g.drawImage(bgimg,0,0,window_size.width,window_size.height,this) ;

          bkd_g.setColor(Color.red) ;
          bkd_g.setFont(font) ;
	  String aux = score > 9 ? "" : "0" ;
          bkd_g.drawString(aux+score, window_size.width - 60,30) ;
          bkd_g.dispose() ;
          // bkd_g = null ;

          Graphics bg = buffer.getGraphics() ;
          bg.clipRect(0, 0, window_size.width, 40);
          bg.drawImage(backdrop,0,0,this) ;
          bg.dispose() ;
          // bg = null ;

          Graphics g = getGraphics() ;
          g.clipRect(0, 0, window_size.width, 40);
          g.drawImage(buffer,0,0,this) ;
          g.dispose() ;
          // g = null ;
	}

	public void display_game_over() {
          set_say_font(font) ;
          set_say_mode(CENTER) ;
          set_say_style(SHADOW) ;
	  set_say_pos(10,80) ;
	  say("GAME OVER") ;
          set_say_font(font_s) ;
          set_say_style(NORMAL) ;
	  say("(click to start)") ;
	  repaint() ;
          try {Thread.sleep(500);} catch (InterruptedException e) { } ;
	}

	public boolean mouseMove(Event e, int x, int y) {
	  // showStatus("mouse at: (" + x + ", " + y + ")");
	  mouse_x = x ;
	  return true;
	}

        // Capture the mouse clicks
	public boolean mouseDown(Event e, int x, int y) {
	  if (game_over) {
	    game_over = false ;
	    if (game != null) {
	      game.stop() ;
	      game = null ;
	    }
	    NU    = 1 ;
	    score = 0 ;
	    M.active(false) ;
	    UV.removeAllElements() ;
	    EV.removeAllElements() ;

	    game  = new Thread(this) ;
	    game.setPriority(Thread.MIN_PRIORITY) ;
	    game.start() ;

	    buf_g.dispose() ;

	    return true ;
	  }

	  if (M != null && ! M.active()) {
	    missile_launch.stop() ;
	    missile_launch.play() ;
	    M.set_pos(L.px,L.py) ;
	    M.active(true) ;
	  }

	  return true;
	}

	
	// The paint methos just draws the buffer
	public void paint(Graphics g) {
 	  if (buffer != null) g.drawImage(buffer, 0, 0, this);
	}

        // Makes an animation smoother
	public void update(Graphics g) { 
	  paint(g) ; 
	}

        // Get the Frame of this applet
        public Frame getFrame(Component c) {
          while( c != null && !(c instanceof java.awt.Frame) )
                c = c.getParent();
          return (Frame) c;
        }


        // Text Display Constants
        public static final int CENTER = 1 ;  // Modes: Centered
        public static final int LEFT   = 2 ;  //        Left Justified
        public static final int RIGHT  = 3 ;  //        Right Justified
        public static final int FREE   = 0 ;  //        At x,y

        public static final int NORMAL = 0 ;  // Styles: Normal
        public static final int SHADOW = 1 ;  //         Shadowed 

        // Text Display Variables
        private int  say_pos_y   =  0 ;
        private int  say_pos_x   =  0 ;
        private int  say_mode    = -1 ;
        private int  say_style   = -1 ;
        private int  say_margin  = 10 ;
        private Font say_font    = null ;

        // Text Print Methods
	public void say(String s, int x, int y) {
	  set_say_pos(x, y) ;
	  say(s) ;
	}

	public void say(String s) {
	  FontMetrics fm = getFontMetrics(say_font) ;

          // Compute the x position
	  switch(say_mode) {
	    case CENTER:
	      say_pos_x = (window_size.width - fm.stringWidth(s))/2 ;
	      break ;
	    case RIGHT:
	      say_pos_x = window_size.width - fm.stringWidth(s) - say_margin ;
	      break ;
	    case LEFT:
	    default :
	      say_pos_x = say_margin ;
	      break ;
	  }

          Graphics bg = buffer.getGraphics() ;
          bg.setFont(say_font) ;

          // Print style extras
	  if (say_style == SHADOW) {
            bg.setColor(new Color(150,150,150)) ;
            bg.drawString(s, say_pos_x+2,say_pos_y+1) ;
	  }

          // Print string
          bg.setColor(Color.white) ;
          bg.drawString(s, say_pos_x,say_pos_y) ;

          // Shift y position to next line
	  say_pos_y += (int) (1.2 * fm.getHeight()) ;

          // Free some resources
          bg.dispose() ;
	}

	public void set_say_mode(int m) {
	  say_mode = m ;
	}

	public void set_say_style(int s) {
	  say_style = s ;
	}

	public void set_say_font(Font f) {
	  say_font = f ;
	}

	public void set_say_margin(int margin) {
	  say_margin = margin ;
	}

	public void set_say_pos(int x, int y) {
	  say_pos_x = x ;
	  say_pos_y = y ;
	}
}


class Piece {
  UFO_Attack a ;

  int px,py ;
  int opx,opy ;
  int w,h ;
  int vx,vy ;
  Color c ;
  boolean active = false ;
  Image img = null ;

  public void set_pos(int x, int y) {
    px = opx = x ;
    py = opy = y ;
  }

  public void set_vel(int x,int y) {
    vx = x ;
    vy = y ;
  }

  public void set_size(int x,int y) {
    w = x ;
    h = y ;
  }

  public void set_color(Color c) {
    this.c = c ;
  }

  public void set_draw_rectangles(Rectangle o, Rectangle n) {
    int sh = a.window_size.height ; 

    int x  = px - w/2 ;
    int y  = (sh - py) - h/2 ;

    int ox = opx - w/2 ;
    int oy = (sh - opy) - h/2 ;
    
    o.reshape(ox,oy,w,h) ; 
    n.reshape(x,y,w,h) ; 
  }

  public boolean active() {
    return active ;
  }

  public void active(boolean s) {
    active = s ;
  }

  public boolean collision(Piece p) {
    int dpx = Math.abs(px - p.px) ;
    int dpy = Math.abs(py - p.py) ;

    if ((dpx < (Math.max(w/2,p.w/2))+1) && (dpy < (Math.max(h/2,p.h/2)+1))) 
      return true ;

    return false ;
  }

  public void draw() {
    set_draw_rectangles(a.paint_area, a.new_area) ;

    // a.buf_g.setColor(a.bgColor);
    // a.buf_g.fillRect(a.paint_area.x, a.paint_area.y, w, h);

    Graphics bg = a.buffer.getGraphics() ;
    bg.clipRect(a.paint_area.x, a.paint_area.y, w, h);
    bg.drawImage(a.backdrop,0,0,a) ;
    bg.dispose() ;
    // bg = null ;

    a.buf_g.setColor(c);
    a.buf_g.fillRect(a.new_area.x, a.new_area.y, w, h);

    a.paint_area.add(a.new_area) ;

    Graphics g = a.getGraphics() ;
    g.clipRect(a.paint_area.x ,a.paint_area.y, a.paint_area.width, a.paint_area.height);
    g.drawImage(a.buffer, 0, 0, a);
    g.dispose() ;
    // g = null ;
  }

  public void erase() {
    set_draw_rectangles(a.paint_area, a.new_area) ;
    a.paint_area.add(a.new_area) ;

    // Copy the backdrop into the buffer
    Graphics bg = a.buffer.getGraphics() ;
    bg.clipRect(a.paint_area.x, a.paint_area.y, a.paint_area.width, a.paint_area.height);
    bg.drawImage(a.backdrop,0,0,a) ;
    bg.dispose() ;

    // Paint the change into the screen
    Graphics g = a.getGraphics() ;
    g.clipRect(a.paint_area.x,a.paint_area.y,a.paint_area.width,a.paint_area.height);
    g.drawImage(a.buffer,0,0, a);
    g.dispose() ;
  }
}

class Launcher extends Piece {

  public Launcher (UFO_Attack a) {
    this.a = a ; 
    w  = 12 ;
    h  = 22 ;
    px = opx = a.window_size.width/2 ;
    py = opy = w/2+1 ;
    active = true ;
    img = a.missile ;
  }

  public void move() {
    opx = px ;
    opy = py ;

    int dx     = a.mouse_x - px ;
    int abs_dx = Math.abs(dx) ;
    int step = 1 ;

    if (abs_dx > 10) 
      step = 5 ;
    else if (abs_dx > 1)
      step = abs_dx/2 ;

    if (dx != 0) {
      px += step*(dx/abs_dx) ;
      if (px < w/2) 
        px = w/2 ;
      else if (px > (a.window_size.width - w/2)) 
        px = a.window_size.width - w/2 ;
    }
  }

  public boolean has_moved() {
    if ((px - opx) != 0) return true ;

    return false ;
  }

  public void draw() {
    set_draw_rectangles(a.paint_area, a.new_area) ;

    // a.buf_g.setColor(a.bgColor);
    // a.buf_g.fillRect(a.paint_area.x, a.paint_area.y, w, h);

    Graphics bg = a.buffer.getGraphics() ;
    bg.clipRect(a.paint_area.x, a.paint_area.y, w, h);
    bg.drawImage(a.backdrop,0,0,a) ;
    bg.dispose() ;
    // bg = null ;


    if (a.M.active()) {
      a.buf_g.setColor(c);
      a.buf_g.fillRect(a.new_area.x, a.new_area.y, w, h);
    }
    else {
      bg = a.buffer.getGraphics() ;
      bg.clipRect(a.new_area.x, a.new_area.y, w, h);
      bg.drawImage(img,a.new_area.x,a.new_area.y,a) ;
      bg.dispose() ;
      // bg = null ;
    }

    a.paint_area.add(a.new_area) ;

    Graphics g = a.getGraphics() ;
    g.clipRect(a.paint_area.x ,a.paint_area.y, a.paint_area.width, a.paint_area.height);
    g.drawImage(a.buffer, 0, 0, a);
    g.dispose() ;
    // g = null ;
  }

}

class Missile extends Piece {
  public Missile (UFO_Attack a) {
    this.a = a ; 
    px = opx = 0 ;
    py = opy = 0 ;
    vx = 0 ;
    vy = 7 ;
    w  = 12 ;
    h  = 22 ;
    active = false ;

    img = a.missile ;
  }

  public void move() {
    opx = px ;
    opy = py ;

    px = a.L.px ;

    // Try to make the speed more realistic
    int dx = px - opx ;
    int nvy = vy*vy - dx*dx ;
    if (nvy > 0) nvy = (int) Math.sqrt(nvy) ; // Should exceptions
    if (nvy < 1) nvy = 1 ;

    py += nvy ;

    if (py > a.window_size.height + 2*h) active = false ;
  }

  int seq  = 0 ;
  // int seq2 = 0 ;
  public void draw() {
    set_draw_rectangles(a.paint_area, a.new_area) ;

    // a.buf_g.setColor(a.bgColor);
    // a.buf_g.fillRect(a.paint_area.x, a.paint_area.y, w, h);

    Graphics bg = a.buffer.getGraphics() ;
    bg.clipRect(a.paint_area.x, a.paint_area.y, w, h);
    bg.drawImage(a.backdrop,0,0,a) ;
    bg.dispose() ;
    // bg = null ;


    // if ((++seq2 % 4) == 0) seq = ++seq % 1 ;
    int dx = px - opx ;
    seq = 0 ;
    if (dx > 0) 
      seq = 1 ;
    else if (dx < 0)
      seq = 2 ;

    bg = a.buffer.getGraphics() ;
    bg.clipRect(a.new_area.x, a.new_area.y, w, h);
    bg.drawImage(img,a.new_area.x-w*seq,a.new_area.y,a) ;

    bg.dispose() ;
    // bg = null ;

    a.paint_area.add(a.new_area) ;

    Graphics g = a.getGraphics() ;
    g.clipRect(a.paint_area.x ,a.paint_area.y, a.paint_area.width, a.paint_area.height);
    g.drawImage(a.buffer, 0, 0, a);
    g.dispose() ;
    // g = null ;
  }

}

class UFO extends Piece {
  public UFO (UFO_Attack a) {
    this.a = a ; 
    vx = (Math.random() > 0.5 ? 1 : -1) ;
    vy = -2 ;
    w  = 20 ;
    h  = 8 ;
    int aw = a.window_size.width ;
    px = opx =  (int) (w/2+1 + (aw-w-2)* Math.random()) ;
    py = opy = a.window_size.height + h/2 + 1  ;
    active = true ;

    img = a.ufostrip ;
  }

  public void move() {
    opx = px ;
    opy = py ;

    px += vx ;
    py += vy ;

    if (py < -h/2) active = false ;

    if ((px <= w/2) || 
        (px >= (a.window_size.width - w/2)) ||
        (Math.random() > 0.96)) {
      vx = -vx ;
    }
  }

  int seq  = 0 ;
  int seq2 = 0 ;
  public void draw() {
    set_draw_rectangles(a.paint_area, a.new_area) ;

    // Clear the old image
    Graphics bg = a.buffer.getGraphics() ;
    bg.clipRect(a.paint_area.x, a.paint_area.y, w, h);
    bg.drawImage(a.backdrop,0,0,a) ;
    bg.dispose() ;
    // bg = null ;


    // Choose the image sequence (every 4 draws change frame)
    if ((++seq2 % 4) == 0) seq = ++seq % 4 ;

    // Paint new region into buffer
    bg = a.buffer.getGraphics() ;
    bg.clipRect(a.new_area.x, a.new_area.y, w, h);
    bg.drawImage(img,a.new_area.x-w*seq,a.new_area.y,a) ;
    bg.dispose() ;

    // Add old and new areas for updating
    a.paint_area.add(a.new_area) ;

    // Paint changes into screen buffer
    Graphics g = a.getGraphics() ;
    g.clipRect(a.paint_area.x ,a.paint_area.y, a.paint_area.width, a.paint_area.height);
    g.drawImage(a.buffer, 0, 0, a);
    g.dispose() ;
  }
}
	
class Explosion extends Piece {
  public Explosion (UFO_Attack a, int x, int y) {
    this.a = a ; 
    w  = 30 ;
    h  = 30 ;
    px = opx = x ;
    py = opy = y ;
    active = true ;
    img = a.missile_explosion ;
  }

  int seq  = 0 ;
  int seq2 = 0 ;
  public void draw() {
    set_draw_rectangles(a.paint_area, a.new_area) ;

    // Clear the old image
    Graphics bkd_g = a.backdrop.getGraphics();
    bkd_g.clipRect(a.paint_area.x, a.paint_area.y, w, h);
    bkd_g.drawImage(a.bgimg,0,0,a.window_size.width,a.window_size.height,a) ;

    // Choose the image sequence (every 4 draws change frame)
    if ((++seq2 % 4) == 0) seq = ++seq % 5 ;

    // Deactivate after drawing the last explosion frame
    if (seq == 4) active = false ;

    // Paint new region into buffer
    bkd_g.clipRect(a.new_area.x, a.new_area.y, w, h);
    bkd_g.drawImage(img,a.new_area.x-w*seq,a.new_area.y,a) ;
    bkd_g.dispose() ;

    // Paint changes into the buffer
    Graphics bg = a.buffer.getGraphics() ;
    bg.clipRect(a.new_area.x,a.new_area.y,w,h);
    bg.drawImage(a.backdrop,0,0,a) ;
    bg.dispose() ;

    // Paint changes into screen buffer
    Graphics g = a.getGraphics() ;
    g.clipRect(a.paint_area.x ,a.paint_area.y, a.paint_area.width, a.paint_area.height);
    g.drawImage(a.buffer, 0, 0, a);
    g.dispose() ;
  }

  public void erase() {
    set_draw_rectangles(a.paint_area, a.new_area) ;

    // Clear the old image
    Graphics bkd_g = a.backdrop.getGraphics();
    bkd_g.clipRect(a.paint_area.x, a.paint_area.y, w, h);
    bkd_g.drawImage(a.bgimg,0,0,a.window_size.width,a.window_size.height,a) ;
    bkd_g.dispose() ;

    // Do the same for the buffer and the screen
    // It will flicker any UFO on top...
    super.erase() ;
  }
}
