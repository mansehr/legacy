			//////////////////////////////////////////////////////////
			//														//
			//			Fil: "Fil hant.hpp"							//
			//														//
			//			F�rfattad av: Andreas Sehr 2002,01,19		//
			//														//
			//			Senast �ndrad:	2002,01,19					//
			//														//
			//////////////////////////////////////////////////////////

#include <stdio.h>
#include <fstream>
#include "string.hpp"

class saveFile {
	public:
		saveFile();
		~saveFile();
		bool open(char*);
		char* get(char*, bool isInt = false);
	private:
		ifstream inFil;
		String* returnString;
		String* stringOfFile;
		bool fileIsOpen;

};

saveFile::saveFile()
{
	stringOfFile = new String("");
	returnString = new String("");
}

saveFile::~saveFile()
{
	delete stringOfFile;
	delete returnString;

	if (fileIsOpen)
	{
		inFil.clear();
		inFil.close();
	}
}

bool saveFile::open(char* initFile)
{
	inFil.open(initFile);
	if(inFil.fail())
	{
		fileIsOpen = false;
		cout << "Failed to open:" << initFile << endl;
		return false;
	}
	else
	{
		fileIsOpen = true;
		cout << "Open: '" << initFile << "'" << endl;

		char ch;
		for (int i = 0; !inFil.eof(); ++i)
		{
			if (inFil.get(ch))				//Kopierar filen tilll ett str�ng objekt
				*stringOfFile += ch;		//som �r l�ttare att hantera
		}
		cout << "String copied" << endl;
		cout << "Size on stringOfFile: " << stringOfFile->getLen() << endl;

		return true;
	}
}

char* saveFile::get(char* initGet, bool isInt)
{
	ofstream fout("res.txt");
	fout << stringOfFile->getString();

	if (fileIsOpen)
	{
		String testString("");
		returnString->setToNull();

		bool beginTest = false, copyString = false, endTest = false;

		for (int i=0; i < stringOfFile->getLen(); ++i) //G�r igenom str�ng objektet tecken f�r tecken
		{
			fout << "\n" << i << "\n";
			fout << "beginTest = ";
			if (beginTest == true)
				fout << "true" << endl;
			else
				fout << "false" << endl;

			fout << "copyString = ";
			if ( copyString == true)
				fout << "true" << endl;
			else
				fout << "false" << endl;

			fout << "endTest = ";
			if (endTest == true)
				fout << "true" << endl;
			else
				fout << "false" << endl;


			if (stringOfFile[0][i] == ']')
			{
				if (testString == initGet)
				{
					if (beginTest == true)
						copyString = true;
					else if (endTest == true)
						copyString = false;
				}
				beginTest = false;
				endTest = false;
				testString.setToNull();			//Nollst�ller testString
				continue;
			}
			if (beginTest == true)
				testString += stringOfFile[0][i];

			if (stringOfFile[0][i] == '[')
			{
				if (stringOfFile[0][i+1] == '/')
				{
					endTest = true;
					copyString = false;
				}
				else
					beginTest = true;
			}

			if (copyString == true)
			{
				*returnString += stringOfFile[0][i];		//L�gga till tecken f�r tecken till
			}									//det str�ngobjekt som retureras
		}
		fout << "\n\n" << returnString->getString();
		fout << "\n" << returnString->getLen() << endl;
	/*	if (isInt == true)
			return returnString->convertToInt(); //Kan inte returna en int.....
		else
	*/		return returnString->getString();
	}
}


void main() //TestProgram
{
	saveFile testFile;
	testFile.open("textfil.txt");
	char* temp = testFile.get("Spar1");
	cout << endl << temp;
	cout << endl << "Slut" << endl;

	String nummer("1234567890");
	unsigned long int inten = nummer.convertToInt();
	cout << inten;
}
