			////////////////////////////////////////////////////////
			//																		//
			//			Fil: "ADMenuItem.hpp"								//
			//																		//
			//			F�rfattad av: Andreas Sehr 2001,10,17			//
			//																		//
			//			Senast �ndrad:	2002,12,05							//
			//																		//
			////////////////////////////////////////////////////////

#ifndef __MenuItem.hpp__
#define __MenuItem.hpp__

#include "Constvars.hpp"
#include "Objekt.hpp"

class MenuItem : public Objekt
{
	public:
		MenuItem() {;}
		MenuItem (char *initText) {setText(initText);}

		int getIndrag() const {return indrag;}

		void setIndrag (int initIndrag) {indrag = initIndrag;}

		bool test(char initKey);
		void draw();

	protected:
		int indrag;
};

void MenuItem::draw()
{
	if(this->getStatus() == NotPressable)
	{
		textcolor(DARKGRAY);
		textbackground(MDOWNBG);
	}
	else if(this->getStatus() == Active)
	{
		textcolor(MIACTIVETEXT);
		textbackground(MIACTIVEBG);
	}
	else
	{
		textcolor(MITEXT);
		textbackground(MDOWNBG);
	}
	gotoxy(getLeft(), getTop());
	cputs(getText());
}

bool MenuItem::test(char initKey)
{
	if(getStatus() == NotPressable)
	return false;

	if(getStatus() == Pressed)
		setStatus(Null);

	if(getStatus() == Active && initKey == ENTER)
		setStatus(Pressed);

	return true;
}

#endif /*__ADMenuItem.hpp__*/