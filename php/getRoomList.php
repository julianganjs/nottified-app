<?php

	require "conn.php";
	$mysqli_query = "SELECT t1.* FROM facilities_status t1 JOIN (SELECT room_no, MAX(created_on) created_on FROM facilities_status GROUP BY room_no) t2 ON t1.room_no = t2.room_no AND t1.created_on = t2.created_on order by room_no ASC";

	$result = mysqli_query($conn,$mysqli_query);

	if(mysqli_num_rows($result)>0){
	//print("Login success");
	
		$row = mysqli_fetch_all($result); 
		Print("[");
		for ($x = 0; $x < sizeof($row); $x++){
			Print('["'.$row[$x][0].'","'.$row[$x][1].'","'.$row[$x][2].'"]');
			if ($x!=sizeof($row)-1){
				Print(",");
			}
		}
		Print("]");
		

	}
	else{
		print("FAILED"); 
	}

?>