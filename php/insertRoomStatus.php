<?php

	require "conn.php";
	$roomno = $_GET["roomno"];
	$lightstat = $_GET["lightstat"];
	$acstat = $_GET["acstat"];
	$mysqli_query = "INSERT INTO facilities_status (room_no, lights_status, ac_status) VALUES ('$roomno','$lightstat','$acstat')";

	$result = mysqli_query($conn,$mysqli_query);
	
	Print("UPDATED");
?>