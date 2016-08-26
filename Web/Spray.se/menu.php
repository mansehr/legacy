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
			<div class="menyBoxText" ID=main> Första sidan.</div>
			<div class="menyBoxText" ID=omMig> Här hittar du lite intresanta saker om mig.</div>
			<div class="menyBoxText" ID=nv> NV2B:s oficiella hemmsida. Vi går Naturvetenskaps linjen.</div>
			<div class="menyBoxText" ID=humor> Genom åren har jag lyckats sammla på mig en hel del roligt. Minst ett skratt garanteras.</div>
			<div class="menyBoxText" ID=prog> Jag programmerar i de flesta olika språk.</div>
			<div class="menyBoxText" ID=citat> Jag är väldigt farsinerad av alla visa saker som kan komma ur en människo-mun.</div>
			<div class="menyBoxText" ID=bandy> Jag spelar bandy i GT/76. Riktig bandy, på is.!!!</div>
			<div class="menyBoxText" ID=minatankar> Jag är en person som går och funderar lite då och då. Här har jag sammlat mina tankar.</div>
			<div class="menyBoxText" ID=mittliv> Här kan du läsa om mitt liv. Det som varit och det som kommer att bli.</div>
			<div class="menyBoxText" ID=vitsar> Här är lite roliga vitsar som förhoppningsvis uppskattas av alla.</div>
			<div class="menyBoxText" ID=bilder> De flesta bilder här har min pappa fått från nån polare på jobbet. Väldigt roliga.</div>
			<div class="menyBoxText" ID=skamt> Här är en av de roligaste delarna på sidan...Om du är på dåligt humör kolla in här.</div>
			<div class="menyBoxText" ID=humorlankar> Här sammlar jag länkar om humor. Ni får gärna komma med egna förslag.</div>
			<div class="menyBoxText" ID=c> Lite C++ programmering, Program jag gjort och skolor jag rekomenderar eller skrivit själv.</div>
			<div class="menyBoxText" ID=java> Java! Har inte gjort något avancerat där men vet en hel del.</div>
			<div class="menyBoxText" ID=basic> Det simplaste programeringspråket som finns tror jag.</div>
			<div class="menyBoxText" ID=phpl> Ja vad trodde du hur skulle jag annars gjort det här.</div>
			<div class="menyBoxText" ID=casio> Jag har en CASIO miniräknare som jag brukar spela på ibland.</div>
			<div class="menyBoxText" ID=chat> Jag har programmerat en chat i php. Den fungerar rätt bra faktiskt.Klicka här och kolla hur många som är inne.</div>
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
        <div class="menyPopupLank" style="width: 38px; left: 82px;"><a href="skamt.php" 	    onMouseOver="visa_text('skamt')"  onMouseOut="bort_text('skamt')" >Skämt</a></div>
        <div class="menyPopupLank" style="width: 40px; left: 122px;"><a href="humorlankar.php" onMouseOver="visa_text('humorlankar')" onMouseOut="bort_text('humorlankar')" >Länkar</a></div>
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