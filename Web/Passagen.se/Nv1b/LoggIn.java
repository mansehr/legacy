import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import Chatten.*;

public class LoggIn extends Applet implements ActionListener {



       //--Namn--
private String namn[] = new String[] {
"Andreas",		//0
"Klas",
"Jonas",
"Maria",
"Fredrik",
"Emil",
"Peter",
"Daniel",
"Marie",
"Marina",
"Prisca",
"Rickard",
"Sandra",
"Joel",
"Johanna",
"Emmelie"};		//15

		//--Password--
private String[] passw = new String[] {
"Adde_Bäst",	//0
"",
"",
"",
"",
"",
"",
"",
"",
"",
"",
"lurk",
"",
"",
"",
""};		//15

Font f;

//Textfield & button         (Globala Variabler)
	TextField annv = new TextField(10);
	TextField pass = new TextField(10);
	Button logg	= new Button("Logga In");



  public void init() {
//Labelobjekt
	Label textAn = new Label("Annvändare:");
	Label textPas = new Label("Lösenord:");
//Fonten för Label
	f = new Font("Serif", Font.BOLD, 16);
	textAn.setFont(f);
	textPas.setFont(f);
//Fonten för TextField
	annv.setFont(new Font("Serif", Font.BOLD, 14));		//obs annv=BOLD
	pass.setFont(new Font("Serif", Font.PLAIN, 14));	//obs pass=PLAIN
//Fonten på knappen
	f = new Font("Serif", Font.BOLD, 16);
	logg.setFont(f);

//Bakrundsfärgen
  setBackground(Color.white);
//Layout
  GridBagLayout m = new GridBagLayout();
  setLayout(m);
  GridBagConstraints con;
  con = new GridBagConstraints();

  //Rad ~~~~~~1~~~~~~
  con.gridy = 10;
  con.gridx = 19;
  con.gridheight = 2;			//Text "Annvändare"
  m.setConstraints(textAn, con);
  add(textAn);

  con.gridx = 20;
  m.setConstraints(annv, con); 		//Användaren. Fält
  add(annv);

  //Rad ~~~~~~2~~~~~~
  con.gridy = 13;
  con.gridx = 19;			//Text "Lösenord"
  m.setConstraints(textPas, con);
  add(textPas);

  con.gridx = 20;
  m.setConstraints(pass, con);		//Lösenord. Fält
  pass.setEchoChar('*');
  add(pass);

  //Rad ~~~~~~3~~~~~~
  con.gridy = 18;
  con.anchor = GridBagConstraints.WEST;
  m.setConstraints(logg, con);		//Knappen
  add(logg);

//fästa lyssnaren till knappen
  logg.addActionListener(this);
 }//Slut init


//Bestämmer positionen. Fungerar bra!!!
 void position(int Y, int X, int height, GridBagConstraints con) {
  con.gridy = Y;
  con.gridx = X;
  con.gridheight = height;
 }//Slut Position


//Denna metod anropas när händelse inträffar
 public void actionPerformed(ActionEvent e) {
   if (e.getSource()==logg) {				 //händelse i svarsrutan
    String annvandare = annv.getText();
    String password = pass.getText();
    int i = Dialog(annvandare,password);



  }//Slut Actionperformed
 }

//Anropar Dialogrutan
 int Dialog(String Annv, String Passv)
 {
    boolean loggin = false;
    for(int i = 0; i<16; ++i)
    {

      if (Annv.equalsIgnoreCase(namn[i]))
      {
        loggin = true;
        if (Passv.equals(passw[i]) )
        {
			Dialogen di1 =  new Dialogen(new Label("Välkommen in i chatten " + namn[i] + "!!!" ),"Välkommen! :-)", true);
           	Chatt chatt = new Chatt(namn[i]);
			chatt.setVisible(true);
           	return i;
     	}
        else
        {
       		Dialogen di2 =  new Dialogen(new Label("Fel Lösenord!!"), "Ojojoj :-|", true);
 			return -1;
      	}
      }
    }
    if (loggin != true) {
  		Dialogen di3	 =  new	Dialogen(new Label("Fel Annvändare!!"), "Warning :-(", true);

    }
    return 1;
 }//Slut Dialog

}//Slut LoggIn
