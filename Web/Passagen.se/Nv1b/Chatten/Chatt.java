package Chatten;
import java.awt.*;
import java.applet.*;
import java.awt.event.*;


public class Chatt extends Frame implements ActionListener {

//-----------Globala variabler----------
	Button avslut = new Button("Avsluta");
	TextArea text = new TextArea("",20,70,TextArea.SCROLLBARS_VERTICAL_ONLY);
	String namn1 = new String();
	Font f;

//H�gerspalten-------
	List inloggade = new List(16,false);
	TextField skriv = new TextField();

	public Chatt(String namn) {
//Test


//Bakrundsf�rg och f�nster egenskaper
	 setBackground(Color.orange);
	 setSize(300, 200);
	 setTitle("Java Chatt Av:Andreas    ;-)     Inloggad: "+namn);


//H�gerspalten
		inloggade.add(namn);
		inloggade.setBackground(Color.green);

//Egenskaper TextArea
		text.setEditable(false);
		f = new Font("Serif", Font.PLAIN, 14);
		text.setFont(f);
		text.append("~~~~~~~~~~~~~~ V�lkommen in i chatten "+namn+"!!! ~~~~~~~~~~~~~~\n\n"+
		"Kontakta mig om du f�r problem eller om du har n�gon ide p� f�rb�ttring!!!\n\n"+
		"OBS!!!! Det h�r �r en demo version, allts� inte riktigt klar �n\n"+
		"Du avsluta p� avsluta knappen l�nst ner. Det g�r inte att st�nga f�nstret \n"+
		"p� krysset(inte �n ialla fall)"+
		"\nDu har egentligen inte riktigt loggat in �n heller, men du kan skriva och \n"+
		"bed�mma layouten..."+
		"\nHa nu s� roligt\n"+"Vi h�rs ///Adde\n"+
		"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");

		text.append(namn+": LOGGAR IN\n");

//L�gnst Ner
		Panel skrivOKnapp = new Panel();
		Panel pKnapp	= new Panel();
		skrivOKnapp.setLayout(new BorderLayout());
		skrivOKnapp.add("North",skriv);
		pKnapp.setLayout(new FlowLayout());
		pKnapp.add(avslut);
		skrivOKnapp.add("South",pKnapp);
		namn1 = namn;


//Placera Ut
		setLayout(new BorderLayout());
		add("Center", text);
		add("East", inloggade);
		add("South", skrivOKnapp);
		pack();

//Actionlisteners
	avslut.addActionListener(this);
	skriv.addActionListener(this);
	}//Slut konstruktor


//Denna metod anropas n�r h�ndelse intr�ffar
 public void actionPerformed(ActionEvent e) {
	if (e.getSource()==avslut) {	//h�ndelse i svarsrutan
   		this.setVisible(false);

	}
	if (e.getSource()==skriv) {
	   String skrivUt = skriv.getText();
	   skriv.setText("");
	   text.append(namn1+" s�ger: "+ skrivUt +"\n");
	}

  }//Slut actionperformed

}//Slut chatten