<?php

	require "conn.php";
	$cuisine = $_POST["cuisine"];
	//$cuisine = "arabic";
	$mysqli_query = "SELECT * FROM cuisine_menu where cuisine like '$cuisine'";

	$result = mysqli_query($conn,$mysqli_query);

	if(mysqli_num_rows($result)>0){
	//print("Login success");
	
		$row = mysqli_fetch_all($result); 
		Print("[");
		for ($x = 0; $x < sizeof($row); $x++){
			Print('["'.$row[$x][0].'","'.$row[$x][1].'","'.$row[$x][2].'","'.$row[$x][3].'"]');
			if ($x!=sizeof($row)-1){
				Print(",");
			}
		}
		Print("]");
		

	}
	else{
		print("FAILED"); 
	}

?>