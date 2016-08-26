<?php
	$File = "citat.php";
	$Overs = strtok($File, ".");
  	require("top.php");
?>
<div class="rubrik"><u>Mycket att lära av andra!</u></div>
 Ja det stämmer mycket bra. Av citat kan man lära sig mycket saker om livet. De flesta citat är ren
 fakta medans andra är lite klurigare visdoms ord. Ibland kan det behövas lite tanke kapacitet för att klura ur
 vad som menas. <br>
 Jag har här försökt samla in allt jag hört. Om du har något bra citat kan du kontakta mig så
 kanske jag skriver upp det på listan.
</div><br>
<font size=2>
<?php
	//Citat filen includeras i top.php som includeras i den här
	for($i = 0; $i < $AntCitat; $i++)
		print("<div class=\"ramen\"><i>Citat: </i><b>\"$Citatet[$i]\"</b><br><div style=\"text-align: right\"><i>Av:</i> <b><i>$CitatAv[$i]</i></b></div></div><br>\n");
?>

</font>
<?php
  	require("bottom.php");
?>
