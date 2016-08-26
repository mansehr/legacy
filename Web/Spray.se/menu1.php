<SCRIPT LANGUAGE="JavaScript">
<!--
	function visa_text(id)
		{ document.all[id].style.visibility="visible"; }

	function bort_text(id)
		{ document.all[id].style.visibility="hidden"; }

	function bort_alla()
		{ document.all.style.visibility="hidden"; }

	function Datum()
		{ document.write(document.lastModified); }
//-->
</SCRIPT>

<!--Menyn-->
<div class="menyn">
  <!--Meny Popup-->
   <div class="menyPopup" ID=menypopup2 style="left: 22px;" onMouseOver="visa_text('menypopup2')" onMouseOut="bort_text('menypopup2')">
	<a href="bandy.php" onMouseOver="visa_text('bandy')" onMouseOut="bort_text('bandy')" >Bandy</a>
	<img src="bilder/bg.jpg" border=0 width=8><a href="mittliv.php"  onMouseOver="visa_text('mittliv')" onMouseOut="bort_text('mittliv')" >Mitt Liv</a>
	<img src="bilder/bg.jpg" border=0 width=8><a href="minatankar.php" onMouseOver="visa_text('minatankar')" onMouseOut="bort_text('minatankar')" >Mina Tankar</a>
   </div>
   <div class="menyPopup" ID=menypopup3 style="left: 86px;" onMouseOver="visa_text('menypopup3')" onMouseOut="bort_text('menypopup3')">
	<a href="vitsar.php" 	onMouseOver="visa_text('vitsar')" onMouseOut="bort_text('vitsar')" >Vitsar</a>
	<img src="bilder/bg.jpg" border=0 width=8><a href="skamtbilder.php" onMouseOver="visa_text('bilder')" onMouseOut="bort_text('bilder')" >Bilder</a>
	<img src="bilder/bg.jpg" border=0 width=8><a href="skamt.php" 	    onMouseOver="visa_text('skamt')"  onMouseOut="bort_text('skamt')" >Skämt</a>
	<img src="bilder/bg.jpg" border=0 width=8><a href="humorlankar.php" onMouseOver="visa_text('humorlankar')" onMouseOut="bort_text('humorlankar')" >Länkar</a>
   </div>
   <div class="menyPopup" ID=menypopup4 style="left: 120px;" onMouseOver="visa_text('menypopup4')" onMouseOut="bort_text('menypopup4')">
	<a href="prog.php"  onMouseOver="visa_text('c')"    onMouseOut="bort_text('c')"  >C++</a>
	<img src="bilder/bg.jpg" border=0 width=8><a href="prog.php" onMouseOver="visa_text('java')" onMouseOut="bort_text('java')"  >Java</a>
	<img src="bilder/bg.jpg" border=0 width=8><a href="prog.php" onMouseOver="visa_text('basic')"onMouseOut="bort_text('basic')"  >Basic</a>
	<img src="bilder/bg.jpg" border=0 width=8><a href="prog.php" onMouseOver="visa_text('phpl')" onMouseOut="bort_text('phpl')"  >HTML</a>
	<img src="bilder/bg.jpg" border=0 width=8><a href="prog.php" onMouseOver="visa_text('casio')"onMouseOut="bort_text('casio')"  >Casio</a>
	<img src="bilder/bg.jpg" border=0 width=8><a href="chatstart.php" onMouseOver="visa_text('chat')"onMouseOut="bort_text('chat')"  >Chat</a>
   </div>

<!--Länkarna-->
<div class="menyLank" style="width: 48px;left: 0px;"> <a href="start.php"  onMouseOver="bort_text('menypopup2');bort_text('menypopup3');bort_text('menypopup4');visa_text('main');"  onMouseOut="bort_text('main')"  >
  <!--<img src="bilder/hem.gif" border=0>-->
  Hem</a></div>
<div class="menyLank" style="width: 60px;left: 52px;"> <a href="OmMig.php" onMouseOver="bort_text('menypopup3');bort_text('menypopup4');visa_text('omMig');visa_text('menypopup2')" onMouseOut="bort_text('omMig')" >
  <!--<img src="bilder/head.gif" border=0>-->
  Om mig</a></div>
<div class="menyLank" style="width: 60px;left: 116px;"><a href="humor.php" onMouseOver="bort_text('menypopup2');bort_text('menypopup4');visa_text('humor');visa_text('menypopup3')" onMouseOut="bort_text('humor')">
  <!--<img src="bilder/humor.gif" border=0>-->
  Humor</a></div>
<div class="menyLank" style="width: 98px;left: 180px;"><a href="prog.php"  onMouseOver="bort_text('menypopup2');bort_text('menypopup3');visa_text('prog');visa_text('menypopup4')"  onMouseOut="bort_text('prog')"  >
  <!--<img src="bilder/dator.gif" border=0>-->
  Programmering</a></div>
<div class="menyLank" style="width: 50px;left: 282px;"><a href="citat.php"  onMouseOver="bort_text('menypopup2');bort_text('menypopup3');bort_text('menypopup4');visa_text('citat');"  onMouseOut="bort_text('citat')"  >
  <!--<img src="bilder/dator.gif" border=0>-->
  Citat</a></div>
<div class="menyLank" style="width: 54px;left: 336px;"><a href="nv/index.php" target="_top"  onMouseOver="bort_text('menypopup2');bort_text('menypopup3');bort_text('menypopup4');visa_text('nv')"  onMouseOut="bort_text('nv')"  >
  <!--<img src="bilder/nv1b.gif" border=0>-->
  NV</a></div>

  <!--Info rutan-->
  <div class="menyBoxtextRam">
	<? //<div class="menyBoxText" ID=> För muspekaren över länkarna för mer info.</div>
	?>
	<!--Olika texter till menyn-->
	<div class="menyBoxText" ID=main> Första sidan.</div>
	<div class="menyBoxText" ID=omMig> Här hittar du lite intresanta saker om mig.</div>
	<div class="menyBoxText" ID=nv>  NV2B:s oficiella hemmsida. Vi går Naturvetenskaps linjen.</div>
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
</div>
