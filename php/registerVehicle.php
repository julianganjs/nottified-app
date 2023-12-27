<?php

	require "conn.php";
	$plate_no = $_POST["plate_no"];
	$username = $_POST["username"];
	$type = $_POST["type"];
	$entry_date = $_POST["entry_date"];
	$expiry_date = $_POST["expiry_date"];
	$info = $_POST["info"];

	
	$mysqli_query = "INSERT INTO vehicle_entry_permit (license_plate_number, user_name, permit_type, start_date, expiry_date, vehicle_info) 
	VALUES ('$plate_no','$username','$type','$entry_date','$expiry_date','$info')";

	$result = mysqli_query($conn,$mysqli_query);

	if($result){
		print("REGISTERED");
	}
	else{
		print("FAILED"); 
	}

?>