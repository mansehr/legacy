package Chatten;
import java.awt.*;
import java.awt.event.*;

public class Dialogen extends Frame implements ActionListener {

 private Panel knappen = new Panel();
 private Dialog info 		= new Dialog(this);	//icke-modal
 private Button infoKnapp	= new Button("OK");

 public void paint(Graphics g) {
   g.setColor(Color.black);
   g.fillRect(0,0,20,20);
 }

 //Konstrukotor 1 En parmentar: Text i fältet.
 public Dialogen(Label text ) {
   info.add("Center", text);
   knappen.add("Center", infoKnapp);
   info.add("South", knappen);
   infoKnapp.addActionListener(this);		//Registrerar Lyssnare
   info.pack();		//Placera ut komponeterna
   info.show();
 }

 //Konstrukotor 2
 public Dialogen(Label text, String top, boolean modal ) {
   info.add("Center", text);
   knappen.add("South", infoKnapp);
   info.add("South", knappen);
   infoKnapp.addActionListener(this);		//Registrerar Lyssnare
   info.setTitle(top);
   info.pack();		//Placera ut komponeterna
   info.setResizable(false);
   info.setLocation(300,300);
   info.setModal(modal);
   info.show();
 }

 //Konstruktor 3
 public Dialogen() {
	  setBackground(Color.white);
   knappen.add("South", infoKnapp);
   info.add("South", knappen);
   infoKnapp.addActionListener(this);		//Registrerar Lyssnare
   info.setTitle("");
   info.pack();		//Placera ut komponeterna
   info.setResizable(true);
   info.setLocation(300,300);
   info.show();
 }


//Åtkomlighets program
  public void changeReziseble(boolean b) {
    info.setResizable(b);
  }

  public void changeLocation(int x, int y) {
    info.setLocation(x,y);
  }

  public void changeModal(boolean modal) {
   info.setModal(modal);
  }

  public void changeTitle(String text) {
   info.setTitle(text);
  }

 public void VisaInforuta() {
  info.show();
 }

 //Lyssnare
 public void actionPerformed(ActionEvent e) {
  if (e.getSource() == infoKnapp) {
   info.setVisible(false);		//Stäng dialogrutan
 }



 }

}