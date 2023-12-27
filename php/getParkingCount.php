<?php

	require "conn.php";
	$type = $_POST["type"];
	//$type = "staff";
	$mysqli_query = "SELECT SUM(IF(status = '1' AND location = 'j_block' AND type = '$type', 1, 0)) AS j_no, SUM(IF(status = '1' AND location = 'b_block' AND type = '$type', 1, 0)) AS b_no, SUM(IF(status = '1' AND location = 'cafeteria' AND type = '$type', 1, 0)) AS cafe_no FROM parking_spot_status";

	$result = mysqli_query($conn,$mysqli_query);

	if(mysqli_num_rows($result)>0){
	//print("Login success");
	
		$row = mysqli_fetch_all($result); 
		Print("[");
		Print('["'.$row[0][0].'","'.$row[0][1].'","'.$row[0][2].'"]');
		Print("]");
		

	}
	else{
		print("EMPTY"); 
	}

?>