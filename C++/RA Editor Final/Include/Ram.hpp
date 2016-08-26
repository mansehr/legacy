////////////////////////////////////////////////////////////
//																			 //
//		Fil: "ram.hpp"												 	 //
//																			 //
//		F�rfattad av: Rickard Lindberg, 2002-12-16			 //
//																			 //
//		Beskrivning: Denna fil inneh�ller en klass som		 //
//						 skriver ut en enkel ram. 					 //
//																			 //
////////////////////////////////////////////////////////////

/*
Ram ram;
ram.setRows(7);
ram.setCols(40);
ram.setLeft(30);
ram.setTop(2);
ram.printRam();
*/

class Ram
{
	private:

		int rows;	// Antal rader i ramen
		int cols;	// Antal columner i ramen
		int top;		// Y-position d�r ramen skall b�rja skrivas ut
		int left;	// X-position d�r ramen skall b�rja skrivas ut

	public:

		void printRam ();

		void setRows(int rader)
		{
			rows = rader;
		}

		void setCols(int kolumner)
		{
			cols = kolumner;
		}

		void setTop(int y)
		{
			top = y;
		}

		void setLeft(int x)
		{
			left = x;
		}
};

//----------------------------------------------------------
//	Rita upp ramen
//----------------------------------------------------------
void Ram::printRam ()
{
	char ram[2000];

	//-------------------------------------------------------
	//	H�ller reda p� vilken position i arrayen vi ska
	// mata in ett tecken p�
	//-------------------------------------------------------
	int counter = 0;

	//-------------------------------------------------------
	//	Lagra ramen i en array
	//-------------------------------------------------------
	for (int i=1;i<=rows;i++)
	{
		//----------------------------------------------------
		//	Om det �r f�rsta raden ska en sak lagras
		//----------------------------------------------------
		if (i==1)
		{
			ram[counter++] = TLUCORNER;

			for (int j=1;j<=cols;j++)
			{
				ram[counter++] = TVAGRAT;
			}

			ram[counter++] = TRUCORNER;
			ram[counter++] = '\n';

			//-------------------------------------------------
			//	Efter radbrytningen backar vi s� m�nga steg som
			// vi har antal kolumner+2 (ramkanterna)
			//-------------------------------------------------
			for (int k=0;k<cols+2;k++)
			{
				ram[counter++] = '\b';
			}
		}
		//----------------------------------------------------
		//	Om det �r sista raden ska en annan sak lagras
		//----------------------------------------------------
		else if (i==rows)
		{
			ram[counter++] = TLBCORNER;

			for (int j=1;j<=cols;j++)
			{
				ram[counter++] = TVAGRAT;
			}

			ram[counter++] = TRBCORNER;
			ram[counter++] = '\n';
		}
		//----------------------------------------------------
		//	Om det �r en vanlig rad lagrar vi detta
		//----------------------------------------------------
		else
		{
			ram[counter++] = TLODRAT;

			for (int j=1;j<=cols;j++)
			{
				ram[counter++] = ' ';
			}

			ram[counter++] = TLODRAT;
			ram[counter++] = '\n';

			//-------------------------------------------------
			//	Efter radbrytningen backar vi s� m�nga steg som
			// vi har antal kolumner+2 (ramkanterna)
			//-------------------------------------------------
			for (int k=0;k<cols+2;k++)
			{
				ram[counter++] = '\b';
			}
		}
	}
	//-------------------------------------------------------
	//	H�r slutar ramvektorn
	//-------------------------------------------------------
	ram[counter] = '\0';

	//-------------------------------------------------------
	//	Skriv ut ramen
	//-------------------------------------------------------
	gotoxy(left,top);
	textbackground(7);	// Gr� bg
	textcolor(0);			// Svart text
	cputs(ram);
}

