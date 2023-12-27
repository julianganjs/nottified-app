<?php

	require "conn.php";
	$username = $_POST["username"];
	//$username = "czlph5";
	$mysqli_query = "SELECT order_id,dish_name,quantity,cuisine,status FROM order_list where user_name like '$username' and time_received > now() - interval 1 week order by order_id DESC";

	$result = mysqli_query($conn,$mysqli_query);

	if(mysqli_num_rows($result)>0){
	//print("Login success");
	
		$row = mysqli_fetch_all($result); 
		Print("[");
		for ($x = 0; $x < sizeof($row); $x++){
			Print('["'.$row[$x][0].'","'.$row[$x][1].'","'.$row[$x][2].'","'.$row[$x][3].'","'.$row[$x][4].'"]');
			if ($x!=sizeof($row)-1){
				Print(",");
			}
		}
		Print("]");
		

	}
	else{
		print("EMPTY"); 
	}

?>