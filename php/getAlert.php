<?php

	require "conn.php";
	$username = $_POST["username"];
	//$username = "efyjg5";
	$mysqli_query = "SELECT order_id,cuisine FROM order_list where user_name like '$username' and status like 'ready_un' limit 1";

	$result = mysqli_query($conn,$mysqli_query);

	if(mysqli_num_rows($result)>0){
	//print("Login success");
	
		$row = mysqli_fetch_all($result); 
		Print("[");
		for ($x = 0; $x < sizeof($row); $x++){
			Print('["'.$row[$x][0].'","'.$row[$x][1].'","'.$username.'"]');
			if ($x!=sizeof($row)-1){
				Print(",");
			}
		}
		Print("]");
		

	}
	else{
		print("NON"); 
	}

?>