<?php

	require "conn.php";
	$id = $_POST["dish_id"];
	//$id = "1";
	$mysqli_query = "SELECT * FROM cuisine_menu where dish_id like '$id'";

	$result = mysqli_query($conn,$mysqli_query);

	if(mysqli_num_rows($result)>0){
	//print("Login success");
	
		$row = mysqli_fetch_all($result); 
		Print("[");
		for ($x = 0; $x < sizeof($row); $x++){
			Print('["'.$row[$x][0].'","'.$row[$x][1].'","'.$row[$x][2].'","'.$row[$x][3].'","'.$row[$x][4].'"]');
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