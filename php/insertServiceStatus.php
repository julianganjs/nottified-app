<?php

	require "conn.php";
	$location = $_GET["location"];
	$crowdno = $_GET["crowdno"];
	$mysqli_query = "INSERT INTO service_status (location, crowd_size) VALUES ('$location','$crowdno')";

	$result = mysqli_query($conn,$mysqli_query);
	
	
	Print("UPDATED");

?>