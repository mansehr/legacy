
//***************************************************************
//
//   FIL: Pekare.h      VERSION: 0.01
//   
//   SYFTE: Skapa nya pekare och tea bort dem sen. 
//   Och samtidigt ge dem värdet NULL.
//
//   ANMÄRKNING: Klarar bara int variabler 
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
};   //Slut på Pekare

int* Pekare::New (int *p)
{
 p = new  int;  //En vanlig pekare får ny plats på det fria lagrings ytrummet
 *p = 1;	     //Den får värdet 1   
return p;       //Sen returnreras pekaren
}

int* Pekare::Delete (int *c)
{
 delete c;     //Pekaren tas bort från det fria lagring utrymmet.
return 0;      //Pekaren får värdet NULL
}

Pekare myP;    //Skapar en medlem av klassen PEKARE.
