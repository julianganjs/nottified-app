<?php

	require "conn.php";
	$cuisine = $_POST["cuisine"];
	//$cuisine = "chinese";
	$mysqli_query = "SELECT * FROM order_list where cuisine like '$cuisine' and not status like 'done' order by order_id ASC";

	$result = mysqli_query($conn,$mysqli_query);

	if(mysqli_num_rows($result)>0){
	//print("Login success");
	
		$row = mysqli_fetch_all($result); 
		Print("[");
		for ($x = 0; $x < sizeof($row); $x++){
			Print('["'.$row[$x][0].'","'.$row[$x][1].'","'.$row[$x][2].'","'.$row[$x][3].'","'.$row[$x][4].'","'.$row[$x][5].'","'.$row[$x][6].'","'.$row[$x][7].'","'.$row[$x][8].'","'.$row[$x][9].'"]');
			if ($x!=sizeof($row)-1){
				Print(",");
			}
		}
		Print("]");
		

	}
	else{
		print("EMPTY"); 
	}

?>