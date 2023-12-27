<?php

	require "conn.php";

	$username = $_POST["username"];
	$orderid = $_POST["orderid"];
	$cuisine = $_POST["cuisine"];
	$mysqli_query = "update order_list set status = 'ready_n' where user_name like '$username' and order_id like '$orderid' and cuisine like '$cuisine'";

	$result = mysqli_query($conn,$mysqli_query);
	
	Print($result);
?>