#include <time.h>
#include "Class RAKNA.h"      //includerar Class RAKNA.h & Iostream.h  & stdlib
//#include "Minne.h"            //includerar Minne.h
#include "Hjalp.h" //"Hjalp.h" includeras med "void Hjalp()" funktionen
#include "teckenClass.h"




char DoMeny();              //Deklaration
void AvslutText();


int main ()                //MainMainMainMainMainMainMainMainMainMainMainMain
{
 RAKNA Kul;                //"Kul" blir ett objekt av klassen "RAKNA"
 int fQuit = 1;            //Om "fQuit" = 0 avslutas while satsen.
 char MenyChoice = Addera;

 while (fQuit)             //Om "fQuit" = 0 avslutas while satsen.
 {
  MenyChoice = DoMeny();   //DoMeny Tillkallas

  if (MenyChoice < Avsluta || MenyChoice > Hjlp)
  {
  cout << "\n\n\n\n\n\n\n\n\n\n";
  cout << "\t      _______________________________________________\n";
  cout << "\t     |                                               |\n";
  cout << "\t     |                 Fel inmatning!!!              |\n";
  cout << "\t     |_______________________________________________|\n";
  cout << "\n\n\n\n\n\n\n\n\n\n";
  system("PAUSE");
  continue;
  }	//Slut p� if

  switch ( MenyChoice )
  {
   case '1':
      Kul.Add();
      break;		//Break
   case '2':
      Kul.Sub();
      break;		//Break
   case '3':
      Kul.Multi();
      break;		//Break
   case '4':
      Kul.Div();
      break;		//Break
   case '5':
      Kul.Utskrift();
      break;        //Break
   case '6':
      Kul.Reset();
      cout << "\n\n\t\t\t\tMinnet rensat!!!";
      system("PAUSE");
      break;		//Break
   case '7':
      Kul.Roten();
      break;		//Break
   case '8':
      Hjalp();
      break;
   case '0':
      fQuit = 0;	//=Avslut
      AvslutText();
      break;		//Break
   default:
      cout << "\n\n\t\tFelaktigt Val!!!";
//      AvslutText();
  }  	//Slut p� switch
 }	//Slut p� while
return 0;
}	//Slut p� main


char DoMeny()		//val i menyn
{
 char meny = Addera;
			//Utskrift Av DoMeny
 cout << "\n\n\n\n\n\n\n\n\n\n";
 cout << "               __________________Meny__________________\n";
 cout << "                              1. Addera\n";
 cout << "                              2. Subtrahera\n";
 cout << "                              3. Multiplicera\n";
 cout << "                              4. Dividera\n";
 cout << "                              5. Se resultat\n";
 cout << "                              6. Rensa minnet\n";
 cout << "                              7. Roten\n";
 cout << "                              8. Hj�lp\n";
 cout << "                              0. Avsluta\n";
 cout << "\n\n\n\n\n\n\n\n";
 cout << "                                 Val: ";
 cin >> meny;	//H�mtar V�rde
 return meny;	//Returerar V�rdet "meny"
}

void AvslutText()
{
 cout << "\n\n\n\n\n\n";
 cout << "\n\t   _______________________________________________________";
 cout << "\n\t  |                                                       |";
 cout << "\n\t  |     Tack f�r att ni annv�nder min minir�knare         |";
 cout << "\n\t  |                 M.V.H Andreas Sehr                    |";
 cout << "\n\t  |                                                       |";
 cout << "\n\t  |       Om Du Har problem kan du maila till mig:        |";
 cout << "\n\t  |                ande_vega@hotmail.com                  |";
 cout << "\n\t  |_______________________________________________________|";
 cout << "\n\n\n\n\n\n\n\n\n\n\n";

 system("PAUSE");
}
