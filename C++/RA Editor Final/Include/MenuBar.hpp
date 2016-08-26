			//////////////////////////////////////////////////////////
			//														//
			//			Fil: "ADMenuBar.hpp"						//
			//														//
			//			F�rfattad av: Andreas Sehr 2001,10,17		//
			//														//
			//			Senast �ndrad:	2002,12,05					//
			//														//
			//////////////////////////////////////////////////////////
#ifndef __ADMenuBar.hpp__
#define __ADMenuBar.hpp__

#include "Constvars.hpp"
#include "Menu.hpp"
//----------------------------------------------------------
//	MenuBar: Har hand om alla Menu objekt. S�tter variablerna
// till dessa n�r de l�ggs till i listan. Anv�nd funktionen
// drawGrund f�r att rita upp allting. DrawUpdate f�r att rita
// n�r n�got �r aktivt. Objektet kan inte rensa sina menyer sj�lv.
// M�ste ha hj�lp f�r att ta bort menyer som varit aktiva.
//----------------------------------------------------------
class MenuBar : public Objekt
{
	public:
		MenuBar();

		void 	add 			(Menu &initMenu);
		void 	drawGrund	();
		void 	drawUpdate	();
		int 	getEnd		() 	const { return right; }
		int 	getMenuAntal() 	const { return mAntal; }
		void 	test			(char initKey);

	private:
		Menu 	*menu[15];
		Menu 	*activeMenu;
		int 	iAktiveMenu;
		int 	mAntal;
		Status menuStatus;
};
//----------------------------------------------------------
//	MenuBar grund konstruktor.
// S�tter v�rden till alla variabler s� att det inte blir n�got minnesfel
//----------------------------------------------------------
MenuBar::MenuBar() : Objekt()
{
	mAntal 		= 0;									// Inga under menyer har lagts till �n
	top 			= 1;									// Den rad som objektet �r p�
	left 			= 1;									// Allt ska vara lika med 1 s� att allt kommer
	right 		= 1;									// innanf�r sk�rmen n�r man annv�nder gotoxy(int x, int y)
	menuStatus 	= Null;								// Objektet �r inte aktivt p� n�t s�tt
	activeMenu 	= 0;									// Ingen under meny finns som kan vara aktiv
	iAktiveMenu = 0;									// -||-
	for(int i = 0; i < 15; i++)
		menu[i] = 0;									// Rensar minnet
}

//----------------------------------------------------------
//	H�r l�ggs alla Menu objekt till. Alla v�rden s�tts. �ven
// nere p� MenuItem niv�
//----------------------------------------------------------
void MenuBar::add(Menu &initMenu)
{
	int i = mAntal;									//Vilken knapp i vektorn
	menu[i] = &initMenu;								//Jobba med objektet
//Knappen
	menu[i]->setTop(top);							// S�tter knappens rad som den �r p�
	menu[i]->setLeft(right+1);						// S�tter kanppens v�nstra pos
	menu[i]->setRight(menu[i]->getLeft() + menu[i]->getWidth());	// R�knar ut och s�tter knappens h�gra del
//Menyn bakgrunden under menyItem
	menu[i]->setMenuTop(top+2);
	menu[i]->setMenuBottom(top+2);
	menu[i]->setMenuLeft(menu[i]->getLeft()+1);
	menu[i]->setMenuRight(menu[i]->getMenuLeft() + menu[i]->getMenuWidth());
//Uppdatera menyItem v�rdena i meny Objektet
	for (int z = 0; z < menu[i]->getMenuItemAntal(); z++)
	{
		menu[i]->menuItem[z]->setTop( menu[i]->menuItem[z]->getTop()+this->getTop()+1);
		menu[i]->menuItem[z]->setLeft( menu[i]->getLeft() + 1 );
		menu[i]->menuItem[z]->setRight( menu[i]->getMenuRight() - 1);
	}
	right = menu[i]->getRight()+1;				//MenuBar ny h�ger
	mAntal++;											//�ka Antalet objekt
}

void MenuBar::drawGrund()
{
//Den tomma gr�a raden
	char tom[81];
	for(int i = 0; i < getWidth(); i++)
		tom[i] = ' ';
	tom[getWidth()] = '\0';
	textbackground(MBARBG);
	gotoxy(getTop(),getLeft());
	cputs(tom);

	drawUpdate();										//Knapparna och activa menyer
}

void MenuBar::drawUpdate()
{
	//Rita menyerna
	for ( int i = 0; i < mAntal; ++i)
	{
		menu[i]->draw();
	}
}

void MenuBar::test(char initKey)
{
	if(initKey == ENTER)								//ENTER
	{
		this->setStatus(Null);						// Nollst�ll menyBar
		activeMenu->setNotActive();				// Nollst�ll menyn
		if(activeMenu != 0 && activeMenu->activeMenuItem != 0)// Testar om de pekar p� n�got objekt
			activeMenu->activeMenuItem->test(initKey);			// Testar om vi kan s�tta menyItem Pressed
	}


	if(initKey == CHOPENMENU || initKey == CHESC)						//Visa menu
		if(this->getStatus() != Active)			// Om menuBar INTE �R aktiv blir den det
		{
			this->setStatus(Active);				// menuBar blir aktiv
			iAktiveMenu = 0;							// F�rsta menynObjektet (L�ngst till v�nster)
			activeMenu = menu[iAktiveMenu];		// AktiveMenu pekaren pekar p� det aktiva objektet
			activeMenu->setActive();				// F�rsta menyn blir aktiv
		}
		else
		{
			this->setStatus(Null);					// Nollst�ller menyBar
			activeMenu->setNotActive();;			// Nollst�ller menyn
			if(activeMenu->activeMenuItem != 0)
				activeMenu->activeMenuItem->setStatus(Null);
		}

	if(initKey == CHRIGHT || initKey == CHRIGHT1)//Pil h�ger
		if(this->getStatus() == Active)			// Om menuBar �R aktiv
		{
			iAktiveMenu += 1;							// Flytta h�ger
			if(iAktiveMenu >= mAntal)				// Utanf�r listan?
				iAktiveMenu = 0;						// F�rsta objektet blir aktivt
			activeMenu->setNotActive();			// Nollst�ll gamla objektet
			if(activeMenu->activeMenuItem != 0)
				activeMenu->activeMenuItem->setStatus(Null);
			activeMenu = menu[iAktiveMenu];		// AktiveMenu pekaren pekar p� det aktiva objektet
			activeMenu->setActive();				// Det nya aktiva objektet blir aktivt
		}

	if(initKey == CHLEFT || initKey == CHLEFT1)//Pil V�nster
		if(this->getStatus() == Active)			// Om menuBar �R aktiv
		{
			iAktiveMenu -= 1;							// Flytta v�nster
			if(iAktiveMenu < 0)						// Utanf�r listan?
				iAktiveMenu = mAntal-1;				// Sista objektet blir aktivt
			activeMenu->setNotActive();			// Nollst�ll gamla objektet
			if(activeMenu->activeMenuItem != 0)
				activeMenu->activeMenuItem->setStatus(Null);
			activeMenu = menu[iAktiveMenu];		// AktiveMenu pekaren pekar p� det aktiva objektet
			activeMenu->setActive();				// Det nya aktiva objektet blir aktivt
		}

	if(initKey == CHUP || initKey == CHUP1)	//Pil Upp
		if(this->getStatus() == Active)			//Om menuBar �R aktiv
			activeMenu->moveUp();					// Flytta upp i menyn

	if(initKey == CHDOWN || initKey == CHDOWN1)//Pil Ner
		if(this->getStatus() == Active)			//Om menuBar �R aktiv
			activeMenu->moveDown();					// Flytta ner i menyn
}

#endif /*__ADMenuBar.hpp__*/