<?php

	require "conn.php";
	$user_name = $_POST["user_name"];
	$dish_name = $_POST["dish_name"];
	$quantity = $_POST["quantity"];
	$total_price = $_POST["total_price"];
	$cuisine = $_POST["cuisine"];
	$firstname = $_POST["firstname"];
	$lastname = $_POST["lastname"];
	
	//$user_name = "ha";
	//$dish_name = "ha";
	//$quantity = "ha";
	//$total_price = "ha";
	//$cuisine = "ha";
	
	$mysqli_query = "INSERT INTO order_list (user_name, dish_name, quantity, total_price, cuisine, status, first_name, last_name) 
	VALUES ('$user_name','$dish_name','$quantity','$total_price','$cuisine','received','$firstname','$lastname')";

	$result = mysqli_query($conn,$mysqli_query);

	if($result){
		print("Order Received.");
	}
	else{
		print("FAILED"); 
	}

?>