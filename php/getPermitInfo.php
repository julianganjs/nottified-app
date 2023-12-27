<?php

	require "conn.php";
	$username = $_POST["username"];
	//$username = 'efyjg5';
	$mysqli_query = "select permit_type, start_date, expiry_date, license_plate_number, vehicle_info from vehicle_entry_permit where user_name = '$username' order by created_at desc limit 1";

	$result = mysqli_query($conn,$mysqli_query);

	if(mysqli_num_rows($result)>0){
	//print("Login success");
	
		$row = mysqli_fetch_all($result); 
		Print("[");
		Print('["'.$row[0][0].'","'.$row[0][1].'","'.$row[0][2].'","'.$row[0][3].'","'.$row[0][4].'"]');
		Print("]");
		

	}
	else{
		print("NA"); 
	}

?>