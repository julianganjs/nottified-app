<?php

	require "conn.php";


	$orderid = $_POST["orderid"];
	$status = $_POST["status"];
	$mysqli_query = "update order_list set status = '$status' where order_id like '$orderid'";

	$result = mysqli_query($conn,$mysqli_query);
	
	Print($result);
?>