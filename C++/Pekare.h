
//***************************************************************
//
//   FIL: Pekare.h      VERSION: 0.01
//   
//   SYFTE: Skapa nya pekare och tea bort dem sen. 
//   Och samtidigt ge dem v�rdet NULL.
//
//   ANM�RKNING: Klarar bara int variabler 
//   
//   SKRIVEN AV: Andreas Sehr 2001.06.05 kl 16.14
//
//
//***************************************************************


class Pekare   //Skapar en ny klass med nammnet "Pekare"
{
public:
 int* New (int *p);     //Funktion som skapar en ny pekare av formen int.
 int* Delete (int *c);  //Funktion som tar bort en int pekare.
};   //Slut p� Pekare

int* Pekare::New (int *p)
{
 p = new  int;  //En vanlig pekare f�r ny plats p� det fria lagrings ytrummet
 *p = 1;	     //Den f�r v�rdet 1   
return p;       //Sen returnreras pekaren
}

int* Pekare::Delete (int *c)
{
 delete c;     //Pekaren tas bort fr�n det fria lagring utrymmet.
return 0;      //Pekaren f�r v�rdet NULL
}

Pekare myP;    //Skapar en medlem av klassen PEKARE.
