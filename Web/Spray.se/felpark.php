<?php
	$File = "felpark.php";
	$Overs = "humor";
  	require("top.php");
?>

<table width="580" border="1" cellpadding="0" cellspacing="10">
  <tr>
    <td bgcolor="#FFFFFF" width="100" height="0"></td>
    <td bgcolor="#FFFFFF" width="100" height="0"></td>
    <td bgcolor="#FFFFFF" width="100" height="0"></td>
    <td bgcolor="#FFFFFF" width="100" height="0"></td>
    <td bgcolor="#FFFFFF" width="100" height="0"></td>
  </tr>
  <tr>
    <td colspan="5" height="36" valign="top">
      <div align="center">
        <p><b><font size="6"><u>Felparkerat värre</u></font></b></p>
        <p align="left"><font size="3">Det &auml;r inte rekomenderat att parkera
          s&aring;h&auml;r...Jag undrar var de fick sina körkort nånstans...</font></p>
      </div>
    </td>
  </tr>
  <?php
  	$pic = 1;
	$picAntal = 17;
	$Height = 80;
	$Width = 100;
  	for($i = 0; $i < 6; $i++)
	{
		print("<tr>\n");
		for($j = 0; $j < 5 && $pic <= $picAntal; $j++, $pic++)
		{	print("<td valign=\"top\" height=\"$Height\"><a href=\"Felpark\parkerat$pic.jpg\" target=\"_blank\"><img src=\"Felpark\parkerat$pic.jpg\" width=\"$Width\" height=\"$Height\" style=\"border: thin black solid;\"></a></td>\n");	}
		print("</tr>\n");
	}

  	require("bottom.php");
?>