////////////////////////////////////////////////////////////
//																			 //
//		Fil: "ruta.hpp"												 //
//																			 //
//		F�rfattad av: Rickard Lindberg, 2002-12-16			 //
//																			 //
//																			 //
//		Beskrivning: Denna fil inneh�ller en klass som		 //
//						 skriver ut en popupruta. Man kan		 //
//						 sedan enkelt l�gga till rubrik och		 //
//						 text i rutan med hj�lp av metoder.		 //
//																			 //
////////////////////////////////////////////////////////////

class Popup
{
	private:

		int rows;	// Antal rader i rutan
		int cols;	// Antal columner i rutan
		int activeRow;
		int activeCol;
		int top;		// Y-position d�r rutan skall b�rja skrivas ut
		int left;	// X-position d�r rutan skall b�rja skrivas ut

	public:

		void ritaRuta ();

		void setRows(int rader)
		{
			rows = rader;
		}

		void setCols(int kolumner)
		{
			cols = kolumner;
		}

		int getActiveRow()
		{
			return activeRow;
		}

		int getActiveCol()
		{
			return activeCol;
		}

		//----------------------------------------------------
		//	Skriver ut en centrerad rubrik p� f�rsta raden
		//----------------------------------------------------
		void printRubb(char * text)
		{
			gotoxy(left+(cols/2)-strlen(text)/2,top);
			cputs(text);
			gotoxy(80,25);
		}

		//----------------------------------------------------
		//	Skriver ut en text p� rad X
		//----------------------------------------------------
		void printLine(int rad, char * text)
		{
			textbackground(1);	// Bl� bg
			textcolor(15);			// Vit text
			gotoxy(left+2,top+1+rad);

			activeRow = top+1+rad;
			activeCol = left+2+strlen(text);

			cputs(text);
			gotoxy(80,25);
		}
};

//----------------------------------------------------------
//	Rita upp popup-rutan med skugga
//----------------------------------------------------------
void Popup::ritaRuta ()
{
	char ruta[2000];
	char skugga[2000];

	//-------------------------------------------------------
	//	H�ller reda p� vilken position i arrayen vi ska
	// mata in ett tecken p�
	//-------------------------------------------------------
	int counter = 0;

	//-------------------------------------------------------
	//	Lagra skuggan i en array
	//-------------------------------------------------------
	for (int i=1;i<=rows;i++)
	{
		for (int j=1;j<=cols+2;j++)
		{
			skugga[counter++] = ' ';
		}

		skugga[counter++] = '\n';

		//----------------------------------------------------
		//	Efter radbrytningen backar vi s� m�nga steg som
		// vi har antal kolumner+2 (ramkanterna)
		//----------------------------------------------------
		for (int k=0;k<cols+2;k++)
		{
			skugga[counter++] = '\b';
		}
	}
	//-------------------------------------------------------
	//	H�r slutar skuggvektorn
	//-------------------------------------------------------
	skugga[counter] = '\0';
	counter = 0;

	//-------------------------------------------------------
	//	Lagra f�nstret i en array
	//-------------------------------------------------------
	for (int i=1;i<=rows;i++)
	{
		//----------------------------------------------------
		//	Om det �r f�rsta raden ska en sak lagras
		//----------------------------------------------------
		if (i==1)
		{
			ruta[counter++] = LUCORNER;

			for (int j=1;j<=cols;j++)
			{
				ruta[counter++] = VAGRAT;
			}

			ruta[counter++] = RUCORNER;
			ruta[counter++] = '\n';

			//-------------------------------------------------
			//	Efter radbrytningen backar vi s� m�nga steg som
			// vi har antal kolumner+2 (ramkanterna)
			//-------------------------------------------------
			for (int k=0;k<cols+2;k++)
			{
				ruta[counter++] = '\b';
			}
		}
		//----------------------------------------------------
		//	Om det �r sista raden ska en annan sak lagras
		//----------------------------------------------------
		else if (i==rows)
		{
			ruta[counter++] = LBCORNER;

			for (int j=1;j<=cols;j++)
			{
				ruta[counter++] = VAGRAT;
			}

			ruta[counter++] = RBCORNER;
			ruta[counter++] = '\n';
		}
		//----------------------------------------------------
		//	Om det �r en vanlig rad lagrar vi detta
		//----------------------------------------------------
		else
		{
			ruta[counter++] = LODRAT;

			for (int j=1;j<=cols;j++)
			{
				ruta[counter++] = ' ';
			}

			ruta[counter++] = LODRAT;
			ruta[counter++] = '\n';

			//-------------------------------------------------
			//	Efter radbrytningen backar vi s� m�nga steg som
			// vi har antal kolumner+2 (ramkanterna)
			//-------------------------------------------------
			for (int k=0;k<cols+2;k++)
			{
				ruta[counter++] = '\b';
			}
		}
	}
	//-------------------------------------------------------
	//	H�r slutar popupvektorn
	//-------------------------------------------------------
	ruta[counter] = '\0';

	//-------------------------------------------------------
	//	R�kna ut x och y f�r centrering
	// (punkt uppe i v�nstra h�rnet)
	//-------------------------------------------------------
	top	= 12-(rows/2);
	left	= 40-(cols/2);

	//-------------------------------------------------------
	//	Skriv ut skuggan
	//-------------------------------------------------------
	gotoxy(left+1,top+1);
	textbackground(7); // Gr� f�rg p� skuggan
	cputs(skugga);

	//-------------------------------------------------------
	//	Skriv ut rutan ovanp� skuggan
	//-------------------------------------------------------
	gotoxy(left,top);
	textbackground(1);	// Bl� bg
	textcolor(15);			// Vit text
	cputs(ruta);
}

