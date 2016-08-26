<html>
<head>
</head>
<body>
Välj ett alternativ: <br />
<?php
	
//echo print_r($_POST);	//test utskrift

if(!isset($_POST['mobileName']))
	$_POST['mobileName'] = "";
if(!isset($_POST['type_id']))
	$_POST['type_id'] = "";
	
if($_POST['type_id'] == "" && $_POST['mobileName'] == "")
{
	
}
else
{
	if($_POST['type_id'] == "")
	{
		echo '<font color="#ff0000">Välj ett alternativ</font>';
	}
	if($_POST['mobileName'] == "")
	{
		echo '<font color="#ff0000">Skriv ditt mobil namn</font>';
	}
	if($_POST['mobileName'] != "" && $_POST['type_id'] != "")
	{
		$id = $_POST['type_id'];
		$mobil = $_POST['mobileName'];
		$dest = $_POST['dest'];
		$curlGet = 'id='.urlencode($id).'&additional_data='.urlencode($mobil).'&destination='.urlencode($dest); 
		$ch = curl_init();

		curl_setopt($ch, CURLOPT_URL,'http://localhost/get_data.php?'.$curlGet);
		curl_setopt($ch, CURLOPT_HEADER, 0);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1); 

		$result = curl_exec($ch);
		curl_close($ch);
		list($a, $b) = split('[ ]', $result);
		if($b == "200") echo 'Filen är skickad, om du inte får något testa skicka igen.';
		else echo 'Fel uppstod testa skicka igen';
	}
}
	
function getList($host, $port, $addr)
{
	// create socket
	/*$socket = fsockopen($host, $port);
	fwrite($socket, $addr) or die("Could not write to socket\n");
	//read the response.
	$xmlstring = "";
	while(!feof($socket))
	{
		$xmlstring .= fread($socket, 1024);
	}
	$xmlstring = substr($xmlstring, strpos($xmlstring, "\r\n\r\n") + 4);
	$xml = new SimpleXMLElement($xmlstring);
	fclose($socket);*/
  $ch = curl_init();

	curl_setopt($ch, CURLOPT_URL,'http://localhost/'.$addr);
	curl_setopt($ch, CURLOPT_HEADER, 0);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1); 

	$xml = curl_exec($ch);
	curl_close($ch);
  
	return $xml;
}

function getDestination($xml)
{
	$dest = "";
	foreach($xml->children() as $child)
  {
	foreach($child->attributes() as $a => $b)
	{
		if($a == "id")
		{
			$dest = $b;
		}else if($a == "type")
		{
			if($b == "bt")
			{
				return $dest;
			}
		}
	}
  }
}

$host = "localhost";
$port = 80;
/*$dataList = "GET /get_data_list.php?cc_type=bt HTTP/1.1\r\n".
			"Host: localhost\r\n".
			"Connection: close\r\n\r\n";
$ccList = "GET /get_channels.php HTTP/1.1\r\n".
			"Host: localhost\r\n".
			"Connection: close\r\n\r\n";
*/
$dataList = "get_data_list.php?cc_type=bt";
$ccList = "get_channels.php";

//Get destination address.
$xml = getList($host, $port, $ccList);

//cc parsing  
$_POST['dest'] = getDestination($xml);

//Data list parsing
$xml = getList($host, $port, $dataList);

//parsning
?>
<form action='' method='post'>
<?php

foreach($xml->children() as $child)
  {
	foreach($child->attributes() as $a => $b)
	{
	if($a == "id"){
	?>
		<label><input type='radio' name='type_id' value="<?php echo $b; ?>" <?php if($b == $_POST['type_id']) echo 'checked="checked"'; ?>/>		
	
	<?php
	}
	else if($a == "category")
	{
		echo $b.'</label><br />';
	}
	//echo $a,'="',$b,"\"</br>";
	}

  }
?>

<br />Fyll i ditt mobilnamn:<br /><input type='text' name='mobileName' value="<?php echo $_POST['mobileName']; ?>" /><br /><br />
<input type='submit'/>
</form>
</body>
</html>