<?php

$Action = $_GET['Action'];

switch($Action)
{
	case "loggIn": LoggInChat(); break;
	case "enterChat": enterChat(); break;
	case "putInMessage": putInComment();		//Inge break, ska sen skriva nytt meddelande
	case "typeMessage": typeComment(); break;
	case "showMessage": showComment(); break;
}

//----------------------------------------LoggInChat()--------------------------------------
function LoggInChat()
{
	$File = "chat.php";
	$Overs = strtok($File, ".");
	require("top.php");
?>
<center>
<table border=0 height=350px>
<tr><td valign="middle" align="center">
<h1>Skriv ditt namn!</h1>
<form action="chat.php" method="get">
<input type="text" name="Name"><br><br>
<input type="submit" value="Gå in i chatten">
<input type="hidden" name="Action" value="enterChat">
<input type="hidden" name="InloggTime" value="<?php $datum=date("U");print "$datum"; ?>">
</form>
</td></tr>
</table>
</center>
<?php
	require("bottom.php");
}

//----------------------------------------enterchat()--------------------------------------
function enterChat () {
	$Name = $_GET['Name'];
	$InloggTime = $_GET['InloggTime'];
	print("<head><title>||| Sihr chat :) Enjoy |||</title></head>");
	//print "<body onLoad=\"alert('Välkommen in i chatten $Name!');\"> \n";
	print "<frameset rows=\"*,120\" frameborder=1 framespacing=0>\n";
	print "	<frame src=\"chat.php?Action=showMessage&Name=$Name&InloggTime=$InloggTime\" name=\"showMessageFrame\" noresize scrolling=\"Yes\">\n";
	print "	<frame src=\"chat.php?Action=putInMessage&Name=$Name&InloggTime=$InloggTime&Comment=Loggar+In\" name=\"typeMessageFrame\" noresize scrolling=\"no\">\n";
	print "</frameset>\n </body>";
}

//----------------------------------------showComment()--------------------------------------
function showComment() {
	$File = "chat.php";
	require("databas/variabler.php");
//Databas tabell
	$TableName = "chatten";

	$Comments = "";
	$InloggTime = $_GET['InloggTime'];
	$Name = $_GET['Name'];

//Hämtar info från databasen
	$Query = "SELECT * from $TableName where (incomeTime > $InloggTime )";
	$Result = mysql_db_query ($DBName, $Query, $Link);
	while($Row = mysql_fetch_array($Result))
	{
		$Time = $Row['incomeTime'];
		$Hour = date("H", $Time);
		$Min = date("i", $Time);
		$Sec = date("s", $Time);
		//Återannvänder Time variabeln
		$Time = $Hour.":".$Min.":".$Sec;
		$FromName = $Row['fromName'];
		$Comment = $Row['comment'];
		$Comments = "<i>$Time</i> -> <b>$FromName:</b> $Comment <br> ".$Comments;
	}
//Stänger databas länken
	mysql_close($Link);


//Det är den här tagen som gör att det uppdateras var femte sekund
	print ("<HEAD><meta http-equiv=\"refresh\" content=\"4;URL=chat.php?Action=showMessage&Name=$Name&InloggTime=$InloggTime\"></HEAD>\n");
	print ("$Comments \n");
	print ("<a name=\"Bottom\"></a> \n");
}

//----------------------------------------typeComment()--------------------------------------
function typeComment()
{
	$Name = $_GET['Name'];
	//$Comment = $_GET['Comment'];
	$InloggTime = $_GET['InloggTime'];
?>
	<BODY onLoad="document.input.Comment.focus()">
	<BR>
	<form action="chat.php" method="get" name="input">
		Skriv: <input type="text" name="Comment">
		<input type="submit" value="Skicka">
		<input type="hidden" name="InloggTime" value=<?php print("$InloggTime"); ?> >
		<input type="hidden" name="Name" value="<?php print("$Name"); ?>">
		<input type="hidden" name="Action" value="putInMessage">
	</form>
	</BODY>
<?php
}

//----------------------------------------putInComment()--------------------------------------
function putInComment()
{
	require("databas/variabler.php");
//Databas tabell
	$TableName = "chatten";

	$Name = $_GET['Name'];
	$Comment = $_GET['Comment'];

//Skickar in $Comment till databasen
	if($Comment != "")
	{
		$Time = date("U");
		$Query = "Insert into $TableName values ('$Time','$Comment','$Name')";
		mysql_db_query( $DBName, $Query, $Link);
	}
//Stänger databas länken
	mysql_close($Link);
}
?>