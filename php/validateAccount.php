<?php

	require "conn.php";
	$user_name = $_POST["username"];
	$password = $_POST["pass"];

	$mysqli_query = "SELECT * FROM user_accounts where username like '$user_name' and pass like '$password'";

	$result = mysqli_query($conn,$mysqli_query);

	if(mysqli_num_rows($result)>0){
	//print("Login success");
	
		$row = mysqli_fetch_assoc($result); 
		$first_name =$row["first_name"]; 
		$last_name =$row["last_name"]; 
		
		Print($first_name."--".$last_name."--".$user_name);

	}
	else{
		print("FAILED"); 
	}

?>