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
	<img src="bilder/bg.jpg" border=0 width=8><a href="skamt.php" 	    onMouseOver="visa_text('skamt')"  onMouseOut="bort_text('skamt')" >Sk�mt</a>
	<img src="bilder/bg.jpg" border=0 width=8><a href="humorlankar.php" onMouseOver="visa_text('humorlankar')" onMouseOut="bort_text('humorlankar')" >L�nkar</a>
   </div>
   <div class="menyPopup" ID=menypopup4 style="left: 120px;" onMouseOver="visa_text('menypopup4')" onMouseOut="bort_text('menypopup4')">
	<a href="prog.php"  onMouseOver="visa_text('c')"    onMouseOut="bort_text('c')"  >C++</a>
	<img src="bilder/bg.jpg" border=0 width=8><a href="prog.php" onMouseOver="visa_text('java')" onMouseOut="bort_text('java')"  >Java</a>
	<img src="bilder/bg.jpg" border=0 width=8><a href="prog.php" onMouseOver="visa_text('basic')"onMouseOut="bort_text('basic')"  >Basic</a>
	<img src="bilder/bg.jpg" border=0 width=8><a href="prog.php" onMouseOver="visa_text('phpl')" onMouseOut="bort_text('phpl')"  >HTML</a>
	<img src="bilder/bg.jpg" border=0 width=8><a href="prog.php" onMouseOver="visa_text('casio')"onMouseOut="bort_text('casio')"  >Casio</a>
	<img src="bilder/bg.jpg" border=0 width=8><a href="chatstart.php" onMouseOver="visa_text('chat')"onMouseOut="bort_text('chat')"  >Chat</a>
   </div>

<!--L�nkarna-->
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
	<? //<div class="menyBoxText" ID=> F�r muspekaren �ver l�nkarna f�r mer info.</div>
	?>
	<!--Olika texter till menyn-->
	<div class="menyBoxText" ID=main> F�rsta sidan.</div>
	<div class="menyBoxText" ID=omMig> H�r hittar du lite intresanta saker om mig.</div>
	<div class="menyBoxText" ID=nv>  NV2B:s oficiella hemmsida. Vi g�r Naturvetenskaps linjen.</div>
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
</div>
