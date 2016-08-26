<?php
	$File = "dagbok.php";
	$Overs = strtok($File, ".");
	require("top.php");
	require("databas/variabler.php");
//Databas tabell
	$TableName = "Dagbok";

//Hämtar info från databasen
	$Query = "SELECT * from $TableName";
	mysql_select_db($DBName);
	$Result = mysql_query ($Query, $Link);
	while($Row = mysql_fetch_array($Result))
	{
		print("<div class=\"maintext\"><div class=\"ramen\">");
		$Time = $Row['incomeTime'];
		$Comment = $Row['skrivet'];
		print("<i>$Time</i> <br> $Comment <br>");
		print("</div></div>");
	}
	//Stänger databas länken
	mysql_close($Link);

	require("bottom.php");
?>