<?php

	require "conn.php";
	$room_no = $_POST["room_no"];
	//$username = 'efyjg5';
	$mysqli_query = "select ac_status,lights_status from facilities_status where room_no = '$room_no' order by created_on desc limit 1";

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