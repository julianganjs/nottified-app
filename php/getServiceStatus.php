<?php

	require "conn.php";
	$location = $_POST["location"];
	//$username = 'efyjg5';
	$mysqli_query = "SELECT a.crowd_size,b.open_time,b.closing_time FROM service_status a INNER JOIN service_time b ON a.location=b.location where a.location = '$location' order by a.created_on desc limit 1;";

	$result = mysqli_query($conn,$mysqli_query);

	if(mysqli_num_rows($result)>0){
	//print("Login success");
	
		$row = mysqli_fetch_all($result); 
		Print("[");
		Print('["'.$row[0][0].'","'.$row[0][1].'","'.$row[0][2].'"]');
		Print("]");
		

	}
	else{
		print("NA"); 
	}

?>