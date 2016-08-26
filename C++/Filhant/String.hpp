			//////////////////////////////////////////////////////////
			//														//
			//			Fil: "String.hpp"							//
			//														//
			//			F�rfattad av: Andreas Sehr 2002,01,19		//
			//														//
			//			Senast �ndrad:	2002,01,19					//
			//														//
			//////////////////////////////////////////////////////////

// Grundl�ggande str�ngklass
class String
{
	public:
		//Konstruerare
		String();
		String(const char* const);
		String(const String &);
		String (unsigned short);
		~String();

		//�verlagrade Operatorer
		char & operator [] (unsigned short offset);
		char operator [] (unsigned short offset) const;
		String operator + (const String&);
		void operator += (const String&);
		void operator += (const char);
		String & operator = (const String &);
		bool operator == (const String &);
		bool operator == (const char*);

		//Gener�lla �tkomstmetoder
		unsigned short getLen() const {return itsLen;}

		char * getString() {return itsString;}

		unsigned long int convertToInt();	//Klarar tyv�r bara av positiva tal...
		void isSwe(bool initBool) { sweString = initBool;}
		void setToNull ();
		//Egen Funktion
		void setLength(int initLen);
		void changeToSwe();
	private:
		char * itsString;
		unsigned short itsLen;
		bool sweString;
};

//Standard konstruerare skapar en str�ng p� 0 bytes
String::String()
{
	itsString = new char[1];
	itsString[0] = '\0';
	itsLen = 0;
	bool sweString = false;
}

//Privat (hj�lp-) konstruerare, som anv�nds endast av klassmetoder
//f�r att skapa en ny str�ng av en angiven storlek. Null-utfylld.
String::String(unsigned short len)
{
	itsString = new char[len+1];
	for (unsigned short i = 0; i <= len; ++i)
		itsString[i] = '\0';
	itsLen = len;
}

//Omvandlar en vektor av tecken till en string
String::String(const char * const cString)
{
	itsLen = strlen(cString);
	itsString = new char[itsLen+1];
	for (unsigned short i = 0; i < itsLen; ++i)
		itsString[i] = cString[i];
	itsString[itsLen] = '\0';
}

//Kopieringskonstruerare
String::String(const String & rhs)
{
	itsLen = rhs.getLen();
	itsString = new char[itsLen+1];
	for (unsigned short i = 0; i < itsLen; i++)
		itsString[i] = rhs[i];
	itsString[itsLen] = '\0';
}

//Destruerare, l�mmnar tillbaka reserverat minne
String::~String()
{
	delete [] itsString;
	itsLen = 0;
}

//Operator =, l�mmnar tillbaka existerande minne, kopierar sedan str�ngen och storleken
String & String::operator = (const String & rhs)
{
	if (this == &rhs)
		return *this;
	delete [] itsString;
	itsLen = rhs.getLen();
	itsString = new char[itsLen+1];
	for (unsigned short i = 0; i < itsLen; i++)
		itsString[i] = rhs[i];
	itsString[itsLen] = '\0';
	return *this;
}

//Icke konstant indexoperator, returnerar en referens till teknet s� att det g�r att �ndra
char & String::operator [] (unsigned short offset)
{
	if (offset	> itsLen)
		return itsString[itsLen-1];
	else
		return itsString[offset];
}

//Const indexoperator f�r annv�ndning p� const-objekt ( se kopieringkonstruerare)
char String::operator [] (unsigned short offset) const
{
	if (offset	> itsLen)
		return itsString[itsLen-1];
	else
		return itsString[offset];
}

//Skapar en ny str�ng genom att l�gga rhs till den aktuella str�ngen.
String String::operator + ( const String& rhs)
{
	unsigned short totalLen = itsLen + rhs.getLen();
	String temp(totalLen);
	unsigned short i;
	for ( i = 0; i < itsLen; ++i)
		temp[i] = itsString[i];
	for (unsigned short j = 0; j < rhs.getLen(); ++j, ++i)
		temp[i] = rhs[j];
	temp[totalLen] = '\0';
	return temp;
}

//�ndrar den aktuella str�ngen, returnerar ingenting
void String::operator += (const String& rhs)
{
	unsigned short rhsLen = rhs.getLen();
	unsigned short totalLen = itsLen + rhsLen;
	String temp(totalLen);
	unsigned short i;
	for (i = 0; i < itsLen; ++i)
		temp[i] = itsString[i];
	for (unsigned short j = 0; j < rhs.getLen(); ++j, ++i)
		temp[i] = rhs[i-itsLen];
	temp[totalLen] = '\0';
	*this = temp;
}
//L�gger till en cha och ut�kar str�ngens storlek
void String::operator += (const char initChar)
{
	itsLen += 1;
	String temp(itsLen);
	temp = *this;

	temp[itsLen-1] = initChar;
	temp[itsLen] = '\0';
	*this = temp;
}
//Kollar om tv� st�ngar �r likadana
bool String::operator == (const String& rhs)
{
	if (rhs.getLen() != this->getLen())
		return false;

	char ch1, ch2;
	for (int i=0; i < this->getLen(); ++i )
	{
		ch1 = itsString[i];
		ch2 = rhs[i];

		if (ch1 != ch2)
	 		return false;
	}
	return true;
}
//Testar en str�ng och en char pekare
bool String::operator == (const char * initChar)
{
	for (int i=0; i < this->getLen(); ++i )
	{
		if (itsString[i] != initChar[i] || initChar[i] == '\0')
	 		return false;
	}

	if (initChar[this->getLen()] != '\0')	//Testar om char pekaren �r l�ngre �n string objektet
		return false;
	else
		return true;
}
//S�tter st�ngens l�ngd
void String::setLength(int initLen)
{
	if ( (initLen - itsLen) < 0)
		return;
	else
	{
		itsString = new char[initLen-itsLen];
		for (unsigned short i = itsLen - 1; i <= initLen - itsLen; ++i)
			itsString[i] = '\0';
		itsLen = initLen;
	}
}

//Returerar (om str�ngen best�r av bara siffror) en unsigned int som �r likadan som str�ngen fast en int
unsigned long int String::convertToInt()
{
	unsigned long int returnInt = 0;
	int tempInt = 0, tioPotens = 10;

	if (itsLen > 10)	//Om str�ngen �r l�ngre �n tio kan inte variabeln unsigned int hantera den
		return 0;

	for (int i = itsLen - 1; i >= 0 ; --i)
	{
//		cout << "itsString[" << i << "]: " << itsString[i] << endl;
		switch (itsString[i])
		{
			case '0': tempInt = 0; break;
			case '1': tempInt = 1; break;
			case '2': tempInt = 2; break;
			case '3': tempInt = 3; break;
			case '4': tempInt = 4; break;
			case '5': tempInt = 5; break;
			case '6': tempInt = 6; break;
			case '7': tempInt = 7; break;
			case '8': tempInt = 8; break;
			case '9': tempInt = 9; break;

			default: return 0;
		}
		tioPotens = 10;
	//Obs om utr�kningen inte blir noll utf�rs ber�kningen
		if (!(itsLen - 1 - i == 0))
		{
			for (int j = 1; j < itsLen - 1 - i; ++j)
				tioPotens *= 10;

			tempInt *= tioPotens; 	//R�knar ut tio potensen
		}
//		cout << "TempInt: " << tempInt << endl;

		returnInt += tempInt;
	}
	return returnInt;
}

void String::setToNull()
{
	delete [] itsString;
	itsLen = 0;

	itsString = new char[itsLen + 1];
	itsString [0] = '\0';
}

void String::changeToSwe()
{
	for ( int i = 0; i < itsLen; i++)
	{
		switch (itsString[i])
		{
			case '�': itsString[i] = 134; break;  //Det �r det nummer som annv�nds
			case '�': itsString[i] = 143; break;  //i den svenska tecken tabellen.
			case '�': itsString[i] = 132; break;
			case '�': itsString[i] = 142; break;
			case '�': itsString[i] = 148; break;
			case '�': itsString[i] = 153; break;
		}
   	}
}
