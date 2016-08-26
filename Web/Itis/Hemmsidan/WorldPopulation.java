import java.applet.*;
import java.awt.*;
import java.lang.*;
import java.util.*;
import Odometer;

// written by Joseph Silverman (yossie@mail.blacksteel.com), last update 09/21/96

public class WorldPopulation extends Applet implements Runnable {

        Odometer o;
        
        public void init() {
                setLayout(new FlowLayout());
                o = new Odometer(worldPopulation(), 15);
                o.setFont(new Font("Helvetica", Font.BOLD, 24));
                o.setBackground(Color.black);
                o.setForeground(Color.white);
                add(o);
                Thread t = new Thread(this);
                t.start();
        }

        public void run() {
                while (true) {
                        o.setVal(worldPopulation());
                        try { Thread.sleep(1000); } catch (InterruptedException e) {}
                }
        }
                
        public long worldPopulation() {
                Date current_time = new Date();
                return((long) (5733687096.0 *
                        Math.pow(1.015, (double) (current_time.getTime()/1000 -
                                807177600) / 31536000)));
        }
}