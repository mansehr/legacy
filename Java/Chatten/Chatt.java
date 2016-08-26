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

//Högerspalten-------
	List inloggade = new List(16,false);
	TextField skriv = new TextField();

	public Chatt(String namn) {
//Test


//Bakrundsfärg och fönster egenskaper
	 setBackground(Color.orange);
	 setSize(300, 200);
	 setTitle("Java Chatt Av:Andreas    ;-)     Inloggad: "+namn);


//Högerspalten
		inloggade.add(namn);
		inloggade.setBackground(Color.green);

//Egenskaper TextArea
		text.setEditable(false);
		f = new Font("Serif", Font.PLAIN, 14);
		text.setFont(f);
		text.append("~~~~~~~~~~~~~~ Välkommen in i chatten "+namn+"!!! ~~~~~~~~~~~~~~\n\n"+
		"Kontakta mig om du får problem eller om du har någon ide på förbättring!!!\n\n"+
		"OBS!!!! Det här är en demo version, alltså inte riktigt klar än\n"+
		"Du avsluta på avsluta knappen länst ner. Det går inte att stänga fönstret \n"+
		"på krysset(inte än ialla fall)"+
		"\nDu har egentligen inte riktigt loggat in än heller, men du kan skriva och \n"+
		"bedömma layouten..."+
		"\nHa nu så roligt\n"+"Vi hörs ///Adde\n"+
		"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");

		text.append(namn+": LOGGAR IN\n");

//Lägnst Ner
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


//Denna metod anropas när händelse inträffar
 public void actionPerformed(ActionEvent e) {
	if (e.getSource()==avslut) {	//händelse i svarsrutan
   		this.setVisible(false);

	}
	if (e.getSource()==skriv) {
	   String skrivUt = skriv.getText();
	   skriv.setText("");
	   text.append(namn1+" säger: "+ skrivUt +"\n");
	}

  }//Slut actionperformed

}//Slut chatten