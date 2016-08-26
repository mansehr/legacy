import java.awt.*;
import java.util.*;

// written by Joseph Silverman (yossie@mail.blacksteel.com), last update 06/05/97

public class Odometer extends Canvas implements Runnable {

        protected Image osg;
        protected Thread t;
        
        protected int digits;
        protected int cycle = 50;
        protected int cellw, cellh;
        
        protected long history[][] = new long[5][2];
        protected int h = 0;
        
        public Odometer(long val, int digits) {
                this.digits = digits;
                history[0][0] = val * 1000;
                history[0][1] = (new Date()).getTime();
                for (int i = 1; i < 5; i++ ) {
                        history[i][0] = history[i][0];
                        history[i][1] = history[i][1];
                }
                t = new Thread(this);
                t.setPriority(1);
                t.start();
        }

        public Odometer() {
                this(0,8);
        }
        
        protected void measure() {
                FontMetrics fm = getFontMetrics(getFont());
                if (fm == null) return;
                cellw = 0;
                for (int i = 0; i < 10; i++) {
                        int w = fm.stringWidth("" + i);
                        if (cellw < w) cellw = w;
                }
                cellw += 5; // 2 on each side and room for enclosing box!
                cellh = fm.getHeight();
        }
        
        public void setFont(Font f) {
                super.setFont(f);
                measure();
        }
        
        public void setForeground(Color c) {
                super.setForeground(c);
                repaint();
        }
        
        public synchronized void setVal(long val) {
                h = (h + 1) % 5;
                history[h][0] = val * 1000;
                history[h][1] = (new Date()).getTime();
        }
        
        public void addNotify() {
                super.addNotify();
                measure();
        }
        
        public Dimension minimumSize() {
                return new Dimension(cellw*digits, cellh*4/3);
        }
        
        public Dimension preferredSize() {
                return minimumSize();
        }
        
        public void run() {
                while (true) {
                        repaint();
                        try { Thread.sleep(cycle); } catch (InterruptedException e) {}
                }
        }
        
        public void paint(Graphics g) {
                update(g);
        }
        public void update(Graphics g) {
                Dimension d = size();
                if (d.width == 0 || d.height == 0) return;
                if (osg == null) osg = createImage(d.width, d.height);

                Graphics og = osg.getGraphics();
                og.setColor(this.getBackground()); 
                og.fillRect(0, 0, d.width, d.height);
                og.setColor(this.getForeground());
                og.drawRect(0, 0, d.width, d.height);
                og.setFont(this.getFont());
                for (int i = 1; i < digits; i++)
                        og.drawLine(cellw * i, 0, cellw * i, d.height);
                
                long currval = history[h][0] + (history[h][0] - history[(h + 6) % 5][0]) *
                                ((new Date()).getTime() - history[h][1]) /
                                (history[h][1] - history[(h + 6) % 5][1]);
                long decimal = currval / 1000;
                int fraction = (int) (currval % 1000), pixels = (cellh * fraction) / 1000;
                boolean flag = (fraction != 0);
                for (int i = digits - 1; i >= 0; i--) {
                        int digit = (int) (decimal % 10);
                        decimal /= 10;
                        if (flag) {
                                og.drawString("" + digit, cellw * i + 2, cellh - pixels);
                                og.drawString("" + ((digit + 1) % 10), cellw * i + 2, cellh * 2 - pixels);
                        } else og.drawString("" + digit, cellw * i + 2, cellh);
                        flag = flag && (digit == 9);
                }
                g.drawImage(osg, 0, 0, this);
        }
}