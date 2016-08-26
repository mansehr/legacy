

import java.awt.*;
import java.applet.*;

  

public class particleTree extends java.applet.Applet implements Runnable 
{
    fPointTree root;
    Image      im;
    Graphics   offscreen;
    Thread     mainThread = null;
    int        width = 200;
    int        height = 300;
    int        iteration = 0;
    double     kVY = -15, kVX = 0;   // Initial velocity
    int        kMaxIteration = 30;

               // Play with these parameters if you're bored...
    double     kChanceOfBranchPoint = 0.3;
    double     kScaleAtBranch = 0.7;
    


public void init() {
  resize(width, height);
  try {
    im = createImage(width, height);
    offscreen = im.getGraphics();
  } catch (Exception e) {
    offscreen = null;
  }
  root = new fPointTree((double)width/2, height, kVX, kVY, null, null); 
}




//  Recursively run down the tree drawing lines between the points.    
public void paintApplet (Graphics g, fPointTree current) {
    int  i;

    g.setColor(Color.blue);
    if (current.left!=null) {
      g.drawLine((int) current.x, (int) current.y, (int) current.left.x, (int) current.left.y);
      paintApplet(g, current.left);
    }
    if (current.right!=null) {
      g.drawLine((int) current.x, (int) current.y, (int) current.right.x, (int) current.right.y);
      paintApplet(g, current.right);
    }
}



public void update(Graphics g) {
    paint(g);
}



public void paint(Graphics g) {
    if (offscreen != null) {
	offscreen.setColor(Color.white);
    	offscreen.fillRect(0,0, width, height);    
	paintApplet(offscreen, root);
	g.drawImage(im, 0, 0, this);
    }
    else {
	paintApplet(g, root);
    }
}

public void start() {
  if (mainThread == null) {
    mainThread = new Thread(this);
    mainThread.start();
  }
}

public void stop() {
    mainThread.stop();
    mainThread = null;
}


//  Recursively wander down the tree.  When we get to a leaf node,
//  add a new node or, with a random probability, make this a branch
//  point and add a left and a right node.  The length of the segments
//  (represented by particle velocity) is scaled after a branch.

public void AddOneLevel (fPointTree current) {
  if (current.left==null && current.right==null) {
    if (Math.random() < kChanceOfBranchPoint) {
      // A branch point!  Create a new left and right node, randomly
      // modify and scale the new velocities.
      current.left = new fPointTree(current.x + current.vX, 
				    current.y + current.vY,
				    kScaleAtBranch * (current.vX + Math.max(Math.abs(current.vX), Math.abs(current.vY)) * (Math.random() * 0.5 - 0.25)),
				    kScaleAtBranch * (current.vY + Math.max(Math.abs(current.vX), Math.abs(current.vY)) * (Math.random() * 0.5 - 0.25)),
				    null,null);
      current.right = new fPointTree(current.x + current.vX, 
				    current.y + current.vY,
				    kScaleAtBranch * (current.vX + Math.max(Math.abs(current.vX), Math.abs(current.vY)) * (Math.random() * 0.5 - 0.25)),
				    kScaleAtBranch * (current.vY + Math.max(Math.abs(current.vX), Math.abs(current.vY)) * (Math.random() * 0.5 - 0.25)),
				    null,null);
    }
    else {
      // Not a branch point.  Just add a new left node with minor
      // changes to velocity.
      current.left = new fPointTree(current.x + current.vX, 
				    current.y + current.vY,
				    current.vX + Math.max(Math.abs(current.vX), Math.abs(current.vY)) * (Math.random() * 0.5 - 0.25),
				    current.vY + Math.max(Math.abs(current.vX), Math.abs(current.vY)) * (Math.random() * 0.5 - 0.25),
				    null,null);
    }
  }
  else {
    // Not at a leaf node yet.  Recursively wander down the tree...
    if (current.left!=null)
      AddOneLevel(current.left);
    if (current.right!=null)
      AddOneLevel(current.right);
  }
}
 

public void run() {
  while (mainThread!=null) {
    try {mainThread.sleep(200);}
    catch (InterruptedException e) {}
    if (iteration < kMaxIteration) {
      repaint();	
      AddOneLevel(root);
    }
    else if (iteration > 1.5 * kMaxIteration) {
      iteration = 0;
      root = new fPointTree((double)width/2, height, kVX, kVY, null, null); 
      repaint();
      AddOneLevel(root);
    }
    iteration++;
    
  }
  mainThread = null;
}
}

























