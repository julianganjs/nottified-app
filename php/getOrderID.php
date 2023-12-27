<?php

	require "conn.php";
	$username = $_POST["username"];
	$mysqli_query = "SELECT order_id FROM order_list where user_name like '$username' order by time_received DESC limit 1";

	$result = mysqli_query($conn,$mysqli_query);

	if(mysqli_num_rows($result)>0){
	//print("Login success");
	
		$row = mysqli_fetch_all($result); 
		Print("[");
		Print('["'.$row[0][0].'"]');
		Print("]");
		

	}
	else{
		print("FAILED"); 
	}

?>