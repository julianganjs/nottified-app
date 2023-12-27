<?php

	require "conn.php";
	$username = $_POST["username"];
	//$username = "efyjg5";
	$mysqli_query = "SELECT id FROM vehicle_entry_permit where user_name like '$username' and expiry_date > now() - interval 1 day";

	$result = mysqli_query($conn,$mysqli_query);

	if(mysqli_num_rows($result)>0){
	//print("Login success");
 
		print("EXISTS"); 
		

	}
	else{
		print("NON"); 
	}

?>