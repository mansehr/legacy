<?php
	$File = "dagbok.php";
	$Overs = strtok($File, ".");
	require("top.php");
	require("databas/variabler.php");
//Databas tabell
	$TableName = "Dagbok";

//H�mtar info fr�n databasen
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
	//St�nger databas l�nken
	mysql_close($Link);

	require("bottom.php");
?>