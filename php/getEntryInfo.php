<?php

	require "conn.php";
	$username = $_POST["username"];
	//$username = 'efyjg5';
	$mysqli_query = "SELECT a.entry_datetime, a.exit_datetime FROM vehicle_entry_session a INNER JOIN vehicle_entry_permit b ON a.license_plate_number=b.license_plate_number where b.user_name = '$username' and a.entry_datetime > b.start_date and b.expiry_date > now() - interval 1 day order by a.entry_datetime desc limit 1;";

	$result = mysqli_query($conn,$mysqli_query);

	if(mysqli_num_rows($result)>0){
	//print("Login success");
	
		$row = mysqli_fetch_all($result); 
		Print("[");
		Print('["'.$row[0][0].'","'.$row[0][1].'"]');
		Print("]");
		

	}
	else{
		print("NA"); 
	}

?>