<HTML>
<HEAD><TITLE>|| Adde @ medlem.spray.se ||</TITLE></HEAD>
<LINK REL=stylesheet HREF="Stil.css" TYPE="text/css">
<BODY>
<TABLE WIDTH=640px border=0 cellspacing="5">
<TR><TD VALIGN="TOP">
<center>
<?php
//Bilderna ör skrivna med fonten Monotype Corsiva(Västerländskt)  är gif med genomskinlig bakgrund
	print("<img src=\"bilder/overs$Overs.gif\" width=246 height=45>");
?>
</center>
</TD></TR>
<TR><TD VALIGN="TOP">
<div class="ramen" style="padding: 4px; left: 10px; text-align: center;">
<font size=2>
<?php
	require("citaten.php");
	$DatumNr = date("j");
	print("<i>Citat: </i><b>\"$Citatet[$DatumNr]\"</b>\n<br>\n<i>Av:</i> <b><i>$CitatAv[$DatumNr]</i></b>");
?>
</font>
</div>
</TD></TR>
<TR><TD VALIGN="TOP">
<?php
	require("menu.php");
?>
</TD></TR>
<TR><TD VALIGN="TOP">
<?php
//Påbörjar ramen för text. Avslutas i bottom.php
	print("<!--Texten-->\n<div class=\"maintext\"><div class=\"ramen\">");
?>
