////////////////////////////////////////////////////////////
//																			 //
//		Fil: "ram.cpp"												 	 //
//																			 //
//		F�rfattad av: Rickard Lindberg, 2002-12-16			 //
//																			 //
//		Beskrivning: Denna fil inneh�ller en funktion som	 //
//						 skriver ut huvudramen. 					 //
//																			 //
////////////////////////////////////////////////////////////

void ritaRam(int rows = 23)		// Rita antalet rader fr�n toppen �r som standard 23
{											// G�r att skicka egna v�rden...
	// Definera f�nstrets storlek
	const int COLUMNS	 = 78;
	const int MAX_ROWS = 23;

	// Definera ram-vektorn, titeln och r�knaren
	char ram[(COLUMNS+3)*MAX_ROWS];
	char * titel = " RA Editor - Beta 0.956 ";
	int counter = 0;

	// Loopa igenom varje rad
	for (int i=1;i<=rows;i++)
	{
		// Om det �r f�rsta raden ska n�got spec skrivas ut
		if (i==1)
		{
			ram[counter++] = LUCORNER;

			for (unsigned int j=1;j<=COLUMNS;j++)
			{
				ram[counter++] = VAGRAT;

				// R�kna ut positionen d�r rubriken ska b�rja skrivas ut
				if (j==((COLUMNS/2)-(strlen(titel)/2)))
				{
					// Printa ut rubriken
					for (unsigned int k=0;k<strlen(titel);k++)
					{
						ram[counter++] = titel[k];
					}

					j+=strlen(titel);
				}
			}

			ram[counter++] = RUCORNER;
			ram[counter++] = '\r\n';
		}
		// Om det �r sista raden ska n�got spec skrivas ut
		else if (i==MAX_ROWS)
		{
			ram[counter] = LBCORNER; counter++;

			for (int j=1;j<=COLUMNS;j++)
			{
				ram[counter++] = VAGRAT;
			}

			ram[counter++] = RBCORNER;
			ram[counter++] = '\r\n';
		}
		// Om det �r en "vanlig rad skriver vi ut detta"
		else
		{
			ram[counter++] = LODRAT;

			for (int j=1;j<=COLUMNS;j++)
			{
				ram[counter++] = ' ';
			}

			ram[counter++] = LODRAT;
			ram[counter++] = '\r\n';
		}
	}
	// H�r slutar vektorn
	ram[counter] = '\0';

	textbackground(BLACK);
	textcolor(WHITE);
	gotoxy(1,2);
	cputs(ram);
}