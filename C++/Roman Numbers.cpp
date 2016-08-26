

/// Int to String
/// Gör om ett heltal till romerska siffror.

String IntToRoman(unsigned int init)
{
	String retString = new String("");
	if(init > 0 && init < 13)
	{
		/*while(temp != 0)
		{

		}*/
		if(init >= 10)
		{
			retString += 'X';
			init -= 10;
		}
		if(init >= 5)
		{
			retString += 'V';
			init -= 5;
		}
		if(init == 4)
		{
			retString += "IV";
			init -= 4;
		}
		if(init >= 1)
		{
			retString += 'I';
			init -= 1;
		}
	}
	return retString;
}