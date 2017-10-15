
<!-- #Project Raspberry IOT Temperature monitoring -->
<!-- #Server side code to read data and display to webview -->
<!-- #Create by Minh Toan -->
<!-- #Create on 15 October, 2017 -->

<?php
$username="root";
$password="toanpi";
$database="templog";
$server="127.0.0.1";
$mysqli = new mysqli($server,$username,$password, $database);
$query="SELECT * FROM `temprecord` ORDER BY `Date` DESC, `Time` DESC;"; 
$result=$mysqli->query($query);
?>

<html>
	<head>
  	 	<style type="text/css">
  	 		#tb{
  	 			border-style: solid;
  	 			border-color: teal;
  	 			border-width: 1;
  	 			color: teal
  	 		}
  	 		h2{
  	 			color: teal
  	 		}
  	 		td{
  	 			border-style: solid;
  	 			border-width: 1;
  	 			border-color: teal
  	 		}
  	 		td:hover{
  	 			color: white;
  	 			background-color: teal
  	 		}
  	 	</style>
     	<title>Sensor Data</title>
   	</head>
<body>
   	<h2>Temperature readings</h2>

   	<table id="tb">
		<tr>
			<td>&nbsp;Date&nbsp;</td>
			<td>&nbsp;Time&nbsp;</td>
			<td>&nbsp;Temperature&nbsp;</td>
		</tr>
      	<?php 
		  	if($result!==FALSE)
		  	{
		    	while($row = $result->fetch_assoc()) 
		    	{
		        	printf("<tr><td> &nbsp;%s </td><td> &nbsp;%s&nbsp; 
		        		</td><td> &nbsp;%s&nbsp; </td></tr>", 
		        		$row["Date"], $row["Time"], $row["Temperature"]);
		    	}
		    	$mysqli->free_result($result);
		    	$mysqli->close();
		  	}
      	?>
   	</table>
</body>
</html>
