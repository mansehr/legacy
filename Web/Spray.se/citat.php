<?php
	$File = "citat.php";
	$Overs = strtok($File, ".");
  	require("top.php");
?>
<div class="rubrik"><u>Mycket att l�ra av andra!</u></div>
 Ja det st�mmer mycket bra. Av citat kan man l�ra sig mycket saker om livet. De flesta citat �r ren
 fakta medans andra �r lite klurigare visdoms ord. Ibland kan det beh�vas lite tanke kapacitet f�r att klura ur
 vad som menas. <br>
 Jag har h�r f�rs�kt samla in allt jag h�rt. Om du har n�got bra citat kan du kontakta mig s�
 kanske jag skriver upp det p� listan.
</div><br>
<font size=2>
<?php
	//Citat filen includeras i top.php som includeras i den h�r
	for($i = 0; $i < $AntCitat; $i++)
		print("<div class=\"ramen\"><i>Citat: </i><b>\"$Citatet[$i]\"</b><br><div style=\"text-align: right\"><i>Av:</i> <b><i>$CitatAv[$i]</i></b></div></div><br>\n");
?>

</font>
<?php
  	require("bottom.php");
?>
