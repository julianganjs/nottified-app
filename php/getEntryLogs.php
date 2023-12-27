<?php

	require "conn.php";
	$mysqli_query = "SELECT a.license_plate_number,b.vehicle_info,a.duration,a.entry_datetime, a.exit_datetime FROM vehicle_entry_session a INNER JOIN vehicle_entry_permit b ON a.license_plate_number=b.license_plate_number order by a.entry_datetime desc";

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