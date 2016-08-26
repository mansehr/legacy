			////////////////////////////////////////////////////////
			//																		//
			//			Fil: "ADMenu.hpp"										//
			//																		//
			//			F�rfattad av: Andreas Sehr 2001,10,17			//
			//																		//
			//			Senast �ndrad:	2002,12,05							//
			//																		//
			////////////////////////////////////////////////////////
#ifndef __ADMenu.hpp__
#define __ADMenu.hpp__

#include "Constvars.hpp"
#include "MenuItem.hpp"
#include "MenuButton.hpp"
#include "Ram.hpp"

class Menu : public MenuButton
{
	public:
		Menu ();
		Menu (char *initText);

		void 	add 					(MenuItem &initMenuItem);
		int 	biggest 				(int a, int b);
		void 	draw 					();
		int 	getMenuBottom 		()	const { return mBottom;		}
		int 	getMenuItemAntal 	()	const { return mItemAntal;	}
		int 	getMenuLeft 		()	const { return mLeft;		}
		int 	getMenuRight 		()	const { return mRight;		}
		int 	getMenuTop 			()	const { return mTop;			}
		int 	getMenuWidth 		() const { return mWidth;		}
		void 	moveUp 				();
		void 	moveDown 			();
		void 	setActive 			();
		void 	setMenuItemAntal	(int initMIA) 				{ mItemAntal = initMIA;}
		void 	setMenuTop 			(int initMenuTop) 		{ mTop = initMenuTop;}
		void 	setMenuBottom 		(int initMenuBottom) 	{ mBottom = initMenuBottom;}
		void 	setMenuLeft 		(int initML)				{ mLeft = initML;}
		void 	setMenuRight 		(int initMR) 				{ mRight = initMR;}
		void 	setMenuWidth 		(int initMenuWidth) 		{ mWidth = initMenuWidth;}
		void 	setNotActive 		();

		MenuItem *activeMenuItem;
	 	MenuItem *menuItem[30];

	protected:
		int iActiveMenuItem;
		int mItemAntal;
		int mTop;
		int mBottom;
		int mWidth;
		int mLeft;
		int mRight;
		int textTop;
		int headMenuBar;
};

Menu::Menu()
{
	iActiveMenuItem 	= 0;
	mItemAntal 			= 0;
	mTop 					= 0;
	mBottom 				= 0;
	mWidth 				= 0;
	mLeft 				= 0;
	mRight	 			= 0;
	headMenuBar 		= 0;
	activeMenuItem 	= 0;
	for(int i = 0; i < 30; i++)
		menuItem[i] = 0;
}


Menu::Menu( char *initText)
: MenuButton(initText)
{
	Menu();
}

void Menu::add(MenuItem &initMenuItem)
{
	int i = mItemAntal;								// Spar utrymme
	menuItem[i] = &initMenuItem;					// Jobba Direkt med objektet
	menuItem[i]->setIndrag(1);						// Left + lite till(1)
	menuItem[i]->setTop (mBottom+1); 			// +1, Annars hamnar f�rsta menyraden p� knappraden
	menuItem[i]->setWidth(menuItem[i]->getWidth()  + (2 * menuItem[i]->getIndrag() ) );
	mBottom 	= menuItem[i]->getTop();			// Menyn har blivit lite l�ngre
	mWidth 	= biggest( mWidth, menuItem[i]->getWidth()+2 );	// �r den nya menyItem bredare �n bredast
	mItemAntal += 1;									// Ett till menyItem objekt
}

int Menu::biggest(int a, int b)
{
	if (a > b)
		return a;
	else return b;
}

void Menu::draw()
{
	MenuButton::draw();								// Ritar upp Knappen
	if ( getStatus() == Pressed)					// �r knappen nertryckt?
	{														// Om s�...
		textbackground(MDOWNBG);					// S�tt bakgrundsf�rgen
		Ram ram;											// Menyramen
		ram.setRows(getMenuItemAntal()+2);		// Antal rader
		ram.setCols(getMenuWidth());				// Max bredden p� menyItem
		ram.setLeft(getMenuLeft()-2);				// Left
		ram.setTop(getMenuTop()-1);				// Top
		ram.printRam();								// Rita ramen

		for(int i = 0; i < getMenuItemAntal(); i++)
		{
			menuItem[i]->draw();						// Rita menyItem texterna
		}
	}
}

void Menu::moveDown()
{
	iActiveMenuItem += 1;							// Flytta ner
	if(iActiveMenuItem >= mItemAntal)			// Utanf�r listan?
		iActiveMenuItem = 0;							// Sista objektet blir aktivt
															// Nya aktiva menynItem
	for(int i = iActiveMenuItem; i < mItemAntal; i++)
		if(menuItem[i]->getStatus() != NotPressable)	// Har vi hittat en menyItem som kan vara aktiv?
		{													// Om s�...
			if(activeMenuItem != 0)					// �r activeMenuItem initierad
				activeMenuItem->setStatus(Null); // Nollst�ll gamla objektet

			activeMenuItem = menuItem[i];			// activeMenyItem blir lika med det aktiva objektet
			activeMenuItem->setStatus(Active);	// AktiveMenu pekaren pekar p� det aktiva objektet
			iActiveMenuItem = i;						// S�tter iActiveMenu item
			return;										// Hoppa ur funktionen
		}
}

void Menu::moveUp()
{
	iActiveMenuItem -= 1;							// Flytta upp
	if(iActiveMenuItem < 0)							// Utanf�r listan?
		iActiveMenuItem = mItemAntal-1;			// Sista objektet blir aktivt
															// Nya aktiva menynItem
	for(int i = iActiveMenuItem; i >= 0; i--)
		if(menuItem[i]->getStatus() != NotPressable)	// Har vi hittat en menyItem som kan vara aktiv?
		{
			if(activeMenuItem != 0)					// �r activeMenuItem initierad
				activeMenuItem->setStatus(Null);	// Nollst�ll gamla objektet

			activeMenuItem = menuItem[i];			// activeMenyItem blir lika med det aktiva objektet
			activeMenuItem->setStatus(Active);	// AktiveMenu pekaren pekar p� det aktiva objektet
			iActiveMenuItem = i;						// S�tter iActiveMenu item
			return;										// Hoppa ur funktionen
		}
}

void Menu::setActive()
{
	this->setStatus(Pressed);						// Menyn �r aktiv
	iActiveMenuItem = 0;								// Nollst�ller menyn
	for(int i = iActiveMenuItem; i < mItemAntal; i++)
		if(menuItem[i]->getStatus() != NotPressable)	// Har vi hittat en menyItem som kan vara aktiv?
		{
			activeMenuItem = menuItem[i];			// activeMenyItem blir lika med det aktiva objektet
			activeMenuItem->setStatus(Active);	// AktiveMenu pekaren pekar p� det aktiva objektet
			iActiveMenuItem = i;						// S�tter iActiveMenu item
			return;										// Hoppa ur funktionen
		}
}

void Menu::setNotActive()
{
	this->setStatus(Null);							// Nollst�ller objektet
	iActiveMenuItem = 0;								// activeMenuItem pekar p� noll
}


#endif /*__ADMenu.hpp__*/