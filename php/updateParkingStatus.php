<?php

	require "conn.php";

	$spot_id = $_GET["spotid"];
	$location = $_GET["location"];
	$status = $_GET["status"];
	$mysqli_query = "update parking_spot_status set status = '$status' where location like '$location' and spot_id like '$spot_id'";

	$result = mysqli_query($conn,$mysqli_query);
	
	
	Print("UPDATED");
?>