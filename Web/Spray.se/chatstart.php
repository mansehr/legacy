<?php
	$File = "chatstart.php";
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
?>