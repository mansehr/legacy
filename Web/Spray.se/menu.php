<TABLE WIDTH="100%" border="0" cellspacing="0">
  <SCRIPT LANGUAGE="JavaScript">
<!--
	function visa_text(id)
	{
		document.all['tiden'].style.visibility="hidden";
		document.all[id].style.visibility="visible";
	}

	function bort_text(id)
	{
		document.all[id].style.visibility="hidden";
		document.all['tiden'].style.visibility="visible";
	}

	function bort_alla()
		{ document.all.style.visibility="hidden"; }

	function Datum()
		{ document.write(document.lastModified); }

	function updateTime()
	{
		var nu = new Date();
		var timme = nu.getHours();
		var minut = nu.getMinutes();
		var sekund = nu.getSeconds();
		var nyTid = "";
		nyTid += ((timme < 10)? "0":"") + timme;
		nyTid += ((minut < 10)? ":0" : ":") + minut;
		nyTid += ((sekund < 10)? ":0" : ":") + sekund;

		document.all['tiden'].value = nyTid;
		setTimeout("updateTime()", 1000);
	}
//-->
</SCRIPT>
  <TR>
    <TD width="8%" height="23" VALIGN="TOP">
      <div class="menyLank"> <a href="start.php"  onMouseOver="bort_text('menypopup2');bort_text('menypopup3');bort_text('menypopup4');visa_text('main');"  onMouseOut="bort_text('main')"  >
        Hem </a> </div>
    </TD>
    <TD width="10%" VALIGN="top">
      <div class="menyLank"><a href="OmMig.php" onMouseOver="bort_text('menypopup3');bort_text('menypopup4');visa_text('omMig');visa_text('menypopup2')" onMouseOut="bort_text('omMig')" >
        Om mig </a> </div>
    </TD>
    <TD width="10%" VALIGN="top">
      <div class="menyLank"><a href="humor.php" onMouseOver="bort_text('menypopup2');bort_text('menypopup4');visa_text('humor');visa_text('menypopup3')" onMouseOut="bort_text('humor')">
        Humor </a></div>
    </TD>
    <TD width="16%" VALIGN="top">
      <div class="menyLank"><a href="prog.php"  onMouseOver="bort_text('menypopup2');bort_text('menypopup3');visa_text('prog');visa_text('menypopup4')"  onMouseOut="bort_text('prog')"  >
        Programmering </a> </div>
    </TD>
    <TD width="8%" VALIGN="top">
      <div class="menyLank"><a href="citat.php"  onMouseOver="bort_text('menypopup2');bort_text('menypopup3');bort_text('menypopup4');visa_text('citat');"  onMouseOut="bort_text('citat')"  >
        Citat </a></div>
    </TD>
    <TD width="8%" VALIGN="top">
      <div class="menyLank"><a href="nv/index.php" target="_top"  onMouseOver="bort_text('menypopup2');bort_text('menypopup3');bort_text('menypopup4');visa_text('nv')"  onMouseOut="bort_text('nv')"  >
        NV </a></div>
    </TD>
    <td width="40%" rowspan="2" valign="top">
		<div class="menyBoxTextRam">
			<!-- Klocka -->
			<input type="text" onLoad="" value="15 : 10 : 20" class="menyBoxText" ID=tiden style="visibility: visible; background-color: transparent; border: none; font-size: 8pt;">

			<!--Olika texter till menyn-->
			<div class="menyBoxText" ID=main> F�rsta sidan.</div>
			<div class="menyBoxText" ID=omMig> H�r hittar du lite intresanta saker om mig.</div>
			<div class="menyBoxText" ID=nv> NV2B:s oficiella hemmsida. Vi g�r Naturvetenskaps linjen.</div>
			<div class="menyBoxText" ID=humor> Genom �ren har jag lyckats sammla p� mig en hel del roligt. Minst ett skratt garanteras.</div>
			<div class="menyBoxText" ID=prog> Jag programmerar i de flesta olika spr�k.</div>
			<div class="menyBoxText" ID=citat> Jag �r v�ldigt farsinerad av alla visa saker som kan komma ur en m�nnisko-mun.</div>
			<div class="menyBoxText" ID=bandy> Jag spelar bandy i GT/76. Riktig bandy, p� is.!!!</div>
			<div class="menyBoxText" ID=minatankar> Jag �r en person som g�r och funderar lite d� och d�. H�r har jag sammlat mina tankar.</div>
			<div class="menyBoxText" ID=mittliv> H�r kan du l�sa om mitt liv. Det som varit och det som kommer att bli.</div>
			<div class="menyBoxText" ID=vitsar> H�r �r lite roliga vitsar som f�rhoppningsvis uppskattas av alla.</div>
			<div class="menyBoxText" ID=bilder> De flesta bilder h�r har min pappa f�tt fr�n n�n polare p� jobbet. V�ldigt roliga.</div>
			<div class="menyBoxText" ID=skamt> H�r �r en av de roligaste delarna p� sidan...Om du �r p� d�ligt hum�r kolla in h�r.</div>
			<div class="menyBoxText" ID=humorlankar> H�r sammlar jag l�nkar om humor. Ni f�r g�rna komma med egna f�rslag.</div>
			<div class="menyBoxText" ID=c> Lite C++ programmering, Program jag gjort och skolor jag rekomenderar eller skrivit sj�lv.</div>
			<div class="menyBoxText" ID=java> Java! Har inte gjort n�got avancerat d�r men vet en hel del.</div>
			<div class="menyBoxText" ID=basic> Det simplaste programeringspr�ket som finns tror jag.</div>
			<div class="menyBoxText" ID=phpl> Ja vad trodde du hur skulle jag annars gjort det h�r.</div>
			<div class="menyBoxText" ID=casio> Jag har en CASIO minir�knare som jag brukar spela p� ibland.</div>
			<div class="menyBoxText" ID=chat> Jag har programmerat en chat i php. Den fungerar r�tt bra faktiskt.Klicka h�r och kolla hur m�nga som �r inne.</div>
		</div>
  		<script language="JavaScript">updateTime();</script>
  </td>
  </TR>
  <TR>
    <TD colspan="6" VALIGN="top" HEIGHT="25">
    <div style="position: absolute">
      <div class="menyPopup" ID=menypopup2 style="left: 30px; top: 0; width: 170px;" onMouseOver="visa_text('menypopup2')" onMouseOut="bort_text('menypopup2')">
        <div class="menyPopupLank" style="width: 40px; left: 0px;"><a href="bandy.php" onMouseOver="visa_text('bandy')" onMouseOut="bort_text('bandy')" >Bandy</a></div>
        <div class="menyPopupLank" style="width: 53px; left: 42px;"><a href="mittliv.php"  onMouseOver="visa_text('mittliv')" onMouseOut="bort_text('mittliv')" >Mitt Liv</a></div>
        <div class="menyPopupLank" style="width: 72px; left: 97px;"><a href="minatankar.php" onMouseOver="visa_text('minatankar')" onMouseOut="bort_text('minatankar')" >Mina Tankar</a></div>
       </div>
      <div class="menyPopup" ID=menypopup3 style="left: 80px; top: -22px; width: 160px;" onMouseOver="visa_text('menypopup3')" onMouseOut="bort_text('menypopup3')">
        <div class="menyPopupLank" style="width: 40px; left: 0px;"><a href="vitsar.php" 	onMouseOver="visa_text('vitsar')" onMouseOut="bort_text('vitsar')" >Vitsar</a></div>
        <div class="menyPopupLank" style="width: 38px; left: 42px;"><a href="skamtbilder.php" onMouseOver="visa_text('bilder')" onMouseOut="bort_text('bilder')" >Bilder</a></div>
        <div class="menyPopupLank" style="width: 38px; left: 82px;"><a href="skamt.php" 	    onMouseOver="visa_text('skamt')"  onMouseOut="bort_text('skamt')" >Sk�mt</a></div>
        <div class="menyPopupLank" style="width: 40px; left: 122px;"><a href="humorlankar.php" onMouseOver="visa_text('humorlankar')" onMouseOut="bort_text('humorlankar')" >L�nkar</a></div>
      </div>
      <div class="menyPopup" id=menypopup4 style=" left: 120px; top: -44px; width: 210px;" onMouseOver="visa_text('menypopup4')" onMouseOut="bort_text('menypopup4')">
        <div class="menyPopupLank" style="width: 35px; left: 0px;"><a href="prog.php"  onMouseOver="visa_text('c')"    onMouseOut="bort_text('c')"  >C++</a></div>
        <div class="menyPopupLank" style="width: 35px; left: 37px;"><a href="prog.php" onMouseOver="visa_text('java')" onMouseOut="bort_text('java')"  >Java</a></div>
        <div class="menyPopupLank" style="width: 37px; left: 74px;"><a href="prog.php" onMouseOver="visa_text('basic')"onMouseOut="bort_text('basic')"  >Basic</a></div>
        <div class="menyPopupLank" style="width: 45px; left: 113px;"><a href="prog.php" onMouseOver="visa_text('phpl')" onMouseOut="bort_text('phpl')"  >HTML</a></div>
        <div class="menyPopupLank" style="width: 40px; left: 160px;"><a href="prog.php" onMouseOver="visa_text('casio')"onMouseOut="bort_text('casio')"  >Casio</a></div>
        <div class="menyPopupLank" style="width: 33px; left: 202px;"><a href="chat.php?Action=loggIn" onMouseOver="visa_text('chat')"onMouseOut="bort_text('chat')"  >Chat</a></div>
      </div>
     </div>
    </TD>
  </TR>
</TABLE>