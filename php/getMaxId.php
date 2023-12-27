<?php

	require "conn.php";
	$cuisine = $_POST["cuisine"];
	//$cuisine = "chinese";
	$mysqli_query = "SELECT order_id FROM order_list where cuisine like '$cuisine' and not status like 'done' order by order_id DESC limit 1";

	$result = mysqli_query($conn,$mysqli_query);

	if(mysqli_num_rows($result)>0){
	//print("Login success");
	
		$row = mysqli_fetch_all($result); 
		Print("[");
		Print('["'.$row[0][0].'"]');
		Print("]");
		

	}
	else{
		print("EMPTY"); 
	}

?>