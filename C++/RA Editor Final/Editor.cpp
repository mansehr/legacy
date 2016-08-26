#define __VERSION__ 0.956

#include <conio>									//Inkluderar viktiga huvudbibliotek
#include <fstream>
#include <iostream>
#include <string>
#include <vector>
using namespace std;

#include "Include/Constvars.hpp"				//Inkluderar hemma-gjorda bibliotek
#include "Include/Huvudram.cpp"
#include "Include/MenuBar.hpp"
#include "Include/EditorMenu.cpp"
#include "Include/Ruta.hpp"
#include "Include/String.hpp"
#include "Include/TextField.hpp"
#include "Include/TextEdit.hpp"

void 	menuAction();								//Funktioner
void	oppnaString();
void 	quit();
void 	runProgram();
void 	sparaString();

enum ProgStatus { Quit = 0, Init, Runing, Menu };
ProgStatus progStatus = Init;

TextEdit texten;

void main ()
{
	while(progStatus != Quit)					//Hoppar ur lopen om progStatus == quit
	{
		switch(progStatus)						// Vad ska programmet g�ra
		{
			case Init:								// Starta programmet
				system("cls");						// Rensa sk�rmen
				ritaRam();							// Rita ramen
				initMenu();							// Initiera menyn
				menuBar.drawGrund();				// Rita upp meny knapparna
				//-------------------------------------------------------
				//	Rita upp den nedre baren
				//-------------------------------------------------------
				{
					char bottom[80];
					int i = 0;
					for (;i < 79; i++)
					{
						bottom[i] = ' ';
					}
					bottom[i] = '\0';
					textbackground(7);
					gotoxy(1,25);
					cputs(bottom);
				}
				texten.draw(ALL);					// Rensar text delen.
				progStatus = Runing;				// Forts�tt k�r programmet
			break;
			case Runing:							// K�r programmet
				runProgram();
			break;
			case Menu:								// Menyn �r aktiv
				gotoxy(80,25);						//	N�r man �r inne i menyn placerar vi pekaren l�ngst ner i h�gra h�rnet
				char key = getch();				// V�nta p� knapptryckning
				if(key!=0)							// Ingen special knapp
				{
					menuBar.test(key);			// Skicka informationen vidare
					if(key == CHLEFT 	|| key == CHRIGHT  || key == ENTER 	|| key == CHOPENMENU ||
					 	key == CHLEFT1 || key == CHRIGHT1 || key == CHESC)
					{									// Ta bort den meny som inte l�ngre �r aktiv
						ritaRam(1);					// Rita �versta raden p� ramen
    					texten.drawRow(0, 6);	// Uppdatera texten
					}
					menuBar.drawUpdate();		// Rita om menyn
					menuAction();					// Har man tryckt p� enter
				}
				if(menuBar.getStatus() == Null && progStatus != Quit)
				{										// Om menyn �r nollst�lld
					progStatus = Runing;			// Forts�tt k�ra programmet
				}
				break;
		}
		texten.showPointer();					// Placera ut pekaren
	}
}

//----------------------------------------------------------
//	Har man tryck p� menyn
//----------------------------------------------------------
void menuAction()
{
	if (miNytt.getStatus() == Pressed)		// �r nytt aktiv
	{
		texten.setToNull();						// Nollst�ll editorn
		texten.draw(ALL);							// Rensa allt
		miNytt.setStatus(Null);					// Nollst�ller menyItem
	}
	if (miOppna.getStatus() == Pressed)		// �r oppna aktiv
	{
		oppnaString();								// Oppna en fil
		texten.draw(ALL);							// Tar bort f�nstret
		miOppna.setStatus(Null);				// Nollst�ller menyItem
	}
	if (miSpara.getStatus() == Pressed)		// �r spara aktiv
	{
		sparaString();								// Spara till en fil
		texten.draw(ALL);							// Tar bort f�nstret
		miSpara.setStatus(Null);					// Nollst�ller menyItem
	}
	if	(miReadMe.getStatus() == Pressed)	// �r ReadMe aktiv
	{
		ifstream sparfil;							// Skapa ett filobjekt
		sparfil.open("ReadMe.txt");			// F�rs�k �ppna readMe.txt
		if (sparfil.fail())						// Misslyckades?
		{
			Popup oppna;							//	Skapa ett popupobjet f�r oppna rutan
			oppna.setRows(6);
			oppna.setCols(40);
			oppna.ritaRuta();
			oppna.printRubb(" ERROR ");
			oppna.printLine(1, "Kunde inte oppna 'ReadMe.txt'");	// Skriver ut felmeddelandet
			oppna.printLine(2,"Tryck p� valfri tangent.");
			getch();									// V�nta p� knapptryckning
		}
		else											// Det gick att �ppna filen
		{
			texten.setToNull();					// Nollst�ller texten
			char TextBuffer[512];				// Skapar en textbuffert
			while(!sparfil.eof())				// Har vi n�tt slutet p� filen
			{											// Om inte...
				sparfil.getline(TextBuffer, 512);// Kan vi l�sa en rad till
				for(unsigned int i = 0; i < strlen(TextBuffer); i++)
					texten.putIn(TextBuffer[i]);// Kopiera in textbufferten p� raden
				texten.addRow();					// Raden �r slut
			}
			sparfil.clear();						// Tar bort eof biten
			sparfil.close();						// St�nger filen
		}
		texten.draw(ALL);
		miReadMe.setStatus(Null);					// Nollst�ller menyItem
	}
	if (miHelp.getStatus() == Pressed)		// �r hj�lp aktiv
	{
		Popup hjalp;								// Ta fram popuprutan
		hjalp.setRows(11);
		hjalp.setCols(43);
		hjalp.ritaRuta();
		hjalp.printRubb(" OM EDITORN ");
		hjalp.printLine(1,"Denna editor �r utvecklad av Andreas Sehr");
		hjalp.printLine(2,"och Rickard Lindberg under jullovet 2002.");
		hjalp.printLine(4,"Ni n�r oss via mail: ");
		hjalp.printLine(5,"ande_vega@hotmail.com & webmaster@ricki.nu");
		hjalp.printLine(7,"Version: Beta 0.956");

		getch();										// V�nta p� knapptryckning
		texten.draw(ALL);							//Tar bort f�nstret
		miHelp.setStatus(Null);					// Nollst�ller menyItem
	}
	if (miAvsluta.getStatus() == Pressed)	// �r Avsluta aktiv
	{
		quit();										// Avsluta!
	}
}

//----------------------------------------------------------
//	�ppna texten
//----------------------------------------------------------
void oppnaString ()
{
	ifstream sparfil;

	Popup oppna;									//	Skapa ett popupobjet f�r oppna rutan
	oppna.setRows(7);
	oppna.setCols(40);
	oppna.ritaRuta();
	oppna.printRubb(" Oppna FIL ");
	oppna.printLine(1,"Filnamn: ");

	TextField fileName;							//	Skapa ett textf�lt d�r man kan skriva in ett filnamn
	fileName.setWidth(15);
	fileName.setTop(oppna.getActiveRow());
	fileName.setLeft(oppna.getActiveCol());
	fileName.isActive();							// Den blir aktiv s� att man kan skriva i den

	sparfil.open(fileName.getString());		// N�r man tryck p� enter f�rs�ker vi �ppna filen
	if (sparfil.fail())							// Misslyckades
	{
		String tempString;						// Skapa ett str�ngobjekt som �r l�ttare att arbeta med
		tempString = tempString + "Kunde inte oppna '" + fileName.getString() + "'";
		oppna.printLine(2, tempString.getString());	// Skriver ut felmeddelandet
		oppna.printLine(3,"Tryck p� valfri tangent.");
		getch();										// V�nta p� knapptryckning
		return;										// Hoppa ur funktionen
	}
	else												// Det gick att �ppna filen
	{
		texten.setToNull();						// Nollst�ller texten
		char TextBuffer[512];					// Skapar en textbuffert
		while(!sparfil.eof())					// Har vi n�tt slutet p� filen
		{												// Om inte...
			sparfil.getline(TextBuffer, 512);// Kan vi l�sa en rad till
			for(unsigned int i = 0; i < strlen(TextBuffer); i++)
				texten.putIn(TextBuffer[i]);	// Kopiera in textbufferten p� raden
			texten.addRow();						// Raden �r slut
		}
		sparfil.clear();							// Tar bort eof biten
		sparfil.close();							// St�nger filen

		oppna.printLine(2,"Filen �r Oppnad.");// Skriv ut att det l�stes utan problem
		oppna.printLine(3,"Tryck p� valfri tangent.");

		getch();										// V�ntar p� knapptryckning
		return;										// Hoppar ur funktionen
	}
}

//----------------------------------------------------------
//	Avslutar programmet
//----------------------------------------------------------
void quit()
{
	progStatus = Quit;							// Avsluta
	system("cls");									// Rensa sk�rmen
}

//----------------------------------------------------------
//	Funktionen som k�rs n�r TextEdit objektet �r aktivt
//----------------------------------------------------------
void runProgram()
{
	char key = getch();							// V�nta p� knapptryckning fr�n tangentbordet

	switch(key)
	{
		case CHESC:									// Man tryckte p� ESC	(�ppna menyn)
		case CHOPENMENU:							// Man tryckte p� �ppna meny knappen
			menuBar.test(key);					// Testar meny knappen == s�tter menyn till aktiv
			menuBar.drawUpdate();				// Rita den uppdaterade menyn
			progStatus = Menu;					// Menyn �r aktiv
		return;										// Ska inte testa att skriva knappen.

		default:										// Annars
			texten.testKey(key);					// Testa knapptryckningen, och rita uppdateringen
		break;
	}
}

//----------------------------------------------------------
//	Spara texten
//----------------------------------------------------------
void sparaString ()
{
	ofstream sparfil;								// Skapa ett filobjekt

	Popup spara;									//	Skapa ett popupobjet f�r spara rutan
	spara.setRows(7);
	spara.setCols(40);
	spara.ritaRuta();
	spara.printRubb(" SPARA FIL ");
	spara.printLine(1,"Filnamn: ");

	TextField fileName;							//	Skapa ett textf�lt d�r man kan skriva in ett filnamn
	fileName.setWidth(15);
	fileName.setTop(spara.getActiveRow());
	fileName.setLeft(spara.getActiveCol());
	fileName.isActive();							// Den blir aktiv s� att man kan skriva i den

	sparfil.open(fileName.getString());		// N�r man tryck p� enter f�rs�ker vi �ppna filen
	if (sparfil.fail())							// Misslyckades
	{
		String tempString;						// Skapa ett str�ngobjekt som �r l�ttare att arbeta med
		tempString = tempString + "Kunde inte oppna '" + fileName.getString() + "'";
		spara.printLine(2, tempString.getString());	// Skriver ut felmeddelandet
		spara.printLine(3,"Tryck p� valfri tangent.");
		getch();										// V�nta p� knapptryckning
		return;										// Hoppa ur funktionen
	}
	else												// Det gick att �ppna filen
	{
		sparfil << texten.getAllRows();		// Spara ALLA rader ifr�n texten
		sparfil.close();							// St�ng filen

		spara.printLine(2,"Filen �r sparad.");	// Skriv ut att det l�stes utan problem
		spara.printLine(3,"Tryck p� valfri tangent.");

		getch();										// V�nta p� knapptryckning
		return;										// Hoppa ur programmet
	}
}